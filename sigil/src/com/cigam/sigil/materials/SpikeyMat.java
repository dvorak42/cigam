package com.cigam.sigil.materials;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.ScaledNumericValue;
import com.cigam.sigil.Constants;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.magic.MaterialDescriptor;

public class SpikeyMat extends MaterialDescriptor {
	private ArrayList<PhysicalEntity> objectsInRange;
	float damageDelay;
	public SpikeyMat() {
		super();
		objectsInRange = new ArrayList<PhysicalEntity>();
		damageDelay = 0;
		ParticleEffect p = new ParticleEffect();
		p.load(Gdx.files.internal("art/particles/Element2.p"), Gdx.files.internal("art/particles"));
		this.init(p,0.2f,0.2f,0);
		this.scaleManifestation(1*(Constants.SPELL_SCALE_FACTOR), 1/(Constants.SPELL_SCALE_FACTOR));
	}	
	@Override
	public void OnCollide(PhysicalEntity p) {
		if(!objectsInRange.contains(p)){
			objectsInRange.add(p);
		}
	}
	@Override
	public void Update(float delta){
		if(this.manifestion!=null&&this.manifestion.active()){
			damageDelay -= delta;
			if(damageDelay <= 0){
				for(int i = 0; i < objectsInRange.size(); i++){
					System.out.println("damaging " + objectsInRange.get(i));
					objectsInRange.get(i).damage(4);
					damageDelay = 1;
				}
			}
		} else if(this.manifestion!=null){
			objectsInRange.clear();
		}
	}
	@Override
	public void NoCollide(PhysicalEntity p) {
		objectsInRange.remove(p);
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
