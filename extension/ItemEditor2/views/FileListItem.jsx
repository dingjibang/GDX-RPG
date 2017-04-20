import React from 'react';
import {List, ListItem} from 'material-ui/List';

export default class FileListItem extends React.Component {

	/**
	 * props = {fileName: file name, key: dom index}
	 */

	state = {fileName: "", errorFormat: false, key: 0, fileText: "", label: "", prefix: null}


	componentWillMount() {
		this.setState({fileName: this.props.fileName, key: this.props.key})
		this.reader = require("../scripts/FileType/" + this.props.type).default;

		this.load(this.props.fileName);
	}

	load(fileName) {
		fileName = fileName || this.state.fileName;

		this.reader.read(fileName, file => {
			this.setState({errorFormat: file.errorFormat, fileText: file.fileText, label: file.label, prefix: file.prefix});
		});
	}

	render() {
		return (
			<ListItem
				key={this.state.key}
				primaryText={
					<div style={this.state.errorFormat ? {color: "red"} : {}}>
						{this.state.prefix ? <span className={"list-item-prefix"} style={{color: this.state.prefix.color}}>[{this.state.prefix.text}]</span> : ""}
						{this.state.label}
						&nbsp;&nbsp;
						<span style={{color: "lightgray"}}>{this.state.fileName}</span>
					</div>
				}
			/>);
	}
}