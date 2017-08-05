import React from 'react';
import Editor from "./Editor";
import Bind from "./widget/Bind";
import Input from "./widget/Input";
import ItemSelector from "./widget/ItemSelector";
import Number from "./widget/Number";
import FileSelector from "./widget/FileSelector";
import Check from "./widget/Check";
import Select from "./widget/Select";

export default class TaskEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind ref="root" editor={this} from={this.props.file.object() || {}} child={[
				<Input from="name" desc="任务名称" required={true}/>,
				<Input from="description" desc="任务描述" required={true}/>,
				<Input from="description2" desc="任务奖励描述" required={true}/>,
				<Select from="type" desc="任务类型" values={[{value: "main", label: "主线任务"}, {value: "secondly", label: "支线任务"}]} required={true}/>,
				<Input from="by" desc="任务发布人"/>,
				<Check from="giveup" desc="是否可以放弃这个任务"/>,
				<Bind from="gain" desc="任务完成时获得的奖励" child={[
					<ItemSelector from="item" type="item" multi={true} count={true} desc="奖励道具"/>,
					<Number from="gold" desc="奖励金额"/>,
					<FileSelector from="js" path="script/trigger" desc="完成后触发JS脚本" />
				]}/>
			]}/>
		)
	}
}