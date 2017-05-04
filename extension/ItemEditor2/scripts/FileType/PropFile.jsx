import File from "../File";
import SuperFile from "./SuperFile";

export default class PropFile extends SuperFile{

	static typeName = "游戏配置";
	static path = () => window.localStorage["path"] + "/script/data/prop/";

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new PropFile();

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
		return super.create(id, this.path(), new PropFile());
	}

	type = "prop";
	static type = "prop";
	static addable = false;
}