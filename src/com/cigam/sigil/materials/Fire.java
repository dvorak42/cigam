package com.cigam.sigil.materials;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.MaterialDescriptor;

public class Fire extends MaterialDescriptor {

	public Fire() {
		super();
		BodyDef bd = new BodyDef();
		bd.active = true;
		bd.angle = 0;
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;
		FixtureDef fd = new FixtureDef();
		fd.density = 0.01f;
		CircleShape cs = new CircleShape();
		cs.setRadius(1f);
		cs.m_p.set(0, 0);
		fd.shape = cs;
		this.init(null, 0.5f, 0.0001f, 0, 10,Area.baseArea.CIRCLE, fd, bd);
	}

	@Override
	public void OnCollide(MaterialDescriptor m) {
		// TODO Does damage, sets things on fire, etc...
	}

	@Override
	public void OnCreate() {
		// TODO Auto-generated method stub
		
	}
}
