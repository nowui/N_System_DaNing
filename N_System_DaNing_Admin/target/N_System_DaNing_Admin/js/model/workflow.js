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