package com.exercise.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.UserDAO;
import com.exercise.dto.UserRequestDto;
import com.exercise.dto.UserResponseDto;
import com.exercise.model.UserBean;

@Controller
public class LoginController {
	@Autowired
	private UserDAO dao;

	// login - get
	@GetMapping("/")
	public ModelAndView login() {
		return new ModelAndView("LGN001", "user", new UserBean());
	}
	
	@GetMapping("/page")
	public String page() {
		return "M00001";
	}

	// login - post
	@PostMapping("/setuplogin")
	public String setuplogin(@ModelAttribute("user") @Validated UserBean bean, BindingResult bind, ModelMap map,HttpSession session) {

		if (bind.hasErrors()) {
			return "LGN001";
		}
		
		UserRequestDto dto = new UserRequestDto();
		dto.setId(bean.getId());

		List<UserResponseDto> list = dao.select(dto);

		if (list.size() == 0) {
			map.addAttribute("err", "User not found");
			return "LGN001";
		} else if (bean.getPassword().equals(list.get(0).getPassword())) {
			 session.setAttribute("mySession", list.get(0).getName()); 
			return "M00001";
		} else {
			return "LGN001";
		}

	}

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	// usersearch - get
	@GetMapping(value = "/usersearch")

	public ModelAndView usersearch() {
		return new ModelAndView("USR001", "user", new UserBean());
	}

	// user register - get
	@GetMapping(value = "/useradd")
	public ModelAndView useradd() {
		return new ModelAndView("USR002", "user", new UserBean());
	}

	// user register - post
	@PostMapping("/setupadduser")
	public String setupuseradd(@ModelAttribute("user") @Validated UserBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {

			return "USR002";
		}
		if (bean.getPassword().equals(bean.getConfirm())) {
			UserRequestDto dto = new UserRequestDto();
			dto.setId(bean.getId());
			dto.setName(bean.getName());
			dto.setPassword(bean.getPassword());
			List<UserResponseDto> list = dao.select(dto);

			if (list.size() != 0)
				map.addAttribute("err", "User Id has been already!!");
			else {
				int res = dao.insert(dto);
				if (res > 0)
					map.addAttribute("msg", "Insert successfully");
				else
					map.addAttribute("err", "Insert fail");
				return "USR002";
			}

		} else
			map.addAttribute("err", "Password are not match");

		return "USR002";
	}

	// user register - usersearch
	@PostMapping("/setupusersearch")
	public String setupusersearch(@ModelAttribute("user") UserBean bean, ModelMap map) {
		UserRequestDto dto = new UserRequestDto();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		List<UserResponseDto> list = dao.select(dto);
		if (list.size() == 0)
			map.addAttribute("err", "User not found");
		else
			map.addAttribute("userlist", list);
		return "USR001";
	}

	// user delete - get
	@GetMapping(value = "/userdelete")
	public String userdelete(@RequestParam("id") String id, ModelMap map) {
		UserRequestDto dto = new UserRequestDto();
		dto.setId(id);
		int res = dao.delete(dto);
		if (res > 0)
			map.addAttribute("msg", "Delete successful!!");
		else
			map.addAttribute("err", "Delete fail");

		return "redirect:/usersearch";
	}

	@GetMapping(value = "/userupdate")

	public ModelAndView userupdate(@RequestParam("id") String id) {
		UserRequestDto dto = new UserRequestDto();
		dto.setId(id);
		return new ModelAndView("USR002-01", "user", dao.selectOne(dto));
	}

	@PostMapping("/userupdate")
	public String updateuser(@ModelAttribute("user") @Validated UserBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {

			return "USR002-01";
		}
		if (bean.getPassword().equals(bean.getConfirm())) {
			UserRequestDto dto = new UserRequestDto();
			dto.setId(bean.getId());
			dto.setName(bean.getName());
			dto.setPassword(bean.getPassword());
			dao.select(dto);
			
			int res = dao.update(dto);
			if (res > 0)
				map.addAttribute("msg", "Update successfully");
			else
				map.addAttribute("err", "Insert fail");
			return "USR002-01";
		}
		map.addAttribute("err", "Password are not match");

		return "USR002-01";
	}

	@GetMapping(value = "userreset")
	public String reset() {
		return "redirect:/usersearch";
	}
}
