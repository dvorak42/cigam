package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.materials.Creation;
import com.cigam.sigil.screens.*;


public class Create extends Spell {
	private SpellDescriptor toCreate;
	private float defaultDuration = Constants.SPELL_DEFAULT_DURATION;
	private float defaultRadius = Constants.SPELL_SHORT_RANGE;
	
	public Create(){
		super();
		area = new CircleShape();
		area.setRadius(defaultRadius);
		effectValue = Constants.CREATE_EFFECT_VALUE;
		argsNum = 0;
		gui = Utils.makeCreateGui("UI/cigam/create512.png");
	}
	/*
	public Create(Spell target, ArrayList<Spell> args) {
		super(target, args);
		effectValue = Constants.CREATE_EFFECT_VALUE;
	}
	public Create(PhysicalEntity c,  AdventureScreen b, Target target, ArrayList<Spell> args) {
		super(c, b, target, args);
		effectValue = Constants.CREATE_EFFECT_VALUE;
	}
	*/

	@Override
	public SpellDescriptor evalEffect(){
		System.out.println("Casting Create");
		toCreate = target.evalEffect();
		System.out.println(toCreate.duration);
		Vector2 pos = caster.body.getWorldCenter().cpy().rotate(caster.body.getAngle());
		SpellDescriptor effect = new SpellDescriptor(new Creation(effectValue), defaultDuration, effectValue, toCreate, null, caster.body.getAngle(), area, pos);
		return effect;
	}
	@Override
	public void cast() {
		Vector2 castDir = new Vector2(1, 0).rotate(caster.body.getAngle());
		castDir.nor();
		System.out.println(toCreate);
		toCreate.position = caster.body.getWorldCenter();
		screen.createSpellEffect(evalEffect());
	}
}
