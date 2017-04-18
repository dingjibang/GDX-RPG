import React from 'react';
import { FlatButton } from 'material-ui';
import { Paper } from 'material-ui';
import { Menu } from 'material-ui';
import { MenuItem } from 'material-ui';
import IconMenu from './IconMenu';
import IconButton from 'material-ui/IconButton';
import Divider from 'material-ui/Divider';
import Setting from 'material-ui/svg-icons/action/settings';
import ArrowDropRight from 'material-ui/svg-icons/navigation-arrow-drop-right';
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert';
import ReactDOM from 'react-dom';

import About from './About'

const style = {
	top: '100px'
};

export default class Menubar extends React.Component {
	
	render() {
		return(
			<Paper zDepth={1} className="menubar">
				<IconMenu className="menuctx" iconButtonElement={<FlatButton label="文件"/>} anchorOrigin={{horizontal: 'left', vertical: 'top'}} targetOrigin={{horizontal: 'left', vertical: 'top'}}>
					<MenuItem primaryText="设定assets文件夹位置..." rightIcon={<Setting />} />
					<MenuItem primaryText="打开assets文件夹" />
					<Divider />
					<MenuItem primaryText="保存当前文件" secondaryText="Ctrl+S"/>
					<Divider />
					<MenuItem primaryText="退出" />
					</IconMenu>
				<IconMenu className="menuctx" iconButtonElement={<FlatButton label="编辑"/>} anchorOrigin={{horizontal: 'left', vertical: 'top'}} targetOrigin={{horizontal: 'left', vertical: 'top'}}>
					<MenuItem primaryText="撤销" secondaryText="Ctrl+Z"/>
					<MenuItem primaryText="重做" secondaryText="Ctrl+Y"/>
					<Divider />
					<MenuItem primaryText="全选" secondaryText="Ctrl+A"/>
					<MenuItem primaryText="删除" secondaryText="Delete"/>
					<Divider />
					<MenuItem primaryText="剪切" secondaryText="Ctrl+A"/>
					<MenuItem primaryText="复制" secondaryText="Ctrl+A"/>
					<MenuItem primaryText="复制当前文件路径"/>
					<MenuItem primaryText="粘贴" secondaryText="Ctrl+V"/>
					<Divider />
					<MenuItem primaryText="格式化当前代码" secondaryText="Ctrl+F"/>
				</IconMenu>
				<IconMenu className="menuctx" iconButtonElement={<FlatButton label="视图"/>} anchorOrigin={{horizontal: 'left', vertical: 'top'}} targetOrigin={{horizontal: 'left', vertical: 'top'}}>
					<MenuItem primaryText="切换代码/编辑窗口"/>
				</IconMenu>
				<IconMenu className="menuctx" iconButtonElement={<FlatButton label="工具"/>} anchorOrigin={{horizontal: 'left', vertical: 'top'}} targetOrigin={{horizontal: 'left', vertical: 'top'}}>
					<MenuItem primaryText="启动秘封异闻录(GDX-RPG)" secondaryText="F11"/>
					<Divider />
					<MenuItem primaryText="首选项..."/>
				</IconMenu>
				<IconMenu className="menuctx" iconButtonElement={<FlatButton label="其他"/>} anchorOrigin={{horizontal: 'left', vertical: 'top'}} targetOrigin={{horizontal: 'left', vertical: 'top'}}>
					<MenuItem primaryText="关于GDX-RPG资源编辑器..." onTouchTap={() => {ReactDOM.render(<About open={true}/>, document.getElementById("dialog"))}}/>
				</IconMenu>
			</Paper>
		)
	}
}