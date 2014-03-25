package com.cigam.sigil.screens;

import java.util.ArrayList;

import org.jbox2d.common.*;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.cigam.sigil.Constants;
import com.cigam.sigil.Enemy;
import com.cigam.sigil.Entity;
import com.cigam.sigil.ImmovableWall;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.SolidProjectile;
import com.cigam.sigil.Helper;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Player;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.Assets;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.graphics.TileMap;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.magic.Verb;
import com.cigam.sigil.magic.targets.MaterialRune;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.materials.Fire;

public class BattleScreen extends Screen {
	public TileMap hitmap;
	public World world;
	public BodyDef borderDef;
	public ArrayList<Entity> entities;
    //public ArrayList<SolidProjectile> fireballs;
    public ArrayList<Enemy> enemies;
    public ArrayList<PhysicalEntity> spells;
	public int fireDelay = 0;
	public int startDelay = 1000;
	public static int INIT_ENEMIES = 0;
	public ArrayList<Verb> testSpells;
	private GameContainer gc;
	private int dt;
	

	@Override
	public Screen transition(int state) {
		if(state == 0)
			INIT_ENEMIES *= 1;
		else
			INIT_ENEMIES /= 1;
		if(INIT_ENEMIES < 1)
			INIT_ENEMIES = 1;
		
		return new BattleScreen(game, this);
	}

    
	public BattleScreen(CigamGame g, Screen par)
	{
		new ImmovableWall(g, new Vec2(0f,0f), new Vec2(0f, Constants.DISPLAY_DIMS[1]));
		new ImmovableWall(g, new Vec2(0f,0f), new Vec2(Constants.DISPLAY_DIMS[0], 0f));
		new ImmovableWall(g, new Vec2(0f, Constants.DISPLAY_DIMS[1]), new Vec2(Constants.DISPLAY_DIMS[0],Constants.DISPLAY_DIMS[1]));
		new ImmovableWall(g, new Vec2(Constants.DISPLAY_DIMS[0], 0f), new Vec2(Constants.DISPLAY_DIMS[0],Constants.DISPLAY_DIMS[1]));

		game = g;
		player = game.player;
		parent = par;
		cleanRestart();
	}


	@Override
	public void init() throws SlickException {
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spells = new ArrayList<PhysicalEntity>();
		hitmap = new TileMap(0, 0);
		world = new World(new Vec2());
		testSpells = new ArrayList<Verb>();
		testSpells.add(new Create(player, game, new MaterialRune(new Fire()), null));
		//testSpells.add(new Summon(player, game, new MaterialRune(new Fire()), null));
		testSpells.add(new Create(player, game, new Summon(player, game, new MaterialRune(new Fire()), null), null));
		//testSpell = new Create(player, game, new Create(player, game, new Create(player, game, new MaterialRune(new Fire()), null), null), null);
		//testSpell = new Summon()
		for(Verb s: testSpells){
			s.topEvalEffect();
		}
		background = Assets.loadImage("art/background.png");
		restart();
	}
	
	
	@Override
	public void restart() throws SlickException {
		entities.clear();
        enemies.clear();
        spells.clear();
        player.setPosition(new Vector2f(128, 128));
        entities.add(player);
        
        int num_enemies = INIT_ENEMIES;

        for(int i = 0; i < num_enemies; i++)
        {
            DirectedImage da = new DirectedImage(Assets.loadImage("art/enemy.png"));

            Enemy enemy = new Enemy(game, this);
            enemy.setPosition(new Vector2f((int)(Math.random()*500)+100, (int)(Math.random()*500)+100));
            enemy.setImage(da);
            enemy.setDirection(Helper.randomDirection());
            entities.add(enemy);
            enemies.add(enemy);
        }
	}
	

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		super.render(gc, g);
		for(Entity e : entities)
			e.draw(g);
	}
	

    public void createFireball(Entity e, Direction dir, boolean player)
    {
        SolidProjectile f = new SolidProjectile(game, Helper.directionToAngle(dir), e, 1, 
        		Helper.v2v(Helper.directionToVector(dir)).mul(Constants.PLAYER_PROJECTILE_SPEED));
        if(e instanceof Player)
        {
        	this.player.setDirection(dir);
        }
        f.img = new DirectedImage(Assets.loadImage("art/fireball.png"));
        entities.add(f);
    }

    public void createSpellEffect(SpellEffect e){
    	entities.add(e);
    	spells.add(e);
    	game.world.step(dt/1000.0f, Constants.VELOCITY_ITERS, Constants.POSITION_ITERS);
        game.world.clearForces();
    	e.mat.OnCreate(e);
    	e.img = new DirectedImage(Assets.loadImage("art/fireball.png"));
    }
    @Override
	public void update(GameContainer gc, int dt) throws SlickException {
    	this.dt = dt;
        Input in = gc.getInput();
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
        
        playerMoveVec.normalise();
            
        player.body.applyForceToCenter(Helper.v2v(playerMoveVec.scale((int)(dt/1000f * Constants.PLAYER_ACCELERATION_FACTOR))));
        for(PhysicalEntity e : spells){
        	e.mat.Update();
        }
        //System.out.println(player.body.m_mass + "is player mass");
        //System.out.println(player.body.getLinearVelocity() + "is player velocity");
        
        if(fireDelay <= 0)
        {
        	fireDelay = Constants.FIRE_DELAY;
        	Vector2f moff = new Vector2f(in.getMouseX(), in.getMouseY());
        	Direction mFire = Helper.directionTo(player.getPosition(), moff);
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
	        else if(in.isKeyDown(Input.KEY_0))
	        	testSpells.get(0).cast();
	        else if(in.isKeyDown(Input.KEY_1))
	        	testSpells.get(1).cast();
	        else if(in.isKeyDown(Input.KEY_2))
	        	testSpells.get(2).cast();
	        else if(in.isKeyDown(Input.KEY_3))
	        	testSpells.get(3).cast();
	        else if(in.isKeyDown(Input.KEY_4))
	        	testSpells.get(4).cast();
	        else if(in.isKeyDown(Input.KEY_5))
	        	testSpells.get(5).cast();
	        else if(in.isKeyDown(Input.KEY_6))
	        	testSpells.get(6).cast();
	        else if(in.isKeyDown(Input.KEY_7))
	        	testSpells.get(7).cast();
	        else if(in.isKeyDown(Input.KEY_8))
	        	testSpells.get(8).cast();
	        else if(in.isKeyDown(Input.KEY_9))
	        	testSpells.get(9).cast();
	        else
	        	fireDelay = 0;
	        //End of creating a projectile
        }
        
        game.world.step(dt/1000.0f, Constants.VELOCITY_ITERS, Constants.POSITION_ITERS);
        game.world.clearForces();
        player.update(dt);
        fireDelay -= dt;
        

        for(Enemy e : enemies)
        	e.update(dt);
        
        if(!player.active())
        	game.transition(-1);
	}
}
