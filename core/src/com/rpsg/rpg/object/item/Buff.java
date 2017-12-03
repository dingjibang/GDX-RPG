package com.rpsg.rpg.object.item;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.object.game.Iconable;
import com.rpsg.rpg.object.game.Target;
import com.rpsg.rpg.object.prop.Props;
import com.rpsg.rpg.ui.widget.Image;

/***
 * GDX-RPG Buff<br>
 * <br>
 * Buff在战斗中，可以挂载到一个{@link Target}身上，使他获得额外的增益/负面效果<br>
 * Buff一般从两个地方获得，第一是{@link Equipment 装备}身上自带的buff，另一种也是主要的，从{@link UsableItem#effect}中获取。<br>
 * Buff本身只是一段数值描述，不包含回合数，包含回合数的是{@link EffectBuff}。
 */
public class Buff implements Serializable, Iconable{
	private static final long serialVersionUID = 1L;
	
	/**ID*/
	public int id;
	/**buff的名称*/
	public String name;
	/**buff的描述信息*/
	public String description;
	/**buff的类型（buff or debuff）*/
	public BuffType type;
	/**buff所提供的属性*/
	public Props prop;
	
	public Image getIcon() {
		String name = Path.IMAGE_ICONS + "b" + id + ".png";
		if (Gdx.files.internal(name).exists())
			return Res.get(name);
		return getDefaultIcon();
	}

	public static Image getDefaultIcon() {
		return Res.get(Path.IMAGE_ICONS + "b0.png");
	}
	
	public static Buff fromJSON(Integer id, JsonValue value) {
		Buff buff = new Buff();
		
		buff.id = id;
		buff.name = value.getString("name");
		buff.description = value.getString("description");
		buff.type = value.has("type") ? BuffType.valueOf(value.getString("type")) : BuffType.debuff;
		buff.prop = value.has("prop") ? Props.fromJSON(value.get("prop")) : null;
		
		return buff;
	}
	
	public static Buff fromJSON(Integer id){
		return fromJSON(id, File.readJSON(Path.SCRIPT_DATA_BUFF + id + ".grd"));
	}
	
	
}
