package com.cigam.sigil;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.screens.AdventureScreen;

public class CrystalShard extends PhysicalEntity {
	Vector2 position;
	public Vector2 destination;
	public float teleportDelay;
	public PhysicalEntity teleportTarget;
	
	public CrystalShard(SigilGame eg, Sprite sprite, AdventureScreen a, MaterialDescriptor mat, Vector2 pos) {
        super(eg, sprite, a, mat);
        health = 10000000;
        position = pos;
        teleportDelay = 10.0f;
        initEntity();
	}

    @Override
	public void initBody() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KinematicBody;
		bd.position.set(position.cpy().sub(new Vector2(sprite.getWidth() / 2, sprite.getHeight() / 2)));
		
		body = screen.world.createBody(bd);

		FixtureDef fd = new FixtureDef();
		fd.density = 10; 

	    Utils.mainBodies.attachFixture(body, "crystal", fd, sprite.getWidth());
	    modelOrigin = Utils.mainBodies.getOrigin("crystal", sprite.getWidth());

		body.setUserData(this);
	}
    
    @Override
    public void render(float delta) {
    	if(teleportTarget == null)
    		teleportDelay = 10.0f;
    	else {
    		teleportDelay -= delta;
    		if(Utils.dist(this, teleportTarget) > 50) {
    			teleportTarget.setVisible(true);
    			teleportTarget = null;
    		}
    		else if(teleportDelay < 0)
    			teleportTarget.setPosition(destination);
    	}
    	float N = 0.2f;
    	if(teleportDelay > 8)
    		N = 1f;
    	else if(teleportDelay > 6)
    		N = 0.5f;
    	else if(teleportDelay > 4)
    		N = 0.25f;
    	else if(teleportDelay > 2)
    		N = 0.125f;
    	else 
    		N = 0.0625f;
    	if(teleportTarget != null) {
    		if(teleportDelay % N < N / 2 || teleportDelay >= 10.0f)
    			teleportTarget.setVisible(true);
    		else
    			teleportTarget.setVisible(false);
    	}
    	super.render(delta);
    }
    
    public void touchEntity(PhysicalEntity e) {
    	if(teleportTarget == null)
    		teleportTarget = e;
    }
}
