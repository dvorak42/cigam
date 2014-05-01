package com.cigam.sigil.materials;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.MaterialDescriptor;

public class Element2 extends MaterialDescriptor {

	public Element2() {
		super();
		this.init(null,3,0.2f,0);
		ParticleEffect p = new ParticleEffect();
		p.load(Gdx.files.internal("art/particles/Element2.p"), Gdx.files.internal("art/particles"));
		this.init(p,0,0,0);
		this.scaleManifestation(1*(Constants.SPELL_SCALE_FACTOR), 1/(Constants.SPELL_SCALE_FACTOR));
	}

	@Override
	public void OnCollide(PhysicalEntity p) {
		// TODO Does damage, sets things on fire, etc...
	}


	@Override
	public void NoCollide(PhysicalEntity b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
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
