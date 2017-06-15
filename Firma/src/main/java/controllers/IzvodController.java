package controllers;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.SoapClientConfig;

import client.IzvodRacunaClient;
import io.spring.guides.gs_producing_web_service.IzvodRequest;
import io.spring.guides.gs_producing_web_service.IzvodResponse;

@Controller
public class IzvodController {

	 @PostMapping("/izvodRequest")
    public String greetingSubmit() {
		 
			AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(SoapClientConfig.class);
		    IzvodRacunaClient wsclient2 =  context2.getBean(IzvodRacunaClient.class);
		    wsclient2.setDefaultUri("http://localhost:10011/ws");
	  
	    	IzvodRequest req = new IzvodRequest();
	    	req.setBrojRacuna("asdfs");
	    	IzvodResponse resp2 = wsclient2.getBeer(req);
		 return "ok";
	 }
	   
}
