package hello;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;
import io.spring.guides.gs_producing_web_service.GetMT900Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;


public class MT103Client  extends WebServiceGatewaySupport{
	 
	public MT103Client(){}
	
	public GetMOneZeroThreeResponse doMT103Request(GetMOneZeroThreeRequest request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/mt103"));
	        
	        return (GetMOneZeroThreeResponse) response;

    }
}
