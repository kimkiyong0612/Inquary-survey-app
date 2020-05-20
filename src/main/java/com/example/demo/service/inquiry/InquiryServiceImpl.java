package com.example.demo.service.inquiry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.inquiry.InquiryDao;
import com.example.demo.entity.Inquiry;

@Service
public class InquiryServiceImpl implements InquiryService {

	//型をInterface名にする
	private final InquiryDao dao;

	@Autowired
	InquiryServiceImpl(InquiryDao dao){
		this.dao = dao;
	}


	@Override
	public void save(Inquiry inquiry) {
		dao.insertInquiry(inquiry);
	}

	@Override
	public List<Inquiry> getAll() {
		return dao.getAll();
	}


	@Override
	public void update(Inquiry inquiry) {
		if(dao.updateInquiry(inquiry)==0) {
			throw new InquiryNotFoundException("can't find the same ID");
		}

	}

}
