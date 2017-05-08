import React from 'react';
import Editor from "./Editor";
import {TextField} from "material-ui";

export default class ArchivementEditor extends Editor {
	constructor(props){
		super(props)

		this.state.object = {
			name: "",
		};
	}

	render(){
		return super.renderContainer(
			<div>
				<TextField floatingLabelText="成就名称"/>
			</div>
		)
	}
}