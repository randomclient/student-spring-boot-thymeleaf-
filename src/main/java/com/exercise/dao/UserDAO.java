package com.exercise.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exercise.dto.UserRequestDto;
import com.exercise.dto.UserResponseDto;

@Repository
public class UserDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(UserRequestDto dto) {
		int result = 0;
		String sql = "insert into user (id,name,password) values(?,?,?)";
		result = jdbcTemplate.update(sql, dto.getId(), dto.getName(), dto.getPassword());

		return result;
	}

	public int update(UserRequestDto dto) {
		int result = 0;
		String sql = "update user set name=?,password=? where id=?";
		result = jdbcTemplate.update(sql, dto.getName(), dto.getPassword(), dto.getId());

		return result;
	}

	public int delete(UserRequestDto dto) {
		int result = 0;
		String sql = "delete from user where id=?";
		result = jdbcTemplate.update(sql, dto.getId());
		return result;
	}

	public List<UserResponseDto> select(UserRequestDto dto) {
		String sql;
		List<UserResponseDto> result = new ArrayList<>();

		if (dto.getId().equals("") && dto.getName().equals("")) {
			sql = "select * from user";
			result = jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDto(rs.getString("id"),
					rs.getString("name"), rs.getString("password")));
		} else {
			if (!dto.getId().equals("")) {
				sql = "select * from user where id=?";
				result = jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDto(rs.getString("id"),
						rs.getString("name"), rs.getString("password")), dto.getId());
			} else if (!dto.getName().equals("")) {
				sql = "select * from user where id=?";
				result = jdbcTemplate.query(sql, (rs, rowNum) -> new UserResponseDto(rs.getString("id"),
						rs.getString("name"), rs.getString("password")), dto.getId());
			}
		}
		return result;

	}

	public UserResponseDto selectOne(UserRequestDto dto) {
		String sql = "select * from user where id=?";
		return jdbcTemplate.queryForObject(sql,
				(rs, rowNum) -> new UserResponseDto(rs.getString("id"), rs.getString("name"), rs.getString("password")),
				dto.getId());
	}

}
