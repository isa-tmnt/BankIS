package hello;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetMT910Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;


public class MT910Client  extends WebServiceGatewaySupport{
	 
	public MT910Client(){}
	
	public GetMT9X0Response doMT910Request(GetMT910Request request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/mt910"));
	        
	        return (GetMT9X0Response) response;

    }
}
