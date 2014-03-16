package com.cigam.sigil;

public class Constants {
	public static final int MAX_HEALTH = 80;
	public static final int ENEMY_MAX_HEALTH = 1;
	public static final int FIRE_LIFE = 500;
	public static final double ENEMY_MOVE_SPEED = 0.5;
	public static final int[] DISPLAY_DIMS = {800, 600};
	public static final float AGGRO_RANGE = 10;
	public static final double ENEMY_FIRE_RATE = 16;
	public static final int PLAYER_PROJECTILE_SPEED = 1;
	public static final int ENEMY_PROJECTILE_SPEED = 2;
	public static final int DEFAULT_DAMAGE = 1;
	public static final double PLAYER_MOVE_SPEED = 0.75;
	public static final int FIRE_DELAY = 500;
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
