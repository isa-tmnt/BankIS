package bis.project.services;

import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import bis.project.model.BankAccount;
import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankAccountRepository;
import bis.project.ws.IAmWsClientConfig;
import bis.project.ws.M102Client;
import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;
import io.spring.guides.gs_producing_web_service.M102StavkaType;

@Service
public class ClearingAndSService {
	
	@Autowired
	private BankAccountRepository accRepo;
		
	@Autowired
    private SessionFactory sessionFactory;
	
	
	
	public void DoIt(){
		Session session = sessionFactory.openSession();
        session.beginTransaction();
              
        
		String query = "SELECT * FROM bisdb.bank_order where amount < 250000 and urgently != 1";

        List<BankOrder> list = session.createSQLQuery(query).addEntity(BankOrder.class).list();
       
        session.close();
        
        GetM102Request request = new GetM102Request();
        request.setDatum(null);
        request.setDatumValute(null);
        request.setId(new Random().nextInt());
        request.setObracunskiRacunBankeDuznika("TODO");
        request.setObracunskiRacunBankePoverioca("TODO");
        request.setSifraValute("");
        request.setSwiftBankeDuznika("TODO");
        request.setSwiftBankePoverioca("TODO");
        request.setUkupanIznos(000000);
        
        for(int i =0; i < list.size(); i++){
        	BankOrder order = list.get(i);
        	
        	M102StavkaType stavka = new M102StavkaType();
        	stavka.setDatumNaloga(null);
        	stavka.setDuznikNalogodavac(order.getDebtor());
        	stavka.setIdNalogaZaPlacanje("");
        	stavka.setIznos(order.getAmount());
        	stavka.setModelOdobrenja(Integer.parseInt(order.getSecondModel()));
        	stavka.setModelZaduzenja(Integer.parseInt(order.getFirstModel()));
        	stavka.setPozivNaBrojOdobrenja(order.getSecondNumber());
        	stavka.setPozivNaBrojZaduzenja(order.getFirstNumber());
        	stavka.setPrimalacPoverilac(order.getRecipient());
        	stavka.setRacunDuznika(order.getDebtor());
        	stavka.setRacunPoverioca(order.getSecondAccount());
        	stavka.setSifraValute("random");
        	stavka.setSvrhaPlacanja(order.getPurposeOfPayment());
        	
            request.getGetM102Stavka().add(stavka);
        }
             
    
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	     M102Client wsclient2 =  context.getBean(M102Client.class);
	    wsclient2.setDefaultUri("http://localhost:7779/ws");
		GetM102Response response2 = wsclient2.doM102Request(request);
              
	}
}
