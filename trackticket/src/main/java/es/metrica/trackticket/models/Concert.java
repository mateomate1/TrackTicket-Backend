package es.metrica.trackticket.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "concerts")
public class Concert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_concert")
	private Long idConcert;

	@Column(name = "external_id_concert", unique = true, nullable = false)
	private String externalIdConcert;

	@Column(name = "concert_date", nullable = false)
	private LocalDateTime concertDate;

	@Column(name = "sell_link")
	private String sellLink;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_venue")
	private Venue venue;

	@ManyToMany
	@JoinTable(name = "concert_artists", joinColumns = @JoinColumn(name = "id_concert"), inverseJoinColumns = @JoinColumn(name = "id_artist"))
	private List<Artist> artists;

	@ManyToMany(mappedBy = "favouriteConcerts")
	private List<User> users;

	protected Concert() {
	}

	public Concert(String externalIdConcert, LocalDateTime concertDate, String sellLink, Venue venue) {
		super();
		this.externalIdConcert = externalIdConcert;
		this.concertDate = concertDate;
		this.sellLink = sellLink;
		this.venue = venue;
		this.artists = new ArrayList<>();
		this.users = new ArrayList<>();
	}

	
	public String getexternalIdConcert() {
		return externalIdConcert;
	}
	public Long getIdConcert() {
		return idConcert;
	}

	public void setIdConcert(Long idConcert) {
		this.idConcert = idConcert;
	}

	public LocalDateTime getConcertDate() {
		return concertDate;
	}

	public void setConcertDate(LocalDateTime concertDate) {
		this.concertDate = concertDate;
	}

	public String getSellLink() {
		return sellLink;
	}

	public void setSellLink(String sellLink) {
		this.sellLink = sellLink;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
		
	}
	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

}
