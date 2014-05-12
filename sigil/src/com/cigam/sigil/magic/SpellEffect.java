package com.cigam.sigil.magic;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.screens.AdventureScreen;

public class SpellEffect extends PhysicalEntity {
	public float duration;
	public SpellDescriptor target;
	public ArrayList<SpellDescriptor> arguments;
	public float effectValue;
	public float angle;
	public SpellDescriptor sd;

	public SpellEffect(SigilGame game, AdventureScreen a, SpellDescriptor s){
		super(game, null, a, s.mat);
		this.effectValue = s.effectValue;
		this.duration = s.duration;
		this.target = s.target;
		this.angle = s.angle;
		this.arguments = s.arguments;
		this.sd = s;
		//System.out.println(this.mat.image);
		if(mat.image!=null){
			mat.image.setPosition(s.position.x, s.position.y);
			float scale = (float)Math.sqrt(s.scale*Constants.SPELL_SCALE_FACTOR/ 100 * 2);
			for(ParticleEmitter pe : mat.image.getEmitters()) {
				pe.getXOffsetValue().setLow(pe.getXOffsetValue().getLowMin() * scale * scale / 2);
				pe.getYOffsetValue().setLow(pe.getYOffsetValue().getLowMin() * scale * scale / 2);
				pe.getVelocity().setLowMin(pe.getVelocity().getLowMin() * scale);
				pe.getVelocity().setLowMax(pe.getVelocity().getLowMax() * scale);
				pe.getVelocity().setHighMin(pe.getVelocity().getHighMin() * scale);
				pe.getVelocity().setHighMax(pe.getVelocity().getHighMax() * scale);
			}
			mat.image.start();
		}
		//System.out.println("Initial duration is " + duration);
		initEntity();
	}
	
	@Override
	public void initBody() {
		BodyDef bd = new BodyDef();
		if(sd != null)
			bd.position.set(sd.position);
		//System.out.println(bd.position);
		//bd.type = BodyType.KinematicBody;
		bd.type = BodyType.DynamicBody;
		body = world.createBody(bd);
		
		FixtureDef fd = new FixtureDef();
		if(sd != null)
			fd.shape = sd.shape;
		fd.density = 0.1f;
		fd.isSensor = true;
		//TODO: Filter

		body.createFixture(fd);
		body.setUserData(this);
	}

	public void timeStep(float dt){
		if(this.active())
			duration -= dt;
	}
	
	public void render(float delta) {
		//modelOrigin = new Vector2(sprite.getWidth() / 2, sprite.getHeight() / 2);
		if(this.mat.image!=null&&this.active()&&this.visible){
			mat.image.setPosition(this.body.getWorldCenter().x, this.body.getWorldCenter().y);
			mat.image.draw(screen.game.batch, delta);
		}
		autoDelay -= delta;
		if(autoDelay <= 0) {
			damage(autoDamage);
			autoDelay = 1;
		}
		if(this.active) {
			elapsedTime += delta;
		}
	}
}
