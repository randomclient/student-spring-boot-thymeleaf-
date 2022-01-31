package com.exercise.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exercise.dto.ClassRequestDto;
import com.exercise.dto.ClassResponseDto;

@Repository

public class ClassDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(ClassRequestDto dto) {
		int result = 0;
		String sql = "insert into class (id,name) values(?,?)";
		result = jdbcTemplate.update(sql, dto.getId(), dto.getName());

		return result;
	}

	public List<ClassResponseDto> select(ClassRequestDto dto) {
		String sql = "select * from class";
		return jdbcTemplate.query(sql, (rs, rowNum) -> new ClassResponseDto(rs.getString("id"), rs.getString("name")));

	}

	public ClassResponseDto selectOne(ClassRequestDto dto) {
		ClassResponseDto oneRow = null;
		String sql = "select * from class where id=?";
		try {
			oneRow = jdbcTemplate.queryForObject(sql,
					(rs, rowNum) -> new ClassResponseDto(rs.getString("id"), rs.getString("name")), dto.getId());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		return oneRow;
	}

}
