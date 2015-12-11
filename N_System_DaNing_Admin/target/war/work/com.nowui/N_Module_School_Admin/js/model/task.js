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