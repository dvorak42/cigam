package com.cigam.sigil.magic;

public class Area {
	public static enum baseArea{
		CIRCLE, TRIANGLE, SQUARE
		}	
	
	//By default, height is away from caster and width is left-right
	private double area;
	private double height;
	private double width;
	private double rotation;
	private baseArea shape;
	
	public Area(baseArea shape, double area) {
		this.shape = shape;
		this.area = area;
		rotation = 0;
		switch(shape){
		case CIRCLE:
			height = Math.sqrt((area/Math.PI))*2;
			width = height;
			break;
		case TRIANGLE:
			height = Math.sqrt(3)*area/2.0;
			width = 2*Math.sqrt(area)/Math.pow(3, 0.25);
			break;
		case SQUARE:
			height = Math.sqrt(area);
			width = height;
		}
	}
	public double getForwardLength(){
		return this.height;
	}
	public double getForwardAngle(){
		return this.rotation;
	}
	@Override
	public String toString() {
		switch(this.shape){
		case CIRCLE:
			return " an elipse of radii " + this.height + " and " + this.width;
		case TRIANGLE:
			return " a code of length " + this.height + " and width " + this.width;
		case SQUARE:
			return " a rectangle of length " + this.height + " and width " + this.width;
		}
		return "Error, invalid area type";
	}
	public void stretch(double i) {
		this.height = this.height>=this.width? this.height*i: this.height/i; 
		this.width = this.width>this.height? this.width*i: this.width/i; 
	} 
	
}
