import React from 'react';

export default class Editor extends React.Component {
	state = {
		modified: false
	}

	constructor(props){
		super(props)
		props.file.fileText += Math.random()
	}

	modified() {
		return this.state.modified;
	}

}