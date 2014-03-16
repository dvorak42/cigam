package com.cigam.sigil;
import org.newdawn.slick.Graphics;
import java.util.*;


public class Drawing extends Entity{
	private ArrayList<ArrayList<Integer>> X;
	private ArrayList<ArrayList<Integer>> Y;
	
	public Drawing(){
		X = new ArrayList<ArrayList<Integer>>();
		Y = new ArrayList<ArrayList<Integer>>();
		X.add(new ArrayList<Integer>());
		Y.add(new ArrayList<Integer>());
		this.active = true;
	}
	
	public void AddPoint(int x, int y){
		X.get(X.size()-1).add(x);
		Y.get(Y.size()-1).add(y);
	}
	
	public void reset(){
		X.clear();
		Y.clear();
	}
		
	public void breakPoint(){
		X.add(new ArrayList<Integer>());
		Y.add(new ArrayList<Integer>());
	}
	
	@Override
	public void draw(Graphics g)
    {
		if(active){
			System.out.println("X: " + X.toString());
			System.out.println("Y: " + Y.toString());
			for(int i = 0; i < X.size();i++){
				for(int j = 0; j < X.get(i).size()-1;j++){
					g.drawLine(X.get(i).get(j), Y.get(i).get(j), X.get(i).get(j+1), Y.get(i).get(j+1));
				}
			}
		}
	}

	@Override
	public int maxHealth() {
		return 1;
	} 
	
}
