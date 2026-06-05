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

import es.metrica.trackticket.exception.EncryptationFailureException;

@Service
public class EncryptionService {

	private final KeyPair keyPair;

	public EncryptionService(KeyPair keyPair) {
		this.keyPair = keyPair;
		System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
	}

	public String decrypt(String encryptedData) {

			Cipher cipher;
			try {
				cipher = Cipher.getInstance("RSA");
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				throw new EncryptationFailureException(e.getMessage());
			} 

			try {
				cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			} catch (InvalidKeyException e) {
				throw new EncryptationFailureException("Los datos son inválidos o están corruptos.");
			}

			try {
				return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
			} catch (IllegalBlockSizeException e) {
				throw new EncryptationFailureException("Los datos son inválidos o están corruptos.");
			} catch (BadPaddingException e) {
				throw new EncryptationFailureException("Los datos son inválidos o están corruptos.");
			}
	}
}
