package com.rpsg.gdxQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
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

	private ArrayList<Actor> values = new ArrayList<Actor>();
	
	private CustomRunnable<Actor> click,touchUp,touchDown,over,leave,move;
	
	private boolean isOver = false;
	private GdxQuery father;
	
	private InputListener clickListener=(new InputListener(){
		public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
			if(b != Buttons.LEFT)
				return;
			if(click!=null && x>=0 && y>=0 && x <= event.getListenerActor().getWidth() && y <= event.getListenerActor().getHeight()) 
				click.run(event.getListenerActor());
			if(touchUp!=null) touchUp.run(event.getListenerActor());
		}
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
			if(touchDown!=null) touchDown.run(event.getListenerActor());
			return true;
		}
		public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
			if(!isOver){
				if(over != null) over.run(event.getListenerActor());
				isOver = true;
			}
			
		};
		public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
			if(isOver){
				if(leave != null) leave.run(event.getListenerActor());
				isOver = false;
			}
		};
		public boolean mouseMoved(InputEvent event, float x, float y) {
			return super.mouseMoved(event, x, y);
		};
		
	});

	public GdxQuery(Object... a) {
		add(a);
	}
	
	private static void copyListener(GdxQuery parent,GdxQuery child){
		child.click = parent.click;
		child.touchUp = parent.touchUp;
		child.touchDown = parent.touchDown;
		child.over = parent.over;
		child.leave = parent.leave;
		child.move = parent.move;
	}
	
	private GdxQuery copyListener(){
		if(father() != null)
			copyListener(father(), this);
		return this;
	}
	
	public GdxQuery first(){
		return $.add(values.size()==0?new NullActor():values.get(0)).father(father==null?this:father);
	}
	
	public GdxQuery last(){
		return $.add(values.size()==0?new NullActor():values.get(values.size()-1)).father(father==null?this:father);
	}
	
	public GdxQuery father(){
		return father;
	}
	
	public GdxQuery next(){
		if(father==null)
			return $.add();
		try {
			return $.add(father.list().get(father.list().indexOf(get())+1)).father(father);
		} catch (Exception e) {
			return $.add();
		}
	}
	
	public GdxQuery prev(){
		if(father==null)
			return $.add();
		try {
			return $.add(father.list().get(father.list().indexOf(get())-1)).father(father);
		} catch (Exception e) {
			return $.add();
		}
	}

	public GdxQuery origin (int alignment){
		for(Actor actor:list())
			actor.setOrigin(alignment);
		return this;
	}
	
	public GdxQuery parent(){
		if(empty())
			return $.add();
		return $.add(get().getParent());
	}
	
	public GdxQuery transform(boolean flag){
		for(Actor actor : list())
			if(actor instanceof Group)
				((Group) actor).setTransform(flag);
		return this;
	}
	
	public Stage stage(){
		if(empty())
			return null;
		return get().getStage();
	}
	
	public Actor item(int index){
		if(index>=list().size())
			return null;
		else
			return list().get(index);
	}
	
	public GdxQuery eq(int index){
		return new GdxQuery(item(index));
	}
	
	public GdxQuery find(Class<?>... cls){
		GdxQuery query=$.add();
		for(Class<?> c:cls)
			if(c != null)
				for(Actor actor:list())
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
		return (T)get();
	}
	
	public GdxQuery find(Object userObject){
		if(userObject instanceof Class)
			return find((Class<?>)userObject,null);
		
		GdxQuery result = $.add().father(this);
		
		for(Actor actor:list())
			if(actor.getUserObject()!=null && actor.getUserObject().equals(userObject))
				result.add(actor);
		
		return result;
	}
	
	public GdxQuery findUserObjectInstanceOf(Class<?>... clz){
		GdxQuery result = $.add().father(this);
		
		for(Class<?> c:clz)
			for(Actor actor:list())
				if(actor.getUserObject()!=null && (actor.getUserObject().getClass().equals(c) || actor.getUserObject().getClass().getSuperclass().equals(c)))
					result.add(actor);
		
		return result;
	}
	
	public GdxQuery remove(Object... o){
		for(Object obj:o){
			$.add(obj).get().remove();
			notUserObject(obj);
		}
		return this;
	}
	
	public GdxQuery removeAll(){
		for(Actor actor:list())
			actor.remove();
		return this;
	}
	
	public GdxQuery size(float width,float height){
		for(Actor actor:list())
			actor.setSize(width, height);
		return this;
	}
	
	public GdxQuery invalidate(){
		for(Actor actor:list())
			if(actor instanceof Widget)
				((Widget) actor).invalidate();
		return this;
	}
	
	public GdxQuery scale(float scaleXY){
		for(Actor actor:list())
			actor.setScale(scaleXY);
		return this;
	}
	
	public GdxQuery scaleX(float scaleX){
		for(Actor actor:list())
			actor.setScaleX(scaleX);
		return this;
	}
	
	public GdxQuery scaleY(float scaleY){
		for(Actor actor:list())
			actor.setScaleY(scaleY);
		return this;
	}

	public GdxQuery y(float y){
		for(Actor actor:list())
			actor.setY(y);
		return this;
	}

	public GdxQuery x(float x){
		for(Actor actor:list())
			actor.setX(x);
		return this;
	}
	
	public float width(){
		return get().getWidth();
	}
	
	public float height(){
		return get().getHeight();
	}
	
	public GdxQuery width(float width){
		for(Actor actor:list())
			actor.setWidth(width);
		return this;
	}
	
	public GdxQuery height(float height){
		for(Actor actor:list())
			actor.setHeight(height);
		return this;
	}
	
	public float x(){
		return get().getX();
	}
	
	public float y(){
		return get().getY();
	}
	
	public Vector2 position(){
		return new Vector2(get().getX(),get().getY());
	}
	
	public GdxQuery position(float x, float y){
		for(Actor actor:list())
			actor.setPosition(x, y);
		return this;
	}
	
	public GdxQuery color(Color color){
		for(Actor actor:list())
			actor.setColor(color);
		return this;
	}
	
	public GdxQuery placeHolder(String ph){
		for(Actor actor:list())
			if(actor instanceof TextField)
				((TextField)actor).setMessageText(ph);
		return this;
	}
	
	public GdxQuery alpha(float a){
		for(Actor actor:list())
			actor.getColor().a=a;
		return this;
	}
	
	public GdxQuery color(float r,float g,float b,float a){
		for(Actor actor:list())
			actor.setColor(r,g,b,a);
		return this;
	}
	
	public GdxQuery color(float r,float g,float b){
		return color(r,g,b,1);
	}
	
	public GdxQuery fadeOut(float time){
		return colorTo(new Color(1,1,1,0), time);
	}
	
	public GdxQuery fadeOut(){
		return alpha(0);
	}
	
	public GdxQuery fadeIn(){
		return alpha(1);
	}
	
	public GdxQuery fadeIn(float time) {
		return colorTo(Color.WHITE, time);
	}

	public GdxQuery colorTo(Color color,float duration){
		return action(Actions.color(color, duration));
	}
	
	public GdxQuery visible(boolean v){
		for(Actor actor:list())
			actor.setVisible(v);
		return this;
	}
	
	public GdxQuery hide(){
		for(Actor actor:list())
			actor.setVisible(false);
		return this;
	}
	
	public GdxQuery show(){
		for(Actor actor:list())
			actor.setVisible(true);
		return this;
	}
	
	public GdxQuery run (GdxQueryRunnable run){
		if(run!=null)
			run.run(this);
		return this;
	}
	
	
	public GdxQuery each(CustomRunnable<Actor> run){
		if(run!=null)
			for(Actor actor:(List<? extends Actor>)list())
				run.run(actor);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> GdxQuery each(Class<T> clz,CustomRunnable<T> run){
		if(run!=null)
			for(Actor actor:(List<? extends Actor>)list())
				run.run((T)actor);
		return this;
	}
	
	public GdxQuery action(Action... action){
//		FIXME this method is not work!
		for(Actor actor:list())
			for(Action act:action)
				actor.addAction(act);
		return this;
	}
	
	public GdxQuery userObject(Object object){
		for(Actor actor:list())
			actor.setUserObject(object);
		return this;
	}
	
	public GdxQuery align(int align){
		for(Actor actor:list()){
			if(actor instanceof Label){
				((Label)actor).setAlignment(align);
			}
		}
		return this;
	}
	
	public Object userObject(){
		return get().getUserObject();
	}
	
	public GdxQuery cleanActions(){
		for(Actor actor:list())
			actor.clearActions();
		return this;
	}
	
	public GdxQuery listener(EventListener... listener){
		for(Actor actor:list())
			for(EventListener l:listener)
				actor.addListener(l);
		return this;
	}
	
	public GdxQuery cleanListeners(){
		for(Actor actor:list())
			actor.clearListeners();
		return this;
	}
	
	public GdxQuery draw(Batch batch,float pf){
		for(Actor actor:list())
			actor.draw(batch,pf);
		return this;
	}
	
	public GdxQuery draw(Batch batch){
		return draw(batch,1);
	}
	
	public GdxQuery act(float time){
		for(Actor actor:list())
			actor.act(time);
		return this;
	}
	
	public GdxQuery act(){
		return act(Gdx.graphics.getDeltaTime());
	}
	
	public GdxQuery debug(){
		for(Actor actor:list())
			actor.debug();
		return this;
	}
	
	public GdxQuery debug(boolean debug){
		for(Actor actor:list())
			actor.setDebug(debug);
		return this;
	}
	
	public GdxQuery pack(){
		for(Actor actor:list())
			if(actor instanceof Widget)
				((Widget)actor).pack();
		return this;
	}
	
	public GdxQuery fire(Event event){
		for(Actor actor:list())
			actor.fire(event);
		return this;
	}
	
	public Texture texture(){
		Actor actor=get();
		if(actor instanceof Image)
			return $.getTexture(((Image)actor).getDrawable());
		return null;
	}
	
	public String text() {
		Actor actor = get();
		return actor instanceof Label ? ((Label) actor).getText().toString() : (actor.getName()==null?actor.toString(): actor.getName());
	}
	
	public GdxQuery text(String txt) {
		for (Actor actor : list()) 
			if (actor instanceof Label)
				((Label) actor).setText(txt);
		return this;
	}
	
	public GdxQuery add(Object... a) {
		for (Object obj: a)
			if(obj instanceof Actor)
				list().add((Actor)obj);
			else if(obj instanceof Cell<?>)
				list().add(((Cell<?>)obj).getActor());
//			else if(obj instanceof ButtonGroup)
//				for(Button button:((ButtonGroup)obj).getButtons())
//					getItems().add(button);
			else if(obj instanceof Stage)
				for(Actor actor:((Stage)obj).getActors())
					list().add(actor);
			else if(obj instanceof GdxQuery)
				list().addAll(((GdxQuery)obj).list());
			else if(obj instanceof Collection)
				for(Object col:(Collection<?>)obj)
					add(col);
			else if(obj instanceof Array)
				for(Object col:(Array<?>)obj)
					add(col);
		return this;
	}
	
	private GdxQuery father(GdxQuery query){
		this.father=query;
		return copyListener();
	}
	
	public GdxQuery and(Object... a){
		return add(a);
	}
	
	public GdxQuery not( Class<?>... cls){
		GdxQuery query=$.add();
		for(Class<?> c:cls)
			for(Actor actor:list())
				if(!(actor.getClass().equals(c) || actor.getClass().getSuperclass().equals(c)))
					query.add(actor);
		return query;
	}
	
	public GdxQuery notUserObject(Object userObject){
		for(Actor actor:list())
			if(!(actor.getUserObject()!=null && actor.getUserObject().equals(userObject)))
				return $.add(actor);
		return $.add();
	}
	
	public GdxQuery not(Actor... actors){
		GdxQuery query=$.add();
		for(Actor a :actors)
			for(Actor actor:list())
				if(actor != a)
					query.add(actor);
		return query.father(this);
	}
	
	public boolean equals(Actor a){
		return get().equals(a);
	}
	
	public boolean equalClassType(Actor a){
		return equalClassType(a.getClass());
	}

	private boolean equalClassType(Class<? extends Actor> class1) {
		return class1.equals(get());
	}

	public List<Actor> list() {
		return values;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> mapToList(CustomCallback<Actor,Object> each,Class<T> cls){
		List<Object> list = new ArrayList<>();
		for(Actor actor : list())
			list.add(each.run(actor));
		return (ArrayList<T>)list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> list(Class<T> t) {
		return (List<T>)values;
	}
	
	public GdxQuery appendTo(Object... object){
		for(Object o:object){
			if(o instanceof Stage)
				for(Actor a:list())
					((Stage)o).addActor(a);
			else if(o instanceof ScrollPane)
					((ScrollPane)o).setWidget(get());
			else if(o instanceof Table)
				for(Actor a:list())
					((Table)o).add(a).fill().prefSize(a.getWidth(),a.getHeight()).row();
			else if(o instanceof Group || o instanceof WidgetGroup)
				for(Actor a:list())
					((Group)o).addActor(a);
			else if (o instanceof GdxQuery)
				for(Actor a:list())
					((GdxQuery)o).getItem(Group.class).addActor(a);
		}
		return this;
	}
	
	public GdxQuery row(){
		for(Actor a : list())
			if(a instanceof Table)
				((Table) a).row();
		return this;
	}
	
	public GdxQuery into(Object... object){
		for(Object o : object){
			for(Actor a : list())
				if(a instanceof Table)
					((Table) a).add($.add(o).get());
		}
		return this;
	}
	
	public GdxQuery background(Drawable draw){
		for(Actor a:list())
			if(a instanceof Table)
				((Table)a).setBackground(draw);
		return this;
	}
	
	public GdxQuery children(Class<?> _class){
		return children().find(_class);
	}
	
	public GdxQuery children(){
		GdxQuery query=new GdxQuery();
		for(Actor actor:list()){
			if(actor instanceof Group)
				query.add((Object[])((Group)actor).getChildren().toArray());
			else if(actor instanceof com.badlogic.gdx.scenes.scene2d.ui.List<?>)
				query.add(((com.badlogic.gdx.scenes.scene2d.ui.List<?>)actor).getItems());
			else if(actor instanceof SelectBox<?>)
				query.add(((SelectBox<?>)actor).getItems());
			else if(actor instanceof Table){
				query.add(((Table)actor).getChildren());
			}
		}
		return query.father(this);
	}
	
	public GdxQuery children(boolean deep){
		GdxQuery $q = new GdxQuery();
		for(Actor actor:list()){
//			$q.add(actor);
			
			GdxQuery child = new GdxQuery(actor).children();
			$q.add(child);
			
			if(!child.empty())
			for(Actor a:child.list()){
				GdxQuery _q = new GdxQuery(a);
				GdxQuery _qc = _q.children(true);
				$q.add(_qc.empty() ? _q : _qc);
			}
		}
		return $q.father(this);
	}
	
	public GdxQuery touchable(Touchable able){
		for(Actor actor:list())
			actor.setTouchable(able);
		return this;
	}

	public Actor get() {
		if(empty())
			return new NullActor();
		return values.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getItem(Class<T> c){
		return (T)get();
	}

	public boolean empty() {
		return values.isEmpty();
	}

	public int length() {
		return values.size();
	}

	public List<Actor> clone() {
		List<Actor> copy = new LinkedList<Actor>();
		copy.addAll(list());
		return copy;
	}
	
	private GdxQuery _tryRegListener() {
		for(Actor actor:list())
			if(!actor.getListeners().contains(clickListener, true))
				actor.addListener(clickListener);
		return this;
	}
	
	public GdxQuery click(Runnable run){
		this.click= e-> run.run();
		return _tryRegListener();
	}
	
	@SuppressWarnings("unchecked")
	public GdxQuery click(CustomRunnable<? extends Actor> run){
		this.click= (CustomRunnable<Actor>) run;
		return _tryRegListener();
	}
	
	public GdxQuery click(){
		if(click!=null)
			click.run(get());
		return this;
	}
	
	public GdxQuery touchUp(Runnable run){
		this.touchUp=e->run.run();
		return _tryRegListener();
	}
	
	public GdxQuery touchUp(){
		touchUp.run(null);
		return this;
	}
	
	public GdxQuery touchDown(Runnable run){
		this.touchDown=e->run.run();
		return _tryRegListener();
	}
	
	public GdxQuery touchDown(){
		touchDown.run(null);
		return this;
	}
	
	public GdxQuery click(boolean sure){
		if(sure)
			click.run(null);
		return this;
	}
	
	public Cell<?> cell(){
		try {
			if(get() instanceof Table){
				return ((Table)get()).getCells().get(0);
			}else{
				Actor parent = get().getParent();
				if(parent instanceof Table)
					return ((Table) parent).getCell(get());
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	public List<Cell<? extends Actor>> cells(){
		List<Cell<? extends Actor>> list = new ArrayList<>();
		if(get() instanceof Table)
			for(Cell<?> cell : ((Table)get()).getCells())
				list.add(cell);
		return list;
	}
	
	public GdxQuery eachCells(CustomRunnable<GdxCellQuery<?>> run){
		for(Cell<? extends Actor> cell : cells())
			run.run(GdxCellQuery.build(this.children(), cell));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Actor> GdxQuery eachCells(Class<T> type,CustomRunnable<GdxCellQuery<T>> run){
		for(Cell<?> cell : cells())
			run.run(GdxCellQuery.build(this.children(), (Cell<T>)cell));
		return this;
	}

	public GdxQuery checked(boolean b) {
		for(Actor actor:list())
			if(actor instanceof Button)
				((Button)actor).setChecked(b);
		return this;
	}

	public GdxQuery disabled(boolean b) {
		for(Actor actor:list())
			if(actor instanceof Button)
				((Button)actor).setDisabled(b);
		return this;
	}
	
	public boolean checked(){
		if(get() instanceof Button)
			return ((Button)get()).isChecked();
		return false;
	}

	public GdxQuery zIndex(int i) {
		for(Actor actor:list())
			actor.setZIndex(i);
		return this;
	}
	
	@Override
	public String toString() {
		return "GDX-Query:"+list();
	}

	public GdxQuery hover(Runnable over, Runnable leave) {
		this.over = e->over.run();
		this.leave = e->leave.run();
		return _tryRegListener();
	}
	
	public GdxQuery enter(Runnable over){
		this.over = e->over.run();
		return _tryRegListener();
	}
	
	public GdxQuery over(Runnable over){
		return enter(over);
	}
	
	public GdxQuery exit(Runnable leave){
		this.leave = e->leave.run();
		return _tryRegListener();
	}
	
	public GdxQuery leave(Runnable leave){
		return exit(leave);
	}
	
	public GdxQuery fillParent(boolean flag) {
		for(Actor actor : list())
			if(actor instanceof Widget)
				((Widget) actor).setFillParent(flag);
		return this;
	}
	
	public GdxQuery mouseMoved(Runnable run){
		this.move = e->run.run();
		return _tryRegListener();
	}
	
	public GdxQuery mouseMoved(){
		if(move != null) move.run(null);
		return this;
	}
	
	public GdxQuery fillParent(){
		return fillParent(true);
	}

	/**maybe return null*/
	public <T extends Actor> GdxCellQuery<T> cell(T append) {
		for(Actor actor : list())
			if(actor instanceof Table)
				return GdxCellQuery.build(this, ((Table)actor).add(append));
		return null;
	}

	public int indexOf(Actor actor) {
		return list().indexOf(actor);
	}

	public GdxQuery scrollY(float top) {
		for(Actor actor : list())
			if(actor instanceof ScrollPane)
				((ScrollPane) actor).setScrollY(top);
		return this;
	}

	public GdxQuery color(String string) {
		for(Actor actor : list())
			actor.setColor(Color.valueOf(string));
		return this;
	}

	public GdxQuery colorTo(String string, float d) {
		return colorTo(Color.valueOf(string), d);
	}
	
}