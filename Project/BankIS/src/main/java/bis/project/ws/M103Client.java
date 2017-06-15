package bis.project.ws;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;


public class M103Client  extends WebServiceGatewaySupport{
	 
	public M103Client(){}
	
	public GetMOneZeroThreeResponse doM103Request(GetMOneZeroThreeRequest request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request,new SoapActionCallback("http://banka/m103"));
	        
	        return (GetMOneZeroThreeResponse) response;

    }
}
