package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ArtistResponseDTO;
import es.metrica.trackticket.services.ArtistProfileServiceImpl;

class ArtistProfileServiceImplTest {

	private ArtistProfileServiceImpl artistProfileService;
	private MockRestServiceServer mockServer;

	@BeforeEach
	void setUp() {

		RestClient.Builder builder = RestClient.builder();

		mockServer = MockRestServiceServer.bindTo(builder).build();

		artistProfileService = new ArtistProfileServiceImpl(builder, "https://api.spotify.com/v1",
				"https://accounts.spotify.com/api/token", "41002883abee4b4f8721d9b0fc13cbe5",
				"935a926a691e4413a9f81b06d46d3b2e");
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid artist name.")
	void getArtistHappyPath() {
		String artist = "Cruz Cafuné";
		String genre = "Rap/HipHop";

		mockServer.expect(MockRestRequestMatchers.requestTo("https://accounts.spotify.com/api/token"))
				.andRespond(MockRestResponseCreators.withSuccess(
						"{\"access_token\":\"b1928beo9wefo9sa8dfg\", \"expires_in\":3600}",
						MediaType.APPLICATION_JSON));

		mockServer
				.expect(MockRestRequestMatchers.requestTo(
						"https://api.spotify.com/v1/search?q=Cruz%20Cafun%C3%A9&type=artist&market=ES&limit=1"))
				.andRespond(MockRestResponseCreators.withSuccess(
						"""
														{
								  "artists": {
								    "href": "https://api.spotify.com/v1/search?offset=0&limit=1&query=Cruz%20Cafun%C3%A9&type=artist&market=ES&locale=es-ES,es;q%3D0.8",
								    "limit": 1,
								    "next": "https://api.spotify.com/v1/search?offset=1&limit=1&query=Cruz%20Cafun%C3%A9&type=artist&market=ES&locale=es-ES,es;q%3D0.8",
								    "offset": 0,
								    "previous": null,
								    "total": 801,
								    "items": [
								      {
								        "external_urls": {
								          "spotify": "https://open.spotify.com/artist/0jeYkqwckGJoHQhhXwgzk3"
								        },
								        "followers": {
								          "href": null,
								          "total": 547294
								        },
								        "genres": [],
								        "href": "https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3",
								        "id": "0jeYkqwckGJoHQhhXwgzk3",
								        "images": [
								          {
								            "url": "https://i.scdn.co/image/ab6761610000e5eb9f52199eefb0ccb6f69afe2c",
								            "height": 640,
								            "width": 640
								          },
								          {
								            "url": "https://i.scdn.co/image/ab676161000051749f52199eefb0ccb6f69afe2c",
								            "height": 320,
								            "width": 320
								          },
								          {
								            "url": "https://i.scdn.co/image/ab6761610000f1789f52199eefb0ccb6f69afe2c",
								            "height": 160,
								            "width": 160
								          }
								        ],
								        "name": "Cruz Cafuné",
								        "popularity": 67,
								        "type": "artist",
								        "uri": "spotify:artist:0jeYkqwckGJoHQhhXwgzk3"
								      }
								    ]
								  }
								}
														""",
						MediaType.APPLICATION_JSON));

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3/albums?market=ES&include_groups=album&limit=10"))
				.andRespond(MockRestResponseCreators.withSuccess(
						"""
												{
								  "href": "https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3/albums?offset=0&limit=10&market=ES&locale=es-ES,es;q%3D0.8&include_groups=album",
								  "limit": 10,
								  "next": null,
								  "offset": 0,
								  "previous": null,
								  "total": 4,
								  "items": [
								    {
								      "album_type": "album",
								      "total_tracks": 8,
								      "external_urls": {
								        "spotify": "https://open.spotify.com/album/4cdd2jx9GScUzDETwJ6GEk"
								      },
								      "href": "https://api.spotify.com/v1/albums/4cdd2jx9GScUzDETwJ6GEk",
								      "id": "4cdd2jx9GScUzDETwJ6GEk",
								      "images": [
								        {
								          "url": "https://i.scdn.co/image/ab67616d0000b273c09df971803bf3ae2dff0682",
								          "height": 640,
								          "width": 640
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d00001e02c09df971803bf3ae2dff0682",
								          "height": 300,
								          "width": 300
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d00004851c09df971803bf3ae2dff0682",
								          "height": 64,
								          "width": 64
								        }
								      ],
								      "name": "blu€s (d€lux€)",
								      "release_date": "2024-10-24",
								      "release_date_precision": "day",
								      "type": "album",
								      "uri": "spotify:album:4cdd2jx9GScUzDETwJ6GEk",
								      "artists": [
								        {
								          "external_urls": {
								            "spotify": "https://open.spotify.com/artist/0jeYkqwckGJoHQhhXwgzk3"
								          },
								          "href": "https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3",
								          "id": "0jeYkqwckGJoHQhhXwgzk3",
								          "name": "Cruz Cafuné",
								          "type": "artist",
								          "uri": "spotify:artist:0jeYkqwckGJoHQhhXwgzk3"
								        }
								      ],
								      "album_group": "album",
								      "is_playable": true
								    },
								    {
								      "album_type": "album",
								      "total_tracks": 23,
								      "external_urls": {
								        "spotify": "https://open.spotify.com/album/7yvmtCjHcBe9DqIVl7AwQT"
								      },
								      "href": "https://api.spotify.com/v1/albums/7yvmtCjHcBe9DqIVl7AwQT",
								      "id": "7yvmtCjHcBe9DqIVl7AwQT",
								      "images": [
								        {
								          "url": "https://i.scdn.co/image/ab67616d0000b2734102f96ba4b1df4dfe8bc35f",
								          "height": 640,
								          "width": 640
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d00001e024102f96ba4b1df4dfe8bc35f",
								          "height": 300,
								          "width": 300
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d000048514102f96ba4b1df4dfe8bc35f",
								          "height": 64,
								          "width": 64
								        }
								      ],
								      "name": "Me Muevo Con Dios",
								      "release_date": "2023-05-25",
								      "release_date_precision": "day",
								      "type": "album",
								      "uri": "spotify:album:7yvmtCjHcBe9DqIVl7AwQT",
								      "artists": [
								        {
								          "external_urls": {
								            "spotify": "https://open.spotify.com/artist/0jeYkqwckGJoHQhhXwgzk3"
								          },
								          "href": "https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3",
								          "id": "0jeYkqwckGJoHQhhXwgzk3",
								          "name": "Cruz Cafuné",
								          "type": "artist",
								          "uri": "spotify:artist:0jeYkqwckGJoHQhhXwgzk3"
								        }
								      ],
								      "album_group": "album",
								      "is_playable": true
								    },
								    {
								      "album_type": "album",
								      "total_tracks": 13,
								      "external_urls": {
								        "spotify": "https://open.spotify.com/album/7z44Enk09spyCdKwxPJ5xG"
								      },
								      "href": "https://api.spotify.com/v1/albums/7z44Enk09spyCdKwxPJ5xG",
								      "id": "7z44Enk09spyCdKwxPJ5xG",
								      "images": [
								        {
								          "url": "https://i.scdn.co/image/ab67616d0000b2736724a9f0667730f6e417fa87",
								          "height": 640,
								          "width": 640
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d00001e026724a9f0667730f6e417fa87",
								          "height": 300,
								          "width": 300
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d000048516724a9f0667730f6e417fa87",
								          "height": 64,
								          "width": 64
								        }
								      ],
								      "name": "Moonlight922",
								      "release_date": "2020-01-10",
								      "release_date_precision": "day",
								      "type": "album",
								      "uri": "spotify:album:7z44Enk09spyCdKwxPJ5xG",
								      "artists": [
								        {
								          "external_urls": {
								            "spotify": "https://open.spotify.com/artist/0jeYkqwckGJoHQhhXwgzk3"
								          },
								          "href": "https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3",
								          "id": "0jeYkqwckGJoHQhhXwgzk3",
								          "name": "Cruz Cafuné",
								          "type": "artist",
								          "uri": "spotify:artist:0jeYkqwckGJoHQhhXwgzk3"
								        }
								      ],
								      "album_group": "album",
								      "is_playable": true
								    },
								    {
								      "album_type": "album",
								      "total_tracks": 15,
								      "external_urls": {
								        "spotify": "https://open.spotify.com/album/7dThD0ZbftpiMw5kChXpC5"
								      },
								      "href": "https://api.spotify.com/v1/albums/7dThD0ZbftpiMw5kChXpC5",
								      "id": "7dThD0ZbftpiMw5kChXpC5",
								      "images": [
								        {
								          "url": "https://i.scdn.co/image/ab67616d0000b27371fa1f493824349117812e60",
								          "height": 640,
								          "width": 640
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d00001e0271fa1f493824349117812e60",
								          "height": 300,
								          "width": 300
								        },
								        {
								          "url": "https://i.scdn.co/image/ab67616d0000485171fa1f493824349117812e60",
								          "height": 64,
								          "width": 64
								        }
								      ],
								      "name": "Maracucho Bueno Muere Chiquito",
								      "release_date": "2018-03-16",
								      "release_date_precision": "day",
								      "type": "album",
								      "uri": "spotify:album:7dThD0ZbftpiMw5kChXpC5",
								      "artists": [
								        {
								          "external_urls": {
								            "spotify": "https://open.spotify.com/artist/0jeYkqwckGJoHQhhXwgzk3"
								          },
								          "href": "https://api.spotify.com/v1/artists/0jeYkqwckGJoHQhhXwgzk3",
								          "id": "0jeYkqwckGJoHQhhXwgzk3",
								          "name": "Cruz Cafuné",
								          "type": "artist",
								          "uri": "spotify:artist:0jeYkqwckGJoHQhhXwgzk3"
								        }
								      ],
								      "album_group": "album",
								      "is_playable": true
								    }
								  ]
								}
												""",
						MediaType.APPLICATION_JSON));

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://api.spotify.com/v1/search?q=This%20is%20Cruz%20Cafun%C3%A9&type=playlist&market=ES&limit=2"))
				.andRespond(MockRestResponseCreators.withSuccess(
						"""
														{
								  "playlists": {
								    "href": "https://api.spotify.com/v1/search?offset=0&limit=2&query=This%20is%20Cruz%20Cafun%C3%A9&type=playlist&market=ES&locale=es-ES,es;q%3D0.8",
								    "limit": 2,
								    "next": "https://api.spotify.com/v1/search?offset=2&limit=2&query=This%20is%20Cruz%20Cafun%C3%A9&type=playlist&market=ES&locale=es-ES,es;q%3D0.8",
								    "offset": 0,
								    "previous": null,
								    "total": 3,
								    "items": [
								      null,
								      {
								        "collaborative": false,
								        "description": "",
								        "external_urls": {
								          "spotify": "https://open.spotify.com/playlist/2RY8A6sFNkxCDIVtGW1ImG"
								        },
								        "href": "https://api.spotify.com/v1/playlists/2RY8A6sFNkxCDIVtGW1ImG",
								        "id": "2RY8A6sFNkxCDIVtGW1ImG",
								        "images": [
								          {
								            "url": "https://mosaic.scdn.co/640/ab67616d00001e021aaada2ba7cfd29031862430ab67616d00001e024102f96ba4b1df4dfe8bc35fab67616d00001e02a815c1e10f3081ad68108ff5ab67616d00001e02eca0def33e1edf19b76d1aef",
								            "height": 640,
								            "width": 640
								          },
								          {
								            "url": "https://mosaic.scdn.co/300/ab67616d00001e021aaada2ba7cfd29031862430ab67616d00001e024102f96ba4b1df4dfe8bc35fab67616d00001e02a815c1e10f3081ad68108ff5ab67616d00001e02eca0def33e1edf19b76d1aef",
								            "height": 300,
								            "width": 300
								          },
								          {
								            "url": "https://mosaic.scdn.co/60/ab67616d00001e021aaada2ba7cfd29031862430ab67616d00001e024102f96ba4b1df4dfe8bc35fab67616d00001e02a815c1e10f3081ad68108ff5ab67616d00001e02eca0def33e1edf19b76d1aef",
								            "height": 60,
								            "width": 60
								          }
								        ],
								        "name": "Best of Cruz Cafuné",
								        "owner": {
								          "external_urls": {
								            "spotify": "https://open.spotify.com/user/1148401953"
								          },
								          "href": "https://api.spotify.com/v1/users/1148401953",
								          "id": "1148401953",
								          "type": "user",
								          "uri": "spotify:user:1148401953",
								          "display_name": "Pedro Poveda Moguer"
								        },
								        "public": true,
								        "snapshot_id": "AAAAqm6pdY0Bz/0oKWE3QP3E5VNGf9kR",
								        "items": {
								          "href": "https://api.spotify.com/v1/playlists/2RY8A6sFNkxCDIVtGW1ImG/items",
								          "total": 85
								        },
								        "tracks": {
								          "href": "https://api.spotify.com/v1/playlists/2RY8A6sFNkxCDIVtGW1ImG/tracks",
								          "total": 85
								        },
								        "type": "playlist",
								        "uri": "spotify:playlist:2RY8A6sFNkxCDIVtGW1ImG",
								        "primary_color": null
								      }
								    ]
								  }
								}
														""",
						MediaType.APPLICATION_JSON));

		ArtistResponseDTO result = artistProfileService.getArtist(artist, genre);

		ArtistResponseDTO resultShouldBe = new ArtistResponseDTO("0jeYkqwckGJoHQhhXwgzk3", "Cruz Cafuné",
				"https://open.spotify.com/playlist/2RY8A6sFNkxCDIVtGW1ImG",
				"https://i.scdn.co/image/ab6761610000e5eb9f52199eefb0ccb6f69afe2c", "Rap/HipHop",
				List.of("blu€s (d€lux€)", "Me Muevo Con Dios", "Moonlight922", "Maracucho Bueno Muere Chiquito"));

		assertEquals(result, resultShouldBe);
		mockServer.verify();
	}
	
	
}
