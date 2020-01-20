package pl.kruko.PracaInz.dataTransferObjects;

public class VisitDTOAndSuccess {
	private VisitDTO visitDTO;
	private Long success;

	public VisitDTOAndSuccess() {
		super();
	}

	public VisitDTO getVisitDTO() {
		return visitDTO;
	}

	public void setVisitDTO(VisitDTO visitDTO) {
		this.visitDTO = visitDTO;
	}

	public Long getSuccess() {
		return success;
	}

	public void setSuccess(Long success) {
		this.success = success;
	}

}
