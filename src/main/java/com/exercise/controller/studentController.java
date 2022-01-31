
package com.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.ClassDAO;
import com.exercise.dao.StudentDAO;
import com.exercise.dto.ClassRequestDto;
import com.exercise.dto.ClassResponseDto;
import com.exercise.dto.StudentRequestDto;
import com.exercise.dto.StudentResponseDto;
import com.exercise.model.StudentBean;

@Controller
public class studentController {

	@Autowired
	private StudentDAO dao;

	@Autowired
	private ClassDAO cdao;

	@GetMapping(value = "/addstudent")
	public ModelAndView addstudent(ModelMap map) {
		ClassRequestDto dto = new ClassRequestDto();
		dto.setId("");
		dto.setName("");
		List<ClassResponseDto> list = cdao.select(dto);
		map.addAttribute("clist", list);
		return new ModelAndView("BUD002", "student", new StudentBean());
	}

	@PostMapping(value = "/setupaddstudent")
	public String setupaddstudent(@ModelAttribute("student") @Validated StudentBean bean, BindingResult bs,
			ModelMap map) {
		if (bs.hasErrors()) {
			return "BUD002";
		}
		StudentRequestDto dto = new StudentRequestDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		dto.setRegister(bean.getRegister());
		dto.setStatus(bean.getStatus());
		List<StudentResponseDto> list = dao.select(dto);
		if (list.size() != 0)
			map.addAttribute("err", "StudentId has been already");
		else {
			int res = dao.insert(dto);
			if (res > 0)
				map.addAttribute("msg", "Insert successfully!!");
			else
				map.addAttribute("err", "Insert fail");
		}
		return "BUD002";
	}

	@GetMapping(value = "/searchstudent")
	public ModelAndView searchstudent() {
		return new ModelAndView("BUD001", "student", new StudentBean());
	}

	@PostMapping(value = "/setupsearchstudent")
	public String setupsearchstudent(@ModelAttribute("student") StudentBean bean, ModelMap map) {
		StudentRequestDto dto = new StudentRequestDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		List<StudentResponseDto> list = dao.select(dto);
		if (list.size() == 0)
			map.addAttribute("msg", "Student not found!!");
		else
			map.addAttribute("stulist", list);
		return "BUD001";

	}

	@GetMapping(value = "/studentupdate")
	public ModelAndView updatestudent(@RequestParam("id") String studentId, Model model) {
		ClassRequestDto cdto = new ClassRequestDto();
		cdto.setId("");
		cdto.setName("");
		List<ClassResponseDto> list = cdao.select(cdto);
		model.addAttribute("clist", list);
		StudentRequestDto dto = new StudentRequestDto();
		dto.setStudentId(studentId);
		model.addAttribute("id", dto.getStudentId());
		return new ModelAndView("BUD002-01", "student", dao.selectOne(dto));
	}

	@PostMapping(value = "/setupupdatestudent")
	public String setupupdatestudent(@ModelAttribute("student") @Validated StudentBean bean, BindingResult bs,
			ModelMap map) {

		StudentRequestDto dto = new StudentRequestDto();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		dto.setRegister(bean.getRegister());
		dto.setStatus(bean.getStatus());
		dao.select(dto); //List<StudentResponseDto> list =
		System.out.println(dto.getStudentId());
		int res = dao.update(dto);

		if (res > 0)
			map.addAttribute("msg", "Update successfully!!");
		else
			map.addAttribute("err", "Update fail");

		return "BUD002-01";
	}

	@GetMapping(value = "/deletestudent")
	public String deletestudent(@RequestParam("id") String studentId, ModelMap map) {
		StudentRequestDto dto = new StudentRequestDto();
		dto.setStudentId(studentId);
		int res = dao.delete(dto);
		if (res > 0)
			map.addAttribute("msg", "Delete successful!!");
		else
			map.addAttribute("err", "Delete fail");

		return "redirect:/searchstudent";
	}

	@GetMapping(value = "/studentreset")
	public String reset() {
		return "redirect:/searchstudent";
	}

}
