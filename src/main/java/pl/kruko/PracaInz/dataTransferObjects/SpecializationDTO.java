package pl.kruko.PracaInz.dataTransferObjects;

public class SpecializationDTO {

	private Long id;
	private String name;
	
	public SpecializationDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
