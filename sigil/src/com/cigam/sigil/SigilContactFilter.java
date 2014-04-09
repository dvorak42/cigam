package com.cigam.sigil;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

public class SigilContactFilter implements ContactFilter {
	@Override
	public boolean shouldCollide(Fixture a, Fixture b) {
		Object oa = a.getBody().getUserData();
		Object ob = b.getBody().getUserData();
		if(oa instanceof Entity && ob instanceof Entity) {
			Entity ea = (Entity)oa;
			Entity eb = (Entity)ob;
			
			return (ea.plane & eb.plane) != 0;
		}
		
		return true;
	}

}
