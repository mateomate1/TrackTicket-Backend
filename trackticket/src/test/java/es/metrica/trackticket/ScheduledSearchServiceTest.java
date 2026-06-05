package es.metrica.trackticket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.models.Address;
import es.metrica.trackticket.models.Artist;
import es.metrica.trackticket.models.City;
import es.metrica.trackticket.models.Concert;
import es.metrica.trackticket.models.Country;
import es.metrica.trackticket.models.Location;
import es.metrica.trackticket.models.Notification;
import es.metrica.trackticket.models.State;
import es.metrica.trackticket.models.User;
import es.metrica.trackticket.models.Venue;
import es.metrica.trackticket.repositories.ArtistRepository;
import es.metrica.trackticket.repositories.ConcertRepository;
import es.metrica.trackticket.repositories.NotificationRepository;
import es.metrica.trackticket.repositories.UserRepository;
import es.metrica.trackticket.services.ScheduledSearchServiceImpl;

@ExtendWith(MockitoExtension.class)
class ScheduledSearchServiceTest {

	private ScheduledSearchServiceImpl scheduledSearchService;
	private MockRestServiceServer mockServer;
	@Mock
	private ConcertRepository concertRepository;
	@Mock
	private ArtistRepository artistRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private NotificationRepository notificationRepository;
	@Mock
	private User user;

	@BeforeEach
	void setUp() {
		RestClient.Builder builder = RestClient.builder();

		mockServer = MockRestServiceServer.bindTo(builder).build();

		scheduledSearchService = new ScheduledSearchServiceImpl(builder, "https://app.ticketmaster.com/discovery/v2",
				"uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9", concertRepository, artistRepository, userRepository,
				notificationRepository);
	}

	@Test
	@DisplayName("Tests if deletePastConcert calls the concert repository")
	void deletePastConcert() {
		scheduledSearchService.deletePastConcerts();
		verify(concertRepository, times(1)).deleteByConcertDateBefore(any(LocalDate.class));
	}

	@Test
	@DisplayName("Tests if searchForFavouriteConcerts doesn't do anything when there are no changes in the concert")
	void searchForFavouriteConcertsNoChanges() {

		Concert concert = new Concert("Z698xZ2qZ16v-3a6x6", "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				LocalDate.of(2026, 06, 06),
				"https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				new Venue("Estadio Riyadh Air Metropolitano", new Location(40.43624, -3.59947),
						new Address("Av. de Luis Aragonés, 4", "", "28022",
								new City("Madrid", new State("Madrid", new Country("España"))))));

		concert.setIdConcert(1L);

		concert.setArtists(List.of(new Artist("idBadBunny", "Bad Bunny")));

		when(concertRepository.findAll()).thenReturn(List.of(concert));
		when(userRepository.findByFavouriteConcertsContains(concert)).thenReturn(List.of(user));

		String result = """
				{
				    "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				    "type": "event",
				    "id": "Z698xZ2qZ16v-3a6x6",
				    "test": false,
				    "url": "https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				    "locale": "es-es",
				    "images": [
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				            "width": 2048,
				            "height": 1152,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_16_9.jpg",
				            "width": 640,
				            "height": 360,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_3_2.jpg",
				            "width": 1024,
				            "height": 683,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_3_2.jpg",
				            "width": 640,
				            "height": 427,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RECOMENDATION_16_9.jpg",
				            "width": 100,
				            "height": 56,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_ARTIST_PAGE_3_2.jpg",
				            "width": 305,
				            "height": 203,
				            "fallback": false
				        },
				        {
				            "ratio": "4_3",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_CUSTOM.jpg",
				            "width": 305,
				            "height": 225,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_EVENT_DETAIL_PAGE_16_9.jpg",
				            "width": 205,
				            "height": 115,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_16_9.jpg",
				            "width": 1024,
				            "height": 576,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_LANDSCAPE_16_9.jpg",
				            "width": 1136,
				            "height": 639,
				            "fallback": false
				        }
				    ],
				    "sales": {
				        "public": {
				            "startDateTime": "2025-05-09T13:00:00Z",
				            "startTBD": false,
				            "startTBA": false,
				            "endDateTime": "2026-06-06T18:15:00Z"
				        }
				    },
				    "dates": {
				        "start": {
				            "localDate": "2026-06-06",
				            "localTime": "20:00:00",
				            "dateTime": "2026-06-06T18:00:00Z",
				            "dateTBD": false,
				            "dateTBA": false,
				            "timeTBA": false,
				            "noSpecificTime": false
				        },
				        "timezone": "Europe/Madrid",
				        "status": {
				            "code": "onsale"
				        },
				        "spanMultipleDays": false
				    },
				    "classifications": [
				        {
				            "primary": true,
				            "segment": {
				                "id": "KZFzniwnSyZfZ7v7nJ",
				                "name": "Música"
				            },
				            "genre": {
				                "id": "KnvZfZ7vAJ6",
				                "name": "Latin"
				            },
				            "subGenre": {
				                "id": "KZazBEonSMnZfZ7va1a",
				                "name": "Latin"
				            },
				            "family": false
				        }
				    ],
				    "promoter": {
				        "id": "6359",
				        "name": "Giras Latinas Forever, SL"
				    },
				    "promoters": [
				        {
				            "id": "6359",
				            "name": "Giras Latinas Forever, SL"
				        }
				    ],
				    "seatmap": {
				        "staticUrl": "https://media.ticketmaster.eu/spain/45324716aed82986d206b9e5d7f3db56.png"
				    },
				    "ticketing": {
				        "safeTix": {
				            "enabled": false
				        }
				    },
				    "nameOrigin": "custom",
				    "_links": {
				        "self": {
				            "href": "/discovery/v2/events/Z698xZ2qZ16v-3a6x6?locale=es-es"
				        },
				        "attractions": [
				            {
				                "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				            }
				        ],
				        "venues": [
				            {
				                "href": "/discovery/v2/venues/Z598xZ2qZ6Fe1?locale=es-es"
				            }
				        ]
				    },
				    "_embedded": {
				        "venues": [
				            {
				                "name": "Estadio Riyadh Air Metropolitano",
				                "type": "venue",
				                "id": "Z598xZ2qZ6Fe1",
				                "test": false,
				                "url": "https://www.ticketmaster.es/venue/estadio-riyadh-air-metropolitano-madrid-entradas/civitasmad/112",
				                "locale": "es-es",
				                "postalCode": "28022",
				                "timezone": "Europe/Madrid",
				                "city": {
				                    "name": "Madrid"
				                },
				                "state": {
				                    "name": "Madrid"
				                },
				                "country": {
				                    "name": "España",
				                    "countryCode": "ES"
				                },
				                "address": {
				                    "line1": "Av. de Luis Aragonés, 4"
				                },
				                "location": {
				                    "longitude": "-3.59947",
				                    "latitude": "40.43624"
				                },
				                "upcomingEvents": {
				                    "mfx-es": 37,
				                    "tmr": 9,
				                    "_total": 46,
				                    "_filtered": 0
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/venues/Z598xZ2qZ6Fe1?locale=es-es"
				                    }
				                }
				            }
				        ],
				        "attractions": [
				            {
				                "name": "Bad Bunny",
				                "type": "attraction",
				                "id": "K8vZ9174l1f",
				                "test": false,
				                "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                "locale": "es-br",
				                "externalLinks": {
				                    "youtube": [
				                        {
				                            "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                        }
				                    ],
				                    "twitter": [
				                        {
				                            "url": "https://twitter.com/sanbenito"
				                        }
				                    ],
				                    "itunes": [
				                        {
				                            "url": "https://itunes.apple.com/us/artist/id1126808565"
				                        }
				                    ],
				                    "lastfm": [
				                        {
				                            "url": "https://www.last.fm/music/Bad+Bunny"
				                        }
				                    ],
				                    "spotify": [
				                        {
				                            "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                        }
				                    ],
				                    "facebook": [
				                        {
				                            "url": "https://www.facebook.com/BadBunnyOfficial"
				                        }
				                    ],
				                    "wiki": [
				                        {
				                            "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                        }
				                    ],
				                    "musicbrainz": [
				                        {
				                            "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                            "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                        }
				                    ],
				                    "instagram": [
				                        {
				                            "url": "https://www.instagram.com/badbunnypr/"
				                        }
				                    ]
				                },
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_SOURCE",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    }
				                ],
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vke1",
				                            "name": "Latin Hip-Hop"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7la",
				                            "name": "Individual"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7vAd7",
				                            "name": "Músico"
				                        },
				                        "family": false
				                    }
				                ],
				                "upcomingEvents": {
				                    "ticketnet": 3,
				                    "mfx-se": 4,
				                    "mfx-be": 2,
				                    "mfx-es": 19,
				                    "tmr": 21,
				                    "mfx-nl": 4,
				                    "ticketmaster": 2,
				                    "mfx-de": 4,
				                    "mfx-it": 2,
				                    "mfx-pl": 3,
				                    "_total": 64,
				                    "_filtered": 0
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                    }
				                }
				            }
				        ]
				    }
				}
																""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events/Z698xZ2qZ16v-3a6x6.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		scheduledSearchService.searchForFavouriteConcerts();

		verify(notificationRepository, never()).save(any());
		verify(concertRepository, never()).save(any());
	}

	@Test
	@DisplayName("Tests if searchForFavouriteConcerts saves concert and notification when there are changes in the concert date")
	void searchForFavouriteConcertsDateChanges() {

		Concert concert = new Concert("Z698xZ2qZ16v-3a6x6", "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				LocalDate.of(2026, 06, 05),
				"https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				new Venue("Estadio Riyadh Air Metropolitano", new Location(40.43624, -3.59947),
						new Address("Av. de Luis Aragonés, 4", "", "28022",
								new City("Madrid", new State("Madrid", new Country("España"))))));

		concert.setArtists(List.of(new Artist("idBadBunny", "Bad Bunny")));

		when(concertRepository.findAll()).thenReturn(List.of(concert));
		when(userRepository.findByFavouriteConcertsContains(concert)).thenReturn(List.of(user));

		String result = """
				{
				    "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				    "type": "event",
				    "id": "Z698xZ2qZ16v-3a6x6",
				    "test": false,
				    "url": "https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				    "locale": "es-es",
				    "images": [
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				            "width": 2048,
				            "height": 1152,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_16_9.jpg",
				            "width": 640,
				            "height": 360,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_3_2.jpg",
				            "width": 1024,
				            "height": 683,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_3_2.jpg",
				            "width": 640,
				            "height": 427,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RECOMENDATION_16_9.jpg",
				            "width": 100,
				            "height": 56,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_ARTIST_PAGE_3_2.jpg",
				            "width": 305,
				            "height": 203,
				            "fallback": false
				        },
				        {
				            "ratio": "4_3",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_CUSTOM.jpg",
				            "width": 305,
				            "height": 225,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_EVENT_DETAIL_PAGE_16_9.jpg",
				            "width": 205,
				            "height": 115,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_16_9.jpg",
				            "width": 1024,
				            "height": 576,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_LANDSCAPE_16_9.jpg",
				            "width": 1136,
				            "height": 639,
				            "fallback": false
				        }
				    ],
				    "sales": {
				        "public": {
				            "startDateTime": "2025-05-09T13:00:00Z",
				            "startTBD": false,
				            "startTBA": false,
				            "endDateTime": "2026-06-06T18:15:00Z"
				        }
				    },
				    "dates": {
				        "start": {
				            "localDate": "2026-06-06",
				            "localTime": "20:00:00",
				            "dateTime": "2026-06-06T18:00:00Z",
				            "dateTBD": false,
				            "dateTBA": false,
				            "timeTBA": false,
				            "noSpecificTime": false
				        },
				        "timezone": "Europe/Madrid",
				        "status": {
				            "code": "onsale"
				        },
				        "spanMultipleDays": false
				    },
				    "classifications": [
				        {
				            "primary": true,
				            "segment": {
				                "id": "KZFzniwnSyZfZ7v7nJ",
				                "name": "Música"
				            },
				            "genre": {
				                "id": "KnvZfZ7vAJ6",
				                "name": "Latin"
				            },
				            "subGenre": {
				                "id": "KZazBEonSMnZfZ7va1a",
				                "name": "Latin"
				            },
				            "family": false
				        }
				    ],
				    "promoter": {
				        "id": "6359",
				        "name": "Giras Latinas Forever, SL"
				    },
				    "promoters": [
				        {
				            "id": "6359",
				            "name": "Giras Latinas Forever, SL"
				        }
				    ],
				    "seatmap": {
				        "staticUrl": "https://media.ticketmaster.eu/spain/45324716aed82986d206b9e5d7f3db56.png"
				    },
				    "ticketing": {
				        "safeTix": {
				            "enabled": false
				        }
				    },
				    "nameOrigin": "custom",
				    "_links": {
				        "self": {
				            "href": "/discovery/v2/events/Z698xZ2qZ16v-3a6x6?locale=es-es"
				        },
				        "attractions": [
				            {
				                "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				            }
				        ],
				        "venues": [
				            {
				                "href": "/discovery/v2/venues/Z598xZ2qZ6Fe1?locale=es-es"
				            }
				        ]
				    },
				    "_embedded": {
				        "venues": [
				            {
				                "name": "Estadio Riyadh Air Metropolitano",
				                "type": "venue",
				                "id": "Z598xZ2qZ6Fe1",
				                "test": false,
				                "url": "https://www.ticketmaster.es/venue/estadio-riyadh-air-metropolitano-madrid-entradas/civitasmad/112",
				                "locale": "es-es",
				                "postalCode": "28022",
				                "timezone": "Europe/Madrid",
				                "city": {
				                    "name": "Madrid"
				                },
				                "state": {
				                    "name": "Madrid"
				                },
				                "country": {
				                    "name": "España",
				                    "countryCode": "ES"
				                },
				                "address": {
				                    "line1": "Av. de Luis Aragonés, 4"
				                },
				                "location": {
				                    "longitude": "-3.59947",
				                    "latitude": "40.43624"
				                },
				                "upcomingEvents": {
				                    "mfx-es": 37,
				                    "tmr": 9,
				                    "_total": 46,
				                    "_filtered": 0
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/venues/Z598xZ2qZ6Fe1?locale=es-es"
				                    }
				                }
				            }
				        ],
				        "attractions": [
				            {
				                "name": "Bad Bunny",
				                "type": "attraction",
				                "id": "K8vZ9174l1f",
				                "test": false,
				                "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                "locale": "es-br",
				                "externalLinks": {
				                    "youtube": [
				                        {
				                            "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                        }
				                    ],
				                    "twitter": [
				                        {
				                            "url": "https://twitter.com/sanbenito"
				                        }
				                    ],
				                    "itunes": [
				                        {
				                            "url": "https://itunes.apple.com/us/artist/id1126808565"
				                        }
				                    ],
				                    "lastfm": [
				                        {
				                            "url": "https://www.last.fm/music/Bad+Bunny"
				                        }
				                    ],
				                    "spotify": [
				                        {
				                            "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                        }
				                    ],
				                    "facebook": [
				                        {
				                            "url": "https://www.facebook.com/BadBunnyOfficial"
				                        }
				                    ],
				                    "wiki": [
				                        {
				                            "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                        }
				                    ],
				                    "musicbrainz": [
				                        {
				                            "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                            "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                        }
				                    ],
				                    "instagram": [
				                        {
				                            "url": "https://www.instagram.com/badbunnypr/"
				                        }
				                    ]
				                },
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_SOURCE",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    }
				                ],
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vke1",
				                            "name": "Latin Hip-Hop"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7la",
				                            "name": "Individual"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7vAd7",
				                            "name": "Músico"
				                        },
				                        "family": false
				                    }
				                ],
				                "upcomingEvents": {
				                    "ticketnet": 3,
				                    "mfx-se": 4,
				                    "mfx-be": 2,
				                    "mfx-es": 19,
				                    "tmr": 21,
				                    "mfx-nl": 4,
				                    "ticketmaster": 2,
				                    "mfx-de": 4,
				                    "mfx-it": 2,
				                    "mfx-pl": 3,
				                    "_total": 64,
				                    "_filtered": 0
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                    }
				                }
				            }
				        ]
				    }
				}
																""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events/Z698xZ2qZ16v-3a6x6.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		scheduledSearchService.searchForFavouriteConcerts();

		verify(notificationRepository, times(1)).save(any(Notification.class));
		verify(concertRepository, times(1)).save(concert);
	}

	@Test
	@DisplayName("Tests if searchForFavouriteConcerts saves concert and notification when there are changes in the concert venue")
	void searchForFavouriteConcertsVenueChanges() {

		Concert concert = new Concert("Z698xZ2qZ16v-3a6x6", "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				LocalDate.of(2026, 06, 06),
				"https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				new Venue("La Riviera", new Location(40.43624, -3.59947), new Address("Av. de Luis Aragonés, 4", "",
						"28022", new City("Madrid", new State("Madrid", new Country("España"))))));

		concert.setArtists(List.of(new Artist("idBadBunny", "Bad Bunny")));

		when(concertRepository.findAll()).thenReturn(List.of(concert));
		when(userRepository.findByFavouriteConcertsContains(concert)).thenReturn(List.of(user));

		String result = """
				{
				    "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				    "type": "event",
				    "id": "Z698xZ2qZ16v-3a6x6",
				    "test": false,
				    "url": "https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				    "locale": "es-es",
				    "images": [
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				            "width": 2048,
				            "height": 1152,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_16_9.jpg",
				            "width": 640,
				            "height": 360,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_3_2.jpg",
				            "width": 1024,
				            "height": 683,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_3_2.jpg",
				            "width": 640,
				            "height": 427,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RECOMENDATION_16_9.jpg",
				            "width": 100,
				            "height": 56,
				            "fallback": false
				        },
				        {
				            "ratio": "3_2",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_ARTIST_PAGE_3_2.jpg",
				            "width": 305,
				            "height": 203,
				            "fallback": false
				        },
				        {
				            "ratio": "4_3",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_CUSTOM.jpg",
				            "width": 305,
				            "height": 225,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_EVENT_DETAIL_PAGE_16_9.jpg",
				            "width": 205,
				            "height": 115,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_16_9.jpg",
				            "width": 1024,
				            "height": 576,
				            "fallback": false
				        },
				        {
				            "ratio": "16_9",
				            "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_LANDSCAPE_16_9.jpg",
				            "width": 1136,
				            "height": 639,
				            "fallback": false
				        }
				    ],
				    "sales": {
				        "public": {
				            "startDateTime": "2025-05-09T13:00:00Z",
				            "startTBD": false,
				            "startTBA": false,
				            "endDateTime": "2026-06-06T18:15:00Z"
				        }
				    },
				    "dates": {
				        "start": {
				            "localDate": "2026-06-06",
				            "localTime": "20:00:00",
				            "dateTime": "2026-06-06T18:00:00Z",
				            "dateTBD": false,
				            "dateTBA": false,
				            "timeTBA": false,
				            "noSpecificTime": false
				        },
				        "timezone": "Europe/Madrid",
				        "status": {
				            "code": "onsale"
				        },
				        "spanMultipleDays": false
				    },
				    "classifications": [
				        {
				            "primary": true,
				            "segment": {
				                "id": "KZFzniwnSyZfZ7v7nJ",
				                "name": "Música"
				            },
				            "genre": {
				                "id": "KnvZfZ7vAJ6",
				                "name": "Latin"
				            },
				            "subGenre": {
				                "id": "KZazBEonSMnZfZ7va1a",
				                "name": "Latin"
				            },
				            "family": false
				        }
				    ],
				    "promoter": {
				        "id": "6359",
				        "name": "Giras Latinas Forever, SL"
				    },
				    "promoters": [
				        {
				            "id": "6359",
				            "name": "Giras Latinas Forever, SL"
				        }
				    ],
				    "seatmap": {
				        "staticUrl": "https://media.ticketmaster.eu/spain/45324716aed82986d206b9e5d7f3db56.png"
				    },
				    "ticketing": {
				        "safeTix": {
				            "enabled": false
				        }
				    },
				    "nameOrigin": "custom",
				    "_links": {
				        "self": {
				            "href": "/discovery/v2/events/Z698xZ2qZ16v-3a6x6?locale=es-es"
				        },
				        "attractions": [
				            {
				                "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				            }
				        ],
				        "venues": [
				            {
				                "href": "/discovery/v2/venues/Z598xZ2qZ6Fe1?locale=es-es"
				            }
				        ]
				    },
				    "_embedded": {
				        "venues": [
				            {
				                "name": "Estadio Riyadh Air Metropolitano",
				                "type": "venue",
				                "id": "Z598xZ2qZ6Fe1",
				                "test": false,
				                "url": "https://www.ticketmaster.es/venue/estadio-riyadh-air-metropolitano-madrid-entradas/civitasmad/112",
				                "locale": "es-es",
				                "postalCode": "28022",
				                "timezone": "Europe/Madrid",
				                "city": {
				                    "name": "Madrid"
				                },
				                "state": {
				                    "name": "Madrid"
				                },
				                "country": {
				                    "name": "España",
				                    "countryCode": "ES"
				                },
				                "address": {
				                    "line1": "Av. de Luis Aragonés, 4"
				                },
				                "location": {
				                    "longitude": "-3.59947",
				                    "latitude": "40.43624"
				                },
				                "upcomingEvents": {
				                    "mfx-es": 37,
				                    "tmr": 9,
				                    "_total": 46,
				                    "_filtered": 0
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/venues/Z598xZ2qZ6Fe1?locale=es-es"
				                    }
				                }
				            }
				        ],
				        "attractions": [
				            {
				                "name": "Bad Bunny",
				                "type": "attraction",
				                "id": "K8vZ9174l1f",
				                "test": false,
				                "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                "locale": "es-br",
				                "externalLinks": {
				                    "youtube": [
				                        {
				                            "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                        }
				                    ],
				                    "twitter": [
				                        {
				                            "url": "https://twitter.com/sanbenito"
				                        }
				                    ],
				                    "itunes": [
				                        {
				                            "url": "https://itunes.apple.com/us/artist/id1126808565"
				                        }
				                    ],
				                    "lastfm": [
				                        {
				                            "url": "https://www.last.fm/music/Bad+Bunny"
				                        }
				                    ],
				                    "spotify": [
				                        {
				                            "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                        }
				                    ],
				                    "facebook": [
				                        {
				                            "url": "https://www.facebook.com/BadBunnyOfficial"
				                        }
				                    ],
				                    "wiki": [
				                        {
				                            "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                        }
				                    ],
				                    "musicbrainz": [
				                        {
				                            "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                            "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                        }
				                    ],
				                    "instagram": [
				                        {
				                            "url": "https://www.instagram.com/badbunnypr/"
				                        }
				                    ]
				                },
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_SOURCE",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ac0/5a0435d2-95f1-491d-911a-598d513e5ac0_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    }
				                ],
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vke1",
				                            "name": "Latin Hip-Hop"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7la",
				                            "name": "Individual"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7vAd7",
				                            "name": "Músico"
				                        },
				                        "family": false
				                    }
				                ],
				                "upcomingEvents": {
				                    "ticketnet": 3,
				                    "mfx-se": 4,
				                    "mfx-be": 2,
				                    "mfx-es": 19,
				                    "tmr": 21,
				                    "mfx-nl": 4,
				                    "ticketmaster": 2,
				                    "mfx-de": 4,
				                    "mfx-it": 2,
				                    "mfx-pl": 3,
				                    "_total": 64,
				                    "_filtered": 0
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                    }
				                }
				            }
				        ]
				    }
				}
																""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events/Z698xZ2qZ16v-3a6x6.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		scheduledSearchService.searchForFavouriteConcerts();

		verify(notificationRepository, times(1)).save(any(Notification.class));
		verify(concertRepository, times(1)).save(concert);
	}

	@Test
	@DisplayName("Tests if searchForFavouriteConcerts deletes concert and saves notification when the concert is cancelled")
	void searchForFavouriteConcertsCancelled() {

		Concert concert = new Concert("Z698xZ2qZ16v-3a6x6", "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				LocalDate.of(2026, 06, 06),
				"https://www.ticketmaster.es/event/bad-bunny-debi-tirar-mas-fotos-world-tour-entradas/1852247887",
				new Venue("La Riviera", new Location(40.43624, -3.59947), new Address("Av. de Luis Aragonés, 4", "",
						"28022", new City("Madrid", new State("Madrid", new Country("España"))))));

		concert.setArtists(List.of(new Artist("idBadBunny", "Bad Bunny")));

		when(concertRepository.findAll()).thenReturn(List.of(concert));
		when(userRepository.findByFavouriteConcertsContains(concert)).thenReturn(List.of(user));

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events/Z698xZ2qZ16v-3a6x6.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es"))
				.andRespond(MockRestResponseCreators.withResourceNotFound());

		scheduledSearchService.searchForFavouriteConcerts();

		verify(notificationRepository, times(1)).save(any(Notification.class));
		verify(concertRepository, times(1)).deleteById(concert.getIdConcert());
		verify(concertRepository, never()).save(any());
	}

	@Test
	@DisplayName("Tests if searchForNewConcerts saves artist but not notification when the artist doesn't have any known concerts")
	void searchForNewConcertsNoKnownConcerts() {

		Artist artist = new Artist("id1", "Bad Bunny");
		artist.setKnownConcerts(new ArrayList<>());

		when(artistRepository.findAll()).thenReturn(List.of(artist));

		String result = """
				{
				    "_embedded": {
				        "events": [
				            {
				                "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				                "type": "event",
				                "id": "17u8vxG61C8qvZL",
				                "test": false,
				                "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-28-06-2026/event/350062A39074101F",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                        "width": 8448,
				                        "height": 6336,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startTBD": false,
				                        "startTBA": false
				                    },
				                    "presales": [
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Priority from O2"
				                        },
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Live Nation Presale"
				                        }
				                    ]
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-28",
				                        "localTime": "16:30:00",
				                        "dateTime": "2026-06-28T15:30:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/London",
				                    "status": {
				                        "code": "onsale"
				                    },
				                    "spanMultipleDays": false
				                },
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vaEI"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7nI",
				                            "name": "Indefinido"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7v7lJ",
				                            "name": "Indefinido"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "4110",
				                    "name": "LIVE NATION MUSIC UK LTD",
				                    "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                },
				                "promoters": [
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "3749",
				                        "name": "LNM - VIP NATION UK",
				                        "description": "LNM - VIP NATION UK / NTL / GBR"
				                    }
				                ],
				                "info": "4:30pm Doors 6:00pm CHUWI 7:00pm Bad Bunny 10:00pm Curfew A portion of the ticket proceeds will be donated to the Good Bunny Foundation which focuses on supporting children and youth from underserved communities to encourage the development of talent in music, arts, and sports.",
				                "pleaseNote": "Over 3s only. Under 16s must be accompanied by an adult over 18. Anyone under the age of 14 is not allowed onto the floor pitch area under any circumstances, regardless of whether they are accompanied by an adult or otherwise. We strongly advise that children under the age of 5 are not brought to events at Tottenham Hotspur Stadium. Please note that young children and all visitors to the stadium require a full priced ticket. A max of 8 tickets per person and per household applies. Tickets in excess of 8 will be cancelled.",
				                "products": [
				                    {
				                        "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour - Venue Hospitality",
				                        "id": "17u8vxG61LZvwO1",
				                        "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-06-28-2026/event/350062A7C007280A",
				                        "type": "VIP",
				                        "classifications": [
				                            {
				                                "primary": true,
				                                "segment": {
				                                    "id": "KZFzniwnSyZfZ7v7nJ",
				                                    "name": "Música"
				                                },
				                                "genre": {
				                                    "id": "KnvZfZ7vAv1",
				                                    "name": "Hip-Hop/Rap"
				                                },
				                                "subGenre": {
				                                    "id": "KZazBEonSMnZfZ7vaEI"
				                                },
				                                "type": {
				                                    "id": "KZAyXgnZfZ7v7nI",
				                                    "name": "Indefinido"
				                                },
				                                "subType": {
				                                    "id": "KZFzBErXgnZfZ7v7lJ",
				                                    "name": "Indefinido"
				                                },
				                                "family": false
				                            }
				                        ]
				                    }
				                ],
				                "accessibility": {
				                    "url": "https://www.eticketing.co.uk/tottenhamhotspurstadium/EDP/Event/Index/109?spell=cb086474-7cca-4da4-b739-b486a8c64062",
				                    "urlText": "Click here to purchase tickets"
				                },
				                "ticketLimit": {
				                    "info": "Please note: There is a ticket Limit of 8 tickets per person and per credit card on this event"
				                },
				                "ageRestrictions": {
				                    "legalAgeEnforced": false
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": true
				                    },
				                    "allInclusivePricing": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "linkMoreInfo": {
				                    "descriptions": {
				                        "ca-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "de-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-us": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "es-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-gb": "To learn more about the Good Bunny Foundation, please click here."
				                    },
				                    "url": "https://www.goodbunnyfoundation.org/"
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/17u8vxG61C8qvZL?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Tottenham Hotspur Stadium",
				                            "type": "venue",
				                            "id": "KovZ9177OxV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.co.uk/tottenham-hotspur-stadium-entradas-london/venue/434396",
				                            "locale": "es-es",
				                            "postalCode": "N17 0BX",
				                            "timezone": "Europe/London",
				                            "city": {
				                                "name": "London"
				                            },
				                            "country": {
				                                "name": "Gran Bretaña",
				                                "countryCode": "GB"
				                            },
				                            "address": {
				                                "line1": "782 High Rd"
				                            },
				                            "location": {
				                                "longitude": "-0.06787000",
				                                "latitude": "51.60081900"
				                            },
				                            "markets": [
				                                {
				                                    "id": "201"
				                                }
				                            ],
				                            "dmas": [
				                                {
				                                    "id": 601
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketmaster": 14,
				                                "_total": 14,
				                                "_filtered": 0
				                            },
				                            "ada": {
				                                "adaPhones": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)",
				                                "adaHours": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)"
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Bad Bunny",
				                            "type": "attraction",
				                            "id": "K8vZ9174l1f",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/sanbenito"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id1126808565"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "https://www.last.fm/music/Bad+Bunny"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/BadBunnyOfficial"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                                        "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/badbunnypr/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                                    "width": 8448,
				                                    "height": 6336,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAv1",
				                                        "name": "Hip-Hop/Rap"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vke1",
				                                        "name": "Latin Hip-Hop"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAd7",
				                                        "name": "Músico"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketnet": 3,
				                                "mfx-se": 4,
				                                "mfx-be": 2,
				                                "mfx-es": 19,
				                                "tmr": 21,
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "mfx-de": 4,
				                                "mfx-it": 2,
				                                "mfx-pl": 3,
				                                "_total": 64,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Chuwi",
				                            "type": "attraction",
				                            "id": "K8vZ917ripf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/chuwi-entradas/1375814",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/6wF1Cz760dpdbX9RJIDpQW"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAJ6",
				                                        "name": "Latin"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7va1a",
				                                        "name": "Latin"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7l1",
				                                        "name": "Grupo"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vA71",
				                                        "name": "Grupo"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "draftStatus": "ACCEPTED",
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				                "type": "event",
				                "id": "1AdfZbtGkDHdFRv",
				                "test": false,
				                "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-27-06-2026/event/3500629EFC0C8BC1",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                        "width": 8448,
				                        "height": 6336,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startTBD": false,
				                        "startTBA": false
				                    },
				                    "presales": [
				                        {
				                            "startDateTime": "2025-05-07T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Priority from O2"
				                        },
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Live Nation Presale"
				                        }
				                    ]
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-27",
				                        "localTime": "17:00:00",
				                        "dateTime": "2026-06-27T16:00:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/London",
				                    "status": {
				                        "code": "onsale"
				                    },
				                    "spanMultipleDays": false
				                },
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vaEI"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7nI",
				                            "name": "Indefinido"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7v7lJ",
				                            "name": "Indefinido"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "4110",
				                    "name": "LIVE NATION MUSIC UK LTD",
				                    "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                },
				                "promoters": [
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "3749",
				                        "name": "LNM - VIP NATION UK",
				                        "description": "LNM - VIP NATION UK / NTL / GBR"
				                    }
				                ],
				                "info": "5:00pm Doors 6:30pm CHUWI 7:30pm Bad Bunny 10:30pm Curfew A portion of the ticket proceeds will be donated to the Good Bunny Foundation which focuses on supporting children and youth from underserved communities to encourage the development of talent in music, arts, and sports.",
				                "pleaseNote": "Over 3s only. Under 16s must be accompanied by an adult over 18. Anyone under the age of 14 is not allowed onto the floor pitch area under any circumstances, regardless of whether they are accompanied by an adult or otherwise. We strongly advise that children under the age of 5 are not brought to events at Tottenham Hotspur Stadium. Please note that young children and all visitors to the stadium require a full priced ticket. A max of 8 tickets per person and per household applies. Tickets in excess of 8 will be cancelled.",
				                "products": [
				                    {
				                        "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour - Venue Hospitality",
				                        "id": "17u8vxG61rxk-FI",
				                        "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-06-27-2026/event/350062A7A21A1228",
				                        "type": "VIP",
				                        "classifications": [
				                            {
				                                "primary": true,
				                                "segment": {
				                                    "id": "KZFzniwnSyZfZ7v7nJ",
				                                    "name": "Música"
				                                },
				                                "genre": {
				                                    "id": "KnvZfZ7vAv1",
				                                    "name": "Hip-Hop/Rap"
				                                },
				                                "subGenre": {
				                                    "id": "KZazBEonSMnZfZ7vaEI"
				                                },
				                                "type": {
				                                    "id": "KZAyXgnZfZ7v7nI",
				                                    "name": "Indefinido"
				                                },
				                                "subType": {
				                                    "id": "KZFzBErXgnZfZ7v7lJ",
				                                    "name": "Indefinido"
				                                },
				                                "family": false
				                            }
				                        ]
				                    }
				                ],
				                "accessibility": {
				                    "url": "https://www.eticketing.co.uk/tottenhamhotspurstadium/EDP/Event/Index/108?spell=21aa8a74-0102-46fa-b5b7-c0d9f737521d",
				                    "urlText": "Click here to purchase tickets"
				                },
				                "ticketLimit": {
				                    "info": "Please note: There is a ticket Limit of 8 tickets per person and per credit card on this event"
				                },
				                "ageRestrictions": {
				                    "legalAgeEnforced": false
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": true
				                    },
				                    "allInclusivePricing": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "linkMoreInfo": {
				                    "descriptions": {
				                        "ca-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "de-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-us": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "es-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-gb": "To learn more about the Good Bunny Foundation, please click here."
				                    },
				                    "url": "https://www.goodbunnyfoundation.org/"
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/1AdfZbtGkDHdFRv?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Tottenham Hotspur Stadium",
				                            "type": "venue",
				                            "id": "KovZ9177OxV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.co.uk/tottenham-hotspur-stadium-entradas-london/venue/434396",
				                            "locale": "es-es",
				                            "postalCode": "N17 0BX",
				                            "timezone": "Europe/London",
				                            "city": {
				                                "name": "London"
				                            },
				                            "country": {
				                                "name": "Gran Bretaña",
				                                "countryCode": "GB"
				                            },
				                            "address": {
				                                "line1": "782 High Rd"
				                            },
				                            "location": {
				                                "longitude": "-0.06787000",
				                                "latitude": "51.60081900"
				                            },
				                            "markets": [
				                                {
				                                    "id": "201"
				                                }
				                            ],
				                            "dmas": [
				                                {
				                                    "id": 601
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketmaster": 14,
				                                "_total": 14,
				                                "_filtered": 0
				                            },
				                            "ada": {
				                                "adaPhones": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)",
				                                "adaHours": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)"
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Bad Bunny",
				                            "type": "attraction",
				                            "id": "K8vZ9174l1f",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/sanbenito"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id1126808565"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "https://www.last.fm/music/Bad+Bunny"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/BadBunnyOfficial"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                                        "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/badbunnypr/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                                    "width": 8448,
				                                    "height": 6336,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAv1",
				                                        "name": "Hip-Hop/Rap"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vke1",
				                                        "name": "Latin Hip-Hop"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAd7",
				                                        "name": "Músico"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketnet": 3,
				                                "mfx-se": 4,
				                                "mfx-be": 2,
				                                "mfx-es": 19,
				                                "tmr": 21,
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "mfx-de": 4,
				                                "mfx-it": 2,
				                                "mfx-pl": 3,
				                                "_total": 64,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Chuwi",
				                            "type": "attraction",
				                            "id": "K8vZ917ripf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/chuwi-entradas/1375814",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/6wF1Cz760dpdbX9RJIDpQW"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAJ6",
				                                        "name": "Latin"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7va1a",
				                                        "name": "Latin"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7l1",
				                                        "name": "Grupo"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vA71",
				                                        "name": "Grupo"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "draftStatus": "ACCEPTED",
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            }
				        ]
				    },
				    "_links": {
				        "first": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=0&size=2&sort=date,desc"
				        },
				        "self": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&size=2&includeTBA=no&sort=date%2Cdesc&locale=es&keyword=Bad+Bunny&includeTBD=no"
				        },
				        "next": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=1&size=2&sort=date,desc"
				        },
				        "last": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=9&size=2&sort=date,desc"
				        }
				    },
				    "page": {
				        "size": 2,
				        "totalElements": 20,
				        "totalPages": 10,
				        "number": 0
				    }
				}
								""";

		mockServer.expect(MockRestRequestMatchers.anything())
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		scheduledSearchService.searchForNewConcerts();

		assertEquals(2, artist.getKnownConcerts().size());
		verify(notificationRepository, never()).save(any());
		verify(artistRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("Tests if searchForNewConcerts does nothing when there are no new concerts")
	void searchForNewConcertsNoNewConcerts() {

		Artist artist = new Artist("id1", "Bad Bunny");
		artist.setKnownConcerts(new ArrayList<>(List.of("17u8vxG61C8qvZL")));

		when(artistRepository.findAll()).thenReturn(List.of(artist));

		String result = """
				{
				    "_embedded": {
				        "events": [
				            {
				                "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				                "type": "event",
				                "id": "17u8vxG61C8qvZL",
				                "test": false,
				                "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-28-06-2026/event/350062A39074101F",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                        "width": 8448,
				                        "height": 6336,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startTBD": false,
				                        "startTBA": false
				                    },
				                    "presales": [
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Priority from O2"
				                        },
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Live Nation Presale"
				                        }
				                    ]
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-28",
				                        "localTime": "16:30:00",
				                        "dateTime": "2026-06-28T15:30:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/London",
				                    "status": {
				                        "code": "onsale"
				                    },
				                    "spanMultipleDays": false
				                },
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vaEI"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7nI",
				                            "name": "Indefinido"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7v7lJ",
				                            "name": "Indefinido"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "4110",
				                    "name": "LIVE NATION MUSIC UK LTD",
				                    "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                },
				                "promoters": [
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "3749",
				                        "name": "LNM - VIP NATION UK",
				                        "description": "LNM - VIP NATION UK / NTL / GBR"
				                    }
				                ],
				                "info": "4:30pm Doors 6:00pm CHUWI 7:00pm Bad Bunny 10:00pm Curfew A portion of the ticket proceeds will be donated to the Good Bunny Foundation which focuses on supporting children and youth from underserved communities to encourage the development of talent in music, arts, and sports.",
				                "pleaseNote": "Over 3s only. Under 16s must be accompanied by an adult over 18. Anyone under the age of 14 is not allowed onto the floor pitch area under any circumstances, regardless of whether they are accompanied by an adult or otherwise. We strongly advise that children under the age of 5 are not brought to events at Tottenham Hotspur Stadium. Please note that young children and all visitors to the stadium require a full priced ticket. A max of 8 tickets per person and per household applies. Tickets in excess of 8 will be cancelled.",
				                "products": [
				                    {
				                        "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour - Venue Hospitality",
				                        "id": "17u8vxG61LZvwO1",
				                        "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-06-28-2026/event/350062A7C007280A",
				                        "type": "VIP",
				                        "classifications": [
				                            {
				                                "primary": true,
				                                "segment": {
				                                    "id": "KZFzniwnSyZfZ7v7nJ",
				                                    "name": "Música"
				                                },
				                                "genre": {
				                                    "id": "KnvZfZ7vAv1",
				                                    "name": "Hip-Hop/Rap"
				                                },
				                                "subGenre": {
				                                    "id": "KZazBEonSMnZfZ7vaEI"
				                                },
				                                "type": {
				                                    "id": "KZAyXgnZfZ7v7nI",
				                                    "name": "Indefinido"
				                                },
				                                "subType": {
				                                    "id": "KZFzBErXgnZfZ7v7lJ",
				                                    "name": "Indefinido"
				                                },
				                                "family": false
				                            }
				                        ]
				                    }
				                ],
				                "accessibility": {
				                    "url": "https://www.eticketing.co.uk/tottenhamhotspurstadium/EDP/Event/Index/109?spell=cb086474-7cca-4da4-b739-b486a8c64062",
				                    "urlText": "Click here to purchase tickets"
				                },
				                "ticketLimit": {
				                    "info": "Please note: There is a ticket Limit of 8 tickets per person and per credit card on this event"
				                },
				                "ageRestrictions": {
				                    "legalAgeEnforced": false
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": true
				                    },
				                    "allInclusivePricing": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "linkMoreInfo": {
				                    "descriptions": {
				                        "ca-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "de-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-us": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "es-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-gb": "To learn more about the Good Bunny Foundation, please click here."
				                    },
				                    "url": "https://www.goodbunnyfoundation.org/"
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/17u8vxG61C8qvZL?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Tottenham Hotspur Stadium",
				                            "type": "venue",
				                            "id": "KovZ9177OxV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.co.uk/tottenham-hotspur-stadium-entradas-london/venue/434396",
				                            "locale": "es-es",
				                            "postalCode": "N17 0BX",
				                            "timezone": "Europe/London",
				                            "city": {
				                                "name": "London"
				                            },
				                            "country": {
				                                "name": "Gran Bretaña",
				                                "countryCode": "GB"
				                            },
				                            "address": {
				                                "line1": "782 High Rd"
				                            },
				                            "location": {
				                                "longitude": "-0.06787000",
				                                "latitude": "51.60081900"
				                            },
				                            "markets": [
				                                {
				                                    "id": "201"
				                                }
				                            ],
				                            "dmas": [
				                                {
				                                    "id": 601
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketmaster": 14,
				                                "_total": 14,
				                                "_filtered": 0
				                            },
				                            "ada": {
				                                "adaPhones": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)",
				                                "adaHours": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)"
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Bad Bunny",
				                            "type": "attraction",
				                            "id": "K8vZ9174l1f",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/sanbenito"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id1126808565"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "https://www.last.fm/music/Bad+Bunny"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/BadBunnyOfficial"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                                        "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/badbunnypr/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                                    "width": 8448,
				                                    "height": 6336,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAv1",
				                                        "name": "Hip-Hop/Rap"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vke1",
				                                        "name": "Latin Hip-Hop"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAd7",
				                                        "name": "Músico"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketnet": 3,
				                                "mfx-se": 4,
				                                "mfx-be": 2,
				                                "mfx-es": 19,
				                                "tmr": 21,
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "mfx-de": 4,
				                                "mfx-it": 2,
				                                "mfx-pl": 3,
				                                "_total": 64,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Chuwi",
				                            "type": "attraction",
				                            "id": "K8vZ917ripf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/chuwi-entradas/1375814",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/6wF1Cz760dpdbX9RJIDpQW"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAJ6",
				                                        "name": "Latin"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7va1a",
				                                        "name": "Latin"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7l1",
				                                        "name": "Grupo"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vA71",
				                                        "name": "Grupo"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "draftStatus": "ACCEPTED",
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            }
				        ]
				    },
				    "_links": {
				        "first": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=0&size=2&sort=date,desc"
				        },
				        "self": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&size=2&includeTBA=no&sort=date%2Cdesc&locale=es&keyword=Bad+Bunny&includeTBD=no"
				        },
				        "next": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=1&size=2&sort=date,desc"
				        },
				        "last": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=9&size=2&sort=date,desc"
				        }
				    },
				    "page": {
				        "size": 2,
				        "totalElements": 20,
				        "totalPages": 10,
				        "number": 0
				    }
				}
								""";

		mockServer.expect(MockRestRequestMatchers.anything())
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		scheduledSearchService.searchForNewConcerts();

		assertEquals(1, artist.getKnownConcerts().size());
		verify(notificationRepository, never()).save(any());
		verify(artistRepository, never()).save(any());
	}
	
	@Test
	@DisplayName("Tests if searchForNewConcerts saves artist and notification when the artist has one new concert")
	void searchForNewConcertsOneNewConcert() {

		Artist artist = new Artist("id1", "Bad Bunny");
		artist.setKnownConcerts(new ArrayList<>(List.of("17u8vxG61C8qvZL")));

		when(artistRepository.findAll()).thenReturn(List.of(artist));
		when(userRepository.findByFavouriteArtistsContains(artist)).thenReturn(List.of(user));

		String result = """
				{
				    "_embedded": {
				        "events": [
				            {
				                "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				                "type": "event",
				                "id": "17u8vxG61C8qvZL",
				                "test": false,
				                "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-28-06-2026/event/350062A39074101F",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                        "width": 8448,
				                        "height": 6336,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startTBD": false,
				                        "startTBA": false
				                    },
				                    "presales": [
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Priority from O2"
				                        },
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Live Nation Presale"
				                        }
				                    ]
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-28",
				                        "localTime": "16:30:00",
				                        "dateTime": "2026-06-28T15:30:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/London",
				                    "status": {
				                        "code": "onsale"
				                    },
				                    "spanMultipleDays": false
				                },
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vaEI"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7nI",
				                            "name": "Indefinido"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7v7lJ",
				                            "name": "Indefinido"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "4110",
				                    "name": "LIVE NATION MUSIC UK LTD",
				                    "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                },
				                "promoters": [
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "3749",
				                        "name": "LNM - VIP NATION UK",
				                        "description": "LNM - VIP NATION UK / NTL / GBR"
				                    }
				                ],
				                "info": "4:30pm Doors 6:00pm CHUWI 7:00pm Bad Bunny 10:00pm Curfew A portion of the ticket proceeds will be donated to the Good Bunny Foundation which focuses on supporting children and youth from underserved communities to encourage the development of talent in music, arts, and sports.",
				                "pleaseNote": "Over 3s only. Under 16s must be accompanied by an adult over 18. Anyone under the age of 14 is not allowed onto the floor pitch area under any circumstances, regardless of whether they are accompanied by an adult or otherwise. We strongly advise that children under the age of 5 are not brought to events at Tottenham Hotspur Stadium. Please note that young children and all visitors to the stadium require a full priced ticket. A max of 8 tickets per person and per household applies. Tickets in excess of 8 will be cancelled.",
				                "products": [
				                    {
				                        "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour - Venue Hospitality",
				                        "id": "17u8vxG61LZvwO1",
				                        "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-06-28-2026/event/350062A7C007280A",
				                        "type": "VIP",
				                        "classifications": [
				                            {
				                                "primary": true,
				                                "segment": {
				                                    "id": "KZFzniwnSyZfZ7v7nJ",
				                                    "name": "Música"
				                                },
				                                "genre": {
				                                    "id": "KnvZfZ7vAv1",
				                                    "name": "Hip-Hop/Rap"
				                                },
				                                "subGenre": {
				                                    "id": "KZazBEonSMnZfZ7vaEI"
				                                },
				                                "type": {
				                                    "id": "KZAyXgnZfZ7v7nI",
				                                    "name": "Indefinido"
				                                },
				                                "subType": {
				                                    "id": "KZFzBErXgnZfZ7v7lJ",
				                                    "name": "Indefinido"
				                                },
				                                "family": false
				                            }
				                        ]
				                    }
				                ],
				                "accessibility": {
				                    "url": "https://www.eticketing.co.uk/tottenhamhotspurstadium/EDP/Event/Index/109?spell=cb086474-7cca-4da4-b739-b486a8c64062",
				                    "urlText": "Click here to purchase tickets"
				                },
				                "ticketLimit": {
				                    "info": "Please note: There is a ticket Limit of 8 tickets per person and per credit card on this event"
				                },
				                "ageRestrictions": {
				                    "legalAgeEnforced": false
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": true
				                    },
				                    "allInclusivePricing": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "linkMoreInfo": {
				                    "descriptions": {
				                        "ca-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "de-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-us": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "es-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-gb": "To learn more about the Good Bunny Foundation, please click here."
				                    },
				                    "url": "https://www.goodbunnyfoundation.org/"
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/17u8vxG61C8qvZL?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Tottenham Hotspur Stadium",
				                            "type": "venue",
				                            "id": "KovZ9177OxV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.co.uk/tottenham-hotspur-stadium-entradas-london/venue/434396",
				                            "locale": "es-es",
				                            "postalCode": "N17 0BX",
				                            "timezone": "Europe/London",
				                            "city": {
				                                "name": "London"
				                            },
				                            "country": {
				                                "name": "Gran Bretaña",
				                                "countryCode": "GB"
				                            },
				                            "address": {
				                                "line1": "782 High Rd"
				                            },
				                            "location": {
				                                "longitude": "-0.06787000",
				                                "latitude": "51.60081900"
				                            },
				                            "markets": [
				                                {
				                                    "id": "201"
				                                }
				                            ],
				                            "dmas": [
				                                {
				                                    "id": 601
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketmaster": 14,
				                                "_total": 14,
				                                "_filtered": 0
				                            },
				                            "ada": {
				                                "adaPhones": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)",
				                                "adaHours": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)"
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Bad Bunny",
				                            "type": "attraction",
				                            "id": "K8vZ9174l1f",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/sanbenito"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id1126808565"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "https://www.last.fm/music/Bad+Bunny"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/BadBunnyOfficial"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                                        "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/badbunnypr/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                                    "width": 8448,
				                                    "height": 6336,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAv1",
				                                        "name": "Hip-Hop/Rap"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vke1",
				                                        "name": "Latin Hip-Hop"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAd7",
				                                        "name": "Músico"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketnet": 3,
				                                "mfx-se": 4,
				                                "mfx-be": 2,
				                                "mfx-es": 19,
				                                "tmr": 21,
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "mfx-de": 4,
				                                "mfx-it": 2,
				                                "mfx-pl": 3,
				                                "_total": 64,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Chuwi",
				                            "type": "attraction",
				                            "id": "K8vZ917ripf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/chuwi-entradas/1375814",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/6wF1Cz760dpdbX9RJIDpQW"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAJ6",
				                                        "name": "Latin"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7va1a",
				                                        "name": "Latin"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7l1",
				                                        "name": "Grupo"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vA71",
				                                        "name": "Grupo"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "draftStatus": "ACCEPTED",
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour",
				                "type": "event",
				                "id": "1AdfZbtGkDHdFRv",
				                "test": false,
				                "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-27-06-2026/event/3500629EFC0C8BC1",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                        "width": 8448,
				                        "height": 6336,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startTBD": false,
				                        "startTBA": false
				                    },
				                    "presales": [
				                        {
				                            "startDateTime": "2025-05-07T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Priority from O2"
				                        },
				                        {
				                            "startDateTime": "2025-05-08T11:00:00Z",
				                            "endDateTime": "2025-05-09T10:00:00Z",
				                            "name": "Live Nation Presale"
				                        }
				                    ]
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-27",
				                        "localTime": "17:00:00",
				                        "dateTime": "2026-06-27T16:00:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/London",
				                    "status": {
				                        "code": "onsale"
				                    },
				                    "spanMultipleDays": false
				                },
				                "classifications": [
				                    {
				                        "primary": true,
				                        "segment": {
				                            "id": "KZFzniwnSyZfZ7v7nJ",
				                            "name": "Música"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vaEI"
				                        },
				                        "type": {
				                            "id": "KZAyXgnZfZ7v7nI",
				                            "name": "Indefinido"
				                        },
				                        "subType": {
				                            "id": "KZFzBErXgnZfZ7v7lJ",
				                            "name": "Indefinido"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "4110",
				                    "name": "LIVE NATION MUSIC UK LTD",
				                    "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                },
				                "promoters": [
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "4110",
				                        "name": "LIVE NATION MUSIC UK LTD",
				                        "description": "LIVE NATION MUSIC UK LTD / NTL / GBR"
				                    },
				                    {
				                        "id": "3749",
				                        "name": "LNM - VIP NATION UK",
				                        "description": "LNM - VIP NATION UK / NTL / GBR"
				                    }
				                ],
				                "info": "5:00pm Doors 6:30pm CHUWI 7:30pm Bad Bunny 10:30pm Curfew A portion of the ticket proceeds will be donated to the Good Bunny Foundation which focuses on supporting children and youth from underserved communities to encourage the development of talent in music, arts, and sports.",
				                "pleaseNote": "Over 3s only. Under 16s must be accompanied by an adult over 18. Anyone under the age of 14 is not allowed onto the floor pitch area under any circumstances, regardless of whether they are accompanied by an adult or otherwise. We strongly advise that children under the age of 5 are not brought to events at Tottenham Hotspur Stadium. Please note that young children and all visitors to the stadium require a full priced ticket. A max of 8 tickets per person and per household applies. Tickets in excess of 8 will be cancelled.",
				                "products": [
				                    {
				                        "name": "Bad Bunny - DeBÍ TiRAR MáS FOToS World Tour - Venue Hospitality",
				                        "id": "17u8vxG61rxk-FI",
				                        "url": "https://www.ticketmaster.co.uk/bad-bunny-debi-tirar-mas-fotos-london-06-27-2026/event/350062A7A21A1228",
				                        "type": "VIP",
				                        "classifications": [
				                            {
				                                "primary": true,
				                                "segment": {
				                                    "id": "KZFzniwnSyZfZ7v7nJ",
				                                    "name": "Música"
				                                },
				                                "genre": {
				                                    "id": "KnvZfZ7vAv1",
				                                    "name": "Hip-Hop/Rap"
				                                },
				                                "subGenre": {
				                                    "id": "KZazBEonSMnZfZ7vaEI"
				                                },
				                                "type": {
				                                    "id": "KZAyXgnZfZ7v7nI",
				                                    "name": "Indefinido"
				                                },
				                                "subType": {
				                                    "id": "KZFzBErXgnZfZ7v7lJ",
				                                    "name": "Indefinido"
				                                },
				                                "family": false
				                            }
				                        ]
				                    }
				                ],
				                "accessibility": {
				                    "url": "https://www.eticketing.co.uk/tottenhamhotspurstadium/EDP/Event/Index/108?spell=21aa8a74-0102-46fa-b5b7-c0d9f737521d",
				                    "urlText": "Click here to purchase tickets"
				                },
				                "ticketLimit": {
				                    "info": "Please note: There is a ticket Limit of 8 tickets per person and per credit card on this event"
				                },
				                "ageRestrictions": {
				                    "legalAgeEnforced": false
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": true
				                    },
				                    "allInclusivePricing": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "linkMoreInfo": {
				                    "descriptions": {
				                        "ca-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "de-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-us": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-de": "To learn more about the Good Bunny Foundation, please click here.",
				                        "es-es": "To learn more about the Good Bunny Foundation, please click here.",
				                        "en-gb": "To learn more about the Good Bunny Foundation, please click here."
				                    },
				                    "url": "https://www.goodbunnyfoundation.org/"
				                },
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/1AdfZbtGkDHdFRv?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Tottenham Hotspur Stadium",
				                            "type": "venue",
				                            "id": "KovZ9177OxV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.co.uk/tottenham-hotspur-stadium-entradas-london/venue/434396",
				                            "locale": "es-es",
				                            "postalCode": "N17 0BX",
				                            "timezone": "Europe/London",
				                            "city": {
				                                "name": "London"
				                            },
				                            "country": {
				                                "name": "Gran Bretaña",
				                                "countryCode": "GB"
				                            },
				                            "address": {
				                                "line1": "782 High Rd"
				                            },
				                            "location": {
				                                "longitude": "-0.06787000",
				                                "latitude": "51.60081900"
				                            },
				                            "markets": [
				                                {
				                                    "id": "201"
				                                }
				                            ],
				                            "dmas": [
				                                {
				                                    "id": 601
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketmaster": 14,
				                                "_total": 14,
				                                "_filtered": 0
				                            },
				                            "ada": {
				                                "adaPhones": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)",
				                                "adaHours": "https://www.tottenhamhotspurstadium.com/plan-your-visit/accessibility/ (copy to browser)"
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/KovZ9177OxV?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Bad Bunny",
				                            "type": "attraction",
				                            "id": "K8vZ9174l1f",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/bad-bunny-entradas/979454",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCmBA_wu8xGg1OfOkfW13Q0Q"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/sanbenito"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id1126808565"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "https://www.last.fm/music/Bad+Bunny"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/4q3ewBCX7sLwd24euuV69X"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/BadBunnyOfficial"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Bad_Bunny"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "89aa5ecb-59ad-46f5-b3eb-2d424e941f19",
				                                        "url": "https://musicbrainz.org/artist/89aa5ecb-59ad-46f5-b3eb-2d424e941f19"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/badbunnypr/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_SOURCE",
				                                    "width": 8448,
				                                    "height": 6336,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/47e/4c3cb342-dc14-45d3-93ea-85fa9ecdd47e_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAv1",
				                                        "name": "Hip-Hop/Rap"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vke1",
				                                        "name": "Latin Hip-Hop"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAd7",
				                                        "name": "Músico"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "ticketnet": 3,
				                                "mfx-se": 4,
				                                "mfx-be": 2,
				                                "mfx-es": 19,
				                                "tmr": 21,
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "mfx-de": 4,
				                                "mfx-it": 2,
				                                "mfx-pl": 3,
				                                "_total": 64,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9174l1f?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Chuwi",
				                            "type": "attraction",
				                            "id": "K8vZ917ripf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/chuwi-entradas/1375814",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/6wF1Cz760dpdbX9RJIDpQW"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/000/4f7b0418-8349-4cb3-a1ef-1bb1a753d000_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7nJ",
				                                        "name": "Música"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7vAJ6",
				                                        "name": "Latin"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7va1a",
				                                        "name": "Latin"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7l1",
				                                        "name": "Grupo"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vA71",
				                                        "name": "Grupo"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-nl": 4,
				                                "ticketmaster": 2,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "draftStatus": "ACCEPTED",
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917ripf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            }
				        ]
				    },
				    "_links": {
				        "first": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=0&size=2&sort=date,desc"
				        },
				        "self": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&size=2&includeTBA=no&sort=date%2Cdesc&locale=es&keyword=Bad+Bunny&includeTBD=no"
				        },
				        "next": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=1&size=2&sort=date,desc"
				        },
				        "last": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-06-06T00%3A00%3A00Z&includeTBA=no&locale=es&keyword=Bad+Bunny&includeTBD=no&page=9&size=2&sort=date,desc"
				        }
				    },
				    "page": {
				        "size": 2,
				        "totalElements": 20,
				        "totalPages": 10,
				        "number": 0
				    }
				}
								""";

		mockServer.expect(MockRestRequestMatchers.anything())
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		scheduledSearchService.searchForNewConcerts();

		assertEquals(2, artist.getKnownConcerts().size());
		verify(notificationRepository, times(1)).save(any(Notification.class));
		verify(artistRepository, times(1)).save(any());
	}
}
