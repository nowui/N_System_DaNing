Ext.define("N_Viewport", {
	extend: "Ext.container.Viewport",
    layout: "border"
});

Ext.define("N_Hidden", {
	extend: "Ext.form.field.Hidden"
});

Ext.define("N_Component", {
	extend: "Ext.Component"
});

Ext.define("N_Container", {
	extend: "Ext.container.Container",
	border: false,
	style : "background-color:#ffffff"
});

Ext.define("N_Panel", {
	extend: "Ext.panel.Panel",
	border: false
});

Ext.define("N_Tab", {
	extend: "Ext.tab.Panel",
	border: false
});

Ext.define("N_Tree", {
	extend: "Ext.tree.Panel",
	border: false,
	animate: false,
	rootVisible: false
});

Ext.define("N_FieldSet", {
	extend: "Ext.form.FieldSet"
});

Ext.define("N_CheckboxModel", {
	extend: "Ext.selection.CheckboxModel"
});

Ext.define("N_Toolbar", {
	extend: "Ext.toolbar.Toolbar",
	border: false,
	style : "background-color:#5fa2dd"
});

Ext.define("N_Action", {
	extend: "Ext.Action"
});

Ext.define("N_Menu", {
	extend: "Ext.menu.Menu"
});

Ext.define("N_Label", {
	extend: "Ext.form.Label"
});

Ext.define("N_Button", {
	extend: "Ext.button.Button"
});

Ext.define("N_Form", {
	extend: "Ext.form.Panel",
	border: false,
	padding: "3 8 8 8",
	cls: "color_white",
	autoScroll: true
});

Ext.define("N_North", {
	extend: "Ext.form.Panel",
	defaults:{
	    padding: "0 0 0 8"
	},
	style: "border-bottom: 1px solid silver;"
});

Ext.define("N_Text", {
	extend: "Ext.form.field.Text",
	width: textWidth,
	labelWidth: 70,
	blankText: "不能为空!",
    constructor: function(config) {
		if (typeof(config.enterFunction) != "undefined") {
			config.listeners = {
				specialkey: function(field, e) {
					if (e.getKey() == e.ENTER) {
		                config.enterFunction()
		            }
				}
			};
		}
        this.callParent([config]);
    }
});

Ext.define("N_Date", {
	extend: "Ext.form.field.Date",
	width: textWidth,
	labelWidth: 70,
	blankText: "不能为空!",
	format: "Y-m-d",
    constructor: function(config) {
		if (typeof(config.enterFunction) != "undefined") {
			config.listeners = {
				specialkey: function(field, e) {
					if (e.getKey() == e.ENTER) {
		                config.enterFunction()
		            }
				}
			};
		}
        this.callParent([config]);
    }
});

Ext.define("N_HtmlEditor", {
	extend: "Ext.panel.Panel",
	layout: "hbox",
	margin: "0 0 10 0",
	constructor: function(config) {
		if (typeof(config.height) == "undefined") {
			config.height = 570;
		}

		this.editor = Ext.create("Ext.form.field.HtmlEditor", {
			id: config.id + "_htmleditor",
			width: "100%",
			height: config.height - 20,
			plugins: [{
				xtype: "Ext.util.Observable",
	        	langTitle: "Insert Image",
	    		langIconCls: "x-fa fa-photo",
				init: function(view){
			        var scope = this;
			        view.on("render", function () {
			            scope.onRender(view);
			        });
			    },
			    onRender: function (view) {
			        var scope = this;
			        view.getToolbar().insert(21, {
			            iconCls: scope.langIconCls,
			            tooltip: {
			                title: scope.langTitle
			            },
			            handler: function () {

							//scope.showImageWindow(view);

							//view.insertAtCursor("123");

							showMessage("该功能即将开放!");
			            }
			        });
			    },
				showImageWindow: function(view) {
					loadScript({
						url: "/resource/help",
						params: {
							function: "aaa"
						}
					});
				}
			}]
		});

		config.items = [{
			xtype: "label",
			margin: '10 0 0 0',
			width: 75,
			text: config.fieldLabel
		}, {
			flex: 1,
			style: {
				border: "1px",
			    borderColor: "#d0d0d0",
			    borderStyle: "solid"
			},
			items: [this.editor]
		}];

		config.getValue = function() {
			return this.editor.getValue().replace("\u200b", "");
		}

		config.setValue = function(value) {
			this.editor.setValue(value);
		}

        this.callParent([config]);
    }
});

Ext.define("N_Number", {
	extend: "Ext.form.field.Number",
	width: textWidth,
	labelWidth: 70,
    maxValue: 99999,
    minValue: 0,
    value: 0,
	blankText: "不能为空!",
    constructor: function(config) {
		if (typeof(config.enterFunction) != "undefined") {
			config.listeners = {
				specialkey: function(field, e) {
					if (e.getKey() == e.ENTER) {
		                config.enterFunction()
		            }
				}
			};
		}
        this.callParent([config]);
    }
});

Ext.define("N_TextArea", {
	extend: "Ext.form.field.TextArea",
	width: textWidth,
	labelWidth: 70,
	blankText: "不能为空!",
	grow: true
});

Ext.define("N_ComboBox" ,{
	extend: "Ext.form.ComboBox",
	width: textWidth,
	labelWidth: 70,
	queryMode: "local",
    forceSelection: true,
    editable: false,
    valueField: "id",
    displayField: "name",
	blankText: "不能为空!"
});

Ext.define("N_Window", {
	extend: "Ext.window.Window",
		width: 1024,
		height: 683,
		modal: true,
		resizable: false,
		draggable: false,
		maximizable: false,
		closeAction: "destroy",
		layout: "fit"
});

Ext.define("N_TreeStore", {
	extend: "Ext.data.TreeStore",
    constructor: function(config) {
    	config.model = "M_Tree";
        config.proxy = {
			type: "ajax",
	        url: config.url,
	        paramsAsJson: true,
    		headers: {
				"Content-Type": "application/json;charset=utf-8"
			},
    		actionMethods: {
                create: "POST",
                read: "POST",
                update: "POST",
                destroy: "POST"
            },
	        reader: {
	        	type: "json",
	        	root: "data",
	        	successProperty: "result",
	        	messageProperty: "message"
	        }
		};
		config.autoLoad = true;
		config.listeners = {
			load: function(store, records, successful, operation) {
				if(!successful) {
					showError();
				}
			},
			beforeload: function(store, options) {
				if (typeof(config.params) != "undefined") {
					Ext.apply(store.proxy.extraParams, config.params);
				}
			}
		};
        this.callParent([config]);
    }
});

Ext.define("N_Store", {
	extend: "Ext.data.Store",
	pageSize: 20,
    constructor: function(config) {
        config.proxy = {
    		type: "ajax",
    		url: config.url,
	        paramsAsJson: true,
    		headers: {
				"Content-Type": "application/json;charset=utf-8"
			},
    		actionMethods: {
                create: "POST",
                read: "POST",
                update: "POST",
                destroy: "POST"
            },
    		reader: {
    			type: "json",
	        	root: "data",
	        	successProperty: "result",
	        	messageProperty: "message",
    			totalProperty: "total"
    		}
        },
        this.callParent([config]);
    }
});

Ext.define("N_PagingToolbar", {
	extend: "Ext.PagingToolbar",
	displayInfo: true,
    afterPageText: "页 , 共 {0} 页",
	beforePageText: "第",
    displayMsg: "第 {0} - {1} 条 , 共 {2} 条数据",
    emptyMsg: "没有数据"
});

Ext.define("N_Grid", {
	extend: "Ext.grid.Panel",
	border: false,
    constructor: function(config) {
        if (typeof(config.isShowPage) == "undefined" || config.isShowPage == true) {
	        config.bbar = Ext.create("N_PagingToolbar", {
	            store: config.store
	        });
        }

        this.callParent([config]);
    }
});

Ext.define("N_Trigger", {
	extend: "Ext.form.field.Trigger",
	width: textWidth,
	labelWidth: 70,
	triggerCls: "x-fa fa-plus",
	onTriggerClick: function() {

	}
});