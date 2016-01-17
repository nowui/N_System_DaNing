package com.nowui.base.web;

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
import com.alibaba.fastjson.JSONObject;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.module.model.Category;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.Notice;
import com.nowui.module.model.Task;
import com.nowui.module.model.User;
import com.nowui.module.service.CategoryService;
import com.nowui.module.service.LogService;
import com.nowui.module.service.NoticeService;
import com.nowui.module.service.TaskService;
import com.nowui.module.service.UserService;

@Controller
public class ApplicationController {

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/index.html";
	}

	@RequestMapping(value = "/ionic", method = RequestMethod.GET)
	public ModelAndView ionic(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/ionic", map);
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/index", map);
	}

	@RequestMapping(value = "/application", method = RequestMethod.GET)
	public ModelAndView application(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/application", map);
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/contact", map);
	}

	@RequestMapping(value = "/mine", method = RequestMethod.GET)
	public ModelAndView mine(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/mine", map);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/login", map);
	}

	@RequestMapping(value = "/count", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> count(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/count");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			JSONObject objectJson = JSON.parseObject(parameter);

			Integer userId = Integer.valueOf(objectJson.get("userId").toString());

			User userParameter = new User();
			userParameter.setId(userId);

			User user = userService.find(userParameter);

			Task taskJson = (Task) JSON.parseObject(parameter, Task.class);
			taskJson.setUserId(userId);

			Integer taskCount = taskService.count(taskJson);

			Category noticeCategoryParameter = new Category();
			noticeCategoryParameter.setValue("category_notice");
			Category noticeCategory = categoryService.find(noticeCategoryParameter);

			Notice noticeParameter = new Notice();
			noticeParameter.setCategoryId(noticeCategory.getId());
			noticeParameter.setUser(user.getId().toString());
			noticeParameter.setDepartment(user.getDepartmentId().toString());
			noticeParameter.setReader(userId.toString());
			Integer noticeCount = noticeService.count(noticeParameter);

			Category meetingCategoryParameter = new Category();
			meetingCategoryParameter.setValue("category_meeting");
			Category meetingCategory = categoryService.find(meetingCategoryParameter);

			Notice meetingParameter = new Notice();
			meetingParameter.setCategoryId(meetingCategory.getId());
			meetingParameter.setUser(user.getId().toString());
			meetingParameter.setDepartment(user.getDepartmentId().toString());
			meetingParameter.setReader(userId.toString());
			Integer meetingCount = noticeService.count(meetingParameter);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("taskCount", taskCount);
			map.put("noticeCount", noticeCount);
			map.put("meetingCount", meetingCount);

			return new BaseResponseImpl(true, "", map).build();
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