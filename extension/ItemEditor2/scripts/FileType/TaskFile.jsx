import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import TaskEditor from "../../views/editor/TaskEditor";

export default class TaskFile extends SuperFile{

	static typeName = "任务";
	static path = () => window.localStorage["path"] + "/script/data/task/";

	parse(){
		try{
			this.label = eval("(" + this.fileText + ")").name;
		}catch(e){
			this.label = "<无法解析>";
			this.errorFormat = true;
		}
	}

	static create(id){
		return super.create(id, this.path(), new TaskFile());
	}

	$static = TaskFile;

	editor(ref){
		return <TaskEditor file={this} ref={ref}/>
	}

	static type = "task";
}