import React from 'react';
import {IconButton, RaisedButton, Toolbar, ToolbarGroup, ToolbarSeparator, ToolbarTitle} from "material-ui";
import Code from "material-ui/svg-icons/action/code"
import Save from "material-ui/svg-icons/content/save"
import Create from "material-ui/svg-icons/content/create"
import Pitch from "material-ui/svg-icons/maps/my-location"
import CodeMirror from "../overwrite-codemirror/CodeMirror"

export default class Editor extends React.Component {
	state = {
		modified: false,
		coding: false,
		text: null,
		object: {}
	}

	constructor(props){
		super(props)
		this.state.text = props.file.fileText
		this.state.object = this.props.file.object() || {};
	}

	domId = "codeMirror" + E.seq()
	editor = null

	componentDidMount() {
		const dom = document.getElementById(this.domId);
		if(this.editor || !dom)
			return;

		this.editor = CodeMirror(document.getElementById(this.domId), {
			value: this.state.text,
			mode: "javascript",
			autofocus: true,
			matchBrackets: true
		})

	}

	modified(flag) {
		if(flag == undefined)
			return this.state.modified;

		this.setState({modified: flag});
		this.props.file.modified = flag;
		E.editors.editors.refresh();

	}

	toggleCoding() {
		this.setState({coding: !this.state.coding})
	}

	setValue(txt) {
		this.state.text = txt;
		this.modified(true)
	}


	save(){
		if(!this.state.modified)
			return;

		const file = this.props.file;

		file.fileText = this.state.text;
		file.save(() => {
			file.parse();

			let outer = E.files.find(file.$static.type);

			if(file.post)
				file.post(outer.listFiles())

			outer.refresh()
			this.modified(false)
		});

	}

	getCodeMirror() {
		return this.refs.cm && this.refs.cm.getCodeMirror()
	}

	renderContainer(dom) {
		if(this.editor)
			this.editor.refresh();

		const file = this.props.file;
		return (
			<div>
				<Toolbar className="toolbar">
					<ToolbarGroup>
						<IconButton onClick={() => E.files.select(file.$static.type, file.fileName, true)} className="pitch" tooltip="定位文件位置" style={{width: 28, height: 28, padding: 0}}><Pitch color="white"/></IconButton>
						<ToolbarTitle text={file.$static.typeName + " > " + file.label} />
						<span className="gray">{file.fileName}</span>
					</ToolbarGroup>

					<ToolbarGroup>
						<RaisedButton backgroundColor={"#7fccba"} style={{height: 28}} label="保存" icon={<Save color="white"/>} disabled={!this.state.modified} onClick={() => this.save()}/>
						{
							this.state.coding ?
								<RaisedButton backgroundColor={"#e29161"} style={{height: 28}} label="切换为编辑视图" icon={<Create color="white"/>} onClick={() => this.toggleCoding()}/>
							:
								<RaisedButton backgroundColor={"#71d28a"} style={{height: 28}} label="切换为代码视图" icon={<Code color="white"/>} onClick={() => this.toggleCoding()}/>
						}
					</ToolbarGroup>
				</Toolbar>

				<div className="editor-inner">
					<div style={{display: this.state.coding ? "none" : "block"}}>{dom}</div>
					<div style={{display: this.state.coding ? "block" : "none"}}>
						<CodeMirror ref="cm" value={this.state.text} onChange={txt => this.setValue(txt)} options={{
							mode: "javascript",
							lineNumbers: true,
							gutters: ["CodeMirror-lint-markers"],
							tabSize: 8
						}}/>
					</div>
				</div>
			</div>
		)
	}

}