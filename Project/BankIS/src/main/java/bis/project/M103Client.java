package bis.project;

import org.springframework.context.annotation.Bean;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeRequest;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;


public class M103Client  extends WebServiceGatewaySupport{
	 
	public M103Client(){}
	
	public GetMOneZeroThreeResponse doM103Request(GetMOneZeroThreeRequest request){
	        Object response = getWebServiceTemplate()
	                .marshalSendAndReceive(request);
	        
	        return (GetMOneZeroThreeResponse) response;

    }
}
