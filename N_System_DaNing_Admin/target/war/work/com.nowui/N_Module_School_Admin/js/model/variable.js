Ext.define("M_Variable", {
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
		name: "compare",
		type: "string"
	}]
});