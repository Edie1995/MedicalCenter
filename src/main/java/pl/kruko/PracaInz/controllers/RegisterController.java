package pl.kruko.PracaInz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.kruko.PracaInz.dataTransferObjects.UserDTO;
import pl.kruko.PracaInz.service.DoctorService;
import pl.kruko.PracaInz.service.PatientService;
import pl.kruko.PracaInz.service.SpecializationService;
import pl.kruko.PracaInz.service.UserService;

@Controller
public class RegisterController {

	SpecializationService specializationService;
	PatientService patientService;
	DoctorService doctorService;
	UserService userService;

	@Autowired
	public RegisterController(SpecializationService specializationService, PatientService patientService,
			DoctorService doctorService, UserService userService) {
		super();
		this.specializationService = specializationService;
		this.patientService = patientService;
		this.doctorService = doctorService;
		this.userService = userService;
	}

	@GetMapping("/registerAccount.html")
	public String registerAccount(Model model) {
		model.addAttribute("specializations", specializationService.findAll());
		model.addAttribute("user", new UserDTO());
		return "registerAccount.html";
	}

	@PostMapping("/registerAccount/patient")
	public String registerPatient(String firstName, String lastName, String mail, String pesel,
			@ModelAttribute(value = "user") UserDTO user, RedirectAttributes redirectAttributes) {
		int succes = patientService.createNewUserPatient(firstName, lastName, mail, pesel, user);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/registerAccount.html";
	}

	@PostMapping("/registerAccount/doctor")
	public String registerDoctor(String firstName, String lastName, String phone, String specialization,
			@ModelAttribute(value = "user") UserDTO user,RedirectAttributes redirectAttributes) {
		int succes = doctorService.createNewUserDoctor(firstName, lastName, phone, specialization, user);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/registerAccount.html";
	}
}
