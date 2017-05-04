import React from 'react';
import {List, ListItem, IconButton, TextField} from 'material-ui';
import AddIcon from 'material-ui/svg-icons/content/add';
import SearchIcon from 'material-ui/svg-icons/action/subject';
import FileListItem from './FileListItem';

export default class FileListItems extends React.Component {

	state = {open: false, files: [], searchValue: ""};

	constructor(props) {
		super(props);

		E.files._files.push(this);
	}

	componentWillMount() {
		this.reader = require("../scripts/FileType/" + this.props.type).default;
		this.load();
	}

	list() {
		let result = [];

		for(let i = 0; i > -1; i++){
			var id = "item" + i;
			if(this.refs[id] != undefined)
				result.push(this.refs[id])
			else
				break;
		}

		return result;
	}

	type() {
		return this.reader.type;
	}

	open(flag){
		if(flag == undefined)
			return this.state.open;
		this.setState({open: flag})
	}

	search(value){
		this.setState({searchValue: value})
	}

	load(softLoad = true) {
		this.reader.list(filesName => {
			let originList = this.state.files || [];
			let files = [];

			if(softLoad){
				let filesNameExcude = [];
				for(let fileName of filesName){
					for(let file of originList){
						if(file.fileName == fileName){
							files.push(file);
							filesNameExcude.push(fileName);
						}
					}
				}
				this.setFiles(files);
				filesName = filesName.filter(name => filesNameExcude.indexOf(name) < 0);
			}

			let currentRead = -1;
			let read = () => {
				if(++currentRead >= filesName.length){
					for(let file of files){
						if(!file.post)
							break;
						file.post(files);
						this.setFiles(files);
					}
				}else{
					this.reader.read(filesName[currentRead], file => {
						files.push(file);
						this.setFiles(files);
						read();
					});
				}
			};
			read();

			this.setState({searchValue: ""});
		});
	}

	setFiles(files) {
		this.setState({files: files})
	}

	refresh() {
		this.setFiles(this.state.files.filter(e => !e.deleted));
	}

	add(file) {
		this.setFiles([...this.state.files, file]);
	}

	create() {
		this.open(true);
		let id = this.nextID();
		let file = this.reader.create(id);
		file.save(() => {
			this.add(file);
			E.files.select(this.reader.type, id + ".grd", true);
		});
	}

	nextID() {
		let files = this.state.files.sort(this.sort);
		for(let file of files){
			let id = +(file.fileName.split(".")[0]) + 1;
			if(files.filter(f => f.fileName.split(".")[0] == id).length == 0)
				return id;
		}
		return 1;
	}

	sort = (a, b) => {
		a = a.fileName.split(".")[0];
		b = b.fileName.split(".")[0]

		if(isNaN(+a) && isNaN(+b))
			return a < b ? -1 : 1;
		if(isNaN(+a))
			return 1;
		if(isNaN(+b))
			return -1;

		return +(a) - +(b);
	};

	render() {
		let files = this.state.files;
		let filtedFiles = [];
		let searchValue = this.state.searchValue;

		for(let file of files){
			let text = (file.prefix ? ("[" + file.prefix.text + "]") : "") + file.label + "  " + file.fileName;
			let include = searchValue.length == 0 || false;

			for(let value of searchValue.split(" ")) {
				if (value.trim().length == 0)
					continue;

				include = text.toLocaleLowerCase().indexOf(value.toLocaleString()) >= 0;
				if (!include)
					break;
			}

			if(include)
				filtedFiles.push(file);
		}

		filtedFiles = filtedFiles.sort(this.sort)

		filtedFiles = filtedFiles.map((file, index) => <FileListItem ref={"item" + index} file={file} index={index} key={index} reader={this.reader}/>);

		var searching = filtedFiles.length != files.length;
		return (
			<ListItem
				ref="listItem"
				className={"list-item-head"}
				primaryText={
					<div className="list-type-head">
						{this.reader.typeName}
						<span className={searching ? "searching" : ""}>
							{searching ? filtedFiles.length + " / " : ""}
							{this.state.files.length}
						</span>
					</div>
				}
				primaryTogglesNestedList={true}
				leftIcon={
					<div className="file-list-toolbar">
						{this.reader.addable == undefined || this.reader.addable == true ? <IconButton onClick={e => {e.stopPropagation(); this.create();}}><AddIcon/></IconButton> : <IconButton disabled><AddIcon/></IconButton>}
					</div>
				}
				open={this.state.open}
				onClick={() => this.setState({open: !this.state.open})}
				key={1}
				nestedItems={[
					<ListItem key={-1} primaryText={
						<TextField hintText="输入名称以过滤..." className={"filter-input"} onChange={e => this.search(e.target.value)} value={this.state.searchValue}/>
					} className={"filter"} leftIcon={<SearchIcon />} />,
					...filtedFiles
				]}
			/>
		);
	}
}