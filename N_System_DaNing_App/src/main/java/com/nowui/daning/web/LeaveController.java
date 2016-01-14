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
import com.nowui.module.model.Leave;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.User;
import com.nowui.module.service.LeaveService;
import com.nowui.module.service.LogService;
import com.nowui.module.service.UserService;

@Controller
@RequestMapping("/leave")
public class LeaveController {

	@Autowired
	private LogService logService;

	@Autowired
	private LeaveService leaveService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/leave/index", map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/leave/detail", map);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/leave/view", map);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/leave/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);

			JSONObject objectJson = JSON.parseObject(parameter);

			Integer userId = objectJson.getInteger("userId");

			Leave leaveParameter = new Leave();
			leaveParameter.setUserId(userId);

			List<Leave> leaveList = leaveService.findList(leaveParameter, backRequest.getPage(), backRequest.getLimit());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Leave Leave : leaveList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", Leave.getId());
				map.put("userName", Leave.getUserName());
				map.put("stepName", Leave.getStepName());
				map.put("instanceId", Leave.getInstanceId());
				map.put("instanceState", Leave.getInstanceState());
				map.put("auditResult", Leave.getAuditResult());
				map.put("createTime", Helper.formatShortDateTime(Leave.getCreateTime()));

				list.add(map);
			}

			return new BaseResponseImpl(true, "", list).build();
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
		log.setAction("/leave/find");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			Leave leaveJson = (Leave) JSON.parseObject(parameter, Leave.class);

			Leave leaveParameter = new Leave();
			leaveParameter.setId(leaveJson.getId());

			Leave leave = leaveService.find(leaveParameter);

			return new BaseResponseImpl(true, "", leave).build();
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

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/leave/save");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			JSONObject jsonObject = JSON.parseObject(parameter);

			Integer userId = jsonObject.getInteger("userId");

			boolean isAudit = jsonObject.getBooleanValue("isAudit");

			User userParameter = new User();
			userParameter.setId(userId);

			User user = userService.find(userParameter);

			Leave leaveJson = (Leave) JSON.parseObject(parameter, Leave.class);

			leaveService.save(leaveJson, user, isAudit);

			return new BaseResponseImpl(true, "", null).build();
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/leave/update");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			JSONObject jsonObject = JSON.parseObject(parameter);

			Integer userId = jsonObject.getInteger("userId");

			boolean isAudit = jsonObject.getBooleanValue("isAudit");

			User userParameter = new User();
			userParameter.setId(userId);

			User user = userService.find(userParameter);

			Leave leaveJson = (Leave) JSON.parseObject(parameter, Leave.class);

			leaveService.update(leaveJson, user, isAudit);

			return new BaseResponseImpl(true, "", null).build();
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
