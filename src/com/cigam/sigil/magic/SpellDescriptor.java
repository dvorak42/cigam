package com.cigam.sigil.magic;
import java.util.ArrayList;

import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;

import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.Constants.Direction;

public class SpellDescriptor {
	public MaterialDescriptor mat;
	public float duration;
	public SpellDescriptor target;
	public ArrayList<SpellDescriptor> arguments;
	public Direction dir;
	public Shape shape;
	public Vec2 position;
	public SpellDescriptor(MaterialDescriptor mat, float duration,
			SpellDescriptor target, ArrayList<SpellDescriptor> arguments,
			Direction dir, Shape shape, Vec2 pos) {
		super();
		this.mat = mat;
		this.duration = duration;
		this.target = target;
		this.arguments = arguments;
		this.dir = dir;
		this.shape = shape;
		this.position = pos;
	}
}
