package com.example.demo.dao.inquiry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Inquiry;

@Repository
public class InquiryDaoImpl implements InquiryDao {


	private final JdbcTemplate jdbcTemplate;
	//AutowiredをつけるとContenaからインスタンスをもって来れる
	@Autowired
	public InquiryDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate= jdbcTemplate;
	}



	@Override
	public void insertInquiry(Inquiry inquiry) {
		jdbcTemplate.update("INSERT INTO Inquiry(name, email, contents, created) VALUES (?, ?, ?, ?)",
				inquiry.getName(),inquiry.getEmail(),inquiry.getContents(),inquiry.getCreated());

	}

	@Override
	public List<Inquiry> getAll() {
		String sql = "SELECT id, name, contents, created  FROM Inquiry";
		//MapのListを作成して、そこにデータベースの値を入れていく
		List<Map<String, Object>> resultlist = jdbcTemplate.queryForList(sql);

		//Listの初期化
		List<Inquiry>list = new ArrayList<Inquiry>();

		//Mapの中身をダウンキャストしてListに詰め替える
		//大規模開発では常識
		for(Map <String, Object> result: resultlist) {
			Inquiry inquiry = new Inquiry();
			inquiry.setId((int)result.get("id"));
			inquiry.setName((String)result.get("name"));
			inquiry.setEmail((String)result.get("email"));
			inquiry.setContents((String)result.get("contents"));
			inquiry.setCreated(((Timestamp) result.get("created")).toLocalDateTime());
			list.add(inquiry);
		}

		return list;
		}



	@Override
	public int updateInquiry(Inquiry inquiry) {

		return jdbcTemplate.update("UPDATE inquiry SET name = ?, email = ?, contents = ?, WHERE id = ?",
				inquiry.getName(),inquiry.getEmail(),inquiry.getContents(),inquiry.getId());
	}


	}


