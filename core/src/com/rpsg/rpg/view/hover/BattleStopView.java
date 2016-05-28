package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;

public class BattleStopView extends HoverView{
	
	Table outer,data;
	Runnable callback;
	
	@Override
	public void init() {
//		BattleView bv = (BattleView)param.get("view");
		callback = (Runnable)param.get("callback");
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(GameUtil.stage_width, GameUtil.stage_height).appendTo(stage).setColor(0,0,0,0).addAction(Actions.alpha(.9f,1f,Interpolation.pow4Out));
		
//		stage.setDebugAll(true);
		$.add(outer = new Table().bottom().left()).appendTo(stage).setPosition(0, 0);
		
		for(final Hero hero : RPG.ctrl.hero.currentHeros()){
			outer.add(new AnimateGroup(){
				
				public void create() {
					$.add(Res.get(hero.name,55).right()).setWidth(1000).setAlpha(0).setPosition(205, 375).addAction(Actions.parallel(Actions.fadeIn(.5f),Actions.moveBy(-300, 0, .5f,Interpolation.pow4Out))).appendTo(this);
					$.add(Res.get(hero.jname,40).right()).setWidth(1000).setAlpha(0).setPosition(235, 355).addAction(Actions.parallel(Actions.alpha(.2f,.5f),Actions.moveBy(-300, 0, .5f,Interpolation.pow4Out))).appendTo(this);
					$.add(hero.defaultFG()).appendTo(this).setScale(.3f).setOrigin(Align.bottomLeft).setPosition(-300,-50).setAlpha(0).addAction(Actions.fadeIn(.5f)).addAction(Actions.moveBy(300, 0,.5f,Interpolation.pow4Out));
					$.add(Res.get(Setting.IMAGE_BATTLE + "battle_result_table.png")).appendTo(this).setAlpha(0).addAction(Actions.fadeIn(.5f)).setPosition(368, 30);
					
					$.add(data = new Table()).appendTo(this)
						.append(Res.get(hero.target.getProp("maxhp"),22),Res.get(hero.target.getProp("maxmp"),22)).row()
						.append(Res.get(hero.target.getProp("attack"),22),Res.get(hero.target.getProp("magicAttack"),22)).row()
						.append(Res.get(hero.target.getProp("defense"),22),Res.get(hero.target.getProp("magicDefense"),22)).row()
						.append(Res.get(hero.target.getProp("speed"),22),Res.get(hero.target.getProp("hit"),22)).row()
					.eachCells(c-> ((Label)c.padLeft(125).size(154,49).center().getActor()).center().width(154)).setPosition(648, 127);
					
					Label level,exp;
					$.add(level = Res.get(hero.target.getProp("level"), 75)).appendTo(this).setPosition(375, 300).setWidth(100).setAlign(Align.center);
					$.add(exp = Res.get("下一级所需经验 : " + hero.target.getProp("maxexp"), 22)).appendTo(this).setPosition(825, 250).setWidth(100).setAlign(Align.right);
					
				}
				
				public void act(float delta) {
					super.act(delta);
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
		
		public void create(){};
		
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
