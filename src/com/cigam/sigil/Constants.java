package com.cigam.sigil;

public class Constants {
    // TODO: define specific subclasses of enemy, which should have their own definitions for aggro range, fire right, projectiel speed, move speed, etc.

	public static final int MAX_HEALTH = 80;
	public static final int ENEMY_MAX_HEALTH = 1;
	public static final int FIRE_LIFE = 500;
	public static final double ENEMY_MOVE_SPEED = 5000000;
	public static final int[] DISPLAY_DIMS = {800, 600};
	public static final float AGGRO_RANGE = 10;
	public static final double ENEMY_FIRE_RATE = 16;
	public static final int PLAYER_PROJECTILE_SPEED = 200;
	public static final int ENEMY_PROJECTILE_SPEED = 20;
	public static final int DEFAULT_DAMAGE = 1;
	public static final double PLAYER_ACCELERATION_FACTOR = 10000000;
	public static final int FIRE_DELAY = 500;
	public static final int VELOCITY_ITERS = 8;
	public static final int POSITION_ITERS = 3;
	public static int MAGIC = 17 * 22;
	public static int TILE_SIZE = 128;
	public static enum Direction
    {
        NORTH,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    }
}
