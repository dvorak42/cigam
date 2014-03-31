package com.cigam.sigil;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

public class ClosestQueryCallback implements QueryCallback {
	public Body nearest = null;
	public float min = Float.MAX_VALUE;
	private Vector2 start;
	private Body b;
	public ClosestQueryCallback(Body b){
		start = b.getPosition();
		this.b = b;
	}

	@Override
	public boolean reportFixture(Fixture f) {
		float distance = start.cpy().sub(f.getBody().getPosition()).len();
		//System.out.println("Now testing " + f.getBody().getUserData() + ". Distance is " + distance);
		if(distance < min&&f.getBody().getType()==BodyType.DynamicBody&&f.getBody()!=b){
			min = distance;
			nearest = f.getBody();
		}
		return true;
	}

}
