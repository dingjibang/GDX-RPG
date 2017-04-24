import File from "../File";

export default class HeroFile{

	static typeName = "角色";
	static path = () => window.localStorage["path"] + "/script/data/hero/";

	static list(callback) {
		File.list(this.path(), files => {
			callback(files);
		});
	}

	static read(name, callback){
		var absPath = this.path() + name;
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

	fileName = "";
	path = "";
	errorFormat = false;
	fileText = "";
	label = "";
	prefix = null;
	type = "hero";
}