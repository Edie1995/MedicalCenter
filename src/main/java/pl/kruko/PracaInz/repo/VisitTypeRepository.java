package pl.kruko.PracaInz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.kruko.PracaInz.models.Type;
import pl.kruko.PracaInz.models.VisitType;
public interface VisitTypeRepository extends JpaRepository<VisitType, Long>{

	VisitType findByName(String name);
	List<VisitType> findByType (Type type);
	List<VisitType> findAll();
}
