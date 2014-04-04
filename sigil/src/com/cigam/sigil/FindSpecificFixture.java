package com.cigam.sigil;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class FindSpecificFixture implements RayCastCallback {
	public Fixture toFind;
	public Vector2 intersectionPoint;
	public FindSpecificFixture(Fixture f){
		toFind = f;
	}
	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point,
			Vector2 normal, float fraction) {
		if(fixture.equals(toFind)){
			intersectionPoint = point;
			return 0;
		} else {
			return 1;
		}
	}

}
