import React from 'react';
import BaseWidget from "./BaseWidget";
import {TextField} from "material-ui";
import PickerDialog from "../../overwrite-material-ui-color-picker/PickerDialog";


export default class Color extends BaseWidget{

	constructor(props) {
		super(props);

		this.state.show = false;
	}

	defaultValue(){
		return "00000000";
	}

	check(){
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	error(flag){
		this.setState({errorText: flag ? "该值不能为空" : null});
	}

	//rrggbbaa hex to rgba()
	preValue(value){
		if(value && value.length === 8){
			const r = value.substr(0, 2), g = value.substr(2, 2), b = value.substr(4, 2), a = value.substr(6, 2)
			value = `rgba(${parseInt(r, 16)}, ${parseInt(g, 16)}, ${parseInt(b, 16)}, ${(parseInt(a, 16) / 255).toFixed(2)})`
		}
		return value;
	}

	//rgba() to rrggbbaa hex
	postValue(value){
		if(value && value.toString().substr(0, 5) === "rgba("){
			let rgb = value.match(/^rgba?[\s+]?\([\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\d+)[\s+]?,[\s+]?(\S+?)[\)]/i);
			value = (rgb && rgb.length === 5) ? "" +
				("0" + parseInt(rgb[1],10).toString(16)).slice(-2) +
				("0" + parseInt(rgb[2],10).toString(16)).slice(-2) +
				("0" + parseInt(rgb[3],10).toString(16)).slice(-2) +
				("0" + parseInt(rgb[4] * 255 + "",10).toString(16)).slice(-2) : ""
			return value;
		}else{
			return "";
		}

	}

	show(flag){
		this.setState({show: flag});
	}


	draw(){
		return (
			<div>
				<TextField
					floatingLabelText={<span>{this.props.desc}{this.props.required ? <span className="required">*</span> : null}</span>}
					value={this.state.obj}
					ref="picker"
					onChange={(_, v) => !this.props.required && this.change(v)}
					inputStyle={{ color: this.state.obj }}
					onTouchTap={() => this.show(true)}
					errorText={this.state.errorText}
				/>
				{this.state.show && (
					<PickerDialog
						value={this.state.obj}
						onClick={() => this.show(false)}
						onChange={c => {
							console.log(c)
							if(c.rgb)
								return this.change(`rgba(${c.rgb.r}, ${c.rgb.g}, ${c.rgb.b}, ${c.rgb.a})`);
							this.change(null);
						}}
					/>
				)}
			</div>
		)
	}
}