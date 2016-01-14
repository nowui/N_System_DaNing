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
import com.nowui.base.utility.Helper;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.User;
import com.nowui.module.model.UserType;
import com.nowui.module.service.LogService;
import com.nowui.module.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private LogService logService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView password(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/user/password", map);
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> check(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/user/check");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			User userJson = (User) JSON.parseObject(parameter, User.class);

			System.out.println(Helper.MD5(Helper.AppKey + userJson.getPassword()));

			User user = userService.findByAccountAndPasswordAndType(userJson.getAccount(), userJson.getPassword(), UserType.TEACHER.toString());

			if(Helper.isNullOrEmpty(user)) {
				return new BaseResponseImpl(false, "帐号或者密码错误!", null).build();
			} else {
				if(! Helper.isNullOrEmpty(userJson.getJpushRegistrationId())) {
					userService.updateJpushRegistrationId(user.getId(), userJson.getJpushRegistrationId());
				}

				return new BaseResponseImpl(true, "", user).build();
			}
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
