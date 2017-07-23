import React from 'react';
import BaseWidget from "./BaseWidget";
import {TextField} from "material-ui";

export default class Input extends BaseWidget{

	defaultValue(){
		return "";
	}

	check(){
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	error(flag){
		this.setState({errorText: flag ? "该值不能为空" : null});
	}

	render(){
		return (
			<TextField
				floatingLabelText={this.props.desc}
				value={this.state.obj}
				onChange={(e, v) => this.change(v)}
				errorText={this.state.errorText}
			/>
		)
	}
}