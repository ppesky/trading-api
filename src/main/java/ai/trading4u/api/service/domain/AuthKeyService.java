package ai.trading4u.api.service.domain;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ai.trading4u.api.Base58;
import ai.trading4u.api.web.entity.AuthKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthKeyService {
	
	@Cacheable(cacheNames = "generate_key", key="{#exchangeName, #apiKey, #apiSecret}", unless="#result == null")
	public String getAuthKeyStr(String exchangeName, String apiKey, String apiSecret) {
		log.info("getAuthKeyStr = " + exchangeName +","+ apiKey);
		AuthKey authKey = new AuthKey(exchangeName, apiKey, apiSecret);
		return generateKey(authKey);
	}

	@Cacheable(cacheNames = "resolve_key", unless="#result == null")
	public AuthKey resolveKey(String authKeyStr) {
		log.info("resolveKey = " + authKeyStr);
		String computeString = decrypt(authKeyStr);
		return AuthKey.getAuthKey(authKeyStr, computeString);
	}

	public String generateKey(AuthKey authKey) {
		log.info(authKey.getExchangeName() + " - " + authKey.getApiKey() + " generate.");
		if(StringUtils.hasText(authKey.getComputeString())) {
			return encrypt(authKey.getComputeString());
		}
		return null;
	}

	private String internalKey = "Tbe_rhJ#nw$9Aahj";
	
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
//			return Base64.encodeBase64String(b);
//			return Hex.encodeHexString(b);
			return Base58.encode(b);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String decrypt(String key, String decryptText) {
		SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
		try {
			Cipher cipher = Cipher.getInstance(algorithmTransformation);
			cipher.init(Cipher.DECRYPT_MODE, skey);
//			byte[] b = cipher.doFinal(Base64.decodeBase64(decryptText));
//			byte[] b = cipher.doFinal(Hex.decodeHex(decryptText));
			byte[] b = cipher.doFinal(Base58.decode(decryptText));
			return new String(b, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
