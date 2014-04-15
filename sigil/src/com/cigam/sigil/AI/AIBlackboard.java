package com.cigam.sigil.AI;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.cigam.sigil.PhysicalEntity;

public class AIBlackboard {
    
    //example values used in the example class
    public boolean exampleCondition;
    public int exampleParameter;
    public com.cigam.sigil.PhysicalEntity examplePointer;
    
    //basic enemy values
    //set by BT, read by actor
    public Vector2 movePoint;
    public PhysicalEntity moveTarget;
    public boolean movingToPoint;
    public boolean movingToTarget;
    public boolean attacking;
    public PhysicalEntity attackTarget;
    //set by actor, read by BT
    public float distanceToPlayer;
    public PhysicalEntity player;
    public float actorSpeed = (float) com.cigam.sigil.Constants.ENEMY_MOVE_SPEED;
    public PhysicalEntity actor;
}
