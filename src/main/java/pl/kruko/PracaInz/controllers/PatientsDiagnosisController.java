package pl.kruko.PracaInz.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.kruko.PracaInz.dataTransferObjects.DiagnosisDTO;
import pl.kruko.PracaInz.service.DiagnosisService;

@Controller
public class PatientsDiagnosisController {

	private DiagnosisService diagnosisService;
	private List<DiagnosisDTO> diagnosisDTO;

	@Autowired
	public PatientsDiagnosisController(DiagnosisService diagnosisService) {
		super();
		this.diagnosisService = diagnosisService;
	}

	@GetMapping("/patientDiagnosis.html")
	public String showAllDiagnosis(HttpServletRequest request, Model model) {
		String login = currentUserNameSimple(request);
		List<DiagnosisDTO> diagnosisDTODic = diagnosisService.findByVisit(login);
		model.addAttribute("diagnosisDic", diagnosisDTODic);
		model.addAttribute("diagnosis", diagnosisDTO);
		return "patientDiagnosis.html";

	}

	@GetMapping("/homePatientDiagnosis.html")
	public String showDiagnosisByName(HttpServletRequest request) {
		String login = currentUserNameSimple(request);
		diagnosisDTO = diagnosisService.findByVisit(login);
		return "redirect:/patientDiagnosis.html";
	}

	@PostMapping("/patientDiagnosis.html")
	public String showDiagnosisByStatus(HttpServletRequest request, Integer statusNumber) {
		String login = currentUserNameSimple(request);
		if (statusNumber == null) {
			return "redirect:/homePatientDiagnosis.html";
		}
		diagnosisDTO = diagnosisService.findByStatusAndPatient(statusNumber, login);
		return "redirect:/patientDiagnosis.html";
	}

	@PostMapping("/patientDiagnosis/name")
	public String showDiagnosisByName(HttpServletRequest request, String diagnosisName) {
		String login = currentUserNameSimple(request);
		if(!diagnosisName.isEmpty()) {
		diagnosisDTO = diagnosisService.findByVisitAndName(login, diagnosisName);
		return "redirect:/patientDiagnosis.html";
		}else {
		return "redirect:/homePatientDiagnosis.html";
		}
		
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
