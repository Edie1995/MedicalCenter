package pl.kruko.PracaInz.dataTransferObjects;

public class MedicamentDTO {
	private Long id;
	private String name;
	private String producent;
	private int dose;
	
	public MedicamentDTO() {
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

	public String getProducent() {
		return producent;
	}

	public void setProducent(String producent) {
		this.producent = producent;
	}

	public int getDose() {
		return dose;
	}

	public void setDose(int dose) {
		this.dose = dose;
	}
	
	
}
