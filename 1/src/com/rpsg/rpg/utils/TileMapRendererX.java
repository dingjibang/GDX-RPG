
package com.rpsg.rpg.utils;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * <h1>精简＆修改版TileMapRenderer<br></h1>
 * 扔掉了原版很多也不知道都是什么激霸乱七八糟的东西（扔的已经只剩下两个方法了，可怕<br>
 * 我就不说这个傻屌libgdx引擎到底多屌了，随便new个画笔就莫名其妙的能画图<br>
 * 还搞个SpriteCache画图，你说这个谁尼玛懂啊。<br>
 * 总之改成用batch画图了，画图的时候必须传入一个batch。<br>
 * 算法什么的也都全改了，变成普通的三维数组遍历，虽然我很努力的想要读懂原来的画图算法，但是事实总是告诉我，有那个时间不如简单些个循环遍历下拉到。改算法后似乎FPS有些降低，嘛反正是一个2D腊鸡游戏。
 * @version 1.0.3 20140613
 * @author 煞笔学生
 **/
public class TileMapRendererX{
	public TileAtlas atlas;
	public TiledMap map;
	public OrthographicCamera camera;
	public TileMapRendererX (TiledMap map, TileAtlas atlas,Camera c) {
		this.atlas = atlas;
		this.map = map;
		camera=(OrthographicCamera)c;
	}

	public void render (OrthographicCamera cam, int layers, SpriteBatch batch) {
		render(cam.position.x, cam.position.y, cam.viewportWidth * cam.zoom, cam.viewportHeight * cam.zoom, layers ,batch);
	}

	public void render (float x, float y, float width, float height,int layers, SpriteBatch batch) {
		int startx=(int)x/48;
		if(startx<0) startx=0;
		int xmod=(int)x%48;
		batch.setColor(1, 1, MathUtils.random(), 1);
		int l=layers;
		for (int i = 0; i < map.layers.get(l).tiles.length; i++) 
			for (int j = startx; j < startx+GameUtil.screen_width/48+2; j++) 
				if (map.layers.get(l).tiles[i][j] != 0){
					batch.draw(
							atlas.getRegion(map.layers.get(l).tiles[i][j]),
							(float)((j-(int)x/48)*48-xmod)/camera.zoom, 
							(float)(((map.layers.get(l).tiles.length-i-1)*48f/camera.zoom)+(GameUtil.screen_height*camera.zoom-y)),
							48f/camera.zoom,
							48f/camera.zoom);
				}

	}


}
