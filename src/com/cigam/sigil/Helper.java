package com.cigam.sigil;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Vector2f;

public class Helper {
	public static Vector2f bound(Vector2f vec, float minX, float minY, float maxX, float maxY)
	{
		return new Vector2f(Helper.clamp(vec.x, minX, maxX), Helper.clamp(vec.y, minY, maxY));
	}

	public static Constants.Direction oppositeDirection(Constants.Direction dir)
	{
		switch(dir) {
		case NORTH : return Constants.Direction.SOUTH;
		case NORTH_EAST : return Constants.Direction.SOUTH_WEST;
		case NORTH_WEST : return Constants.Direction.SOUTH_EAST;
		case EAST : return Constants.Direction.WEST;
		case SOUTH : return Constants.Direction.NORTH;
		case SOUTH_EAST : return Constants.Direction.NORTH_WEST;
		case SOUTH_WEST : return Constants.Direction.NORTH_EAST;
		case WEST : return Constants.Direction.EAST;
		}
		return Constants.Direction.NORTH;
	}
	
	public static Constants.Direction directionTo(Vector2f v1, Vector2f v2)
	{
		Vector2f dirVec = v2.copy().sub(v1).normalise();
		return Constants.Direction.values()[((((int)(dirVec.getTheta() + 22.5)) / 45 + 2) % 8)];
	}
	
	public static float clamp(float x, float min, float max)
	{
		return Math.min(max, Math.max(x, min));
	}

	public static Constants.Direction randomDirection() {
		double r = Math.random();
		return Constants.Direction.values()[(int)(r * 8)];        
	}
	
	public static Vector2f directionToVector(Constants.Direction d)
	{
		switch(d) {
		case NORTH : return new Vector2f(0, -1);
		case NORTH_EAST : return new Vector2f(1, -1);
		case NORTH_WEST : return new Vector2f(-1, -1);
		case EAST : return new Vector2f(1, 0);
		case SOUTH : return new Vector2f(0, 1);
		case SOUTH_EAST : return new Vector2f(1, 1);
		case SOUTH_WEST : return new Vector2f(-1, 1);
		case WEST : return new Vector2f(-1, 0);
		}
		return new Vector2f(0, 0);
	}
	
	public static Constants.Direction angleToDirection(float a) {
		return directionTo(new Vector2f(), new Vector2f(1, 0).add(a));
	}

	public static float directionToAngle(Constants.Direction dir) {
		return (float)directionToVector(dir).getTheta();
	}

	public static Vector2f v2v(Vec2 v) {
		return new Vector2f(v.x, v.y);
	}
	
	public static Vec2 v2v(Vector2f v) {
		return new Vec2(v.x, v.y);
	}
}
