package script.ui.widget.menu


import com.badlogic.gdx.graphics.g2d.Batch;
import com.rpsg.rpg.core.UI;
import com.rpsg.rpg.ui.widget.Label;
import com.rpsg.rpg.ui.widget.Image;

class BackgroundableLabel extends Label{
	
	Image bg, bg2
	
	BackgroundableLabel(String color, float a1, String color2, Float a2, Object text, int fontsize) {
		super(text, fontsize)
		
		bg = UI.base().query().color(color).a(a1).get()
		
		if(color2)
			bg2 = UI.base().query().color(color2).a(a2).get()
			
		center()
	}
	
	BackgroundableLabel(String color, float a1, Object text, int fontsize) {
		this(color, a1, null, null, text, fontsize)
	}
	
	
	@Override
	void draw(Batch batch, float parentAlpha) {
		bg.setSize width, height
		bg.setPosition x, y
		bg.draw(batch, parentAlpha)
		if(bg2){
			bg2.setSize width, height
			bg2.setPosition x, y
			bg2.draw(batch, parentAlpha)
		}
		super.draw(batch, parentAlpha)
	}
	
	BackgroundableLabel size(int w, int h){
		setSize w, h
		this
	}
	
	
}
