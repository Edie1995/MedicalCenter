package pl.kruko.PracaInz.dataTransferObjects;

import java.time.LocalDate;
import java.util.List;

public class ScheduledVisitsWithDatesDTO {
	private List<ScheduledVisitToCalendarDTO> scheduledVisitDTO;
	private List<LocalDate> dates;

	public ScheduledVisitsWithDatesDTO() {
		super();
	}

	public List<ScheduledVisitToCalendarDTO> getScheduledVisitDTO() {
		return scheduledVisitDTO;
	}

	public void setScheduledVisitDTO(List<ScheduledVisitToCalendarDTO> scheduledVisitDTO) {
		this.scheduledVisitDTO = scheduledVisitDTO;
	}

	public List<LocalDate> getDates() {
		return dates;
	}

	public void setDates(List<LocalDate> dates) {
		this.dates = dates;
	}

}
