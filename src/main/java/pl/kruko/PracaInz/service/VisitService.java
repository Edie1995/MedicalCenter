package pl.kruko.PracaInz.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.DoctorForSearchDTO;
import pl.kruko.PracaInz.dataTransferObjects.PatientDTO;
import pl.kruko.PracaInz.dataTransferObjects.ScheduledVisitDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitDTOAndSuccess;
import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.ScheduledVisit;
import pl.kruko.PracaInz.models.Visit;
import pl.kruko.PracaInz.repo.VisitRepository;

@Service
public class VisitService {

	private VisitRepository visitRepository;
	private PatientService patientService;
	private DoctorService doctorService;
	private ScheduledVisitService scheduledVisitService;

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public VisitService(VisitRepository visitRepository, PatientService patientService, DoctorService doctorService,
			ScheduledVisitService scheduledVisitService) {
		super();
		this.visitRepository = visitRepository;
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.scheduledVisitService = scheduledVisitService;
	}

	public List<Visit> findByPatient(String login) {
		Patient patient = getPatient(login);
		List<Visit> visits = visitRepository.findByPatient(patient);
		return visits;
	}

	public VisitDTOAndSuccess saveWithScheduled(String login, ScheduledVisitDTO scheduledVisitDTO) {
		VisitDTO visitDTO = createVisitForDTO(login, scheduledVisitDTO);
		VisitDTOAndSuccess visitDTOAndSuccess = new VisitDTOAndSuccess();
		ScheduledVisit schedVisit = modelMapper.map(scheduledVisitDTO, ScheduledVisit.class);
		try {
			Visit visit = visitRepository.save(modelMapper.map(visitDTO, Visit.class));
			schedVisit.setVisit(visit);
			scheduledVisitService.setAndSaveVisit(schedVisit);
			visitDTOAndSuccess.setSuccess(visit.getId());
			visitDTOAndSuccess.setVisitDTO(visitDTO);
			return visitDTOAndSuccess;
		} catch (DataIntegrityViolationException e) {
			return null;
		}
	}

	public Patient getPatient(String login) {
		Patient patient = patientService.findByUser(login);
		return patient;
	}

	public List<Visit> findByPatientDTO(PatientDTO patientDTO) {
		Patient patient = modelMapper.map(patientDTO, Patient.class);
		List<Visit> visits = visitRepository.findByPatient(patient);
		return visits;
	}

	public VisitDTO createVisitForDTO(String login, ScheduledVisitDTO scheduledVisit) {
		DoctorForSearchDTO doctorDTO = doctorService.findDTObyUser(login);
		VisitDTO visitDTO = new VisitDTO();
		visitDTO.setDoctor(doctorDTO);
		visitDTO.setPatient(scheduledVisit.getPatient());
		visitDTO.setDate(scheduledVisit.getDate());
		return visitDTO;
	}
}
