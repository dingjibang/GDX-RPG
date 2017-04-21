import File from "../File";

export default class IndexFile{

	static type = "图鉴";
	static path = () => window.localStorage["path"] + "/script/data/index/";

	static list(callback) {
		File.list(this.path(), files => {
			callback(files);
		});
	}

	static read(name, callback){
		var absPath = this.path() + name;
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

	fileName = "";
	path = "";
	errorFormat = false;
	fileText = "";
	label = "";
	prefix = null;
	type = "index";
}