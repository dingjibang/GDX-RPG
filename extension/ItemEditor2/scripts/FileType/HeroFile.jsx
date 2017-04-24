import File from "../File";
import SuperFile from "./SuperFile";

export default class HeroFile extends SuperFile{

	static typeName = "角色";
	static path = () => window.localStorage["path"] + "/script/data/hero/";

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new HeroFile();

			file.fileName = name;
			file.path = absPath;
			file.fileText = e;

			try{
				let obj = eval("(" + e + ")");
				file.label = obj.name;
				file.prefix = {
					text: obj.tag,
					color: "#" + obj.color.substr(0, 6)
				}
			}catch(e){
				file.label = "<无法解析>";
				file.errorFormat = true;
			}

			callback(file);
		});
	}

	type = "hero";
	static type = "hero";
}