import React from 'react';
import {IconButton, RaisedButton, Toolbar, ToolbarGroup, ToolbarSeparator, ToolbarTitle} from "material-ui";
import Code from "material-ui/svg-icons/action/code"
import Save from "material-ui/svg-icons/content/save"
import Create from "material-ui/svg-icons/content/create"
import CodeMirror from "../overwrite-codemirror/CodeMirror"

export default class Editor extends React.Component {
	state = {
		modified: false,
		coding: false,
		text: null
	}

	constructor(props){
		super(props)
		this.state.text = props.file.fileText
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

	modified() {
		return this.state.modified;
	}

	toggleCoding() {
		this.setState({coding: !this.state.coding})
	}

	renderContainer(dom) {
		if(this.editor)
			this.editor.refresh();

		const file = this.props.file;
		return (
			<div>
				<Toolbar className="toolbar">
					<ToolbarGroup>
						<ToolbarTitle text={file.$static.typeName + " > " + file.label} />
						<span className="gray">{file.fileName}</span>
					</ToolbarGroup>

					<ToolbarGroup>
						<RaisedButton backgroundColor={"#0799e2"} style={{height: 28}} label="保存" icon={<Save color="white"/>} disabled={!this.state.modified}/>
						{
							this.state.coding ?
								<RaisedButton backgroundColor={"#e29161"} style={{height: 28}} label="切换为编辑视图" icon={<Create color="white"/>} onClick={() => this.toggleCoding()}/>
							:
								<RaisedButton backgroundColor={"#71d28a"} style={{height: 28}} label="切换为代码视图" icon={<Code color="white"/>} onClick={() => this.toggleCoding()}/>
						}
					</ToolbarGroup>
				</Toolbar>

				<div className="editor-inner">
					{
						!this.state.coding ? dom :
						<CodeMirror value={this.state.text} onChange={() => console.log("?")} options={{
							mode: "javascript",
							lineNumbers: true,
							gutters: ["CodeMirror-lint-markers"],
							lint: true
						}}/>
					}
				</div>
			</div>
		)
	}

}