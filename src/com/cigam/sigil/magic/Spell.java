package com.cigam.sigil.magic;

public abstract class Spell {
	private Die effectValue;
	private String effect;
	private double returnValue;

	public abstract String topEvalEffect();
	public abstract String evalAsTarget();
	public abstract double getDuration();
	
	public Spell(){
		this.effectValue = new Die(1,1);
	}
	public Die getEffectValue(){
		return this.effectValue;
	}
	public void setEffectValue(Die die){
		this.effectValue = die;
	}
}
