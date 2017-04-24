const fs = require("fs");

export default class File{

	static read(path, callback){
		fs.readFile(path, {encoding:'utf-8'}, (err, bytes) => {callback(bytes)});
	}

	static list(dirPath, callback){
		const arr = [];
		fs.readdir(dirPath, (err, dir) => {
			for(let filePath of dir)
				arr.push(filePath.toString())

			callback(arr.sort((a, b) => +(a.split(".")[0]) - +(b.split(".")[0])));
		});
	}

	static write(path, text, callback){
		fs.writeFile(path, text, callback);
	}

	static delete(path, callback){
		fs.unlink(path, callback);
	}

}