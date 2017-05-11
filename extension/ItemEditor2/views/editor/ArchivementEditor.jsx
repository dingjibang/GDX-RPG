import React from 'react';
import Editor from "./Editor";
import {TextField} from "material-ui";
import Bind from "./widget/Bind";

export default class ArchivementEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind from={this.props.file.object() || {}} />
		)
	}
}