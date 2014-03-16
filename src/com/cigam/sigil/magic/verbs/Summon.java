package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.Die;
import com.cigam.sigil.magic.Modifier;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.Target;
import com.cigam.sigil.magic.Verb;


public class Summon extends Verb {

	public Summon(Target target, ArrayList<Modifier> mod, ArrayList<Spell> args) {
		super(target, mod, args);
		Area area = new Area(Area.baseArea.CIRCLE, 100);
		this.setArea(area);
	}
	public Summon(Verb target, ArrayList<Modifier> mod, ArrayList<Spell> args) {
		super(target, mod, args);
		Area area = new Area(Area.baseArea.CIRCLE, 100);
		this.setArea(area);
	}

	@Override
	public String topEvalEffect() {
		this.evalModifiers();
		String summonedTo = this.evalArguments();
		String targetEffect = this.getTarget().evalAsTarget();
		Die targetAmount = this.getTarget().getEffectValue();
		targetAmount = targetAmount.mul(this.getEffectValue());
		this.setDuration(this.getTarget().getDuration()*this.getDuration()); 
		return "Pulls " +  targetEffect + " with a force of " + this.getEffectValue() + " toward" + summonedTo + 
				" as long as it is within area of the spell" +
				"\n Area: " + this.getArea() +
				"\n Duration: " + this.getDuration() + " ticks" +
				"\n Casting time: " + this.getCastDelay();
	}

	@Override
	public String evalAsTarget() {
		return " a summon spell with stats as follows : \n"+ this.topEvalEffect();
	}
	@Override
	public String evalArguments() {
		return this.getArgs().get(0).evalAsTarget();
	}

}
