import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import AchievementEditor from "../../views/editor/AchievementEditor";

export default class AchievementFile extends SuperFile{

	static typeName = "成就";
	static path = () => window.localStorage["path"] + "/script/data/achievement/";

	parse(){
		try{
			this.label = eval("(" + this.fileText + ")").name;
			this.errorFormat = false;
		}catch(e){
			this.label = "(无法解析)";
			this.errorFormat = true;
		}
	}

	static create(id){
		return super.create(id, this.path(), new AchievementFile());
	}

	$static = AchievementFile;

	editor(ref){
		return <AchievementEditor file={this} ref={ref}/>
	}

	static type = "achievement";
}