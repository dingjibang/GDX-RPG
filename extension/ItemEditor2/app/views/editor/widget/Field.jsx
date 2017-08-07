import React from 'react';
import BaseWidget from "./BaseWidget";

export default class Field extends BaseWidget{

	componentWillMount(){
		this.state.obj = this.defaultValue();
	}

	defaultValue(){
		return this.props.value;
	}

	check(){
		return true;
	}

	error(flag){
	}


	draw(){
		return null;
	}
}