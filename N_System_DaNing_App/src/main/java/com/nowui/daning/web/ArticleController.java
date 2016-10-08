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
import com.nowui.base.request.impl.BackRequestImpl;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.module.model.Article;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.service.ArticleService;
import com.nowui.module.service.LogService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private LogService logService;

	@Autowired
	private ArticleService articleService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/article/index");
		log.setParameter("");
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/article/index", map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/article/detail");
		log.setParameter("");
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			logService.save(log);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/article/detail", map);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/article/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);
			Article articleParameter = new Article();

			List<Article> articleList = articleService.findList(articleParameter, backRequest.getPage(), backRequest.getLimit());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(Article article : articleList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", article.getId());
				map.put("title", article.getTitle());
				map.put("summary", article.getSummary());
				map.put("picture", article.getPicture());

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
		log.setAction("/article/find");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			Article articleJson = (Article) JSON.parseObject(parameter, Article.class);

			Article articleParameter = new Article();
			articleParameter.setId(articleJson.getId());

			Article article = articleService.find(articleParameter);

			return new BaseResponseImpl(true, "", article).build();
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
