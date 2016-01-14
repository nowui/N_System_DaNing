package com.nowui.daning.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.Page;
import com.nowui.module.service.LogService;
import com.nowui.module.service.PageService;

@Controller
@RequestMapping("/portal")
public class PortalController {

	@Autowired
	private PageService pageService;

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	public ModelAndView basic(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/portal/basic");
		log.setParameter("");
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/portal/basic", map);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/portal/view");
		log.setParameter("");
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/portal/view", map);
	}

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> find(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/portal/find");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			Page pageJson = (Page) JSON.parseObject(parameter, Page.class);

			Page pageParameter = new Page();
			pageParameter.setValue(pageJson.getValue());

			Page page = pageService.find(pageParameter);

			return new BaseResponseImpl(true, "", page).build();
		} catch (Exception e) {
			log.setType(LogType.ERROR.toString());
			log.setContent(e.toString());

			return new BaseResponseImpl(false, e.toString(), null).build();
		} finally {
			try {
				logService.save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
