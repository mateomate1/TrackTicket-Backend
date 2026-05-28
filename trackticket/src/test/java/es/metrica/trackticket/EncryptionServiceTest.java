package es.metrica.trackticket;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import javax.crypto.Cipher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import es.metrica.trackticket.services.EncryptionService;

@ExtendWith(MockitoExtension.class)
public class EncryptionServiceTest {

    private EncryptionService encryptionService;
    private KeyPair keyPair;

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        keyPair = generator.generateKeyPair();
        encryptionService = new EncryptionService(keyPair);
    }

    @Test
    void decrypt_OK() throws Exception {
        String original = "hola";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        String cifrado = Base64.getEncoder().encodeToString(cipher.doFinal(original.getBytes()));

        String resultado = encryptionService.decrypt(cifrado);

        assertEquals("hola", resultado);
    }

    @Test
    void decrypt_datosNoCifrados_lanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> encryptionService.decrypt("estoNoEstaCifrado"));
    }

    @Test
    void decrypt_stringVacio_lanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> encryptionService.decrypt(""));
    }

    @Test
    void decrypt_null_lanzaExcepcion() {
        assertThrows(RuntimeException.class, () -> encryptionService.decrypt(null));
    }
}