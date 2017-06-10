package bis.project.services;

import java.util.Set;

import bis.project.model.DailyAccountBalance;
import bis.project.model.InterbankTransfer;

public interface InterbankTransferServices {
	public Set<InterbankTransfer> getAllInterbankTrasfers();
		
		public InterbankTransfer getInterbankTransfer(Integer id);
		
		public InterbankTransfer addInterbankTransfer(InterbankTransfer interbankTransfer);
		
		public InterbankTransfer updateInterbankTransfer(InterbankTransfer interbankTransfer);
		
		public void deleteInterbankTrasfer(Integer id);
}
