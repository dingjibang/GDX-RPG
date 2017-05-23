import React from 'react';
import BaseWidget from "./BaseWidget";

export default class Bind extends BaseWidget{

	defaultValue = {};

	/**从子对象向上冒泡获取结果*/
	update() {
		let parent = {};

		let i = 0;
		while(true){
			let ref = this.refs["ref" + i++];
			if(!ref)
				break;

			parent = ref.update(parent);
			if(parent === false)
				return;
		}

		if(!this.props.parent)
			return this.props.change(parent);

		return parent;
	}

	render(){
		let child = this.props.child.map((e, i) => React.cloneElement(
			e,
			{...e.props, parent: this.state.obj, change: () => this.update(), ref: "ref" + i}
		));

		return (
			<div>
				{child.map((ele, idx) => <div key={idx}>{ele}</div>)}
			</div>
		)
	}
}