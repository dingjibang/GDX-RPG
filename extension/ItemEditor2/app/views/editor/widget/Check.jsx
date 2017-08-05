import React from 'react';
import BaseWidget from "./BaseWidget";
import {Toggle} from 'material-ui';

export default class Check extends BaseWidget{
	defaultValue(){
		return false;
	}

	check(){
		return true;
	}

	error(flag){
	}

	draw(){
		return <Toggle label={<span>{this.props.desc}{this.props.required ? <span className="required">*</span> : null}</span>} onToggle={(e, v) => this.change(v)} toggled={this.state.obj}/>
	}
}