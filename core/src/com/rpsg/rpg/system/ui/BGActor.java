package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

public abstract class BGActor extends Actor{
	
	protected Image image=Res.get(Setting.UI_BASE_IMG);
	protected List<Actor> actors=new ArrayList<Actor>();
	
	public BGActor() {
	}
	
	@Override
	public Array<EventListener> getListeners() {
		return image.getListeners();
	}
	@Override
	public void setX(float x) {
		image.setX(x);
		image.setX(x);
	}
	
	@Override
	public void setY(float y) {
		image.setY(y);
		image.setY(y);
	}
	
	@Override
	public void setBounds(float x, float y, float width, float height) {
		image.setBounds(x, y, width, height);
	}
	
	@Override
	public Array<EventListener> getCaptureListeners() {
		return image.getCaptureListeners();
	}
	
	@Override
	public void setTouchable(Touchable touchable) {
		image.setTouchable(touchable);
	}
	
	@Override
	public boolean addCaptureListener(EventListener listener) {
		return image.addCaptureListener(listener);
	}
	
	
	
	@Override
	public void setScale(float scaleX, float scaleY) {
		image.setScale(scaleX, scaleY);
		super.setScale(scaleX, scaleY);
	}
	
	@Override
	public void act(float delta) {
		
		image.act(delta);
	}

	@Override
	public boolean fire(Event event) {
		
		return image.fire(event);
	}

	@Override
	public boolean notify(Event event, boolean capture) {
		
		return image.notify(event, capture);
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		
		return image.hit(x, y, touchable);
	}

	@Override
	public boolean remove() {
		
		return image.remove();
	}

	@Override
	public boolean removeListener(EventListener listener) {
		
		return image.removeListener(listener);
	}

	@Override
	public boolean removeCaptureListener(EventListener listener) {
		
		return image.removeCaptureListener(listener);
	}

	@Override
	public void addAction(Action action) {
		
		image.addAction(action);
	}

	@Override
	public void removeAction(Action action) {
		
		image.removeAction(action);
	}

	@Override
	public Array<Action> getActions() {
		
		return image.getActions();
	}

	@Override
	public void clearActions() {
		
		image.clearActions();
	}

	@Override
	public void clearListeners() {
		
		image.clearListeners();
	}

	@Override
	public void clear() {
		
		image.clear();
	}

	@Override
	public Stage getStage() {
		
		return image.getStage();
	}


	@Override
	public boolean isDescendantOf(Actor actor) {
		
		return image.isDescendantOf(actor);
	}

	@Override
	public boolean isAscendantOf(Actor actor) {
		
		return image.isAscendantOf(actor);
	}

	@Override
	public boolean hasParent() {
		
		return image.hasParent();
	}

	@Override
	public Group getParent() {
		
		return image.getParent();
	}


	@Override
	public boolean isTouchable() {
		
		return image.isTouchable();
	}

	@Override
	public boolean isVisible() {
		
		return image.isVisible();
	}

	@Override
	public void setVisible(boolean visible) {
		
		image.setVisible(visible);
	}

	@Override
	public Object getUserObject() {
		
		return image.getUserObject();
	}

	@Override
	public void setUserObject(Object userObject) {
		
		image.setUserObject(userObject);
	}

	@Override
	public float getX() {
		
		return image.getX();
	}

	@Override
	public float getX(int align) {
		
		return image.getX(align);
	}

	@Override
	public float getY() {
		
		return image.getY();
	}

	@Override
	public float getY(int align) {
		
		return image.getY(align);
	}

	@Override
	public void setPosition(float x, float y, int align) {
		
		image.setPosition(x, y, align);
	}

	@Override
	public void moveBy(float x, float y) {
		
		image.moveBy(x, y);
	}

	@Override
	public float getWidth() {
		
		return image.getWidth();
	}

	@Override
	public float getHeight() {
		
		return image.getHeight();
	}

	@Override
	public float getTop() {
		
		return image.getTop();
	}

	@Override
	public float getRight() {
		
		return image.getRight();
	}



	@Override
	public void sizeBy(float size) {
		
		image.sizeBy(size);
	}

	@Override
	public void sizeBy(float width, float height) {
		
		image.sizeBy(width, height);
	}

	@Override
	public float getOriginX() {
		
		return image.getOriginX();
	}

	@Override
	public void setOriginX(float originX) {
		
		image.setOriginX(originX);
	}

	@Override
	public float getOriginY() {
		
		return image.getOriginY();
	}

	@Override
	public void setOriginY(float originY) {
		
		image.setOriginY(originY);
	}

	@Override
	public void setOrigin(float originX, float originY) {
		
		image.setOrigin(originX, originY);
	}

	@Override
	public void setOrigin(int align) {
		
		image.setOrigin(align);
	}

	@Override
	public float getScaleX() {
		
		return image.getScaleX();
	}

	@Override
	public void setScaleX(float scaleX) {
		
		image.setScaleX(scaleX);
	}

	@Override
	public float getScaleY() {
		
		return image.getScaleY();
	}

	@Override
	public void setScaleY(float scaleY) {
		
		image.setScaleY(scaleY);
	}

	@Override
	public void setScale(float scaleXY) {
		
		image.setScale(scaleXY);
	}

	@Override
	public void scaleBy(float scale) {
		
		image.scaleBy(scale);
	}

	@Override
	public void scaleBy(float scaleX, float scaleY) {
		
		image.scaleBy(scaleX, scaleY);
	}

	@Override
	public float getRotation() {
		
		return image.getRotation();
	}

	@Override
	public void setRotation(float degrees) {
		
		image.setRotation(degrees);
	}

	@Override
	public void rotateBy(float amountInDegrees) {
		
		image.rotateBy(amountInDegrees);
	}

	@Override
	public void setColor(Color color) {
		
		image.setColor(color);
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		
		image.setColor(r, g, b, a);
	}

	@Override
	public Color getColor() {
		
		return image.getColor();
	}

	@Override
	public String getName() {
		
		return image.getName();
	}

	@Override
	public void setName(String name) {
		
		image.setName(name);
	}

	@Override
	public void toFront() {
		
		image.toFront();
	}

	@Override
	public void toBack() {
		
		image.toBack();
	}

	@Override
	public void setZIndex(int index) {
		
		image.setZIndex(index);
	}

	@Override
	public int getZIndex() {
		
		return image.getZIndex();
	}

	@Override
	public boolean clipBegin() {
		
		return image.clipBegin();
	}

	@Override
	public boolean clipBegin(float x, float y, float width, float height) {
		
		return image.clipBegin(x, y, width, height);
	}

	@Override
	public void clipEnd() {
		
		image.clipEnd();
	}

	@Override
	public Vector2 screenToLocalCoordinates(Vector2 screenCoords) {
		
		return image.screenToLocalCoordinates(screenCoords);
	}

	@Override
	public Vector2 stageToLocalCoordinates(Vector2 stageCoords) {
		
		return image.stageToLocalCoordinates(stageCoords);
	}

	@Override
	public Vector2 localToStageCoordinates(Vector2 localCoords) {
		
		return image.localToStageCoordinates(localCoords);
	}

	@Override
	public Vector2 localToParentCoordinates(Vector2 localCoords) {
		
		return image.localToParentCoordinates(localCoords);
	}

	@Override
	public Vector2 localToAscendantCoordinates(Actor ascendant,
			Vector2 localCoords) {
		
		return image.localToAscendantCoordinates(ascendant, localCoords);
	}

	@Override
	public Vector2 parentToLocalCoordinates(Vector2 parentCoords) {
		
		return image.parentToLocalCoordinates(parentCoords);
	}

	@Override
	public void drawDebug(ShapeRenderer shapes) {
		if(image!=null && shapes!=null)
		image.drawDebug(shapes);
	}


	@Override
	public void setDebug(boolean enabled) {
		
		image.setDebug(enabled);
	}

	@Override
	public boolean getDebug() {
		
		return image.getDebug();
	}

	@Override
	public Actor debug() {
		
		return image.debug();
	}

	@Override
	public String toString() {
		
		return image.toString();
	}

	@Override
	public void setWidth(float width) {
		image.setWidth(width);
		super.setWidth(width);
	}
	
	@Override
	public void setHeight(float height) {
		image.setHeight(height);
		super.setHeight(height);
	}
	
	@Override
	public void setSize(float width, float height) {
		image.setSize(width, height);
		super.setSize(width, height);
	}
	
	@Override
	public void setPosition(float x, float y) {
		image.setPosition(x, y);
		super.setPosition(x, y);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		drawBefore();
		image.draw(batch, parentAlpha);
		drawActors(batch, parentAlpha);
	}
	
	private void drawActors(Batch batch, float parentAlpha) {
		for(Actor actor:actors){
			if(actor.isVisible()){
				actor.act(Gdx.graphics.getDeltaTime());
				float x=actor.getX(),y=actor.getY();
				actor.setPosition(x+getX(), y+getY());
				actor.draw(batch, parentAlpha);
				actor.setPosition(x, y);
			}
		}
	}
	
	@Override
	public Touchable getTouchable() {
		return image.getTouchable();
	}
	
	@Override
	public boolean addListener(EventListener listener) {
		return image.addListener(listener);
	}

	public void drawBefore() {}
}
