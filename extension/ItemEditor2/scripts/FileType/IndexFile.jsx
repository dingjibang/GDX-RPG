import File from "../File";
import SuperFile from "./SuperFile";

export default class IndexFile extends SuperFile{

	static typeName = "图鉴";
	static path = () => window.localStorage["path"] + "/script/data/index/";

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new IndexFile();

			file.fileName = name;
			file.path = absPath;
			file.fileText = e;

			try{
				let obj = eval("(" + e + ")");
				file.label = obj.name;
				file.prefix = {
					text: isNaN(+obj.path) ? "角色" : "敌人",
					color: isNaN(+obj.path) ? "#7cc126" : "#c16226"
				}
			}catch(e){
				file.label = "<无法解析>";
				file.errorFormat = true;
			}

			callback(file);
		});
	}

	static create(id){
		return super.create(id, this.path(), new IndexFile());
	}


	type = "index";
	static type = "index";
}