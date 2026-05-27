package es.metrica.trackticket.services;

import java.security.KeyPair;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	private final KeyPair keyPair;
	
	public EncryptionService(KeyPair keyPair) {
		this.keyPair = keyPair;
	}
	
	public String decrypt(String encryptedData) {
		
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			
			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));	
			
		} catch (Exception e) {
		    throw new RuntimeException("Error al descifrar", e);
		}
			
	}
}
