package com.cigam.sigil.screens;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class RadialMenu {
	ArrayList<RadialMenu> subMenus;
	int selected;
	boolean visible;
	boolean firstClick;
	Vector2 position;
	float radius;
	Color color;
	
	public RadialMenu() {
		subMenus = new ArrayList<RadialMenu>();
		selected = -1;
		visible = false;
		firstClick = true;
		position = new Vector2();
		radius = 128;
		color = Color.WHITE;
	}
	
	public RadialMenu(Object... items) {
		this();
		for(Object o : items) {
			addMenu(new RadialEnd(o));
		}
	}
	
	public void setRadius(float r) {
		radius = r;
		for(RadialMenu rm : subMenus)
			rm.setRadius((float) (r / 2));  //menue item sizes
	}
	
	public void addMenu(RadialMenu rm) {
		subMenus.add(rm);
		rm.setRadius((float) (radius / 2)); //recurive size of submenues
	}
	
	public Object getValue() {
		if(selected != -1) {
			Object childValue = subMenus.get(selected).getValue();
			return childValue;
		}
		return null;
	}
	
	public void update(Vector2 mouse, boolean clicked) {
		this.update(mouse, clicked, mouse);
	}
	
	public void hide() {
		selected = -1;
		visible = false;
		for(RadialMenu rm : subMenus)
			rm.hide();
	}
	
	public void update(Vector2 mouse, boolean clicked, Vector2 center) {
		if(clicked) {
			if(firstClick) {
				visible = true;
				firstClick = false;
				position = center.cpy();
			}
			
			if(selected == -1) {
				float d = mouse.dst(position);
				if(d > 1.8 * radius) {          //dictates how far the cursor goes before hiding the current menu.
					hide();
				} else if(subMenus.size() > 0) {
					float ax = 360 / subMenus.size();
					float angle = mouse.cpy().sub(position).angle();
					float da = angle % ax;
					if(d > radius * 0.4 && d < radius * 1.4 && (da < ax / 2 || da > 3 * ax / 2)) {
						selected = (int) (angle / ax);
						subMenus.get(selected).firstClick = true;
						subMenus.get(selected).update(mouse, clicked, position.cpy().add(new Vector2(radius, 0).rotate(selected * ax)));
					}		
				}
			} else if(subMenus.size() > 0) {
				RadialMenu child = subMenus.get(selected);
				child.update(mouse, clicked, child.position);
				if(!child.visible)
					selected = -1;
			} else {
				float d = mouse.dst(position);
				if(d > 1.3 * radius) {
					hide();
				}
			}
		} else {
			firstClick = true;
			hide();
			if(selected >= 0 && subMenus.size() > 0)
				subMenus.get(selected).update(mouse, clicked, mouse);
		}
	}

	public void renderPreview(ShapeRenderer sr, SpriteBatch b, Vector2 position, float radius) {
		sr.setColor(Color.GRAY);
		sr.circle(position.x, position.y, radius);		
	}
	
	public void render(ShapeRenderer sr, SpriteBatch b) {
		if(visible) {
			sr.setColor(color);
			sr.circle(position.x, position.y, radius);
			for(int i = 0; i < subMenus.size(); i++) {
				Vector2 sp = position.cpy().add(new Vector2(radius, 0).rotate(i * 360 / subMenus.size()));
				subMenus.get(i).renderPreview(sr, b, sp, 10); //this controls the menu indicator dots
			}
			if(selected >= 0)
				subMenus.get(selected).render(sr, b);
		}
	}
}
