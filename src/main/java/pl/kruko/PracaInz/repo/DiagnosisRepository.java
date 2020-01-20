package pl.kruko.PracaInz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Diagnosis;
import pl.kruko.PracaInz.models.Status;
import pl.kruko.PracaInz.models.Visit;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

	List<Diagnosis> findByVisit(Visit visit);

	List<Diagnosis> findByVisitAndName(Visit visit, String name);	
	
	List<Diagnosis> findByVisitAndStatus(Visit visit, Status status);

}
