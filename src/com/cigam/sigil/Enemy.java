package com.cigam.sigil;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.screens.BattleScreen;


public class Enemy extends Entity {
	public CigamGame game;
	public BattleScreen pScreen;
	int timeout = 0;
	public int hurtTimer;
	
	public Enemy(CigamGame g, BattleScreen battleScreen){
		super();
		game = g;
		pScreen = battleScreen;
		hurtTimer = 100;
	}

    public int maxHealth() {
        return Constants.ENEMY_MAX_HEALTH;
    }
	
	//Enemy Motion
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		float startX = position.x - size.x / 2;
		float startY = position.y + size.y / 2;
		float offY = 4;
		float maxWidth = size.x;
		g.setColor(Color.red);
        g.fillRect(startX, startY, maxWidth, offY);
        g.setColor(Color.green);
        g.fillRect(startX, startY, (1.0f * health / maxHealth()) * maxWidth, offY);

	}
	
	
	@Override
	public void update(int dt) {
		super.update(dt);

		if(!active)
			return;
		
		if(hurtTimer > 0)
		{
			hurtTimer -= dt;
			return;
		}
		for(int i = 0; i < Constants.ENEMY_MOVE_SPEED * dt; i++)
		{
			Vector2f prevPosition = position.copy();
			Vector2f newPosition = position.copy();
			newPosition.add(Helper.directionToVector(direction).normalise().scale(1));
			if(pScreen.hitmap.valid(0, 0, newPosition.copy().add(this.size.copy().scale(-0.5f)), newPosition.copy().add(this.size.copy().scale(0.5f))))
				position = Helper.bound(newPosition, 0, 0, Constants.DISPLAY_DIMS[0], Constants.DISPLAY_DIMS[1]);
			else
				position = Helper.bound(prevPosition, this.size.x / 2, this.size.y / 2, Constants.DISPLAY_DIMS[0] - this.size.x / 2, Constants.DISPLAY_DIMS[1] - this.size.y / 2);
		}				
		if(!pScreen.hitmap.valid(0, 0, position.copy().add(this.size.copy().scale(-0.5f)), position.copy().add(this.size.copy().scale(0.5f))))
			position = new Vector2f(400, 300);
		
		timeout -= dt;
		if(timeout > 0)
			return;
		else
			timeout = 300;
		
		//
		
		direction = Helper.randomDirection();

		if(position.distance(game.player.position) < Constants.AGGRO_RANGE)
			direction = Helper.directionTo(position, game.player.position);
		else
		{
			direction = Helper.randomDirection();
		}
		
		//if(Math.random() < Constants.ENEMY_FIRE_RATE)
			//pScreen.createFireball(this, direction, false);
		
		
	}
}