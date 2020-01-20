package pl.kruko.PracaInz.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pl.kruko.PracaInz.dataTransferObjects.ExaminationWithoutDataDTO;
import pl.kruko.PracaInz.dataTransferObjects.PatientDTO;
import pl.kruko.PracaInz.models.Examination;
import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.repo.ExaminationRepository;

@Service
public class ExaminationService {

	private ExaminationRepository examinationRepository;
	private PatientService patientService;
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public ExaminationService(ExaminationRepository examinationRepository, PatientService patientService) {
		super();
		this.examinationRepository = examinationRepository;
		this.patientService = patientService;
	}

	public void save(Examination examination) {
		examinationRepository.save(examination);
	}

	public int saveExamination(String login, ExaminationWithoutDataDTO examinationDTO, MultipartFile file) {
		Examination examination = modelMapper.map(examinationDTO, Examination.class);
		try {
			byte[] blob = file.getInputStream().readAllBytes();

			examination.setFilename(file.getOriginalFilename());
			examination.setContent(blob);
			examination.setContentType(file.getContentType());
			examination.setCreated(LocalDate.now());
			examination.setPatient(patientService.findByUser(login));
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		try {
			save(examination);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	public List<ExaminationWithoutDataDTO> findByPatientDTO(PatientDTO patientDTO) {
		Patient patient = modelMapper.map(patientDTO, Patient.class);
		List<Examination> examinations = examinationRepository.findByPatient(patient);
		Type listType = new TypeToken<List<ExaminationWithoutDataDTO>>() {
		}.getType();
		return modelMapper.map(examinations, listType);
	}

	public List<ExaminationWithoutDataDTO> findByPatient(String login) {
		Patient patient = patientService.findByUser(login);
		List<Examination> examinations = examinationRepository.findByPatient(patient);
		Type listType = new TypeToken<List<ExaminationWithoutDataDTO>>() {
		}.getType();
		return modelMapper.map(examinations, listType);
	}

	public Examination findById(Integer id) {
		Long idLong = id.longValue();
		return examinationRepository.findById(idLong).orElse(null);
	}

	public ResponseEntity<Resource> download(Integer documentId) {

		Examination examination = findById(documentId);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(examination.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + examination.getFilename() + "\"")
				.body(new ByteArrayResource(examination.getContent()));
	}
}
