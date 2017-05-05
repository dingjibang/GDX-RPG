import React from 'react';
import Editor from "./Editor";

export default class ArchivementEditor extends Editor {
	constructor(props){
		super(props)
	}

	render(){
		return super.renderContainer(
			<div>
				<pre>
					{this.props.file.fileText}
				</pre>
			</div>
		)
	}
}