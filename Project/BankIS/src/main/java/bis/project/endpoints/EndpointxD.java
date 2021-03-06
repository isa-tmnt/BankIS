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
import bis.project.services.BankOrderServices;
import bis.project.services.ClearingAndSService;
import bis.project.validators.ValidationException;
import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;
import io.spring.guides.gs_producing_web_service.GetMT900Request;
import io.spring.guides.gs_producing_web_service.GetMT910Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;
import io.spring.guides.gs_producing_web_service.IzvodRequest;
import io.spring.guides.gs_producing_web_service.IzvodResponse;
import io.spring.guides.gs_producing_web_service.NalogZaPlacanje;

@Endpoint
public class EndpointxD {

	@Autowired
	private BankOrderServices orderService;
	
	@Autowired
	private ClearingAndSService clearingService;
	
	
	@PayloadRoot(namespace = "http://banka/importOrder", localPart = "importNalogZaPlacanjeRequest")
	@SoapAction("http://banka/importOrder")
	@ResponsePayload
	public ImportNalogZaPlacanjeResponse importOrder(@RequestPayload NalogZaPlacanje request) throws ValidationException {
		
		BankOrder order = new BankOrder();

		
		order.setAmount(request.getIznos());
		order.setBankOrderDate(new Date());
		order.setCurrencyDate(new Date());
		order.setDebtor(request.getDuznikNalogodavac());
		order.setDirection("A");
		order.setFirstAccount(request.getRacunDuznika());
		order.setFirstModel(String.valueOf(request.getModelZaduzenja()));
		order.setFirstNumber(request.getPozivNaBrojZaduzenja());
		order.setOrderDate(new Date());
		order.setPurposeOfPayment(request.getSvrhaPlacanja());
		order.setRecipient(request.getPrimalacPoverilac());
		order.setSecondAccount(request.getRacunPoverioca());
		order.setSecondModel(String.valueOf(request.getModelOdobrenja()));
		order.setSecondNumber(request.getPozivNaBrojOdobrenja());
		order.setUrgently(request.isHitno());
		
	
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
	
	
	@PayloadRoot(namespace = "http://banka/mt103", localPart = "getMT103Request")
	@SoapAction("http://banka/mt103")
	@ResponsePayload
	public GetMOneZeroThreeResponse mt103(@RequestPayload GetMOneZeroThreeRequest request) throws ValidationException {
		
		
		BankOrder order = new BankOrder();
		
		order.setAmount(request.getIznos());
		order.setBankOrderDate(new Date());
		order.setCurrencyDate(new Date());
		order.setDebtor(request.getDuznikNalogodavac());
		order.setDirection("A");
		order.setFirstAccount(request.getRacunDuznika());
		order.setFirstModel(String.valueOf(request.getModelZaduzenja()));
		order.setFirstNumber(request.getPozivNaBrojZaduzenja());
		order.setOrderDate(new Date());
		order.setPurposeOfPayment(request.getSvrhaPlacanja());
		order.setRecipient(request.getPrimalacPoverilac());
		order.setSecondAccount(request.getRacunPoverioca());
		order.setSecondModel(String.valueOf(request.getModelOdobrenja()));
		order.setSecondNumber(request.getPozivNaBrojOdobrenja());

		orderService.processMt103FromCentralBank(order);
		
		GetMOneZeroThreeResponse response = new GetMOneZeroThreeResponse();
		
		
		return response;
	}
	

	@PayloadRoot(namespace = "http://banka/mt102", localPart = "getMT102Request")
	@SoapAction("http://banka/mt102")
	@ResponsePayload
	public GetM102Response mt102(@RequestPayload GetM102Request request) throws ValidationException {
		
		GetM102Response response = new GetM102Response();
		response.setId(123);
		
		clearingService.messageFromCentralBank(request);
		
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