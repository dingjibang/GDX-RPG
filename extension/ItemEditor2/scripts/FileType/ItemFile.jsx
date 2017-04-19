import File from "../File";

export default class ItemFile{

	type = "物品";
	files = [/**{name: item name, text: file text, fileName: file name}*/];

	fileChange = () => {};

	//call this.fileChange when loaded
	init() {
		var path = window.localStorage["path"] + "/script/data/item/";

		File.list(path, files => {
			this.files = files.map(e => {return {name: e, type: this.type, fileName: e, text: "", path: path + e, errorFormat: false}});
			this.fileChange();

			var current = -1;
			var read = () => {
				if(!this.files[++current])
					return;

				var file = this.files[current];

				File.read(file.path, e => {
					file.text = e;
					try{
						file.name = eval("(" + e + ")").name;
					}catch(e){
						file.name = "[无法解析] " + file.fileName;
						file.errorFormat = true;
					}
					this.fileChange();
					setTimeout(read, 50);//慢点读别tm卡住hhhh
				});
			};

			setTimeout(read, ~~(Math.random() * 500));
		});

	}

	toggleSearch() {
		console.log("?");
	}
}