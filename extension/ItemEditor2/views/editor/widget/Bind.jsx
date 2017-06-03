import React from 'react';
import BaseWidget from "./BaseWidget";

export default class Bind extends BaseWidget{

	defaultValue(){
		return {};
	}

	error(flag){}
	check(){return true}

	render(){
		let child = this.props.child.map((e, i) => React.cloneElement(
			e,
			{...e.props, parent: this.state.obj, requireGet: () => this.requireGet(), ref: "ref" + i}
		));

		return (
			<div>
				{child.map((ele, idx) => <div key={idx}>{ele}</div>)}
			</div>
		)
	}
}