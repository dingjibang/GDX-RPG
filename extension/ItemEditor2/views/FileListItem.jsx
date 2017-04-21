import React from 'react';
import {List, ListItem} from 'material-ui/List';
import AddIcon from 'material-ui/svg-icons/content/add';
import SearchIcon from 'material-ui/svg-icons/action/subject';
import IconButton from 'material-ui/IconButton';
import FileListItem from './FileListItem';
import TextField from 'material-ui/TextField';


export default class FileListType extends React.Component {

	state = {open: false, files: [], searchValue: ""};

	componentWillMount() {
		this.reader = require("../scripts/FileType/" + this.props.type).default;
		this.load();
	}

	search = value => {
		// let files = this.state.files, filter = [];
		//
		// console.log(this.refs.listItem);
		// console.log(this.refs.searchField);
		// console.log(files[0]);
		// console.log(this.props.children);
		//
		// this.setState({showFiles: filter})
		this.setState({searchValue: value})
	}

	 load() {
		this.reader.list(filesName => {

			let files = [];

			let currentRead = -1;
			let read = () => {
				if(++currentRead >= filesName.length){
					this.setState({files: files});
					for(let file of files){
						if(!file.post)
							break;
						file.post(files);
					}
				}else{
					this.reader.read(filesName[currentRead], file => {
						files.push(file);
						setTimeout(read, ~~Math.random() * 100);
					});
				}
			};
			read();

			const subItem = [];

			for(let file of files)
				subItem.push(<FileListItem key={j} fileName={file} type={this.props.type} search={() => this.state.searchValue}/>);

			this.setState({searchValue: ""});
		});
	}

	render() {
		let files = this.state.files, filtedFiles = [];
		let searchValue = this.state.searchValue;
		for(let file of files){
			let text = (file.prefix ? ("[" + file.prefix.text + "]") : "") + file.label + "  " + file.fileName;
			let include = searchValue.length == 0 || false;

			for(let value of searchValue.split(" ")) {
				if (value.trim().length == 0)
					continue;

				if (text.indexOf(value) >= 0){
					include = true;
				}else{
					include = false;
					break;
				}
			}

			if(include)
				filtedFiles.push(file);
		}

		filtedFiles = filtedFiles.map((file, index) =>
			<ListItem
				ref = "item"
				key={index}
				primaryText={
					<div style={file.errorFormat ? {color: "red"} : {}}>
						{file.prefix ? <span className={"list-item-prefix"} style={{color: file.prefix.color}}>[{file.prefix.text}]</span> : ""}
						{file.label}
						&nbsp;&nbsp;
						<span style={{color: "lightgray"}}>{file.fileName}</span>
					</div>
				}
			/>
		);

		return (
			<ListItem
				ref="listItem"
				className={"list-item-head"}
				primaryText={
					<div className="list-type-head">
						{this.reader.type}
						<span>
							{filtedFiles.length == files.length ? "" : filtedFiles.length + " / "}
							{this.state.files.length}
						</span>
					</div>
				}
				primaryTogglesNestedList={true}
				leftIcon={
					<div className="file-list-toolbar">
						<IconButton onClick={e => e.stopPropagation()}><AddIcon/></IconButton>
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