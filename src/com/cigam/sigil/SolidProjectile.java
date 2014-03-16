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
		bd.position = Helper.v2v(parent.position);
		FixtureDef fd = new FixtureDef();
		CircleShape cs = new CircleShape();
		cs.setRadius(1);
		cs.m_p.set(0, 0);
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(64, 32);
		fd.shape = ps;
		fd.density = density;
		updateBody(bd, new FixtureDef[]{fd});
		body.setLinearVelocity(vel);
		direction = Helper.angleToDirection(bd.angle);
        game = eg;
    }
    
	public void draw(Graphics g) {
		System.out.println(position);
		if(visible)
			img.draw(body.getPosition().x - img.width / 2, body.getPosition().y - img.height / 2, img.width, img.height, direction);
	}
}
