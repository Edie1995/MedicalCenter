package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.DoctorDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorsCalendarDTO;
import pl.kruko.PracaInz.dataTransferObjects.InstitutionDTO;
import pl.kruko.PracaInz.dataTransferObjects.PatientDTO;
import pl.kruko.PracaInz.dataTransferObjects.ScheduledVisitDTO;
import pl.kruko.PracaInz.dataTransferObjects.ScheduledVisitToCalendarDTO;
import pl.kruko.PracaInz.dataTransferObjects.ScheduledVisitsWithDatesDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeDTO;
import pl.kruko.PracaInz.models.Doctor;
import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.ScheduledVisit;
import pl.kruko.PracaInz.repo.ScheduledVisitRepository;

@Service
public class ScheduledVisitService {

	private PatientService patientService;
	private DoctorService doctorService;
	private DoctorsCalendarService doctorsCalendarService;
	private ScheduledVisitRepository scheduledVisitRepository;

	private ModelMapper modelMapper = new ModelMapper();
	private Type listType = new TypeToken<List<ScheduledVisitDTO>>() {
	}.getType();

	@Autowired
	public ScheduledVisitService(PatientService patientService, DoctorService doctorService,
			DoctorsCalendarService doctorsCalendarService, ScheduledVisitRepository scheduledVisitRepository) {
		super();
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.doctorsCalendarService = doctorsCalendarService;
		this.scheduledVisitRepository = scheduledVisitRepository;
	}

	public List<ScheduledVisitToCalendarDTO> findByPatient(String login) {
		Patient patient = getPatient(login);
		List<ScheduledVisit> scheduledVisits = scheduledVisitRepository.findByPatient(patient);
		Type listType = new TypeToken<List<ScheduledVisitToCalendarDTO>>() {
		}.getType();
		List<ScheduledVisitToCalendarDTO> scheduledVisitDTO = modelMapper.map(scheduledVisits, listType);
		Collections.sort(scheduledVisitDTO);
		return scheduledVisitDTO;
	}

	public List<ScheduledVisitDTO> findByPatientAndDate(String login, LocalDateTime date) {
		Patient patient = getPatient(login);
		List<ScheduledVisit> scheduledVisits = scheduledVisitRepository.findByPatientAndDate(patient, date);
		List<ScheduledVisitDTO> scheduledVisitDTO = modelMapper.map(scheduledVisits, listType);
		return scheduledVisitDTO;
	}

	public Patient getPatient(String login) {
		Patient patient = patientService.findByUser(login);
		return patient;
	}

	public int addNewScheduledVisit(PatientDTO patient, VisitTypeDTO visitType, InstitutionDTO institution,
			DoctorDTO doctor, LocalDateTime dateTime) {
		ScheduledVisitToCalendarDTO scheduledVisitDTO = new ScheduledVisitToCalendarDTO(dateTime, visitType, doctor,
				institution, patient);
		ScheduledVisit scheduledVisit = modelMapper.map(scheduledVisitDTO, ScheduledVisit.class);
		scheduledVisit.setVisit(null);
		try {
			scheduledVisitRepository.save(scheduledVisit);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}

	}

	public int addNewEvent(String login, DoctorsCalendarDTO doctorCalendar) {
		Patient patient = getPatient(login);
		PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
		int success = addNewScheduledVisit(patientDTO, doctorCalendar.getVisitType(), doctorCalendar.getInstitution(),
				doctorCalendar.getDoctor(), doctorCalendar.getDate());
		if (success == 1) {
			return 1;
		}
		success = doctorsCalendarService.addPatientToDoctorCalendar(login, doctorCalendar);
		if (success == 1) {
			return 1;
		}
		return 0;

	}

	public List<LocalDate> getDates(List<ScheduledVisitToCalendarDTO> scheduledVisitDTO) {
		List<LocalDate> dates = new ArrayList<LocalDate>();
		for (ScheduledVisitToCalendarDTO sV : scheduledVisitDTO) {
			if (!dates.contains(sV.getDate().toLocalDate())) {
				if (sV.getDate().toLocalDate().plusDays(1).atStartOfDay().isAfter(LocalDateTime.now()))
					dates.add(sV.getDate().toLocalDate());
			}
		}
		return dates;
	}

	public List<ScheduledVisitDTO> findByDoctorAndDate(String login, LocalDate dateString) {
		LocalDateTime dateStart = dateString.atStartOfDay();
		LocalDateTime dateEnd = dateString.plusDays(1).atStartOfDay();
		Doctor doctor = doctorService.findByUser(login);
		List<ScheduledVisit> scheduledVisits = scheduledVisitRepository.findByDoctorAndDateBetween(doctor, dateStart,
				dateEnd);
		List<ScheduledVisitDTO> scheduledVisitsDTO = modelMapper.map(scheduledVisits, listType);
		Collections.sort(scheduledVisitsDTO);
		return scheduledVisitsDTO;
	}

	public void setAndSaveVisit(ScheduledVisit scheduledVisit) {
		scheduledVisitRepository.save(scheduledVisit);
	}

	public List<LocalDateTime> getDatesTimes(String login) {
		Patient patient = getPatient(login);
		List<ScheduledVisit> scheduledVisits = scheduledVisitRepository.findByPatient(patient);
		List<LocalDateTime> dates = new ArrayList<LocalDateTime>();
		for (ScheduledVisit sV : scheduledVisits) {
			dates.add(sV.getDate());
		}
		return dates;
	}

	public ScheduledVisitsWithDatesDTO findScheduledWisitAndDates(String login) {
		ScheduledVisitsWithDatesDTO scheduledVisitsWithDatesDTO = new ScheduledVisitsWithDatesDTO();
		List<ScheduledVisitToCalendarDTO> scheduledVisitDTOs = findByPatient(login);
		List<LocalDate> localDates = getDates(scheduledVisitDTOs);
		scheduledVisitsWithDatesDTO.setDates(localDates);
		scheduledVisitsWithDatesDTO.setScheduledVisitDTO(scheduledVisitDTOs);
		return scheduledVisitsWithDatesDTO;
	}

}
