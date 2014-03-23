package com.cigam.sigil.magic.verbs;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.FixtureDef;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Helper;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.*;
import com.cigam.sigil.materials.Creation;
import com.cigam.sigil.screens.*;


public class Create extends Verb {
	private float defaultRad = 50;
	private MaterialDescriptor mat;
	
	public Create(PhysicalEntity c, CigamGame g, Verb target, ArrayList<Spell> args) {
		super(c, g, target, args);
	}
	public Create(PhysicalEntity c, CigamGame g, Target target, ArrayList<Spell> args) {
		super(c, g, target, args);
	}
	
	@Override
	public void topEvalEffect(){
		mat = target.evalEffect();
	}

	@Override
	public MaterialDescriptor evalEffect() {
		mat = target.evalEffect();
		return new Creation(mat, caster.getDirection(), this.duration, game);
	}
	@Override
	public void cast() {
		Vec2 castDir = Helper.v2v(Helper.directionToVector(caster.getDirection()));
		castDir.normalize();
		mat.bd.position = caster.body.getTransform().p.add(castDir.mul(this.defaultRad));
		//System.out.println(caster.body.getTransform().p + " is caster location");
		//System.out.println(mat.bd.position + " is created object location");
		SpellEffect effect = new SpellEffect(target.duration*this.duration, game.world, mat, mat.bd, new FixtureDef[]{mat.fd});
		((BattleScreen) game.current).createSpellEffect(effect);
	}
}
