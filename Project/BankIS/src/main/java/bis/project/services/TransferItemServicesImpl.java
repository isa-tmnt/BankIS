package bis.project.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bis.project.model.TransferItem;
import bis.project.repositories.TransferItemRepository;

@Service
public class TransferItemServicesImpl implements TransferItemServices {
	
	@Autowired
	private TransferItemRepository transferItemRepository;
	
	@Override
	public Set<TransferItem> getTransferItems() {
		Set<TransferItem> transferItems = new HashSet<TransferItem>();
		for(TransferItem item : transferItemRepository.findAll()) {
			transferItems.add(item);
		}
		
		return transferItems;
	}

	@Override
	public TransferItem getTransferItem(Integer id) {
		TransferItem transferItem = transferItemRepository.findOne(id);
		
		if(transferItem != null) {
			return transferItem;
		}
		
		return null;
	}

	@Override
	public TransferItem addTransferItem(TransferItem transferItem) {
		return transferItemRepository.save(transferItem);
	}

	@Override
	public TransferItem updateTransferItem(TransferItem transferItem) {
		return transferItemRepository.save(transferItem);
	}

	@Override
	public void deleteTransferItem(Integer id) {
		TransferItem transferItem = transferItemRepository.findOne(id);
		
		if(transferItem != null) {
			transferItemRepository.delete(id);
		}
	}
}
