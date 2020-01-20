package pl.kruko.PracaInz.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.kruko.PracaInz.models.Doctor;
import pl.kruko.PracaInz.models.Specialization;
import pl.kruko.PracaInz.models.User;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

	Optional<Doctor> findById (Long id);
	
	@Query("SELECT d FROM Doctor d  WHERE (:lastName is null or d.lastName =:lastName) and (:specialization is null or d.specialization = :specialization)")
	List<Doctor> findByNameAndSpecialization(String lastName, Specialization specialization);
	List<Doctor> findByLastName(String lastName);
	List<Doctor> findAll();
	Doctor findByUser(User user);
}
