package com.nowui.daning.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.nowui.base.request.impl.BackRequestImpl;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.base.utility.Helper;
import com.nowui.module.model.Category;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.Notice;
import com.nowui.module.model.User;
import com.nowui.module.service.CategoryService;
import com.nowui.module.service.LogService;
import com.nowui.module.service.NoticeService;
import com.nowui.module.service.UserService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private LogService logService;

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/notice/index", map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/notice/detail", map);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/notice/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);

			JSONObject objectJson = JSON.parseObject(parameter);

			Integer userId = objectJson.getInteger("userId");

			User userParameter = new User();
			userParameter.setId(userId);

			User user = userService.find(userParameter);

			Category categoryParameter = new Category();
			categoryParameter.setValue("category_notice");

			Category category = categoryService.find(categoryParameter);

			Notice noticeParameter = new Notice();
			noticeParameter.setCategoryId(category.getId());
			noticeParameter.setUser(user.getId().toString());
			noticeParameter.setDepartment(user.getDepartmentId().toString());

			List<Notice> noticeList = noticeService.findList(noticeParameter, backRequest.getPage(), backRequest.getLimit());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Notice notice : noticeList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", notice.getId());
				map.put("title", notice.getTitle());
				map.put("createTime", Helper.formatShortDateTime(notice.getCreateTime()));
				map.put("isRead", ! notice.getReader().contains(":" + user.getId() + "}"));

				list.add(map);
			}

			return new BaseResponseImpl(true, "", list.size(), list).build();
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

	@RequestMapping(value = "/find", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> find(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/notice/find");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			JSONObject objectJson = JSON.parseObject(parameter);

			Integer userId = objectJson.getInteger("userId");

			Notice noticeJson = (Notice) JSON.parseObject(parameter, Notice.class);

			Notice noticeParameter = new Notice();
			noticeParameter.setId(noticeJson.getId());

			Notice notice = noticeService.findByUserId(noticeParameter, userId);

			return new BaseResponseImpl(true, "", notice).build();
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
