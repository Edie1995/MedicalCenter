package pl.kruko.PracaInz.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.User;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	Patient findByMail(String mail);
	Patient findByPesel(Long pesel);
	Optional<Patient> findById(Long id);
	Patient findByUser(User user);
}
