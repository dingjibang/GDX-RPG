import React from 'react';
import {ListItem} from 'material-ui/List';
import Popover from 'material-ui/Popover';



export default class FileListItems extends React.Component {

	state = {open: false, anchorEl: null}

	postItem(dom, file) {
		if(!dom)
			return;

		dom.ondblclick = () => {
			console.log("open")
		};

		dom.oncontextmenu = () => {
			this.setState({open: true, anchorEl: dom})
		};
	}

	render() {
		let file = this.props.file;
		let index = this.props.index;

		return (
			<div ref={(ref) => this.postItem(ref, file)}>
				<ListItem
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
					useLayerForClickAway={false}
					open={this.state.open}
					anchorEl={this.state.anchorEl}
					anchorOrigin={{horizontal: 'left', vertical: 'bottom'}}
					targetOrigin={{horizontal: 'left', vertical: 'top'}}
					onRequestClose={() => this.setState({open: false})}
				>
					{"asdasd"}
				</Popover>
			</div>
		)
	}

}