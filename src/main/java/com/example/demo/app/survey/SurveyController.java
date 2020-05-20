package com.example.demo.app.survey;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Survey;
import com.example.demo.service.survey.SurveyService;

@Controller //Formをどのように処理するか、操作するかを作る
@RequestMapping("/survey")
public class SurveyController {


	public final SurveyService surveyService;
	@Autowired
	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}


	//アンケート入力画面
	@GetMapping("/form")
	public String form(Model model,SurveyForm surveyForm) {
		model.addAttribute("title","Survey Form");

		return "survey/form_boot";
	}


	@GetMapping
	public String index(Model model) {
		List<Survey> list = surveyService.getAll();

		model.addAttribute("title","Survey Index");
		model.addAttribute("surveyList", list);

		return "survey/index_boot";
	}

	@PostMapping("/form")
	public String fromGoBack(SurveyForm surveyForm,Model model) {
		model.addAttribute("title","Survey Form");

		return "survey/form_boot";
	}

	@PostMapping("/confirm")
	public String confirm(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form");
			return "survey/form_boot";
		}
		model.addAttribute("title","Confirm Page");
		return "survey/confirm_boot";

	}

	@PostMapping("/complete")
	public String complete(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("tittle","Survey Form");
			return "survey/form_boot";
		}

		Survey survey = new Survey();
		survey.setAge(surveyForm.getAge());
		survey.setSatisfaction(surveyForm.getSatisfaction());
		survey.setComment(surveyForm.getComment());
		survey.setCreated(LocalDateTime.now());

		surveyService.save(survey);

		//元のページに戻る
		redirectAttributes.addFlashAttribute("complete","登録完了");
		return "redirect:form";
	}

}
