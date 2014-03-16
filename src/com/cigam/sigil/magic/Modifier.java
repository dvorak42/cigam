package com.cigam.sigil.magic;

import java.util.ArrayList;

public abstract class Modifier extends Spell{
	private ArrayList<Modifier> modifiers; //Only modifiers are allowed
	private Spell target; // Only array or modifier allowed
	public abstract void Modify(Spell target);
}
