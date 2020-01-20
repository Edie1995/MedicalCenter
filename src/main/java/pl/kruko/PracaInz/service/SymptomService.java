package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.SymptomDTO;
import pl.kruko.PracaInz.models.Symptom;
import pl.kruko.PracaInz.repo.SymptomRepository;

@Service
public class SymptomService {

	private SymptomRepository symptomRepository;

	private ModelMapper modelMapper = new ModelMapper();
	private Type listType = new TypeToken<List<SymptomDTO>>() {
	}.getType();

	@Autowired
	public SymptomService(SymptomRepository symptomRepository) {
		super();
		this.symptomRepository = symptomRepository;
	}
	
	public List<SymptomDTO> findAll() {
		List<Symptom> symptoms = symptomRepository.findAll();
		return modelMapper.map(symptoms, listType);
	}

}
