package es.metrica.trackticket.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_artist")
	private Long idArtist;

	@Column(name = "external_id_artist", unique = true, nullable = false)
	private String externalIdArtist;

	@Column(name = "artist_name", nullable = false)
	private String artistName;

	@Column(name = "music_genre")
	private String musicGenre;

	@Column(name = "artist_image_url")
	private String artistImageUrl;

	@Column(name = "spotify_link")
	private String spotifyLink;

	@Column(name = "playlist_link")
	private String playlistLink;

	@ElementCollection
	@CollectionTable(name = "artist_albums", joinColumns = @JoinColumn(name = "id_artist"))
	@Column(name = "album_name")
	private List<String> albums;

	@ManyToMany(mappedBy = "favouriteArtists")
	private List<User> users;

	@ManyToMany(mappedBy = "artists")
	private List<Concert> concerts;
	
	@ElementCollection
	@CollectionTable(name = "artist_known_concerts", joinColumns = @JoinColumn(name = "id_artist"))
	@Column(name = "ticketmaster_concert_id")
	private List<String> knownConcerts;

	protected Artist() {
	}

	public Artist(String externalIdArtist, String artistName) {
		this.externalIdArtist = externalIdArtist;
		this.artistName = artistName;
		this.users = new ArrayList<>();
		this.concerts = new ArrayList<>();
		this.knownConcerts = new ArrayList<>();
	}

	public Long getIdArtist() {
		return idArtist;
	}

	public void setIdArtist(Long idArtist) {
		this.idArtist = idArtist;
	}

	public String getExternalIdArtist() {
		return externalIdArtist;
	}

	public void setExternalIdArtist(String externalIdArtist) {
		this.externalIdArtist = externalIdArtist;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getMusicGenre() {
		return musicGenre;
	}

	public void setMusicGenre(String musicGenre) {
		this.musicGenre = musicGenre;
	}

	public String getSpotifyLink() {
		return spotifyLink;
	}

	public void setSpotifyLink(String spotifyLink) {
		this.spotifyLink = spotifyLink;
	}

	public String getArtistImageUrl() {
		return this.artistImageUrl;
	}

	public void setArtistImageUrl(String artistImageUrl) {
		this.artistImageUrl = artistImageUrl;
	}

	public String getPlaylistLink() {
		return playlistLink;
	}

	public void setPlaylistLink(String playlistLink) {
		this.playlistLink = playlistLink;
	}

	public List<String> getAlbums() {
		return albums;
	}

	public void setAlbums(List<String> albums) {
		this.albums = albums;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Concert> getConcerts() {
		return concerts;
	}

	public void setConcerts(List<Concert> concerts) {
		this.concerts = concerts;
	}

	public List<String> getKnownConcerts() {
		return knownConcerts;
	}

	public void setKnownConcerts(List<String> knownConcerts) {
		this.knownConcerts = knownConcerts;
	}
	
	
}
