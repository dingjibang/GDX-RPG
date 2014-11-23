
package com.rpsg.rpg.utils;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/**
 * <h1>�����޸İ�TileMapRenderer<br></h1>
 * �ӵ���ԭ��ܶ�Ҳ��֪������ʲô�������߰���Ķ������ӵ��Ѿ�ֻʣ�����������ˣ�����<br>
 * �ҾͲ�˵���ɵ��libgdx���浽�׶����ˣ����new�����ʾ�Ī��������ܻ�ͼ<br>
 * �����SpriteCache��ͼ����˵���˭���궮����<br>
 * ��֮�ĳ���batch��ͼ�ˣ���ͼ��ʱ����봫��һ��batch��<br>
 * �㷨ʲô��Ҳ��ȫ���ˣ������ͨ����ά�����������Ȼ�Һ�Ŭ������Ҫ����ԭ���Ļ�ͼ�㷨��������ʵ���Ǹ����ң����Ǹ�ʱ�䲻���Щ��ѭ�����������������㷨���ƺ�FPS��Щ���ͣ��ﷴ����һ��2D������Ϸ��
 * @version 1.0.3 20140613
 * @author ɷ��ѧ��
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
