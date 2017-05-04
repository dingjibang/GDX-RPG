import File from "../File";
import SuperFile from "./SuperFile";

export default class TaskFile extends SuperFile{

	static typeName = "任务";
	static path = () => window.localStorage["path"] + "/script/data/task/";

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

	static create(id){
		return super.create(id, this.path(), new TaskFile());
	}

	type = "task";
	static type = "task";
}