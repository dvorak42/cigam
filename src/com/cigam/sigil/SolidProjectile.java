package com.cigam.sigil;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Graphics;

import com.cigam.sigil.Constants.Direction;

public class SolidProjectile extends PhysicalEntity {
    public CigamGame game;
    public Direction direction;
    public Entity parent;
    
    public SolidProjectile(CigamGame eg, float angle, Entity parent, float density, Vec2 vel)//TODO move Body and fixture defs to constants 
    {
        super(eg.world);
		BodyDef bd = new BodyDef();
		bd.active = true;
		bd.type = BodyType.DYNAMIC;
		bd.userData = this;
		bd.angle = angle;
		bd.awake = true;
		bd.bullet = true;
		bd.position = Helper.v2v(parent.position).add(vel.mul(50.0f));
		FixtureDef fd = new FixtureDef();
		fd.density = 10;
		fd.filter.groupIndex = -1;
		CircleShape cs = new CircleShape();
		cs.setRadius(1f);
		cs.m_p.set(0, 0);
		fd.shape = cs;
		fd.density = density;
		updateBody(bd, new FixtureDef[]{fd});
		body.setLinearVelocity(vel);
		direction = Helper.angleToDirection(bd.angle);
        game = eg;
    }
}
