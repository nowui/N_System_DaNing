var loadingText = "加载中...";
var windowSecondWidth = 880;
var windowSecondHeight = 547;
var textWidth = 300;
var columnWidth = 200;

var destroyObject = function(name) {
	try {
		window.eval("delete " + name);
		window.eval(name + " = undefined");
	} catch(e) {
		alert(e);
	}
}

var getUrlParam = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) {
		return unescape(r[2]);
	} else {
		return null;
	}
}

var isNullOrEmpty = function(string) {
	if(string == "" || typeof(string) == "undefined" || string == null) {
		return true;
	} else {
		return false;
	}
}

String.prototype.replaceAll = function(s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

String.prototype.Trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

function mask() {
	var mask = Ext.getBody().mask(loadingText);
	mask.setStyle('z-index', Ext.WindowMgr.zseed + 1000);
}

function unmask() {
	Ext.getBody().unmask();
}

function showSuccess() {
	Ext.toast("提交成功!");
}

function showError() {
	Ext.MessageBox.show({
		title: "系统提示",
		msg: "发生错误啦,与我们联系!",
		icon: Ext.Msg.INFO,
		buttons: Ext.MessageBox.OK
	});
}

function showMessage(message) {
	Ext.MessageBox.show({
		title: "系统提示",
		msg: message,
		icon: Ext.Msg.INFO,
		buttons: Ext.MessageBox.OK
	});
}

function showEmpty() {
	Ext.MessageBox.show({
		title: "系统提示",
		msg: "请至少选择一项内容!",
		icon: Ext.Msg.INFO,
		buttons: Ext.MessageBox.OK
	});
}

function checkEmpty(records) {
	if (records.length == 0) {
		showEmpty();

		return true;
	}
	return false;
}

function loadScript(config) {
	mask();

	Ext.Ajax.request({
		method: "POST",
		headers: {
			"Content-Type": "application/json;charset=utf-8"
		},
		url: config.url,
		params: Ext.JSON.encode(config.params),
		success: function(response) {
			try {
				if(response.responseText == "") {
					removeFromIndexCenter(config);

					showError();
				} else {
					window.eval(response.responseText);
				}
			} catch(e) {
				removeFromIndexCenter(config);

				if(response.responseText.indexOf("<!DOCTYPE html>") != -1) {
					showMessage("已经超时,请重新登陆!");
	            } else {
					showMessage(e);
	            }
			}

			unmask();
		},
		failure: function(response) {
			removeFromIndexCenter(config);

			showError();

			unmask();
		}
	});
}

function removeFromIndexCenter(config) {
	if (typeof(config.id) != "undefined") {
		Ext.getCmp("system_index_center").remove(Ext.getCmp(config.id));
	}
}

function ajaxRequest(config) {
	mask();

	Ext.Ajax.request({
		method: "POST",
		headers: {
			"Content-Type": "application/json;charset=utf-8"
		},
		url: config.url,
		params: Ext.JSON.encode(config.params),
		success: function(response) {
			unmask();

			try {
				var data = Ext.JSON.decode(response.responseText);
				if(data.result) {
					config.success(data);

					showSuccess();
				} else {
					showMessage(data.message.replace("java.lang.Exception:", ""));
				}
			} catch(e) {
				if(response.responseText.indexOf("<!DOCTYPE html>") != -1) {
					showMessage("已经超时,请重新登陆!");
	            } else {
					showMessage(e);
	            }
			}
		},
		failure: function(response) {
			unmask();

			if (typeof(config.failure) != "undefined") {
				config.failure(response);
			}

			showError();
		}
	});
}

Ext.apply(Ext.util.Format, {
	formatterState: function(val) {
		if(val == "ON") {
			return "<span style=\"color: green;\">开启</span>";
		} else {
			return "<span style=\"color: red;\">关闭</span>";
		}
	},
	formatterVariableType: function(val) {
		if(val == "NORMAL") {
			return "变量";
		} else {
			return "用户";
		}
	},
	formatterVariableCompare: function(val) {
		if(val == "GT") {
			return "大于";
		} else if(val == "EQ") {
			return "等于";
		} else if(val == "IT") {
			return "小于";
		}
	},
	formatterLeaveType: function(val) {
		if(val == "SJ") {
			return "事假";
		} else if(val == "BJ") {
			return "病假";
		} else if(val == "NJ") {
			return "年假";
		} else if(val == "TX") {
			return "调休";
		} else if(val == "HJ") {
			return "婚假";
		} else if(val == "CJ") {
			return "产假";
		}
	},
	formatterDay: function(val) {
		return val.substring(0, 10)
	},
	formatterWorkflowType: function(val) {
		if(val == "LEAVE") {
			return "请假";
		} else if(val == "PURCHASE") {
			return "采购";
		} else if(val == "MAINTENANCE") {
			return "维修";
		}
	},
	formatterTaskType: function(val) {
		if(val == "LEAVE") {
			return "请假申请单";
		} else if(val == "PURCHASE") {
			return "采购申请单";
		} else if(val == "MAINTENANCE") {
			return "维修申请单";
		}
	},
	formatterFormAuditResult: function(val) {
		if(val == "YES") {
			return "正常";
		} else if(val == "NO") {
			return "拒绝";
		}
	},
	formatterStepType: function(val) {
		if(val == "SERIAL") {
			return "串签";
		} else if(val == "PARALLEL") {
			return "并签";
		}
	}
})