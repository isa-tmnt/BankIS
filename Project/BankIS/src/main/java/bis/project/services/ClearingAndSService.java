package bis.project.services;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import bis.project.helpers.CommonBankOperations;
import bis.project.model.Bank;
import bis.project.model.BankAccount;
import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankAccountRepository;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.BankRepository;
import bis.project.repositories.DailyAccountBalanceRepository;
import bis.project.validators.ValidationException;
import bis.project.ws.IAmWsClientConfig;
import bis.project.ws.M102Client;
import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;
import io.spring.guides.gs_producing_web_service.M102StavkaType;
import io.spring.guides.gs_producing_web_service.StavkaPreseka;

@Service
public class ClearingAndSService {
		
	@Autowired
    private SessionFactory sessionFactory;

	@Autowired
	private BankRepository bankRepo;
	
	@Autowired
	private BankOrderRepository orderRepo;
	
	
	
	@Autowired
	private CommonBankOperations commons;
	
	private static Date lastClearingTime;
	
	public ClearingAndSService(){
		if(lastClearingTime == null){
			final Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.DATE, -10000);
		    lastClearingTime = cal.getTime();
		}
	}
	
	public void DoIt(){
		for(Bank bankSender : bankRepo.findAll()){
			for(Bank bankRecipent : bankRepo.findAll())
				doForOneBank(bankSender,bankRecipent);
		}
		lastClearingTime = new Date();
	}
	
	
	public void doForOneBank(Bank bankSender, Bank bankRecipient){
		Session session = sessionFactory.openSession();
        session.beginTransaction();
              
		String queryString = "SELECT * FROM bisdb.bank_order where amount < 250000 and urgently != b'1' and substring(first_account,1,3) = :bankSenderCode and substring(second_account,1,3) = :bankRecipientCode and bank_order_date > :lastClearingTime and direction = 'A'";

       SQLQuery query = session.createSQLQuery(queryString).addEntity(BankOrder.class);
       query.setInteger("bankSenderCode", bankSender.getBankCode());
       query.setInteger("bankRecipientCode", bankRecipient.getBankCode());
       query.setTimestamp("lastClearingTime", lastClearingTime);
       List<BankOrder> list =  query.list();
       session.close();
        
        if(list.isEmpty()){
    	   return;
        }
             
        GetM102Request request = new GetM102Request();
        request.setDatum(commons.today());
        request.setDatumValute(commons.today());
        request.setId(Integer.toString(new Random().nextInt()));
        request.setObracunskiRacunBankeDuznika("123456789012345678");
        request.setObracunskiRacunBankePoverioca("123456789012345678");
        request.setSifraValute("TOD");
        request.setSwiftBankeDuznika(bankSender.getSwiftCode());
        request.setSwiftBankePoverioca(bankRecipient.getSwiftCode());
        request.setUkupanIznos(new BigDecimal(0));
        
        for(int i =0; i < list.size(); i++){
        	BankOrder order = list.get(i);
        	
        	M102StavkaType stavka = new M102StavkaType();
        	stavka.setDatumNaloga(commons.xmlFromDate(order.getOrderDate()));
        	stavka.setDatumValute(commons.xmlFromDate(order.getOrderDate()));
        	stavka.setDuznikNalogodavac(order.getDebtor());
        	stavka.setIdNalogaZaPlacanje("000000" + Integer.toString(order.getId()));
        	stavka.setIznos(order.getAmount());
        	stavka.setModelOdobrenja(new BigDecimal(order.getSecondModel()));
        	stavka.setModelZaduzenja(new BigDecimal(order.getFirstModel()));
        	stavka.setPozivNaBrojOdobrenja(order.getSecondNumber());
        	stavka.setPozivNaBrojZaduzenja(order.getFirstNumber());
        	stavka.setPrimalacPoverilac(order.getRecipient());
        	stavka.setRacunDuznika(order.getFirstAccount());
        	stavka.setRacunPoverioca(order.getSecondAccount());
        	stavka.setOznakaValute("TOD");
        	stavka.setSvrhaPlacanja(order.getPurposeOfPayment());
        	
        	request.setUkupanIznos(request.getUkupanIznos().add(order.getAmount()));
        	
            request.getGetM102Stavka().add(stavka);
        }
             
    
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	     M102Client wsclient2 =  context.getBean(M102Client.class);
	    wsclient2.setDefaultUri("https://localhost:7779/ws");
		GetM102Response response2 = wsclient2.doM102Request(request);
              
	}
	
	
	

	public void messageFromCentralBank(GetM102Request request) throws ValidationException {
		
		for(M102StavkaType st : request.getGetM102Stavka()){
			BankOrder order = new BankOrder();
			order.setAmount(st.getIznos());
			order.setBankOrderDate(st.getDatumNaloga().toGregorianCalendar().getTime());
			order.setCurrencyDate(new Date());
			order.setDebtor(st.getDuznikNalogodavac());
			order.setDirection("B");
			order.setFirstAccount(st.getRacunDuznika());
			order.setFirstModel(st.getModelZaduzenja().toString());
			order.setFirstNumber(st.getPozivNaBrojZaduzenja());
			order.setSecondAccount(st.getRacunPoverioca());
			order.setSecondModel(st.getModelOdobrenja().toString());
			order.setSecondNumber(st.getPozivNaBrojOdobrenja());
			order.setId(null);
			order.setOrderDate(st.getDatumNaloga().toGregorianCalendar().getTime());
			order.setPurposeOfPayment(st.getSvrhaPlacanja());
			order.setRecipient(st.getPrimalacPoverilac());
			order.setUrgently(false);
			
			commons.favorRecipientAccount(order);
		}
		
		
	}
	
}
