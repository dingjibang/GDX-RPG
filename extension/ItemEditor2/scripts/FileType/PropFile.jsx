import File from "../File";
import SuperFile from "./SuperFile";

export default class TaskFile extends SuperFile{

	static typeName = "游戏配置";
	static path = () => window.localStorage["path"] + "/script/data/prop/";

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new TaskFile();

			file.fileName = name;
			file.path = absPath;
			file.fileText = e;

			try{
				let obj = eval("(" + e + ")");
				file.label = obj.name;
			}catch(e){
				file.label = "<无法解析>";
				file.errorFormat = true;
			}

			callback(file);
		});
	}

	type = "prop";
	static type = "prop";
}