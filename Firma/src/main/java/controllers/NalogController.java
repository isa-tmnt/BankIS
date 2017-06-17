package controllers;

import java.math.BigDecimal;
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
import io.spring.guides.gs_producing_web_service.ImportNalogZaPlacanjeResponse;
import io.spring.guides.gs_producing_web_service.NalogZaPlacanje;


@Controller
public class NalogController {

	

    @PostMapping("/nalogRequest")
    public String greetingSubmit(@ModelAttribute NalogIzForme greeting) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SoapClientConfig.class);
	    BankOrderClient wsclient = /*new BankOrderClient();*/ context.getBean(BankOrderClient.class);
	    wsclient.setDefaultUri("https://localhost:10011/ws");
	    NalogZaPlacanje request = new NalogZaPlacanje();
	    
	    request.setIznos(new BigDecimal(greeting.getIznos()));
	    Date date = new Date();
	    try {
	    	XMLGregorianCalendar now = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
			request.setDatumNaloga(now);
			request.setDatumValute(now);
	    } catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    request.setDuznikNalogodavac(greeting.getDuznikNalogodavac());
	    request.setRacunDuznika(greeting.getRacunDuznika());
	    request.setModelZaduzenja(new BigDecimal(greeting.getModelZaduzenja()));
	    request.setPozivNaBrojZaduzenja(greeting.getPozivNaBrojZaduzenja());
	    request.setSvrhaPlacanja(greeting.getSvrhaPlacanja());
	    request.setPrimalacPoverilac(greeting.getPrimalacPoverilac());
	    request.setRacunPoverioca(greeting.getRacunPoverioca());
	    request.setModelOdobrenja(new BigDecimal(greeting.getModelOdobrenja()));
	    request.setPozivNaBrojOdobrenja(greeting.getPozivNaBrojOdobrenja());
	    request.setHitno(greeting.isHitno());
	    
	    
     	ImportNalogZaPlacanjeResponse resp = wsclient.getBeer(request);
	    	System.out.println(resp.getCreatedBankOrderId());
	    	
	    return "ok";
	
    }
    
    

}