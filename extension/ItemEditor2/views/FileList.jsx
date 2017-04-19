import React from 'react';
import {List, ListItem} from 'material-ui/List';
import AddIcon from 'material-ui/svg-icons/content/add';
import SearchIcon from 'material-ui/svg-icons/action/subject';
import IconButton from 'material-ui/IconButton';
import FileListItem from "./FileListItem";


export default class FileList extends React.Component {

	state = {
		open: true
	};

	constructor(props){
		super(props);

		this.type = ["ItemFile"]
			.map(ele => require("../scripts/FileType/" + ele).default).map(ele => new ele);
	}

	render() {
		const items = [];

		for(var i = 0; i < this.type.length; i ++)
			items.push(<FileListItem key={i} item={this.type[i]}/>);

		return (
			<div>
				<List>
					{items}
				</List>
			</div>
		);
	}

	getByName(name){
		return this.type.filter(t => t.name == name)[0];
	}
}