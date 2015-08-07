package com.rpsg.rpg.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.object.CGLOOP1.Instance;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.DefaultNPC;
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Move;

public class INNERAUTO extends DefaultNPC{
	private static final long serialVersionUID = -3859019464901632709L;
	public INNERAUTO(String path, Integer width, Integer height) {
		super(path, width, height);
	}

	@Override
	public void init() {
		scripts.put(CollideType.auto,Instance.class);
	}
	
	public static class Instance extends Script {
		Image black,tip1,tip2;
		public void init() {
			if(RPG.getFlag("1-1-inner")==null){
				RPG.setFlag("1-1-inner",true);
				$(new BaseScriptExecutor() {public void init() {
					RPG.ctrl.cg.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(Color.BLACK).getItem()).addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
						RPG.ctrl.cg.dispose(black);
					}})));
					PostUtil.showMenu=false;
				}});
				setKeyLocker(true);
				$(new BaseScriptExecutor() {public void init() {
					RPG.ctrl.hero.turn(RPGObject.FACE_U);
				}});
				wait(50);
				
				showMSG(莲子);
				say("这里就是我家啦，当然今天开始也是结成同学家","莲子");
				say("到晚上还有一点时间，结成同学可以随意转转\n二楼最里面是你的房间，已经给你准备了一些道具和食物，自己去拿就好了","莲子");
				say("准备好了就来找我出发吧，当然有什么问题也是可以问我的哦！","莲子");
				hideMSG();
				wait(20);
				$(new BaseScriptExecutor() {public void init() {
					RPG.ctrl.cg.push(black=(Image) $.add(Res.getNP(Setting.UI_BASE_IMG)).setSize(GameUtil.screen_width, GameUtil.screen_height).setColor(0,0,0,0).getItem()).addAction(Actions.color(new Color(0,0,0,0.5f),0.3f));
				}});
				$(new BaseScriptExecutor() {public void init() {
					tip1=Res.getNP(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc1.png":"tip_mobile1.png"));
					tip2=Res.getNP(Setting.IMAGE_GLOBAL+(GameUtil.isDesktop?"tip_pc2.png":"tip_mobile2.png"));
					_$(new BaseScriptExecutor() {public void init() {
						RPG.ctrl.cg.push(tip1.color(1,1,1,0).oranCenter().position((int)(GameUtil.screen_width/2-tip1.getWidth()/2), (int)(GameUtil.screen_height/2-+tip1.getHeight()/2))).addAction(Actions.color(new Color(1,1,1,1),0.3f));
					}});
					_$(say("   "));
					_$(new BaseScriptExecutor() {public void init() {
						RPG.ctrl.cg.dispose(tip1);
						RPG.ctrl.cg.push(tip2.oranCenter().position((int)(GameUtil.screen_width/2-tip2.getWidth()/2), (int)(GameUtil.screen_height/2-+tip2.getHeight()/2)));
					}});
					_$(say("   "));
					_$(new BaseScriptExecutor() {public void init() {
						tip2.addAction(Actions.sequence(Actions.fadeOut(0.3f),Actions.run(new Runnable() {public void run() {
							RPG.ctrl.cg.dispose(tip2);
						}})));
						black.addAction(Actions.sequence(Actions.color(new Color(0,0,0,0),0.3f),Actions.run(new Runnable() {public void run() {
							RPG.ctrl.cg.dispose(black);
						}})));
					}});
					setKeyLocker(false);
				}});
				
			}
			removeSelf();
			
		}
	}
	
}
