package com.nowui.daning.web;

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

import com.alibaba.fastjson.JSON;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.module.model.Class;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.service.ClassService;
import com.nowui.module.service.LogService;

@Controller
@RequestMapping("/class")
public class ClassController {

	@Autowired
	private LogService logService;

	@Autowired
	private ClassService classService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/class/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			Class classJson = (Class) JSON.parseObject(parameter, Class.class);

			Integer total = classService.count(classJson);
			List<Class> classList = classService.findList(classJson, 0, 0);

			return new BaseResponseImpl(true, "", total, classList).build();
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
