package com.cigam.sigil;


public class Constants {
    public enum Direction {
    	FORWARD, BACKWARD, RIGHT, LEFT, IDLE;
    }

    // TODO: define specific subclasses of enemy, which should have their own definitions for aggro range, fire right, projectile speed, move speed, etc.

    public static final Integer LAVA = new Integer(132423);
    
	public static final int MAX_HEALTH = 100;
	public static final int ENEMY_MAX_HEALTH = 20;
	public static final int FIRE_LIFE = 500;
	public static final double ENEMY_MOVE_SPEED = 100000;
	public static final float AGGRO_RANGE = 10;
	public static final double ENEMY_FIRE_RATE = 16;
	public static final int PLAYER_PROJECTILE_SPEED = 200;
	public static final int ENEMY_PROJECTILE_SPEED = 20;
	public static final int DEFAULT_DAMAGE = 1;
	public static final double PLAYER_ACCELERATION_FACTOR = 10000;
	public static final int FIRE_DELAY = 10;
	public static final int VELOCITY_ITERS = 8;
	public static final int POSITION_ITERS = 3;
	public static final float PLAYER_SCALE = 0.5f;

	public static final int[][] SLOT_POSITION = new int[][]{{215, 215}, {74, 75}, {352, 75}, {74, 356}, {352, 356}};
	public static final int[] CREATE_SLOT_POSITION = new int[]{226, 303};
	public static final int SLOT_SIZE = 83;
	public static int MAGIC = 17 * 22;
	public static int TILE_SIZE = 128;
	
	public static float SPELL_DEFAULT_DURATION = 100;
	public static float SPELL_LONG_RANGE = 300;
	public static float SPELL_MEDIUM_RANGE = 150;
	public static float SPELL_SHORT_RANGE = 50;
	public static float DENSITY_VERY_LOW;
	public static float DENSITY_LOW;
	public static float DENISITY_MEDIUM;
	public static float DENSITY_HIGH;
	public static float DENSITY_VERY_HIGH;
	public static float FORCE_VERY_LOW;
	public static float FORCE_LOW;
	public static float FORCE_MEDIUM = 100000;
	public static float FORCE_HIGH;
	public static float FORCE_VERY_HIGH;
	public static float BIND_EFFECT_VALUE = 50;
	public static float CREATE_EFFECT_VALUE = 2;
	public static float SPELL_SCALE_FACTOR = 1.3f;
	
	public static int MATERIAL_PLANE = 0x01;
	public static int ETHEREAL_PLANE = 0x02;
	public static int ALL_PLANES = 0xFF;
	
}
