import React from 'react';
import Editor from "./Editor";

export default class TaskEditor extends Editor {
	constructor(props){
		super(props)
	}

	render(){
		return (<div>{this.props.file.fileText}</div>)
	}
}