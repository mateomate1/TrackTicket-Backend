package es.metrica.trackticket.services;

public interface ScheduledSearchService {
	
	void deletePastConcerts();
	
	void searchForFavouriteConcerts();
	
	void searchForNewConcerts();

}
