package com.cigam.sigil.magic;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
	public float scale;
	
	public SpellDescriptor(MaterialDescriptor mat, float duration, float scale, float effectValue,
			SpellDescriptor target, ArrayList<SpellDescriptor> arguments,
			float angle, Shape shape, Vector2 pos) {
		this.effectValue = effectValue;
		this.mat = mat;
		this.duration = duration;
		this.target = target;
		this.arguments = arguments;
		this.angle = angle;
		this.shape = shape;
		this.position = pos;
		this.scale = scale;
	}
	
	public SpellDescriptor(MaterialDescriptor m){
		effectValue = 1;
		mat = m;
		duration = Float.MAX_VALUE;
		shape = new CircleShape();
		shape.setRadius(10); 
		position = Vector2.Zero;
	}
}
