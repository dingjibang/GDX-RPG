package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.io.SL;
import com.rpsg.rpg.object.base.ObjectRunnable;
import com.rpsg.rpg.object.base.SLData;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.utils.display.AlertUtil;

public class SaveView extends HoverView{
	TextButtonStyle butstyle;
	public int currentSelect=-1;
	public TextButton autobut,savebutton;
	public void init() {
		stage.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebg.png").color(1,1,1,0).action(Actions.fadeIn(0.2f)));
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"file_exit.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"file_exit_active.png"));
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
		butstyle.up=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebut.png");
		butstyle.down=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebuth.png");
		
		TextButton llbutton=new TextButton("<<", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart - 5 > 1)
					currentPageStart -= 5;
				else
					currentPageStart = 1;
				SaveView.this.generateList();
			}
		});
		llbutton.setOffset(5).setPad(-5).setHof(-1).setSize(62,33);
		llbutton.setPosition(44,422);
		stage.addActor(llbutton);
		
		TextButton lbutton=new TextButton("<", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart - 1 > 1)
					currentPageStart -= 1;
				else
					currentPageStart = 1;
				SaveView.this.generateList();
			}
		});
		lbutton.setOffset(1).setPad(-5).setHof(-1).setSize(62,33);
		lbutton.setPosition(124,422);
		stage.addActor(lbutton);
		
		TextButton rbutton=new TextButton(">", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart + 5 < Setting.GAME_SAVE_FILE_MAX_PAGE)
					currentPageStart += 1;
				else
					currentPageStart = Setting.GAME_SAVE_FILE_MAX_PAGE - 5;
				SaveView.this.generateList();
			}
		});
		rbutton.setOffset(1).setPad(-5).setHof(-1).setSize(62,33);
		rbutton.setPosition(715,422);
		stage.addActor(rbutton);
		
		TextButton rrbutton=new TextButton(">>", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentPageStart + 10 < Setting.GAME_SAVE_FILE_MAX_PAGE)
					currentPageStart += 5;
				else
					currentPageStart = Setting.GAME_SAVE_FILE_MAX_PAGE - 5;
				SaveView.this.generateList();
			}
		});
		rrbutton.setOffset(5).setPad(-5).setHof(-1).setSize(62,33);
		rrbutton.setPosition(795,422);
		stage.addActor(rrbutton);
		
		savebutton=new TextButton("保存游戏", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				HoverController.add(ConfirmView.getDefault("确定要保存到这个位置么？", new ObjectRunnable() {
					@Override
					public void run(Object view) {
						if (currentSelect != -1) {
							if (SL.save(currentSelect))
								AlertUtil.add("存档完成。", AlertUtil.Green);
							else
								AlertUtil.add("存档失败。", AlertUtil.Red);
							SaveView.this.generateList();
						} else {
							AlertUtil.add("请选择要保存的位置。", AlertUtil.Yellow);
							Music.playSE("err");
						}
						((HoverView) view).disposed = true;
					}
				}));
			}
		});
		savebutton.setOffset(10).setPad(1).setHof(-2).setSize(192,58);
		savebutton.setPosition(562,28);
		stage.addActor(savebutton);
		savebutton.setName("mask");
		
		TextButton cancelbutton=new TextButton("取消", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				disposed = true;
			}
		});
		cancelbutton.setOffset(10).setPad(1).setHof(-2).setSize(192,58);
		cancelbutton.setPosition(792,28);
		stage.addActor(cancelbutton);
		
		TextButton deletebutton=new TextButton("删除档案", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				HoverController.add(ConfirmView.getDefault("确定要删除这个档案么？", new ObjectRunnable() {
					@Override
					public void run(Object view) {
						if (currentSelect != -1) {
							if (SL.delete(currentSelect)) {
								AlertUtil.add("删除档案成功。", AlertUtil.Green);
								SaveView.this.generateList();
							} else
								AlertUtil.add("删除档案失败。", AlertUtil.Red);
						} else
							AlertUtil.add("请选择要删除的档案。", AlertUtil.Yellow);
						((HoverView) view).disposed = true;
					}
				}));
			}
		});
		deletebutton.setOffset(10).setPad(1).setHof(-2).setSize(192,58);
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
		
		if(!(currentPageStart+5>Setting.GAME_SAVE_FILE_MAX_PAGE))
			for(int i=0;i<5;i++){
//				if(currentPageStart+i>=Setting.GAME_SAVE_FILE_MAX_PAGE)
//					continue;
				final TextButton tmp=new TextButton(""+(currentPageStart+i), butstyle);
				tmp.setUserObject(new Object());
				tmp.setOffset(1).setPad(tmp.getText().length()==2?-2:-4).setHof(-1).onClick(new Runnable() {
                    @Override
                    public void run() {
                        currentPage = Integer.parseInt(tmp.getText().toString());
                        SaveView.this.generateList();
                    }
                }).setSize(62,33);
				tmp.setPosition(260+i*80,422);
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
                    stage.addActor(new Label(currentPage+"",22).setWidth(1000).setPos((int) tmp.getX()+(tmp.getText().length()==2?1:13), (int)tmp.getY()+26).setPad(tmp.getText().length()==2?-2:-4).userObj(new Object()));
                    tmp.remove();
                }
            }
        }

        if(currentPage!=Setting.GAME_SAVE_FILE_MAX_PAGE-1){
			autobut=new TextButton("Auto", butstyle).onClick(new Runnable() {
                @Override
                public void run() {
                    currentPageStart = Setting.GAME_SAVE_FILE_MAX_PAGE - 5;
                    currentPage = Setting.GAME_SAVE_FILE_MAX_PAGE - 1;
                    SaveView.this.generateList();
                }
            });
			autobut.setOffset(17).setPad(-5).setHof(-1).setSize(110,33);
			autobut.setPosition(874,422);
			autobut.setUserObject(new Object());
			stage.addActor(autobut);
		}else{
			Image img=new Image(butstyle.down);
			img.setSize(110, 33);
			img.setPosition(874,422);
			img.setUserObject(new Object());
			stage.addActor(img);
			stage.addActor(new Label("Auto",22).setWidth(1000).setPos(887, 448).setPad(-5).userObj(new Object()));
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
	
	public void logic() {
		stage.act();
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	public void close() {
		
	}

	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE)
			disposed=true;
		return stage.keyDown(keycode);
	}
	
}
