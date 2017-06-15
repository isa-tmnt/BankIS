package bis.project;

import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.SmartEndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

import bis.project.endpoints.EndpointxD;

public class IAmClientInterceptor extends Wss4jSecurityInterceptor implements SmartEndpointInterceptor {
	
	
	public IAmClientInterceptor() {
		super();
	}
	
    @Override
    public boolean shouldIntercept(MessageContext messageContext, Object endpoint) {
        if (endpoint instanceof MethodEndpoint) {
            MethodEndpoint methodEndpoint = (MethodEndpoint)endpoint;
            boolean isSame = methodEndpoint.getMethod().getDeclaringClass() == EndpointxD.class; 
            return isSame;
        }
        return false;
    }
}