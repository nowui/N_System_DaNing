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
import com.nowui.module.model.History;
import com.nowui.module.model.Instance;
import com.nowui.module.model.Log;
import com.nowui.module.model.LogType;
import com.nowui.module.model.Task;
import com.nowui.module.model.User;
import com.nowui.module.service.HistoryService;
import com.nowui.module.service.InstanceService;
import com.nowui.module.service.LogService;
import com.nowui.module.service.TaskService;
import com.nowui.module.service.UserService;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private LogService logService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/task/index", map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ModelAndView detail(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("/task/detail", map);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/task/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			BackRequestImpl backRequest = (BackRequestImpl) JSON.parseObject(parameter, BackRequestImpl.class);

			JSONObject objectJson = JSON.parseObject(parameter);

			Integer userId = objectJson.getInteger("userId");

			JSONObject jsonObject = JSON.parseObject(parameter);

			List<Task> taskList;

			if(jsonObject.getString("type").equals("NO")) {
				Task taskJson = (Task) JSON.parseObject(parameter, Task.class);
				taskJson.setUserId(userId);

				taskList = taskService.findList(taskJson, backRequest.getPage(), backRequest.getLimit());
			} else {
				History historyParameter = new History();
				historyParameter.setUserId(userId);

				List<History> historyList = historyService.findList(historyParameter, 0, 0);

				List<Integer> instanceIdList = new ArrayList<Integer>();
				instanceIdList.add(0);
				for(History history : historyList) {
					if(history.getStepSort() > 0) {
						instanceIdList.add(history.getInstanceId());
					}
				}

				Instance instanceParameter = new Instance();
				instanceParameter.setIdList(instanceIdList);

				List<Instance> instanceList = instanceService.findList(instanceParameter, backRequest.getPage(), backRequest.getLimit());

				taskList = new ArrayList<Task>();
				int i = 0;
				for(Instance instance : instanceList) {
					Task task = new Task();
					task.setId(i);
					task.setInstanceType(instance.getType());
					task.setObjectId(instance.getObjectId());
					task.setCreateUserName(instance.getCreateUserName());
					task.setCreateTime(instance.getCreateTime());
					taskList.add(task);

					i++;
				}
			}

			return new BaseResponseImpl(true, "", taskList).build();
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

	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> audit(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/task/audit");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			Task taskJson = (Task) JSON.parseObject(parameter, Task.class);

			JSONObject objectJson = JSON.parseObject(parameter);

			Integer userId = objectJson.getInteger("userId");

			User userParameter = new User();
			userParameter.setId(userId);

			User user = userService.find(userParameter);

			JSONObject jsonObject = JSON.parseObject(parameter);

			Task task = taskService.find(taskJson);

			taskService.audit(task.getId(), task.getInstanceId(), user.getId(), user.getName(), task.getStepId(), task.getStepSort(), jsonObject.getString("auditResult"), jsonObject.getString("auditMessage"));

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
