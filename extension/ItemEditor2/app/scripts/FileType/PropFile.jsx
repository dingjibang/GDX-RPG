import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import PropEditor from "../../views/editor/PropEditor";

export default class PropFile extends SuperFile{

	static typeName = "游戏配置";
	static path = () => window.localStorage["path"] + "/script/data/prop/";

	parse(){
		try{
			let obj = eval("(" + this.fileText + ")");
			this.label = obj.name;
			this.errorFormat = false;
		}catch(e){
			this.label = "(无法解析)";
			this.errorFormat = true;
		}
	}

	static create(id){
		return super.create(id, this.path(), new PropFile());
	}

	$static = PropFile;

	editor(ref){
		return <PropEditor file={this} ref={ref}/>
	}

	static addable = false;
	static type ="prop";
}