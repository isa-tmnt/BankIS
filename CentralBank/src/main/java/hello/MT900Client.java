package hello;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetMT900Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;


public class MT900Client  extends WebServiceGatewaySupport{
	 
	public MT900Client(){}
	
	public GetMT9X0Response doMT900Request(GetMT900Request request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/mt900"));
	        
	        return (GetMT9X0Response) response;

    }
}
