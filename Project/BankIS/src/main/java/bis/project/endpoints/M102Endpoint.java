package bis.project.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import bis.project.model.BankOrder;
import bis.project.repositories.BankOrderRepository;
import io.spring.guides.gs_producing_web_service.GetM102Request;
import io.spring.guides.gs_producing_web_service.GetM102Response;
import io.spring.guides.gs_producing_web_service.GetM103Response;

@Endpoint
public class M102Endpoint {
private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

	private static int idPoruke;
	
	@Autowired
	private BankOrderRepository orderRepo;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getM102Request")
	@ResponsePayload
	public GetM102Response getM102(@RequestPayload GetM102Request request) {
		
		int id = request.getId();
		
		BankOrder order = orderRepo.findOne(id);
		
		if(order != null){
			GetM102Response response = new GetM102Response();
			
			response.setId(idPoruke++);
			response.setSwiftBankeDuznika("111");
			response.setObracunskiRacunBankeDuznika("sdfasd");
			response.setSwiftBankePoverioca("asfasdf");
			response.setObracunskiRacunBankePoverioca("sadfsd");

			response.setUkupanIznos(21412);
			response.setSifraValute("yyy");
			
			return response;
		}
		
	
		return null;
	}
}