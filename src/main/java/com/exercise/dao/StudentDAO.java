
package com.exercise.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exercise.dto.StudentRequestDto;
import com.exercise.dto.StudentResponseDto;

@Repository
public class StudentDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(StudentRequestDto dto) {
		int result = 0;
		String sql = "insert into student (student_id,student_name,class_name,register_date,status) values(?,?,?,?,?)";
		result = jdbcTemplate.update(sql, dto.getStudentId(), dto.getStudentName(), dto.getClassName(),
				dto.getRegister(), dto.getStatus());

		return result;
	}

	public int update(StudentRequestDto dto) {
		int result = 0;

		String sql = "update student set student_name=?,class_name=?,register_date=?,status=? where student_id=?";
		result = jdbcTemplate.update(sql, dto.getStudentName(), dto.getClassName(), dto.getRegister(), dto.getStatus(),
				dto.getStudentId());
		System.out.println(result);
		return result;
	}

	public int delete(StudentRequestDto dto) {
		int result = 0;
		String sql = "delete from student where student_id=?";
		result = jdbcTemplate.update(sql, dto.getStudentId());

		return result;
	}

	public List<StudentResponseDto> select(StudentRequestDto dto) {
		String sql;
		List<StudentResponseDto> result = new ArrayList<>();
		if (dto.getStudentId().equals("") && dto.getStudentName().equals("")) {
			sql = "select * from student";
			result = jdbcTemplate.query(sql,
					(rs, rowNum) -> new StudentResponseDto(rs.getString("student_id"), rs.getString("student_name"),
							rs.getString("class_name"), rs.getString("register_date"), rs.getString("status")));
		} else {
			if (!dto.getStudentId().equals("")) {
				sql = "select * from student where student_id=?";
				result = jdbcTemplate.query(sql,
						(rs, rowNum) -> new StudentResponseDto(rs.getString("student_id"), rs.getString("student_name"),
								rs.getString("class_name"), rs.getString("register_date"), rs.getString("status")),
						dto.getStudentId());
			} else if (!dto.getStudentName().equals("")) {
				sql = "select * from student where student_name=?";
				result = jdbcTemplate.query(sql,
						(rs, rowNum) -> new StudentResponseDto(rs.getString("student_id"), rs.getString("student_name"),
								rs.getString("class_name"), rs.getString("register_date"), rs.getString("status")),
						dto.getStudentName());
			}
		}
		return result;
	}

	public StudentResponseDto selectOne(StudentRequestDto dto) {
		String sql = "select * from student where student_id=?";
		return jdbcTemplate.queryForObject(sql,
				(rs, rowNum) -> new StudentResponseDto(rs.getString("student_id"), rs.getString("student_name"),
						rs.getString("class_name"), rs.getString("register_date"), rs.getString("status")),
				dto.getStudentId());

	}

}
