package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.io.SL;
import com.rpsg.rpg.object.base.SLData;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.TextButton;

public class SaveView extends HoverView{
	TextButtonStyle butstyle;
	public int currentSelect=-1;
	public TextButton autobut,savebutton;
	public void init() {
		stage.addActor(Res.get(Setting.IMAGE_MENU_SYSTEM+"savebg.png").color(1,1,1,0).action(Actions.fadeIn(0.2f)));
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"file_exit.png"),Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"file_exit_active.png"));
		exit.setPosition(945, 530);
		exit.addAction(Actions.moveTo(945, 500,0.1f));
		exit.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				disposed=true;
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(exit);
		
		butstyle=new TextButtonStyle();
		butstyle.up=Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"savebut.png");
		butstyle.down=Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"savebuth.png");
		butstyle.font=Res.font.get(18);
		
		stage.addActor($.add(new TextButton("<<", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart - 5 > 1)
					currentPageStart -= 5;
				else
					currentPageStart = 1;
				SaveView.this.generateList();
			}
		}).setSize(62,33).setPosition(44,422).getItem());
		
		
		stage.addActor($.add(new TextButton("<", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart - 1 > 1)
					currentPageStart -= 1;
				else
					currentPageStart = 1;
				SaveView.this.generateList();
			}
		}).setSize(62,33).setPosition(124,422).getItem());
		
		
		stage.addActor($.add(new TextButton(">", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart + 5 < Setting.SAVE_FILE_MAX_PAGE)
					currentPageStart += 1;
				else
					currentPageStart = Setting.SAVE_FILE_MAX_PAGE - 5;
				SaveView.this.generateList();
			}
		}).setSize(62,33).setPosition(715,422).getItem());
		

		stage.addActor($.add(new TextButton(">>", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart + 10 < Setting.SAVE_FILE_MAX_PAGE)
					currentPageStart += 5;
				else
					currentPageStart = Setting.SAVE_FILE_MAX_PAGE - 5;
				SaveView.this.generateList();
			}
		}).setSize(62,33).setPosition(795,422).getItem());
		
		savebutton=$.add(new TextButton("保存游戏", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				RPG.popup.add(ConfirmView.okCancel("确定要保存到这个位置么？", new CustomRunnable<HoverView>() {
					@Override
					public void run(HoverView view) {
						if (currentSelect != -1) {
							if (SL.save(currentSelect))
								RPG.putMessage("存档完成", Color.GREEN);
							else
								RPG.putMessage("存档失败", Color.RED);
							SaveView.this.generateList();
						} else {
							RPG.putMessage("请选择要保存的位置", Color.YELLOW);
							Music.playSE("err");
						}
						((HoverView) view).disposed = true;
					}
				}));
			}
		}).setSize(192,58).setPosition(562, 28).getItem(TextButton.class);
		
		savebutton.setName("mask");
		stage.addActor(savebutton);
		
		TextButton cancelbutton=$.add(new TextButton("取消", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				disposed = true;
			}
		}).getItem(TextButton.class);
		cancelbutton.setSize(192,58);
		cancelbutton.setPosition(792,28);
		stage.addActor(cancelbutton);
		
		TextButton deletebutton=$.add(new TextButton("删除档案", butstyle)).click(new Runnable() {
			@Override
			public void run() {
				RPG.popup.add(ConfirmView.okCancel("确定要删除这个档案么？", new CustomRunnable<HoverView>() {
					@Override
					public void run(HoverView view) {
						if (currentSelect != -1) {
							if (SL.delete(currentSelect)) {
								RPG.putMessage("删除档案成功", Color.GREEN);
								SaveView.this.generateList();
							} else
								RPG.putMessage("删除档案失败", Color.RED);
						} else
							RPG.putMessage("请选择要删除的档案", Color.YELLOW);
						((HoverView) view).disposed = true;
					}
				}));
			}
		}).getItem(TextButton.class);
		deletebutton.setSize(192,58);
		deletebutton.setPosition(45,28);
		stage.addActor(deletebutton);
		
		generateList();
		for (Actor act : stage.getActors()) {
			if(act instanceof TextButton && act.getUserObject()==null)
				act.addListener(new InputListener(){
					public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
						Music.playSE("snd210.wav");
					}
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
						return true;
					}
				});
		}
		
	}
	
	int currentPageStart=1,currentPage=1;
	public void generateList(){
		currentSelect=-1;
		Array<Actor> removeList=new Array<Actor>();
        for (Actor actor : stage.getActors()) {
            if(actor.getUserObject()!=null)
                removeList.add(actor);
        }

        stage.getActors().removeAll(removeList, true);
		
        if(!(currentPageStart+5>Setting.SAVE_FILE_MAX_PAGE))
			for(int i=0;i<5;i++){
//				if(currentPageStart+i>=Setting.GAME_SAVE_FILE_MAX_PAGE)
//					continue;
				final TextButton tmp=new TextButton(""+(currentPageStart+i), new TextButtonStyle(butstyle));
				
				tmp.setUserObject(new Object());
				$.add(tmp).click(new Runnable() {
					@Override
					public void run() {
						currentPage = Integer.parseInt(tmp.getText().toString());
						SaveView.this.generateList();
					}
				}).setSize(62,33);
				tmp.setPosition(260+i*80,422);
				if(currentPage==currentPageStart+i)
					tmp.getStyle().up=tmp.getStyle().down;
				stage.addActor(tmp);
			}

		for (Actor actor : stage.getActors()) {
			if(actor instanceof TextButton){
				TextButton tmp=(TextButton)actor;
				if(String.valueOf(currentPage).equals(tmp.getText())){
					Image img=new Image(butstyle.down);
					img.setSize(62, 33);
					img.setPosition(tmp.getX(), tmp.getY());
					img.setUserObject(new Object());
					stage.addActor(img);
					stage.addActor($.add(Res.font.getLabel(currentPage+"",22)).setWidth(300).setPosition((int) tmp.getX()+(tmp.getText().length()==2?1:13), (int)tmp.getY()+26).setUserObject(new Object()).getItem());
					tmp.remove();
				}
			}
		}

		if(currentPage!=Setting.SAVE_FILE_MAX_PAGE-1){
			autobut=$.add(new TextButton("Auto", butstyle)).click(new Runnable() {
				@Override
				public void run() {
					currentPageStart = Setting.SAVE_FILE_MAX_PAGE - 5;
					currentPage = Setting.SAVE_FILE_MAX_PAGE - 1;
					SaveView.this.generateList();
				}
			}).getItem(TextButton.class);
			
			autobut.setSize(110,33);
			autobut.setPosition(874,422);
			autobut.setUserObject(new Object());
			stage.addActor(autobut);
		}else{
			Image img=new Image(butstyle.down);
			img.setSize(110, 33);
			img.setPosition(874,422);
			img.setUserObject(new Object());
			stage.addActor(img);
			stage.addActor($.add(Res.font.getLabel("Auto",18)).setWidth(110).setAlign(Align.center).setPosition(874,428).setUserObject(new Object()).getItem(Label.class));
		}
		for(int i=0;i<4;i++){
			SLData.generate((currentPage-1)*4+i,i,stage,this);
		}

        for (Actor act: stage.getActors()) {
            if(act instanceof TextButton && act.getUserObject()!=null)
                act.addListener(new InputListener(){
                    public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
                        Music.playSE("snd210.wav");
                    }
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
                        return true;
                    }
                });
        }

    }
	
	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE)
			disposed=true;
		return stage.keyDown(keycode);
	}
	
}
