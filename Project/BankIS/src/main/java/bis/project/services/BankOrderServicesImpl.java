package bis.project.services;

import java.math.BigDecimal;
import java.util.Calendar;
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

import bis.project.model.Bank;
import bis.project.model.BankAccount;
import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankAccountRepository;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.DailyAccountBalanceRepository;
import bis.project.validators.ValidationException;
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
	public BankOrder addBankOrder(BankOrder bankOrder) throws ValidationException{
		     	
		if(!bankAccountExists(bankOrder.getFirstAccount())){
			throw new ValidationException("bank account " + bankOrder.getFirstAccount() + " not found");
		}
		
		if(!bankAccountExists(bankOrder.getSecondAccount())){
			throw new ValidationException("bank account " + bankOrder.getSecondAccount() + " not found");
		}
		
		
		DailyAccountBalance balanceForToday = getDailtyAccBalanceAndCreateIfDoesntExist(getDebtorForBankOrder(bankOrder));
		balanceForToday.setAmountCharged(balanceForToday.getAmountCharged().add(bankOrder.getAmount()));
		balanceForToday.setNewState(balanceForToday.getNewState().add(bankOrder.getAmount().negate()));
		
    	bankOrder.setDailyAccountBalance(balanceForToday);
    	
    	dailyRepo.save(balanceForToday);
    	repository.save(bankOrder);        	
             
        if(checkIfRecipientBelongsToSameBankAsDebtor(bankOrder)){  
        	favorRecipientAccount(bankOrder);
        }else{      	  
            if(bankOrder.getAmount().longValue() >= 250000 || bankOrder.isUrgently()){
            	doRTGS(bankOrder);
            }
        }
      
		return bankOrder;
	}
	
	
	private boolean bankAccountExists(String account) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
              
		String query = "SELECT * FROM bisdb.bank_account where account_number = '" + account+ "'";

        List<BankAccount> list = session.createSQLQuery(query).addEntity(BankAccount.class).list();
        
        session.close();
        
        return !list.isEmpty();
	}

	private DailyAccountBalance getDailtyAccBalanceAndCreateIfDoesntExist(BankAccount bankAccount){
		Session session = sessionFactory.openSession();
        session.beginTransaction();
              
		String query = "select * from daily_account_balance where date = '" + getTodayMySqlString() + "' and account_id = " + bankAccount.getId() + "";

        List<DailyAccountBalance> list = session.createSQLQuery(query).addEntity(DailyAccountBalance.class).list();
        if(list.isEmpty()){
			DailyAccountBalance dd = new DailyAccountBalance();
	    	dd.setAccount(bankAccount);
	    	dd.setAmountCharged(new BigDecimal(0));
	    	dd.setAmountInFavor(new BigDecimal(0));
	    	dd.setDate(new Date());
	    	dd.setNewState(new BigDecimal(0));
	    	dd.setPreviousState(new BigDecimal(0));
	    	
	    	String q = "SELECT * FROM bisdb.daily_account_balance where account_id ='" + bankAccount.getId() + "' and date = '" +getYesterdayMySqlString()+ "' order by id asc";
	    	List<DailyAccountBalance> list2 = session.createSQLQuery(query).addEntity(DailyAccountBalance.class).list();
	    	if(!list2.isEmpty()){
	    		DailyAccountBalance  previousBalance = list2.get(0);
	    		dd.setPreviousState(previousBalance.getNewState());
	    		dd.setNewState(previousBalance.getNewState());	    	
	    	}
      	
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
	private String getYesterdayMySqlString(){
		java.util.Date dt = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime ( dt ); // convert your date to Calendar object
		int daysToDecrement = -1;
		cal.add(Calendar.DATE, daysToDecrement);
		dt = cal.getTime();
        java.text.SimpleDateFormat sdf = 
             new java.text.SimpleDateFormat("yyyy-MM-dd");
     
        return sdf.format(dt);  
	}
	
	private void favorRecipientAccount(BankOrder bankOrder){		
		DailyAccountBalance balance = getDailtyAccBalanceAndCreateIfDoesntExist(getRecipientForBankOrder(bankOrder));
		balance.setAmountInFavor(balance.getAmountInFavor().add(bankOrder.getAmount()));
		balance.setNewState(balance.getNewState().add(bankOrder.getAmount()));
		dailyRepo.save(balance);
	}
	
	private BankAccount getDebtorForBankOrder(BankOrder bankOrder) throws ValidationException{
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

		String getDebtorAccountQuery = "SELECT * FROM bisdb.bank_account where account_number = " + bankOrder.getFirstAccount(); 
    	BankAccount accDebtor = (BankAccount) session.createSQLQuery(getDebtorAccountQuery).addEntity(BankAccount.class).uniqueResult();
    	
    	session.close();
    	
    	if(accDebtor == null)
    		throw new ValidationException("Debtor not found: " + bankOrder.getDebtor());
    	
    	return accDebtor;
	}
	
	private BankAccount getRecipientForBankOrder(BankOrder bankOrder){
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

		String getRecipientAccountQuery = "SELECT * FROM bisdb.bank_account where account_number = " + bankOrder.getSecondAccount(); 
    	BankAccount acc = (BankAccount) session.createSQLQuery(getRecipientAccountQuery).addEntity(BankAccount.class).uniqueResult();
    	
    	session.close();
    	
    	return acc;
	}
	
	private boolean checkIfRecipientBelongsToSameBankAsDebtor(BankOrder order){
		/*Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        String query = "SELECT * FROM bisdb.bank_account where account_number = " + order.getRecipient();

        List<DailyAccountBalance> list = session.createSQLQuery(query).addEntity(DailyAccountBalance.class).list();
     
        if(!list.isEmpty()){
        	session.close();
        	return true;
        }
        
        session.close();*/
		return order.getFirstAccount().substring(0, 2).equals(order.getSecondAccount().substring(0, 2));
	}

	private void doRTGS(BankOrder bankOrder) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	    M103Client wsclient =  context.getBean(M103Client.class);
	    wsclient.setDefaultUri("https://localhost:7779/ws");
	    
	    GetMOneZeroThreeRequest request = new GetMOneZeroThreeRequest();
	    request.setId(Integer.toString(new Random().nextInt()));
	    request.setDatumNaloga(null);
	    request.setDatumValute(null);
	    request.setDuznikNalogodavac(bankOrder.getDebtor());
	    request.setIznos(bankOrder.getAmount());
	    request.setModelOdobrenja(new BigDecimal(bankOrder.getSecondModel()));
	    request.setModelZaduzenja(new BigDecimal(bankOrder.getFirstModel()));
	    request.setObracunskiRacunBankeDuznika("123456789012345678");
	    request.setObracunskiRacunBankePoverioca("123456789012345678");
	    request.setPozivNaBrojOdobrenja(bankOrder.getSecondAccount());
	    request.setPozivNaBrojZaduzenja(bankOrder.getFirstAccount());
	    request.setPrimalacPoverilac(bankOrder.getRecipient());
	    request.setRacunDuznika(bankOrder.getFirstAccount());
	    request.setRacunPoverioca(bankOrder.getSecondAccount());
	    request.setSifraValute("TOD");
	    request.setSvrhaPlacanja(bankOrder.getPurposeOfPayment());
	    request.setSwiftBankeDuznika(getSwiftOfBankCode(bankOrder.getFirstAccount().substring(0,3)));
	    request.setSwiftBankePoverioca(getSwiftOfBankCode(bankOrder.getSecondAccount().substring(0,3)));

		GetMOneZeroThreeResponse response = wsclient.doM103Request(request);
				
	}
	private String getSwiftOfBankCode(String bankCode) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        String query = "SELECT * FROM bisdb.bank where bank_code = " + bankCode;

        List<Bank> list = session.createSQLQuery(query).addEntity(Bank.class).list();
     
        if(list.isEmpty()){
        	session.close();        		
        	return null;
        }
        
        session.close();
        
        return list.get(0).getSwiftCode();
	}
	
	public void processMt103FromCentralBank(BankOrder bankOrder) throws ValidationException{
		if(!bankAccountExists(bankOrder.getFirstAccount())){
			throw new ValidationException("bank account " + bankOrder.getFirstAccount() + " not found");
		}
		
		if(!bankAccountExists(bankOrder.getSecondAccount())){
			throw new ValidationException("bank account " + bankOrder.getSecondAccount() + " not found");
		}
				
		favorRecipientAccount(bankOrder);
	}

	//TODO we dont need this
	@Override
	public BankOrder updateBankOrder(BankOrder bankOrder) {
		return repository.save(bankOrder);
	}
	//TODO we dont need this
	@Override
	public void deleteBankOrder(Integer id) {
		BankOrder bankOrder = repository.findOne(id);
		
		if(bankOrder != null) {
			repository.delete(id);
		}
	}
}
