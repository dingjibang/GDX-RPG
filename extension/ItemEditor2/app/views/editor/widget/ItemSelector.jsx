import React from 'react';
import BaseWidget from "./BaseWidget";
import {Dialog, FlatButton, List, ListItem, RaisedButton, TextField} from "material-ui";
import File from "../../../scripts/File";

import cs from 'classnames';
import FileListItems from "../../FileListItems";
import FileListItem from "../../FileListItem";

import SearchIcon from 'material-ui/svg-icons/action/subject';

export default class ItemSelector extends BaseWidget{
	constructor(props){
		super(props);

		this.state.open = false;

		this.state.files = [];

		this.state.searchValue = "";
	}

	componentWillMount(){
		let files = E.files.find(this.props.type).list().map(dom => dom.get());

		if(this.props.filter){
			let _files = [];
			for(let file of files)
				try{
					if(this.props.filter(eval("(" + file.fileText + ")")))
						_files.push(file);
				}catch(e){}

			files = _files;
		}

		this.setState({files: files});
	}

	defaultValue(){
		return null;
	}

	check(){
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	error(flag){
	}

	select(){
		this.setState({open: false});
	}

	preFileName(filename){
		return this.props.id ? +filename.split(".")[0] : filename;
	}

	selectFile(file){
		let obj = this.has(file);

		let created = !obj;

		let to = this.state.obj;

		if(!obj && this.props.count)
			obj = {id: this.preFileName(file.fileName), count: 0};

		if(!obj && !this.props.count)
			obj = this.preFileName(file.fileName);

		if(!to && this.props.multi)
			to = [];

		if(this.props.count)
			obj.count += 1;

		if(this.props.multi && created)
			to.push(obj);

		if(!this.props.multi)
			to = obj;

		this.change(to);


	}

	unselectFile(file){
		let obj = this.has(file);
		if(!obj || !this.state.obj)
			return;

		let remove = !this.props.count;
		if(this.props.count){
			remove = obj.count === 1;
			obj.count -= 1;
		}

		if(this.props.multi && remove)
			this.state.obj = this.state.obj.filter(f => f != obj);

		if(!this.props.multi && remove)
			this.state.obj = null;

		this.setState(this.state);


	}

	search(value){
		this.setState({searchValue: value})
	}

	has(obj, via){
		if(!this.state.obj)
			return null;

		if(this.props.multi && !via){
			for(let via of this.state.obj){
				let value = this.has(obj, via);
				if(value)
					return value;
			}
			return null;
		}

		if(!via)
			via = this.state.obj;

		if(this.props.count && this.preFileName(obj.fileName) == via.id)
			return via;

		return this.preFileName(obj.fileName) == via ? via : null;
	}


	draw(){

		let fileName = "选择项目";
		if(this.state.obj && this.state.obj.length != 0){
			if(this.props.multi){
				fileName = this.state.obj.length + "个项目";
			}else{
				try{
					const dom = E.files.find(this.props.type, this.state.obj);
					if(dom)
						fileName = dom.get().label;
				}catch(e){
					console.error(e);
				}
			}
		}



		const actions = [];
		if(!this.props.required)
			actions.push(
				<FlatButton
					label="清空"
					primary={true}
					onTouchTap={() => {this.change(this.defaultValue()); this.select();}}
				/>
			);

		actions.push(
			<FlatButton
				label="选择"
				primary={true}
				onTouchTap={() => this.select()}
			/>
		);

		let path = window.localStorage.getItem("path") + "/";
		let files = [];
		const searchValue = this.state.searchValue;


		for(let i = 0; i < this.state.files.length; i++){
			let file = this.state.files[i];
			let obj = this.has(file);
			let current = !!obj;

			let text = FileListItems.text(file);

			if(!searchValue || text.toLowerCase().indexOf(searchValue.toLowerCase()) >= 0)
				files.push(
					<ListItem
						className={cs({"file": true, "current": current})}
						data-fileName={file}
						onClick={() => this.selectFile(file)}
						onContextMenu={() => this.unselectFile(file)}
						key={i}
						ref={"files" + i}
						primaryText={
							<div>
								{FileListItem.readableDom(file, true)}
								{current && this.props.count ? <span className="selector-count">(已选择{obj.count}个)</span> : ""}
							</div>
						}
					/>
				)
		}

		return (
			<div className="file-selector">
				<div className="file-selector-desc"><span>{this.props.desc}{this.props.required ? <span className="required">*</span> : null}</span></div>
				<RaisedButton label={fileName} onTouchTap={() => this.setState({open: true})}/>


				<Dialog
					title={"选择项目 (左键选择，右键取消)"}
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