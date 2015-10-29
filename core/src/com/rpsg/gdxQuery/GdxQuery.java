package com.rpsg.gdxQuery;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
/**
 * GDX-Query 
 * more simplified way to enjoy LibGDX
 * 
 * Project website: https://github.com/dingjibang/GDX-Query/
 * RPSG-TEAM: http://www.rpsg-team.com
 * 
 * @author dingjibang
 *
 */
public class GdxQuery {

	private LinkedList<Actor> values = new LinkedList<Actor>();
	
	private Runnable click,touchUp,touchDown;
	
	private GdxQuery father;
	
	private InputListener clickListener=(new InputListener(){
		public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
			if(click!=null && x>=0 && y>=0 && x <= event.getListenerActor().getWidth() && y <= event.getListenerActor().getHeight()) click.run();
			if(touchUp!=null) touchUp.run();
		}
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
			if(touchDown!=null) touchDown.run();
			return true;
		}
	});

	public GdxQuery(Object... a) {
			add(a);
	}
	
	public GdxQuery first(){
		return $.add(values.size()==0?new NullActor():values.get(0)).setFather(father==null?this:father);
	}
	
	public GdxQuery last(){
		return $.add(values.size()==0?new NullActor():values.get(values.size()-1)).setFather(father==null?this:father);
	}
	
	public GdxQuery getFather(){
		return father;
	}
	
	public GdxQuery next(){
		if(father==null)
			return $.add();
		try {
			return $.add(father.getItems().get(father.getItems().indexOf(getItem())+1)).setFather(father);
		} catch (Exception e) {
			return $.add();
		}
	}
	
	public GdxQuery prev(){
		if(father==null)
			return $.add();
		try {
			return $.add(father.getItems().get(father.getItems().indexOf(getItem())-1)).setFather(father);
		} catch (Exception e) {
			return $.add();
		}
	}

	public GdxQuery setOrigin (int alignment){
		for(Actor actor:getItems())
			actor.setOrigin(alignment);
		return this;
	}
	
	public GdxQuery parent(){
		if(isEmpty())
			return $.add();
		return $.add(getItem().getParent());
	}
	
	public Stage getStage(){
		if(isEmpty())
			return null;
		return getItem().getStage();
	}
	
	public Actor getItem(int index){
		if(index>=getItems().size())
			return null;
		else
			return getItems().get(index);
	}
	
	public GdxQuery find(Class<?>... cls){
		GdxQuery query=$.add();
		for(Class<?> c:cls)
			for(Actor actor:getItems())
				if(actor.getClass().equals(c) || actor.getClass().getSuperclass().equals(c))
					query.add(actor);
		return query;
	}
	
	public GdxQuery findByClass(Class<?>... cls){
		return find(cls);
	}
	
	public <T> T findAndParse(@SuppressWarnings("unchecked") Class<T>... cls){
		return find(cls).parse(cls[0]);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T parse(Class<T> clazz){
		return (T)getItem();
	}
	
	public GdxQuery find(Object userObject){
		for(Actor actor:getItems())
			if(actor.getUserObject()!=null && actor.getUserObject().equals(userObject))
				return $.add(actor);
		return $.add();
	}
	
	public GdxQuery remove(Object... o){
		for(Object obj:o){
			$.add(obj).getItem().remove();
			not(obj);
		}
		return this;
	}
	
	public GdxQuery removeAll(){
		for(Actor actor:getItems())
			actor.remove();
		return this;
	}
	
	public GdxQuery setSize(float width,float height){
		for(Actor actor:getItems())
			actor.setSize(width, height);
		return this;
	}
	
	public GdxQuery invalidate(){
		for(Actor actor:getItems())
			if(actor instanceof Widget)
				((Widget) actor).invalidate();
		return this;
	}
	
	public GdxQuery setScale(float scaleXY){
		for(Actor actor:getItems())
			actor.setScale(scaleXY);
		return this;
	}
	
	public GdxQuery setScaleX(float scaleX){
		for(Actor actor:getItems())
			actor.setScaleX(scaleX);
		return this;
	}
	
	public GdxQuery setScaleY(float scaleY){
		for(Actor actor:getItems())
			actor.setScaleY(scaleY);
		return this;
	}

	public GdxQuery setY(float y){
		for(Actor actor:getItems())
			actor.setY(y);
		return this;
	}

	public GdxQuery setX(float x){
		for(Actor actor:getItems())
			actor.setX(x);
		return this;
	}
	
	public float getWidth(){
		return getItem().getWidth();
	}
	
	public float getHeight(){
		return getItem().getHeight();
	}
	
	public GdxQuery setWidth(float width){
		for(Actor actor:getItems())
			actor.setWidth(width);
		return this;
	}
	
	public GdxQuery setHeight(float height){
		for(Actor actor:getItems())
			actor.setHeight(height);
		return this;
	}
	
	public float getX(){
		return getItem().getX();
	}
	
	public float getY(){
		return getItem().getY();
	}
	
	public Vector2 getPosition(){
		return new Vector2(getItem().getX(),getItem().getY());
	}
	
	public GdxQuery setPosition(float x,float y){
		for(Actor actor:getItems())
			actor.setPosition(x, y);
		return this;
	}
	
	public GdxQuery setColor(Color color){
		for(Actor actor:getItems())
			actor.setColor(color);
		return this;
	}
	
	public GdxQuery setAlpha(float a){
		for(Actor actor:getItems())
			actor.getColor().a=a;
		return this;
	}
	
	public GdxQuery setColor(float r,float g,float b,float a){
		for(Actor actor:getItems())
			actor.setColor(r,g,b,a);
		return this;
	}
	
	public GdxQuery setColor(float r,float g,float b){
		return setColor(r,g,b,1);
	}
	
	public GdxQuery fadeOut(float time){
		return colorTo(new Color(1,1,1,0), time);
	}
	
	public GdxQuery fadeOut(){
		return fadeOut(0);
	}
	
	public GdxQuery fadeIn(){
		return fadeIn(0);
	}
	
	public GdxQuery fadeIn(float time) {
		return colorTo(Color.WHITE, time);
	}

	public GdxQuery colorTo(Color color,float duration){
		return addAction(Actions.color(color, duration));
	}
	
	public GdxQuery setVisible(boolean v){
		for(Actor actor:getItems())
			actor.setVisible(v);
		return this;
	} 
	
	public GdxQuery run (GdxQueryRunnable run){
		if(run!=null)
			run.run(this);
		return this;
	}
	
	public GdxQuery each(ActorRunnable run){
		if(run!=null)
			for(Actor actor:getItems())
				run.run(actor);
		return this;
	}
	
	public GdxQuery addAction(Action... action){
//		FIXME this method is not work!
		for(Actor actor:getItems())
			for(Action act:action)
				actor.addAction(act);
		return this;
	}
	
	public GdxQuery setUserObject(Object object){
		for(Actor actor:getItems())
			actor.setUserObject(object);
		return this;
	}
	
	public GdxQuery setAlign(int align){
		for(Actor actor:getItems()){
			if(actor instanceof Label){
				((Label)actor).setAlignment(align);
			}
		}
		return this;
	}
	
	public Object getUserObject(){
		return getItem().getUserObject();
	}
	
	public GdxQuery cleanActions(){
		for(Actor actor:getItems())
			actor.clearActions();
		return this;
	}
	
	public GdxQuery addListener(EventListener... listener){
		for(Actor actor:getItems())
			for(EventListener l:listener)
				actor.addListener(l);
		return this;
	}
	
	public GdxQuery cleanListeners(){
		for(Actor actor:getItems())
			actor.clearListeners();
		return this;
	}
	
	public GdxQuery draw(Batch batch,float pf){
		for(Actor actor:getItems())
			actor.draw(batch,pf);
		return this;
	}
	
	public GdxQuery draw(Batch batch){
		return draw(batch,1);
	}
	
	public GdxQuery act(float time){
		for(Actor actor:getItems())
			actor.act(time);
		return this;
	}
	
	public GdxQuery act(){
		return act(Gdx.graphics.getDeltaTime());
	}
	
	public GdxQuery debug(){
		for(Actor actor:getItems())
			actor.debug();
		return this;
	}
	
	public GdxQuery setDebug(boolean debug){
		for(Actor actor:getItems())
			actor.setDebug(debug);
		return this;
	}
	
	public GdxQuery pack(){
		for(Actor actor:getItems())
			if(actor instanceof Widget)
				((Widget)actor).pack();
		return this;
	}
	
	public GdxQuery fire(Event event){
		for(Actor actor:getItems())
			actor.fire(event);
		return this;
	}
	
	public Texture getTexture(){
		Actor actor=getItem();
		if(actor instanceof Image)
			return $.getTexture(((Image)actor).getDrawable());
		return null;
	}
	
	public String getText() {
		Actor actor = getItem();
		return actor instanceof Label ? ((Label) actor).getText().toString() : (actor.getName()==null?actor.toString(): actor.getName());
	}
	
	public GdxQuery setText(String txt) {
		for (Actor actor : getItems()) 
			if (actor instanceof Label)
				((Label) actor).setText(txt);
		return this;
	}
	
	public GdxQuery add(Object... a) {
		for (Object obj: a)
			if(obj instanceof Actor)
				getItems().add((Actor)obj);
			else if(obj instanceof Cell<?>)
				getItems().add(((Cell<?>)obj).getActor());
//			else if(obj instanceof ButtonGroup)
//				for(Button button:((ButtonGroup)obj).getButtons())
//					getItems().add(button);
			else if(obj instanceof Stage)
				for(Actor actor:((Stage)obj).getActors())
					getItems().add(actor);
			else if(obj instanceof GdxQuery)
				getItems().addAll(((GdxQuery)obj).getItems());
			else if(obj instanceof Collection)
				for(Object col:(Collection<?>)obj)
					add(col);
		return this;
	}
	
	private GdxQuery setFather(GdxQuery query){
		this.father=query;
		return this;
	}
	
	public GdxQuery and(Object... a){
		return add(a);
	}
	
	public GdxQuery not( Class<?>... cls){
		GdxQuery query=$.add();
		for(Class<?> c:cls)
			for(Actor actor:getItems())
				if(!(actor.getClass().equals(c) || actor.getClass().getSuperclass().equals(c)))
					query.add(actor);
		return query;
	}
	
	public GdxQuery not(Object userObject){
		for(Actor actor:getItems())
			if(!(actor.getUserObject()!=null && actor.getUserObject().equals(userObject)))
				return $.add(actor);
		return $.add();
	}
	
	public boolean equals(Actor a){
		return getItem().equals(a);
	}
	
	public boolean equalClassType(Actor a){
		return equalClassType(a.getClass());
	}

	private boolean equalClassType(Class<? extends Actor> class1) {
		return class1.equals(getItem());
	}

	public List<Actor> getItems() {
		return values;
	}
	
	public GdxQuery appendTo(Object... object){
		for(Object o:object){
			if(o instanceof Stage)
				for(Actor a:getItems())
					((Stage)o).addActor(a);
			else if(o instanceof ScrollPane)
					((ScrollPane)o).setWidget(getItem());
			else if(o instanceof Table)
				for(Actor a:getItems())
					((Table)o).add(a).fill().prefSize(a.getWidth(),a.getHeight()).row();
			else if(o instanceof Group)
				for(Actor a:getItems())
					((Group)o).addActor(a);
			
		}
		return this;
	}
	
	public GdxQuery setBackground(Drawable draw){
		for(Actor a:getItems())
			if(a instanceof Table)
				((Table)a).setBackground(draw);
		return this;
	}
	
	public GdxQuery children(){
		GdxQuery query=new GdxQuery();
		for(Actor actor:getItems()){
			if(actor instanceof Group)
				query.add((Object[])((Group)actor).getChildren().items);
			if(actor instanceof com.badlogic.gdx.scenes.scene2d.ui.List<?>)
				query.add(((com.badlogic.gdx.scenes.scene2d.ui.List<?>)actor).getItems());
			if(actor instanceof SelectBox<?>)
				query.add(((SelectBox<?>)actor).getItems());
			if(actor instanceof Table){
				query.add(((Table)actor).getCells());
				query.add(((Table)actor).getChildren());
			}
		}
		return query.setFather(this);
	}
	
	public GdxQuery setTouchable(Touchable able){
		for(Actor actor:getItems())
			actor.setTouchable(able);
		return this;
	}

	public Actor getItem() {
		if(isEmpty())
			return new NullActor();
		return values.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getItem(Class<T> c){
		return (T)getItem();
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public int length() {
		return values.size();
	}

	public List<Actor> clone() {
		List<Actor> copy = new LinkedList<Actor>();
		copy.addAll(getItems());
		return copy;
	}
	
	private GdxQuery tryRegListener() {
		for(Actor actor:getItems())
			if(!actor.getListeners().contains(clickListener, true))
				actor.addListener(clickListener);
		return this;
	}
	
	public GdxQuery onClick(Runnable run){
		this.click=run;
		return tryRegListener();
	}
	
	public GdxQuery click(){
		if(click!=null)
		click.run();
		return this;
	}
	
	public GdxQuery onTouchUp(Runnable run){
		this.touchUp=run;
		return tryRegListener();
	}
	
	public GdxQuery touchUp(){
		touchUp.run();
		return this;
	}
	
	public GdxQuery onTouchDown(Runnable run){
		this.touchDown=run;
		return tryRegListener();
	}
	
	public GdxQuery touchDown(){
		touchDown.run();
		return this;
	}
	
	public GdxQuery click(boolean sure){
		if(sure)
			click.run();
		return this;
	}
	
	public Cell<?> getCell(){
		try {
			if(getItem() instanceof Table)
				return ((Table)getItem()).getCells().get(0);
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public GdxQuery setChecked(boolean b) {
		for(Actor actor:getItems())
			if(actor instanceof Button)
				((Button)actor).setChecked(b);
		return this;
	}

	public GdxQuery setDisabled(boolean b) {
		for(Actor actor:getItems())
			if(actor instanceof Button)
				((Button)actor).setDisabled(b);
		return this;
	}
	
	public boolean isChecked(){
		if(getItem() instanceof Button)
			return ((Button)getItem()).isChecked();
		return false;
	}

}