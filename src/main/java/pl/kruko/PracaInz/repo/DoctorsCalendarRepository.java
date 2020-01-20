package pl.kruko.PracaInz.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.kruko.PracaInz.models.Doctor;
import pl.kruko.PracaInz.models.DoctorsCalendar;
import pl.kruko.PracaInz.models.Institution;
import pl.kruko.PracaInz.models.VisitType;

public interface DoctorsCalendarRepository extends JpaRepository<DoctorsCalendar, Long> {

	List<DoctorsCalendar> findByDoctor(Doctor doctor);


	List<DoctorsCalendar> findByDoctorAndDateTimeBetween(Doctor doctor, LocalDateTime startDate, LocalDateTime endDate);


	@Query("SELECT d FROM DoctorCalendar d WHERE (:doctor is null or d.doctor = :doctor) "
			+ "and (dateTime BETWEEN :date1 and :date2) and d.visitType=:visitType "
			+ "and (:institution is null or d.institution = :institution) and (d.patient=null)")
	List<DoctorsCalendar> findByDoctorAndDateTimeBetweenAndTypeAndInstitutionAndPatient(Doctor doctor, LocalDateTime date1,
			LocalDateTime date2, VisitType visitType, Institution institution);

}
