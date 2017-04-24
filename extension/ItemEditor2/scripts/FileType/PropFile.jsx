import File from "../File";

export default class TaskFile{

	static typeName = "游戏配置";
	static path = () => window.localStorage["path"] + "/script/data/prop/";

	static list(callback) {
		File.list(this.path(), files => {
			callback(files);
		});
	}

	static read(name, callback){
		var absPath = this.path() + name;
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

	fileName = "";
	path = "";
	errorFormat = false;
	fileText = "";
	label = "";
	prefix = null;
	type = "task";
}