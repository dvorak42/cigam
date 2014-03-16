package com.cigam.sigil;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.TileMap;
import com.cigam.sigil.screens.BattleScreen;

public class Fireball extends Entity{
    public CigamGame game;
    public Direction direction;
    public Entity parent;
    
    public Fireball(CigamGame eg, Direction dir)
    {
        super();
        direction = dir;
        game = eg;
        this.health = Constants.FIRE_LIFE;
    }
    
    public int maxHealth() {
    	return Constants.FIRE_LIFE;
    }
    
    @Override
	public void draw(Graphics g)
    {
		if(active)
			img.draw(position.x - size.x / 2, position.y - size.y / 2, size.x, size.y, direction);
	} 
    
	@Override
	public void move(Vector2f mov, int lx, int ly, int mx, int my) {
		if(!active)
			return;
		boolean parDesc = false;
		Entity e = parent;
		for(int l = 0; l < game.player.MOLOTOV_DEPTH && !parDesc; l++)
		{
			if(e instanceof Player)
				parDesc = true;
			else if(e instanceof Fireball)
				e = ((Fireball)e).parent;
		}

		if(parDesc)
			((BattleScreen)game.current).createFireball(this, Helper.randomDirection(), true);
		position = position.add(mov);
	}
	
	public void move(Vector2f mov, int lx, int ly, int mx, int my, TileMap map) {
		move(mov, lx, ly, mx, my);
	}

}
