import React from 'react';
import NoFile from "./NoFile";

export default class Editors extends React.Component {
	
	state = {editors: []}

	constructor(props){
		super(props);
	}
	
	render() {
		if(this.state.editors.length == 0)
			return <NoFile/>

		return (
			<div>assd</div>
		)
	}
}