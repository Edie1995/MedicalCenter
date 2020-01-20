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

import pl.kruko.PracaInz.dataTransferObjects.MedicamentDTO;
import pl.kruko.PracaInz.dataTransferObjects.PatientDTO;
import pl.kruko.PracaInz.dataTransferObjects.ScheduledVisitDTO;
import pl.kruko.PracaInz.dataTransferObjects.SymptomDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitDTO;
import pl.kruko.PracaInz.dataTransferObjects.VisitDTOAndSuccess;
import pl.kruko.PracaInz.service.DiagnosisService;
import pl.kruko.PracaInz.service.ExaminationService;
import pl.kruko.PracaInz.service.MedicamentService;
import pl.kruko.PracaInz.service.PatientSymptomService;
import pl.kruko.PracaInz.service.PatientsMedicamentService;
import pl.kruko.PracaInz.service.SymptomService;
import pl.kruko.PracaInz.service.VisitService;

@Controller
public class ChosenPatientController {

	private VisitService visitService;
	private PatientsMedicamentService patientsMedicamentService;
	private PatientSymptomService patientSymptomService;
	private DiagnosisService diagnosisService;
	private SymptomService symptomService;
	private MedicamentService medicamentService;

	private ScheduledVisitDTO scheduledVisit;
	private VisitDTO visitDTO;

	private ExaminationService examinationService;

	@Autowired
	public ChosenPatientController(VisitService visitService, PatientsMedicamentService patientsMedicamentService,
			PatientSymptomService patientSymptomService, DiagnosisService diagnosisService,
			SymptomService symptomService, MedicamentService medicamentService, ExaminationService examinationService) {
		super();
		this.visitService = visitService;
		this.patientsMedicamentService = patientsMedicamentService;
		this.patientSymptomService = patientSymptomService;
		this.diagnosisService = diagnosisService;
		this.symptomService = symptomService;
		this.medicamentService = medicamentService;
		this.examinationService = examinationService;
	}

	@GetMapping("/chosenPatient.html")
	public String getPatient(HttpServletRequest request, Model model) {
		List<SymptomDTO> symptomsDictionary = symptomService.findAll();
		model.addAttribute("symptomsDic", symptomsDictionary);
		model.addAttribute("symptomName", new SymptomDTO());
		List<MedicamentDTO> medicamentDictionary = medicamentService.findAll();
		model.addAttribute("medicamentDic", medicamentDictionary);
		model.addAttribute("medicament", new MedicamentDTO());
		PatientDTO patientDTO = scheduledVisit.getPatient();
		model.addAttribute("patient", patientDTO);
		model.addAttribute("medicaments", patientsMedicamentService.findAllByPatientDTO(patientDTO));
		model.addAttribute("symptoms", patientSymptomService.findByPatientDTO(patientDTO));
		model.addAttribute("diagnosis", diagnosisService.findByVisitPatient(patientDTO));
		model.addAttribute("visit", visitDTO);
		model.addAttribute("scheduledVisit", scheduledVisit);
		model.addAttribute("documentList", examinationService.findByPatientDTO(patientDTO));
		return "chosenPatient.html";
	}

	@GetMapping("/homechosenPatient.html")
	public String getPatientData(HttpServletRequest request, Model model) {
		scheduledVisit = (ScheduledVisitDTO) model.getAttribute("scheduledVisit");
		visitDTO = scheduledVisit.getVisit();
		return "redirect:/chosenPatient.html";
	}

	@PostMapping("/chosenPatient/addToVisit")
	public String addToVisit(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String login = currentUserNameSimple(request);
		VisitDTOAndSuccess visitAndSuccess = visitService.saveWithScheduled(login, scheduledVisit);
		visitDTO = visitAndSuccess.getVisitDTO();
		Long succes = visitAndSuccess.getSuccess();
		visitDTO.setId(succes);
		scheduledVisit.setVisit(visitDTO);
		redirectAttributes.addFlashAttribute("succes", succes);
		return "redirect:/chosenPatient.html";
	}

	@PostMapping("/chosenPatient/addSymptom")
	public String addSymptomToVisit(@ModelAttribute("symptomName") SymptomDTO symptom, String date,
			RedirectAttributes redirectAttributes) {
		int succesAdd = patientSymptomService.saveSymptomByVisit(symptom, visitDTO, date);
		redirectAttributes.addFlashAttribute("succesAdd", succesAdd);
		return "redirect:/chosenPatient.html";

	}

	@PostMapping("/chosenPatient/addMedicament")
	public String addMedicamentToVisit(@ModelAttribute("medicament") MedicamentDTO medicamentDTO, int dosage,
			int frequency, String date, RedirectAttributes redirectAttributes) {
		int succesAdd = patientsMedicamentService.save(medicamentDTO, dosage, frequency, visitDTO, date);
		redirectAttributes.addFlashAttribute("succesAdd", succesAdd);
		return "redirect:/chosenPatient.html";

	}

	@PostMapping("/chosenPatient/addDiagnosis")
	public String addDiagnosisToVisit(String diagnosisName, String details, RedirectAttributes redirectAttributes) {
		int succesAdd = diagnosisService.save(diagnosisName, details, visitDTO);
		redirectAttributes.addFlashAttribute("succesAdd", succesAdd);
		return "redirect:/chosenPatient.html";

	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
