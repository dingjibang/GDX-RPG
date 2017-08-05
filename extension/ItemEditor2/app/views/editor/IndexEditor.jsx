import React from 'react';
import Editor from "./Editor";
import Select from "./widget/Select";
import Bind from "./widget/Bind";
import Input from "./widget/Input";
import FileSelector from "./widget/FileSelector";

export default class IndexEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind ref="root" editor={this} from={this.props.file.object() || {}} child={[
				<Input from="name" desc="图鉴名称" required={true}/>,
				<Input from="jname" desc="图鉴罗马音"/>,
				<Input from="description" desc="图鉴描述"/>,
				<Select from="type" desc="图鉴类型" values={[{value: "actor", label: "角色图鉴"}, {value: "enemy", label: "敌人图鉴"}]} required={true}/>,
				<FileSelector from="path" path="images/fg" type="dir" toggle="root.type" when="actor" desc="角色图鉴图片位置" required={true}/>,
				<FileSelector from="path" path="images/enemy" toggle="root.type" when="enemy" desc="敌人图鉴图片位置" required={true}/>,
			]}/>
		)
	}
}