package com.cigam.sigil.materials;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.cigam.sigil.FindSpecificFixture;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.screens.AdventureScreen;

public class Creation extends MaterialDescriptor {
	private SpellEffect created;
	private float effectValue;
	public Creation(float effectValue) {
		super();
		this.effectValue = effectValue;
		ParticleEffect p = new ParticleEffect();
		System.out.println("NEW");
		p.load(Gdx.files.internal("art/particles/create.p"), Gdx.files.internal("art/particles"));
		this.init(p,0,0,0);
	}


	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen b) {
		Vector2 castDir = Utils.angleToVector(manifestation.angle);
		castDir.nor();	
		//TODO: Use correct offset.
		if(manifestation.body == null || manifestation.target == null)
			return;
		Fixture manifestFixture = manifestation.body.getFixtureList().get(0);
		FindSpecificFixture f = new FindSpecificFixture(manifestFixture);
		b.world.rayCast(f,manifestation.body.getPosition().cpy().add(castDir.cpy().scl(manifestFixture.getShape().getRadius()*2)),manifestation.body.getPosition());
		manifestation.target.position = f.intersectionPoint;
		System.out.println(manifestation.target);
		manifestation.target.duration = manifestation.duration;
		manifestation.target.effectValue*=effectValue;
		created = b.createSpellEffect(manifestation.target);
	}

	@Override
	public void onDestroy(AdventureScreen b){
		System.out.println("Created = " + created);
		b.destroySpellEffect(created);
	}



}
