package pl.kruko.PracaInz.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleError2(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
//
//		redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
		redirectAttributes.addFlashAttribute("succes", 2);
		return "redirect:/patientExaminations.html";

	}

}
