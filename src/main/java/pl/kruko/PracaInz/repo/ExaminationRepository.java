package pl.kruko.PracaInz.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Examination;
import pl.kruko.PracaInz.models.Patient;

public interface ExaminationRepository extends JpaRepository<Examination, Long>{
	List<Examination> findByPatient(Patient patient);
	Optional<Examination> findById(Long id);

}
