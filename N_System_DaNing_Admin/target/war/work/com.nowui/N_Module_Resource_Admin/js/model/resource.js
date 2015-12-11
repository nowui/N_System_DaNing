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