package client;

import org.springframework.context.annotation.Bean;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;
import io.spring.guides.gs_producing_web_service.IzvodRequest;
import io.spring.guides.gs_producing_web_service.IzvodResponse;


public class IzvodRacunaClient  extends WebServiceGatewaySupport{
	 
	public IzvodRacunaClient(){}
	
	public IzvodResponse getBeer(IzvodRequest request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/dajIzvode"));
	        
	        return (IzvodResponse) response;

	    }
}
