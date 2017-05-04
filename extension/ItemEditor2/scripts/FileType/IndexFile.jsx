import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import IndexEditor from "../../views/editor/IndexEditor";

export default class IndexFile extends SuperFile{

	static typeName = "图鉴";
	static path = () => window.localStorage["path"] + "/script/data/index/";

	parse(){
		try{
			let obj = eval("(" + this.fileText + ")");
			this.label = obj.name;
			this.prefix = {
				text: isNaN(+obj.path) ? "角色" : "敌人",
				color: isNaN(+obj.path) ? "#7cc126" : "#c16226"
			}
		}catch(e){
			this.label = "<无法解析>";
			this.errorFormat = true;
		}
	}

	static create(id){
		return super.create(id, this.path(), new IndexFile());
	}


	$static = IndexFile;

	editor(ref){
		return <IndexEditor file={this} ref={ref}/>
	}

	static type = "index";
}