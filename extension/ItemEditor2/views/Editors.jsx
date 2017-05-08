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
			editors: this,
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
			indexOf: file => this.state.files.indexOf(this.state.files.filter(file2 => SuperFile.equals(file, file2))[0]),
			get: file => E.editors.list().filter(e => SuperFile.equals(e.props.file, file))[0],
			close: file => {
				let obj = E.editors.get(file);
				const close = () => {
					let current = this.state.current;
					let index = E.editors.indexOf(file);
					if(index == current)
						current = current == 0 ? 0 : current - 1;
					else
						current = current < index ? current : current - 1;

					this.setState({files: this.state.files.filter(file2 => !SuperFile.equals(file, file2)), current: current})
				}

				if(obj.modified()){
					E.confirm("文件尚未保存，确定要关闭么？", flag => {
						if(flag)
							close()
					});
				}else{
					close();
				}

			},
			current: () => E.editors.list()[this.state.current],
			codeMirror: () => E.editors.current() && E.editors.current().getCodeMirror(),
			save: () => {
				let current = E.editors.current()
				if(current)
					current.save()
			}
		};
	}

	refresh() {
		this.setState(this.state);
	}
	
	render() {
		if(this.state.files.length == 0)
			return <NoFile/>

		let tabs = [];
		for(let i = 0; i < this.state.files.length; i++){
			let file = this.state.files[i];

			let dom = file.editor("editor" + i);

			tabs.push(
				<Tab label={file.label + (file.modified ? "*" : "")} key={i} value={i} onActive={() => this.setState({current: i})} onClose={() => E.editors.close(file)}>
					<div>
						{dom}
					</div>
				</Tab>
			);
		}

		return (
			<Tabs value={this.state.current} ref="tabs" contentContainerClassName="editor-tabs-outer">
				{tabs}
			</Tabs>
		)
	}
}