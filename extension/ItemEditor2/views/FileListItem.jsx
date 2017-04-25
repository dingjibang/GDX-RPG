import React from 'react';
import {ListItem, Popover, MenuItem, Divider} from 'material-ui';


export default class FileListItem extends React.Component {

	state = {open: false, anchorEl: null, select: false}

	constructor(props){
		super(props);
	}

	postItem(dom, file) {
		if(!dom)
			return;

		dom.ondblclick = () => this.openFile();

		dom.onclick = () => {
			window.E.files.select(this.props.file.type, this.props.file.fileName);
		};

		dom.oncontextmenu = e => {
			dom.onclick();
			this.setState({open: true, anchorEl: dom, left: e.clientX, top: e.clientY})
		};
	}

	select(flag) {
		if(flag == undefined)
			return this.state.select;

		this.setState({select: flag});
	}

	get(){
		return this.props.file;
	}


	get target() {
		return {
			getBoundingClientRect: () => ({
				left: this.state.left - (window.innerWidth * 0.2),
				top: this.state.top
			}),
			offsetWidth: 200,
			offsetHeight: 0
		}
	}

	openFile(){
		this.setState({open: false})
	}

	deleteFile(){
		this.setState({open: false})
		this.props.file.delete(() => {
			E.snack("删除文件成功", "撤销", this.undeleteFile, this.props.file);
			E.files.find(this.props.file.type).refresh();
		});

	}

	undeleteFile(file){
		if(!file)
			return;

		file.save(() => {
			E.files.find(file.type).add(file);
		});
	}

	openDialog() {
		this.setState({open: false})
		const {shell} = require('electron');
		shell.showItemInFolder(this.props.file.path)
	}

	render() {
		let file = this.props.file;
		let index = this.props.index;

		return (
			<div ref={(ref) => this.postItem(ref, file)}>
				<ListItem
					className={this.state.select ? "select" : ""}
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
				<Popover
					className={"popmenu"}
					useLayerForClickAway={false}
					open={this.state.open}
					anchorEl={this.target}
					anchorOrigin={{horizontal: 'right', vertical: 'top'}}
					targetOrigin={{horizontal: 'left', vertical: 'top'}}
					onRequestClose={() => this.setState({open: false})}
				>
						<MenuItem primaryText="打开" onClick={() => this.openFile()}/>
						<MenuItem primaryText="删除所选文件" onClick={() => this.deleteFile()}/>
						<Divider />
						<MenuItem primaryText="刷新" onClick={() => {this.setState({open: false}); E.files.reload(file.type);}}/>
						<MenuItem primaryText="刷新全部" onClick={() => {this.setState({open: false}); E.files.reload();}}/>
						<Divider />
						<MenuItem primaryText="打开文件所在位置" onClick={() => this.openDialog()}/>
				</Popover>

			</div>
		)
	}

}