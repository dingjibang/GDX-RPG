import React from 'react';
import BaseWidget from "./BaseWidget";
import {TextField} from "material-ui";

export default class Input extends BaseWidget{
	defaultValue(){
		return "";
	}

	change(value){
		this.state.obj = value;
		this.setState(this.state);

		this.props.change();
	}

	check(){
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	render(){
		return <TextField floatingLabelText={this.props.desc} value={this.state.obj} onChange={(e, v) => this.change(v)}/>;
	}
}