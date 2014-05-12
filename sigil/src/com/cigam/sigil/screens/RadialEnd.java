package com.cigam.sigil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.Utils;

public class RadialEnd extends RadialMenu {
	public Object value;
	public Sprite icon;
	private float iconScale;
	
	public RadialEnd(Object value) {
		super();
		iconScale = .7f;
		this.value = value;
		this.icon = null;
		String path;
		if(value.equals(-1)){
			path = "UI/cigam/DeleteSymbol512.png";
		} else {
			path = Utils.classesToMenuPaths.get(this.value);
		}
		if(path!=null){
			Texture t = new Texture(Gdx.files.internal(path));
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			icon = new Sprite(t);
			icon.flip(false, true);
			icon.setSize(radius*iconScale, radius*iconScale);
		}
	}

	public Object getValue() {
		return value;
	}

	public void renderPreview(ShapeRenderer sr, SpriteBatch b, Vector2 position, float radius) {
		if(icon != null && !value.equals(-1)) {
			sr.end();
			b.begin();
			icon.setPosition(position.x-this.radius*2*iconScale, position.y-this.radius*2*iconScale);
			icon.draw(b, 0.5f);
			b.end();
			sr.begin(ShapeType.Filled);
		} else {
			//sr.setColor(Color.GRAY);
			//sr.circle(position.x, position.y, radius);
		}
	}

	public void render(ShapeRenderer sr, SpriteBatch b) {
		if(visible) {
			if(this.icon!=null){
				sr.end();
				b.begin();
				icon.setPosition((float) (position.x-radius*2*iconScale), (float) (position.y-radius*2*iconScale));
				this.icon.draw(b);
				b.end();
				sr.begin(ShapeType.Filled);
			} else {
				//sr.setColor(color);
				//sr.circle(position.x, position.y, radius);				
			}
			for(int i = 0; i < subMenus.size(); i++) {
				/*sr.setColor(Color.GRAY);
				Vector2 sp = position.cpy().add(new Vector2(radius, 0).rotate(i * 360 / subMenus.size()));
				sr.circle(sp.x, sp.y, 5);*/
			}
			if(selected >= 0)
				subMenus.get(selected).render(sr, b);
		}
	}
}
