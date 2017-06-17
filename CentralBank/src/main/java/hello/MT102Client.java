package hello;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;
import io.spring.guides.gs_producing_web_service.GetMT900Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;


public class MT102Client  extends WebServiceGatewaySupport{
	 
	public MT102Client(){}
	
	public GetM102Response doMT102Request(GetM102Request request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/mt102"));
	        
	        return (GetM102Response) response;

    }
}
