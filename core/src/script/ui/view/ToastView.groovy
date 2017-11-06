package script.ui.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.rpsg.gdxQuery.GdxQuery
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.*
import com.rpsg.rpg.ui.widget.Button
import com.rpsg.rpg.ui.widget.Image
import com.rpsg.rpg.util.Position
import com.rpsg.rpg.util.Timer.TimeType
import com.rpsg.rpg.util.Timer

import script.ui.widget.menu.HeroFGLabel
import script.ui.widget.menu.HeroSelector
import script.ui.widget.menu.MenuLeftBarButton

import static com.rpsg.rpg.util.UIUtil.*

/**
 * GDX-RPG Toast视图
 */
class ToastView extends UIView{
	
	// 0 show 1 deleting 2deleted
	//public int status = 0;
	
	public String text;
	
	public static final float def_interlvalheight = 4;
	public static final float def_interlvalwidth = 60;
	public static final float def_bottommagin = 36.0;
	public static final String def_bgcolor = "333333cc";
	public static final int def_fontsize = 26;
	public static final float def_fadetime = 0.5f;
	
	public float interlvalheight = def_interlvalheight;
	public float interlvalwidth = def_interlvalwidth;
	public float bottommagin = def_bottommagin;
	public String bgcolor = def_bgcolor;
	public int fontsize = def_fontsize;

	public static NinePatchDrawable UI_TOAST;
	static{
		UI_TOAST = new NinePatchDrawable(UI.get9(Path.IMAGE_GLOBAL + "toast_box.png",2,2,1,1,5,5,5,5));
	}
	GdxQuery bg
	GdxQuery grp 
	void create() {
		bg = q(new Image( UI_TOAST)) to stage
		grp = q(new Group()) to stage //
		//table.addActor(UI.base().query().size(870	, 180).color(Color.BLACK).a(0.3f).y(-17).get())
		//def table = new Table().top().left()
		
		//grp.position(360, 36)
		//grp.width(Game.STAGE_WIDTH)
		//grp.center()
		
		String[] lines = text.split("\n");
	    float curbottommagin = bottommagin;
		float totalh = 0;
		float maxwidth = 0;
		for(int i=lines.length -1;i>=0;i--){
			def label = $(lines[i], fontsize) to grp
			def w = label.width()
			label.position(Math.max((float)(Game.STAGE_WIDTH/2f - w/2f),0f),curbottommagin)
			curbottommagin += label.height();
			
			//label.action( Actions.fadeIn(1f))
			if(maxwidth<w) maxwidth =w;
		}
		
		totalh = curbottommagin - bottommagin;
		
		
		bg.size(Math.min((float)(maxwidth + interlvalwidth),(float)Game.STAGE_WIDTH),
			Math.min((float)(totalh + interlvalheight),(float)Game.STAGE_HEIGHT)).
		color(bgcolor).
		x(Math.max((float)(Game.STAGE_WIDTH/2f - maxwidth/2f - interlvalwidth/2f), 0f)).
		y(Math.max((float)(bottommagin - interlvalheight/2f),0f)) 

		bg.a(0f).action( Actions.fadeIn(def_fadetime))
		grp.a(0f).action(Actions.fadeIn(def_fadetime))
		this.buuleable(true)
		//label.center(0)
		//table to grp 
	}
	
	void setRemoveMessage(int delayTime){

		def vw = this;
		Timer.add(TimeType.millisecond, delayTime, new Runnable(){
			public void run()
			{
				bg.action( Actions.fadeOut(def_fadetime))
				grp.action(Actions.fadeOut(def_fadetime))
			}
		})
		Timer.add(TimeType.millisecond, (int)(def_fadetime * 1000) + delayTime, new Runnable(){
			void run(){
				vw.remove();
			}
		})
	}
}