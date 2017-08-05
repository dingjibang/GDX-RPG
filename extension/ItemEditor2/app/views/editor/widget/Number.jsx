import React from 'react';
import BaseWidget from "./BaseWidget";
import {TextField} from "material-ui";

export default class Number extends BaseWidget{

	defaultValue(){
		return "";
	}

	check(){
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	error(flag){
		this.setState({errorText: flag ? "该值不能为空" : null});
	}

	draw(){
		return (
			<TextField
				type="number"
				floatingLabelText={<span>{this.props.desc}{this.props.required ? <span className="required">*</span> : null}</span>}
				value={this.state.obj}
				onChange={(e, v) => this.change(parseInt(v))}
				errorText={this.state.errorText}
			/>
		)
	}
}