package pl.kruko.PracaInz.dataTransferObjects;

import pl.kruko.PracaInz.models.Status;

public class DiagnosisDTO implements Comparable<DiagnosisDTO>{

	private Long id;
	private String name;
	private String details;
	private VisitDTO visit;
	Status status;
	
	public DiagnosisDTO() {
		// TODO Auto-generated constructor stub
	}
	
		
	public DiagnosisDTO(String name, String details, VisitDTO visit, Status status) {
		super();
		this.name = name;
		this.details = details;
		this.visit = visit;
		this.status = status;
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
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public VisitDTO getVisit() {
		return visit;
	}
	public void setVisit(VisitDTO visit) {
		this.visit = visit;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int compareTo(DiagnosisDTO o) {
		return this.visit.compareTo(o.getVisit());
	}
	
	
	
}
