package es.metrica.trackticket.services;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.dto.FavouriteArtistRequestDTO;
import es.metrica.trackticket.dto.TokenRequestDTO;

@Service
public class FavouriteArtistServiceImpl implements FavouriteArtistService {
	
	private RestClient restClientArtistSearch;
	private RestClient restClientToken;
	private String token;
	private LocalDateTime tokenExpiration;
	private String clientId;
	private String clientSecret;
	
	public FavouriteArtistServiceImpl(RestClient.Builder restClientBuilder, @Value("${spotify.api.url}") String apiUrl,
			@Value("${spotify.token.url}") String tokenUrl, @Value("${spotify.client.id}") String clientId,
			@Value("${spotify.client.secret}") String clientSecret) {
		this.restClientArtistSearch = restClientBuilder.clone().baseUrl(apiUrl).build();
		this.restClientToken = restClientBuilder.clone().baseUrl(tokenUrl).build();
		this.tokenExpiration = LocalDateTime.now().minusSeconds(1);
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.token = "";
	}

	private String getToken() {

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
	
	@Override
	public List<ArtistResponseDTO> getFavouriteArtists(TokenRequestDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFavouriteArtist(FavouriteArtistRequestDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFavouriteArtist(FavouriteArtistRequestDTO dto) {
		// TODO Auto-generated method stub
		
	}
	
	private record SpotifyTokenResponse(String access_token, int expires_in) {}

}
