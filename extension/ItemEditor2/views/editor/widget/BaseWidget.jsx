import React from 'react';

export default class BaseWidget extends React.Component{

	state = {obj: null}

	constructor(props){
		super(props);

		this.set(undefined, props);
	}

	/**
	 * 当内容变更时，尝试向上调用#requireGet()
	 */
	change(value){
		this.state.obj = value;
		this.setState(this.state);

		if(!this.check())
			return this.error(true);

		this.error(false);
		this.requireGet();
	}

	/**
	 * 获取当前组件的值，可能是Object(bind)或者基本类型
	 */
	get(parent){
		if(this.props.child){
			this.child().forEach(c => parent[c.props.from] = c.get(parent));
			return parent;
		}else{
			if(this.check())
				return this.state.obj;

			return this.defaultValue();
		}
	}

	set(value, props = this.props){
		if(props.parent){
			let parent = props.parent;
			if(value === undefined)
				this.state.obj = parent[props.from] === undefined ? this.defaultValue() : parent[props.from];
			else
				this.state.obj = value;
		}else{
			this.state.obj = value || props.from;
		}

		if(props.child && value){
			this.child().forEach(c => c.set(value[c.props.from] || c.defaultValue()));
		}
	}

	child(){
		let child = [];
		let i = 0;
		while(true){
			let ref = this.refs["ref" + i++];
			if(!ref)
				break;

			child.push(ref);
		}
		return child;
	}

	/**
	 * 向上查找root，找到后获取obj传给editor
	 */
	requireGet(){
		if(!this.props.parent){
			let obj = this.get({});
			this.props.change(obj);
		}else{
			this.props.requireGet();
		}
	}
}