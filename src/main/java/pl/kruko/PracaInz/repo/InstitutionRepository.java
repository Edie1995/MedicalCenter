package pl.kruko.PracaInz.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.kruko.PracaInz.models.Institution;
import pl.kruko.PracaInz.models.Status;

public interface InstitutionRepository extends JpaRepository<Institution, Long>{

	Optional<Institution> findById(Long id);	
	
	@Query("SELECT i FROM Institution i WHERE (:name is null or i.name = :name) and (:city is null or i.city = :city) and (:status is null or i.status = :status)" )
	List<Institution> findByNameAndCityAndStatus(String name, String city, Status status);
	
	@Query("SELECT i FROM Institution i  WHERE (:city is null or i.city = :city) and (:status is null or i.status = :status)" )
	List<Institution> findByCityAndStatus( String city, Status status);
	
}
