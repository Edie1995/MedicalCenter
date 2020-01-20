package pl.kruko.PracaInz.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.kruko.PracaInz.dataTransferObjects.ExaminationWithoutDataDTO;
import pl.kruko.PracaInz.service.ExaminationService;

@Controller
public class ExaminationController {

	private ExaminationService examinationService;

	@Autowired
	public ExaminationController(ExaminationService examinationService) {
		super();
		this.examinationService = examinationService;
	}

	@RequestMapping(value = "/save.html", method = RequestMethod.POST)
	public String save(HttpServletRequest request, @ModelAttribute("examination") ExaminationWithoutDataDTO examination,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
		String login = currentUserNameSimple(request);
		int success = examinationService.saveExamination(login, examination, file);
		redirectAttributes.addFlashAttribute("succes", success);
		return "redirect:/patientExaminations.html";
	}

	@GetMapping("/patientExaminations.html")
	public String home(HttpServletRequest request, Model model) {
		String login = currentUserNameSimple(request);
		try {
			model.addAttribute("document", new ExaminationWithoutDataDTO());
			model.addAttribute("documentList", examinationService.findByPatient(login));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "patientExaminations.html";
	}

	@GetMapping("patientExaminations/download/{documentId}")
	public ResponseEntity<Resource> download(@PathVariable("documentId") Integer documentId,
			HttpServletResponse response) {

		return examinationService.download(documentId);
	}

	public String currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String login = principal.getName();
		return login;
	}

}
