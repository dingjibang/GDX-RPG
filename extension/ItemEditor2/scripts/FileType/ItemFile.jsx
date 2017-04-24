import File from "../File";
import SuperFile from "./SuperFile";

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

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new ItemFile();

			file.fileName = name;
			file.path = absPath;
			file.fileText = e;

			try{
				let obj = eval("(" + e + ")");
				file.label = obj.name;

				let type = this.findType(obj.type);
				file.prefix = {
					text: type.name,
					color: type.color
				};

			}catch(e){
				file.label = "<无法解析>";
				file.errorFormat = true;
			}

			callback(file);
		});
	}

	static findType(str){
		for(let type of this.typeMap)
			if(type.name.toLocaleLowerCase().indexOf(str.toLocaleLowerCase()) >= 0 || type.type.toLocaleLowerCase().indexOf(str.toLocaleLowerCase()) >= 0)
				return type;

			return {name: "", type: ""};
	}

	type = "item";
	static type = "item";
}