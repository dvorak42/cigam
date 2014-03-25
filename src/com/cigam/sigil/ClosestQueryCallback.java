package com.cigam.sigil;
import org.jbox2d.callbacks.QueryCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class ClosestQueryCallback implements QueryCallback {
	public Body nearest = null;
	public float min = Float.MAX_VALUE;
	private Vec2 start;
	private Body b;
	public ClosestQueryCallback(Body b){
		start = b.getPosition();
		this.b = b;
	}

	@Override
	public boolean reportFixture(Fixture f) {
		float distance = start.sub(f.getBody().getPosition()).length();
		//System.out.println("Now testing " + f.getBody().getUserData() + ". Distance is " + distance);
		if(distance < min&&f.getBody().getType()==BodyType.DYNAMIC&&f.getBody()!=b){
			min = distance;
			nearest = f.getBody();
		}
		return true;
	}

}
