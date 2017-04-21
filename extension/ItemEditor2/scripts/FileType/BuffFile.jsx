import File from "../File";

export default class BuffFile{

	static type = "BUFF";
	static path = () => window.localStorage["path"] + "/script/data/buff/";

	static list(callback) {
		File.list(this.path(), files => {
			callback(files);
		});
	}

	static read(name, callback){
		var absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new BuffFile();

			file.fileName = name;
			file.path = absPath;
			file.fileText = e;

			try{
				let obj = eval("(" + e + ")");
				file.label = obj.name;
				file.prefix = {
					text: obj.type == "buff" ? "Buff" : "Debf",
					color: obj.type == "buff" ? "#67d490" : "#d46767"
				}
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
	type = "buff";
}