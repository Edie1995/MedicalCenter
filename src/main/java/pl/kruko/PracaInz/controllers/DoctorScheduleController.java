package pl.kruko.PracaInz.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.kruko.PracaInz.dataTransferObjects.DoctorDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorsCalendarDTO;
import pl.kruko.PracaInz.service.DoctorService;
import pl.kruko.PracaInz.service.DoctorsCalendarService;


@Controller
public class DoctorScheduleController {
	
	private List<DoctorsCalendarDTO> calendar;
	private LocalDate chosenDate;
	private DoctorsCalendarService doctorsCalendarService;
	private DoctorService doctorService;
	

	@Autowired
	public DoctorScheduleController(DoctorsCalendarService doctorsCalendarService, DoctorService doctorService) {
		super();
		this.doctorsCalendarService = doctorsCalendarService;
		this.doctorService = doctorService;
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

	@GetMapping("/doctorSchedule.html")
	public String getRegisteredPatients(HttpServletRequest request, Model model) {
		String login = currentUserNameSimple(request);
		DoctorDTO doctorDTO =doctorService.findDTObyUser(login);
		model.addAttribute("specialization", doctorDTO.getSpecializtaion().getName());
		model.addAttribute("calendars", calendar);
		model.addAttribute("chosenDate", chosenDate);
		return "doctorSchedule.html";
	}

	@GetMapping("/homeDoctorSchedule.html")
	public String getHomeRegisteredPatients(HttpServletRequest request) {
		String login = currentUserNameSimple(request);
		chosenDate = LocalDate.now();
		calendar = doctorsCalendarService.findByDoctorandDate(login, chosenDate);
		return "redirect:/doctorSchedule.html";
	}

	@PostMapping("/doctorSchedule/search")
	public String getDateRegisteredPatients(HttpServletRequest request, String date) {
		String login = currentUserNameSimple(request);
		chosenDate = LocalDate.parse(date);
		calendar = doctorsCalendarService.findByDoctorandDate(login, chosenDate);
		return "redirect:/doctorSchedule.html";
	}

//	@PostMapping("/registeredPatients/getPatient")
//	public String getPatientVisit(HttpServletRequest request, int docorIdx, RedirectAttributes redirectAttributes) {
//		Doc visit = visits.get(docorIdx);
//		redirectAttributes.addFlashAttribute("visit", visit);
//		return "redirect:/doctorSchedule.html";
//	}

}
