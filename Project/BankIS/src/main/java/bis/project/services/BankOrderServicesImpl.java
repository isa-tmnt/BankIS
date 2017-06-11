package bis.project.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;

import bis.project.BankIsApplication;
import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankAccountRepository;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.DailyAccountBalanceRepository;


@Service
public class BankOrderServicesImpl implements BankOrderServices {

	@Autowired
	private BankOrderRepository repository;
	
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
		bankOrder = repository.save(bankOrder);
		
		Session session = sessionFactory.openSession();
        session.beginTransaction();

        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = 
             new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentDate = sdf.format(dt);       
        String query = "select * from daily_account_balance where date = '" + currentDate + "'";


        List<DailyAccountBalance> list = session.createSQLQuery(query).addEntity(DailyAccountBalance.class).list();
        if(list.isEmpty()){
        	DailyAccountBalance dd = new DailyAccountBalance();
        	dd.setAccount(bankAccRepo.findOne(1)); // TODO, in paremeters set account id
        	dd.setAmountCharged(new BigDecimal(0));
        	dd.setAmountInFavor(new BigDecimal(0));
        	dd.setDate(new Date());
        	dd.setNewState(new BigDecimal(0));
        	dd.setPreviousState(new BigDecimal(0));
          	
        	dailyRepo.save(dd);
        	
        	bankOrder.setDailyAccountBalance(dd);
        	repository.save(bankOrder);        	
        }else{
        	bankOrder.setDailyAccountBalance(list.get(0));
        	repository.save(bankOrder);  
        }
        
        session.close();
		
		
		
		return bankOrder;
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
