import File from "../File";
import SuperFile from "./SuperFile";

export default class BuffFile extends SuperFile{

	static typeName = "BUFF";
	static path = () => window.localStorage["path"] + "/script/data/buff/";

	static read(name, callback){
		let absPath = this.path() + name;
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

	static create(id){
		return super.create(id, this.path(), new BuffFile());
	}

	type = "buff";
	static type = "buff";
}