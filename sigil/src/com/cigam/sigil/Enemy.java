package com.cigam.sigil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.AI.AIBlackboard;
import com.cigam.sigil.AI.AIConstants;
import com.cigam.sigil.AI.BasicEnemyBT;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.screens.AdventureScreen;

public class Enemy extends PhysicalEntity {
	private AIBlackboard bb = new AIBlackboard();
	private BasicEnemyBT bt = new BasicEnemyBT();
	
	public Enemy(SigilGame eg, Sprite sprite, AdventureScreen a, MaterialDescriptor mat, Player player) {
		super(eg, sprite, a, mat);
		initEntity();
		initBehaviorTree(player);
		health = Constants.DEFAULT_HEALTH;
	}
	
	//Initializes the blackboard and behavior tree
	public void initBehaviorTree(Player player){
	    bt.createBehaviourTree(bb);
        bb.player = player;
        bb.actor = this;
	}
	
	@Override
	public void initBody() {
		Vector2 pos = new Vector2(300,300);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.angle = 0;
		bd.fixedRotation = true;
		bd.position.set(pos);
		
		body = screen.world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 0.0001f; 

	    Utils.mainBodies.attachFixture(body, "enemy", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("enemy", sprite.getWidth());

	    body.setUserData(this);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		if(!active)
			return;
		
		bt.updateBehaviorTree();
		bb.distanceToPlayer = bb.player.getPosition().dst(getPosition());
		
		if (bb.movingToTarget){
		    if (bb.moveTarget == bb.player){
		        body.applyForceToCenter(bb.player.getPosition().sub(getPosition()).nor().scl((float)Constants.ENEMY_MOVE_SPEED/2), true);
		    }
		}
	}
}