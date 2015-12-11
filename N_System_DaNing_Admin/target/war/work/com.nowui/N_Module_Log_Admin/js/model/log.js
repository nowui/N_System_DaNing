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