import React from 'react';
import {List, ListItem} from 'material-ui/List';
import AddIcon from 'material-ui/svg-icons/content/add';
import SearchIcon from 'material-ui/svg-icons/action/subject';
import IconButton from 'material-ui/IconButton';
import FileListItem from './FileListItem';
import TextField from 'material-ui/TextField';


export default class FileListType extends React.Component {

	state = {open: true, dom: [], searchValue: ""};

	componentWillMount() {
		this.reader = require("../scripts/FileType/" + this.props.type).default;
		this.load();
	}

	load() {
		this.reader.list(files => {
			const subItem = [<ListItem key={-1} primaryText={
				<TextField hintText="输入名称以过滤..." className={"filter-input"} onChange={this.toggleSearch} value={this.searchValue}/>
			} className={"filter"} leftIcon={<SearchIcon />} />];

			for(var j = 0; j < files.length; j++)
				subItem.push(<FileListItem key={j} fileName={files[j]} type={this.props.type}/>);

			this.setState({files: subItem});
		});
	}

	render() {
		var subItem = this.state.files;

		return (
			<ListItem
				className={"list-item-head"}
				primaryText={this.reader.type}
				initiallyOpen={true}
				primaryTogglesNestedList={true}
				leftIcon={
					<div className="file-list-toolbar">
						<IconButton onClick={e => e.stopPropagation()}><AddIcon/></IconButton>
					</div>
				}
				open={this.state.open}
				onClick={() => this.setState({open: !this.state.open})}
				key={1}
				nestedItems={subItem}
			/>
		);
	}
}