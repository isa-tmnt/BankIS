package bis.project.ws;

import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.KeyStoreCallbackHandler;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import org.springframework.ws.transport.http.HttpsUrlConnectionMessageSender;


@EnableWs
@Configuration
public class IAmWsClientConfig{

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        
        //-------------------------------------- if i am client ----------------------------------
        
        // set security actions
        securityInterceptor.setSecurementActions("Timestamp Signature Encrypt");

        // sign the request
        securityInterceptor.setSecurementUsername("server");
        securityInterceptor.setSecurementPassword("123456");
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

        // encrypt the request
        securityInterceptor.setSecurementEncryptionUser("client-public");
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementEncryptionParts("{}{http://schemas.xmlsoap.org/soap/envelope/}Body");
        securityInterceptor.setSecurementSignatureKeyIdentifier("DirectReference");

        //try------ IT WORKS, now response doesnt throw null pointer
        securityInterceptor.setValidationActions("Signature Encrypt");      
        securityInterceptor.setValidationSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationDecryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
       
        //end try-----
      
        
        return securityInterceptor;
    }
    
    @Bean //try, remove if not working-- IT WORKS, now response doesnt throw null pointer
    public KeyStoreCallbackHandler securityCallbackHandler(){
        KeyStoreCallbackHandler callbackHandler = new KeyStoreCallbackHandler();
        callbackHandler.setPrivateKeyPassword("123456");
        return callbackHandler;
    }

    //------------------------------------------ if i am client beans ----------------------------
    
    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword("123456");
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("server.jks"));
        return cryptoFactoryBean;
    }

    @Bean
    public Jaxb2Marshaller getMarshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("io.spring.guides.gs_producing_web_service");
        return marshaller;
    }

   /* @Bean
    public M103Client getM103Client() throws Exception {
		M103Client beerClient = new M103Client();
        beerClient.setMarshaller(getMarshaller());
        beerClient.setUnmarshaller(getMarshaller());
        beerClient.setDefaultUri("http://localhost:7779/ws");
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        beerClient.setInterceptors(interceptors);
        return beerClient;
    }*/
    
 /*   @Bean
    public M102Client getM102Client() throws Exception {
		M102Client beerClient = new M102Client();
        beerClient.setMarshaller(getMarshaller());
        beerClient.setUnmarshaller(getMarshaller());
        beerClient.setDefaultUri("http://localhost:7779/ws");
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        beerClient.setInterceptors(interceptors);
        return beerClient;
    }*/
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IAmWsClientConfig.class);

    private String url = "https://localhost:7779/ws";

    private Resource keyStore = new ClassPathResource("server.jks");

    private String keyStorePassword = "123456";

    private Resource trustStore = new ClassPathResource("server.jks");
    
    private String trustStorePassword = "123456";
    
    @Bean
    public M103Client mt103(Jaxb2Marshaller marshaller) throws Exception {
    	M103Client client = new M103Client();
        client.setDefaultUri(this.url);
        client.setMarshaller(getMarshaller());//marshaller);
        client.setUnmarshaller(getMarshaller());

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());

        LOGGER.info("Loaded keystore: " + keyStore.getURI().toString());
        try {
            keyStore.getInputStream().close();
        } catch (IOException e) {
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(ks, keyStorePassword.toCharArray());

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(trustStore.getInputStream(), trustStorePassword.toCharArray());
        LOGGER.info("Loaded trustStore: " + trustStore.getURI().toString());
        try {
            trustStore.getInputStream().close();
        } catch(IOException e) {
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(ts);

        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

        // otherwise: java.security.cert.CertificateException: No name matching localhost found
        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if (hostname.equals("localhost")) {
                return true;
            }
            return false;
        });
        
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        client.setInterceptors(interceptors);

        client.setMessageSender(messageSender);
        return client;
    }
    
    @Bean
    public M102Client mt102(Jaxb2Marshaller marshaller) throws Exception {
    	M102Client client = new M102Client();
        client.setDefaultUri(this.url);
        client.setMarshaller(getMarshaller());//marshaller);
        client.setUnmarshaller(getMarshaller());

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());

        LOGGER.info("Loaded keystore: " + keyStore.getURI().toString());
        try {
            keyStore.getInputStream().close();
        } catch (IOException e) {
        }
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(ks, keyStorePassword.toCharArray());

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(trustStore.getInputStream(), trustStorePassword.toCharArray());
        LOGGER.info("Loaded trustStore: " + trustStore.getURI().toString());
        try {
            trustStore.getInputStream().close();
        } catch(IOException e) {
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(ts);

        HttpsUrlConnectionMessageSender messageSender = new HttpsUrlConnectionMessageSender();
        messageSender.setKeyManagers(keyManagerFactory.getKeyManagers());
        messageSender.setTrustManagers(trustManagerFactory.getTrustManagers());

        // otherwise: java.security.cert.CertificateException: No name matching localhost found
        messageSender.setHostnameVerifier((hostname, sslSession) -> {
            if (hostname.equals("localhost")) {
                return true;
            }
            return false;
        });
        
        ClientInterceptor[] interceptors = new ClientInterceptor[]{securityInterceptor()};
        client.setInterceptors(interceptors);

        client.setMessageSender(messageSender);
        return client;
    }
    
    

}