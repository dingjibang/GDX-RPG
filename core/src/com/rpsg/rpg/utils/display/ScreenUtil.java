package com.rpsg.rpg.utils.display;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
/**
 * 屏幕工具类 可以获取截图
 * @author 煞笔学生
 *
 */
public class ScreenUtil {
	static Stage tmpStage=new Stage();
	public static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown){
//        tmpStage.getBatch().begin();
//        tmpStage.getBatch().end();
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
	
	public static Image getScreenshot(){
		tmpStage.draw();
        final Pixmap pixmap = getFrameBufferPixmap(0, 0, GameUtil.screen_width, GameUtil.screen_height);
        Image i= new Image(new TextureRegion(new Texture(pixmap),0,GameUtil.screen_height,GameUtil.screen_width,-GameUtil.screen_height));
        pixmap.dispose();
        return i;
    }
	
	public static Pixmap getFrameBufferPixmap(int x, int y, int w, int h) {

//		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);

		final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);

		return pixmap;
	}
	
}
