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

	render(){
		return <Toggle label={this.props.desc} onToggle={(e, v) => this.change(v)} toggled={this.state.obj}/>
	}
}