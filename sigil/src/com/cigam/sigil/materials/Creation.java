package com.cigam.sigil.materials;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.cigam.sigil.Constants;
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
		//p.load(Gdx.files.internal("particleFiles/create/createSpell"), Gdx.files.internal("art/particles"));
		this.init(p,0,0,0);
		this.scaleManifestation(1*(Constants.SPELL_SCALE_FACTOR), 1/(Constants.SPELL_SCALE_FACTOR));
	}


	@Override
	public void OnCreate(SpellEffect manifestation, AdventureScreen b) {
		Vector2 castDir = Utils.angleToVector(manifestation.angle);
		castDir.nor();	
		if(manifestation.body == null || manifestation.target == null)
			return;
		Fixture manifestFixture = manifestation.body.getFixtureList().get(0);
		FindSpecificFixture f = new FindSpecificFixture(manifestFixture);
		System.out.println(manifestation.body.getPosition().cpy().add(castDir.cpy().scl(manifestFixture.getShape().getRadius()*2)));
		System.out.println(manifestation.body.getPosition());
		b.world.rayCast(f,manifestation.body.getPosition().cpy().add(castDir.cpy().scl(manifestFixture.getShape().getRadius()*2)),manifestation.body.getPosition());
		manifestation.target.position = f.intersectionPoint;
		//System.out.println(manifestation.target);
		manifestation.target.duration = manifestation.duration;
		manifestation.target.effectValue*=effectValue;
		System.out.println(manifestation.target.mat);
		created = b.createSpellEffect(manifestation.target);
	}

	@Override
	public void onDestroy(AdventureScreen b){
		//System.out.println("Created = " + created);
		b.destroySpellEffect(created);
	}

	@Override
	public void scaleManifestation(float x, float y){
		for(int i = 0; i < image.getEmitters().size; i++){
			ScaledNumericValue height = image.getEmitters().get(i).getSpawnHeight();
			ScaledNumericValue width = image.getEmitters().get(i).getSpawnWidth();
			image.getEmitters().get(i).getSpawnHeight().setHigh(height.getHighMax()*y, height.getHighMin()*y);
			image.getEmitters().get(i).getSpawnHeight().setLow(height.getLowMax()*y, height.getLowMin()*y);
			image.getEmitters().get(i).getSpawnWidth().setHigh(width.getHighMax()*x, width.getHighMin()*x);
			image.getEmitters().get(i).getSpawnWidth().setLow(width.getLowMax()*x, width.getLowMin()*x);
		}
	}


}
