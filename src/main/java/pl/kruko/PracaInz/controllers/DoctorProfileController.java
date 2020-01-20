package pl.kruko.PracaInz.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.kruko.PracaInz.dataTransferObjects.DoctorForSearchDTO;
import pl.kruko.PracaInz.dataTransferObjects.DoctorWithInstitutionsDTO;
import pl.kruko.PracaInz.dataTransferObjects.InstitutionDTO;
import pl.kruko.PracaInz.service.DoctorService;
import pl.kruko.PracaInz.service.UserService;

@Controller
public class DoctorProfileController {

	private DoctorService doctorService;
	private UserService userService;

	@Autowired
	public DoctorProfileController(DoctorService doctorService, UserService userService) {
		super();
		this.doctorService = doctorService;
		this.userService = userService;
	}

	@GetMapping("/doctorProfile.html")
	public String showAll(HttpServletRequest request, Model model) {
		String login = currentUserNameSimple(request);
		DoctorWithInstitutionsDTO doctorWithInstitutionsDTO = doctorService.findDoctorAndInstitutions(login);
		DoctorForSearchDTO doctorDTO = doctorWithInstitutionsDTO.getDoctorDTO();
		model.addAttribute("institutions", doctorWithInstitutionsDTO.getInstitutionDTOs());
		model.addAttribute("newInstitution", new InstitutionDTO());
		model.addAttribute("doctor", doctorDTO);
		return "doctorProfile.html";

	}

	@PostMapping("/doctorProfile/updateDoctorPassword")
	public String updateDoctorPassword(HttpServletRequest request, String userPassword,
			RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		int succes = userService.updatePassword(login, userPassword);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/doctorProfile.html";
	}

	@PostMapping("/doctorProfile/updateDoctorName")
	public String updateDoctorName(HttpServletRequest request, String doctorName,
			RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		int succes = doctorService.upateLastName(login, doctorName);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/doctorProfile.html";
	}

	@PostMapping("/doctorProfile/updateNumber")
	public String updateDoctorNumber(HttpServletRequest request, String doctorNumber,
			RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		int succes = doctorService.updateNumber(login, doctorNumber);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/doctorProfile.html";
	}

	@PostMapping("/doctorProfile/deleteInstitution")
	public String deleteInstitution(HttpServletRequest request, String deleteInstitution,
			RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		int succes = doctorService.deleteInstitutionFromDoctor(login, Integer.parseInt(deleteInstitution));
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/doctorProfile.html";
	}

	@PostMapping("/doctorProfile/addInstitution")
	public String addInstitution(HttpServletRequest request,
			@ModelAttribute(value = "newInstitution") InstitutionDTO institutionDTO,
			RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		int succes = doctorService.addInstitutionToDoctor(login, institutionDTO);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/doctorProfile.html";
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
