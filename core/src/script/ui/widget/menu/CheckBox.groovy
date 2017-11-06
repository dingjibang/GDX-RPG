package script.ui.widget.menu

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.rpsg.gdxQuery.CustomRunnable
import com.rpsg.gdxQuery.GdxQuery
import com.rpsg.rpg.core.UI
import com.rpsg.rpg.ui.widget.Image
import com.rpsg.rpg.ui.widget.Label

class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox{

	GdxQuery query = new GdxQuery(this)
	CustomRunnable<CheckBox> _run;

	CheckBox(String text, com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle style) {
		super(text, style)
	}

	CheckBox padText(int padding){
		getImageCell().padRight(padding)
		this
	}

	CheckBox checked(boolean checked){
		this.setChecked(checked)
		this
	}

	CheckBox click(){
		_run.run this
		this
	}

	CheckBox click(CustomRunnable<CheckBox> run){
		_run = run
		query.click({run.run this})
		this
	}
}
