import fs from "fs"

export default class Dir{

	static list(dirPath, callback){
		let read = (resolve, reject) => {
			const arr = [];
			fs.readdir(dirPath, (err, dir) => {
				for(let filePath of dir)
					if(fs.statSync(dirPath + "/" + filePath).isDirectory())
						arr.push(filePath.toString())

				resolve(arr);
			});
		};

		if(!callback)
			return new Promise(read);

		return read(callback);
	}

}