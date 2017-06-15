package controllers;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.SoapClientConfig;

import client.BankOrderClient;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeRequest;
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;


@Controller
public class NalogController {

	

    @PostMapping("/nalogRequest")
    public String greetingSubmit(@ModelAttribute NalogIzForme greeting) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
	    BankOrderClient wsclient = /*new BankOrderClient();*/ context.getBean(BankOrderClient.class);
	    wsclient.setDefaultUri("http://localhost:10011/ws");
	    ImportNalogZaPlacanjeRequest request = new ImportNalogZaPlacanjeRequest();
	    
	    request.setAmount(greeting.getIznos());
	    Date date = new Date();
	    try {
	    	XMLGregorianCalendar now = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
			request.setBankOrderDate(now);
			request.setCurrensyDate(now);
			request.setOrderDate(now);
	    } catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    request.setDebtor(greeting.getDuznikNalogodavac());
	    request.setDirection("A");
	    request.setFirstAccount(greeting.getRacunDuznika());
	    request.setFirstModel(greeting.getModelZaduzenja());
	    request.setFirstNumber(greeting.getPozivNaBrojZaduzenja());
	    request.setPurposeOfPayment(greeting.getSvrhaPlacanja());
	    request.setRecipient(greeting.getPrimalacPoverilac());
	    request.setSecondAccount(greeting.getRacunPoverioca());
	    request.setSecondModel(greeting.getModelOdobrenja());
	    request.setSecondNumber(greeting.getPozivNaBrojOdobrenja());
	    request.setUrgently(greeting.isHitno());
	    
	    
     	ImportNalogZaPlacanjeResponse resp = wsclient.getBeer(request);
	    	System.out.println(resp.getCreatedBankOrderId());
	    	
	    return "ok";
	
    }
    
    

}