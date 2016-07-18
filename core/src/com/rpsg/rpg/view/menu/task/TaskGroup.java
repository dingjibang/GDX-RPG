package com.rpsg.rpg.view.menu.task;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Task;
import com.rpsg.rpg.object.base.Task.TaskType;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.ListItem;
import com.rpsg.rpg.system.ui.UI;
import com.rpsg.rpg.utils.game.Colors;
import com.rpsg.rpg.view.hover.ConfirmView;

public class TaskGroup extends Group{
	
	List<Task> list;
	static Color doingColor = Colors.of("ec5c5c"), doneColor = Colors.of("7aec5c");
	static Label doing = Res.get("[进行中]",14).color(doingColor), done = Res.get("[已完成]",14).color(doneColor);
	static Label main = Res.get(" [主线]",14), secondly = Res.get(" [支线]",14);
	Group info = new Group();
	ScrollPane pane;
	
	public TaskGroup() {
		generateList();
	}
	
	private void generateList(){
		clear();
		info.clear();
		
		Res.base().query().setSize(768, 251).setColor(0,0,0,.75f).setPosition(222, 197).appendTo(this);
		Res.base().color(0,0,0,.75f).query().setPosition(222, 25).setSize(768, 154).appendTo(this);
		
		list = RPG.ctrl.task.task();
		Table table = new Table().left().top();
		
		float top = pane == null ? 0 : pane.getScrollPercentY();
		
		$.add(pane = UI.scrollPane(table)).appendTo(this).setSize(768, 251).setPosition(222, 197);
		
		for(Task task : list){
			ListItem item = new ListItem();
			item.insert(task.canEnd() ? done : doing);
			item.insert(task.type == TaskType.main ? main : secondly);
			item.addText(task.name, 19);
			item.setUserObject(task);
			table.add(item).row();
		}
		
		table.setTouchable(Touchable.enabled);
		
		$.add(table.padBottom(40)).eachCells(ListItem.class, c -> c.size(768,36).getActor(a -> a.size(708,30)).left().click(() -> {
			c.getActor().select(true);
			$.each(c.others(), c2 -> ((ListItem)c2.getActor()).select(false));
			set(c.getActor().getUserObject());
		}));
		
		pane.setSmoothScrolling(false);
		pane.layout();
		pane.setScrollPercentY(top);
		
		Res.base().disableTouch().query().setSize(752,27).setPosition(230, 197).setColor(.1f,.1f,.1f,.95f).appendTo(this);
		Res.get("正在进行"+list.size()+"个任务",16).width(768).center().query().setPosition(222, 202).appendTo(this);
		
		info.remove();
		addActor(info);
	}
	
	private void set(Object _task){
		if(_task == null || !(_task instanceof Task)) return;
		
		Task task = (Task)_task;
		info.clear();
		
		Res.base().size(768, 2).color(task.canEnd() ? doneColor : doingColor).position(222, 177).query().appendTo(info);
		
		Res.get("[" + (task.type == TaskType.main ? "主线" : "支线") + "]  " + task.name, 22).query().setPosition(250, 141).appendTo(info);
		if(task.by != null && task.by.length() != 0)
			Res.get("委托人：" + task.by, 16).width(200).right().query().setPosition(task.giveup ?630 : 766, 144).appendTo(info);
		if(task.giveup)
			UI.imageButton().size(129, 31).setFg(Res.getNP(Setting.IMAGE_MENU_TASK + "remove_task.png")).query().setPosition(836, 137).appendTo(info).click(()->{
				RPG.popup.add(ConfirmView.okCancel("确定要删除任务["+task.name+"]么？", v->{
					v.disposed = true;
					RPG.ctrl.task.removeTask(task.id);
					generateList();
				}));
			});
		
		Res.base().query().setSize(715, 2).setPosition(250, 127).appendTo(info);
		
		$.add(UI.miniPane(
			Res.get(task.description+"\n\n"+task.description2,18).warp(true).align(Align.topLeft)
		)).setSize(715, 82).setPosition(250, 35).appendTo(info);
		
		
	}
}
