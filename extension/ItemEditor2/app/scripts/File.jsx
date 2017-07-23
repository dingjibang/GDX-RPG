import fs from "fs"

export default class File{

	static read(path, callback){
		fs.readFile(path, {encoding:'utf-8'}, (err, bytes) => {callback(bytes)});
	}

	static list(dirPath, callback){
		let read = (resolve, reject) => {
			const arr = [];
			fs.readdir(dirPath, (err, dir) => {
				for(let filePath of dir)
					arr.push(filePath.toString())

				resolve(arr);
			});
		};

		if(!callback)
			return new Promise(read);

		return read(callback);
	}

	static write(path, text, callback){
		fs.writeFile(path, text, callback);
	}

	static delete(path, callback){
		fs.unlink(path, callback);
	}

}