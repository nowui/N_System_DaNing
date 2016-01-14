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
import com.nowui.module.model.Purchase;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.User;
import com.nowui.module.service.PurchaseService;
import com.nowui.module.service.LogService;
import com.nowui.module.service.UserService;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

	@Autowired
	private LogService logService;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/purchase/index", map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/purchase/detail", map);
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/purchase/view", map);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/purchase/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);

			Map<String, Object> objectMap = JSON.parseObject(parameter);

			Integer userId = Integer.valueOf(objectMap.get("userId").toString());

			Purchase purchaseParameter = new Purchase();
			purchaseParameter.setUserId(userId);

			List<Purchase> purchaseList = purchaseService.findList(purchaseParameter, backRequest.getPage(), backRequest.getLimit());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Purchase Purchase : purchaseList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", Purchase.getId());
				map.put("userName", Purchase.getUserName());
				map.put("stepName", Purchase.getStepName());
				map.put("instanceId", Purchase.getInstanceId());
				map.put("instanceState", Purchase.getInstanceState());
				map.put("auditResult", Purchase.getAuditResult());
				map.put("createTime", Helper.formatShortDateTime(Purchase.getCreateTime()));

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
		log.setAction("/purchase/find");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			Purchase purchaseJson = (Purchase) JSON.parseObject(parameter, Purchase.class);

			Purchase purchaseParameter = new Purchase();
			purchaseParameter.setId(purchaseJson.getId());

			Purchase purchase = purchaseService.find(purchaseParameter);

			return new BaseResponseImpl(true, "", purchase).build();
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
		log.setAction("/purchase/save");
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

			Purchase purchaseJson = (Purchase) JSON.parseObject(parameter, Purchase.class);

			purchaseService.save(purchaseJson, user, isAudit);

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
		log.setAction("/purchase/update");
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

			Purchase purchaseJson = (Purchase) JSON.parseObject(parameter, Purchase.class);

			purchaseService.update(purchaseJson, user, isAudit);

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
