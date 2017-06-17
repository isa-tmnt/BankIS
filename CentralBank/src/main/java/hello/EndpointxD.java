package hello;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeRequest;
import io.spring.guides.gs_producing_web_service.GetMOneZeroThreeResponse;
import io.spring.guides.gs_producing_web_service.GetMT900Request;
import io.spring.guides.gs_producing_web_service.GetMT9X0Response;

@Endpoint
public class EndpointxD {
private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private static int idPoruke;
	
	@PayloadRoot(namespace = "http://banka/m103", localPart = "getMOneZeroThreeRequest")
	@SoapAction("http://banka/m103")
	@ResponsePayload
	public GetMOneZeroThreeResponse getM103(@RequestPayload GetMOneZeroThreeRequest request) {
		
		
		new Thread(new Runnable() {
		     public void run() {
		    	try {
					Thread.sleep(5 * 1000);
					GregorianCalendar c = new GregorianCalendar();
					c.setTime(new Date());
					XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
					
					request.setDatumNaloga(date2);
					request.setDatumValute(date2);
					sendMt103ToRecipient(request);
				//	sendMt900ToRecipient(request); TODO 
				} catch (InterruptedException | DatatypeConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	    	 
		     }
		}).start();

		GetMOneZeroThreeResponse respone = new GetMOneZeroThreeResponse();
		respone.setId(543);
		return respone;
	}
	
	protected void sendMt900ToRecipient(GetMOneZeroThreeRequest request) {
		sendMt900ToRecipient();
	}

	protected void sendMt103ToRecipient(GetMOneZeroThreeRequest request) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
		MT103Client wsclient2 =  context.getBean(MT103Client.class);
	    wsclient2.setDefaultUri("https://localhost:10011/ws");
	    GetMOneZeroThreeResponse response2 = wsclient2.doMT103Request(request);	
	}

	@PayloadRoot(namespace = "http://banka/m102", localPart = "getM102Request")
	@SoapAction("http://banka/m102")
	@ResponsePayload
	public GetM102Response getM102(@RequestPayload GetM102Request request) {
		
		new Thread(new Runnable() {
		     public void run() {
		    	try {
					Thread.sleep(5 * 1000);
					sendMt900ToRecipient();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	    	 
		     }
		}).start();
		
		
		
		GetM102Response response = new GetM102Response();
			response.setId(12312);
		return response;
	}
	
	
	public void sendMt900ToRecipient(){
	
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IAmWsClientConfig.class);
	     MT900Client wsclient2 =  context.getBean(MT900Client.class);
	    wsclient2.setDefaultUri("https://localhost:10011/ws");
	    GetMT900Request request2 = new GetMT900Request();
	    request2.setId(Integer.toString(new Random().nextInt()));
	    GetMT9X0Response response2 = wsclient2.doMT900Request(request2);			
		
	}
	
	
}
