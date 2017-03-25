package com.rpsg.gdxQuery;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

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
public class TypedGdxQuery<T extends Actor> extends GdxQuery{
	private T t;
	
	private CustomRunnable<T> clickRun,touchUpRun,touchDownRun,overRun,leaveRun,moveRun;
	
	private boolean isOver = false;
	
	private InputListener _clickListener;
	
	public TypedGdxQuery(){};
	
	public TypedGdxQuery(T t) {
		this.t = t;
	}
	
	public TypedGdxQuery<T> toggle(){
		return visible(!visible());
	}
	
	public TypedGdxQuery<T> origin (int alignment){
		t.setOrigin(alignment);
		return this;
	}
	
	public TypedGdxQuery<T> origin (int x, int y){
		t.setOrigin(x, y);
		return this;
	}
	
	public TypedGdxQuery<T> transform(boolean flag){
		if(t instanceof Group)
			((Group) t).setTransform(flag);
		return this;
	}
	
	public Stage stage(){
		return t.getStage();
	}
	
	public TypedGdxQuery<T> remove(){
		t.remove();
		return this;
	}
	
	public TypedGdxQuery<T> size(float width, float height){
		t.setSize((int)width, (int)height);
		return this;
	}

	public TypedGdxQuery<T> invalidate(){
		if(t instanceof Widget)
			((Widget) t).invalidate();
		return this;
	}

	public TypedGdxQuery<T> layout(){
		if(t instanceof Widget)
			((Widget) t).layout();
		return this;
	}

	public TypedGdxQuery<T> scale(float scaleXY){
		t.setScale(scaleXY);
		return this;
	}

	public TypedGdxQuery<T> scaleX(float scaleX){
		t.setScaleX(scaleX);
		return this;
	}

	public TypedGdxQuery<T> scaleY(float scaleY){
		t.setScaleY(scaleY);
		return this;
	}

	public TypedGdxQuery<T> y(float y){
		t.setY((int)y);
		return this;
	}

	public boolean isTransparent() {
		return t.getColor().a != 0;
	}

	public TypedGdxQuery<T> x(float x){
		t.setX((int)x);
		return this;
	}

	public float width(){
		return t.getWidth();
	}

	public float height(){
		return t.getHeight();
	}

	public TypedGdxQuery<T> width(float width){
		t.setWidth((int)width);
		return this;
	}

	public TypedGdxQuery<T> height(float height){
		t.setHeight((int)height);
		return this;
	}

	public float x(){
		return t.getX();
	}

	public float y(){
		return t.getY();
	}

	public Vector2 position(){
		return new Vector2(t.getX(),t.getY());
	}

	public TypedGdxQuery<T> position(float x, float y){
		t.setPosition((int)x, (int)y);
		return this;
	}

	public TypedGdxQuery<T> color(Color color){
		t.setColor(color);
		return this;
	}

	public TypedGdxQuery<T> placeHolder(String ph){
		if(t instanceof TextField)
			((TextField)t).setMessageText(ph);
		return this;
	}

	public TypedGdxQuery<T> alpha(float a){
		t.getColor().a=a;
		return this;
	}

	public TypedGdxQuery<T> a(float a){
		return alpha(a);
	}

	public TypedGdxQuery<T> color(float r, float g, float b, float a){
		t.setColor(r,g,b,a);
		return this;
	}

	public TypedGdxQuery<T> color(float r, float g, float b){
		return color(r,g,b,1);
	}

	public TypedGdxQuery<T> fadeOut(float time){
		return colorTo(new Color(1,1,1,0), time);
	}

	public TypedGdxQuery<T> fadeOut(){
		return alpha(0);
	}

	public TypedGdxQuery<T> fadeIn(){
		return alpha(1);
	}

	public TypedGdxQuery<T> fadeIn(float time) {
		return colorTo(Color.WHITE, time);
	}

	public TypedGdxQuery<T> colorTo(Color color, float duration){
		return action(Actions.color(color, duration));
	}

	public TypedGdxQuery<T> fadeTo(float alpha, float duration){
		return action(Actions.alpha(alpha, duration));
	}

	public TypedGdxQuery<T> visible(boolean v){
		t.setVisible(v);
		return this;
	}
	
	public boolean visible(){
		return t.isVisible();
	}

	public TypedGdxQuery<T> hide(){
		t.setVisible(false);
		return this;
	}

	public TypedGdxQuery<T> show(){
		t.setVisible(true);
		return this;
	}

	public TypedGdxQuery<T> action(Action... action){
		for(Action act:action)
			t.addAction(act);
		return this;
	}

	public TypedGdxQuery<T> userObject(Object object){
		t.setUserObject(object);
		return this;
	}

	public TypedGdxQuery<T> align(int align){
		if(t instanceof Label){
			((Label)t).setAlignment(align);
		}
		return this;
	}

	public Object userObject(){
		return t.getUserObject();
	}

	public TypedGdxQuery<T> cleanActions(){
		t.clearActions();
		return this;
	}

	public TypedGdxQuery<T> stopActions(){
		while(t.hasActions())
			t.act(Gdx.graphics.getDeltaTime());
		return this;
	}

	public TypedGdxQuery<T> listener(EventListener... listener){
		for(EventListener l:listener)
			t.addListener(l);
		return this;
	}

	public TypedGdxQuery<T> cleanListeners(){
		t.clearListeners();
		return this;
	}

	public TypedGdxQuery<T> draw(Batch batch, float pf){
		t.draw(batch,pf);
		return this;
	}

	public TypedGdxQuery<T> draw(Batch batch){
		return draw(batch,1);
	}

	public TypedGdxQuery<T> act(float time){
		t.act(time);
		return this;
	}

	public TypedGdxQuery<T> act(){
		return act(Gdx.graphics.getDeltaTime());
	}

	public TypedGdxQuery<T> debug(){
		t.debug();
		return this;
	}

	public TypedGdxQuery<T> debug(boolean debug){
		t.setDebug(debug);
		return this;
	}

	public TypedGdxQuery<T> pack(){
		if(t instanceof Widget)
			((Widget)t).pack();
		return this;
	}

	public TypedGdxQuery<T> fire(Event event){
		t.fire(event);
		return this;
	}

	public Texture texture(){
		Actor actor=t;
		if(actor instanceof Image)
			return $.getTexture(((Image)actor).getDrawable());
		return null;
	}

	public String text() {
		Actor actor = t;
		return actor instanceof Label ? ((Label) actor).getText().toString() : (actor.getName()==null?actor.toString(): actor.getName());
	}

	public TypedGdxQuery<T> text(String txt) {
		if (t instanceof Label)
			((Label) t).setText(txt);
		return this;
	}

	public boolean equals(Actor a){
		return t.equals(a);
	}

	@SuppressWarnings("unchecked")
	public TypedGdxQuery<T> to(Object... object){
		for(Object o:object){
			if(o instanceof Stage)
				((Stage)o).addActor(t);
			else if(o instanceof ScrollPane)
					((ScrollPane)o).setWidget(t);
			else if(o instanceof Table)
				((Table)o).add(t).fill().prefSize(t.getWidth(), t.getHeight()).row();
			else if(o instanceof Group || o instanceof WidgetGroup)
				((Group)o).addActor(t);
			else if (o instanceof TypedGdxQuery)
				((TypedGdxQuery<Group>)o).get().addActor(t);
			else if (o instanceof GdxQuery)
				to(((GdxQuery)o).get());
		}
		return this;
	}

	public TypedGdxQuery<T> row(){
		if(t instanceof Table)
			((Table) t).row();
		return this;
	}

	public TypedGdxQuery<T> into(Object... object){
		for(Object o : object){
			if(t instanceof Table)
				((Table) t).add($.add(o).get());
		}
		return this;
	}

	public TypedGdxQuery<T> background(Drawable draw){
		if(t instanceof Table)
			((Table)t).setBackground(draw);
		return this;
	}

	public TypedGdxQuery<T> touchable(Touchable able){
		t.setTouchable(able);
		return this;
	}

	public T get() {
		return t;
	}

	private TypedGdxQuery<T> _tryRegListener() {
		if(!t.getListeners().contains(clickListener(), true))
		t.addListener(clickListener());
		return this;
	}

	private InputListener clickListener() {
		return _clickListener != null ? _clickListener : (_clickListener = new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				if(b != Buttons.LEFT)
					return;
				if(clickRun!=null && x>=0 && y>=0 && x <= event.getListenerActor().getWidth() && y <= event.getListenerActor().getHeight())
					clickRun.run(t);
				if(touchUpRun!=null) touchUpRun.run(t);
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				if(touchDownRun!=null) touchDownRun.run(t);
				return true;
			}
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if(!isOver){
					if(overRun != null) overRun.run(t);
					isOver = true;
				}

			};
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if(isOver){
					if(leaveRun != null) leaveRun.run(t);
					isOver = false;
				}
			};
			public boolean mouseMoved(InputEvent event, float x, float y) {
				return super.mouseMoved(event, x, y);
			};

		});
	}

	public TypedGdxQuery<T> click(Runnable run){
		this.clickRun= e-> run.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> click(){
		if(clickRun!=null)
			clickRun.run(t);
		return this;
	}

	public TypedGdxQuery<T> touchUp(Runnable run){
		this.touchUpRun=e->run.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> touchUp(){
		touchUpRun.run(null);
		return this;
	}

	public TypedGdxQuery<T> touchDown(Runnable run){
		this.touchDownRun=e->run.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> touchDown(){
		touchDownRun.run(null);
		return this;
	}

	public TypedGdxQuery<T> click(boolean sure){
		if(sure)
			clickRun.run(null);
		return this;
	}

	public Cell<?> cell(){
		try {
			if(t instanceof Table){
				return ((Table)t).getCells().get(0);
			}else{
				Actor parent = t.getParent();
				if(parent instanceof Table)
					return ((Table) parent).getCell(t);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public List<Cell<? extends Actor>> cells(){
		List<Cell<? extends Actor>> list = new ArrayList<>();
		if(t instanceof Table)
			for(Cell<?> cell : ((Table)t).getCells())
				list.add(cell);
		return list;
	}

	public TypedGdxQuery<T> eachCells(CustomRunnable<GdxCellQuery<?, ?>> run){
		for(Cell<? extends Actor> cell : cells())
			run.run(GdxCellQuery.build(this, cell));
		return this;
	}

	public TypedGdxQuery<T> checked(boolean b) {
		if(t instanceof Button)
			((Button)t).setChecked(b);
		return this;
	}

	public TypedGdxQuery<T> disabled(boolean b) {
		if(t instanceof Button)
			((Button)t).setDisabled(b);
		return this;
	}

	public TypedGdxQuery<T> disableTouch() {
		t.setTouchable(null);
		return this;
	}

	public boolean checked(){
		if(t instanceof Button)
			return ((Button)t).isChecked();
		return false;
	}

	public TypedGdxQuery<T> zIndex(int i) {
		t.setZIndex(i);
		return this;
	}

	@Override
	public String toString() {
		return "GDX-Query(typed):"+t;
	}

	public TypedGdxQuery<T> hover(Runnable over, Runnable leave) {
		this.overRun = e->over.run();
		this.leaveRun = e->leave.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> enter(Runnable over){
		this.overRun = e->over.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> over(Runnable over){
		return enter(over);
	}

	public TypedGdxQuery<T> exit(Runnable leave){
		this.leaveRun = e->leave.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> leave(Runnable leave){
		return exit(leave);
	}

	public TypedGdxQuery<T> fillParent(boolean flag) {
		if(t instanceof Widget)
			((Widget) t).setFillParent(flag);
		return this;
	}

	public TypedGdxQuery<T> mouseMoved(Runnable run){
		this.moveRun = e->run.run();
		return _tryRegListener();
	}

	public TypedGdxQuery<T> mouseMoved(){
		if(moveRun != null) moveRun.run(null);
		return this;
	}

	public TypedGdxQuery<T> fillParent(){
		return fillParent(true);
	}

	/**maybe return null*/
	public <TT extends Actor> GdxCellQuery<TT, GdxQuery> cell(TT append) {
		if(t instanceof Table)
			return GdxCellQuery.build(this, ((Table)t).add(append));
		return null;
	}

	public TypedGdxQuery<T> scrollY(float top) {
		if(t instanceof ScrollPane)
			((ScrollPane) t).setScrollY(top);
		return this;
	}

	public TypedGdxQuery<T> color(String string) {
		t.setColor(Color.valueOf(string));
		return this;
	}

	public TypedGdxQuery<T> colorTo(String string, float d) {
		return colorTo(Color.valueOf(string), d);
	}

	public TypedGdxQuery<T> center(boolean setPosition) {
		t.setOrigin(Align.center);
		if(t instanceof Image)
			((Image)t).setAlign(Align.center);
		if(t instanceof Label)
			((Label) t).setAlignment(Align.center);

		if(setPosition) {
			t.setX(t.getX() - t.getWidth() / 2);
			t.setY(t.getY() - t.getHeight() / 2);
		}
		return this;
	}

	public TypedGdxQuery<T> right() {
		if(t instanceof Label)
			((Label)t).setAlignment(Align.right);
		return this;
	}

	public TypedGdxQuery<T> center(){
		return center(false);
	}

	public TypedGdxQuery<T> sizeBy(int i, int j) {
		t.sizeBy(i, j);
		return this;
	}

	public TypedGdxQuery<T> sizeBy(int i) {
		t.sizeBy(i);
		return this;
	}

	public float scale() {
		return t.getScaleX();
	}

	public TypedGdxQuery<T> filter(TextureFilter filter) {
		if(t instanceof Image)
			((TextureRegionDrawable)((Image) t).getDrawable()).getRegion().getTexture().setFilter(filter, filter);
		return this;
	}

	public TypedGdxQuery<T> nearest() {
		return filter(TextureFilter.Nearest);
	}

	public TypedGdxQuery<T> rotate(float f) {
		t.setRotation(f);
		return this;
	}
	
}