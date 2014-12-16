package com.rpsg.rpg.system;

import java.util.HashSet;
import java.util.StringTokenizer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TileSet;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.rpsg.rpg.system.text.Setting;
/**
 *	TileAtlas ��ͼ��ͼ�ض�Ӧ�࣬�޸���libgdxԴ�룬����ͼƬ����ݹ��ܡ� 
 *	@author �ޅ�ѧ��<br>
 */
public class TileAtlas extends com.badlogic.gdx.graphics.g2d.tiled.TileAtlas implements Disposable{
	protected IntMap<TextureRegion> regionsMap = new IntMap<TextureRegion>();
	protected final HashSet<Texture> textures = new HashSet<Texture>(1);

	/** Protected constructor to allow different implementations */
	protected TileAtlas () {
	}

	/** Creates a TileAtlas for use with {@link TileMapRenderer}. Run the map through TiledMapPacker to create the files required.
	 * @param map The tiled map
	 * @param inputDir The directory containing all the files created by TiledMapPacker */
	public TileAtlas (TiledMap map, FileHandle inputDir) {
		for (TileSet set : map.tileSets) {
			FileHandle packfile = getRelativeFileHandle(inputDir, removeExtension(set.imageName) + " packfile");
			TextureAtlas textureAtlas = new TextureAtlas(packfile, packfile.parent(), false);
			Array<AtlasRegion> atlasRegions = textureAtlas.findRegions(removeExtension(removePath(set.imageName)));
			for (AtlasRegion reg : atlasRegions) {
				regionsMap.put(reg.index + set.firstgid, reg);
				if (!textures.contains(reg.getTexture())) {
					Texture txt=reg.getTexture();
					if(Setting.DISPLAY_ANTI_ALIASING)
						txt.setFilter(TextureFilter.Linear, TextureFilter.Linear);
					textures.add(txt);
				}
			}
		}
	}

	/** Gets an {@link TextureRegion} for a tile id
	 * @param id tile id
	 * @return the {@link TextureRegion} */
	public TextureRegion getRegion (int id) {
		return regionsMap.get(id);
	}

	/** Releases all resources associated with this TileAtlas instance. This releases all the textures backing all AtlasRegions,
	 * which should no longer be used after calling dispose. */
	@Override
	public void dispose () {
		for (Texture texture : textures) {
			texture.dispose();
		}
		textures.clear();
	}

	private static String removeExtension (String s) {
		int extensionIndex = s.lastIndexOf(".");
		if (extensionIndex == -1) {
			return s;
		}

		return s.substring(0, extensionIndex);
	}

	private static String removePath (String s) {
		String temp;

		int index = s.lastIndexOf('\\');
		if (index != -1) {
			temp = s.substring(index + 1);
		} else {
			temp = s;
		}

		index = temp.lastIndexOf('/');
		if (index != -1) {
			return s.substring(index + 1);
		} else {
			return s;
		}
	}

	private static FileHandle getRelativeFileHandle (FileHandle path, String relativePath) {
		if (relativePath.trim().length() == 0) {
			return path;
		}

		FileHandle child = path;

		StringTokenizer tokenizer = new StringTokenizer(relativePath, "\\/");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.equals("..")) {
				child = child.parent();
			} else {
				child = child.child(token);
			}
		}

		return child;
	}

	/** Flips all regions in the atlas on the specified axis.
	 * @param x - if <code>true</code> region is flipped on the <b>x</b> axis
	 * @param y - if <code>true</code> region is flipped on the <b>y</b> axis */
	public void flipRegions (boolean x, boolean y) {
		for (TextureRegion region : regionsMap.values()) {
			region.flip(x, y);
		}
	}
}
