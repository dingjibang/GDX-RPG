import React from 'react';
import Editor from "./Editor";
import Bind from "./widget/Bind";
import Input from "./widget/Input";
import FileSelector from "./widget/FileSelector";
import Color from "./widget/Color";
import Field from "./widget/Field";
import Number from "./widget/Number";
import ItemSelector from "./widget/ItemSelector";

export default class HeroEditor extends Editor {

	render(){
		return super.renderContainer(
			<Bind ref="root" editor={this} from={this.props.file.object() || {}} child={[
				<Input from="name" desc="角色名称" required={true}/>,
				<Input from="jname" desc="角色罗马音" required={true}/>,
				<FileSelector from="fg" path="images/fg" type="dir" desc="角色立绘所在文件夹" required={true}/>,
				<FileSelector from="spriteImage" path="images/walk/heros" desc="角色行走图" required={true}/>,
				<Color from="color" required={true} desc="角色主题颜色（UI用）"/>,
				<Input from="tag" desc="角色标签" required={true}/>,

				<Bind from="prop" desc="角色初始属性" className="props" child={[
					<Number from="hp" to="hp maxhp" desc="血量" required={true}/>,
					<Number from="mp" to="mp maxmp" desc="蓝量" required={true}/>,
					<Number from="patk" desc="物理攻击力" required={true}/>,
					<Number from="pdef" desc="物理防御力" required={true}/>,
					<Number from="matk" desc="魔法攻击力" required={true}/>,
					<Number from="mdef" desc="魔法防御力" required={true}/>,
					<Number from="speed" desc="速度" required={true}/>,
					<Number from="hit" desc="命中率" required={true}/>,
					<Number from="evasion" desc="闪避率" required={true}/>,
					<Field from="level" value={1}/>,
					<Field from="exp" value={0}/>,
					<Number from="nextExp" desc="下一等级所需经验值" title="不推荐修改这个数值" defaultValue={1000} required={true}/>,
					<Number from="criticalRate" desc="暴击率(%)" required={true}/>,
					<Number from="critical" desc="暴击伤害(%)" required={true}/>,
					<Field from="dead" value={0}/>,
					<Number from="rank" desc="被敌人攻击的额外评分" title="评分越高，越容易被敌人攻击" required={true}/>,
					<Number from="rankRate" desc="被敌人攻击的额外几率(%)" title="几率越高，越容易被敌人攻击" required={true}/>,
					<Field from="attackRate" value={0}/>,
					<Number from="sun" desc="日属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="moon" desc="月属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="metal" desc="金属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="wood" desc="木属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="water" desc="水属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="fire" desc="火属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="earth" desc="土属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
					<Number from="none" desc="无属性有效率(%)" title="默认100%，有效率越高，受到相应属性攻击伤害则越高" defaultValue={100} required={true}/>,
				]}/>,

				<Bind from="grow" desc="角色每级成长属性" className="props" child={[
					<Number from="maxhp" desc="血量" defaultValue={0}/>,
					<Number from="maxmp" desc="蓝量" defaultValue={0}/>,
					<Number from="patk" desc="物理攻击力" defaultValue={0}/>,
					<Number from="pdef" desc="物理防御力" defaultValue={0}/>,
					<Number from="matk" desc="魔法攻击力" defaultValue={0}/>,
					<Number from="mdef" desc="魔法防御力" defaultValue={0}/>,
					<Number from="speed" desc="速度" defaultValue={0}/>,
					<Number from="hit" desc="命中率" defaultValue={0}/>,
					<Number from="evasion" desc="闪避率" defaultValue={0}/>,
				]}/>,

				<Bind from="equip" desc="初始装备" child={[
					<ItemSelector from="shoes" filter={f => f.type === "Equipment" && f.equipType === "shoes"} type="item" desc="鞋子" id={true}/>,
					<ItemSelector from="clothes" filter={f => f.type === "Equipment" && f.equipType === "clothes"} type="item" desc="衣服" id={true}/>,
					<ItemSelector from="weapon" filter={f => f.type === "Equipment" && f.equipType === "weapon"} type="item" desc="武器" id={true}/>,
					<ItemSelector from="ornament1" filter={f => f.type === "Equipment" && f.equipType === "ornament"} type="item" desc="饰品1" id={true}/>,
					<ItemSelector from="ornament2" filter={f => f.type === "Equipment" && f.equipType === "ornament"} type="item" desc="饰品2" id={true}/>,
				]}/>,

			]}/>
		)
	}
}