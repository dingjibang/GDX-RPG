import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import HeroEditor from "../../views/editor/HeroEditor";

export default class HeroFile extends SuperFile{

	static typeName = "角色";
	static path = () => window.localStorage["path"] + "/script/data/hero/";

	parse(){
		try{
			let obj = eval("(" + this.fileText + ")");
			this.label = obj.name;
			this.prefix = {
				text: obj.tag,
				color: "#" + obj.color.substr(0, 6)
			}
			this.errorFormat = false;
		}catch(e){
			this.label = "(无法解析)";
			this.errorFormat = true;
		}
	}

	static create(id){
		return super.create(id, this.path(), new HeroFile());
	}

	$static = HeroFile;

	editor(ref){
		return <HeroEditor file={this} ref={ref}/>
	}

	static type = "hero";
}