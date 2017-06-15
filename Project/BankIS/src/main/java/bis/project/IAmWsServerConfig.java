package bis.project;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.MethodEndpoint;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.KeyStoreCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class IAmWsServerConfig extends WsConfigurerAdapter {
	
	 
	@Bean
    public KeyStoreCallbackHandler securityCallbackHandler(){
        KeyStoreCallbackHandler callbackHandler = new KeyStoreCallbackHandler();
        callbackHandler.setPrivateKeyPassword("123456");
        return callbackHandler;
    }

   //@Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // validate incoming request
        securityInterceptor.setValidationActions("Timestamp Signature");      
        securityInterceptor.setValidationSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationDecryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());

        // encrypt the response
        securityInterceptor.setSecurementEncryptionUser("client-public");
    //    securityInterceptor.setSecurementEncryptionParts("{Content}{http://spring.io/guides/gs-producing-web-service}importNalogZaPlacanjeResponse");
    //    securityInterceptor.setSecurementEncryptionParts("{Content}{http://spring.io/guides/gs-producing-web-service}getMOneZeroThreeRequest");
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());

        // sign the response
        securityInterceptor.setSecurementActions("Signature");
        securityInterceptor.setSecurementUsername("server");
        securityInterceptor.setSecurementPassword("123456");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

     //   securityInterceptor.setValidateResponse(false);
     //   securityInterceptor.setValidateRequest(false);
        
        return securityInterceptor;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("123456");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("server.jks"));
        return cryptoFactoryBean;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        try {
            interceptors.add(securityInterceptor());
        } catch (Exception e) {
            throw new RuntimeException("could not initialize security interceptor");
        }
    }
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
	
	@Bean(name = "m103")
	public DefaultWsdl11Definition sfdsdf(XsdSchema bankSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("ShemaPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(bankSchema);
		return wsdl11Definition;
	}
	
	@Bean
	public XsdSchema bankSchema() {
		return new SimpleXsdSchema(new ClassPathResource("bankShema.xsd"));
	}
	
	 @Bean
	    public Jaxb2Marshaller getMarshaller(){
	        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	        marshaller.setContextPath("io.spring.guides.gs_producing_web_service");
	        return marshaller;
	    }
	 
	@Bean
    public M103Client getM103Client() throws Exception {
		M103Client beerClient = new M103Client();
        beerClient.setMarshaller(getMarshaller());
        beerClient.setUnmarshaller(getMarshaller());
        beerClient.setDefaultUri("http://localhost:7779/ws");
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        beerClient.setInterceptors(interceptors);
        return beerClient;
    }
	
}

