package pl.kruko.PracaInz.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import pl.kruko.PracaInz.dataTransferObjects.DoctorDTO;
import pl.kruko.PracaInz.dataTransferObjects.InstitutionDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeDTO;
import pl.kruko.PracaInz.models.Type;
import pl.kruko.PracaInz.service.DoctorService;
import pl.kruko.PracaInz.service.DoctorsCalendarService;
import pl.kruko.PracaInz.service.VisitTypeService;

@Controller
public class AddDoctorScheduleController {

	private DoctorsCalendarService doctorCalendarService;
	private DoctorService doctorService;
	private VisitTypeService visitTypeService;
	private List<VisitTypeDTO> visitTypes;

	@Autowired
	public AddDoctorScheduleController(DoctorsCalendarService doctorCalendarService, DoctorService doctorService,
			VisitTypeService visitTypeService) {
		super();
		this.doctorCalendarService = doctorCalendarService;
		this.doctorService = doctorService;
		this.visitTypeService = visitTypeService;
	}

	@GetMapping("/addSchedule.html")
	public String addingSchedule(HttpServletRequest request, Model model) {
		String login = currentUserNameSimple(request);
		DoctorDTO doctorDTO = doctorService.findDTOWithSpecializationByUser(login);
		List<InstitutionDTO> institutionDTOs = doctorDTO.getInstitutions();
		model.addAttribute("institutions", institutionDTOs);
		model.addAttribute("institution", new InstitutionDTO());
		model.addAttribute("visitTypes", visitTypes);
		return "addSchedule.html";
	}

	@PostMapping("/addSchedule/addVisit")
	public String addVisit(HttpServletRequest request) {
		String login = currentUserNameSimple(request);
		VisitTypeDTO visitType = doctorService.findVisitType(login);
		visitTypes = new ArrayList<>();
		visitTypes.add(visitType);
		return "redirect:/addSchedule.html";
	}

	@PostMapping("/addSchedule/addExamination")
	public String addExam(HttpServletRequest request) {
		visitTypes = visitTypeService.findByType(Type.EXAMINATION);
		return "redirect:/addSchedule.html";
	}

	@PostMapping("/addSchedule/add")
	public String add(HttpServletRequest request, String date, String timeStart, String timeEnd, String duration, String typeId,
			@ModelAttribute(value = "newInstitution") InstitutionDTO institution) {
		String login = currentUserNameSimple(request);
		doctorCalendarService.addNewScheduleToDoctor(login, date, timeStart, timeEnd, duration, typeId, institution);

		return "redirect:/addSchedule.html";
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}
}
