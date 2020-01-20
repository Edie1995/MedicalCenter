package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.MedicamentDTO;
import pl.kruko.PracaInz.dataTransferObjects.PatientDTO;
import pl.kruko.PracaInz.dataTransferObjects.PatientsMedicamentDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitDTO;
import pl.kruko.PracaInz.models.Medicament;
import pl.kruko.PracaInz.models.PatientsMedicament;
import pl.kruko.PracaInz.models.Status;
import pl.kruko.PracaInz.models.Visit;
import pl.kruko.PracaInz.repo.PatientsMedicamentRepository;

@Service
public class PatientsMedicamentService {

	private VisitService visitService;
	private MedicamentService medicamentService;
	private PatientsMedicamentRepository patientsMedicamentRepository;
	private ModelMapper modelMapper = new ModelMapper();
	private Type listType = new TypeToken<List<PatientsMedicamentDTO>>() {
	}.getType();

	@Autowired
	public PatientsMedicamentService(VisitService visitService, MedicamentService medicamentService,
			PatientsMedicamentRepository patientsMedicamentRepository) {
		super();
		this.visitService = visitService;
		this.medicamentService = medicamentService;
		this.patientsMedicamentRepository = patientsMedicamentRepository;
	}

	public PatientsMedicamentDTO findById(Long id) {
		PatientsMedicament patientsMedicament = patientsMedicamentRepository.findById(id)
				.orElse(new PatientsMedicament());
		PatientsMedicamentDTO patientsMedicamentDTO = modelMapper.map(patientsMedicament, PatientsMedicamentDTO.class);
		return patientsMedicamentDTO;
	}

	public List<PatientsMedicamentDTO> findByStatusAndPatient(Integer statusNumber, String login) {
		Status status = getStatus(statusNumber);
		List<Visit> visits = getVisit(login);
		List<PatientsMedicament> patientsMedicaments = new ArrayList<>();
		for (Visit v : visits) {
			patientsMedicaments.addAll(patientsMedicamentRepository.findByVisitAndStatus(v, status));
		}
		List<PatientsMedicamentDTO> patientsMedicamentDTO = modelMapper.map(patientsMedicaments, listType);
		Collections.sort(patientsMedicamentDTO);
		return patientsMedicamentDTO;
	}

	public List<Visit> getVisit(String login) {
		List<Visit> visits = visitService.findByPatient(login);
		return visits;
	}

	public Status getStatus(Integer statusNumber) {
		Status status;
		if (statusNumber == 0) {
			status = Status.ACTIVE;
		} else {
			status = Status.INACTIVE;
		}
		return status;
	}

	public List<Medicament> getMedicaments(String medicamentName) {
		List<Medicament> medicament = medicamentService.findByName(medicamentName);
		return medicament;
	}

	public List<PatientsMedicamentDTO> findByMedicamentsAndPatient(String medicamentName, String login) {
		List<Visit> visits = getVisit(login);
		List<PatientsMedicament> patientsMedicaments = new ArrayList<>();
		List<Medicament> medicaments = getMedicaments(medicamentName);
		for (Visit v : visits) {
			for (Medicament medicament : medicaments) {
				patientsMedicaments.addAll(patientsMedicamentRepository.findByVisitAndMedicament(v, medicament));
			}
		}
		List<PatientsMedicamentDTO> patientMedicamentDTO = modelMapper.map(patientsMedicaments, listType);
		return patientMedicamentDTO;

	}

	public List<PatientsMedicamentDTO> findAllByPatient(String login) {
		List<Visit> visits = getVisit(login);
		List<PatientsMedicament> patientsMedicaments = new ArrayList<>();
		for (Visit v : visits) {
			patientsMedicaments.addAll(patientsMedicamentRepository.findByVisit(v));
		}
		List<PatientsMedicament> updateMedicament = new ArrayList<>();
		for (PatientsMedicament med : patientsMedicaments) {
			if (med.getEndTime().isBefore(LocalDate.now()) && med.getStatus().equals(Status.ACTIVE)) {
				med.setStatus(Status.INACTIVE);
				updateMedicament.add(med);
			}
		}
		patientsMedicamentRepository.saveAll(updateMedicament);
		List<PatientsMedicamentDTO> patientsMedicamentDTO = modelMapper.map(patientsMedicaments, listType);
		Collections.sort(patientsMedicamentDTO);
		return patientsMedicamentDTO;
	}

	public List<PatientsMedicamentDTO> findAllByPatientDTO(PatientDTO patientDTO) {
		return findAllByPatient(patientDTO.getUser().getLogin());
	}

	public int save(MedicamentDTO medicamentDTO, int dosage, int frequency, VisitDTO visitDTO, String endDateString) {
		LocalDate date = LocalDate.parse(endDateString);
		PatientsMedicamentDTO patientMedicamentDTO = new PatientsMedicamentDTO(dosage, frequency, visitDTO,
				medicamentDTO, Status.ACTIVE, date);
		try {
			patientsMedicamentRepository.save(modelMapper.map(patientMedicamentDTO, PatientsMedicament.class));
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}

	}

}
