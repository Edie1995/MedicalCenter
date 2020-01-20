package pl.kruko.PracaInz.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Doctor;
import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.ScheduledVisit;
import pl.kruko.PracaInz.models.VisitType;

public interface ScheduledVisitRepository extends JpaRepository<ScheduledVisit, Long> {
	List<ScheduledVisit> findByPatient(Patient patient);
	List<ScheduledVisit> findByPatientAndDate(Patient patient, LocalDateTime date);
	List<ScheduledVisit> findByPatientAndVisitType(Patient patient, VisitType type);
	List<ScheduledVisit> findByDoctorAndDateBetween(Doctor doctor, LocalDateTime dateStart, LocalDateTime dateEnd);
}
