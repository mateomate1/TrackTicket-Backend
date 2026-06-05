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
@Table(name = "addresses")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name= "id_address")
	private Long idAddress;
	
	@Column(name = "first_line", nullable = false)
	private String firstLine;
	
	@Column (name = "second_line")
	private String secondLine;
	
	@Column (name = "zip_code", nullable = false)
	private String zipCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "id_city", nullable = false)
	private City city;
	
	public Address(Long idAddress, String firstLine, String secondLine, String zipCode, City city) {
		this.idAddress = idAddress;
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.zipCode = zipCode;
		this.city = city;
	}
	
	public Address(String firstLine, String secondLine, String zipCode, City city) {
		this.firstLine = firstLine;
		this.secondLine = secondLine;
		this.zipCode = zipCode;
		this.city = city;
	}

	public Address() {
	}

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public String getSecondLine() {
		return secondLine;
	}

	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
