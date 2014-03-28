package com.cigam.sigil;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import com.cigam.sigil.screens.BattleScreen;

public class ImmovableWall extends PhysicalEntity {
	public CigamGame game;
	
	public ImmovableWall(CigamGame eg, Vec2 start, Vec2 end) {
		super(((BattleScreen) eg.current).world);
		BodyDef bd = new BodyDef();
		bd.active = true;
		bd.type = BodyType.STATIC;
		bd.userData = this;
		bd.position = start;
		FixtureDef fd = new FixtureDef();
		EdgeShape es = new EdgeShape();
		es.set(new Vec2(0, 0), end.sub(start));
		fd.shape = es;
		updateBody(bd, new FixtureDef[]{fd});
        game = eg;
        this.visible = false;
	}

}
