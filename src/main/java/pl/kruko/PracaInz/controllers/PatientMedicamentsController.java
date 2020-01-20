package pl.kruko.PracaInz.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.kruko.PracaInz.dataTransferObjects.PatientsMedicamentDTO;
import pl.kruko.PracaInz.service.PatientsMedicamentService;

@Controller
public class PatientMedicamentsController {

	private PatientsMedicamentService patientsMedicamentService;
	private List<PatientsMedicamentDTO> patientMedicamentDTO;

	@Autowired
	public PatientMedicamentsController(PatientsMedicamentService patientsMedicamentService) {
		super();
		this.patientsMedicamentService = patientsMedicamentService;
	}

	@GetMapping("/patientMedicament.html")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public String showAllMedicaments(HttpServletRequest request, Model model) {
		model.addAttribute("medicaments", patientMedicamentDTO);
		return "patientMedicament.html";

	}

	@GetMapping("/homePatientMedicaments.html")
	public String showAll(HttpServletRequest request) {
		String login = currentUserNameSimple(request);
		patientMedicamentDTO = patientsMedicamentService.findAllByPatient(login);
		return "redirect:/patientMedicament.html";
	}

	@PostMapping("/patientMedicament.html")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public String showMedicamentsbyStatus(HttpServletRequest request, Integer statusNumber) {
		String login = currentUserNameSimple(request);
		if (statusNumber == null) {
			return "redirect:/homePatientMedicaments.html";
		}
		patientMedicamentDTO = patientsMedicamentService.findByStatusAndPatient(statusNumber, login);
		return "redirect:/patientMedicament.html";

	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
