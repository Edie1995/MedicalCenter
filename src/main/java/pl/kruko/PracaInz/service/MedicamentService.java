package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.MedicamentDTO;
import pl.kruko.PracaInz.models.Medicament;
import pl.kruko.PracaInz.repo.MedicamentRepository;

@Service
public class MedicamentService {

	private MedicamentRepository medicamentRepository;

	@Autowired
	public MedicamentService(MedicamentRepository medicamentRepository) {
		super();
		this.medicamentRepository = medicamentRepository;
	}

	public List<Medicament> findByName(String name) {
		return medicamentRepository.findByName(name);
	}

	public List<MedicamentDTO> findAll() {
		ModelMapper modelMapper = new ModelMapper();
		Type listType = new TypeToken<List<MedicamentDTO>>() {
		}.getType();
		List<Medicament> medicaments = medicamentRepository.findAll();
		return modelMapper.map(medicaments, listType);
	}
}
