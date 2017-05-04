import File from "../File";
import SuperFile from "./SuperFile";
import React from "react";
import ItemEditor from "../../views/editor/ItemEditor";

export default class ItemFile extends SuperFile{

	static typeName = "物品";
	static path = () => window.localStorage["path"] + "/script/data/item/";

	static typeMap = [
		{name: "道具", type: "item", color: "#7b94a0"},
		{name: "装备", type: "equipment", color: "#7ba087"},
		{name: "符卡", type: "spellcard", color: "#8fa07b"},
		{name: "料理", type: "cooking", color: "#9b7ba0"},
		{name: "任务道具", type: "task", color: "#7c7ba0"},
		{name: "材料", type: "material", color: "#a0907b"},
		{name: "笔记", type: "note", color: "#a07b7b"}
	]

	parse() {
		try{
			let obj = eval("(" + this.fileText + ")");
			this.label = obj.name;

			let type = ItemFile.findType(obj.type);
			this.prefix = {
				text: type.name,
				color: type.color
			};

		}catch(e){
			this.label = "<无法解析>";
			this.errorFormat = true;
		}
	}

	static findType(str){
		for(let type of this.typeMap)
			if(type.name.toLocaleLowerCase().indexOf(str.toLocaleLowerCase()) >= 0 || type.type.toLocaleLowerCase().indexOf(str.toLocaleLowerCase()) >= 0)
				return type;

			return {name: "", type: ""};
	}

	static create(id){
		return super.create(id, this.path(), new ItemFile());
	}

	$static = ItemFile;

	editor(ref){
		return <ItemEditor file={this} ref={ref}/>
	}

	static type = "item";
}