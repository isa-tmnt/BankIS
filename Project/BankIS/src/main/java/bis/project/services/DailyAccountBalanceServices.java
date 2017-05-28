package bis.project.services;

import java.util.Set;

import bis.project.model.DailyAccountBalance;

public interface DailyAccountBalanceServices {
	
	public Set<DailyAccountBalance> getAllDailyAccountBalances();
	
	public DailyAccountBalance getDailyAccountBalance(Integer id);
	
	public DailyAccountBalance addDailyAccountBalance(DailyAccountBalance accountBalance);
	
	public DailyAccountBalance updateDailyAccountBalance(DailyAccountBalance accountBalance);
	
	public void deleteDailyAccountBalance(Integer id);
}
