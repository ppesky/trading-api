package ai.withtrade.api.web.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthKey {
	
	String apiKey;
	
	String apiSecret;
	
	public String getComputeString() {
		return apiKey + "|" + apiSecret;
	}
	
	public static AuthKey getAuthKey(String computeString) {
		String [] arr = computeString.split("|", 2);
		return new AuthKey(arr [0], arr [1]);
	}

}
