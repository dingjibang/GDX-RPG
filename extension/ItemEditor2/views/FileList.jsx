import React from 'react';
import {List, ListItem} from 'material-ui/List';
import AddIcon from 'material-ui/svg-icons/content/add';
import SearchIcon from 'material-ui/svg-icons/action/subject';
import IconButton from 'material-ui/IconButton';
import FileListType from "./FileListType";


export default class FileList extends React.Component {

	state = {
		open: true
	};

	constructor(props){
		super(props);

		this.type = ["ItemFile"];
	}

	render() {
		const items = [];

		for(var i = 0; i < this.type.length; i ++)
			items.push(<FileListType key={i} type={this.type[i]}/>);

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