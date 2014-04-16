package com.cigam.sigil.magic;

import com.badlogic.gdx.utils.Array;
import com.cigam.sigil.screens.AdventureScreen;

public class Spell {
	public Array<Spell> arguments;
	
	private int cost;
	private float duration;
	public float radius;

	public Spell() {
		arguments = new Array<Spell>();
	}

	public SpellDescriptor effect() {
		if(arguments.size > 0)
			return arguments.get(0).effect();
		return null;
	}
	
	public void cast(AdventureScreen s) {
		
	}
	
	public int cost() {
		int total = cost;
		for(Spell s : arguments)
			total += s.cost();
		return total;
	}
	
	public float duration() {
		return duration;
	}
	
	public float radius() {
		return radius;
	}
}
