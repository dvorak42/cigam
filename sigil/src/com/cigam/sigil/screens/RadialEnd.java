package com.cigam.sigil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.Utils;

public class RadialEnd extends RadialMenu {
	public Object value;
	public Sprite icon;
	
	public RadialEnd(Object value) {
		super();
		this.value = value;
		this.icon = null;
		String path = Utils.classesToIconPaths.get(this.value);
		if(path!=null){
			Texture t = new Texture(Gdx.files.internal(path));
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			icon = new Sprite(t);
			icon.flip(false, true);
			icon.setSize(radius, radius);
		}
	}

	public Object getValue() {
		return value;
	}
	
	public void render(ShapeRenderer sr, SpriteBatch b) {
		if(visible) {
			sr.setColor(color);
			sr.circle(position.x, position.y, radius);
			for(int i = 0; i < subMenus.size(); i++) {
				sr.setColor(Color.GRAY);
				Vector2 sp = position.cpy().add(new Vector2(radius, 0).rotate(i * 360 / subMenus.size()));
				sr.circle(sp.x, sp.y, 5);
			}
			if(selected >= 0)
				subMenus.get(selected).render(sr, b);
			if(this.icon!=null){
				icon.setPosition(position.x-radius*2, position.y-radius*2);
				this.icon.draw(b);
			}
		}
	}
}