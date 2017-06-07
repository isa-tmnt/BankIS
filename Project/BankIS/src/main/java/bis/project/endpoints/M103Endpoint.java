package bis.project.endpoints;


import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import bis.project.model.BankOrder;
import bis.project.repositories.BankOrderRepository;
import io.spring.guides.gs_producing_web_service.GetM103Request;
import io.spring.guides.gs_producing_web_service.GetM103Response;

@Endpoint
public class M103Endpoint {
private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private static int idPoruke;
	
	@Autowired
	private BankOrderRepository orderRepo;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getM103Request")
	@ResponsePayload
	public GetM103Response getM103(@RequestPayload GetM103Request request) {
		
		int id = request.getId();
		
		BankOrder order = orderRepo.findOne(id);
		
		if(order != null){
			GetM103Response response = new GetM103Response();
			
			response.setId(idPoruke++);
			response.setSwiftBankeDuznika("asdfdga");
			response.setObracunskiRacunBankeDuznika("sdfasd");
			response.setSwiftBankePoverioca("asfasdf");
			response.setObracunskiRacunBankePoverioca("sadfsd");
			response.setDuznikNalogovdavac("sadfsadf");
			response.setSvrhaPlacanja("asdfhdf");
			response.setPrimalacPoverilac("gasd");
			response.setDatumNaloga(null);
			response.setDatumValute(null);
			response.setRacunDuznika("hfds");
			response.setModelZaduzenja(15);
			response.setPozivNaBrojZaduzenja("sadfsd");
			response.setRacunPoverioca("asdf");
			response.setModelOdobrenja("sadf");
			response.setPozivNaBrojOdobrenja("fdhgffg");
			response.setIznos(21412);
			response.setSifraValute("yyy");
			
			return response;
		}
		
	
		return null;
	}
}
