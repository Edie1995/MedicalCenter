package pl.kruko.PracaInz.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
	Optional<Medicament> findById(Long id);

	List<Medicament> findByName(String name);

}
