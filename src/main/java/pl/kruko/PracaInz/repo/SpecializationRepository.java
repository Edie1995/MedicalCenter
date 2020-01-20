package pl.kruko.PracaInz.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Specialization;


public interface SpecializationRepository extends JpaRepository<Specialization, Long>{
	
	Specialization findByName(String name);

}
