import React from 'react';
import Editor from "./Editor";
import Bind from "./widget/Bind";
import Input from "./widget/Input";
import ItemSelector from "./widget/ItemSelector";
import Number from "./widget/Number";
import FileSelector from "./widget/FileSelector";
import Check from "./widget/Check";

export default class AchievementEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind ref="root" change={obj => this.change(obj)} from={this.props.file.object() || {}} child={[
				<Input from="name" desc="成就名称" required={true}/>,
				<Input from="description" desc="成就描述" required={true}/>,
				<Input from="description2" desc="成就奖励描述" required={true}/>,
				<Check from="hide" desc="隐藏成就，没解锁之前用问号显示标题"/>,
				<Bind from="gain" desc="成就完成时获得的奖励" child={[
					<ItemSelector from="item" type="item" multi={true} count={true} desc="奖励道具"/>,
					<Number from="gold" desc="奖励金额"/>,
					<FileSelector from="js" path="script/trigger" desc="完成后触发JS脚本" />
				]}/>
			]}/>
		)
	}
}