package es.metrica.trackticket.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "locations")
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id_location")
	private Long idLocation;
	
	@Column (name = "latitude", nullable = false)
	private double latitude;
	
	@Column (name = "longitude", nullable = false)
	private double longitude;
	
	@Column (name = "state")
	private String state;

	@Column (name = "country") 
	private String country;
	
	public Location(Long idLocation, double latitude, double longitude) {
		this.idLocation = idLocation;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Location() {}

	public Long getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(Long idLocation) {
		this.idLocation = idLocation;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}	
	
	public String getState() { 
		return state;
	}

	public void setState(String state) { 
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) { 
		this.country = country;
	}
}
