import React from 'react';
import {List, ListItem} from 'material-ui/List';
import FileListItems from "./FileListItems";


export default class FileList extends React.Component {

	state = {
		open: true
	};

	constructor(props){
		super(props);

		this.type = ["ItemFile", "AchievementFile", "BuffFile", "EnemyFile", "HeroFile", "IndexFile", "TaskFile", "PropFile"];

		E.files = {
			_files: [],
			reload: (type, soft = true) => {
				for(let file of E.files._files)
					if(type == undefined || type == file.type())
						file.load(soft);
			},
			find: (type, name) => {
				if(name != undefined){ // find file dom(FileListItem)
					let outer = E.files.find(type);

					let fileResult = null;
					for(let fileDom of outer.list()){
						let file = fileDom.get();
						if(file.$static.type == type && file.fileName == name)
							fileResult = fileDom;
					}

					return fileResult;

				}else{//find group dom(FileListItems)
					for(let fileList of E.files._files){
						if(fileList.type() == type)
							return fileList;
					}

				}

				return null;
			},
			select: (type, name, pitch = false) => {
				if(type && name){
					for(let fileDom of E.files.allDom())
						fileDom.select(false);

					let outer = E.files.find(type);
					let dom = E.files.find(type, name);

					outer.open(true);

					if(dom)
						dom.select(true);


					if(pitch)
						E.files.pitch();

				}else{

					for(let fileDom of E.files.allDom())
						if(fileDom.select())
							return fileDom;

					return null;
				}
			},
			pitch: () => setTimeout(() => {
				let _dom = document.getElementsByClassName("select");
				if(!_dom || _dom.length == 0)
					return;

				let dom = document.getElementsByClassName("file-list")[0];
				let begin = dom.scrollTop;
				let end = document.getElementsByClassName("select")[0].offsetTop - 200;

				let step = (end - begin) / 10;
				let i = 0;

				let id = setInterval(() => {
					dom.scrollTop = step + dom.scrollTop;
					i++;
					if(i >= 10)
						clearInterval(id);
				}, 13);
			}, 100),
			allDom: () => {
				var result = [];
				for (let fileList of E.files._files)
					for (let fileDom of fileList.list())
						result.push(fileDom)

				return result;
			}
		}
	}

	render() {
		const items = [];

		for(var i = 0; i < this.type.length; i ++)
			items.push(<FileListItems key={i} type={this.type[i]}/>);

		return (
			<div>
				<List>
					{items}
				</List>
			</div>
		);
	}

	getByName(name){
		return this.type.filter(t => t.name == name)[0];
	}
}