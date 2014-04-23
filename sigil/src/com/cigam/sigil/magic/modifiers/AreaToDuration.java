package com.cigam.sigil.magic.modifiers;

import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;

public class AreaToDuration extends Spell{
	private SpellDescriptor toModify;
	
	@Override
	public SpellDescriptor evalEffect() {
		toModify = target.evalEffect();
		toModify.duration*=2;
		toModify.shape.setRadius((float) (toModify.shape.getRadius()/(Math.sqrt(2))));
/*		if(toModify.mat.image!=null){
			for(ParticleEmitter e: toModify.mat.image.getEmitters()){
				e.getSpawnHeight().setHigh((float) (e.getSpawnHeight().getHighMin()/Math.sqrt(2)), (float) (e.getSpawnHeight().getHighMax()/Math.sqrt(2)));
			}
		}*/
		this.gui = Utils.makeRuneGui(Utils.classesToIconPaths.get(this.getClass()));
		return toModify;
	}

	@Override
	public void cast() {
		screen.createSpellEffect(toModify);
	}

}
