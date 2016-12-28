package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.AssociationSkill;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.IMenuView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.hover.SupportView;

public class TacticView extends IMenuView {
	int page=2;
	WidgetGroup group;
	TextButtonStyle butstyle;
	ParticleEffect eff;
	Image linkerc,linkerl,linkerr;
	Image linkbox1,linkbox2;
	Label tipLib,tipLib2;
	public View init() {
		eff=new ParticleEffect();
		eff.load(Gdx.files.internal(Setting.PARTICLE+"link.p"),Gdx.files.internal(Setting.PARTICLE));
		eff.setPosition(-1000, -1000);//滚粗
		
		linkerc=Res.get(Setting.IMAGE_MENU_TACTIC+"link_effect.png").disableTouch();
		linkerl=Res.get(Setting.IMAGE_MENU_TACTIC+"link_effect_left.png").disableTouch();
		linkerr=Res.get(Setting.IMAGE_MENU_TACTIC+"link_effect_right.png").disableTouch();
		
		linkbox1=Res.get(Setting.IMAGE_MENU_TACTIC+"linking_heroselbox2.png").disableTouch();
		linkbox2=Res.get(Setting.IMAGE_MENU_TACTIC+"linking_heroselbox2.png").disableTouch();
		
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()),MenuView.stage.getBatch());
		stage.setDebugAll(Setting.persistence.uiDebug);
		
		butstyle=new TextButtonStyle();
		butstyle.down=Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"link_but_active.png");
		butstyle.up=Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"link_but.png");
		butstyle.font=Res.font.get(18);
		
		group=new WidgetGroup();
		group.setSize(GameUtil.stage_width*page,  GameUtil.stage_height);
		stage.addActor(group);
		
		group.addActor(linkerc.color(1,1,1,0));
		group.addActor(linkerl.color(1,1,1,0));
		group.addActor(linkerr.color(1,1,1,0));
		stage.addActor(linkbox1.color(1,1,1,0));
		stage.addActor(linkbox2.color(1,1,1,0));
		
		group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"link.png").position(300, 450).object(new PageMask()));
		group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"support.png").position(300+1024, 440).object(new PageMask()));
		
		generateHeroImage();
		generateSupport();
		
		
		group.addActor(tipLib=new Label("", 26).width(200).position(680, 478).right());
		group.addActor(tipLib2=new Label("", 15).width(200).position(680, 448).right());
		pageTo(1);
		return this;
	}
	
	int speeda=15;
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb = (SpriteBatch) stage.getBatch();
		sb.begin();
		if(linkeff){
			int speed=speeda++;
			if((left+=speed) > right){
				linkeff=false;
				eff.allowCompletion();
				linkerc.action(Actions.fadeOut(0.2f));
				linkerl.action(Actions.fadeOut(0.2f));
				linkerr.action(Actions.fadeOut(0.2f));
				linkbox1.position(oleft-42, 124).color(Color.valueOf("7eff4500")).action(Actions.sequence(Actions.fadeIn(0.15f),Actions.fadeOut(0.85f,Interpolation.pow4)));
				linkbox2.position(oright-82, 124).color(Color.valueOf("7eff4500")).action(Actions.sequence(Actions.fadeIn(0.15f),Actions.fadeOut(0.85f,Interpolation.pow4)));
				speeda=15;
			}else{
				if(eff.isComplete())
					eff.reset();
				linkerc.setWidth(linkerc.getWidth()+speed);
				linkerr.setPosition(left+linkerl.getWidth(), 265);
				eff.setPosition(left, 270);
			}
		}
		eff.draw(batch, Gdx.graphics.getDeltaTime());
		sb.end();
	}

	public void logic() {
		stage.act();
	}

	public void onkeyDown(int keyCode) {
		if (Keys.ESCAPE == keyCode || keyCode == Keys.X) {
			this.disposed = true;
		} else
			stage.keyDown(keyCode);
		
	}

	public void dispose() {
		stage.dispose();
		eff.dispose();
	}
	
	ArrayList<HeroImg> imglist;
	private void generateHeroImage() {
		imglist=new ArrayList<HeroImg>();
		for(int i=0;i<4;i++){
			Hero hero=null;
			try{
				hero=RPG.ctrl.hero.currentHeros.get(i);
			}catch(Exception e){}
			imglist.add(new HeroImg(hero,i));
		}
		addTip();
	}
	
	public void addTip(){
		group.addActor(new Label("连携成功后将获得连携技能", 30).width(1000).position(215,124).userObject(new HeroImgMask4()));
		group.addActor(new Label("获得的连携技能将根据连携者的不同而不同。两名非主角连携后，仅能获得最基本的“追击”技能。\n在少数情况下，两名非主角连携后将获得额外的特殊连携技能。\n而结城有栖（主角）不受此限制影响：\n结城有栖与任何角色连携后，都会获得基础的“追击”加与角色对应社群等级的连携技能。", 16).width(1000).position(215,22).userObject(new HeroImgMask4()));
	}
	
	HeroImg currentLinking;
	class HeroImg{
		Hero hero;
		int idx;
		HeroImg that = this;
		public HeroImg(final Hero hero, final int idx){
			this.hero=hero;
			this.idx=idx;
			final Image bg=Res.get(Setting.IMAGE_MENU_TACTIC+"link_herobg.png");
			group.addActor(bg.position(200*idx+214, 170).onClick(new Runnable() {
				@Override
				public void run() {
					if (hero != null)
						if ((currentLinking == null) || (currentLinking != null && currentLinking.hero != that.hero && that.hero.linkTo == null)) {
							tipLib2.color(1, 1, 1, 0).addAction(Actions.fadeIn(0.2f));
							if (hero.linkTo == null && currentLinking == null) {
								tipLib.setText(hero.name + "仍未连携");
								tipLib2.setText("点击连携此角色按钮开始连携");
							} else if (currentLinking != null) {
								tipLib.setText("选择角色用以与" + currentLinking.hero.name + "连携");
								tipLib2.setText("与" + that.hero.name + "连携预计获得" + that.hero.getLinkSize(currentLinking.hero) + "个技能");
							} else {
								tipLib.setText(hero.name + "与" + hero.linkTo.name + "连携中");
								tipLib2.setText("共获得了" + hero.linkSkills.size() + "个技能");
							}

							Iterator<Actor> it = group.getChildren().iterator();
							while (it.hasNext()) {
								Actor a = it.next();
								if (currentLinking == null) {
									if (a.getUserObject() != null && a.getUserObject() instanceof HeroImgMask)
										it.remove();
								} else {
									if ((a.getUserObject() != null && a.getUserObject() instanceof HeroImgMask2) || (a.getUserObject() != null && a.getUserObject() instanceof HeroImgMask4))
										it.remove();
								}
							}
							if (currentLinking == null) {
								group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "link_heroselbox.png").position(200 * idx + 210, 167).disableTouch().object(new HeroImgMask3()).action(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.color(new Color(1, 1, 1, 0.5f), 0.5f)))));
								group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "link_herosel.png").position(200 * idx + 214, 170).disableTouch().object(new HeroImgMask()));
							} else {
								group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "link_herosel.png").position(200 * idx + 214, 170).disableTouch().object(new HeroImgMask2()));
							}
							if (hero.linkTo != null && getIDX(hero.linkTo) != -1) {
								int tmpIDX = getIDX(hero.linkTo);
								group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "linked_herosel.png").position(200 * tmpIDX + 214, 170).disableTouch().object(new HeroImgMask()));
								group.addActor(new Label("连携中", 35).position(200 * tmpIDX + 250, 275).userObject(new HeroImgMask()).width(200));
								group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "linking_heroselbox.png").position(200 * tmpIDX + 159, 124).disableTouch().object(new HeroImgMask3()).color(Color.valueOf("ffffff77")));
								TextButton but = new TextButton("取消连携关系", butstyle);
								but.setUserObject(new HeroImgMask());
								but.setPosition(200 * idx + 227, 264);
								but.onClick(new Runnable() {
									@Override
									public void run() {
										that.hero.link(that.hero.linkTo, false);
										bg.click();
									}
								});
								group.addActor(but);

								Image tmp;
								for (int i = hero.linkSkills.size() - 1; i >= 0; i--) {
									final int positionX = 100 * i + 214;
									final AssociationSkill skill = hero.linkSkills.get(i);
									group.addActor(tmp = skill.spellcard.getIcon().size(80,80).object(new HeroImgMask()).position(positionX, 60).onClick(new Runnable() {
										@Override
										public void run() {
											Iterator<Actor> it2 = group.getChildren().iterator();
											while (it2.hasNext()) {
												Actor a = it2.next();
												if (a.getUserObject() != null && a.getUserObject() instanceof HeroImgMask4)
													it2.remove();
											}
											group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "link_skill_border.png").object(new HeroImgMask4()).position(positionX - 12, 49).action(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.color(new Color(1, 1, 1, 0.5f), 0.5f)))));
											group.addActor(new Label(skill.spellcard.name, 50).width(1000).position(720, 98).userObject(new HeroImgMask4()).color(1, 1, 1, 0).action(Actions.fadeIn(0.1f)));
											group.addActor(new Label(skill.getClass().getSimpleName(), 30).width(1000).position(740, 84).userObject(new HeroImgMask4()).color(1, 1, 1, 0).action(Actions.alpha(0.12f, 0.5f)));
											group.addActor(new Label("获得条件：连携者等级超过" + skill.level + "级", 18).width(1000).position(720, 65).userObject(new HeroImgMask4()).color(1, 1, 1, 0).action(Actions.fadeIn(0.2f)));
											group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "link_n_bg.png").object(new HeroImgMask4()).position(128, 0).color(1, 1, 1, 0).action(Actions.fadeIn(0.3f)).size(814,30).x(210));
											group.addActor(new Label(skill.spellcard.description, 16).width(1000).position(230, 6).userObject(new HeroImgMask4()));
										}
									}).oranCenter().scale(1.13f).color(1, 1, 1, 0).action(Actions.parallel(Actions.fadeIn(0.3f), Actions.scaleTo(1, 1, 0.3f))));
									tmp.click();
								}

							} else {
								addTip();
								final TextButton but = new TextButton(currentLinking == null ? "连携此角色" : "连携此角色", butstyle);
								but.setUserObject(currentLinking == null ? new HeroImgMask() : new HeroImgMask2());
								but.setPosition(200 * idx + 228, 264);
								final Runnable fin = new Runnable() {
									@Override
									public void run() {
										currentLinking = null;
										Iterator<Actor> it2 = group.getChildren().iterator();
										while (it2.hasNext()) {
											Actor a = it2.next();
											if (a.getUserObject() != null && a.getUserObject() instanceof HeroImgMask2)
												it2.remove();
										}
										bg.click();
									}
								};
								but.onClick(currentLinking == null ? new Runnable() {
									@Override
									public void run() {
										but.setText("取消选择");
										HeroImg.this.setLinkBorder();
										but.setUserObject(new HeroImgMask());
										currentLinking = that;
										tipLib.setText("选择角色用以与" + currentLinking.hero.name + "连携");
										but.onClick(fin);
										for (HeroImg img : imglist) {
											if (img.hero != null)
												if (img != that && img.hero.linkTo == null) {
													group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "linking_heroselbox.png").position(200 * img.idx + 158, 124).disableTouch().object(new HeroImgMask3()).color(Color.valueOf("0660f600")).action(Actions.fadeIn(0.3f)));
												} else if (hero == null || img.hero.linkTo != null) {
													group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "link_disable.png").position(200 * img.idx + 214, 170).disableTouch().object(new HeroImgMask()).color(1, 1, 1, 0).action(Actions.fadeIn(0.3f)));
												}
										}
									}
								} : new Runnable() {
									@Override
									public void run() {
										currentLinking.hero.link(that.hero, true);
										linkEffect(idx, getIDX(that.hero.linkTo));
										fin.run();
									}
								});
								group.addActor(but);
							}
						}
				}
			}));
			if(hero!=null){
				int x = (int)((hero.head[1][0])*hero.head[2][0]);				
				int y = (int)((hero.head[1][1])*hero.head[2][0]);
				group.addActor(Res.get(Setting.IMAGE_FG+hero.fgname+"/Normal.png",hero.head).size(x, y).position(200*idx+214, 170).disableTouch());
			}
			group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"link_mask.png").position(200*idx+214, 170).disableTouch());
			group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"link_level.png").position(200*idx+214+7, 177).color((hero!=null && hero.lead)?Color.valueOf("cc3333"):Color.valueOf("528431")).disableTouch());
			group.addActor(new Label(hero!=null?hero.name:"", 28).overflow(true).width(200).align(200*idx+198, 215));
			group.addActor(new Label(hero!=null?(hero.lead?"LEADER":"Level "+hero.association.level):"<空>", 22).width(200).align(200*idx+198, 182));
		}
		void setLinkBorder(){
			group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"link_heroselbox.png").position(200*idx+210, 167).disableTouch().object(new HeroImgMask()).action(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE,0.5f),Actions.color(new Color(1,1,1,0.5f),0.5f)))).object(new HeroImgMask2()));
		}
	}
	
	public int getIDX(Hero hero){
		for(HeroImg img:imglist)
			if(img.hero.equals(hero))
				return img.idx;
		return -1;
		
	}
	
	boolean linkeff=false;
	int left,right,oleft,oright;
	void linkEffect(int idx2, int idx3) {
		if(!linkeff){
			linkeff=true;
			oleft=left=200*(idx2<idx3?idx2:idx3)+200;
			oright=right=200*(idx2<idx3?idx3:idx2)+240;
			eff.reset();
			eff.setPosition(left, 270);
			linkerl.setPosition(left, 265);
			linkerr.setPosition(left+linkerl.getWidth(), 265);
			linkerc.setPosition(left+linkerl.getWidth(), 265);
			linkerc.setWidth(0);
			linkerl.action(Actions.fadeIn(0.4f)).color(1,1,1,0);
			linkerr.action(Actions.fadeIn(0.4f)).color(1,1,1,0);
			linkerc.action(Actions.fadeIn(0.4f)).color(1,1,1,0);
		}
		
	}
	
	public void pageTo(final int page){
		group.addAction(Actions.moveTo(-GameUtil.stage_width*(page-1), 0,0.6f,Interpolation.pow4));
		Iterator<Actor> i=stage.getActors().iterator();
		while(i.hasNext()){
			Object obj=i.next().getUserObject();
			if(obj!=null && obj instanceof PageMask)
				i.remove();
		}
		stage.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"left.png").position(210, 453).object(new PageMask()).onClick(new Runnable() {
			@Override
			public void run() {
				if(page>1)
					pageTo(page-1);
			}
		}).color(page==1?Color.GRAY:Color.WHITE));
		
		stage.addActor(Res.get(Setting.IMAGE_MENU_TACTIC+"right.png").position(920, 453).object(new PageMask()).onClick(new Runnable() {
			@Override
			public void run() {
				if (page < TacticView.this.page)
					TacticView.this.pageTo(page + 1);
			}
		}).color(page==this.page?Color.GRAY:Color.WHITE));
	}
	
	class HeroImgMask{}
	class HeroImgMask2{}
	class HeroImgMask3 extends HeroImgMask{}
	class PageMask{}
	class HeroImgMask4 extends HeroImgMask{}
	
	class SupGroup{
		Image bg;
		int x,y;
		Table table;
		SupGroup(int x,int y){
			this.x=x;
			this.y=y;
			table=new Table();
			ScrollPane pane=new ScrollPane(table);
			pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"scrollbar.png");
			pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"scrollbarin.png");
			pane.setSize(295, 315);
			pane.setPosition(x, y-13);
			group.addActor(new Image(Setting.IMAGE_MENU_TACTIC+"sup_box.png").position(x-32, y-50));
			group.addActor(pane);
			pane.setForceScroll(false, true);
			pane.setScrollingDisabled(true, false);
		}
		
		SupGroup generate(List<Hero> heroList){
			for(Hero hero:heroList){
				final SupImage si=new SupImage(Res.getTexture(Setting.IMAGE_MENU_TACTIC+"sup_herobox.png"));
				table.add(si.generate(hero,this).onClick(new Runnable() {
					@Override
					public void run() {
						List<SupImage> all = new ArrayList<SupImage>();
						all.addAll(s_l.getAll());
						all.addAll(s_r.getAll());
						for (SupImage s : all)
							s.select = false;
						si.select = true;
						currentSelect = si;
					}
				})).prefSize(300, 58).row();
			}
			table.align(Align.top);
			for (Cell<?> c : table.getCells()) {
				c.padTop(3).padBottom(3);
			}

			return this;
		}
		
		List<SupImage> getAll(){
			List<SupImage> imgs=new ArrayList<SupImage>();
			for (Cell<?> c : table.getCells()) {
				imgs.add((SupImage) c.getActor());
			}

			return imgs;
		}
		
	}
	
	SupGroup s_l,s_r;
	SupImage currentSelect=null;
	@SuppressWarnings("rawtypes")
	private void generateSupport() {
		s_l=new SupGroup(1240, 65).generate(RPG.ctrl.hero.getOtherHeros(true));
		s_r=new SupGroup(1694, 65).generate(RPG.global.support);
		final Label nl,nr;
		group.addActor(nl=new Label("",20).width(1000).align(885, 383));
		group.addActor(nr=new Label("",20).width(1000).align(1350, 383));
		final Runnable func_resetLabel= new Runnable() {
			@Override
			public void run() {
				nl.setText("可用社群角色(" + s_l.table.getCells().size + ")");
				nr.setText("已选择的支援(" + s_r.table.getCells().size + ")");
			}
		};
		func_resetLabel.run();
		final ImageButton l;
		group.addActor(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_right.png"), Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_right_p.png")).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentSelect != null && s_l == currentSelect.parent) {
					RPG.global.support.add(currentSelect.hero);
					Iterator<Cell> i = s_l.table.getCells().iterator();
					while (i.hasNext())
						if (i.next().getActor() == currentSelect)
							i.remove();
					s_l.table.layout();

					s_r.table.add(currentSelect).prefSize(300, 58).row();
					for (Cell<?> c : s_r.table.getCells()) {
						c.padTop(3).padBottom(3);
					}

					s_r.table.layout();
					currentSelect.select = false;
					currentSelect.parent = s_r;
					currentSelect = null;
					func_resetLabel.run();
				}
			}
		}).pos(1573, 320));
		group.addActor(l=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_left.png"), Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_left_p.png")).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentSelect != null && s_r == currentSelect.parent) {
					RPG.global.support.remove(currentSelect.hero);
					Iterator<Cell> i = s_r.table.getCells().iterator();
					while (i.hasNext())
						if (i.next().getActor() == currentSelect)
							i.remove();
					s_r.table.layout();

					s_l.table.add(currentSelect).prefSize(300, 58).row();
					
					for (Cell<?> c : s_l.table.getCells()) {
						c.padTop(3).padBottom(3);
					}

					s_l.table.layout();
					currentSelect.select = false;
					currentSelect.parent = s_l;
					currentSelect = null;
					func_resetLabel.run();
				}
			}
		}).pos(1573, 240));
		group.addActor(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_ll.png"), Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_ll_p.png")).onClick(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < s_r.table.getCells().size; i++) {
					((Image) s_r.table.getCells().first().getActor()).click();
					l.click();
				}
			}
		}).pos(1573, 160));
		group.addActor(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_info.png"), Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"sup_info_p.png")).onClick(new Runnable() {
			@SuppressWarnings("serial")
			@Override
			public void run() {
				if (currentSelect != null && currentSelect.hero!=null) {
					RPG.popup.add(SupportView.class,new HashMap<Object, Object>(){{
						put("hero",currentSelect.hero);
						put("title",currentSelect.hero.name);
					}});
				}
			}
		}).pos(1573, 80));
	}
	
	class SupImage extends Image{
		SupGroup parent;
		Image face,sbox;
		Label name,level,association;
		boolean select;
		Hero hero;
		
		public SupImage(Texture texture) {
			super(texture);
		}

		public SupImage generate(Hero hero,SupGroup parent){
			this.parent=parent;
			this.hero=hero;
			int x = (int)((hero.face[1][0])*hero.face[2][0]);				
			int y = (int)((hero.face[1][1])*hero.face[2][0]);
			sbox=Res.get(Setting.IMAGE_MENU_TACTIC+"sup_herobox_sel.png").disableTouch();
			face=Res.get(Setting.IMAGE_FG+hero.fgname+"/Normal.png",hero.face).size(x, y);
		//	face=Res.get(Setting.IMAGE_FG+hero.fgname+"/face.png");
			name=new Label(hero.name,24).width(1000);
			level=new Label(hero.support!=null?hero.support.name:"无支援技能",16).width(1000);
			association=new Label(hero.association.name,32).color(1,1,1,.16f).width(1000);
			return this;
		}
		
		public void draw(Batch sb,float parentAlpha){
			super.draw(sb, parentAlpha);
			face.setPosition(getX(), getY());
			face.draw(sb);
			name.setPosition(getX()+120, getY()+28);
			name.draw(sb, parentAlpha);
			level.setPosition(getX()+120, getY()+7);
			level.draw(sb, parentAlpha);
			association.setPosition(getX()+220, getY()+10);
			association.draw(sb, parentAlpha);
			sbox.setPosition(getX(), getY()-1);
			if(select)
				sbox.draw(sb);
		}
	
		
		
	}
	
	@Override
	public boolean allowEsc() {
		return true;
	}
	
	
}
