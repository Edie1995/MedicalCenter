package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.UserDTO;
import pl.kruko.PracaInz.dataTransferObjects.UserNoPasswordDTO;
import pl.kruko.PracaInz.models.Role;
import pl.kruko.PracaInz.models.User;
import pl.kruko.PracaInz.repo.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	private ModelMapper modelMapper = new ModelMapper();

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDTO findDTOByLogin(String login) {
		User user = findByLogin(login);
		return modelMapper.map(user, UserDTO.class);
	}

	public User findByLogin(String login) {
		User user = userRepository.findByLogin(login);
		return user;
	}

	public int save(User user) {
		try {
			userRepository.save(user);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 2;
		}
	}

	public int updatePassword(String login, String password) {
		User user = findByLogin(login);
		user.setPassword(passwordEncoder.encode(password));
		try {
			save(user);
			return 0;
		} catch (DataIntegrityViolationException e) {
			return 3;
		}
	}

	public List<UserNoPasswordDTO> findAllByRole(Role role) {
		List<User> users = userRepository.findAllByRole(role);
		Type listType = new TypeToken<List<UserNoPasswordDTO>>() {
		}.getType();
		return modelMapper.map(users, listType);
	}

	public List<UserNoPasswordDTO> findAllDoctors() {
		return findAllByRole(Role.ROLE_DOCTOR);
	}

	public List<UserNoPasswordDTO> findAllPatients() {
		return findAllByRole(Role.ROLE_PATIENT);
	}
}
