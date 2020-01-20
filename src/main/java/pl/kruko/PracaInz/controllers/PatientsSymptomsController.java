package pl.kruko.PracaInz.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.kruko.PracaInz.dataTransferObjects.PatientSymptomDTO;
import pl.kruko.PracaInz.dataTransferObjects.SymptomDTO;
import pl.kruko.PracaInz.service.PatientSymptomService;
import pl.kruko.PracaInz.service.SymptomService;

@Controller
public class PatientsSymptomsController {

	private PatientSymptomService patientSymptomService;
	private SymptomService symptomService;
	private List<PatientSymptomDTO> patientSymptomsDTO;

	@Autowired
	public PatientsSymptomsController(PatientSymptomService patientSymptomService, SymptomService symptomService) {
		super();
		this.patientSymptomService = patientSymptomService;
		this.symptomService = symptomService;
	}

	@PostMapping("patientsSymptoms/symptom")
	public String show(HttpServletRequest request, String date,
			@ModelAttribute(value = "symptomName") SymptomDTO symptom, Model model) {
		String login = currentUserNameSimple(request);
		patientSymptomsDTO = patientSymptomService.findByPatientAndSymptomAndDate(login, symptom, date);
		model.addAttribute("patientSymptoms", patientSymptomsDTO);
		return "redirect:/symptoms.html";
	}

	@GetMapping("/symptoms.html")
	public String showByPatient(HttpServletRequest request, Model model) {
		List<SymptomDTO> symptomsDictionary = symptomService.findAll();
		model.addAttribute("symptomsDic", symptomsDictionary);
		model.addAttribute("symptomName", new SymptomDTO());
		model.addAttribute("patientSymptoms", patientSymptomsDTO);
		return "symptoms.html";
	}

	@GetMapping("/homeSymptoms.html")
	public String showAll(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		patientSymptomsDTO = patientSymptomService.findByPatient(login);
		redirectAttributes.addFlashAttribute("succes", model.getAttribute("succes"));
		return "redirect:/symptoms.html";
	}

	@PostMapping("patientsSymptoms/add")
	public String savePatientSymptom(HttpServletRequest request,
			@ModelAttribute(value = "symptomName") SymptomDTO symptom, String date, RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);		
		int succes = patientSymptomService.savePatientSymptom(login, symptom, date);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/homeSymptoms.html";
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
