import React from 'react';
import Archive from 'material-ui/svg-icons/Content/archive';
import {white} from 'material-ui/styles/colors';

export default class NoFile extends React.Component {
	
	render() {
		return (
			<div className="no-file">
				<Archive color={white}/>
				<p>选择一个存在的资源文件或新建来继续</p>
			</div>
		);
	}
}