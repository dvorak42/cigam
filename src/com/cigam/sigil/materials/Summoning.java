package com.cigam.sigil.materials;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.ContactEdge;


import com.cigam.sigil.CigamGame;
import com.cigam.sigil.ClosestQueryCallback;
import com.cigam.sigil.Helper;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.Area;
import com.cigam.sigil.magic.MaterialDescriptor;


public class Summoning extends MaterialDescriptor {
	private MaterialDescriptor target;
	private float duration;
	private CigamGame game;
	private AABB aabb;
	private PhysicalEntity attractor;
	private ArrayList<PhysicalEntity> objectsInRange;
	public Summoning(MaterialDescriptor target, float dur, CigamGame game, Vec2 pos) {
		super();
		objectsInRange = new ArrayList<PhysicalEntity>();
		this.target = target;
		BodyDef bd = new BodyDef();
		//bd.active = false; //TODO: something more clever than this
		bd.angle = 0;
		bd.position = pos;
		bd.type = BodyType.DYNAMIC;
		bd.fixedRotation = true;
		FixtureDef fd = new FixtureDef();
		fd.density = 0.01f;
		fd.isSensor = true;
		CircleShape cs = new CircleShape();
		cs.setRadius(300f);
		cs.m_p.set(0, 0);
		fd.shape = cs;
		this.init(null, 0.5f, 0.0001f, 0, dur, Area.baseArea.CIRCLE, fd, bd);
		this.game = game;
		aabb = new AABB();
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		System.out.println("collided with " + p);
		objectsInRange.add(p);
	}

	@Override
	public void NoCollide(PhysicalEntity p) {
		System.out.println("not colliding with " + p);
		objectsInRange.remove(p);
	}
	@Override
	public void Update(){
		if(attractor != null){
			System.out.println("Updating");
			for(PhysicalEntity p: objectsInRange){
				Vec2 dir = attractor.body.getPosition().sub(p.body.getPosition());
				dir.normalize();
				p.body.applyForceToCenter(dir.mul(-10000));
			}
		}
	}
	@Override
	public void OnCreate(PhysicalEntity self) {
		Vec2 center = bd.position;
		System.out.println("spell centered on " + center);
		Vec2 dif = new Vec2(this.fd.shape.m_radius, this.fd.shape.m_radius);
		ClosestQueryCallback callback = new ClosestQueryCallback(self.body);
		aabb.lowerBound.set(center.sub(dif));
		aabb.upperBound.set(center.add(dif));
		game.world.queryAABB(callback, aabb);
		attractor = null;
		System.out.println("nearest is " + callback.nearest.getUserData());
		System.out.println("objectsInRange are " + objectsInRange);
		float min = Float.MAX_VALUE;
		for(PhysicalEntity p: objectsInRange){
			float distance = self.body.getPosition().sub(p.body.getPosition()).length();
			if(distance < min&&p.body.getType()==BodyType.DYNAMIC&&p.body!=self.body){
				min = distance;
				attractor = p;
			}
		}
		System.out.println("Attractor is " + attractor);
	}

}
