package bis.project.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import bis.project.model.BankAccount;
import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankAccountRepository;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.DailyAccountBalanceRepository;
import bis.project.ws.IAmWsClientConfig;
import bis.project.ws.M103Client;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;


@Service
public class BankOrderServicesImpl implements BankOrderServices {

	@Autowired
	private BankOrderRepository repository;
	
	@Autowired
	private BankAccountRepository bankAccountRepo;
	
	@Autowired
	private DailyAccountBalanceRepository dailyRepo;
	
	@Autowired
	private BankAccountRepository bankAccRepo;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Set<BankOrder> getBankOrders() {
		Set<BankOrder> bankOrders = new HashSet<BankOrder>();
		for(BankOrder order : repository.findAll()) {
			bankOrders.add(order);
		}
		
		return bankOrders;
	}

	@Override
	public BankOrder getBankOrder(Integer id) {
		BankOrder bankOrder = repository.findOne(id);
		
		if(bankOrder != null) {
			return bankOrder;
		}
		
		return null;
	}

	@Override
	public BankOrder addBankOrder(BankOrder bankOrder) {
		     	
    	bankOrder.setDailyAccountBalance(getDailtyAccBalanceAndCreateIfDoesntExist(getDebtorForBankOrder(bankOrder)));
    	repository.save(bankOrder);        	
             
        if(checkIfRecipientBelongsToThisBank(bankOrder)){  
        	doMoneyExchangeInsideTHisBank(bankOrder);
        }else{      	  
            if(bankOrder.getAmount().longValue() >= 250000 || bankOrder.isUrgently()){
            	doRTGS(bankOrder);
            }
        }
      
		return bankOrder;
	}
	
	private DailyAccountBalance getDailtyAccBalanceAndCreateIfDoesntExist(BankAccount bankAccount){
		Session session = sessionFactory.openSession();
        session.beginTransaction();
              
		String query = "select * from daily_account_balance where date = '" + getTodayMySqlString() + "'";

        List<DailyAccountBalance> list = session.createSQLQuery(query).addEntity(DailyAccountBalance.class).list();
        if(list.isEmpty()){
			DailyAccountBalance dd = new DailyAccountBalance();
	    	dd.setAccount(bankAccount); // TODO, in paremeters set account id
	    	dd.setAmountCharged(new BigDecimal(0));
	    	dd.setAmountInFavor(new BigDecimal(0));
	    	dd.setDate(new Date());
	    	dd.setNewState(new BigDecimal(0));
	    	dd.setPreviousState(new BigDecimal(0));
      	
	    	dailyRepo.save(dd);
	    	return dd;
        }else{
        	return list.get(0);
        }
	}
	
	private String getTodayMySqlString(){
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = 
             new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);  
	}
	
	private void doMoneyExchangeInsideTHisBank(BankOrder bankOrder){
		// idk if we should do something :/ guess not
	}
	
	private BankAccount getDebtorForBankOrder(BankOrder bankOrder){
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

		String getDebtorAccountQuery = "SELECT * FROM bisdb.bank_account where account_number = " + bankOrder.getDebtor(); 
    	BankAccount accDebtor = (BankAccount) session.createSQLQuery(getDebtorAccountQuery).addEntity(BankAccount.class).uniqueResult();
    	
    	session.close();
    	
    	return accDebtor;
	}
	
	private BankAccount getRecipientForBankOrder(BankOrder bankOrder){
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

		String getRecipientAccountQuery = "SELECT * FROM bisdb.bank_account where account_number = " + bankOrder.getDebtor(); 
    	BankAccount acc = (BankAccount) session.createSQLQuery(getRecipientAccountQuery).addEntity(BankAccount.class).uniqueResult();
    	
    	session.close();
    	
    	return acc;
	}
	
	private boolean checkIfRecipientBelongsToThisBank(BankOrder order){
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        String query = "SELECT * FROM bisdb.bank_account where account_number = " + order.getRecipient();

        List<DailyAccountBalance> list = session.createSQLQuery(query).addEntity(DailyAccountBalance.class).list();
     
        if(!list.isEmpty()){
        	session.close();
        	return true;
        }
        
        session.close();
        
        return false;
	}

	private void doRTGS(BankOrder bankOrder) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	    M103Client wsclient =  context.getBean(M103Client.class);
	    wsclient.setDefaultUri("https://localhost:7779/ws");
	    
	    GetMOneZeroThreeRequest request = new GetMOneZeroThreeRequest();
	    request.setId(new Random().nextInt());
	    request.setDatumNaloga(null);
	    request.setDatumValute(null);
	    request.setDuznikNalogovdavac(bankOrder.getDebtor());
	    request.setIznos(bankOrder.getAmount().doubleValue());
	    request.setModelOdobrenja(Integer.parseInt(bankOrder.getSecondModel()));
	    request.setModelZaduzenja(Integer.parseInt(bankOrder.getFirstModel()));
	    request.setObracunskiRacunBankeDuznika(bankOrder.getDebtor());
	    request.setObracunskiRacunBankePoverioca(bankOrder.getRecipient());
	    request.setPozivNaBrojOdobrenja(bankOrder.getSecondAccount());
	    request.setPozivNaBrojZaduzenja(bankOrder.getFirstAccount());
	    request.setPrimalacPoverilac(bankOrder.getRecipient());
	    request.setRacunDuznika(bankOrder.getDebtor());
	    request.setRacunPoverioca(bankOrder.getRecipient());
	    request.setSifraValute("TODO");
	    request.setSvrhaPlacanja(bankOrder.getPurposeOfPayment());
	    request.setSwiftBankeDuznika("TODO");
	    request.setSwiftBankePoverioca("TODO");

		GetMOneZeroThreeResponse response = wsclient.doM103Request(request);
				
	}

	@Override
	public BankOrder updateBankOrder(BankOrder bankOrder) {
		return repository.save(bankOrder);
	}

	@Override
	public void deleteBankOrder(Integer id) {
		BankOrder bankOrder = repository.findOne(id);
		
		if(bankOrder != null) {
			repository.delete(id);
		}
	}
}
