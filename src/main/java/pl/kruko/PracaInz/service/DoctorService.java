package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.DoctorDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorForSearchDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorWithInstitutionsDTO;
import pl.kruko.PracaInz.dataTransferObjects.InstitutionDTO;
import pl.kruko.PracaInz.dataTransferObjects.UserDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeDTO;
import pl.kruko.PracaInz.models.Doctor;
import pl.kruko.PracaInz.models.Institution;
import pl.kruko.PracaInz.models.Role;
import pl.kruko.PracaInz.models.Specialization;
import pl.kruko.PracaInz.models.Status;
import pl.kruko.PracaInz.models.User;
import pl.kruko.PracaInz.repo.DoctorRepository;

@Service
public class DoctorService {

	private InstitutionService institutionService;
	private DoctorRepository doctorRepository;
	private SpecializationService specializationService;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private VisitTypeService visitTypeService;
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public DoctorService(DoctorRepository doctorRepository, SpecializationService specializationService,
			UserService userService, InstitutionService institutionService, VisitTypeService visitTypeService,
			PasswordEncoder passwordEncoder) {
		super();
		this.doctorRepository = doctorRepository;
		this.specializationService = specializationService;
		this.userService = userService;
		this.institutionService = institutionService;
		this.visitTypeService = visitTypeService;
		this.passwordEncoder = passwordEncoder;
	}

	public List<Doctor> findByNameAndSpecialization(String lastName, String specializationName, String city) {
		Specialization specialization = specializationService.findByName(specializationName);
		List<Doctor> doctors = doctorRepository.findByNameAndSpecialization(lastName, specialization);
		return doctors;
	}

	public List<DoctorForSearchDTO> findAll() {
		List<Doctor> doctors = doctorRepository.findAll();
		Type listType = new TypeToken<List<DoctorForSearchDTO>>() {
		}.getType();
		return modelMapper.map(doctors, listType);
	}

	public DoctorDTO findDTObyUser(String login) {
		Doctor doctor = findByUser(login);
		return modelMapper.map(doctor, DoctorDTO.class);
	}

	public DoctorDTO findDTOWithSpecializationByUser(String login) {
		Doctor doctor = findByUser(login);
		return modelMapper.map(doctor, DoctorDTO.class);
	}

	public Doctor findByUser(String login) {
		User user = userService.findByLogin(login);
		Doctor doctor = user.getDoctor();
		return doctor;
	}

	public int upateLastName(String login, String doctorName) {
		Doctor doctor = findByUser(login);
		doctor.setLastName(doctorName);
		try {
			doctorRepository.save(doctor);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}
	}

	public int updateNumber(String login, String doctorNumber) {
		Doctor doctor = findByUser(login);
		doctor.setTelephoneNumber(Long.parseLong(doctorNumber));
		try {
			doctorRepository.save(doctor);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 5;
		}
	}

	public int deleteInstitutionFromDoctor(String login, int idx) {
		Doctor doctor = findByUser(login);
		Institution institution = doctor.getInstitutions().get(idx);
		doctor.getInstitutions().remove(institution);
		institution.getDoctors().remove(doctor);
		try {
			institutionService.save(institution);
			doctorRepository.save(doctor);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 4;
		}

	}

	public int addInstitutionToDoctor(String login, InstitutionDTO institutionDTO) {
		Doctor doctor = findByUser(login);
		Institution institution = institutionService.findById(institutionDTO.getId());
		doctor.getInstitutions().add(institution);
		institution.getDoctors().add(doctor);
		try {
			institutionService.save(institution);
			doctorRepository.save(doctor);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 3;
		}
	}

	public int createNewUserDoctor(String firstName, String lastName, String phone, String specializationName,
			UserDTO userDTO) {
		try {
			Doctor doctor = new Doctor();
			doctor.setFirstName(firstName);
			doctor.setLastName(lastName);
			doctor.setTelephoneNumber(Long.parseLong(phone));
			Specialization specialization = specializationService.findByName(specializationName);
			doctor.setSpecializtaion(specialization);
			doctor.setStatus(Status.ACTIVE);
			User user = new User();
			user.setDoctor(doctor);
			user.setLogin(userDTO.getLogin());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setRole(Role.ROLE_DOCTOR);
			doctor.setUser(user);
			doctorRepository.save(doctor);
			if (userService.save(user) == 2) {
				return 2;
			}
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}

	}

	public List<InstitutionDTO> findDoctorInstitution(DoctorForSearchDTO doctorDTO) {
		List<InstitutionDTO> institutions = institutionService.findAll();
		List<InstitutionDTO> institutions2 = doctorDTO.getInstitutions();
		List<InstitutionDTO> avaliableInst = new ArrayList<InstitutionDTO>();
		for (InstitutionDTO i : institutions) {
			if (!institutions2.contains(i)) {
				avaliableInst.add(i);
			}
		}
		return avaliableInst;
	}

	public VisitTypeDTO findVisitType(String login) {
		DoctorDTO doctor = findDTObyUser(login);
		return visitTypeService.findByName(doctor.getSpecializtaion().getName());
	}

	public DoctorWithInstitutionsDTO findDoctorAndInstitutions(String login) {
		DoctorWithInstitutionsDTO doctorWithSpecializationDTO = new DoctorWithInstitutionsDTO();
		DoctorForSearchDTO doctorDTO = findDTObyUser(login);
		doctorWithSpecializationDTO.setDoctorDTO(doctorDTO);
		doctorWithSpecializationDTO.setInstitutionDTOs(findDoctorInstitution(doctorDTO));
		return doctorWithSpecializationDTO;

	}

}
