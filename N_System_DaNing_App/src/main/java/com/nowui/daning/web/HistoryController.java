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

import com.alibaba.fastjson.JSON;
import com.nowui.base.response.impl.BaseResponseImpl;
import com.nowui.base.utility.Helper;
import com.nowui.module.model.AuditResult;
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
@RequestMapping("/history")
public class HistoryController {

	@Autowired
	private LogService logService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private InstanceService instanceService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestBody String parameter, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Log log = new Log();
		log.setHttpSession(session);
		log.setRequest(request);
		log.setAction("/history/list");
		log.setParameter(parameter);
		log.setType(LogType.INFO.toString());
		log.setContent("");

		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			History historyJson = (History) JSON.parseObject(parameter, History.class);

			Integer total = historyService.count(historyJson);
			List<History> historyList = historyService.findList(historyJson, 0, 0);
			for(History history : historyList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", history.getId());
				map.put("stepName", history.getStepName());
				map.put("userName", history.getUserName());
				map.put("auditResult", history.getAuditMessage());
				if(history.getStepSort() > 0) {
					if(history.getAuditResult().equals(AuditResult.YES.toString())) {
						map.put("auditResult", "(同意) " + history.getAuditMessage());
					} else if(history.getAuditResult().equals(AuditResult.NO.toString())) {
						map.put("auditResult", "(拒绝) " + history.getAuditMessage());
					}
				}
				map.put("createTime", Helper.formatDateTime(history.getCreateTime()));
				list.add(map);
			}

			Instance instanceParameter = new Instance();
			instanceParameter.setId(historyJson.getInstanceId());
			Instance instance = instanceService.find(instanceParameter);

			Task taskParameter = new Task();
			taskParameter.setInstanceId(historyJson.getInstanceId());
			List<Task> taskList = taskService.findList(taskParameter, 0, 0);
			int i = -1;
			for(Task task : taskList) {
				User userParameter = new User();
				userParameter.setId(task.getUserId());
				User user = userService.find(userParameter);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", i);
				map.put("stepName", instance.getStepName());
				map.put("userName", user.getName());
				map.put("auditResult", "(审核中)");
				map.put("createTime", "");
				list.add(map);

				i--;
			}

			return new BaseResponseImpl(true, "", total, list).build();
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
