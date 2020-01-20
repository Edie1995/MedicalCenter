package pl.kruko.PracaInz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.kruko.PracaInz.models.Role;
import pl.kruko.PracaInz.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByLogin(String login);
	List<User> findAllByRole(Role role);
}
