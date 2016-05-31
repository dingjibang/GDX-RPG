package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.NumberLabel;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.BattleView;

public class BattleStopView extends HoverView{
	
	Table outer,data;
	Runnable callback;
	
	@Override
	public void init() {
		BattleView bv = (BattleView)param.get("view");
		callback = (Runnable)param.get("callback");
		
		int exp = Enemy.getExp(Enemy.get(bv.param.enemy));
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.stage_width, GameUtil.stage_height).appendTo(stage).setColor(0,0,0,0).addAction(Actions.alpha(.9f,1f,Interpolation.pow4Out));
		
//		stage.setDebugAll(true);
		
		$.add(outer = new Table().bottom().left()).appendTo(stage).setPosition(0, 0);
		
		for(final Hero hero : RPG.ctrl.hero.currentHeros()){
			outer.add(new AnimateGroup(){
				
				float cexp = hero.target.getProp("exp"), maxexp = hero.target.getProp("maxexp");
				int expcpy = exp;
				Label llevel;
				NumberLabel lexp;
				Image lvbar; 
				boolean acting = false,finalacting = false;
				boolean created = false;
				
				public AnimateGroup create() {
					$.add(Res.get(hero.name,55).right()).setWidth(1000).setAlpha(0).setPosition(205, 375).addAction(Actions.parallel(Actions.fadeIn(.5f),Actions.moveBy(-300, 0, .5f,Interpolation.pow4Out))).appendTo(this);
					$.add(Res.get(hero.jname,40).right()).setWidth(1000).setAlpha(0).setPosition(235, 355).addAction(Actions.parallel(Actions.alpha(.2f,.5f),Actions.moveBy(-300, 0, .5f,Interpolation.pow4Out))).appendTo(this);
					$.add(hero.defaultFG()).appendTo(this).setScale(.3f).setOrigin(Align.bottomLeft).setPosition(-300,-50).setAlpha(0).addAction(Actions.fadeIn(.5f)).addAction(Actions.moveBy(300, 0,.5f,Interpolation.pow4Out));
					$.add(Res.get(Setting.IMAGE_BATTLE + "battle_result_table.png")).appendTo(this).setAlpha(0).addAction(Actions.fadeIn(.5f)).setPosition(368, 30);
					
					$.add(data = new Table()).appendTo(this)
						.append(new NumberLabel(hero.target.getProp("maxhp"),22,"maxhp"),new NumberLabel(hero.target.getProp("maxmp"),22,"maxmp")).row()
						.append(new NumberLabel(hero.target.getProp("attack"),22,"attack"),new NumberLabel(hero.target.getProp("magicAttack"),22,"magicAttack")).row()
						.append(new NumberLabel(hero.target.getProp("defense"),22,"defense"),new NumberLabel(hero.target.getProp("magicDefense"),22,"magicDefense")).row()
						.append(new NumberLabel(hero.target.getProp("speed"),22,"speed"),new NumberLabel(hero.target.getProp("hit"),22,"hit")).row()
					.eachCells(c-> ((Label)c.padLeft(125).size(154,49).center().getActor()).center().width(154)).setPosition(648, 127);
					
					$.add(llevel = Res.get(hero.target.getProp("level"), 75)).appendTo(this).setPosition(375, 300).setWidth(100).setAlign(Align.center);
					$.add(lexp = new NumberLabel((int)(maxexp - cexp), 22,"maxexp").before("下一级所需经验 : ")).appendTo(this).setPosition(825, 250).setWidth(100).setAlign(Align.right);
					
					//max size 402px
					$.add(lvbar = Res.get(Setting.UI_BASE_IMG)).setSize((cexp / maxexp) * 402, 35).setPosition(518, 290).setColor(189f/255f,75f/255f,79f/255f).appendTo(this);
					
					created = true;
					
					return this;
				}
				
				public void act(float delta) {
					if(!created) return;
					super.act(delta);
					
					if(!acting && !finalacting){
						acting = true;
						if(finalacting = cexp + expcpy < maxexp){
							lexp.toNumber(maxexp - (cexp + expcpy));
							lvbar.addAction(Actions.sequence(Actions.sizeTo(((cexp + expcpy) / maxexp) * 402, 35, 1.5f,Interpolation.pow3),Actions.run(()->{
								hero.target.setProp("exp", (int)cexp + expcpy);
							})));
						}else{
							int aon = (int)(maxexp - cexp);
							lexp.toNumber(0);
							lvbar.addAction(Actions.sequence(Actions.sizeTo(402, 35, 1.5f, Interpolation.pow3),Actions.run(()->{
								lvbar.setWidth(0);
								int oldLevel = hero.target.getProp("level");
								
								hero.grow.levelUp();
								acting = false;
								expcpy -= aon;
								cexp = 0;
								maxexp = hero.target.getProp("maxexp");
								lexp.setNumber(maxexp);
								
								int newLevel = hero.target.getProp("level");
								
								for(Cell<?> cell : data.getCells()){
									NumberLabel label = (NumberLabel) cell.getActor();
									for(String key : hero.target.keySet()){
										if(key.equals(label.name)){
											boolean hasChanged = hero.target.getProp(key) != label.num();
											label.toNumber(hero.target.getProp(key));
											if(hasChanged){
												label.setColor(1,0,0,1);
												label.addAction(Actions.color(new Color(1,1,1,1),.5f));
											}
										}
									}
								}
								
								RPG.toast.add(hero.name + "从 等级" + oldLevel + " 升级至 等级" + newLevel, Color.SKY, 22, true, null);
								
								llevel.setText(hero.target.getProp("level") + "");
								llevel.setColor(1,0,0,1);
								llevel.setScale(1.2f);
								llevel.addAction(Actions.color(new Color(1,1,1,1),.5f));
								llevel.addAction(Actions.scaleTo(1, 1, .5f));
							})));
						}
					}
				}
				
			});
		}
		
		$.each(outer.getCells(), c -> c.size(GameUtil.stage_width, 450));
		
		TimeUtil.add(((AnimateGroup) outer.getCells().get(0).getActor()).current()::create, 800);
		
		$.add(Res.get("战斗结束",55)).appendTo(stage).setAlpha(0).setPosition(100, 535).addAction(Actions.parallel(Actions.fadeIn(.5f),Actions.moveBy(0, -50,.5f,Interpolation.pow4Out)));
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(825, 3).setPosition(100, 440).appendTo(stage).setAlpha(0).addAction(Actions.delay(.2f,Actions.parallel(Actions.fadeIn(.8f),Actions.moveBy(0, 20,.5f,Interpolation.pow4Out))));
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.ENTER || keycode == Keys.Z)
			if(current() != null && current().stop){
				if(!nextGroup()){
					callback.run();
					stage.addAction(Actions.sequence(Actions.fadeOut(.5f),Actions.run(() -> disposed = true)));
				}
			}else if(current() != null){
				current().stop();
			}
		return super.keyDown(keycode);
	}
	
	private AnimateGroup current(){
		for(Cell<?> cell : outer.getCells()){
			AnimateGroup $group = (AnimateGroup)cell.getActor();
			if($group.current)
				return $group;
		}
		
		return null;
	}
	
	private boolean nextGroup(){
		
		boolean find = false;
		AnimateGroup group = null;
		
		for(Cell<?> cell : outer.getCells()){
			AnimateGroup $group = (AnimateGroup)cell.getActor();
			if(find){
				group = $group;
				break;
			}
			find = $group.current;
			$group.current = false;
		}
		
		if(group == null) return false;
		
		group.current = true;
		outer.addAction(Actions.sequence(Actions.moveBy(-GameUtil.stage_width, 0, .5f, Interpolation.pow4),Actions.run(group::create)));
		
		return true;
	}
	
	
	private static class AnimateGroup extends Group{
		boolean stop = false,current = false;
		
		public AnimateGroup current(){
			this.current = true;
			return this;
		}
		
		public AnimateGroup create(){return this;};
		
		public void stop(){
			while(!stop) act(Gdx.graphics.getDeltaTime());
		}
		
		@Override
		public void act(float delta) {
			stop = true;
			super.act(delta);
		}
	}
	
}
