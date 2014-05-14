package com.cigam.sigil;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


public class SigilContactListener implements ContactListener {
	@Override
	public void beginContact(Contact c) {
		if(c.getFixtureA().getBody().getUserData() instanceof PhysicalEntity && c.getFixtureB().getBody().getUserData() instanceof PhysicalEntity) {
			PhysicalEntity a = ((PhysicalEntity) c.getFixtureA().getBody().getUserData());
			PhysicalEntity b = ((PhysicalEntity) c.getFixtureB().getBody().getUserData());
			if(a.mat != null && b.mat != null) {
				//System.out.println("collision started between " + a + " and " + b);
				a.mat.OnCollide(b);
				b.mat.OnCollide(a);
			}
			if(a instanceof Enemy && b instanceof Player && a.active) {
				((Enemy)a).OnCollide((PhysicalEntity)b);
			}
			if(b instanceof Enemy && a instanceof Player && b.active) {
				((Enemy)b).OnCollide((PhysicalEntity)a);
			}
			if((a instanceof CrystalShard && b instanceof Player)) {
				((CrystalShard)a).touchEntity((PhysicalEntity)b);
			}
			if((b instanceof CrystalShard && a instanceof Player)) {
				((CrystalShard)b).touchEntity((PhysicalEntity)a);
			}
		}
		if(c.getFixtureA().getBody().getUserData() == Constants.LAVA && c.getFixtureB().getBody().getUserData() instanceof PhysicalEntity) {
			((PhysicalEntity) c.getFixtureB().getBody().getUserData()).damage(1000);
		}
		if(c.getFixtureB().getBody().getUserData() == Constants.LAVA && c.getFixtureA().getBody().getUserData() instanceof PhysicalEntity) {
			((PhysicalEntity) c.getFixtureA().getBody().getUserData()).damage(1000);
		}
	}

	@Override
	public void endContact(Contact c) {
		
		if(c!= null&&c.getFixtureA()!=null&&c.getFixtureB()!=null){
			if(c.getFixtureA().getBody().getUserData() instanceof PhysicalEntity && c.getFixtureB().getBody().getUserData() instanceof PhysicalEntity) {
				PhysicalEntity a = ((PhysicalEntity) c.getFixtureA().getBody().getUserData());
				PhysicalEntity b = ((PhysicalEntity) c.getFixtureB().getBody().getUserData());
				if(a.mat != null && b.mat != null) {
					//System.out.println("collision ended");
					a.mat.NoCollide(b);
					b.mat.NoCollide(a);
				}
				if(a instanceof Enemy && b instanceof Player) {
					((Enemy)a).NoCollide((PhysicalEntity)b);
				}
				if(b instanceof Enemy && a instanceof Player) {
					((Enemy)b).NoCollide((PhysicalEntity)a);
				}
			}
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}

}
