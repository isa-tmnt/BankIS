package client;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;
import io.spring.guides.gs_producing_web_service.NalogZaPlacanje;


public class BankOrderClient  extends WebServiceGatewaySupport{
	 
	public BankOrderClient(){}
	
	public ImportNalogZaPlacanjeResponse getBeer(NalogZaPlacanje request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/importOrder"));
	        
	        return (ImportNalogZaPlacanjeResponse) response;

	    }
}
