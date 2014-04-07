package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.Target;
import com.cigam.sigil.magic.Verb;
import com.cigam.sigil.materials.Binding;
import com.cigam.sigil.materials.Imbueing;
import com.cigam.sigil.screens.AdventureScreen;

public class Imbue extends Verb {
	private ArrayList<SpellDescriptor> toBeImbued;
	private SpellDescriptor toImbueInto;
	private float defaultDuration = 10;
	private float defaultRadius = 100;
	
	public Imbue(Verb target, ArrayList<Spell> args) {
		super(target, args);
		toBeImbued = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
	}
	
	public Imbue(PhysicalEntity caster, AdventureScreen b, Target target, ArrayList<Spell> args) {
		super(caster, b, target, args);
		toBeImbued = new ArrayList<SpellDescriptor>();
		area = new CircleShape();
		area.setRadius(defaultRadius);
	}

	@Override
	public void cast() {
		screen.createSpellEffect(evalEffect());
	}

	@Override
	public void topEvalEffect() {
		toImbueInto = target.evalEffect();
		for(Spell s: arguments){
			toBeImbued.add(s.evalEffect());
		}
	}

	@Override
	public SpellDescriptor evalEffect() {
		toImbueInto = target.evalEffect();
		for(Spell s: arguments){
			toBeImbued.add(s.evalEffect());
		}
		SpellDescriptor effect = new SpellDescriptor(new Imbueing(toImbueInto, toBeImbued), defaultDuration, toImbueInto, toBeImbued, caster.body.getAngle(), area, caster.body.getPosition());
		return effect;
	}

}
