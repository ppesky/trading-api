package ai.trading4u.api.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "apiSecret")
public class AuthKey {
	
	public AuthKey() {}
	
	public AuthKey(String exchangeName, String apiKey, String apiSecret) {
		this.exchangeName = exchangeName;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}

	public AuthKey(String exchangeName, String apiKey, String apiSecret, String authKeyStr) {
		this.exchangeName = exchangeName;
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.authKeyStr = authKeyStr;
	}
	
	String exchangeName;

	String apiKey;

	String apiSecret;

	@JsonProperty("auth_key")
	String authKeyStr;
	
	@JsonIgnore
	public String getComputeString() {
		if(exchangeName == null || apiKey == null || apiSecret == null) {
			return null;
		}
		return exchangeName + ";" + apiKey + ";" + apiSecret;
	}
	
	public static AuthKey getAuthKey(String authKeyStr, String computeString) {
		String [] arr = computeString.split(";", 3);
		return new AuthKey(arr [0], arr [1], arr [2], authKeyStr);
	}

}
