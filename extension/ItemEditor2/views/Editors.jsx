import React from 'react';
import NoFile from "./NoFile";
import SuperFile from "../scripts/FileType/SuperFile";

import {Tabs, Tab} from "./overwrite-material-ui/tabs";

export default class Editors extends React.Component {
	
	state = {files: [], current: 0}

	constructor(props){
		super(props);

		const editors = [];
		E.editors = {
			list: () => this.state.files.map((_, index) => this.refs["editor" + index]),
			add: file => {
				if(!E.editors.get(file))
					this.setState({files: [...this.state.files, file]})

				E.editors.pitch(file);
			},
			pitch: file => this.state.files.forEach((file2, index) => {
				if(SuperFile.equals(file, file2)){
					this.setState({current: index});
					this.refs.tabs.pitch(index);
				}
			}),
			get: file => E.editors.list().filter(e => SuperFile.equals(e.props.file, file))[0],
			close: file => {
				let obj = E.editors.get(file);
				if(obj.modified()){
					//confirm
				}else{
					//close
				}
			}
		};
	}
	
	render() {
		if(this.state.files.length == 0)
			return <NoFile/>

		let tabs = [];
		for(let i = 0; i < this.state.files.length; i++){
			let file = this.state.files[i];

			let dom = file.editor("editor" + i);

			tabs.push(
				<Tab label={file.label} key={i} value={i} onActive={() => this.setState({current: i})}>
					<div>
						{dom}
					</div>
				</Tab>
			);
		}

		return (
			<Tabs value={this.state.current} ref="tabs">
				{tabs}
			</Tabs>
		)
	}
}