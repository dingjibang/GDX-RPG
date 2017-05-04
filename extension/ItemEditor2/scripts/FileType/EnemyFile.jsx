import File from "../File";
import SuperFile from "./SuperFile";

export default class EnemyFile extends SuperFile{

	static typeName = "敌人";
	static path = () => window.localStorage["path"] + "/script/data/enemy/";

	static read(name, callback){
		let absPath = this.path() + name;
		File.read(absPath, e => {
			let file = new EnemyFile();

			file.fileName = name;
			file.path = absPath;
			file.fileText = e;

			try{
				let obj = eval("(" + e + ")");
				if(obj.type == "simple"){
					file.label = obj.name;
				}else{
					file.prefix = {
						text: "组",
						color: "#aaccdd"
					}
					file.label = "多个";
				}
			}catch(e){
				file.label = "<无法解析>";
				file.errorFormat = true;
			}

			callback(file);
		});
	}

	/**
	 * 部分内容需要从其他文件（上下文）中获取，所以需要后处理数据
	 * */
	post(files){
		try{
			let label = [];
			let group = eval("(" + this.fileText + ")")["group"];
			if(!group)
				return;

			for(let file of files)
				for(let groupId of group)
					if(file.fileName.split(".")[0] == groupId)
						label.push(eval("(" + file.fileText + ")").name);

			this.label = label.join("、");
		}catch(e){
			this.label = "<无法解析>";
			this.errorFormat = true;
		}

	}

	static create(id){
		return super.create(id, this.path(), new EnemyFile());
	}


	type = "enemy";
	static type = "enemy";
}