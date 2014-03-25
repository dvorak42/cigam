package com.cigam.sigil.materials;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Game;

import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.Helper;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.BattleScreen;

public class Creation extends MaterialDescriptor {
	private Direction dir;
	private MaterialDescriptor target;
	private float duration;
	private CigamGame game;
	public Creation(MaterialDescriptor target, Direction dir, float dur, CigamGame game, Vec2 pos) {
		super();
		this.target = target;
		BodyDef bd = new BodyDef();
		bd.position = pos;
		bd.active = false; //TODO: something more clever than this
		bd.angle = 0;
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;
		FixtureDef fd = new FixtureDef();
		fd.density = 0.01f;
		CircleShape cs = new CircleShape();
		cs.setRadius(50f);
		cs.m_p.set(0, 0);
		fd.shape = cs;
		this.init(null, 0.5f, 0.0001f, 0, dur, Area.baseArea.CIRCLE, fd, bd);
		this.game = game;
		this.dir = dir;
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
	}

	@Override
	public void OnCreate(PhysicalEntity p) {
		Vec2 castDir = Helper.v2v(Helper.directionToVector(dir));
		castDir.normalize();	
		target.bd.position = bd.position.add(castDir.mul(this.fd.shape.m_radius));
		//System.out.println(target.bd.position + " is caster location");
		//System.out.println(bd.position.add(castDir.mul(this.fd.shape.m_radius)) + " is created object location");
		SpellEffect effect = new SpellEffect(target.duration*this.duration, game.world, target, target.bd, new FixtureDef[]{target.fd});
		((BattleScreen) game.current).createSpellEffect(effect);
	}

	@Override
	public void NoCollide(PhysicalEntity b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

}
