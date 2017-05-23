import React from 'react';

export default class BaseWidget extends React.Component{

	state = {obj: null}

	constructor(props){
		super(props);

		if(props.parent){
			let parent = props.parent;
			this.state.obj = parent[props.from] === undefined ? this.defaultValue() : parent[props.from];
		}else{
			this.state.obj = props.from;
		}

	}

	update(parent){
		if(this.check()){
			parent[this.props.from] = this.state.obj;
			return parent;
		}

		return false;
	}

	render(){
		return null;
	}
}