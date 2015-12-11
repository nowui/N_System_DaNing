package com.nowui.daning.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

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
import com.nowui.module.model.User;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.service.UserService;
import com.nowui.module.service.LogService;

@Controller
public class ApplicationController {

	@Autowired
	private UserService userService;

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String root(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/index.html";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/index");
		log.setParameter("");
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		User user = (User) session.getAttribute(Helper.SessionUser);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		return new ModelAndView("/index", map);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/login");
		log.setParameter("");
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/login", map);
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> check(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/check");
		log.setParameter(parameter);

		try {
			log.setType(LogType.INFO.toString());
			log.setContent("");

			User userJson = (User) JSON.parseObject(parameter, User.class);

			//if(! userJson.getCaptcha().equals(session.getAttribute(Helper.SessionCaptcha))) {
				//return new BaseResponseImpl(false, "验证码不正确!", null).build();
			//}

			User user = userService.findByAccountAndPasswordAndType(userJson.getAccount(), userJson.getPassword(), userJson.getType());

			if(user == null) {
				return new BaseResponseImpl(false, "用户名或者密码错误!", null).build();
			} else {
				Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken();
				token.setUsername(user.getId().toString());
				token.setPassword(user.getPassword().toCharArray());
				token.setRememberMe(false);

				session.setAttribute(Helper.SessionUser, user);

				try{
					subject.login(token);
		        }
		        catch (IncorrectCredentialsException ice) {
		            ice.printStackTrace();
		        }
		        catch (LockedAccountException lae) {
		            lae.printStackTrace();
		        }
		        catch (AuthenticationException ae) {
		            ae.printStackTrace();
		        }
		        catch(Exception e) {
		            e.printStackTrace();
		        }

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				map.put("roleId", user.getRoleId());
				return new BaseResponseImpl(true, "", map).build();
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