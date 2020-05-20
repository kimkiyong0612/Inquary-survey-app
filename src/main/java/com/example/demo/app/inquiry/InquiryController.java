package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.inquiry.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	private final InquiryService inquiryService;
	@Autowired
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}


	//お問い合わせ一覧の表示
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();

		//例外処理実践
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("Jamie");
//		inquiry.setEmail("asdf@example.com");
//		inquiry.setContents("Hello");
//
//		inquiryService.update(inquiry);
//
//		try {
//			inquiryService.update(inquiry);
//		}catch(InquiryNotFoundException e){
//			model.addAttribute("message",e);
//			return "error/CustomPage";
//		}

		model.addAttribute("inquiryList", list);
		model.addAttribute("title","Inquiry index");

		return "inquiry/index_boot" ;
	}


	@GetMapping("/form")
	public String form(InquiryForm inquiryForm,
			Model model,
			@ModelAttribute("complete")String complete) {
		model.addAttribute("title","Inquiry Form");

		return "inquiry/form_boot";
		}

	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title","Inquiry Form");

		return "inquiry/form_boot";
		}

	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form_boot";
		}
		model.addAttribute("title","Confirm Page");
		return "inquiry/confirm_boot";

	}

	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("tittle","Inquiry Form");
			return "inquiry/form_boot";
		}

		//Formからinquiryへの詰め直し（簡素化するクラスあるらしい！）
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());

		//DBに格納
		inquiryService.save(inquiry);

		//元のページに戻る
		redirectAttributes.addFlashAttribute("complete", "Registered");
		return "redirect:/inquiry/form";

	}

//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleException(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}

}
