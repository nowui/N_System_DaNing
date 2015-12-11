package com.nowui.module.web;

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
import com.nowui.base.request.impl.BackRequestImpl;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.module.model.Resource;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.service.ResourceService;
import com.nowui.module.service.LogService;

@Controller
@RequestMapping("/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView index(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/resource/index");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("/resource/index");
	}

	@RequestMapping(value = "/json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> json(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/resource/json");
		log.setParameter(parameter);

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);
			Resource resourceJson = (Resource) JSON.parseObject(parameter, Resource.class);

			Integer total = resourceService.count(resourceJson);
			List<Resource> resourceList = resourceService.findList(resourceJson, backRequest.getPage(), backRequest.getLimit());

			return new BaseResponseImpl(true, "", total, resourceList).build();
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

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/resource/add");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();

		BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);
		map.put("backRequest", backRequest);

		return new ModelAndView("/resource/detail", map);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/resource/edit");
		log.setParameter(parameter);

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);
			map.put("backRequest", backRequest);

			Resource resourceJson = (Resource) JSON.parseObject(parameter, Resource.class);

			Resource resource = resourceService.find(resourceJson);

			map.put("resource", resource);
		} catch (Exception e) {
			log.setType(LogType.ERROR.toString());
			log.setContent(e.toString());
		} finally {
			try {
				logService.save(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("/resource/detail", map);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/resource/save");
		log.setParameter(parameter);

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			Resource resourceJson = (Resource) JSON.parseObject(parameter, Resource.class);

			resourceService.save(resourceJson);

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
		log.setAction("/resource/update");
		log.setParameter(parameter);

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			Resource resourceJson = (Resource) JSON.parseObject(parameter, Resource.class);

			resourceService.update(resourceJson);

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

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/resource/delete");
		log.setParameter(parameter);

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);

			Resource resourceParameter = new Resource();
			resourceParameter.setIdList(JSON.parseArray(backRequest.getIds(), Integer.class));
			resourceService.delete(resourceParameter);

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