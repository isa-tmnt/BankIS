package bis.project.endpoints;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import bis.project.model.BankOrder;
import bis.project.model.DailyAccountBalance;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.DailyAccountBalanceRepository;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeRequest;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;

@Endpoint
public class ImportBankOrderEndpoint {
private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private static int idPoruke;
	
	@Autowired
	private BankOrderRepository orderRepo;
	
	@Autowired
	private DailyAccountBalanceRepository dailyRepo;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "importNalogZaPlacanjeRequest")
	@ResponsePayload
	public ImportNalogZaPlacanjeResponse importOrder(@RequestPayload ImportNalogZaPlacanjeRequest request) {
		
		BankOrder order = new BankOrder();
		Iterable<DailyAccountBalance> dailties = dailyRepo.findAll();
		
		
		order.setAmount(new BigDecimal(request.getAmount()));
		order.setBankOrderDate(new Date());
		order.setCurrencyDate(request.getCurrensyDate().toGregorianCalendar().getTime());
		order.setDailyAccountBalance(getLastElement(dailties)); 
		order.setDebtor(request.getDebtor());
		order.setDirection(request.getDirection());
		order.setFirstAccount(request.getFirstAccount());
		order.setFirstModel(request.getFirstModel());
		order.setFirstNumber(request.getFirstNumber());
		order.setOrderDate(new Date());
		order.setPurposeOfPayment(request.getPurposeOfPayment());
		order.setRecipient(request.getRecipient());
		order.setSecondAccount(request.getSecondAccount());
		order.setSecondModel(request.getSecondModel());
		order.setSecondNumber(request.getSecondNumber());
		order.setUrgently(request.isUrgently());
		
		BankOrder savedOrder = orderRepo.save(order);
		
		ImportNalogZaPlacanjeResponse response = new ImportNalogZaPlacanjeResponse();
		response.setCreatedBankOrderId(savedOrder.getId());
		
		return response;
	}
	
	public static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while(itr.hasNext()) {
            lastElement=itr.next();
        }

        return lastElement;
    }
}