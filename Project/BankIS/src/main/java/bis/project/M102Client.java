package bis.project;

import java.io.IOException;

import org.springframework.ws.FaultAwareWebServiceMessage;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.FaultAwareWebServiceConnection;
import org.springframework.ws.transport.WebServiceConnection;

import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;


public class M102Client  extends WebServiceGatewaySupport{
	 
	public M102Client(){}
	
	public GetM102Response doM102Request(GetM102Request request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request);
	        
	        return (GetM102Response) response;

    }
	
	
}
