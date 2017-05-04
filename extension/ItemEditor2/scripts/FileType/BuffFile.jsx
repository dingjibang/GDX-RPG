import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import BuffEditor from "../../views/editor/BuffEditor";

export default class BuffFile extends SuperFile{

	static typeName = "BUFF";
	static path = () => window.localStorage["path"] + "/script/data/buff/";

	parse(){
		try{
			let obj = eval("(" + this.fileText + ")");
			this.label = obj.name;
			this.prefix = {
				text: obj.type == "buff" ? "Buff" : "Debf",
				color: obj.type == "buff" ? "#67d490" : "#d46767"
			}
		}catch(e){
			this.label = "<无法解析>";
			this.errorFormat = true;
		}
	}

	static create(id){
		return super.create(id, this.path(), new BuffFile());
	}

	$static = BuffFile;

	editor(ref){
		return <BuffEditor file={this} ref={ref}/>
	}

	static type = "buff";
}