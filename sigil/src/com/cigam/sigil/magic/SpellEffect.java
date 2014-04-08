package com.cigam.sigil.magic;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.screens.AdventureScreen;

public class SpellEffect extends PhysicalEntity {
	public float duration;
	public SpellDescriptor target;
	public ArrayList<SpellDescriptor> arguments;
	public float angle;

	public SpellEffect(SigilGame game, AdventureScreen a, Sprite sprite, SpellDescriptor s){
		super(game, sprite, a, s);
		this.duration = s.duration;
		this.body.setUserData(this);
		this.target = s.target;
		this.angle = s.angle;
		this.arguments = s.arguments;
		System.out.println("Initial duration is" + duration);

	}

	public void timeStep(float dt){
		duration -= dt;
	}
	
	public void render() {
		modelOrigin = new Vector2(sprite.getWidth() / 2, sprite.getHeight() / 2);
		super.render();
	}
}
