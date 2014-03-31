package com.cigam.sigil.magic;

import com.badlogic.gdx.physics.box2d.Shape;

public abstract class Target extends Spell {

	private Shape area;
	public Shape getArea(){
		return this.area;
	}
}
