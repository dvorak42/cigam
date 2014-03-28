package com.cigam.sigil.magic;

import org.jbox2d.collision.shapes.Shape;



public abstract class Target extends Spell{

	private Shape area;
	public Shape getArea(){
		return this.area;
	}
}
