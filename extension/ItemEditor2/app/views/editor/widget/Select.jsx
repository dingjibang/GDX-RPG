import React from 'react';
import BaseWidget from "./BaseWidget";
import {TextField} from "material-ui";

import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';

export default class Select extends BaseWidget{

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

		const list = this.props.values.map((v, i) => <MenuItem primaryText={v.label} value={v.value} key={i}/>);

		return (
			<SelectField
				floatingLabelText={this.props.desc}
				value={this.state.obj}
				onChange={(e, i, v) => this.change(v)}
				errorText={this.state.errorText}
            >
				{list}
			</SelectField>
		)
	}
}