import React from 'react';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import TextField from 'material-ui/TextField';

export default class About extends React.Component {
	
	state = {open: true, path: "", buttonDisabled: true, fromMenu: false}

	constructor(props){
		super(props);
	}

	save = () => {
		window.localStorage.setItem("path", this.state.path);
	}

	close = () => {
		this.setState({open: false});
		if(this.props.callback)
			this.props.callback();
	}

	componentWillMount() {
		this.componentWillReceiveProps({open: this.props.open});
	}
	
	componentWillReceiveProps(nextProps) {
		this.setState({path: window.localStorage["path"] || "", buttonDisabled: !window.localStorage["path"], open: true, fromMenu: !!nextProps.open});
	}

	selectFile = () => {
		var path = require('electron').remote.dialog.showOpenDialog({properties: ['openDirectory']});
		if(path && path[0])
			this.setState({path: path[0], buttonDisabled: false});
	};

	render() {
		
		const actions = [
			<FlatButton
				label="确定"
				primary={true}
				keyboardFocused={true}
				onTouchTap={()=>{this.save(); this.close();}}
				disabled={this.state.buttonDisabled}
			/>,
			<FlatButton
				label="取消"
				primary={true}
				keyboardFocused={true}
				onTouchTap={this.close}
				style={{display: this.state.fromMenu ? "inline-block" : "none"}}
			/>
		];

		return (
			<MuiThemeProvider>
				<Dialog
					title="欢迎使用GDX-RPG资源编辑器"
					actions={actions}
					modal={!this.state.fromMenu}
					open={this.state.open}
					onRequestClose={this.close}
				>
					本程序需要获得GDX-RPG的assets文件夹（资源文件夹）的路径位置
					{(this.state.fromMenu) ? <p style={{color: "red"}}>将丢失所有正在编辑的文件</p> : ""}
					<br/>
					<TextField hintText="请选择Assets文件夹路径" style={{width: 400}} onTouchTap={this.selectFile} value={this.state.path}/>
				</Dialog>
			</MuiThemeProvider>
		);
	}
}