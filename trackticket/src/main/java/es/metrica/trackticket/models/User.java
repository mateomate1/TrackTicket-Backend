package es.metrica.trackticket.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUser;

	@Column(name = "user_name", unique = true, nullable = false)
	private String userName;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "user_session", unique = true)
	private String userSession;

	@ManyToMany
	@JoinTable(
			name = "users_favourite_artists",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_artist"))
	private List<Artist> favouriteArtists = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(
			name = "users_favourite_concerts",
			joinColumns = @JoinColumn(name = "id_user"),
			inverseJoinColumns = @JoinColumn(name = "id_concert"))
	private List<Concert> favouriteConcerts = new ArrayList<>();

	public User(Long idUser, String userName, String email, String password) {
		this.idUser = idUser;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public User(String userName, String email, String password ) {
		setUserName(userName);
		setEmail(email);
		setPassword(password);
	}
	
	protected User() {
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserSession() {
		return userSession;
	}
	
	public void setUserSession(String token) {
		this.userSession = token;
	}
}
