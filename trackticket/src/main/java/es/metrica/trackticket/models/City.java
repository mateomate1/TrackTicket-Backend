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
@Table (name = "cities")
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_city")
	private Long idCity;
	
	@Column (name = "city_name", nullable = false)
	private String cityName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_state", nullable = false)
	private State state;
	
	protected City() {}
	
	public City(Long idCity, String cityName, State state) {
		this.idCity = idCity;
		this.cityName = cityName;
		this.state = state;
	}
	
	public Long getIdCity() {
		return idCity;
	}
	public void setIdCity(Long idCity) {
		this.idCity = idCity;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
}
