package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Shape;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.screens.AdventureScreen;

public abstract class Spell {
	public Spell target; // Only array or target allowed
	public ArrayList<Spell> arguments; //Only array or target allowed
	public float duration;
	public Shape area;
	public float castDelay;
	public AdventureScreen screen;
	public PhysicalEntity caster;
	public float effectValue;
	public Spell parent;
	public int argsNum;

	public Spell(){
		arguments = new ArrayList<Spell>();
		target = null;
	};
	/*
	public Spell(Spell target, ArrayList<Spell> args){
		this.caster = target.caster;
		this.screen = target.screen;
		this.target = target;
		target.parent = this;
		this.arguments = args;
		this.castDelay = 0.3f+target.castDelay;
	}
	public Spell(PhysicalEntity caster, AdventureScreen b, Target target,  ArrayList<Spell> args){
		this.caster = caster;
		this.screen = b;
		this.target = target;
		this.arguments = args;
		this.castDelay = 0.3f;
	}*/
	
	public void addChild(Spell child){
		if (argsNum>arguments.size()){
			arguments.add(child);
		} else if(target==null){
			target = child;
		} else {
			Utils.printError("Expected " + (argsNum+1) + " total inputs to " + this + " but got an extra");
			Utils.printError(arguments.size()+"");
			Utils.printError(child.toString());
			return;
		}
		child.caster = this.caster;
		child.screen = this.screen;
		child.parent = this;
		child.castDelay += this.castDelay;
	}
	
	public boolean isDone(){
		return (target!=null&&argsNum==arguments.size());
	}
	public abstract SpellDescriptor evalEffect();
	public abstract void cast();
}
