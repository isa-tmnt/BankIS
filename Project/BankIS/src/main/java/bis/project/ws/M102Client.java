package bis.project.ws;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;


public class M102Client  extends WebServiceGatewaySupport{
	 
	public M102Client(){}
	
	public GetM102Response doM102Request(GetM102Request request){
	        
		Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/m102"));
	        
	        return (GetM102Response) response;

    }
	
	
}
