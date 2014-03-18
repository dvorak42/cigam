package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Helper;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.screens.*;


public class Summon extends Verb {
	private float defaultRad;
	private SpellEffect effect;
	public Summon(PhysicalEntity c, CigamGame g, Verb target, ArrayList<Spell> args) {
		super(c, g, target, args);
		defaultRad = 10;
	}
	public Summon(PhysicalEntity c, CigamGame g, Target target, ArrayList<Spell> args) {
		super(c, g, target, args);
		defaultRad = 10;
	}
	
	@Override
	public void topEvalEffect(){
		MaterialDescriptor mat = target.evalEffect();
		float finalDuration = target.duration*this.duration;
		Vec2 castDir = Helper.v2v(Helper.directionToVector(caster.getDirection()));
		castDir.normalize();
		mat.bd.position = caster.body.getTransform().p.add(castDir.mul(this.defaultRad));
		effect = new SpellEffect(finalDuration, game.world, mat.bd, new FixtureDef[]{mat.fd});

	}

	@Override
	public MaterialDescriptor evalEffect() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void cast() {
		((BattleScreen) game.current).createSpellEffect(effect);
	}
}
