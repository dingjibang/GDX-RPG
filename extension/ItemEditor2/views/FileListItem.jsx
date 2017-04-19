import React from 'react';
import {List, ListItem} from 'material-ui/List';
import AddIcon from 'material-ui/svg-icons/content/add';
import SearchIcon from 'material-ui/svg-icons/action/subject';
import IconButton from 'material-ui/IconButton';


export default class FileListItem extends React.Component {

	state = {
		open: true, files: []
	};

	componentWillMount() {
		this.props.item.fileChange = () => {
			this.setState({files: this.props.item.files});
		};

		setTimeout(() => this.props.item.init(), ~~(Math.random() * 300));
	}

	render() {
		var item = this.props.item, files = this.state.files;

		const subItem = [<ListItem key={-1} primaryText="过滤条件..." leftIcon={<SearchIcon/>} onClick={item.toggleSearch}/>];

		for(var j = 0; j < files.length; j++)
			subItem.push(<ListItem key={j} primaryText={<div style={files[j].errorFormat ? {color: "red"} : {}}>{files[j].name}</div>}/>);

		return (
			<ListItem
				primaryText={item.type}
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