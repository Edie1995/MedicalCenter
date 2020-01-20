package pl.kruko.PracaInz.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.PatientSymptom;
import pl.kruko.PracaInz.models.Symptom;

public interface PatientSymptomRepository extends JpaRepository<PatientSymptom, Long> {
	Optional<PatientSymptom> findById(Long id);

	List<PatientSymptom> findAllByPatientAndSymptom(Patient patient, Symptom symptom);

	List<PatientSymptom> findAllByPatientAndDate(Patient patient, LocalDate date);

	List<PatientSymptom> findAllByPatient(Patient patient);

	@Query("SELECT p FROM PatientSymptom p WHERE  p.patient = :patient and (:symptom is null or p.symptom = :symptom) and (:date is null or p.date = :date)" )
	List<PatientSymptom> findAllByPatientAndSymptomAndDate(Patient patient, Symptom symptom, LocalDate date);
	
	Optional<PatientSymptom> findByIdAndPatient(Long id, Patient patient);
	
	List<PatientSymptom> findByPatientAndDateBetween(Patient patient, LocalDate dateStart, LocalDate dateEnd);
	
	List<PatientSymptom> findByPatientAndSymptomAndDateBetween(Patient patient, Symptom symptom, LocalDate dateStart, LocalDate dateEnd);
	

}
