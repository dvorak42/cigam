package com.cigam.sigil;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.materials.SelfMat;
import com.cigam.sigil.screens.AdventureScreen;

public class Player extends PhysicalEntity {
	public String name = "John Smith";

    private TextureAtlas textureAtlas;
    private Animation[] animations;
    private TextureRegion[][] textures;
	
	//World world, MaterialDescriptor material, BodyDef bd, FixtureDef[] fds
	public Player(SigilGame g, Sprite s,AdventureScreen a) {
		super(g, s, a, new SelfMat());
		initEntity();
		animations = new Animation[5];
		textures = new TextureRegion[5][];
		textureAtlas = new TextureAtlas(Gdx.files.internal("art/PCAnimation/PC.atlas"));
		for(int i = 0; i<4; i++){
			textures[i] = new TextureRegion[4];
			for(int j = 0; j < 4; j++){
				textures[i][j] = textureAtlas.findRegion(""+(4*i+j));
			}
			animations[i] = new Animation(1/8f, textures[i]);
		}
		health = Constants.DEFAULT_HEALTH;
	}

	@Override
	public void initBody() {
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		bd.fixedRotation = true;
		bd.linearDamping = 8f;

		body = screen.world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 0.1f; 
		PolygonShape p = new PolygonShape();
		p.set(Utils.initSpellHitBox(24*Constants.PLAYER_SCALE, Constants.SPELL_SCALE_FACTOR));
		fd.shape = p;
		body.createFixture(fd);
		//Utils.mainBodies.attachFixture(body, "player", fd, sprite.getWidth());
	    //modelOrigin = Utils.mainBodies.getOrigin("player", sprite.getWidth());
		body.setUserData(this);
	}

	@Override
	public void render(float dt) {
		super.render(dt);
		direction = Utils.vecToDir(body.getLinearVelocity());
		if(direction == Direction.BACKWARD||direction == Direction.LEFT||direction == Direction.RIGHT){
			float width = animations[direction.ordinal()].getKeyFrame(elapsedTime).getRegionHeight()*Constants.PLAYER_SCALE;
			float height = animations[direction.ordinal()].getKeyFrame(elapsedTime).getRegionWidth()*Constants.PLAYER_SCALE;
			if(visible)
				game.batch.draw(animations[direction.ordinal()].getKeyFrame(elapsedTime, true), this.getPosition().x-width/2, this.getPosition().y-height/6, width, height);
		} else {
			float width = animations[1].getKeyFrame(elapsedTime).getRegionHeight()*Constants.PLAYER_SCALE;;
			float height = animations[1].getKeyFrame(elapsedTime).getRegionWidth()*Constants.PLAYER_SCALE;
			if(visible)
				game.batch.draw(animations[1].getKeyFrame(elapsedTime, true), this.getPosition().x-width/2, this.getPosition().y-height/6, width, height);
		}
	}
	
	@Override
	public void kill(){
		textureAtlas.dispose();
		super.kill();
	}
	
	@Override
	public String toString() {
		return "Player";
	}
}
