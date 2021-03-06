package ch.fhnw.webfr.flashcard.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

@Controller
@RequestMapping("/questionnaires")
public class QuestionnaireController {

	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String findAll(Model model) {

		List<Questionnaire> questionnaires = questionnaireRepository.findAll();

		model.addAttribute("questionnaires", questionnaires);

		return "questionnaires/list";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String findById(@PathVariable String id, Model model) {

		Optional<Questionnaire> questionnaire = questionnaireRepository.findById(id);
		model.addAttribute("questionnaire", questionnaire.get());

		return "questionnaires/show";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "form" })
	public String getForm(Model model) {

		model.addAttribute("questionnaire", new Questionnaire());

		return "questionnaires/create";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Questionnaire questionnaire, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "questionnaires/create";
		}
		questionnaireRepository.save(questionnaire);
		return "redirect:questionnaires";

	}
}
