package pl.kruko.PracaInz.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Symptom;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {

	Optional<Symptom> findById(Long id);
	Symptom findByName(String name);
}
