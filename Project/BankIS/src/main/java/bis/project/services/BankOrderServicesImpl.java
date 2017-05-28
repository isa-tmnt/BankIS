package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bis.project.model.BankOrder;
import bis.project.repositories.BankOrderRepository;

@Service
public class BankOrderServicesImpl implements BankOrderServices {

	@Autowired
	private BankOrderRepository repository;
	
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
		return repository.save(bankOrder);
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
