package es.metrica.trackticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.services.SearchServiceImpl;

class SearchServiceImplTest {

	private SearchServiceImpl searchService;
	private MockRestServiceServer mockServer;

	@BeforeEach
	void setUp() {

		RestClient.Builder builder = RestClient.builder();

		mockServer = MockRestServiceServer.bindTo(builder).build();

		searchService = new SearchServiceImpl(builder, "https://app.ticketmaster.com/discovery/v2",
				"uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9");
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid artist and two valid dates.")
	void artistAndTwoDates() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		LocalDateTime finalDay = LocalDateTime.of(2026, 05, 24, 00, 00, 00);
		String artist = "El Último de la Fila";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, finalDay, artist, "");

		String result = """
										{
				    "_embedded": {
				        "events": [
				            {
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ16v0vGxJo",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/1901322991",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_SOURCE",
				                        "width": 2427,
				                        "height": 1366,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2025-06-16T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-23T19:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-23",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-23T18:30:00Z",
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
				                            "id": "KnvZfZ7vAeA",
				                            "name": "Rock"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7v6F1",
				                            "name": "Pop"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6089",
				                    "name": "FEEL THE BASS AIE"
				                },
				                "promoters": [
				                    {
				                        "id": "6089",
				                        "name": "FEEL THE BASS AIE"
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/d24a87faf3e6a32ce616237f913cbb6a.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ16v0vGxJo?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
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
				                                "mfx-es": 49,
				                                "tmr": 12,
				                                "_total": 61,
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
				                            "name": "El Último de la Fila",
				                            "type": "attraction",
				                            "id": "K8vZ917r1Yf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/el-ultimo-de-la-fila-entradas/1418653",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/intl-es/artist/2jMYTBTCSNYaCYy54mLc6I"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_SOURCE",
				                                    "width": 2427,
				                                    "height": 1366,
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
				                                        "id": "KnvZfZ7vAeA",
				                                        "name": "Rock"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v6F1",
				                                        "name": "Pop"
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
				                                "mfx-es": 10,
				                                "_total": 10,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Plaza de Parking - El Último de la Fila - 23 de Mayo de 2026",
				                "type": "event",
				                "id": "Z698xZ2qZ16vFEvxAa",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/plaza-de-parking-el-ultimo-de-la-fila-23-de-mayo-de-2026-entradas/1227062149",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_SOURCE",
				                        "width": 2427,
				                        "height": 1366,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2025-06-16T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-23T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-23",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": true,
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
				                            "id": "KnvZfZ7vAeA",
				                            "name": "Rock"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7v6F1",
				                            "name": "Pop"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6089",
				                    "name": "FEEL THE BASS AIE"
				                },
				                "promoters": [
				                    {
				                        "id": "6089",
				                        "name": "FEEL THE BASS AIE"
				                    }
				                ],
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vFEvxAa?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ6F77?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Parking Riyadh Air Metropolitano",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ6F77",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/parking-riyadh-air-metropolitano-madrid-entradas/parkcivmad/112",
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
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ6F77?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "El Último de la Fila",
				                            "type": "attraction",
				                            "id": "K8vZ917r1Yf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/el-ultimo-de-la-fila-entradas/1418653",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/intl-es/artist/2jMYTBTCSNYaCYy54mLc6I"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1d8/ee6c1b17-6113-4329-8134-3ed2717641d8_SOURCE",
				                                    "width": 2427,
				                                    "height": 1366,
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
				                                        "id": "KnvZfZ7vAeA",
				                                        "name": "Rock"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v6F1",
				                                        "name": "Pop"
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
				                                "mfx-es": 10,
				                                "_total": 10,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            }
				        ]
				    },
				    "_links": {
				        "self": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&includeTBA=no&sort=date%2Casc&locale=es&endDateTime=2026-05-24T00%3A00%3A00Z&keyword=El+%C3%9Altimo+de+la+Fila&includeTBD=no"
				        }
				    },
				    "page": {
				        "size": 20,
				        "totalElements": 2,
				        "totalPages": 1,
				        "number": 0
				    }
				}
												""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&endDateTime=2026-05-24T00:00:00Z&keyword=El%20%C3%9Altimo%20de%20la%20Fila"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		List<ConcertResponseDTO> list = searchService.searchConcerts(dto);
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ16v0vGxJo",
				"El Último de la Fila", 
				LocalDate.of(2026, 05, 23),
				"https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/1901322991",
				"El Último de la Fila", 
				new VenueDTO("Estadio Riyadh Air Metropolitano",
						40.43624,
						-3.59947,
						"Av. de Luis Aragonés, 4, 28022, Madrid",
						"Madrid",
						"España"));		
		
		assertEquals(2, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

}
