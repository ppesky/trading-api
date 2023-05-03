package ai.withtrade.api;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import ai.withtrade.api.web.entity.AuthKey;

@Component
public class AuthKeyUtils {
	
	private String internalKey = "Tbe_rhJ#nw$9Aahj";
	
	public String generateKey(AuthKey authKey) {
		return encrypt(authKey.getComputeString());
	}
	
	public AuthKey resolveKey(String authKeyStr) {
		String computeString = decrypt(authKeyStr);
		return AuthKey.getAuthKey(computeString);
	}

	public String encrypt(String plainText) {
		return encrypt(internalKey, plainText);
	}
	
	public String decrypt(String decryptText) {
		return decrypt(internalKey, decryptText);
	}
	
	private static final String algorithm = "AES";
	private static final String algorithmTransformation = "AES/ECB/PKCS5Padding";

	/**
	 * 128bit : s.encrypt("4belrhjgnw59hahj", "4111111111111111") => "nGETvGR8Zcvp54NOYGatxn6c9Fw8QuWCrd4PKXVveow="
	 * 256bit : s.encrypt("4belrhjgnw59hahj4belrhjgnw59hahj", "4111111111111111") => "6nWUdNcq/O+tgAwHwCu9mKYKoL3ASsweVZITlNnjKWM="
	 * @param key
	 * @param plainText
	 * @return
	 */
	public String encrypt(String key, String plainText) {
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
		try {
			Cipher cipher = Cipher.getInstance(algorithmTransformation);
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			byte[] b = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
			return Base64.encodeBase64String(b);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String decrypt(String key, String decryptText) {
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
		try {
			Cipher cipher = Cipher.getInstance(algorithmTransformation);
			cipher.init(Cipher.DECRYPT_MODE, skey);
			byte[] b = cipher.doFinal(Base64.decodeBase64(decryptText));
			return new String(b, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
