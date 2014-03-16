package com.cigam.sigil.screens;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants;
import com.cigam.sigil.Drawing;
import com.cigam.sigil.Enemy;
import com.cigam.sigil.Entity;
import com.cigam.sigil.Fireball;
import com.cigam.sigil.Helper;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Player;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.Assets;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.graphics.TileMap;

public class BattleScreen extends Screen {
	public TileMap hitmap;

	public ArrayList<Entity> entities;
    public ArrayList<Fireball> fireballs;
    public ArrayList<Enemy> enemies;
    public Drawing drawing;
	public int fireDelay = 0;
	public int molotovDelay = 0;
	public int startDelay = 1000;
	Image enemyImage = null;
	public static int INIT_ENEMIES = 8;
	private boolean zPressed;

	@Override
	public Screen transition(int state) {
		if(state == 0)
			INIT_ENEMIES *= 2;
		else
			INIT_ENEMIES /= 2;
		if(INIT_ENEMIES < 1)
			INIT_ENEMIES = 1;
		
		return new BattleScreen(game, this);
	}

    
	public BattleScreen(CigamGame g, Screen par)
	{
		game = g;
		player = game.player;
		parent = par;
		cleanRestart();
	}


	@Override
	public void init() throws SlickException {
		entities = new ArrayList<Entity>();
		fireballs = new ArrayList<Fireball>();
		enemies = new ArrayList<Enemy>();
		hitmap = new TileMap(0, 0);
		drawing = new Drawing();
		background = Assets.loadImage("art/background.png");
		restart();
	}
	
	
	@Override
	public void restart() throws SlickException {
		entities.clear();
        fireballs.clear();
        enemies.clear();
        player.resetHealth();
        player.position = new Vector2f(player.size.copy().scale(2));
        player.direction = Direction.SOUTH;
        entities.add(player);
		entities.add(drawing);
        
        enemyImage = Assets.loadImage("art/enemy.png");
        int num_enemies = INIT_ENEMIES;

        for(int i = 0; i < num_enemies; i++)
        {
            DirectedImage da = new DirectedImage(Assets.loadImage("art/enemy.png"));

            Enemy enemy = new Enemy(game, this);
            enemy.position = new Vector2f((int)(Math.random()*500)+100, (int)(Math.random()*500)+100);
            enemy.img = da;
            enemy.direction = Helper.randomDirection();
            enemy.size = new Vector2f(Constants.TILE_SIZE * 0.7f, Constants.TILE_SIZE * 0.7f);
            if(!hitmap.valid(0, 0, enemy.position.add(enemy.size.copy().scale(-0.5f)), enemy.position.add(enemy.size.copy().scale(0.5f))))
            	i--;
            else
            {
            	entities.add(enemy);
            	enemies.add(enemy);
            }
        }
	}
	

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		super.render(gc, g);
		for(Entity e : entities)
			e.draw(g);
	}
	

    public void updateRangedCollisions(int dt)
    {
        ArrayList<Entity> trash = new ArrayList<Entity>();

        for (Fireball f: fireballs)
        {
        	if(!f.active())
        		continue;
            for(Entity e : entities)
            {
            	if(!e.active())
            		continue;
            	if(e == f || e == f.parent)
            		continue;
                if(f.position.distance(e.position) < 18 && f.parent != e)
                {
                    f.kill();
                    if(!(f.parent instanceof Enemy && e instanceof Enemy))
                    {
                        if(e instanceof Enemy) {
                    		((Enemy)e).hurtTimer = 1000;
                            e.damage(Constants.DEFAULT_DAMAGE);
                        }
                        if(e instanceof Player && f.parent instanceof Enemy)
                    	{
                    		((Player)e).hurtTimer = 1000;
                            e.damage(Constants.DEFAULT_DAMAGE);
                        }
                    }
                }
            }
            f.damage(dt);
        }
        for(Entity e : entities)
            if(!e.active() && e.deathTimer <= 0)
                trash.add(e);
        for(Entity e : trash)
        {
            entities.remove(e);
            if(e instanceof Enemy)
            	enemies.remove(e);
            if(e instanceof Fireball)
            	fireballs.remove(e);
        }
        
    }
	
	public void updateSpriteCollisions(int dt) {
		for(Enemy enemy : enemies) {
			if(enemy.position.distance(player.position) < 32 && player.invulnerable <= 0 && enemy.deathTimer <= 0 && enemy.active())
			{
				player.damage(Constants.DEFAULT_DAMAGE);
				player.invulnerable = 2000;
			}
		}
		player.invulnerable-=dt;
	}
        
    public void createFireball(Entity e, Direction dir, boolean player)
    {
        Fireball f = new Fireball(game, dir);
        if(e instanceof Player)
	{
        	this.player.direction = dir;
        }
	f.position = e.position.copy().add(Helper.directionToVector(dir).scale(16));
	f.size = new Vector2f(Constants.TILE_SIZE * .25f, Constants.TILE_SIZE * .25f);
        f.parent = e;
			f.img = new DirectedImage(Assets.loadImage("art/fireball.png"));
		fireballs.add(f);
        entities.add(f);
    }

	@Override
	public void update(GameContainer gc, int dt) throws SlickException {
        Input in = gc.getInput();
        // drawing
        if(in.isMousePressed(0) && in.isKeyDown(Input.KEY_Z)){
        	System.out.println("X: " + in.getAbsoluteMouseX() + " Y: " + in.getAbsoluteMouseY());
        	drawing.AddPoint(in.getAbsoluteMouseX(), in.getAbsoluteMouseY());
        	zPressed = true;
        }
        if(!in.isKeyDown(Input.KEY_Z) && zPressed){
        	drawing.breakPoint();
        	zPressed = false;
        }
        if(in.isKeyPressed(Input.KEY_Q))
        	drawing.reset();
        
		if(startDelay > 0)
		{
			startDelay -= dt;
			return;
		}
		
        //Moving the player
        Vector2f playerMoveVec = new Vector2f();
        if(in.isKeyDown(Input.KEY_A))
            playerMoveVec.x--;
        if(in.isKeyDown(Input.KEY_D))
            playerMoveVec.x++;
        if(in.isKeyDown(Input.KEY_S))
            playerMoveVec.y++;
        if(in.isKeyDown(Input.KEY_W))
            playerMoveVec.y--;
        if(in.isKeyDown(Input.KEY_ADD))
        	player.MOLOTOV_DEPTH++;
        if(in.isKeyDown(Input.KEY_SUBTRACT))
        	player.MOLOTOV_DEPTH--;
        if(in.isKeyDown(Input.KEY_EQUALS))
        	player.MOLOTOV_DEPTH = 0;
        
        playerMoveVec.normalise();
            
        player.move(playerMoveVec.scale((int)(dt * Constants.PLAYER_MOVE_SPEED)), 0, 0, gc.getWidth(), gc.getHeight(), hitmap);
        //End of moving the player
        
        if(fireDelay <= 0)
        {
        	fireDelay = Constants.FIRE_DELAY;
        	Vector2f moff = new Vector2f(in.getMouseX(), in.getMouseY());
        	Direction mFire = Helper.directionTo(player.position, moff);
        	if(in.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)&&in.isKeyDown(Input.KEY_Z))
        		createFireball(player, mFire, true);
        	else if(in.isKeyDown(Input.KEY_LEFT) && in.isKeyDown(Input.KEY_UP))
	            createFireball(player, Direction.NORTH_WEST, true);
	        else if(in.isKeyDown(Input.KEY_UP) && in.isKeyDown(Input.KEY_RIGHT))
	            createFireball(player, Direction.NORTH_EAST, true);
	        else if(in.isKeyDown(Input.KEY_RIGHT) && in.isKeyDown(Input.KEY_DOWN))
	            createFireball(player, Direction.SOUTH_EAST, true);
	        else if(in.isKeyDown(Input.KEY_DOWN) && in.isKeyDown(Input.KEY_LEFT))
	            createFireball(player, Direction.SOUTH_WEST, true);
	        else if(in.isKeyDown(Input.KEY_LEFT))
	            createFireball(player, Direction.WEST, true);
	        else if(in.isKeyDown(Input.KEY_RIGHT))
	            createFireball(player, Direction.EAST, true);
	        else if(in.isKeyDown(Input.KEY_DOWN))
	            createFireball(player, Direction.SOUTH, true);
	        else if(in.isKeyDown(Input.KEY_UP))
	            createFireball(player, Direction.NORTH, true);
	        else
	        	fireDelay = 0;
	        //End of creating a projectile
        }
        
        player.update(dt);
        fireDelay -= dt;
        molotovDelay -= dt;
        //Move fireballs across the screen
        for(int i = 0; i < fireballs.size(); i++)
        {
        	Fireball f = fireballs.get(i);
            Vector2f projMoveVec = Helper.directionToVector(f.direction);
            projMoveVec.normalise();
			if(f.parent instanceof Player)
				f.move(projMoveVec.scale(dt * Constants.PLAYER_PROJECTILE_SPEED), 0, 0, gc.getWidth(), gc.getHeight());
			else
				f.move(projMoveVec.scale(dt * Constants.ENEMY_PROJECTILE_SPEED), 0, 0, gc.getWidth(), gc.getHeight());
        }
        

        for(Enemy e : enemies)
        	e.update(dt);
        
        this.updateRangedCollisions(dt);
		
		this.updateSpriteCollisions(dt);
        
        if(!player.active())
        	game.transition(-1);
        else if(enemies.size() == 0)
            game.transition(0);
	}
}
