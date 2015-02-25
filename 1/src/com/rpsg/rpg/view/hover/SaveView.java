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
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;

public class SaveView extends HoverView{
	TextButtonStyle butstyle;
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
		
		TextButton llbutton=new TextButton("<<", butstyle).onClick(()->{
			if(currentPageStart-5>1)
				currentPageStart-=5;
			else
				currentPageStart=1;
				generateList();
		});
		llbutton.setOffset(5).setPad(-5).setHof(-1).setSize(62,33);
		llbutton.setPosition(44,422);
		stage.addActor(llbutton);
		
		TextButton lbutton=new TextButton("<", butstyle).onClick(()->{
			if(currentPageStart-1>1)
				currentPageStart-=1;
			else
				currentPageStart=1;
				generateList();
		});
		lbutton.setOffset(1).setPad(-5).setHof(-1).setSize(62,33);
		lbutton.setPosition(124,422);
		stage.addActor(lbutton);
		
		TextButton rbutton=new TextButton(">", butstyle).onClick(()->{
			if(currentPageStart+5<Setting.GAME_SAVE_FILE_MAX_PAGE)
				currentPageStart+=1;
			else
				currentPageStart=Setting.GAME_SAVE_FILE_MAX_PAGE-5;
				generateList();
		});
		rbutton.setOffset(1).setPad(-5).setHof(-1).setSize(62,33);
		rbutton.setPosition(715,422);
		stage.addActor(rbutton);
		
		TextButton rrbutton=new TextButton(">>", butstyle).onClick(()->{
			if(currentPageStart+10<Setting.GAME_SAVE_FILE_MAX_PAGE)
				currentPageStart+=5;
			else
				currentPageStart=Setting.GAME_SAVE_FILE_MAX_PAGE-5;
				generateList();
		});
		rrbutton.setOffset(5).setPad(-5).setHof(-1).setSize(62,33);
		rrbutton.setPosition(795,422);
		stage.addActor(rrbutton);
		
		generateList();
		
	}
	
	int currentPageStart=1,currentPage=1;
	public void generateList(){
		com.badlogic.gdx.utils.Array<Actor> removeList=new Array<Actor>();
		stage.getActors().forEach((Actor actor)->{
			if(actor.getUserObject()!=null)
				removeList.add(actor);
		});
		stage.getActors().removeAll(removeList, true);
		
		if(!(currentPageStart+5>Setting.GAME_SAVE_FILE_MAX_PAGE))
			for(int i=0;i<5;i++){
//				if(currentPageStart+i>=Setting.GAME_SAVE_FILE_MAX_PAGE)
//					continue;
				TextButton tmp=new TextButton(""+(currentPageStart+i), butstyle);
				tmp.setUserObject(new Mark());
				tmp.setOffset(1).setPad(tmp.getText().length()==2?-2:-4).setHof(-1).onClick(()->{
					currentPage=Integer.parseInt(tmp.getText().toString());
					generateList();
				}).setSize(62,33);
				tmp.setPosition(260+i*80,422);
				stage.addActor(tmp);
			}
		
		stage.getActors().forEach((Actor actor)->{
			if(actor instanceof TextButton){
				TextButton tmp=(TextButton)actor;
				if(String.valueOf(currentPage).equals(tmp.getText())){
					Image img=new Image(butstyle.down);
					img.setSize(62, 33);
					img.setPosition(tmp.getX(), tmp.getY());
					img.setUserObject(new Mark());
					stage.addActor(img);
					stage.addActor(new Label(currentPage+"",22).setWidth(1000).setPos((int) tmp.getX()+(tmp.getText().length()==2?1:13), (int)tmp.getY()+26).setPad(tmp.getText().length()==2?-2:-4).userObj(new Mark()));
					tmp.remove();
				}
			}
		});
		if(currentPage!=Setting.GAME_SAVE_FILE_MAX_PAGE-1){
			TextButton autobut=new TextButton("Auto", butstyle).onClick(()->{
				currentPageStart=Setting.GAME_SAVE_FILE_MAX_PAGE-5;
				currentPage=Setting.GAME_SAVE_FILE_MAX_PAGE-1;
				generateList();
			});
			autobut.setOffset(17).setPad(-5).setHof(-1).setSize(110,33);
			autobut.setPosition(874,422);
			autobut.setUserObject(new Mark());
			stage.addActor(autobut);
		}else{
			Image img=new Image(butstyle.down);
			img.setSize(110, 33);
			img.setPosition(874,422);
			img.setUserObject(new Mark());
			stage.addActor(img);
			stage.addActor(new Label("Auto",22).setWidth(1000).setPos(887, 448).setPad(-5).userObj(new Mark()));
		}
		for(int i=0;i<4;i++){
			Image im=Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebl.png").position(i>1?44+(i-2)*483:44+i*483, i>1?270:114);
			im.setUserObject(new Mark());
			System.out.println(im.getX());
			stage.addActor(im);
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
	
	class Mark{
		
	}
}
