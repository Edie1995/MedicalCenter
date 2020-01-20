package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.SpecializationDTO;
import pl.kruko.PracaInz.models.Specialization;
import pl.kruko.PracaInz.repo.SpecializationRepository;

@Service
public class SpecializationService {

	private SpecializationRepository specializationRepository;

	@Autowired
	public SpecializationService(SpecializationRepository specializationRepository) {
		super();
		this.specializationRepository = specializationRepository;
	}

	public Specialization findByName(String name) {
		Specialization specialization = specializationRepository.findByName(name);
		return specialization;
	}

	public List<SpecializationDTO> findAll() {
		List<Specialization> specializations = specializationRepository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<SpecializationDTO>>() {
		}.getType();
		return modelMapper.map(specializations, listType);
	}

}
