package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.screens.AdventureScreen;

import de.lessvoid.nifty.builder.PanelBuilder;


public abstract class Spell {
	public Spell target; // Only array or target allowed
	public ArrayList<Spell> arguments; //Only array or target allowed
	public float duration;
	public PolygonShape area;
	public float castDelay;
	public float effectValue;
	public Spell parent;
	public int argsNum;
	public PanelBuilder gui;
	public float defaultDuration;
	public float defaultRadius;
	public enum Type {
		VERB, TARGET, MODIFIER;
	};
	public ArrayList<Type> validTargets;
	public ArrayList<Type> validArguments;
	public Type type;
	
	public Spell(){
		arguments = new ArrayList<Spell>();
		target = null;
		area = new PolygonShape();
		validTargets = new ArrayList<Type>();
		validTargets.add(Spell.Type.VERB);
		validTargets.add(Spell.Type.TARGET);
		validArguments = new ArrayList<Type>();
		validArguments.add(Spell.Type.VERB);
		validArguments.add(Spell.Type.TARGET);
		area.set(Utils.initSpellHitBox(10,Constants.SPELL_SCALE_FACTOR));
		//area.setRadius(radius);
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		//System.out.println(this.getClass());
	};
	
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
		child.parent = this;
		child.castDelay += this.castDelay;
	}
	
	public boolean addTarget(Spell child) {
		System.out.println("Target added to " + this+". Argument is " + child);
		boolean valid = false;
		for(Type t:validTargets){
			if(t==child.type)
				valid = true;
		}
		if(valid){
			if(target==null){
				target = child;
			}
			child.parent = this;
			child.castDelay += this.castDelay;
			return true;
		} else {
			return false;
		}
		
	}

	public void removeTarget() {
		target = null;
	}

	public boolean addArgument(Spell child) {
		System.out.println("argument added to " + this+". Argument is " + child);
		boolean valid = false;
		for(Type t:validTargets){
			if(t==child.type)
				valid = true;
		}
		if(valid){
			arguments.add(child);
			child.parent = this;
			child.castDelay += this.castDelay;
			return true;
		} else {
			return false;
		}
	}
	
	public void removeArgument(Spell child) {
		arguments.remove(child);
	}
	public boolean isDone(){
		return (target!=null&&argsNum==arguments.size());
	}
	public abstract SpellDescriptor evalEffect(PhysicalEntity caster);
	public abstract void cast(AdventureScreen screen, PhysicalEntity caster);
	
	
}
