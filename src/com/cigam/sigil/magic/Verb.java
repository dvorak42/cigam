package com.cigam.sigil.magic;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.Shape;

import com.cigam.sigil.CigamGame;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.screens.BattleScreen;

public abstract class Verb extends Spell {
	public Spell target; // Only array or target allowed
	public ArrayList<Spell> arguments; //Only array or target allowed
	public float duration;
	public Shape area;
	public float castDelay;
	public BattleScreen screen;
	public PhysicalEntity caster;
		
	public abstract void cast();

	public Verb(Verb target, ArrayList<Spell> args){
		this.caster = target.caster;
		this.screen = target.screen;
		this.target = target;
		this.arguments = args;
		this.castDelay = 0.3f+target.castDelay;
		this.duration = 1.0f;
	}
	public Verb(PhysicalEntity caster, BattleScreen b, Target target,  ArrayList<Spell> args){
		this.caster = caster;
		this.screen = b;
		this.target = target;
		this.arguments = args;
		this.castDelay = 0.3f;
		this.duration = 5.0f;
	}
}
