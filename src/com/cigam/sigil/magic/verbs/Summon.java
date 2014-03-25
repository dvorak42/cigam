package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.ClosestQueryCallback;
import com.cigam.sigil.Helper;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.materials.Creation;
import com.cigam.sigil.materials.Summoning;
import com.cigam.sigil.screens.*;


public class Summon extends Verb {
	private float defaultRad = 50;
	private MaterialDescriptor mat;
	
	public Summon(PhysicalEntity c, CigamGame g, Verb target, ArrayList<Spell> args) {
		super(c, g, target, args);
	}
	public Summon(PhysicalEntity c, CigamGame g, Target target, ArrayList<Spell> args) {
		super(c, g, target, args);
	}
	
	@Override
	public void topEvalEffect(){
		mat = target.evalEffect();
	}

	@Override
	public MaterialDescriptor evalEffect() {
		mat = target.evalEffect();
		return new Summoning(mat, this.duration, game, this.caster.body.getTransform().p);
	}
	@Override
	public void cast() {
	Summoning s = new Summoning(mat, this.duration, game, caster.body.getTransform().p);
	SpellEffect effect = new SpellEffect(target.duration*this.duration, game.world, s, s.bd, new FixtureDef[]{s.fd});
	((BattleScreen) game.current).createSpellEffect(effect);
	}
}
