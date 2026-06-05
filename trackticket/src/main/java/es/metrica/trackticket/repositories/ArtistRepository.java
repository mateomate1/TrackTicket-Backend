package es.metrica.trackticket.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.metrica.trackticket.models.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>{

	Optional<Artist> findByExternalIdArtist(String externalIdArtist);
	
	List<Artist> findByartistNameStartingWith(String artistName);

	List<Artist> findByMusicGenre(String genre);
	
}
