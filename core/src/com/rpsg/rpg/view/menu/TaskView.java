package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxCellQuery;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.IMenuView;
import com.rpsg.rpg.system.ui.UI;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.menu.task.AchievementGroup;
import com.rpsg.rpg.view.menu.task.IndexGroup;
import com.rpsg.rpg.view.menu.task.NoteGroup;
import com.rpsg.rpg.view.menu.task.TaskGroup;

@SuppressWarnings("unchecked")
public class TaskView extends IMenuView {

	List<Group> groupList = new ArrayList<>();
	Group group = null;
	
	public TaskView init() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()), MenuView.stage.getBatch());
		
		GdxQuery tab = $.add(new Table().left().top()).appendTo(stage).setWidth(724).setPosition(222, 530)
			.cell(UI.checkbox(Setting.IMAGE_MENU_TASK+"tab_task.png")).bind(TaskGroup.class)
			.cell(UI.checkbox(Setting.IMAGE_MENU_TASK+"tab_ach.png")).bind(AchievementGroup.class).padLeft(102)
			.cell(UI.checkbox(Setting.IMAGE_MENU_TASK+"tab_idx.png")).bind(IndexGroup.class).padLeft(13)
			.cell(UI.checkbox(Setting.IMAGE_MENU_TASK+"tab_note.png")).bind(NoteGroup.class).padLeft(13)
		.end();
		
		tab.eachCells(CheckBox.class,c -> c.size(160,62).click(() -> {
			tab.children().not(c.getActor()).each(a -> ((CheckBox)a).check(false));
			c.getActor().check(true);
			setGroup((Class<Group>)c.getActor().getUserObject());
		}).clickIf(GdxCellQuery.FIRST_CHILD));
		
		return this;
	}
	
	
	
	public void setGroup(Class<Group> group){
		
		boolean has = $.has(this.groupList, group);
		
		//debug
		if(has){
			has = !has;
			this.groupList.remove($.getIf(this.groupList, g -> g.getClass().equals(group)));
		}
		if(!has)
			try {
				this.groupList.add(group.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		if(this.group != null) this.group.remove();
		
		this.group = $.getIf(this.groupList, g -> g.getClass().equals(group));
		
		stage.addActor(this.group);
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
	}
	
	public void logic() {
		stage.act();
	}
	
}
