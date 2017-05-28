package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.DailyAccountBalance;
import bis.project.repositories.DailyAccountBalanceRepository;

@Service
public class DailyAccountBalanceServicesImpl implements DailyAccountBalanceServices {
	
	@Autowired
	private DailyAccountBalanceRepository dabRepository;
	
	@Override
	public Set<DailyAccountBalance> getAllDailyAccountBalances() {
		Set<DailyAccountBalance> dabs = new HashSet<DailyAccountBalance>();
		for(DailyAccountBalance dab : dabRepository.findAll()) {
			dabs.add(dab);
		}
		return dabs;
	}

	@Override
	public DailyAccountBalance getDailyAccountBalance(Integer id) {
		DailyAccountBalance dab = dabRepository.findOne(id);
		
		if(dab != null) {
			return dab;
		}
		
		return null;
	}

	@Override
	public DailyAccountBalance addDailyAccountBalance(DailyAccountBalance accountBalance) {
		return dabRepository.save(accountBalance);
	}

	@Override
	public DailyAccountBalance updateDailyAccountBalance(DailyAccountBalance accountBalance) {
		return dabRepository.save(accountBalance);
	}

	@Override
	public void deleteDailyAccountBalance(Integer id) {
		DailyAccountBalance dab = dabRepository.findOne(id);
		
		if(dab != null) {
			dabRepository.delete(id);
		}
	}
}
