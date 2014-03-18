package com.cigam.sigil.magic;



public abstract class Spell {
	public Die effectValue;
	public float returnValue;
	public float duration;

	public abstract void topEvalEffect();
	public abstract MaterialDescriptor evalEffect();
	
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
