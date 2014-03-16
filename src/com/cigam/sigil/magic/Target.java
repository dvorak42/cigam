package com.cigam.sigil.magic;

public abstract class Target extends Spell{
	private Area area;
	private double duration;
	public Area getArea(){
		return this.area;
	}
}
