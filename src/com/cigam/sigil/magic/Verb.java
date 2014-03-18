package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.cigam.sigil.CigamGame;
import com.cigam.sigil.PhysicalEntity;

public abstract class Verb extends Spell {
	public Spell target; // Only array or target allowed
	public ArrayList<Spell> arguments; //Only array or target allowed
	public float duration;
	public Area area;
	public float castDelay;
	public CigamGame game;
	public PhysicalEntity caster;
		
	public abstract void cast();

	public Verb(PhysicalEntity caster, CigamGame g, Verb target, ArrayList<Spell> args){
		this.caster = caster;
		this.game = g;
		this.target = target;
		this.area = target.area;
		this.arguments = args;
		this.castDelay = 0.3f+target.castDelay;
		this.duration = 1.0f;
	}
	public Verb(PhysicalEntity caster, CigamGame g, Target target,  ArrayList<Spell> args){
		this.caster = caster;
		this.game = g;
		this.target = target;
		this.area = target.getArea();
		this.arguments = args;
		this.castDelay = 0.3f;
		this.duration = 5.0f;
	}
}
