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
public class GdxCellQuery<T extends Actor, TQ extends GdxQuery>{

	public static final Test FIRST_CHILD = cq -> cq.query.first().get() == cq.getActor();
	public static final Test LAST_CHILD = cq -> cq.query.last().get() == cq.getActor();
	public static Test INDEX(int i) {
		return cell -> cell.query.indexOf(cell.getActor()) == i;
	}
	
	GdxQuery listener;
	TQ query;
	Cell<T> cell;
	
	
	public static <T extends Actor, TQ extends GdxQuery> GdxCellQuery<T, TQ> build(TQ query, Cell<T> cell){
		GdxCellQuery<T, TQ> q = new GdxCellQuery<>();
		q.query = query;
		q.cell = cell;
		return q;
	}

	public GdxCellQuery<T, TQ> size(float w, float h) {
		cell.size(w, h);
		return this;
	}
	
	public GdxCellQuery<T, TQ> prefSize(float w, float h) {
		cell.prefSize(w, h);
		return this;
	}
	
	public GdxCellQuery<T, TQ> left(){
		cell.left();
		return this;
	}
	
	public GdxCellQuery<T, TQ> right(){
		cell.right();
		return this;
	}
	
	public GdxCellQuery<T, TQ> top(){
		cell.top();
		return this;
	}
	
	public GdxCellQuery<T, TQ> bottom(){
		cell.bottom();
		return this;
	}
	
	public TQ end(){
		return query;
	}
	
	public GdxCellQuery<T, TQ> click(Runnable r){
		listener = new GdxQuery(cell.getActor()).click(r);
		return this;
	}
	
	public GdxCellQuery<T, TQ> clickIf(Test t){
		if(t.test(this))
			click();
		return this;
	}
	
	public GdxCellQuery<T, TQ> click(){
		if(listener != null)
			listener.click();
		return this;
	}
	
	public GdxCellQuery<T, TQ> bind(Object o){
		cell.getActor().setUserObject(o);
		return this;
	}
	
	public Object object(){
		return cell.getActor().getUserObject();
	}
	
	public <T2 extends Actor> GdxCellQuery<T2, TQ> cell(T2 a){
		return (GdxCellQuery<T2, TQ>)end().cell(a);
	}

	public GdxCellQuery<T, TQ> padLeft(int i) {
		cell.padLeft(i);
		return this;
	}
	
	public GdxCellQuery<T, TQ> padRight(int i) {
		cell.padRight(i);
		return this;
	}
	
	public GdxCellQuery<T, TQ> padTop(int i) {
		cell.padTop(i);
		return this;
	}
	
	public GdxCellQuery<T, TQ> padBottom(int i) {
		cell.padBottom(i);
		return this;
	}
	
	public GdxCellQuery<T, TQ> padLR(int i) {
		return padLeft(i).padRight(i);
	}
	
	public GdxCellQuery<T, TQ> padTB(int i) {
		return padTop(i).padBottom(i);
	}
	
	public GdxCellQuery<T, TQ> width(int i) {
		cell.width(i);
		return this;
	}
	
	public GdxCellQuery<T, TQ> height(int i) {
		cell.height(i);
		return this;
	}
	
	public GdxCellQuery<T, TQ> pad(int i) {
		return padTB(i).padLR(i);
	}
	
	public GdxCellQuery<T, TQ> center() {
		cell.center();
		return this;
	}

	public T getActor() {
		return cell.getActor();
	}
	
	public GdxCellQuery<T, TQ> getActor(CustomRunnable<T> run){
		run.run(getActor());
		return this;
	}

	public GdxCellQuery<T, TQ> when(Test firstChild, CustomRunnable<T> run) {
		if(firstChild.test(this))
			run.run(getActor());
		return this;
	}

	public GdxCellQuery<T, TQ> row() {
		query.row();
		return this;
	}

}