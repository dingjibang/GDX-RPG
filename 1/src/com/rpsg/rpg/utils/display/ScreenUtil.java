package com.rpsg.rpg.utils.display;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.game.GameUtil;
/**
 * 屏幕工具类 可以获取截图
 * @author 煞笔学生
 *
 */
public class ScreenUtil {
	public static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown){
        final Pixmap pixmap = getFrameBufferPixmap(x, y, w, h);

        if (yDown) {
            // Flip the pixmap upside down
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position((h - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
        }

        return pixmap;
    }
	
	public static Pixmap getFrameBufferPixmap (int x, int y, int w, int h) {
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		final int potW = MathUtils.nextPowerOfTwo(w);
		final int potH = MathUtils.nextPowerOfTwo(h);

		final Pixmap pixmap = new Pixmap(potW, potH, Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(x, y, potW, potH, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);

		return pixmap;
	}
	
	static int blood;
	static Image bloodimage;
	public static void draw(SpriteBatch batch){
		if(blood!=0)
			 bloodimage.draw(batch, 1);
	}
	
	public static void step(){
		if(blood>6) {
			blood-=8;
			bloodimage.setColor(1,1,1,(1f/255f)*blood);
		}
	}
	
	public static void init(){
		bloodimage=new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_CORE+"blood.png")));
		bloodimage.setSize(GameUtil.screen_width, GameUtil.screen_height);
	}
	
	public static void drawBlood(){
		blood=254;
	}
}
