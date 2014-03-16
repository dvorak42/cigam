package com.cigam.sigil.magic;

import java.util.ArrayList;

public abstract class Verb extends Spell {
	private ArrayList<Modifier> modifiers; //Only modifiers are allowed
	private Spell target; // Only array or target allowed
	private ArrayList<Spell> arguments; //Only array or target allowed
	private double duration;
	private Area area;
	private double castDelay;
	
	public abstract String evalArguments();
	
	public Verb(Verb target, ArrayList<Modifier> mod, ArrayList<Spell> args){
		super();
		this.target = target;
		this.area = target.getArea();
		this.arguments = args;
		this.modifiers = mod;
		this.castDelay = 0.3+target.getCastDelay();
		this.duration = 1.0;
	}
	public Verb(Target target, ArrayList<Modifier> mod, ArrayList<Spell> args){
		super();
		this.target = target;
		this.area = target.getArea();
		this.arguments = args;
		this.modifiers = mod;
		this.castDelay = 0.3;
		this.duration = 5.0;
	}
	public void evalModifiers(){
		for(Modifier m: this.getModifiers()){
			m.Modify(this);
		}
	}	
	public ArrayList<Modifier> getModifiers(){
		return this.modifiers;
	}
	public Spell getTarget(){
		return this.target;
	}
	public ArrayList<Spell> getArgs(){
		return this.arguments;
	}
	public double getDuration(){
		return this.duration;
	}
	public void setDuration(double duration){
		this.duration = duration;
	}
	public Area getArea(){
		return this.area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public double getCastDelay(){
		return this.castDelay;
	}
}
