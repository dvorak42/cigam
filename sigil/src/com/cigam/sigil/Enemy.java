package com.cigam.sigil;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cigam.sigil.AI.AIBlackboard;
import com.cigam.sigil.AI.BasicEnemyBT;
import com.cigam.sigil.materials.EnemyMat;
import com.cigam.sigil.screens.AdventureScreen;

public class Enemy extends PhysicalEntity {
	private ArrayList<PhysicalEntity> collidingObjects;
	float damageDelay;
	
	private AIBlackboard bb = new AIBlackboard();
	private BasicEnemyBT bt = new BasicEnemyBT();

	Vector2 position;
	
	public Enemy(SigilGame eg, Sprite sprite, AdventureScreen a, Player player) {
		this(eg, sprite, a, player, new Vector2(300, 300));
	}

	public Enemy(SigilGame eg, Sprite sprite, AdventureScreen a, Player player, Vector2 pos) {
		super(eg, sprite, a, new EnemyMat());
		position = pos;
		collidingObjects = new ArrayList<PhysicalEntity>();
		initEntity();
		initBehaviorTree(player);
		health = Constants.ENEMY_MAX_HEALTH;
	}

	//Initializes the blackboard and behavior tree
	public void initBehaviorTree(Player player){
	    bt.createBehaviourTree(bb);
        bb.player = player;
        bb.actor = this;
	}
	
	@Override
	public void initBody() {
		//Vector2 pos = new Vector2(300,300);

		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.angle = 0;
		bd.fixedRotation = true;
		bd.position.set(position.cpy().sub(sprite.getWidth() / 2, sprite.getHeight() / 2));
		
		body = screen.world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 0.5f; 
	    Utils.mainBodies.attachFixture(body, "enemy", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("enemy", sprite.getWidth());
	    body.setUserData(this);
	}

	@Override
	public void render(float delta) {
		if(!active)
			return;
		super.render(delta);
		
		/* for degbugging BT
		 * if (Gdx.input.isKey(Input.Keys.M)){
		    health -= 10.0f;
		}*/
		
		if(active()){
			damageDelay -= delta;
			if(damageDelay <= 0){
				for(int i = 0; i < collidingObjects.size(); i++){
					System.out.println("damaging " + collidingObjects.get(i));
					collidingObjects.get(i).damage(5);
					damageDelay = 1;
				}
			}
		} else {
			collidingObjects.clear();
		}

		
		bt.updateBehaviorTree();
		bb.distanceToPlayer = bb.player.getPosition().dst(getPosition());
		
		if (bb.movingToTarget){
		    if (bb.moveTarget == bb.player){
		        body.applyForceToCenter(bb.player.getPosition().sub(getPosition()).nor().scl((float)Constants.ENEMY_MOVE_SPEED), true);
		    }
		}
	}

	public void OnCollide(PhysicalEntity p) {
		if(!collidingObjects.contains(p)){
			collidingObjects.add(p);
		}
	}

	public void NoCollide(PhysicalEntity p) {
		collidingObjects.remove(p);
	}
}