import File from "../File";
import SuperFile from "./SuperFile";

export default class AchievementFile extends SuperFile{

	static typeName = "成就";
	static path = () => window.localStorage["path"] + "/script/data/achievement/";

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new AchievementFile();

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

	type = "achievement";
	static type = "achievement";
}