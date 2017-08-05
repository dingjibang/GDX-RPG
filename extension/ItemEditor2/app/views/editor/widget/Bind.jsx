import React from 'react';
import BaseWidget from "./BaseWidget";

export default class Bind extends BaseWidget{

	defaultValue(){
		return {};
	}

	error(flag){}
	check(){return true}

	draw(){
		let child = this.props.child.map((e, i) => React.cloneElement(
			e,
			{...e.props, parent: this.state.obj, message: msg => this.message(msg), ref: "ref" + i, _className: e.type.name.toLowerCase(), editor: this.props.editor}
		));


		let desc = null;
		if(this.props.desc)
			desc = <div className="bind-desc">{this.props.desc}</div>

		return (
			<div className={"bind-object" + (desc ? " desc" : "")}>
				{desc}
				{child.map((ele, idx) => <div key={idx} className={ele.props._className + "-outer"}>{ele}</div>)}
			</div>
		)
	}
}