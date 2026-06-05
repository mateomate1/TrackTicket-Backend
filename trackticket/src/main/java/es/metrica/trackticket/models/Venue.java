package es.metrica.trackticket.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "venues")
public class Venue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id_venue")
	private Long idVenue;
	
	@Column (name = "venue_name", nullable = false)
	private String venueName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_location")
	private Location venueLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_address")
	private Address venueAddress;
	
	public Venue() {}

	public Venue(Long idVenue, String venueName, Location venueLocation, Address venueAddress) {
		this.idVenue = idVenue;
		this.venueName = venueName;
		this.venueLocation = venueLocation;
		this.venueAddress = venueAddress;
	}
	
	public Venue(String venueName, Location venueLocation, Address venueAddress) {
		this.venueName = venueName;
		this.venueLocation = venueLocation;
		this.venueAddress = venueAddress;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public Location getVenueLocation() {
		return venueLocation;
	}

	public void setVenueLocation(Location venueLocation) {
		this.venueLocation = venueLocation;
	}

	public Address getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(Address venueAddress) {
		this.venueAddress = venueAddress;
	}	
}
