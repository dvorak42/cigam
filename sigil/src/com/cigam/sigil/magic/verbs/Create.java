package com.cigam.sigil.magic.verbs;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.materials.Creation;
import com.cigam.sigil.screens.AdventureScreen;


public class Create extends Spell {
	private SpellDescriptor toCreate;

	
	public Create(){
		super();
		defaultDuration = Constants.SPELL_DEFAULT_DURATION/10;
		defaultRadius = Constants.SPELL_SHORT_RANGE;
		area.set(Utils.initSpellHitBox(defaultRadius, Constants.SPELL_SCALE_FACTOR));
		//area.setAsBox(10, 10);
		effectValue = Constants.CREATE_EFFECT_VALUE;
		argsNum = 0;
		type = Spell.Type.VERB;
		this.gui = Utils.makeCreateGui(Utils.classesToIconPaths.get(this.getClass()));

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
	public SpellDescriptor evalEffect(PhysicalEntity caster){
		toCreate = target.evalEffect(caster);
		//System.out.println("Casting Create with " + toCreate);
		toCreate.position = caster.body.getWorldCenter();
		//System.out.println(toCreate.duration);
		Vector2 pos = caster.body.getWorldCenter().cpy().rotate(caster.body.getAngle());
		float angle = Utils.dirToAngle(caster.direction);
		SpellDescriptor effect = new SpellDescriptor(new Creation(effectValue), defaultDuration, defaultRadius, effectValue, toCreate, null, angle, area, pos);
		return effect;
	}
	@Override
	public void cast(AdventureScreen screen, PhysicalEntity caster) {
		Vector2 castDir = new Vector2(1, 0).rotate(caster.body.getAngle());
		castDir.nor();
		screen.createSpellEffect(evalEffect(caster));
	}
}
