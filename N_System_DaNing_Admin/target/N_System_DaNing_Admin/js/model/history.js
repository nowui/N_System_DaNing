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