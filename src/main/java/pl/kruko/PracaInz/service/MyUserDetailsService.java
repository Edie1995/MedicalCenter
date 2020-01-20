package pl.kruko.PracaInz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.models.User;
import pl.kruko.PracaInz.repo.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public MyUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLogin(username);
		if (user == null)
			throw new UsernameNotFoundException("User 404");
		return new UserPrincipal(user);
	}

}
