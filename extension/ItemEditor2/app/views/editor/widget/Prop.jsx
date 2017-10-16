import React from 'react';
import BaseWidget from "./BaseWidget";
import {MenuItem, SelectField, TextField} from "material-ui";

export default class Prop extends BaseWidget{

	preset = [
		{name: "hp", desc: "血量", group: "基础属性"},
		{name: "maxhp", desc: "血量上限", group: "基础属性"},
		{name: "mp", desc: "蓝量", group: "基础属性"},
		{name: "maxmp", desc: "蓝量上限", group: "基础属性"},
		{name: "patk", desc: "物理攻击", group: "基础属性"},
		{name: "matk", desc: "魔法攻击", group: "基础属性"},
		{name: "pdef", desc: "物理防御", group: "基础属性"},
		{name: "mdef", desc: "魔法防御", group: "基础属性"},

		{name: "exp", desc: "经验", group: "玩家属性"},

		{name: "criticalRate", desc: "暴击几率", group: "战斗属性"},
		{name: "critical", desc: "暴击伤害(%)", group: "战斗属性"},
		{name: "rank", desc: "被敌人攻击的额外评分", group: "战斗属性"},
		{name: "rankRate", desc: "被敌人攻击的额外几率(%)", group: "战斗属性"},
		{name: "speed", desc: "速度", group: "战斗属性"},
		{name: "hit", desc: "命中率", group: "战斗属性"},
		{name: "evasion", desc: "闪避", group: "战斗属性"},
		{name: "attackRate", desc: "技能额外倍率(%)", group: "战斗属性"},

		{name: "sun", desc: "日属性有效率", group: "有效率系列"},
		{name: "moon", desc: "月属性有效率", group: "有效率系列"},
		{name: "metal", desc: "金属性有效率", group: "有效率系列"},
		{name: "water", desc: "水属性有效率", group: "有效率系列"},
		{name: "earth", desc: "土属性有效率", group: "有效率系列"},
		{name: "fire", desc: "火属性有效率", group: "有效率系列"},
		{name: "wood", desc: "木属性有效率", group: "有效率系列"},
		{name: "none", desc: "无属性有效率", group: "有效率系列"},

	]

	defaultValue(){
		return null;
	}

	check(){//TODO
		return !this.props.required || (this.state.obj && this.state.obj.length !== 0);
	}

	error(flag){//TODO
		this.setState({errorText: flag ? "该值不能为空" : null});
	}

	nameOf(name){
		return this.preset.filter(p => p.name === name)[0];
	}

	preChange(){

	}

	createPropDom(obj){
		
	}

	draw(){
		let dom = [];

		let selectList = this.preset.map((v, i) => <MenuItem primaryText={v.desc} value={v.name} key={i}/>)

		let key = 0;
		for(let obj of this.state.obj){

			obj.style = obj.formulaType === "negative" ? (obj.style || "physic") : undefined;
			obj.type = obj.formulaType === "negative" ? (obj.type || "none") : undefined;

			dom.push(

			);
		};

		return (
			<div>
				{dom}
			</div>
		)
	}
}