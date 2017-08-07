import React from 'react';
import Editor from "./Editor";
import Select from "./widget/Select";
import Bind from "./widget/Bind";
import Input from "./widget/Input";
import Prop from "./widget/Prop";

export default class BuffEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind ref="root" editor={this} from={this.props.file.object() || {}} child={[
				<Input from="name" desc="Buff名称" required={true}/>,
				<Input from="description" desc="Buff描述"/>,
				<Select from="type" desc="Buff类型" values={[{value: "buff", label: "增益的(Buff)"}, {value: "debuff", label: "减益的(DeBuff)"}]} required={true}/>,
				<Prop from="prop" desc="Buff提供的属性"/>
			]}/>
		)
	}
}