import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import EnemyEditor from "../../views/editor/EnemyEditor";

export default class EnemyFile extends SuperFile{

	static typeName = "敌人";
	static path = () => window.localStorage["path"] + "/script/data/enemy/";

	parse(){
		try{
			let obj = eval("(" + this.fileText + ")");
			if(obj.type == "simple"){
				this.label = obj.name;
			}else{
				this.prefix = {
					text: "组",
					color: "#aaccdd"
				}
				this.label = "多个";
			}
			this.errorFormat = false;
		}catch(e){
			this.label = "(无法解析)";
			this.errorFormat = true;
		}
	}

	/**
	 * 部分内容需要从其他文件（上下文）中获取，所以需要后处理数据
	 * */
	post(files){
		try{
			let label = [];
			let group = eval("(" + this.fileText + ")")["group"];
			if(!group)
				return;

			for(let file of files)
				for(let groupId of group)
					if(file.fileName.split(".")[0] == groupId)
						label.push(eval("(" + file.fileText + ")").name);

			this.label = label.join("、");
		}catch(e){
			this.label = "(无法解析)";
			this.errorFormat = true;
		}

	}

	static create(id){
		return super.create(id, this.path(), new EnemyFile());
	}


	$static = EnemyFile;

	editor(ref){
		return <EnemyEditor file={this} ref={ref}/>
	}

	static type = "enemy";
}