import File from "../File";


export default class SuperFile{
	label = "";
	fileName = "";
	path = "";
	errorFormat = false;
	fileText = "";
	prefix = null;

	deleted = false;

	static list(callback) {
		File.list(this.path(), files => {
			callback(files);
		});
	}

	save(callback) {
		File.write(this.path, this.fileText.toString(), () => {
			this.deleted = false;

			if(callback)
				callback();
		});
	}

	delete(callback) {
		File.delete(this.path, () => {
			this.deleted = true;

			if(callback)
				callback();
		});
	}

	static create(id, path, file){
		file.label = "新建文件";
		file.fileName = id + ".grd";
		file.path = path + file.fileName;

		return file;
	}
}