package es.metrica.trackticket.services;

import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
public class SpotifyTokenServiceImpl implements SpotifyTokenService{
	
	private RestClient restClientToken;
	private LocalDateTime tokenExpiration;
	private String clientId;
	private String clientSecret;
	private String token;
	
	
	public SpotifyTokenServiceImpl(RestClient.Builder restClientBuilder,
			@Value("${spotify.token.url}") String tokenUrl, @Value("${spotify.client.id}") String clientId,
			@Value("${spotify.client.secret}") String clientSecret) {
		this.restClientToken = restClientBuilder.clone().baseUrl(tokenUrl).build();
		this.tokenExpiration = LocalDateTime.now().minusSeconds(1);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.token = "";
	}
	
	@Override
	public String getToken() {

		if (LocalDateTime.now().isAfter(this.tokenExpiration)) {
			String credentials = this.clientId + ":" + this.clientSecret;
			credentials = Base64.getEncoder().encodeToString(credentials.getBytes());

			SpotifyTokenResponse response = restClientToken.post().contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.header("Authorization", "Basic " + credentials).body("grant_type=client_credentials").retrieve()
					.body(SpotifyTokenResponse.class);

			this.token = response.access_token();
			this.tokenExpiration = LocalDateTime.now().plusSeconds(response.expires_in());
		}

		return this.token;
	}
	
	private record SpotifyTokenResponse(String access_token, int expires_in) {
	}

}
