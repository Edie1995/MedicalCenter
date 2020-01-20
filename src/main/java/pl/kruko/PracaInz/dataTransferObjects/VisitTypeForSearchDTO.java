package pl.kruko.PracaInz.dataTransferObjects;

public class VisitTypeForSearchDTO {
	private Long id;
	private String name;	

	public VisitTypeForSearchDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public VisitTypeForSearchDTO() {
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
		return "name=" + name + " id=" + id;
	}

}
