package pl.kruko.PracaInz.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Medicament;
import pl.kruko.PracaInz.models.PatientsMedicament;
import pl.kruko.PracaInz.models.Status;
import pl.kruko.PracaInz.models.Visit;

public interface PatientsMedicamentRepository extends JpaRepository<PatientsMedicament, Long> {

	Optional<PatientsMedicament> findById(Long id);

	List<PatientsMedicament> findByVisitAndStatus(Visit visit, Status status);
	
	List<PatientsMedicament> findByVisitAndMedicament(Visit visit, Medicament medicament);
	
	List<PatientsMedicament> findByVisit(Visit visit);
	
}
