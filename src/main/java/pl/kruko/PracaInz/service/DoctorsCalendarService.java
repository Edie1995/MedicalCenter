package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.DoctorDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorsCalendarDTO;
import pl.kruko.PracaInz.dataTransferObjects.InstitutionDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeForSearchDTO;
import pl.kruko.PracaInz.models.Doctor;
import pl.kruko.PracaInz.models.DoctorsCalendar;
import pl.kruko.PracaInz.models.Institution;
import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.Status;
import pl.kruko.PracaInz.models.VisitType;
import pl.kruko.PracaInz.repo.DoctorsCalendarRepository;

@Service
public class DoctorsCalendarService {

	private DoctorsCalendarRepository doctorsCalendarRepository;
	private DoctorService doctorService;
	private InstitutionService institutionService;
	private PatientService patientService;
	private ModelMapper modelMapper = new ModelMapper();
	private Type listType = new TypeToken<List<DoctorsCalendarDTO>>() {
	}.getType();

	@Autowired
	public DoctorsCalendarService(DoctorsCalendarRepository doctorsCalendarRepository, DoctorService doctorService,
			InstitutionService institutionService, PatientService patientService) {
		super();
		this.doctorsCalendarRepository = doctorsCalendarRepository;
		this.doctorService = doctorService;
		this.institutionService = institutionService;
		this.patientService = patientService;
	}

	public List<DoctorsCalendarDTO> findByDoctorAndDateAndTypeAndCity(String doctorName, LocalDate date,
			VisitTypeForSearchDTO visitTypeDTO, String city, int visitExam) {
		VisitType visitType = modelMapper.map(visitTypeDTO, VisitType.class);
		LocalDate date2 = date.plusDays(7);
		LocalDateTime dateTime1 = date.atStartOfDay();
		LocalDateTime dateTime2 = date2.atStartOfDay();
		List<Institution> institutions = institutionService.findByCityAndStatus(city, Status.ACTIVE);
		List<DoctorsCalendar> calendars = new ArrayList<DoctorsCalendar>();

		List<Doctor> doctors;
		if (visitExam == 1) {
			doctors = doctorService.findByNameAndSpecialization(doctorName, visitType.getName(), city);
		} else {
			doctors = doctorService.findByNameAndSpecialization(doctorName, null, city);
		}

		if (institutions.isEmpty() && doctors.isEmpty()) {
			calendars = doctorsCalendarRepository.findByDoctorAndDateTimeBetweenAndTypeAndInstitutionAndPatient(null,
					dateTime1, dateTime2, visitType, null);
		} else if (institutions.isEmpty()) {
			for (Doctor d : doctors) {
				calendars.addAll(
						doctorsCalendarRepository.findByDoctorAndDateTimeBetweenAndTypeAndInstitutionAndPatient(d,
								dateTime1, dateTime2, visitType, null));
			}
		} else if (doctors.isEmpty()) {
			for (Institution i : institutions) {
				calendars.addAll(
						doctorsCalendarRepository.findByDoctorAndDateTimeBetweenAndTypeAndInstitutionAndPatient(null,
								dateTime1, dateTime2, visitType, i));
			}
		} else {
			for (Doctor d : doctors) {
				for (Institution i : institutions) {
					calendars.addAll(
							doctorsCalendarRepository.findByDoctorAndDateTimeBetweenAndTypeAndInstitutionAndPatient(d,
									dateTime1, dateTime2, visitType, i));
				}
			}
		}
		return modelMapper.map(calendars, listType);

	}

	// OK
	public Patient getPatient(String login) {
		Patient patient = patientService.findByUser(login);
		return patient;
	}

	public int addPatientToDoctorCalendar(String login, DoctorsCalendarDTO doctorCalendarDTO) {
		Patient patient = patientService.findByUser(login);
		DoctorsCalendar doctorsCalendar = modelMapper.map(doctorCalendarDTO, DoctorsCalendar.class);
		doctorsCalendar.setPatient(patient);
		try {
			doctorsCalendarRepository.save(doctorsCalendar);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}

	}

	// OK
	public List<LocalDate> getDates(List<DoctorsCalendarDTO> doctorsCalendarDTO) {
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for (DoctorsCalendarDTO dC : doctorsCalendarDTO) {
			if (!dates.contains(dC.getDate().toLocalDate())) {
				dates.add(dC.getDate().toLocalDate());
			}
		}
		return dates;
	}

	// OK
	public List<DoctorsCalendarDTO> findByDoctorandDate(String login, LocalDate date) {
		Doctor doctor = doctorService.findByUser(login);
		LocalDateTime localTime = date.atStartOfDay();
		LocalDateTime localTime2 = date.plusDays(1).atStartOfDay();
		List<DoctorsCalendar> doctorCalendars = doctorsCalendarRepository.findByDoctorAndDateTimeBetween(doctor,
				localTime, localTime2);
		return modelMapper.map(doctorCalendars, listType);
	}

	// OK
	public void addNewScheduleToDoctor(String login, String date, String timeStart, String timeEnd, String duration,
			String visitTypeId, InstitutionDTO institution) {
		VisitTypeDTO visitType = new VisitTypeDTO();
		visitType.setId(Long.parseLong(visitTypeId));
		LocalDate localDate = LocalDate.parse(date);
		LocalTime localTimeStart = LocalTime.parse(timeStart);
		LocalTime localTimeEnd = LocalTime.parse(timeEnd);
		LocalTime localDuration = LocalTime.parse(duration);
		DoctorDTO doctorDTO = doctorService.findDTObyUser(login);

		List<DoctorsCalendarDTO> doctorCalendars = new ArrayList<>();
		for (LocalTime time1 = localTimeStart; time1.isBefore(localTimeEnd); time1 = time1
				.plusHours(localDuration.getHour()).plusMinutes(localDuration.getMinute())) {
			LocalDateTime dateTime = LocalDateTime.of(localDate, time1);
			DoctorsCalendarDTO doctorCalendar = new DoctorsCalendarDTO(dateTime, doctorDTO, institution, visitType);
			doctorCalendars.add(doctorCalendar);
		}
		Type listType = new TypeToken<List<DoctorsCalendar>>() {
		}.getType();
		List<DoctorsCalendar> dC = modelMapper.map(doctorCalendars, listType);
		doctorsCalendarRepository.saveAll(dC);
	}

}
