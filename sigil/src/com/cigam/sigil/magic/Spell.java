package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.targets.Empty;
import com.cigam.sigil.screens.AdventureScreen;

import de.lessvoid.nifty.builder.PanelBuilder;


public abstract class Spell {
	public Spell target; // Only array or target allowed
	public Spell[] arguments; //Only array or target allowed
	public float duration;
	public PolygonShape area;
	public float castDelay;
	public float effectValue;
	public Spell parent;
	public PanelBuilder gui;
	public float defaultDuration;
	public float defaultRadius;
	public Pixmap cachedDiagram;
	public Texture cachedTexture;
	
	public enum Type {
		VERB, TARGET, MODIFIER;
	};
	public ArrayList<Type> validTargets;
	public ArrayList<Type> validArguments;
	public Type type;
	
	public Spell(){
		arguments = new Spell[4];
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
		int toAdd = -1;
		for(int i = 0; i < arguments.length; i++) {
			if(arguments[i] == null) {
				toAdd = i;
				break;
			}
		}
		if(toAdd != -1){
			arguments[toAdd] = child;
		} else if(target==null){
			target = child;
		} else {
			Utils.printError("Expected 4 total inputs to " + this + " but got an extra argument ");
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
			if(target==null || target instanceof Empty){
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

	public boolean addArgument(Spell child, int index) {
		System.out.println("argument added to " + this+". Argument is " + child);
		boolean valid = false;
		for(Type t:validTargets){
			if(t==child.type)
				valid = true;
		}
		if(valid && index != -1 && (arguments[index] == null || arguments[index] instanceof Empty)) {
			arguments[index] = child;
			child.parent = this;
			child.castDelay += this.castDelay;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addArgument(Spell child) {
		int toAdd = -1;
		for(int i = 0; i < arguments.length; i++) {
			if(arguments[i] == null || arguments[i] instanceof Empty) {
				toAdd = i;
				break;
			}
		}
		return addArgument(child, toAdd);
	}
	
	public void removeArgument(Spell child) {
		for(int i = 0; i < arguments.length; i++) {
			if(arguments[i] == child) {
				arguments[i] = null;
				break;
			}
		}
	}
	public boolean isDone(){
		boolean done = true;
		for(int i = 0; i < arguments.length; i++) {
			if(arguments[i] == null) {
				done = false;
				break;
			}
		}

		return (target != null && done);
	}
	public abstract SpellDescriptor evalEffect(PhysicalEntity caster);
	public abstract void cast(AdventureScreen screen, PhysicalEntity caster);
	
	public Texture getDiagram() {
		if(cachedDiagram == null)
			cachedDiagram = spellDiagram();
		if(cachedTexture == null)
			cachedTexture = new Texture(cachedDiagram);
		return cachedTexture;
	}
	
	public void resetDiagram() {
		cachedDiagram = null;
		cachedTexture = null;
		if(target != null)
			target.resetDiagram();
		for(int i = 0; i < arguments.length; i++) {
			if(arguments[i] != null)
				arguments[i].resetDiagram();
		}
	}
	
	public Pixmap spellDiagram() {
		Pixmap p = new Pixmap(Gdx.files.internal(Utils.classesToIconPaths.get(this.getClass())));

		for(int i = 0; i < arguments.length; i++) {
			Spell arg = arguments[i];
			if(arg == null)
				continue;
			Pixmap argP = arg.spellDiagram();
			int dX = Constants.SLOT_POSITION[i + 1][0];
			int dY = Constants.SLOT_POSITION[i + 1][1];
			int dS = Constants.SLOT_SIZE;
			p.drawPixmap(argP, 0, 0, 512, 512, dX, dY, dS, dS);
		}
		
		if(target != null) {
			Pixmap targetP = target.spellDiagram();
			int dX = Constants.SLOT_POSITION[0][0];
			int dY = Constants.SLOT_POSITION[0][1];
			int dS = Constants.SLOT_SIZE;
			p.drawPixmap(targetP, 0, 0, 512, 512, dX, dY, dS, dS);
		}
		
		return p;
	}
}
