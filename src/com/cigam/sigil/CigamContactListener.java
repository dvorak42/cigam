package com.cigam.sigil;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class CigamContactListener implements ContactListener {

	@Override
	public void beginContact(Contact c) {
		PhysicalEntity a = ((PhysicalEntity) c.getFixtureA().getBody().getUserData());
		PhysicalEntity b = ((PhysicalEntity) c.getFixtureB().getBody().getUserData());
		if(a.mat != null && b.mat != null){
			//System.out.println("collision started between " + a + " and " + b);
			a.mat.OnCollide(b);
			b.mat.OnCollide(a);
		}
	}

	@Override
	public void endContact(Contact c) {
		PhysicalEntity a = ((PhysicalEntity) c.getFixtureA().getBody().getUserData());
		PhysicalEntity b = ((PhysicalEntity) c.getFixtureB().getBody().getUserData());
		if(a.mat != null && b.mat != null){
			//System.out.println("collision ended");
			a.mat.NoCollide(b);
			b.mat.NoCollide(a);
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
