package com.cigam.sigil.magic.targets;

import com.cigam.sigil.magic.MaterialDescriptor;
import com.cigam.sigil.magic.Target;
import com.cigam.sigil.materials.SelfMat;

public class Self extends Target {
	
	@Override
	public void topEvalEffect() {
	}


	@Override
	public MaterialDescriptor evalEffect() {
		return new SelfMat();
	}

}
