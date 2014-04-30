package com.cigam.sigil;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.materials.Fire;

public class SigilContactListener implements ContactListener {

	@Override
	public void beginContact(Contact c) {
		if(c.getFixtureA().getBody().getUserData() instanceof PhysicalEntity && c.getFixtureB().getBody().getUserData() instanceof PhysicalEntity) {
			PhysicalEntity a = ((PhysicalEntity) c.getFixtureA().getBody().getUserData());
			PhysicalEntity b = ((PhysicalEntity) c.getFixtureB().getBody().getUserData());
			if(a.mat != null && b.mat != null) {
				System.out.println("collision started between " + a + " and " + b);
				a.mat.OnCollide(b);
				b.mat.OnCollide(a);
			}
			if(a instanceof SolidProjectile || b instanceof SolidProjectile) {
				a.damage(50);
				b.damage(50);
			}
			if((a instanceof CrystalShard && b instanceof Player)) {
				((Player)b).gainShard((CrystalShard)a);
			}
			if((b instanceof CrystalShard && a instanceof Player)) {
				((Player)a).gainShard((CrystalShard)b);
			}
			if(a instanceof SpellEffect) {
				SpellEffect e = (SpellEffect)a;
				if(e.sd != null && e.sd.mat instanceof Fire)
					b.addAutoDamage(4);
			}
			if(b instanceof SpellEffect) {
				SpellEffect e = (SpellEffect)b;
				if(e.sd != null && e.sd.mat instanceof Fire)
					a.addAutoDamage(4);
			}
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
				if(a instanceof SpellEffect) {
					SpellEffect e = (SpellEffect)a;
					if(e.sd != null && e.sd.mat instanceof Fire)
						b.addAutoDamage(-4);
				}
				if(b instanceof SpellEffect) {
					SpellEffect e = (SpellEffect)b;
					if(e.sd != null && e.sd.mat instanceof Fire)
						a.addAutoDamage(-4);
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
