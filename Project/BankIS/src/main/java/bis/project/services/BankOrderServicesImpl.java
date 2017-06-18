package bis.project.services;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import bis.project.helpers.CommonBankOperations;
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
	
	@Autowired
	private CommonBankOperations commons;
	
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
		     	
		if(!commons.bankAccountExists(bankOrder.getFirstAccount())){
			throw new ValidationException("bank account " + bankOrder.getFirstAccount() + " not found");
		}
		
		if(!commons.bankAccountExists(bankOrder.getSecondAccount())){
			throw new ValidationException("bank account " + bankOrder.getSecondAccount() + " not found");
		}
		
			
		commons.chargeDebtorAccount(bankOrder);
		
        if(commons.checkIfRecipientBelongsToSameBankAsDebtor(bankOrder)){
        	bankOrder.setDirection("B");
        	commons.favorRecipientAccount(bankOrder);
        	bankOrder.setDirection("A");
        }else{      	  
            if(bankOrder.getAmount().longValue() >= 250000 || bankOrder.isUrgently()){         	
            	doRTGS(bankOrder);
            }
        }
      
		return bankOrder;
	}
	
	
	

	private void doRTGS(BankOrder bankOrder) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	    M103Client wsclient =  context.getBean(M103Client.class);
	    wsclient.setDefaultUri("https://localhost:7779/ws");
	    
	    GetMOneZeroThreeRequest request = new GetMOneZeroThreeRequest();
	    request.setId(Integer.toString(new Random().nextInt()));
	    request.setDatumNaloga(commons.xmlFromDate(bankOrder.getBankOrderDate()));
	    request.setDatumValute(commons.xmlFromDate(bankOrder.getCurrencyDate()));
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
	    request.setSwiftBankeDuznika(commons.getSwiftOfBankCode(bankOrder.getFirstAccount().substring(0,3)));
	    request.setSwiftBankePoverioca(commons.getSwiftOfBankCode(bankOrder.getSecondAccount().substring(0,3)));

		GetMOneZeroThreeResponse response = wsclient.doM103Request(request);
				
	}

	
	public void processMt103FromCentralBank(BankOrder bankOrder) throws ValidationException{
		if(!commons.bankAccountExists(bankOrder.getFirstAccount())){
			throw new ValidationException("bank account " + bankOrder.getFirstAccount() + " not found");
		}
		
		if(!commons.bankAccountExists(bankOrder.getSecondAccount())){
			throw new ValidationException("bank account " + bankOrder.getSecondAccount() + " not found");
		}
				
		bankOrder.setDirection("B");
		commons.favorRecipientAccount(bankOrder);
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
