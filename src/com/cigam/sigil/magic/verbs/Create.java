package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.Die;
import com.cigam.sigil.magic.Modifier;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.Target;
import com.cigam.sigil.magic.Verb;


public class Create extends Verb {
	public Create(Verb target, ArrayList<Modifier> mod, ArrayList<Spell> args) {
		super(target, mod, args);
		Area area = new Area(Area.baseArea.SQUARE, 1);
		area.stretch(10);
		this.setArea(area);
	}
	public Create(Target target, ArrayList<Modifier> mod, ArrayList<Spell> args) {
		super(target, mod, args);
		Area area = new Area(Area.baseArea.SQUARE, 1);
		area.stretch(10);
		this.setArea(area);
	}
	
	@Override
	public String topEvalEffect() {
		this.evalModifiers();
		String targetEffect = this.getTarget().evalAsTarget();
		Die targetAmount = this.getTarget().getEffectValue();
		targetAmount = targetAmount.mul(this.getEffectValue());
		this.setDuration(this.getTarget().getDuration()*this.getDuration());  
		return "Creates " + targetAmount + targetEffect + ". " +  
				" \n The effect is created " + this.getArea().getForwardLength()/2 + 
				"feet in front of caster.\n Duration: " + this.getDuration() + " ticks\n Casting time: " +
				this.getCastDelay();
	}
	@Override
	public String evalAsTarget() {
		return " created " + getTarget().evalAsTarget();
	}
	@Override
	public String evalArguments() {
		return "no args";
	}
}
