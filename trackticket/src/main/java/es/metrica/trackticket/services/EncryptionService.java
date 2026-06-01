package es.metrica.trackticket.services;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	private final KeyPair keyPair;

	public EncryptionService(KeyPair keyPair) {
		this.keyPair = keyPair;
		System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
	}

	public String decrypt(String encryptedData) {

		try {

			Cipher cipher = Cipher.getInstance("RSA");

			cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
		} catch (Exception e) {
			throw new RuntimeException("Error al descodificar");
		}
	}
}
