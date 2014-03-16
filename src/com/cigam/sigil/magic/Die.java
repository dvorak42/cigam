package com.cigam.sigil.magic;

public class Die {
	private double variance;
	private double avg;
	
	public Die(double avg, double variance){
		this.avg = avg;
		this.variance = variance;
	}
	
	public Die mul(Die other) {
		return new Die(this.avg*other.getAvg(),(this.variance+other.getVariance())/2);
	}

	public String roll() {
		// Something to do with probability here, which I'm ignoring for now
		return "Die roll. TBD";
	}

	@Override
	public String toString() {
		return " average " + this.avg + " and variance " + this.variance;
	}
	
	public double getVariance() {
		return variance;
	}

	public double getAvg() {
		return avg;
	}
}
