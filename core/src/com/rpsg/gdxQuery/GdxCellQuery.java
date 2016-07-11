package com.rpsg.gdxQuery;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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
public class GdxCellQuery<T extends Actor>{

	public static final Test FIRST_CHILD = cq -> cq.query.first().getItem() == cq.getActor();
	
	GdxQuery query,listener;
	Cell<T> cell;
	
	
	public static <T2 extends Actor> GdxCellQuery<T2> build(GdxQuery query,Cell<T2> cell){
		GdxCellQuery<T2> q = new GdxCellQuery<>();
		q.query = query;
		q.cell = cell;
		return q;
	}

	public GdxCellQuery<T> size(float w, float h) {
		cell.size(w, h);
		return this;
	}
	
	public GdxCellQuery<T> prefSize(float w, float h) {
		cell.prefSize(w, h);
		return this;
	}
	
	public GdxCellQuery<T> left(){
		cell.left();
		return this;
	}
	
	public GdxCellQuery<T> right(){
		cell.right();
		return this;
	}
	
	public GdxCellQuery<T> top(){
		cell.top();
		return this;
	}
	
	public GdxCellQuery<T> bottom(){
		cell.bottom();
		return this;
	}
	
	public GdxQuery end(){
		return query;
	}
	
	public GdxCellQuery<T> click(Runnable r){
		listener = new GdxQuery(cell.getActor()).click(r);
		return this;
	}
	
	public GdxCellQuery<T> clickIf(Test t){
		if(t.test(this))
			click();
		return this;
	}
	
	public GdxCellQuery<T> click(){
		if(listener != null)
			listener.click();
		return this;
	}
	
	public GdxCellQuery<T> bind(Object o){
		cell.getActor().setUserObject(o);
		return this;
	}
	
	public <T2 extends Actor> GdxCellQuery<T2> cell(T2 a){
		return end().cell(a);
	}

	public GdxCellQuery<T> padLeft(int i) {
		cell.padLeft(i);
		return this;
	}
	
	public GdxCellQuery<T> padRight(int i) {
		cell.padRight(i);
		return this;
	}
	
	public GdxCellQuery<T> padTop(int i) {
		cell.padTop(i);
		return this;
	}
	
	public GdxCellQuery<T> padBottom(int i) {
		cell.padBottom(i);
		return this;
	}
	
	public GdxCellQuery<T> padLR(int i) {
		return padLeft(i).padRight(i);
	}
	
	public GdxCellQuery<T> padTB(int i) {
		return padTop(i).padBottom(i);
	}
	
	public GdxCellQuery<T> pad(int i) {
		return padTB(i).padLR(i);
	}

	public GdxCellQuery<T> center() {
		cell.center();
		return this;
		
	}

	public T getActor() {
		return cell.getActor();
	}
	
}