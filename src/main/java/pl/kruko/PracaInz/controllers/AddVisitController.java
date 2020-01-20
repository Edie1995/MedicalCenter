package pl.kruko.PracaInz.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.kruko.PracaInz.dataTransferObjects.DoctorForSearchDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorsCalendarDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitTypeForSearchDTO;
import pl.kruko.PracaInz.models.Type;
import pl.kruko.PracaInz.service.DoctorService;
import pl.kruko.PracaInz.service.DoctorsCalendarService;
import pl.kruko.PracaInz.service.ScheduledVisitService;
import pl.kruko.PracaInz.service.VisitTypeService;

@Controller
public class AddVisitController {

	private ScheduledVisitService scheduledVisitService;
	private DoctorService doctorService;
	private DoctorsCalendarService doctorsCalendarService;
	private VisitTypeService visitTypeService;

	private List<VisitTypeDTO> visitsTypesDTO;
	private List<DoctorsCalendarDTO> doctorsCalendarDTO;
	private int visitExam;

	@Autowired
	public AddVisitController(ScheduledVisitService scheduledVisitService, DoctorService doctorService,
			DoctorsCalendarService doctorsCalendarService, VisitTypeService visitTypeService) {
		super();
		this.scheduledVisitService = scheduledVisitService;
		this.doctorService = doctorService;
		this.doctorsCalendarService = doctorsCalendarService;
		this.visitTypeService = visitTypeService;
	}

	@GetMapping("/searchDoctor.html")
	public String searchDoctor(HttpServletRequest request, Model model) {
		model.addAttribute("visitTypes", visitsTypesDTO);
		model.addAttribute("visitType", new VisitTypeForSearchDTO());
		List<DoctorForSearchDTO> doctorsDTO = doctorService.findAll();
		model.addAttribute("doctors", doctorsDTO);
		return "searchDoctor.html";
	}

	@GetMapping("/searchDoctor/exam")
	public String searchExam(HttpServletRequest request) {
		visitsTypesDTO = visitTypeService.findByType(Type.EXAMINATION);
		visitExam = 0;
		return "redirect:/searchDoctor.html";
	}

	@GetMapping("/searchDoctor/visit")
	public String searchDoctorVisit(HttpServletRequest request) {
		visitsTypesDTO = visitTypeService.findByType(Type.MEDICAL_VISIT);
		visitExam = 1;
		return "redirect:/searchDoctor.html";
	}

	@PostMapping("/addVisit/search")
	public String visitProposition(HttpServletRequest request,
			@ModelAttribute(value = "visitType") VisitTypeForSearchDTO visitTypeDTO, String dateString,
			String doctorName, String city) {
		LocalDate date = LocalDate.parse(dateString);
		doctorsCalendarDTO = doctorsCalendarService.findByDoctorAndDateAndTypeAndCity(doctorName, date, visitTypeDTO,
				city, visitExam);
		return "redirect:/addVisit.html";
	}

	@GetMapping("/addVisit.html")
	public String addVisit(HttpServletRequest request, Model model) {
		String login = currentUserNameSimple(request);
		List<LocalDate> dates = doctorsCalendarService.getDates(doctorsCalendarDTO);
		model.addAttribute("occupiedDates", scheduledVisitService.getDatesTimes(login));
		model.addAttribute("doctorCalendar", doctorsCalendarDTO);
		model.addAttribute("dates", dates);
		return "addVisit.html";
	}

	@PostMapping("/addVisit/add")
	public String addNewVisit(HttpServletRequest request, String docorIdx, RedirectAttributes redirectAttributes) {		
		DoctorsCalendarDTO doctorCalendar = doctorsCalendarDTO.get(Integer.valueOf(docorIdx));
		String login = currentUserNameSimple(request);
		int succes = scheduledVisitService.addNewEvent(login, doctorCalendar);
		doctorsCalendarDTO.remove(doctorCalendar);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/addVisit.html";
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
