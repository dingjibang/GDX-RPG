package com.rpsg.gdxQuery;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GdxQuery {

	private List<Actor> values = new LinkedList<Actor>();
	private Runnable click;
	
	InputListener clickListener=(new InputListener(){
		public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
			if(click!=null)
				click.run();
		}
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
			return true;
		}
	});

	public GdxQuery(Object... a) {
			add(a);
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
	
	public GdxQuery find(Class<? extends Actor>... cls){
		GdxQuery query=$.add();
		for(Class<? extends Actor> c:cls)
			for(Actor actor:getItems())
				if(actor.getClass().equals(c) || actor.getClass().getSuperclass().equals(c))
					query.add(actor);
		return query;
	}
	
	public GdxQuery remove(Object... o){
		for(Object obj:o){
			$.add(obj).getItem().remove();
			not(obj);
		}
		return this;
	}
	
	public GdxQuery setSize(float width,float height){
		for(Actor actor:getItems())
			actor.setSize(width, height);
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
	
	public float getWidth(){
		return getItem().getWidth();
	}
	
	public float getHeight(){
		return getItem().getHeight();
	}
	
	public GdxQuery setWidth(float width){
		getItem().setWidth(width);
		return this;
	}
	
	public GdxQuery setHeight(float height){
		getItem().setHeight(height);
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
	
	public GdxQuery addAction(Action... action){
		for(Actor actor:getItems())
			for(Action act:action)
				actor.addAction(act);
		return this;
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
	
	public String text() {
		Actor actor = getItem();
		return actor instanceof Label ? ((Label) actor).getText().toString() : (actor.getName()==null?actor.toString(): actor.getName());
	}
	
	public GdxQuery text(String txt) {
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
			else if(obj instanceof ButtonGroup)
				for(Button button:((ButtonGroup)obj).getButtons())
					getItems().add(button);
			else if(obj instanceof Stage)
				getItems().addAll((Collection<? extends Actor>) ((Stage)obj).getActors());
			else if(obj instanceof GdxQuery)
				getItems().addAll(((GdxQuery)obj).getItems());
		return this;
	}
	
	public GdxQuery and(Object... a){
		return add(a);
	}
	
	public GdxQuery not(Object... a){
		for(Object o:a)
			getItems().remove($.add(o).getItem());
		return this;
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
			if(o instanceof Group)
				for(Actor a:getItems())
					((Group)o).addActor(a);
			if(o instanceof Table)
				for(Actor a:getItems())
					((Table)o).add(a).row();
		}
		return this;
	}
	
	public GdxQuery children(){
		GdxQuery query=new GdxQuery();
		for(Actor actor:getItems()){
			if(actor instanceof Group)
				query.add(((Group)actor).getChildren().items);
			if(actor instanceof com.badlogic.gdx.scenes.scene2d.ui.List<?>)
				query.add(((com.badlogic.gdx.scenes.scene2d.ui.List<?>)actor).getItems());
			if(actor instanceof SelectBox<?>)
				query.add(((SelectBox<?>)actor).getItems());
			if(actor instanceof Table){
				query.add(((Table)actor).getCells());
				query.add(((Table)actor).getChildren());
			}
		}
		return query;
	}

	public Actor getItem() {
		if(isEmpty())
			return new NullActor();
		return values.get(0);
	}

	private boolean isEmpty() {
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
	
	public GdxQuery onClick(Runnable run){
		this.click=run;
		for(Actor actor:getItems()){
			if(!actor.getListeners().contains(clickListener, true))
				actor.addListener(clickListener);
		}
		return this;
	}
	
	public GdxQuery click(){
		click.run();
		return this;
	}

	
	//----------

}
