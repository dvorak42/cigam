package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Shape;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class Verb extends Spell {
	public Spell target; // Only array or target allowed
	public ArrayList<Spell> arguments; //Only array or target allowed
	public float duration;
	public Shape area;
	public float castDelay;
	public AdventureScreen screen;
	public PhysicalEntity caster;
	public float effectValue;
		
	public abstract void cast();

	public Verb(Verb target, ArrayList<Spell> args){
		this.caster = target.caster;
		this.screen = target.screen;
		this.target = target;
		this.arguments = args;
		this.castDelay = 0.3f+target.castDelay;
		this.duration = 1.0f;
	}
	public Verb(PhysicalEntity caster, AdventureScreen b, Target target,  ArrayList<Spell> args){
		this.caster = caster;
		this.screen = b;
		this.target = target;
		this.arguments = args;
		this.castDelay = 0.3f;
		this.duration = 5.0f;
	}
}
