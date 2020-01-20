package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.InstitutionDTO;
import pl.kruko.PracaInz.models.Institution;
import pl.kruko.PracaInz.models.Status;
import pl.kruko.PracaInz.repo.InstitutionRepository;

@Service
public class InstitutionService {
	private InstitutionRepository institutionRepository;

	@Autowired
	public InstitutionService(InstitutionRepository institutionRepository) {
		super();
		this.institutionRepository = institutionRepository;
	}

	public List<Institution> findByCityAndStatus(String city, Status status) {
		return institutionRepository.findByCityAndStatus(city, status);
	}

	public Institution findById(Long id) {
		return institutionRepository.findById(id).orElse(null);
	}

	public void save(Institution i) {
		institutionRepository.save(i);
	}

	public List<InstitutionDTO> findAll() {
		List<Institution> institutions = institutionRepository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<InstitutionDTO>>() {
		}.getType();
		return modelMapper.map(institutions, listType);
	}

	public List<Institution> findAllObj() {
		return institutionRepository.findAll();
	}
}
