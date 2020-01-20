package pl.kruko.PracaInz.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.PatientDTO;
import pl.kruko.PracaInz.dataTransferObjects.UserDTO;
import pl.kruko.PracaInz.models.Patient;
import pl.kruko.PracaInz.models.Role;
import pl.kruko.PracaInz.models.User;
import pl.kruko.PracaInz.repo.PatientRepository;

@Service
public class PatientService {
	private PatientRepository patientRepository;
	private UserService userService;
	private PasswordEncoder passwordEncoder;
	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public PatientService(PatientRepository patientRepository, UserService userService,
			PasswordEncoder passwordEncoder) {
		super();
		this.patientRepository = patientRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	public Patient findByUser(String login) {
		User user = userService.findByLogin(login);
		Patient patient = user.getPatient();
		return patient;
	}

	public PatientDTO findDTObyUser(String login) {
		Patient patient = findByUser(login);
		return modelMapper.map(patient, PatientDTO.class);
	}

	public int upateLastName(String login, String lastName) {
		Patient patient1 = findByUser(login);
		patient1.setLastName(lastName);
		try {
			patientRepository.save(patient1);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}

	}

	public int updateMail(String login, String mail) {
		Patient patient1 = findByUser(login);
		patient1.setMail(mail);
		try {
			patientRepository.save(patient1);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 2;
		}
	}

	public int createNewUserPatient(String firstName, String lastName, String mail, String pesel, UserDTO userDTO) {
		try {
			Patient patient = new Patient();

			patient.setFirstName(firstName);
			patient.setLastName(lastName);
			patient.setMail(mail);
			if(checkPesel(pesel)!=0) {
				return 3;
			}
			patient.setPesel(Long.parseLong(pesel));
			User user = new User();
			user.setPatient(patient);
			user.setLogin(userDTO.getLogin());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setRole(Role.ROLE_PATIENT);
			patient.setUser(user);
			patientRepository.save(patient);
			if (userService.save(user) == 2) {
				return 2;
			}
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 1;
		}
	}

	public int checkPesel(String p) {
		int a = Integer.parseInt(String.valueOf(p.charAt(0)));
		int b = Integer.parseInt(String.valueOf(p.charAt(1)));
		int c = Integer.parseInt(String.valueOf(p.charAt(2)));
		int d = Integer.parseInt(String.valueOf(p.charAt(3)));
		int e = Integer.parseInt(String.valueOf(p.charAt(4)));
		int f = Integer.parseInt(String.valueOf(p.charAt(5)));
		int g = Integer.parseInt(String.valueOf(p.charAt(6)));
		int h = Integer.parseInt(String.valueOf(p.charAt(7)));
		int i = Integer.parseInt(String.valueOf(p.charAt(8)));
		int j = Integer.parseInt(String.valueOf(p.charAt(9)));
		int control = Integer.parseInt(String.valueOf(p.charAt(10)));

		int sum = 9 * a + 7 * b + 3 * c + 1 * d + 9 * e + 7 * f + 3 * g + 1 * h + 9 * i + 7 * j;
		if (sum%10 == control)
			return 0;
		else
			return 3;

	}

}
