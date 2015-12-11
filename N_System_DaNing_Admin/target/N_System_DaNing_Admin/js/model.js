Ext.define("M_Tree", {
	extend: "Ext.data.TreeModel",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "text",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}]
});

Ext.define("M_Log", {
	extend: "Ext.data.Model",
	fields: [{
		name: "name",
		type: "string"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "sort",
		type: "string"
	}, {
		name: "isNull",
		type: "bool"
	}]
});

Ext.define("M_Code", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "action",
		type: "string"
	}, {
		name: "parameter",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}, {
		name: "createUserId",
		type: "string"
	}]
});

Ext.define("M_Category", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "parentId",
		type: "string"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}, {
		name: "value1",
		type: "string"
	}, {
		name: "value2",
		type: "string"
	}, {
		name: "value3",
		type: "string"
	}, {
		name: "picture",
		type: "string"
	}, {
		name: "summary",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "sort",
		type: "string"
	}, {
		name: "path",
		type: "string"
	}]
});

Ext.define("M_User", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "objectId",
		type: "string"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "account",
		type: "string"
	}, {
		name: "password",
		type: "string"
	}, {
		name: "roleId",
		type: "string"
	}, {
		name: "roleName",
		type: "string"
	}, {
		name: "postId",
		type: "string"
	}, {
		name: "postName",
		type: "string"
	}, {
		name: "departmentId",
		type: "string"
	}, {
		name: "departmentName",
		type: "string"
	}, {
		name: "jpushRegistrationId",
		type: "string"
	}, {
		name: "captcha",
		type: "string"
	}]
});

Ext.define("M_Teacher", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "postId",
		type: "string"
	}, {
		name: "postName",
		type: "string"
	}, {
		name: "departmentId",
		type: "string"
	}, {
		name: "departmentName",
		type: "string"
	}, {
		name: "userId",
		type: "string"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "account",
		type: "string"
	}]
});

Ext.define("M_Article", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "title",
		type: "string"
	}, {
		name: "categoryId",
		type: "string"
	}, {
		name: "categoryName",
		type: "string"
	}, {
		name: "keyword",
		type: "string"
	}, {
		name: "picture",
		type: "string"
	}, {
		name: "summary",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "state",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}, {
		name: "createUserId",
		type: "string"
	}, {
		name: "createUserName",
		type: "string"
	}, {
		name: "auditUserId",
		type: "string"
	}, {
		name: "auditUserName",
		type: "string"
	}]
});

Ext.define("M_Page", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}]
});

Ext.define("M_Notice", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "categoryId",
		type: "string"
	}, {
		name: "title",
		type: "string"
	}, {
		name: "department",
		type: "string"
	}, {
		name: "user",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "attachment",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}, {
		name: "createUserId",
		type: "string"
	}, {
		name: "reader",
		type: "string"
	}]
});

Ext.define("M_Workflow", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "state",
		type: "string"
	}]
});

Ext.define("M_Step", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "workflowId",
		type: "string"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}, {
		name: "user",
		type: "string"
	}, {
		name: "condition",
		type: "string"
	}, {
		name: "sort",
		type: "string"
	}]
});

Ext.define("M_Variable", {
	extend: "Ext.data.Model",
	fields: [{
		name: "name",
		type: "string"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}, {
		name: "compare",
		type: "string"
	}]
});

Ext.define("M_Leave", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "starTime",
		type: "string"
	}, {
		name: "endTime",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}, {
		name: "userId",
		type: "string"
	}, {
		name: "instanceId",
		type: "string"
	}, {
		name: "instanceState",
		type: "string"
	}, {
		name: "stepName",
		type: "string"
	}]
});

Ext.define("M_Task", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "instanceId",
		type: "string"
	}, {
		name: "userId",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}]
});

Ext.define("M_History", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "instanceId",
		type: "string"
	}, {
		name: "userId",
		type: "string"
	}, {
		name: "auditResult",
		type: "string"
	}, {
		name: "auditMessage",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}]
});

Ext.define("M_Class", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "sort",
		type: "string"
	}, {
		name: "gradeId",
		type: "string"
	}]
});

Ext.define("M_Grade", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "sort",
		type: "string"
	}]
});

Ext.define("M_Purchase", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "classId",
		type: "string"
	}, {
		name: "className",
		type: "string"
	}, {
		name: "money",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}, {
		name: "userId",
		type: "string"
	}, {
		name: "instanceId",
		type: "string"
	}, {
		name: "instanceState",
		type: "string"
	}, {
		name: "auditResult",
		type: "string"
	}, {
		name: "stepName",
		type: "string"
	}]
});

Ext.define("M_Maintenance", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "classId",
		type: "string"
	}, {
		name: "className",
		type: "string"
	}, {
		name: "content",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}, {
		name: "userId",
		type: "string"
	}, {
		name: "userName",
		type: "string"
	}, {
		name: "instanceId",
		type: "string"
	}, {
		name: "instanceState",
		type: "string"
	}, {
		name: "auditResult",
		type: "string"
	}, {
		name: "stepName",
		type: "string"
	}]
});

Ext.define("M_Resource", {
	extend: "Ext.data.Model",
	fields: [{
		name: "id",
		type: "int"
	}, {
		name: "name",
		type: "string"
	}, {
		name: "type",
		type: "string"
	}, {
		name: "value",
		type: "string"
	}, {
		name: "size",
		type: "string"
	}, {
		name: "folderId",
		type: "string"
	}, {
		name: "createUserId",
		type: "string"
	}, {
		name: "createTime",
		type: "string"
	}]
});