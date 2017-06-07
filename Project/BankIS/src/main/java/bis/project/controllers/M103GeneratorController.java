package bis.project.controllers;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bis.project.model.BankOrder;
import bis.project.model.M103;
import bis.project.repositories.BankOrderRepository;
import bis.project.repositories.InterbankTransferRepository;

@RestController
public class M103GeneratorController {
	@Autowired
	private InterbankTransferRepository repo;
	
	@Autowired
	private BankOrderRepository ordersRepo;
	
	@RequestMapping(value = "/api/M103/{id}", 
			method = RequestMethod.GET)
	public String getM103(@PathVariable("id") Integer interbankTransferId) {
	
		BankOrder order = ordersRepo.findOne(interbankTransferId);
		M103 m103 = new M103();
		m103.setDebtorBankSwift("sdfa");
		if(order != null){
			 String xml = jaxbObjectToXML(m103);
			 return xml;
		}
		
		return null;
	}
	
	private static String jaxbObjectToXML(M103 order) {
	    String xmlString = "";
	    try {
	        JAXBContext context = JAXBContext.newInstance(M103.class);
	        Marshaller m = context.createMarshaller();

	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

	        StringWriter sw = new StringWriter();
	        m.marshal(order, sw);
	        xmlString = sw.toString();

	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }

	    return xmlString;
	}
}
