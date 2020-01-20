package pl.kruko.PracaInz.service;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kruko.PracaInz.dataTransferObjects.VisitTypeDTO;
import pl.kruko.PracaInz.models.VisitType;
import pl.kruko.PracaInz.repo.VisitTypeRepository;

@Service
public class VisitTypeService {

	private VisitTypeRepository visitTypeRepository;

	private ModelMapper modelMapper = new ModelMapper();
	private Type listType = new TypeToken<List<VisitTypeDTO>>() {
	}.getType();
	
	@Autowired
	public VisitTypeService(VisitTypeRepository visitTypeRepository) {
		super();
		this.visitTypeRepository = visitTypeRepository;
	}

	public List<VisitTypeDTO> findByType(pl.kruko.PracaInz.models.Type type) {
		List<VisitType> visitTypes = visitTypeRepository.findByType(type);
		List<VisitTypeDTO> visitTypesDTO = modelMapper.map(visitTypes, listType);
		return visitTypesDTO;
	}
	
	public VisitTypeDTO findByName(String name) {
		VisitType visitType = visitTypeRepository.findByName(name);
		return modelMapper.map(visitType, VisitTypeDTO.class);
	}

}
