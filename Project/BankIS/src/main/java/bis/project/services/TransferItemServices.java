package bis.project.services;

import java.util.Set;

import bis.project.model.TransferItem;

public interface TransferItemServices {
	
	public Set<TransferItem> getTransferItems();
	
	public TransferItem getTransferItem(Integer id);
	
	public TransferItem addTransferItem(TransferItem transferItem);
	
	public TransferItem updateTransferItem(TransferItem transferItem);
	
	public void deleteTransferItem(Integer id);
}
