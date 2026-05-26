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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import es.metrica.trackticket.dto.ConcertResponseDTO;
import es.metrica.trackticket.dto.ConcertSearchRequestDTO;
import es.metrica.trackticket.dto.VenueDTO;
import es.metrica.trackticket.exception.ResourceNotFoundException;
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
				                                "line1": "Av. de Luis Aragonés, 4",
				                                "line2": "Línea 2 de prueba"
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
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ16v0vGxJo", "El Último de la Fila",
				LocalDate.of(2026, 05, 23),
				"https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/1901322991", "El Último de la Fila", "Rock",
				new VenueDTO("Estadio Riyadh Air Metropolitano", 40.43624, -3.59947,
						"Av. de Luis Aragonés, 4, Línea 2 de prueba, 28022, Madrid", "Madrid", "España"));

		assertEquals(2, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid city and two valid dates.")
	void cityAndTwoDates() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		LocalDateTime finalDay = LocalDateTime.of(2026, 06, 10, 00, 00, 00);
		String city = "Valencia";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, finalDay, "", city);

		String result = """
								{
				    "_embedded": {
				        "events": [
				            {
				                "name": "Kream by Alvama Ice",
				                "type": "event",
				                "id": "Z698xZ2qZ16vv4C3jp",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/kream-by-alvama-ice-entradas/1055392716",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-03T17:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-05T16:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-05",
				                        "localTime": "18:00:00",
				                        "dateTime": "2026-06-05T16:00:00Z",
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
				                            "id": "KnvZfZ7vAvl",
				                            "name": "Otros"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vk1I",
				                            "name": "Otros"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6737",
				                    "name": "Aquí No Hay Silencio, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6737",
				                        "name": "Aquí No Hay Silencio, A.I.E."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vv4C3jp?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917qdIV?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ6v7F?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Estadi Ciutat de València",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ6v7F",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/estadi-ciutat-de-valencia-valencia-entradas/esciutaval/114",
				                            "locale": "es-es",
				                            "postalCode": "46019",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Valencia"
				                            },
				                            "state": {
				                                "name": "Valencia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Sant Vicent de Paül, 44"
				                            },
				                            "location": {
				                                "longitude": "-0.3647",
				                                "latitude": "39.49489"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ6v7F?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Alvama Ice",
				                            "type": "attraction",
				                            "id": "K8vZ917qdIV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/kream-ice-land-by-alvama-ice-entradas/1306223",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_EVENT_DETAIL_PAGE_16_9.jpg",
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
				                                        "id": "KnvZfZ7vAvF",
				                                        "name": "Dance/Electrónica"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vA1E",
				                                        "name": "Dance/Electrónica"
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
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917qdIV?locale=es-br"
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
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&city=Valencia&includeTBA=no&sort=date%2Casc&locale=es&endDateTime=2026-06-10T00%3A00%3A00Z&includeTBD=no"
				        }
				    },
				    "page": {
				        "size": 20,
				        "totalElements": 1,
				        "totalPages": 1,
				        "number": 0
				    }
				}
								""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&endDateTime=2026-06-10T00:00:00Z&city=Valencia"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		List<ConcertResponseDTO> list = searchService.searchConcerts(dto);
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ16vv4C3jp", "Kream by Alvama Ice",
				LocalDate.of(2026, 06, 05), "https://www.ticketmaster.es/event/kream-by-alvama-ice-entradas/1055392716",
				"Alvama Ice", "Otros", new VenueDTO("Estadi Ciutat de València", 39.49489, -0.3647,
						"Carrer de Sant Vicent de Paül, 44, 46019, Valencia", "Valencia", "España"));

		assertEquals(1, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid city, a valid artist and two valid dates.")
	void cityAndArtistAndTwoDates() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		LocalDateTime finalDay = LocalDateTime.of(2026, 06, 10, 00, 00, 00);
		String city = "Barcelona";
		String artist = "Delarue";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, finalDay, artist, city);

		String result = """
								{
				    "_embedded": {
				        "events": [
				            {
				                "name": "Delarue",
				                "type": "event",
				                "id": "Z698xZ2qZ16vCbpdGz",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/delarue-entradas/1398583338",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-04-17T18:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-05T21:00:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-06-05T20:00:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-06-05",
				                        "localTime": "21:00:00",
				                        "dateTime": "2026-06-05T19:00:00Z",
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
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vkvl",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6147",
				                    "name": "La Sordera, S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "6147",
				                        "name": "La Sordera, S.L."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vCbpdGz?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917LtDV?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 2",
				                            "type": "venue",
				                            "id": "Z198xZ2qZd6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-2-barcelona-entradas/slrazz2bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/cb43e3cca923383dd8a20c4790f2772a.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 9,
				                                "_total": 9,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Delarue",
				                            "type": "attraction",
				                            "id": "K8vZ917LtDV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/delarue-entradas/1400664",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
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
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917LtDV?locale=es-br"
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
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&city=Barcelona&includeTBA=no&sort=date%2Casc&locale=es&endDateTime=2026-06-10T00%3A00%3A00Z&keyword=Delarue&includeTBD=no"
				        }
				    },
				    "page": {
				        "size": 20,
				        "totalElements": 1,
				        "totalPages": 1,
				        "number": 0
				    }
				}
								""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&endDateTime=2026-06-10T00:00:00Z&city=Barcelona&keyword=Delarue"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		List<ConcertResponseDTO> list = searchService.searchConcerts(dto);
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ16vCbpdGz", "Delarue",
				LocalDate.of(2026, 06, 05), "https://www.ticketmaster.es/event/delarue-entradas/1398583338", "Delarue", "Hip-Hop/Rap",
				new VenueDTO("Sala Razzmatazz 2", 41.39701, 2.19147, "Carrer de Pamplona, 88, 08018, Barcelona",
						"Barcelona", "España"));

		assertEquals(1, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid city and a valid initial date.")
	void cityAndOneDate() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		String city = "Barcelona";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, "", city);

		String result = """
												{
				    "_embedded": {
				        "events": [
				            {
				                "name": "Madison Beer: the locket tour",
				                "type": "event",
				                "id": "Z698xZ2qZ16vGPFe4K",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/madison-beer-the-locket-tour-entradas/1325202553",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_SOURCE",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/dbc/7fe79548-3bd3-473a-b130-56d83b3eddbc_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false,
				                        "attribution": "Madison Beer Tickets 2026"
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-01-21T09:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-26T19:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-26",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-26T18:30:00Z",
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
				                    "id": "6407",
				                    "name": "Live Nation España Live For Fun, SL"
				                },
				                "promoters": [
				                    {
				                        "id": "6407",
				                        "name": "Live Nation España Live For Fun, SL"
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/1b3f3d4b743e4d9ee2038970d61f0ea9.png"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vGPFe4K?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917prs7?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917hvZf?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917hZR0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZ1e1?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sant Jordi Club",
				                            "type": "venue",
				                            "id": "Z198xZ2qZ1e1",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sant-jordi-club-barcelona-entradas/sjordicbcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/7834ba230c9387671352b3e0b2c8c10a.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08038",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Passeig Olímpic, 5-7"
				                            },
				                            "location": {
				                                "longitude": "2.15257",
				                                "latitude": "41.36323"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 8,
				                                "_total": 8,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZ1e1?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Madison Beer",
				                            "type": "attraction",
				                            "id": "K8vZ917prs7",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/madison-beer-entradas/991303",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UC7zjjW_bvcVsPcBiqUPJf1w"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/madisonbeerhq"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/madison-beer/696146587"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "https://www.last.fm/music/Madison+Beer"
				                                    }
				                                ],
				                                "tiktok": [
				                                    {
				                                        "url": "https://www.tiktok.com/@madisonbeer"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/2kRfqPViCqYdSGhYSM9R0Q"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/MadisonElleBeer"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Madison_Beer"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "749ef494-8518-4b49-b685-63d8e728d25c",
				                                        "url": "https://musicbrainz.org/artist/749ef494-8518-4b49-b685-63d8e728d25c"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://instagram.com/madisonbeer/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://www.madisonbeer.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/dbc/7fe79548-3bd3-473a-b130-56d83b3eddbc_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/81e/f7c54cbc-9973-4984-bdfc-01438885981e_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false,
				                                    "attribution": "Madison Beer Tickets 2026"
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
				                                        "id": "KnvZfZ7vAev",
				                                        "name": "Pop"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vk1t",
				                                        "name": "Pop"
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
				                                "mfx-be": 2,
				                                "mfx-es": 2,
				                                "ticketmaster": 23,
				                                "_total": 27,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917prs7?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Lulu Simon",
				                            "type": "attraction",
				                            "id": "K8vZ917hvZf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/lulu-simon-entradas/1149573",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_SOURCE",
				                                    "width": 2426,
				                                    "height": 1366,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/276/f8ab1e3d-13d6-4dfc-82df-e9418a119276_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
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
				                                        "id": "KnvZfZ7vAev",
				                                        "name": "Pop"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vk1t",
				                                        "name": "Pop"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAde",
				                                        "name": "Cantante"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-be": 1,
				                                "mfx-nl": 1,
				                                "mfx-es": 2,
				                                "ticketmaster": 21,
				                                "_total": 25,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917hvZf?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "Isabel LaRosa",
				                            "type": "attraction",
				                            "id": "K8vZ917hZR0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/isabel-larosa-entradas/1148183",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/5arKwJZEvT5uKq4o0JfqR4"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/isabel.s.larosa/"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "fa22eeda-8db9-4ae5-97c9-6d0b9ceb7cf3",
				                                        "url": "https://musicbrainz.org/artist/fa22eeda-8db9-4ae5-97c9-6d0b9ceb7cf3"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "https://www.isabel-larosa.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_SOURCE",
				                                    "width": 4672,
				                                    "height": 7008,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ce6/18782fdc-a029-4959-844f-996fb4687ce6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
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
				                                        "id": "KnvZfZ7vAev",
				                                        "name": "Pop"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vk1t",
				                                        "name": "Pop"
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
				                                "mfx-be": 1,
				                                "mfx-nl": 1,
				                                "mfx-es": 2,
				                                "ticketmaster": 2,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917hZR0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Elle Coves",
				                "type": "event",
				                "id": "Z698xZ2qZ1k1kFs3b",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/elle-coves-entradas/286231498",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-27T09:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-26T19:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-26",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-26T18:30:00Z",
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
				                    "id": "2727",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2727",
				                        "name": "Live Nation España S.A.U."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1k1kFs3b?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917QiH7?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZdvF1?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 3",
				                            "type": "venue",
				                            "id": "Z598xZ2qZdvF1",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-3-barcelona-entradas/slrazz3bcn/112",
				                            "locale": "es-es",
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZdvF1?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Elle Coves",
				                            "type": "attraction",
				                            "id": "K8vZ917QiH7",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/elle-coves-entradas/1198674",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/46b/c9169394-5033-40fa-884d-57aa5e2f846b_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
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
				                                        "id": "KnvZfZ7vAev",
				                                        "name": "Pop"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vk1t",
				                                        "name": "Pop"
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
				                                "mfx-es": 2,
				                                "ticketmaster": 1,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917QiH7?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "LANY: soft world tour | Paquetes VIP",
				                "type": "event",
				                "id": "Z698xZ2qZ16vZyFP16",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/lany-soft-world-tour-%7C-paquetes-vip-entradas/1039225287",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2025-10-24T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-22T10:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-27",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-27T18:30:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/Madrid",
				                    "status": {
				                        "code": "offsale"
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
				                    "id": "2731",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2731",
				                        "name": "Live Nation España S.A.U."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vZyFP16?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917KQC0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 1",
				                            "type": "venue",
				                            "id": "Z198xZ2qZkeF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-1-barcelona-entradas/slrazz1bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/ce3f944d429901821670fe4766875317.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "C. Almogàvers, 122"
				                            },
				                            "location": {
				                                "longitude": "2.19111",
				                                "latitude": "41.39772"
				                            },
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 19,
				                                "_total": 20,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "LANY",
				                            "type": "attraction",
				                            "id": "K8vZ917KQC0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/lany-entradas/959258",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCBoANX4oFfAyGPjMSUFYrfQ"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/thisislany"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id867853783"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/49tQo2QULno7gxHutgccqF?autoplay=true"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/thisislany"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "f1cc066a-6dd1-4b18-ae68-3708472ab4b1",
				                                        "url": "https://musicbrainz.org/artist/f1cc066a-6dd1-4b18-ae68-3708472ab4b1"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/thisislany/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://thisislany.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
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
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "ticketmaster": 6,
				                                "trium": 1,
				                                "tixcraft-sg": 1,
				                                "mfx-it": 1,
				                                "_total": 13,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917KQC0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "LANY: soft world tour",
				                "type": "event",
				                "id": "Z698xZ2qZ16vvkuvv7",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/lany-soft-world-tour-entradas/1046341044",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2025-10-24T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-27T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-27",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-27T18:30:00Z",
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
				                    "id": "2731",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2731",
				                        "name": "Live Nation España S.A.U."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vvkuvv7?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917KQC0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 1",
				                            "type": "venue",
				                            "id": "Z198xZ2qZkeF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-1-barcelona-entradas/slrazz1bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/ce3f944d429901821670fe4766875317.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "C. Almogàvers, 122"
				                            },
				                            "location": {
				                                "longitude": "2.19111",
				                                "latitude": "41.39772"
				                            },
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 19,
				                                "_total": 20,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "LANY",
				                            "type": "attraction",
				                            "id": "K8vZ917KQC0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/lany-entradas/959258",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCBoANX4oFfAyGPjMSUFYrfQ"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/thisislany"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://itunes.apple.com/us/artist/id867853783"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/49tQo2QULno7gxHutgccqF?autoplay=true"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/thisislany"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "f1cc066a-6dd1-4b18-ae68-3708472ab4b1",
				                                        "url": "https://musicbrainz.org/artist/f1cc066a-6dd1-4b18-ae68-3708472ab4b1"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/thisislany/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://thisislany.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/607/2a949f0e-22e8-4206-b340-3ae40c2a6607_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
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
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "ticketmaster": 6,
				                                "trium": 1,
				                                "tixcraft-sg": 1,
				                                "mfx-it": 1,
				                                "_total": 13,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917KQC0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Ben Howard - Summer 2026",
				                "type": "event",
				                "id": "Z698xZ2qZ16v730t-S",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/ben-howard-summer-2026-entradas/1112929873",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "url": "https://s1.ticketm.net/dam/a/8fd/b23e304f-d802-4fb2-8f87-5992599618fd_SOURCE",
				                        "width": 2426,
				                        "height": 3033,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-06T09:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-28T19:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-28",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-28T18:30:00Z",
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
				                    "id": "2727",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2727",
				                        "name": "Live Nation España S.A.U."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16v730t-S?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917Gi9V?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZdA1?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Apolo",
				                            "type": "venue",
				                            "id": "Z198xZ2qZdA1",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-apolo-barcelona-entradas/slapolobcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/87516ba556575ab86db575fb4ee4b2b8.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08004",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "C. Nou de la Rambla, 113"
				                            },
				                            "location": {
				                                "longitude": "2.16953",
				                                "latitude": "41.37441"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 8,
				                                "_total": 8,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZdA1?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Ben Howard",
				                            "type": "attraction",
				                            "id": "K8vZ917Gi9V",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/ben-howard-entradas/367123",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/user/BenHowardVEVO"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/benhowardmusic"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/gb/artist/ben-howard/432957499"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Ben_Howard"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/benhowardmusic"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/5schNIzWdI9gJ1QRK8SBnc"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "534dda3c-b73f-408b-8889-bd68eae84df6",
				                                        "url": "https://musicbrainz.org/artist/534dda3c-b73f-408b-8889-bd68eae84df6"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "http://instagram.com/bhwrd"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://www.benhowardmusic.co.uk/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "url": "https://s1.ticketm.net/dam/a/8fd/b23e304f-d802-4fb2-8f87-5992599618fd_SOURCE",
				                                    "width": 2426,
				                                    "height": 3033,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/e85/3d325bb0-0568-4f69-80b4-da553dbf1e85_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
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
				                            "upcomingEvents": {
				                                "mfx-dk": 1,
				                                "mfx-nl": 1,
				                                "mfx-es": 1,
				                                "tmr": 3,
				                                "ticketmaster": 4,
				                                "trium": 1,
				                                "crowder": 1,
				                                "_total": 12,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917Gi9V?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Molchat Doma",
				                "type": "event",
				                "id": "Z698xZ2qZ1kFSGwa9",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/molchat-doma-entradas/233332257",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_SOURCE",
				                        "width": 2426,
				                        "height": 1617,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-05T09:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-28T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-05-28T19:00:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-05-28",
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
				                            "id": "KnvZfZ7vAvv",
				                            "name": "Alternativo"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vAvn",
				                            "name": "Rock alternativo"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "2761",
				                    "name": "Sergio Solís Gálvez"
				                },
				                "promoters": [
				                    {
				                        "id": "2761",
				                        "name": "Sergio Solís Gálvez"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kFSGwa9?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917bEp7?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 1",
				                            "type": "venue",
				                            "id": "Z198xZ2qZkeF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-1-barcelona-entradas/slrazz1bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/ce3f944d429901821670fe4766875317.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "C. Almogàvers, 122"
				                            },
				                            "location": {
				                                "longitude": "2.19111",
				                                "latitude": "41.39772"
				                            },
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 19,
				                                "_total": 20,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Molchat Doma",
				                            "type": "attraction",
				                            "id": "K8vZ917bEp7",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/molchat-doma-entradas/1019496",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCK7awvr80upziSM47-h0Odw"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/molchatdomaband"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/molchat-doma/1478740208"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/molchatdomaband/"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/1nVq0hKIVReeaiB3xJgKf0"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "11bcbbf4-1252-496a-b747-06942f3d76db",
				                                        "url": "https://musicbrainz.org/artist/11bcbbf4-1252-496a-b747-06942f3d76db"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/molchatdomaband/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "https://molchatdoma.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_SOURCE",
				                                    "width": 2426,
				                                    "height": 1617,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/de1/dcf23b7c-b56b-45d7-a03d-1f6a96329de1_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
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
				                                        "id": "KZazBEonSMnZfZ7v6Fn",
				                                        "name": "Post-Punk"
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
				                                "mfx-es": 2,
				                                "tmr": 3,
				                                "ticketmaster": 2,
				                                "ticketweb": 3,
				                                "_total": 10,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917bEp7?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "PUBLIC IMAGE LTD - THIS IS NOT THE LAST TOUR",
				                "type": "event",
				                "id": "Z698xZ2qZ1kvte7OZ",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/public-image-ltd-this-is-not-the-last-tour-entradas/69084800",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2025-12-03T10:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-29T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-29",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-05-29T18:30:00Z",
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
				                    "id": "2731",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2731",
				                        "name": "Live Nation España S.A.U."
				                    }
				                ],
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": true
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kvte7OZ?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9171Fz0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 2",
				                            "type": "venue",
				                            "id": "Z198xZ2qZd6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-2-barcelona-entradas/slrazz2bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/cb43e3cca923383dd8a20c4790f2772a.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 9,
				                                "_total": 9,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Public Image Ltd",
				                            "type": "attraction",
				                            "id": "K8vZ9171Fz0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/public-image-ltd-entradas/241",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "http://www.youtube.com/pilofficial"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "http://twitter.com/pilofficial"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/public-image-ltd/20942761"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/70MMkLXtue3Edj3RJhJkYp"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/pilofficial"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/pilofficial"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "https://www.pilofficial.com/"
				                                    }
				                                ]
				                            },
				                            "aliases": [
				                                "PIL"
				                            ],
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/b9d/6d0581e2-435b-4b77-907c-6f1128becb9d_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
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
				                                        "id": "KZazBEonSMnZfZ7v6dt",
				                                        "name": "Rock alternativo"
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
				                            "upcomingEvents": {
				                                "mticket": 1,
				                                "universe": 1,
				                                "mfx-es": 2,
				                                "tmr": 13,
				                                "ticketmaster": 24,
				                                "ticketweb": 1,
				                                "mfx-pl": 1,
				                                "_total": 43,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9171Fz0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Kream by Alvama Ice",
				                "type": "event",
				                "id": "Z698xZ2qZ1k7IbpJp",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/kream-by-alvama-ice-entradas/128616996",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-03T14:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-30T21:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-30",
				                        "localTime": "18:00:00",
				                        "dateTime": "2026-05-30T16:00:00Z",
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
				                            "id": "KnvZfZ7vAvl",
				                            "name": "Otros"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vk1I",
				                            "name": "Otros"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6737",
				                    "name": "Aquí No Hay Silencio, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6737",
				                        "name": "Aquí No Hay Silencio, A.I.E."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1k7IbpJp?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917qdIV?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZ1ae?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Poble Espanyol",
				                            "type": "venue",
				                            "id": "Z198xZ2qZ1ae",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/poble-espanyol-barcelona-entradas/pbleespbcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/cdca72f63efdd263385d722c9114c3a3.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08038",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "P.º de Jean Forestier s/n"
				                            },
				                            "location": {
				                                "longitude": "2.14885",
				                                "latitude": "41.36891"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZ1ae?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Alvama Ice",
				                            "type": "attraction",
				                            "id": "K8vZ917qdIV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/kream-ice-land-by-alvama-ice-entradas/1306223",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/4fd/e852cb0c-980c-4329-b844-3dbf261bb4fd_EVENT_DETAIL_PAGE_16_9.jpg",
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
				                                        "id": "KnvZfZ7vAvF",
				                                        "name": "Dance/Electrónica"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vA1E",
				                                        "name": "Dance/Electrónica"
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
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917qdIV?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Glory",
				                "type": "event",
				                "id": "Z698xZ2qZ16vZVCkC9",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/glory-entradas/1034386397",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-04-23T10:30:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-31T17:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-31",
				                        "localTime": "19:00:00",
				                        "dateTime": "2026-05-31T17:00:00Z",
				                        "dateTBD": false,
				                        "dateTBA": false,
				                        "timeTBA": false,
				                        "noSpecificTime": false
				                    },
				                    "timezone": "Europe/Madrid",
				                    "status": {
				                        "code": "cancelled"
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
				                            "id": "KnvZfZ7vAe6",
				                            "name": "Indefinido"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7v6JI",
				                            "name": "Indefinido"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "3421",
				                    "name": "Matinée 2000, SLU"
				                },
				                "promoters": [
				                    {
				                        "id": "3421",
				                        "name": "Matinée 2000, SLU"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vZVCkC9?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917LdEf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZavde?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Les Enfans Brillants",
				                            "type": "venue",
				                            "id": "Z598xZ2qZavde",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-les-enfans-brillants-barcelona-entradas/slenbribcn/114",
				                            "locale": "es-es",
				                            "postalCode": "08001",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Guàrdia, 3, Ciutat Vella"
				                            },
				                            "location": {
				                                "longitude": "1.55997",
				                                "latitude": "41.39095"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZavde?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Glory",
				                            "type": "attraction",
				                            "id": "K8vZ917LdEf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/glory-entradas/1461183",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/1a6/0394ed57-dd3e-454a-a6a7-7cb86cdf71a6_RETINA_PORTRAIT_3_2.jpg",
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
				                                        "id": "KnvZfZ7vAe6",
				                                        "name": "Indefinido"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v6JI",
				                                        "name": "Indefinido"
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
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917LdEf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Uli Jon Roth",
				                "type": "event",
				                "id": "Z698xZ2qZ16vbSudFp",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/uli-jon-roth-entradas/1633343216",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/96e/4e96ffd2-aeb1-454b-9e29-80d19cc1696e_SOURCE",
				                        "width": 2048,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-01-21T11:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-31T17:00:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-05-31T18:30:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-05-31",
				                        "localTime": "19:00:00",
				                        "dateTime": "2026-05-31T17:00:00Z",
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
				                            "id": "KZazBEonSMnZfZ7v6kl",
				                            "name": "Hard Rock"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "3287",
				                    "name": "Artisti-K Prod. Mus. y Cult, S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "3287",
				                        "name": "Artisti-K Prod. Mus. y Cult, S.L."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vbSudFp?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9175zd0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 2",
				                            "type": "venue",
				                            "id": "Z198xZ2qZd6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-2-barcelona-entradas/slrazz2bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/cb43e3cca923383dd8a20c4790f2772a.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 9,
				                                "_total": 9,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Uli Jon Roth",
				                            "type": "attraction",
				                            "id": "K8vZ9175zd0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/uli-jon-roth-entradas/31351",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCkd4pYkarn1EEg71xxVE-Lg"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/2VoP4JXyxNPIoYAFdB5ssQ"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/ulijonrothofficial"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Uli_Jon_Roth"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "cc533920-6a69-4b14-96ff-27a4cac89365",
				                                        "url": "https://musicbrainz.org/artist/cc533920-6a69-4b14-96ff-27a4cac89365"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://www.ulijonroth.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/96e/4e96ffd2-aeb1-454b-9e29-80d19cc1696e_SOURCE",
				                                    "width": 2048,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/355/e37c3025-845e-4f61-839a-483261ac7355_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
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
				                                        "id": "KnvZfZ7vAvt",
				                                        "name": "Metal"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vkFd",
				                                        "name": "Heavy Metal"
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
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 2,
				                                "trium": 2,
				                                "_total": 5,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9175zd0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Delarue",
				                "type": "event",
				                "id": "Z698xZ2qZ16vCbpdGz",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/delarue-entradas/1398583338",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-04-17T18:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-05T21:00:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-06-05T20:00:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-06-05",
				                        "localTime": "21:00:00",
				                        "dateTime": "2026-06-05T19:00:00Z",
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
				                            "id": "KnvZfZ7vAv1",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vkvl",
				                            "name": "Hip-Hop/Rap"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6147",
				                    "name": "La Sordera, S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "6147",
				                        "name": "La Sordera, S.L."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vCbpdGz?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917LtDV?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 2",
				                            "type": "venue",
				                            "id": "Z198xZ2qZd6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-2-barcelona-entradas/slrazz2bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/cb43e3cca923383dd8a20c4790f2772a.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 9,
				                                "_total": 9,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZd6k?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Delarue",
				                            "type": "attraction",
				                            "id": "K8vZ917LtDV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/delarue-entradas/1400664",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/ea6/5ae70877-e916-4d7f-acff-3a287e9a6ea6_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
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
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917LtDV?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Sasha Velour - Travesty",
				                "type": "event",
				                "id": "Z698xZ2qZ1kC_GOvg",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/sasha-velour-travesty-entradas/399320075",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-01-30T09:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-08T18:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-08",
				                        "localTime": "20:00:00",
				                        "dateTime": "2026-06-08T18:00:00Z",
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
				                            "id": "KZFzniwnSyZfZ7v7na",
				                            "name": "Arte y Teatro"
				                        },
				                        "genre": {
				                            "id": "KnvZfZ7v7l1",
				                            "name": "Teatro"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7v7lt",
				                            "name": "Drama"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "2727",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2727",
				                        "name": "Live Nation España S.A.U."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kC_GOvg?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9179uhf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ77dF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Paral·lel 62",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ77dF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/parallel-62-barcelona-entradas/tebartsbcn/112",
				                            "locale": "es-es",
				                            "postalCode": "08001",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Av. del Paral.lel, 62"
				                            },
				                            "location": {
				                                "longitude": "2.16973",
				                                "latitude": "41.3751"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ77dF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Sasha Velour",
				                            "type": "attraction",
				                            "id": "K8vZ9179uhf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/sasha-velour-entradas/1000364",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3d2/bd219d37-ce79-488b-a26c-7b89168a73d2_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                }
				                            ],
				                            "classifications": [
				                                {
				                                    "primary": true,
				                                    "segment": {
				                                        "id": "KZFzniwnSyZfZ7v7na",
				                                        "name": "Arte y Teatro"
				                                    },
				                                    "genre": {
				                                        "id": "KnvZfZ7v7l6",
				                                        "name": "Arte escénico"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v7l1",
				                                        "name": "Arte escénico"
				                                    },
				                                    "type": {
				                                        "id": "KZAyXgnZfZ7v7la",
				                                        "name": "Individual"
				                                    },
				                                    "subType": {
				                                        "id": "KZFzBErXgnZfZ7vAdd",
				                                        "name": "Artista"
				                                    },
				                                    "family": false
				                                }
				                            ],
				                            "upcomingEvents": {
				                                "mfx-nl": 2,
				                                "mfx-es": 3,
				                                "ticketmaster": 3,
				                                "_total": 8,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9179uhf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "HUMBE",
				                "type": "event",
				                "id": "Z698xZ2qZ1kQ4Gk48",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/humbe-entradas/695306550",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-02-13T11:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-12T19:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-12",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-06-12T18:30:00Z",
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
				                    "id": "6273",
				                    "name": "Versos Musicales en la Noche, A.I.E"
				                },
				                "promoters": [
				                    {
				                        "id": "6273",
				                        "name": "Versos Musicales en la Noche, A.I.E"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kQ4Gk48?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917_mQf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 1",
				                            "type": "venue",
				                            "id": "Z198xZ2qZkeF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-1-barcelona-entradas/slrazz1bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/ce3f944d429901821670fe4766875317.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "C. Almogàvers, 122"
				                            },
				                            "location": {
				                                "longitude": "2.19111",
				                                "latitude": "41.39772"
				                            },
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 19,
				                                "_total": 20,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZkeF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Humbe",
				                            "type": "attraction",
				                            "id": "K8vZ917_mQf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/humbe-entradas/1109963",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/7aa/19a04af2-7a41-45d5-b1aa-cc3fcb55c7aa_EVENT_DETAIL_PAGE_16_9.jpg",
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
				                                        "id": "KnvZfZ7vAve",
				                                        "name": "Baladas/Romántica "
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vAAe",
				                                        "name": "Baladas/Romántica "
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
				                                "mfx-ch": 1,
				                                "mfx-es": 2,
				                                "ticketmaster": 1,
				                                "moshtix": 3,
				                                "trium": 1,
				                                "_total": 8,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917_mQf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "THE WOMBATS - Oh! The Ocean Tour 2026.",
				                "type": "event",
				                "id": "Z698xZ2qZ1kp80a7p",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/the-wombats-oh-the-ocean-tour-2026-entradas/590909116",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/ed2/355f8cdd-f0ac-497f-b6f8-6ce7d4afaed2_SOURCE",
				                        "width": 6720,
				                        "height": 4480,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-04-01T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-12T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-12",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-06-12T18:30:00Z",
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
				                            "id": "KnvZfZ7vAvv",
				                            "name": "Alternativo"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vAvn",
				                            "name": "Rock alternativo"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "6187",
				                    "name": "Live Nation España Red Path SL"
				                },
				                "promoters": [
				                    {
				                        "id": "6187",
				                        "name": "Live Nation España Red Path SL"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kp80a7p?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917GeP0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ77dF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Paral·lel 62",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ77dF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/parallel-62-barcelona-entradas/tebartsbcn/112",
				                            "locale": "es-es",
				                            "postalCode": "08001",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Av. del Paral.lel, 62"
				                            },
				                            "location": {
				                                "longitude": "2.16973",
				                                "latitude": "41.3751"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ77dF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "The Wombats",
				                            "type": "attraction",
				                            "id": "K8vZ917GeP0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/the-wombats-entradas/34297",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/user/TheWOMBATS"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/thewombats"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/the-wombats/162731772"
				                                    }
				                                ],
				                                "lastfm": [
				                                    {
				                                        "url": "http://www.last.fm/music/The+Wombats"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/0Ya43ZKWHTKkAbkoJJkwIB"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/The_Wombats"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/thewombatsuk"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "e105c272-b5d7-4135-82ef-d60bded54345",
				                                        "url": "https://musicbrainz.org/artist/e105c272-b5d7-4135-82ef-d60bded54345"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/wombatsofficial/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://www.thewombats.co.uk/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/ed2/355f8cdd-f0ac-497f-b6f8-6ce7d4afaed2_SOURCE",
				                                    "width": 6720,
				                                    "height": 4480,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/be3/c5bd9092-0672-423e-82d0-d733abb06be3_RETINA_PORTRAIT_3_2.jpg",
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
				                                        "id": "KnvZfZ7vAeA",
				                                        "name": "Rock"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v6dt",
				                                        "name": "Rock alternativo"
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
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 1,
				                                "ticketmaster": 9,
				                                "_total": 11,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917GeP0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "The Dwarves + The Capaces",
				                "type": "event",
				                "id": "Z698xZ2qZ1k3KOyOa",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/the-dwarves--the-capaces-entradas/493839809",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-06T14:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-17T18:00:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-06-17T20:00:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-06-17",
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
				                            "id": "KZazBEonSMnZfZ7v6kl",
				                            "name": "Hard Rock"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "2761",
				                    "name": "Sergio Solís Gálvez"
				                },
				                "promoters": [
				                    {
				                        "id": "2761",
				                        "name": "Sergio Solís Gálvez"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1k3KOyOa?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9175daf?locale=es-br"
				                        },
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917LjZ0?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZ771?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Razzmatazz 3",
				                            "type": "venue",
				                            "id": "Z198xZ2qZ771",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/razzmatazz-3-barcelona-entradas/slrazz3bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/efd30acec47721563320c7cf146f4efc.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "universe": 1,
				                                "mfx-es": 3,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZ771?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Dwarves",
				                            "type": "attraction",
				                            "id": "K8vZ9175daf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/dwarves-entradas/16031",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/@thedwarves"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/thedwarvesband"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/dwarves/13828173"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/4D9H6CaKzDTaN1EbAHypYg?autoplay=true"
				                                    }
				                                ],
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/Dwarves_(band)"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/pages/The-Dwarves/339745519075"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/thedwarves/"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "762e1e6d-ba12-4946-b8ee-dfa0cf9a2655",
				                                        "url": "https://musicbrainz.org/artist/762e1e6d-ba12-4946-b8ee-dfa0cf9a2655"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://www.thedwarves.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/3dd/25c57365-b271-4c6b-8c78-e79fd3d623dd_EVENT_DETAIL_PAGE_16_9.jpg",
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
				                                        "id": "KnvZfZ7vAeA",
				                                        "name": "Rock"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v6dt",
				                                        "name": "Rock alternativo"
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
				                            "upcomingEvents": {
				                                "tmr": 2,
				                                "mfx-nl": 1,
				                                "mfx-es": 1,
				                                "ticketmaster": 3,
				                                "ticketweb": 2,
				                                "_total": 9,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9175daf?locale=es-br"
				                                }
				                            }
				                        },
				                        {
				                            "name": "The Capaces",
				                            "type": "attraction",
				                            "id": "K8vZ917LjZ0",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/the-capaces-entradas/1489383",
				                            "locale": "es-br",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": true
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/c/fbc/b293c0ad-c904-4215-bc59-8d7f2414dfbc_106141_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": true
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
				                                        "id": "KZazBEonSMnZfZ7v6a6",
				                                        "name": "Punk"
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
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917LjZ0?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Kany García",
				                "type": "event",
				                "id": "Z698xZ2qZ16vaVZ6fd",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/kany-garcia-entradas/1274007503",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/5fa/9605ce23-339c-4879-b374-011541b825fa_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2025-12-11T11:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-18T19:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-18",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-06-18T18:30:00Z",
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
				                    "id": "6149",
				                    "name": "JI Entertainment, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6149",
				                        "name": "JI Entertainment, A.I.E."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vaVZ6fd?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917KB97?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZe6d7?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Palau Sant Jordi",
				                            "type": "venue",
				                            "id": "Z598xZ2qZe6d7",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/palau-sant-jordi-barcelona-entradas/plsjordbcn/112",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/49fc8b0a5c71033fdb6a79005f35d615.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08038",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Passeig Olímpic, 5-7"
				                            },
				                            "location": {
				                                "longitude": "2.15259",
				                                "latitude": "41.36337"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 26,
				                                "_total": 26,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZe6d7?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Kany Garcia",
				                            "type": "attraction",
				                            "id": "K8vZ917KB97",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/kany-garcia-entradas/965012",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCc2ZilH94OFrGRaVKwh75tA"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/kanygarcia"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/kany-garcía/251432197"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/kanygarcia/"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/69UypehHabb68utzfjAVlV"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "b73433e9-24bd-4446-bf0c-40826bd48b44",
				                                        "url": "https://musicbrainz.org/artist/b73433e9-24bd-4446-bf0c-40826bd48b44"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/kanygarcia/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "https://www.kanygarcia.com"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/5fa/9605ce23-339c-4879-b374-011541b825fa_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/53c/85c813bf-a1bc-42a0-86de-b1b1513a053c_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
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
				                                        "id": "KnvZfZ7vAev",
				                                        "name": "Pop"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vk1t",
				                                        "name": "Pop"
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
				                            "upcomingEvents": {
				                                "mfx-es": 5,
				                                "tmr": 3,
				                                "ticketmaster": 17,
				                                "crowder": 1,
				                                "_total": 26,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917KB97?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Culture Wars - 2026 World Tour",
				                "type": "event",
				                "id": "Z698xZ2qZ16v7pfS-_",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/culture-wars-2026-world-tour-entradas/1116533859",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-03-31T10:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-26T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-26",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-06-26T18:30:00Z",
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
				                    "id": "6187",
				                    "name": "Live Nation España Red Path SL"
				                },
				                "promoters": [
				                    {
				                        "id": "6187",
				                        "name": "Live Nation España Red Path SL"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16v7pfS-_?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917phpV?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZdvF1?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Razzmatazz 3",
				                            "type": "venue",
				                            "id": "Z598xZ2qZdvF1",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-razzmatazz-3-barcelona-entradas/slrazz3bcn/112",
				                            "locale": "es-es",
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Pamplona, 88"
				                            },
				                            "location": {
				                                "longitude": "2.19147",
				                                "latitude": "41.39701"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZdvF1?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Culture Wars",
				                            "type": "attraction",
				                            "id": "K8vZ917phpV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/culture-wars-entradas/990380",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCbS05TA8lEpb-LwlEQnveLA"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/CultureWars_"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/0DoNSZa1R1DV69oY1djlbf"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/culturewarslive/"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/culturewars_"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://culturewars.io/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/94d/1ed1921b-5e46-4e3a-85f2-da759f3e494d_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
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
				                                "mfx-dk": 1,
				                                "mfx-at": 1,
				                                "universe": 2,
				                                "tmr": 4,
				                                "mfx-es": 2,
				                                "ticketmaster": 10,
				                                "moshtix": 2,
				                                "mfx-pl": 1,
				                                "_total": 23,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917phpV?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Emel",
				                "type": "event",
				                "id": "Z698xZ2qZ16v8t7I4x",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/emel-entradas/1429128562",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-01-30T10:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-07-01T20:00:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-07-01T20:00:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-07-01",
				                        "localTime": "21:00:00",
				                        "dateTime": "2026-07-01T19:00:00Z",
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
				                            "id": "KnvZfZ7vAeF",
				                            "name": "World"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7v6Jt",
				                            "name": "World"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "3385",
				                    "name": "Houston Party, SL"
				                },
				                "promoters": [
				                    {
				                        "id": "3385",
				                        "name": "Houston Party, SL"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16v8t7I4x?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917o6Gf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZdAaF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Sala Upload - Poble Espanyol",
				                            "type": "venue",
				                            "id": "Z598xZ2qZdAaF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/sala-upload-poble-espanyol-barcelona-entradas/sluploabcn/114",
				                            "locale": "es-es",
				                            "postalCode": "08100",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Av Francesc Ferrer i Guardia, 13"
				                            },
				                            "location": {
				                                "longitude": "2.14823",
				                                "latitude": "41.3688"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZdAaF?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Emel Mathlouthi",
				                            "type": "attraction",
				                            "id": "K8vZ917o6Gf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/emel-entradas/921559",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UC5MtqxMFa_ZDnnnGh9xEBmg"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/MathlouthiEmel"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/emel-mathlouthi/348291725"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://facebook.com/envato"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/06MtOym27ALcfdtVOsRcaA"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/emelmathlouthi/"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "https://emelmathlouthi.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/c08/618ed523-8b3e-47ef-a8d8-100d6cad6c08_EVENT_DETAIL_PAGE_16_9.jpg",
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
				                                        "id": "KnvZfZ7vAeF",
				                                        "name": "World"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7v6Jt",
				                                        "name": "World"
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
				                            "upcomingEvents": {
				                                "mfx-es": 2,
				                                "_total": 2,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917o6Gf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "Curtis Harding",
				                "type": "event",
				                "id": "Z698xZ2qZ16v0Ob4u9",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/curtis-harding-entradas/1920615357",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-02-05T11:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-07-03T19:30:00Z"
				                    }
				                },
				                "dates": {
				                    "access": {
				                        "startDateTime": "2026-07-03T19:00:00Z",
				                        "startApproximate": false,
				                        "endApproximate": false
				                    },
				                    "start": {
				                        "localDate": "2026-07-03",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-07-03T18:30:00Z",
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
				                            "id": "KnvZfZ7vAee",
				                            "name": "R&B"
				                        },
				                        "subGenre": {
				                            "id": "KZazBEonSMnZfZ7vknE",
				                            "name": "Soul"
				                        },
				                        "family": false
				                    }
				                ],
				                "promoter": {
				                    "id": "3385",
				                    "name": "Houston Party, SL"
				                },
				                "promoters": [
				                    {
				                        "id": "3385",
				                        "name": "Houston Party, SL"
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ16v0Ob4u9?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ9173jBf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZF11?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "La 2 de Apolo",
				                            "type": "venue",
				                            "id": "Z198xZ2qZF11",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/la-2-de-apolo-barcelona-entradas/slapol2bcn/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/6b64f47a24fe1459e4931d454af1baf4.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "08004",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer Nou de la Rambla, 113"
				                            },
				                            "location": {
				                                "longitude": "2.16953",
				                                "latitude": "41.37441"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 6,
				                                "_total": 6,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZF11?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "Curtis Harding",
				                            "type": "attraction",
				                            "id": "K8vZ9173jBf",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/curtis-harding-entradas/943566",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "youtube": [
				                                    {
				                                        "url": "https://www.youtube.com/channel/UCz6eN_EJr4izMWmWPVNBRxg"
				                                    }
				                                ],
				                                "twitter": [
				                                    {
				                                        "url": "https://twitter.com/Curtis_Harding"
				                                    }
				                                ],
				                                "itunes": [
				                                    {
				                                        "url": "https://music.apple.com/us/artist/curtis-harding/354623326"
				                                    }
				                                ],
				                                "spotify": [
				                                    {
				                                        "url": "https://open.spotify.com/artist/0CUpzKPDfIVzYqMn47jiV3"
				                                    }
				                                ],
				                                "facebook": [
				                                    {
				                                        "url": "https://www.facebook.com/1392287757658766"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/curtisharding/"
				                                    }
				                                ],
				                                "musicbrainz": [
				                                    {
				                                        "id": "3381882a-9e5a-44f9-b383-89759edf1dbd",
				                                        "url": "https://musicbrainz.org/artist/3381882a-9e5a-44f9-b383-89759edf1dbd"
				                                    }
				                                ],
				                                "homepage": [
				                                    {
				                                        "url": "http://www.curtisharding.com/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/145/25eef757-a9ac-4e50-be5c-44f71e9f9145_RETINA_PORTRAIT_3_2.jpg",
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
				                                        "id": "KnvZfZ7vAee",
				                                        "name": "R&B"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vknE",
				                                        "name": "Soul"
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
				                            "upcomingEvents": {
				                                "mfx-es": 2,
				                                "mfx-de": 1,
				                                "mfx-no": 1,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ9173jBf?locale=es-br"
				                                }
				                            }
				                        }
				                    ]
				                }
				            },
				            {
				                "name": "December 10 - The Next Step",
				                "type": "event",
				                "id": "Z698xZ2qZ1kF9fovE",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/december-10-the-next-step-entradas/217511067",
				                "locale": "es-es",
				                "images": [
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RETINA_PORTRAIT_16_9.jpg",
				                        "width": 640,
				                        "height": 360,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_EVENT_DETAIL_PAGE_16_9.jpg",
				                        "width": 205,
				                        "height": 115,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RETINA_LANDSCAPE_16_9.jpg",
				                        "width": 1136,
				                        "height": 639,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_TABLET_LANDSCAPE_16_9.jpg",
				                        "width": 1024,
				                        "height": 576,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "4_3",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_CUSTOM.jpg",
				                        "width": 305,
				                        "height": 225,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_ARTIST_PAGE_3_2.jpg",
				                        "width": 305,
				                        "height": 203,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_SOURCE",
				                        "width": 2426,
				                        "height": 1365,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_TABLET_LANDSCAPE_3_2.jpg",
				                        "width": 1024,
				                        "height": 683,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                        "width": 2048,
				                        "height": 1152,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "3_2",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RETINA_PORTRAIT_3_2.jpg",
				                        "width": 640,
				                        "height": 427,
				                        "fallback": false
				                    },
				                    {
				                        "ratio": "16_9",
				                        "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RECOMENDATION_16_9.jpg",
				                        "width": 100,
				                        "height": 56,
				                        "fallback": false
				                    }
				                ],
				                "sales": {
				                    "public": {
				                        "startDateTime": "2026-04-17T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-07-03T18:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-07-03",
				                        "localTime": "20:30:00",
				                        "dateTime": "2026-07-03T18:30:00Z",
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
				                    "id": "2727",
				                    "name": "Live Nation España S.A.U."
				                },
				                "promoters": [
				                    {
				                        "id": "2727",
				                        "name": "Live Nation España S.A.U."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kF9fovE?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917LvlV?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZkdk1?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "WOLF",
				                            "type": "venue",
				                            "id": "Z598xZ2qZkdk1",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/wolf-barcelona-entradas/slwolfbcn/114",
				                            "locale": "es-es",
				                            "postalCode": "08018",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barcelona"
				                            },
				                            "state": {
				                                "name": "Barcelona"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer dels Almogàvers, 88"
				                            },
				                            "location": {
				                                "longitude": "2.18859",
				                                "latitude": "41.39589"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 7,
				                                "_total": 7,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZkdk1?locale=es-es"
				                                }
				                            }
				                        }
				                    ],
				                    "attractions": [
				                        {
				                            "name": "December 10",
				                            "type": "attraction",
				                            "id": "K8vZ917LvlV",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/artist/december-10-entradas/1455523",
				                            "locale": "es-br",
				                            "externalLinks": {
				                                "wiki": [
				                                    {
				                                        "url": "https://en.wikipedia.org/wiki/December_10_(band)"
				                                    }
				                                ],
				                                "instagram": [
				                                    {
				                                        "url": "https://www.instagram.com/december10/"
				                                    }
				                                ]
				                            },
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RETINA_PORTRAIT_16_9.jpg",
				                                    "width": 640,
				                                    "height": 360,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_EVENT_DETAIL_PAGE_16_9.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RETINA_LANDSCAPE_16_9.jpg",
				                                    "width": 1136,
				                                    "height": 639,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_TABLET_LANDSCAPE_16_9.jpg",
				                                    "width": 1024,
				                                    "height": 576,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "4_3",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_CUSTOM.jpg",
				                                    "width": 305,
				                                    "height": 225,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_ARTIST_PAGE_3_2.jpg",
				                                    "width": 305,
				                                    "height": 203,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_SOURCE",
				                                    "width": 2426,
				                                    "height": 1365,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_TABLET_LANDSCAPE_3_2.jpg",
				                                    "width": 1024,
				                                    "height": 683,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_TABLET_LANDSCAPE_LARGE_16_9.jpg",
				                                    "width": 2048,
				                                    "height": 1152,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "3_2",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RETINA_PORTRAIT_3_2.jpg",
				                                    "width": 640,
				                                    "height": 427,
				                                    "fallback": false
				                                },
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://s1.ticketm.net/dam/a/eb8/5fe31c0b-1df5-4b85-8e33-fe83fcfbaeb8_RECOMENDATION_16_9.jpg",
				                                    "width": 100,
				                                    "height": 56,
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
				                                        "id": "KnvZfZ7vAev",
				                                        "name": "Pop"
				                                    },
				                                    "subGenre": {
				                                        "id": "KZazBEonSMnZfZ7vk1t",
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
				                                "mticket": 1,
				                                "universe": 1,
				                                "mfx-es": 4,
				                                "ticketmaster": 8,
				                                "mfx-de": 2,
				                                "trium": 1,
				                                "_total": 17,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/attractions/K8vZ917LvlV?locale=es-br"
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
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&city=Barcelona&includeTBA=no&locale=es&includeTBD=no&page=0&size=20&sort=date,asc"
				        },
				        "self": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&city=Barcelona&includeTBA=no&sort=date%2Casc&locale=es&includeTBD=no"
				        },
				        "next": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&city=Barcelona&includeTBA=no&locale=es&includeTBD=no&page=1&size=20&sort=date,asc"
				        },
				        "last": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&city=Barcelona&includeTBA=no&locale=es&includeTBD=no&page=5&size=20&sort=date,asc"
				        }
				    },
				    "page": {
				        "size": 20,
				        "totalElements": 118,
				        "totalPages": 6,
				        "number": 0
				    }
				}
												""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&city=Barcelona"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		List<ConcertResponseDTO> list = searchService.searchConcerts(dto);
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ16vGPFe4K", "Madison Beer: the locket tour",
				LocalDate.of(2026, 05, 26),
				"https://www.ticketmaster.es/event/madison-beer-the-locket-tour-entradas/1325202553", "Madison Beer", "Rock",
				new VenueDTO("Sant Jordi Club", 41.36323, 2.15257, "Passeig Olímpic, 5-7, 08038, Barcelona",
						"Barcelona", "España"));

		assertEquals(19, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid artist and one valid date.")
	void artistAndOneDate() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		String artist = "El Último de la Fila";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, artist, "");

		String result = """
														{
				    "_embedded": {
				        "events": [
				            {
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kJvuvbv",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601",
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
				                        "startDateTime": "2025-05-29T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-30T19:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-30",
				                        "localTime": "21:30:00",
				                        "dateTime": "2026-05-30T19:30:00Z",
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
				                    "id": "6291",
				                    "name": "BERRIA MUSIK EXP., S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "6291",
				                        "name": "BERRIA MUSIK EXP., S.L."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/7b44c11eefc27268bb69f98e402130f2.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kJvuvbv?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Bizkaia Arena - BEC!",
				                            "type": "venue",
				                            "id": "Z198xZ2qZk6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/bizkaia-arena-bec-barakaldo-entradas/becarenbar/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/8f671719abb5956ae35b5ad45191dc56.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barakaldo"
				                            },
				                            "state": {
				                                "name": "Bizkaia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Rda. de Azkue, 1"
				                            },
				                            "location": {
				                                "longitude": "-2.98869",
				                                "latitude": "43.29087"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kuMq97E",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/377757127",
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
				                        "startDateTime": "2025-05-30T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-05T21:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-05",
				                        "localTime": "21:30:00",
				                        "dateTime": "2026-06-05T19:30:00Z",
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
				                    "id": "6483",
				                    "name": "Great Live, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6483",
				                        "name": "Great Live, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/d25c0a9c6be7a3e7deda18cbf14c94b6.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kuMq97E?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Bizkaia Arena - BEC!",
				                            "type": "venue",
				                            "id": "Z198xZ2qZk6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/bizkaia-arena-bec-barakaldo-entradas/becarenbar/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/8f671719abb5956ae35b5ad45191dc56.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barakaldo"
				                            },
				                            "state": {
				                                "name": "Bizkaia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Rda. de Azkue, 1"
				                            },
				                            "location": {
				                                "longitude": "-2.98869",
				                                "latitude": "43.29087"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kQ3-eFf",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/692842214",
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
				                        "startDateTime": "2026-04-29T09:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-13T20:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-13",
				                        "localTime": "22:30:00",
				                        "dateTime": "2026-06-13T20:30:00Z",
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
				                    "id": "6291",
				                    "name": "BERRIA MUSIK EXP., S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "6291",
				                        "name": "BERRIA MUSIK EXP., S.L."
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
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kQ3-eFf?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ7Fve?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Monte do Gozo",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ7Fve",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/monte-do-gozo-santiago-de-compostela-entradas/montedosdc/113",
				                            "locale": "es-es",
				                            "postalCode": "15820",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Santiago de Compostela"
				                            },
				                            "state": {
				                                "name": "A Coruña"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Rúa do Gozo, 18"
				                            },
				                            "location": {
				                                "longitude": "-8.50014",
				                                "latitude": "42.88672"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ7Fve?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kGPaEAo",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/325267151",
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
				                        "startDateTime": "2025-05-29T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-20T20:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-20",
				                        "localTime": "22:30:00",
				                        "dateTime": "2026-06-20T20:30:00Z",
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
				                    "id": "6581",
				                    "name": "Euforia Sonora, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6581",
				                        "name": "Euforia Sonora, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/90615ab10f14c9ecdafad2188e361190.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kGPaEAo?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZFaeF?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Exterior Pabellón la Magdalena",
				                            "type": "venue",
				                            "id": "Z598xZ2qZFaeF",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/exterior-pabellon-la-magdalena-aviles-entradas/expamagavs/104",
				                            "locale": "es-es",
				                            "postalCode": "33403",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Avilés"
				                            },
				                            "state": {
				                                "name": "Asturias"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Piqueros de Abajo, 27"
				                            },
				                            "location": {
				                                "longitude": "-5.92255",
				                                "latitude": "43.54422"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 1,
				                                "_total": 1,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZFaeF?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ16vC6pkrf",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/1387586794",
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
				                        "startDateTime": "2025-05-29T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-27T20:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-27",
				                        "localTime": "22:30:00",
				                        "dateTime": "2026-06-27T20:30:00Z",
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
				                    "id": "6579",
				                    "name": "La Última Canción, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6579",
				                        "name": "La Última Canción, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/a0798c4333ca6f611fdeeb801d92f7d9.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ16vC6pkrf?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZ1a1?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Estadio La Cartuja de Sevilla",
				                            "type": "venue",
				                            "id": "Z198xZ2qZ1a1",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/estadio-la-cartuja-de-sevilla-sevilla-entradas/escartusev/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/0c8f8f34a6cb2db431e132247c659fe6.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "postalCode": "41092",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Sevilla"
				                            },
				                            "state": {
				                                "name": "Sevilla"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Isla de la Cartuja, sector norte"
				                            },
				                            "location": {
				                                "longitude": "-6.00722",
				                                "latitude": "37.39861"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 4,
				                                "_total": 4,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZ1a1?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1k--Z3Z-",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/861012021",
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
				                        "startDateTime": "2025-05-29T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-07-04T20:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-07-04",
				                        "localTime": "22:30:00",
				                        "dateTime": "2026-07-04T20:30:00Z",
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
				                    "id": "6355",
				                    "name": "Gira Berria Musik, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6355",
				                        "name": "Gira Berria Musik, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/9959c003a7912ff976861ee0a37f7e31.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1k--Z3Z-?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ6v7F?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Estadi Ciutat de València",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ6v7F",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/estadi-ciutat-de-valencia-valencia-entradas/esciutaval/114",
				                            "locale": "es-es",
				                            "postalCode": "46019",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Valencia"
				                            },
				                            "state": {
				                                "name": "Valencia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Sant Vicent de Paül, 44"
				                            },
				                            "location": {
				                                "longitude": "-0.3647",
				                                "latitude": "39.49489"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ6v7F?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kupqkat",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/356746269",
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
				                        "startDateTime": "2025-05-30T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-07-09T20:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-07-09",
				                        "localTime": "22:30:00",
				                        "dateTime": "2026-07-09T20:30:00Z",
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
				                    "id": "6483",
				                    "name": "Great Live, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6483",
				                        "name": "Great Live, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/8b01c63e858f0d6f094c4c7a798a34e6.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kupqkat?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z598xZ2qZ6v7F?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Estadi Ciutat de València",
				                            "type": "venue",
				                            "id": "Z598xZ2qZ6v7F",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/estadi-ciutat-de-valencia-valencia-entradas/esciutaval/114",
				                            "locale": "es-es",
				                            "postalCode": "46019",
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Valencia"
				                            },
				                            "state": {
				                                "name": "Valencia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Carrer de Sant Vicent de Paül, 44"
				                            },
				                            "location": {
				                                "longitude": "-0.3647",
				                                "latitude": "39.49489"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z598xZ2qZ6v7F?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&includeTBA=no&sort=date%2Casc&locale=es&keyword=El+%C3%9Altimo+de+la+Fila&includeTBD=no"
				        }
				    },
				    "page": {
				        "size": 20,
				        "totalElements": 7,
				        "totalPages": 1,
				        "number": 0
				    }
				}
																""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&keyword=El%20%C3%9Altimo%20de%20la%20Fila"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		List<ConcertResponseDTO> list = searchService.searchConcerts(dto);
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ1kJvuvbv", "El Último de la Fila",
				LocalDate.of(2026, 05, 30), "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601",
				"El Último de la Fila", "Rock", new VenueDTO("Bizkaia Arena - BEC!", 43.29087, -2.98869,
						"Rda. de Azkue, 1, null, Barakaldo", "Bizkaia", "España"));

		assertEquals(7, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service makes the correct call when introducing a valid artist, a valid city and one valid date.")
	void artistAndCityAndOneDate() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		String artist = "El Último de la Fila";
		String city = "Barakaldo";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, artist, city);

		String result = """
																		{
				    "_embedded": {
				        "events": [
				            {
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kJvuvbv",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601",
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
				                        "startDateTime": "2025-05-29T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-30T19:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-30",
				                        "localTime": "21:30:00",
				                        "dateTime": "2026-05-30T19:30:00Z",
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
				                    "id": "6291",
				                    "name": "BERRIA MUSIK EXP., S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "6291",
				                        "name": "BERRIA MUSIK EXP., S.L."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/7b44c11eefc27268bb69f98e402130f2.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kJvuvbv?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Bizkaia Arena - BEC!",
				                            "type": "venue",
				                            "id": "Z198xZ2qZk6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/bizkaia-arena-bec-barakaldo-entradas/becarenbar/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/8f671719abb5956ae35b5ad45191dc56.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barakaldo"
				                            },
				                            "state": {
				                                "name": "Bizkaia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Rda. de Azkue, 1"
				                            },
				                            "location": {
				                                "longitude": "-2.98869",
				                                "latitude": "43.29087"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kuMq97E",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/377757127",
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
				                        "startDateTime": "2025-05-30T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-05T21:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-05",
				                        "localTime": "21:30:00",
				                        "dateTime": "2026-06-05T19:30:00Z",
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
				                    "id": "6483",
				                    "name": "Great Live, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6483",
				                        "name": "Great Live, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/d25c0a9c6be7a3e7deda18cbf14c94b6.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kuMq97E?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Bizkaia Arena - BEC!",
				                            "type": "venue",
				                            "id": "Z198xZ2qZk6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/bizkaia-arena-bec-barakaldo-entradas/becarenbar/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/8f671719abb5956ae35b5ad45191dc56.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barakaldo"
				                            },
				                            "state": {
				                                "name": "Bizkaia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Rda. de Azkue, 1"
				                            },
				                            "location": {
				                                "longitude": "-2.98869",
				                                "latitude": "43.29087"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&city=Barakaldo&includeTBA=no&sort=date%2Casc&locale=es&keyword=El+%C3%9Altimo+de+la+Fila&includeTBD=no"
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
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&city=Barakaldo&keyword=El%20%C3%9Altimo%20de%20la%20Fila"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		List<ConcertResponseDTO> list = searchService.searchConcerts(dto);
		ConcertResponseDTO firstResult = new ConcertResponseDTO("Z698xZ2qZ1kJvuvbv", "El Último de la Fila",
				LocalDate.of(2026, 05, 30), "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601",
				"El Último de la Fila", "Rock", new VenueDTO("Bizkaia Arena - BEC!", 43.29087, -2.98869,
						"Rda. de Azkue, 1, null, Barakaldo", "Bizkaia", "España"));

		assertEquals(2, list.size());
		assertEquals(list.getFirst(), firstResult);
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service returns an empty list when a search has no results")
	void noResultSearch() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		String artist = "El Último de la Fila";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, artist, "");

		String result = """
								{
				    "_links": {
				        "self": {
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&includeTBA=no&sort=date%2Casc&locale=es&endDateTime=2026-05-24T00%3A00%3A00Z&keyword=El+%C3%9Altimo+de+la+Fila&includeTBD=no"
				        }
				    },
				    "page": {
				        "size": 20,
				        "totalElements": 0,
				        "totalPages": 0,
				        "number": 0
				    }
				}
								""";

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&keyword=El%20%C3%9Altimo%20de%20la%20Fila"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		Exception e = assertThrows(ResourceNotFoundException.class, () -> searchService.searchConcerts(dto));
		assertEquals("Búsqueda sin resultados", e.getMessage());
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service returns an empty list when null field is present in the result")
	void nullFieldSearch() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		String artist = "El Último de la Fila";
		String city = "Barkaldo";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, artist, city);

		String result = """
												{
				    "_embedded": {
				        "events": [
				            {
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kJvuvbv",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/981341601",
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
				                        "startDateTime": "2025-05-29T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-05-30T19:30:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-05-30",
				                        "localTime": "21:30:00",
				                        "dateTime": "2026-05-30T19:30:00Z",
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
				                    "id": "6291",
				                    "name": "BERRIA MUSIK EXP., S.L."
				                },
				                "promoters": [
				                    {
				                        "id": "6291",
				                        "name": "BERRIA MUSIK EXP., S.L."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/7b44c11eefc27268bb69f98e402130f2.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kJvuvbv?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				                "name": "El Último de la Fila",
				                "type": "event",
				                "id": "Z698xZ2qZ1kuMq97E",
				                "test": false,
				                "url": "https://www.ticketmaster.es/event/el-ultimo-de-la-fila-entradas/377757127",
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
				                        "startDateTime": "2025-05-30T08:00:00Z",
				                        "startTBD": false,
				                        "startTBA": false,
				                        "endDateTime": "2026-06-05T21:00:00Z"
				                    }
				                },
				                "dates": {
				                    "start": {
				                        "localDate": "2026-06-05",
				                        "localTime": "21:30:00",
				                        "dateTime": "2026-06-05T19:30:00Z",
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
				                    "id": "6483",
				                    "name": "Great Live, A.I.E."
				                },
				                "promoters": [
				                    {
				                        "id": "6483",
				                        "name": "Great Live, A.I.E."
				                    }
				                ],
				                "seatmap": {
				                    "staticUrl": "https://media.ticketmaster.eu/spain/d25c0a9c6be7a3e7deda18cbf14c94b6.jpg"
				                },
				                "ticketing": {
				                    "safeTix": {
				                        "enabled": false
				                    }
				                },
				                "nameOrigin": "custom",
				                "_links": {
				                    "self": {
				                        "href": "/discovery/v2/events/Z698xZ2qZ1kuMq97E?locale=es-es"
				                    },
				                    "attractions": [
				                        {
				                            "href": "/discovery/v2/attractions/K8vZ917r1Yf?locale=es-br"
				                        }
				                    ],
				                    "venues": [
				                        {
				                            "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
				                        }
				                    ]
				                },
				                "_embedded": {
				                    "venues": [
				                        {
				                            "name": "Bizkaia Arena - BEC!",
				                            "type": "venue",
				                            "id": "Z198xZ2qZk6k",
				                            "test": false,
				                            "url": "https://www.ticketmaster.es/venue/bizkaia-arena-bec-barakaldo-entradas/becarenbar/114",
				                            "locale": "es-es",
				                            "images": [
				                                {
				                                    "ratio": "16_9",
				                                    "url": "https://media.ticketmaster.eu/spain/8f671719abb5956ae35b5ad45191dc56.jpg",
				                                    "width": 205,
				                                    "height": 115,
				                                    "fallback": false
				                                }
				                            ],
				                            "timezone": "Europe/Madrid",
				                            "city": {
				                                "name": "Barakaldo"
				                            },
				                            "state": {
				                                "name": "Bizkaia"
				                            },
				                            "country": {
				                                "name": "España",
				                                "countryCode": "ES"
				                            },
				                            "address": {
				                                "line1": "Rda. de Azkue, 1"
				                            },
				                            "location": {
				                                "longitude": "-2.98869",
				                                "latitude": "43.29087"
				                            },
				                            "upcomingEvents": {
				                                "mfx-es": 3,
				                                "_total": 3,
				                                "_filtered": 0
				                            },
				                            "_links": {
				                                "self": {
				                                    "href": "/discovery/v2/venues/Z198xZ2qZk6k?locale=es-es"
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
				                                "mfx-es": 8,
				                                "_total": 8,
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
				            "href": "/discovery/v2/events.json?startDateTime=2026-05-22T00%3A00%3A00Z&size=20&city=Barakaldo&includeTBA=no&sort=date%2Casc&locale=es&keyword=El+%C3%9Altimo+de+la+Fila&includeTBD=no"
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
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&city=Barkaldo&keyword=El%20%C3%9Altimo%20de%20la%20Fila"))
				.andRespond(MockRestResponseCreators.withSuccess(result, MediaType.APPLICATION_JSON));

		Exception e = assertThrows(ResourceNotFoundException.class, () -> searchService.searchConcerts(dto));
		assertEquals("Búsqueda sin resultados", e.getMessage());
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service throws a HttpServerErrorException if the conection to TicketMaster fails")
	void serverError() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		String artist = "El Último de la Fila";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, artist, "");

		mockServer.expect(MockRestRequestMatchers.requestTo(
				"https://app.ticketmaster.com/discovery/v2/events.json?apikey=uDn8Th1Hg0DWXaInqHaQ2gQ7s1VJ0Ru9&locale=es&startDateTime=2026-05-22T00:00:00Z&includeTBA=no&includeTBD=no&size=20&sort=date,asc&keyword=El%20%C3%9Altimo%20de%20la%20Fila"))
				.andRespond(MockRestResponseCreators.withServerError());

		HttpServerErrorException e = assertThrows(HttpServerErrorException.class,
				() -> searchService.searchConcerts(dto));
		assertTrue(e.getStatusCode().is5xxServerError());
		mockServer.verify();
	}

	@Test
	@DisplayName("Tests if service throws a IllegalArgumentException if the startDate is null")
	void badRequestDateError() {
		String artist = "El Último de la Fila";
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(null, null, artist, "");

		Exception e = assertThrows(IllegalArgumentException.class, () -> searchService.searchConcerts(dto));
		assertEquals("Los parámetros de búsqueda no son válidos. Debe haber fecha y artista y/o ciudad.",
				e.getMessage());
	}

	@Test
	@DisplayName("Tests if service throws a IllegalArgumentException if the artist and the city are null")
	void badRequestCityArtistError() {
		LocalDateTime startDate = LocalDateTime.of(2026, 05, 22, 00, 00, 00);
		ConcertSearchRequestDTO dto = new ConcertSearchRequestDTO(startDate, null, null, "");

		Exception e = assertThrows(IllegalArgumentException.class, () -> searchService.searchConcerts(dto));
		assertEquals("Los parámetros de búsqueda no son válidos. Debe haber fecha y artista y/o ciudad.",
				e.getMessage());
	}

}
