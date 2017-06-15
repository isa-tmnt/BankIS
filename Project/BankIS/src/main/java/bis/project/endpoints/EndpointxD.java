package bis.project.endpoints;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import bis.project.model.BankOrder;
import bis.project.repositories.DailyAccountBalanceRepository;
import bis.project.services.BankOrderServices;
import io.spring.guides.gs_producing_web_service.GetMT900Request;
import io.spring.guides.gs_producing_web_service.GetMT910Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeRequest;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;
import io.spring.guides.gs_producing_web_service.IzvodRequest;
import io.spring.guides.gs_producing_web_service.IzvodResponse;

@Endpoint
public class EndpointxD {

	@Autowired
	private BankOrderServices orderService;
	
	
	@PayloadRoot(namespace = "http://banka/importOrder", localPart = "importNalogZaPlacanjeRequest")
	@SoapAction("http://banka/importOrder")
	@ResponsePayload
	public ImportNalogZaPlacanjeResponse importOrder(@RequestPayload ImportNalogZaPlacanjeRequest request) {
		
		BankOrder order = new BankOrder();

		
		order.setAmount(new BigDecimal(request.getAmount()));
		order.setBankOrderDate(new Date());
		order.setCurrencyDate(new Date());
		order.setDebtor(request.getDebtor());
		order.setDirection(request.getDirection());
		order.setFirstAccount(request.getFirstAccount());
		order.setFirstModel(String.valueOf(request.getFirstModel()));
		order.setFirstNumber(request.getFirstNumber());
		order.setOrderDate(new Date());
		order.setPurposeOfPayment(request.getPurposeOfPayment());
		order.setRecipient(request.getRecipient());
		order.setSecondAccount(request.getSecondAccount());
		order.setSecondModel(String.valueOf(request.getSecondModel()));
		order.setSecondNumber(request.getSecondNumber());
		order.setUrgently(request.isUrgently());
		
	
		order.setId(null);
		
		BankOrder savedOrder = orderService.addBankOrder(order);
		
		ImportNalogZaPlacanjeResponse response = new ImportNalogZaPlacanjeResponse();
		response.setCreatedBankOrderId("asdf");
		
		return response;
	}
	
	
	@PayloadRoot(namespace = "http://banka/dajIzvode", localPart = "izvodRequest")
	@SoapAction("http://banka/dajIzvode")
	@ResponsePayload
	public IzvodResponse izvodRequest(@RequestPayload IzvodRequest request) {
		
		IzvodResponse response = new IzvodResponse();
		
		return response;
	}
	
	@PayloadRoot(namespace = "http://banka/mt900", localPart = "getMT900Request")
	@SoapAction("http://banka/mt900")
	@ResponsePayload
	public GetMT9X0Response mt900(@RequestPayload GetMT900Request request) {
		
		GetMT9X0Response response = new GetMT9X0Response();
		
		return response;
	}
	
	@PayloadRoot(namespace = "http://banka/mt910", localPart = "getMT910Request")
	@SoapAction("http://banka/mt910")
	@ResponsePayload
	public GetMT9X0Response mt910(@RequestPayload GetMT910Request request) {
		
		GetMT9X0Response response = new GetMT9X0Response();
		
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