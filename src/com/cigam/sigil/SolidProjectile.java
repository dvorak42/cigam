package com.cigam.sigil;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.TileMap;
import com.cigam.sigil.screens.BattleScreen;

public class SolidProjectile extends Entity{
    public CigamGame game;
    public Direction direction;
    public Entity parent;
    
    public SolidProjectile(CigamGame eg, float angle, Entity parent, float density, Vec2 vel)//TODO move Body and fixture defs to constants 
    {
        super();
        BodyDef fb = new BodyDef();
        fb.type = BodyType.DYNAMIC;
        fb.position = parent.body.getPosition(); //TODO: Make this more accurate
        fb.allowSleep = true;
        fb.awake = true;
        fb.bullet = true;
        fb.angle = angle;
        fb.userData = this;
        this.body = game.world.createBody(fb);
        FixtureDef fd = new FixtureDef();
        CircleShape crc = new CircleShape();
        crc.m_p.set(0,0);
        crc.setRadius(1);
        fd.shape = new CircleShape();
        fd.density = density;
        this.body.createFixture(fd);
        this.body.setLinearVelocity(vel);
        direction = Constants.Direction.SOUTH;//TODO: translate physics angle into direction for display
        game = eg;
        this.health = Constants.FIRE_LIFE;
    }
    
    public int maxHealth() {
    	return Constants.FIRE_LIFE;
    }
}
