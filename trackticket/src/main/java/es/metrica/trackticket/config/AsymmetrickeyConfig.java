package es.metrica.trackticket.config;

import java.security.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AsymmetrickeyConfig {

	@Bean
	public KeyPair KeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		
		return generator.generateKeyPair();
	}
}
