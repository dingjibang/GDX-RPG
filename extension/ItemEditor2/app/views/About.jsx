import React from 'react';
import {Dialog} from 'material-ui';
import {FlatButton} from 'material-ui';
import {MuiThemeProvider} from 'material-ui';

export default class About extends React.Component {
	
	state = {open: true}
	
	close = () => {
		this.setState({open: false});
	}
	
	componentWillReceiveProps() {
		this.setState({open: true});
	}

	render() {
		
		const actions = [
			<FlatButton
				label="确定"
				primary={true}
				keyboardFocused={true}
				onClick={this.close}
			/>,
		];
		
		return (
			<MuiThemeProvider>
				<Dialog
					title="GDX-RPG Assets Editor"
					actions={actions}
					modal={false}
					open={this.state.open}
					onRequestClose={this.close}
				>
					2017 <a href="http://www.rpsg-team.com/">RPSG-TEAM</a>  秘封异闻录(GDX-RPG) 资源编辑器
					<br/>
					可视化的编辑游戏内的资源。
					<br/><br/>
					使用NodeJS + Electron作为后端，Material-UI + React作为前端的技术尝 <del>屎</del> 鲜项目。
					<br/>
					慢慢写，慢慢学_(:3」∠)_<del>你问我游戏呢？什么？游戏是什么hhhh</del>
					<br/>
					<del>现在已经超过普通的用个c#或者写个普通的html项目的所浪费的时间了</del>
				</Dialog>
			</MuiThemeProvider>
		);
	}
}