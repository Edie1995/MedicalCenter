package pl.kruko.PracaInz.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	List<Visit> findByPatient(Patient patient);
	List<Visit> findByPatientAndDate(Patient patient, LocalDate date);
	List<Visit> findByPatientAndDateBetween(Patient patient, LocalDate startDate, LocalDate endDate);
}
