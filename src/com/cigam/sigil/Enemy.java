package com.cigam.sigil;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.screens.BattleScreen;


public class Enemy extends PhysicalEntity {
	public CigamGame game;
	public BattleScreen pScreen;
	int timeout = 0;
	public int hurtTimer;
	
	public Enemy(CigamGame eg, BattleScreen battleScreen) {
		super(((BattleScreen) eg.current).world);
		
		BodyDef bd = new BodyDef();
		bd.active = true;
		bd.type = BodyType.DYNAMIC;
		bd.userData = this;
		bd.angle = 0;
		bd.fixedRotation = true;
		bd.awake = true;
		bd.position = new Vec2(0, 0);
		
		FixtureDef fd = new FixtureDef();
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(64, 32);
		fd.shape = ps;
		fd.density = 1;
		updateBody(bd, new FixtureDef[]{fd});
		direction = Constants.Direction.NORTH;
        game = eg;
		pScreen = battleScreen;
	}
	
	public void setPosition(Vector2f pos) {
		body.setTransform(Helper.v2v(pos), body.getAngle());
	}
	
	@Override
	public void update(int dt) {
		super.update(dt);

		if(!active)
			return;
		body.applyForce(Helper.v2v(Helper.directionToVector(direction).scale((float) (Constants.ENEMY_MOVE_SPEED*dt))), body.getWorldCenter());			
		
		direction = Helper.randomDirection();
	}
}