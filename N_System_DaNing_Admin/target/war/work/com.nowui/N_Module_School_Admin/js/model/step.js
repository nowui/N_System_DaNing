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