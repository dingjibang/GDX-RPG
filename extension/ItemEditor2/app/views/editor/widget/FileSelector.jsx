import React from 'react';
import BaseWidget from "./BaseWidget";
import {Dialog, FlatButton, List, ListItem, RaisedButton, TextField} from "material-ui";
import File from "../../../scripts/File";

import cs from 'classnames';

import SearchIcon from 'material-ui/svg-icons/action/subject';

export default class FileSelector extends BaseWidget{
	constructor(props){
		super(props);

		this.state.open = false;

		this.state.files = [];

		this.state.searchValue = "";
	}

	componentWillMount(){
		File.list(window.localStorage.getItem("path") + "/" + this.props.path, filesName => this.setState({files: filesName}));
	}

	defaultValue(){
		return "";
	}

	check(){
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	error(flag){
	}

	select(){
		this.setState({open: false});
	}

	selectFile(file){
		this.change(file);
	}

	search(value){
		this.setState({searchValue: value})
	}

	render(){

		const fileName = this.state.obj || "选择文件";


		const actions = [
			<FlatButton
				label="取消"
				primary={true}
				onTouchTap={() => {this.change(this.state.origin); this.select();}}
			/>,
			<FlatButton
				label="选择"
				primary={true}
				onTouchTap={() => this.select()}
			/>,
		];

		let path = window.localStorage.getItem("path") + "/";
		let files = [];
		const searchValue = this.state.searchValue;
		for(let i = 0; i < this.state.files.length; i++){
			let file = this.state.files[i];

			if(!searchValue || file.toLowerCase().indexOf(searchValue.toLowerCase()) >= 0)
				files.push(
					<ListItem
						className={cs({"file": true, "current": this.state.obj === file})}
						data-fileName={file}
						onClick={() => this.selectFile(file)}
						key={i}
						ref={"files" + i}
						primaryText={file}
						secondaryText={<div className={cs({"current": this.state.obj === file})}>{path + file}</div>}
					/>
				)
		}

		return (
			<div className="file-selector">
				<div className="file-selector-desc">{this.props.desc}</div>
				<RaisedButton label={fileName} onTouchTap={() => {this.state.origin = this.state.obj; this.setState({open: true})}}/>


				<Dialog
					title="选择文件"
					actions={actions}
					open={this.state.open}
					onRequestClose={() => this.select()}
					bodyClassName="file-selector-outer"
				>
					<div className={"filter selector-filter"}>
						<SearchIcon/>
						<TextField hintText="输入名称以过滤..." className={"filter-input"} onChange={e => this.search(e.target.value)} value={this.state.searchValue}/>
					</div>

					<List>
						{files}
					</List>
				</Dialog>
			</div>
		)
	}
}