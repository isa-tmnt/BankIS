package bis.project.helpers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.Bank;
import bis.project.model.BankAccount;
import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankAccountRepository;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.DailyAccountBalanceRepository;
import bis.project.validators.ValidationException;

@Service
public class CommonBankOperations {

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
	
	public boolean bankAccountExists(String account) {
		Session session = sessionFactory.openSession();
        session.beginTransaction();
              
		String query = "SELECT * FROM bisdb.bank_account where account_number = '" + account+ "'";

        List<BankAccount> list = session.createSQLQuery(query).addEntity(BankAccount.class).list();
        
        session.close();
        
        return !list.isEmpty();
	}

	public DailyAccountBalance getDailtyAccBalanceAndCreateIfDoesntExist(BankAccount bankAccount){
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
	
	public String getTodayMySqlString(){
		java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = 
             new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(dt);  
	}
	public String getYesterdayMySqlString(){
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
	
	public XMLGregorianCalendar today(){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	public XMLGregorianCalendar xmlFromDate(Date d){
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void chargeDebtorAccount(BankOrder bankOrder) throws ValidationException{		
		DailyAccountBalance balance = getDailtyAccBalanceAndCreateIfDoesntExist(getDebtorForBankOrder(bankOrder));
		balance.setAmountCharged(balance.getAmountCharged().add(bankOrder.getAmount()));
		balance.setNewState(balance.getNewState().add(bankOrder.getAmount().negate()));
		dailyRepo.save(balance);
		
		Integer previousId = bankOrder.getId();
    	DailyAccountBalance previousBalance = bankOrder.getDailyAccountBalance();
		
    	bankOrder.setId(null);
    	bankOrder.setDailyAccountBalance(balance);
    	
    	BankOrder saved = repository.save(bankOrder);
    	
    	bankOrder.setId(previousId);
    	bankOrder.setDailyAccountBalance(previousBalance);
	}
		
	public void favorRecipientAccount(BankOrder bankOrder) throws ValidationException{		
		DailyAccountBalance balance = getDailtyAccBalanceAndCreateIfDoesntExist(getRecipientForBankOrder(bankOrder));
		balance.setAmountInFavor(balance.getAmountInFavor().add(bankOrder.getAmount()));
		balance.setNewState(balance.getNewState().add(bankOrder.getAmount()));
		dailyRepo.save(balance);
		
		Integer previousId = bankOrder.getId();
		DailyAccountBalance previousBalance = bankOrder.getDailyAccountBalance();
    	
    	bankOrder.setId(null);
    	bankOrder.setDailyAccountBalance(balance);
    	
    	BankOrder saved = repository.save(bankOrder);
    	
    	bankOrder.setId(previousId);
    	bankOrder.setDailyAccountBalance(previousBalance);
	}
	
	public BankAccount getDebtorForBankOrder(BankOrder bankOrder) throws ValidationException{
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

		String getDebtorAccountQuery = "SELECT * FROM bisdb.bank_account where account_number = " + bankOrder.getFirstAccount(); 
    	BankAccount accDebtor = (BankAccount) session.createSQLQuery(getDebtorAccountQuery).addEntity(BankAccount.class).uniqueResult();
    	
    	session.close();
    	
    	if(accDebtor == null)
    		throw new ValidationException("Debtor not found: " + bankOrder.getDebtor());
    	
    	return accDebtor;
	}
	
	public BankAccount getRecipientForBankOrder(BankOrder bankOrder) throws ValidationException{
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

		String getRecipientAccountQuery = "SELECT * FROM bisdb.bank_account where account_number = " + bankOrder.getSecondAccount(); 
    	BankAccount acc = (BankAccount) session.createSQLQuery(getRecipientAccountQuery).addEntity(BankAccount.class).uniqueResult();
    	
    	session.close();
    	
    	if(acc == null)
    		throw new ValidationException("Recipient not found: " + bankOrder.getRecipient());
    	
    	
    	return acc;
	}
	
	public boolean checkIfRecipientBelongsToSameBankAsDebtor(BankOrder order){
		return order.getFirstAccount().substring(0, 2).equals(order.getSecondAccount().substring(0, 2));
	}
	
	public String getSwiftOfBankCode(String bankCode) {
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
}
