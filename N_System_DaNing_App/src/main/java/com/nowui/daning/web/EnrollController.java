package com.nowui.daning.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/enroll")
public class EnrollController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/enroll/index", map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/enroll/detail", map);
	}

}
