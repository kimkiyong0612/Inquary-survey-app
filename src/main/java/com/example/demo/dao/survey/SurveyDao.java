package com.example.demo.dao.survey;

import java.util.List;

import com.example.demo.entity.Survey;

public interface SurveyDao {

	void insertInquiry(Survey survey);

	List<Survey>getAll();


}
