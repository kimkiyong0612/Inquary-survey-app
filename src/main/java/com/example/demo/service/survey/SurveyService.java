package com.example.demo.service.survey;

import java.util.List;

import com.example.demo.entity.Survey;

public interface SurveyService {

	void save(Survey inquiry);
	
	List<Survey>getAll();



}
