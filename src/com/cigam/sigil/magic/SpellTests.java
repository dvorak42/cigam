package com.cigam.sigil.magic;

import java.util.ArrayList;

import com.cigam.sigil.magic.targets.*;
import com.cigam.sigil.magic.verbs.*;


public class SpellTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<Modifier> mod = new ArrayList<Modifier>();
		ArrayList<Spell> arguments = new ArrayList<Spell>();
		Verb createFire = new Create(new Fire(), mod, arguments);
		arguments.add(new Self());
		Verb summonCreateFire = new Summon(createFire, mod, arguments);
		Verb createSummonCreateFire = new Create(summonCreateFire, mod, arguments);
		Verb createCreateFire = new Create(createFire, mod, arguments);
		System.out.println("Create(Fire)");
		System.out.println(createFire.topEvalEffect());
		System.out.println("-----------------------");
		System.out.println("Summon(Create(Fire))");
		System.out.println(summonCreateFire.topEvalEffect());
		System.out.println("-----------------------");
		System.out.println("Create(Summon(Create(Fire)))");
		System.out.println(createSummonCreateFire.topEvalEffect());
		System.out.println("-----------------------");
		System.out.println("Create(Create(Fire))");
		System.out.println(createCreateFire.topEvalEffect());
		System.out.println("-----------------------");
		
	}

}
