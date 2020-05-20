package com.example.demo.service.inquiry;

import java.util.List;

import com.example.demo.entity.Inquiry;

public interface InquiryService {

	void save(Inquiry inquiry);

	List<Inquiry> getAll();

	void update(Inquiry inquiry);

}
