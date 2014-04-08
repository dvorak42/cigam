package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

public class SpellDescriptor {
	public MaterialDescriptor mat;
	public float duration;
	public SpellDescriptor target;
	public ArrayList<SpellDescriptor> arguments;
	public float angle;
	public Shape shape;
	public Vector2 position;
	public float effectValue;
	
	public SpellDescriptor(MaterialDescriptor mat, float duration, float effectValue,
			SpellDescriptor target, ArrayList<SpellDescriptor> arguments,
			float angle, Shape shape, Vector2 pos) {
		super();
		this.effectValue = effectValue;
		this.mat = mat;
		this.duration = duration;
		this.target = target;
		this.arguments = arguments;
		this.angle = angle;
		this.shape = shape;
		this.position = pos;
	}
}
