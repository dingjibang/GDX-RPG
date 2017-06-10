import React from 'react';
import Editor from "./Editor";
import {TextField} from "material-ui";
import Bind from "./widget/Bind";
import Input from "./widget/Input";
import Check from "./widget/Check";
import Select from "./widget/Select";

export default class ArchivementEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind ref="root" change={obj => this.change(obj)} from={this.props.file.object() || {}} child={[
				<Input from="name" desc="任务名称" required={true}/>,
				<Input from="description" desc="任务描述" required={true}/>,
				<Input from="description2" desc="任务奖励描述" required={true}/>,
				<Check from="giveup" desc="任务是否可以放弃"/>,
				//<Select from="type" desc="任务类型" child={[
				//	{"text": "主线任务", value: "main"},
				//	{"text": "支线任务", value: "secondly"}
				//]}/>,
				<Input from="by" desc="任务委托人" required={true}/>
			]}/>
		)
	}
}