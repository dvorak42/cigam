package com.cigam.sigil.screens;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.cigam.sigil.CigamContactListener;
import com.cigam.sigil.Constants;
import com.cigam.sigil.Enemy;
import com.cigam.sigil.Entity;
import com.cigam.sigil.ImmovableWall;
import com.cigam.sigil.MaterialDescriptor;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.SolidProjectile;
import com.cigam.sigil.Helper;
import com.cigam.sigil.CigamGame;
import com.cigam.sigil.Player;
import com.cigam.sigil.Constants.Direction;
import com.cigam.sigil.graphics.Assets;
import com.cigam.sigil.graphics.DirectedImage;
import com.cigam.sigil.graphics.TileMap;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.magic.Verb;
import com.cigam.sigil.magic.targets.MaterialRune;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.materials.Fire;
import com.cigam.sigil.materials.SelfMat;

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
	private TiledMap map;

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
		//new ImmovableWall(g, new Vec2(0f,0f), new Vec2(0f, Constants.DISPLAY_DIMS[1]));
		//new ImmovableWall(g, new Vec2(0f,0f), new Vec2(Constants.DISPLAY_DIMS[0], 0f));
		//new ImmovableWall(g, new Vec2(0f, Constants.DISPLAY_DIMS[1]), new Vec2(Constants.DISPLAY_DIMS[0],Constants.DISPLAY_DIMS[1]));
		//new ImmovableWall(g, new Vec2(Constants.DISPLAY_DIMS[0], 0f), new Vec2(Constants.DISPLAY_DIMS[0],Constants.DISPLAY_DIMS[1]));
		game = g;
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
		world.setContactListener(new CigamContactListener());
		BodyDef bd = new BodyDef();
		bd.active = true;
		bd.type = BodyType.DYNAMIC;
		bd.userData = this;
		bd.angle = 0;
		bd.awake = true;
		bd.position = new Vec2(0, 0);
		bd.fixedRotation = true;
		bd.linearDamping = 1f;
		FixtureDef fd = new FixtureDef();
		fd.filter.groupIndex = -1;
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(26, 28);
		fd.shape = ps;
		fd.density = 0.1f;
		player = new Player(this, new SelfMat(), bd, new FixtureDef[]{fd});
		
		player.img = new DirectedImage(Assets.loadImage("art/player.png"));
		
		testSpells = new ArrayList<Verb>();
		CircleShape c = new CircleShape();
		c.setRadius(10);
		SpellDescriptor FireRune = new SpellDescriptor(new Fire(), 10, null, null, Direction.SOUTH, c, new Vec2(0,0));
		testSpells.add(new Create(player, this, new MaterialRune(FireRune), null));
		//testSpells.add(new Summon(player, game, new MaterialRune(new Fire()), null));
		testSpells.add(new Create(new Summon(player, this, new MaterialRune(FireRune), null), null));
		//testSpell = new Create(player, game, new Create(player, game, new Create(player, game, new MaterialRune(new Fire()), null), null), null);
		//testSpell = new Summon()
		for(Verb s: testSpells){
			s.topEvalEffect();
		}
		map = Assets.loadMap("maps/testmap.tmx");
		//background = Assets.loadImage("art/background.png");

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
        /*
        int groups = map.getObjectGroupCount();
        for(int i = 0; i < groups; i++){
        	int objs = map.getObjectCount(i);
        	for(int j = 0; j < objs; j++){
        		int x = map.getObjectX(i, j);
        		int y = map.getObjectY(i, j);
        		int height = map.getObjectHeight(i, j);
        		int width  = map.getObjectWidth(i, j);
        		String material = map.getObjectProperty(i, j, "material", "backgroundium");
        		MaterialDescriptor m = MaterialDescriptor.strToMats.get(material);
        		BodyDef b = new BodyDef();
        		b.active = true;
        		b.awake = true;
        		b.type = BodyType.STATIC;
        		b.position = new Vec2(x,y);
        		FixtureDef f = new FixtureDef();
        		PolygonShape psd = new PolygonShape();
        		psd.setAsBox(height, width);
        		f.shape = psd;
        		f.density = 1.0f;
        		PhysicalEntity p = new PhysicalEntity(world, m, b, new FixtureDef[]{f});
        		p.setImage(new DirectedImage(Assets.loadImage("art/fireball.png")));
        		entities.add(p);
        	}
        }*/
	}
	

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		int dx = Math.round((gc.getWidth() / 2 - player.getPosition().x));
		int dy = Math.round((gc.getHeight() / 2 - player.getPosition().y));
		g.translate(dx, dy);
		super.render(gc, g);
		map.render(0, 0);
		for(Entity e : entities) {
			e.draw(g);
		}
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
    
    public void createSpellEffect(SpellDescriptor s) {
		SpellEffect e = new SpellEffect(world,s);
		entities.add(e);
    	spells.add(e);
    	world.step(dt/1000.0f, Constants.VELOCITY_ITERS, Constants.POSITION_ITERS);
        world.clearForces();
    	e.img = new DirectedImage(Assets.loadImage("art/fireball.png"));
    	s.mat.OnCreate(e, this);
		
	}
    @Override
	public void update(GameContainer gc, int dt) throws SlickException {
    	this.dt = dt;
        Input in = gc.getInput();
        if(in.isKeyDown(Input.KEY_P)){
        	game.transition(Constants.PAUSE_STATE);
        	return;
        }
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
        
        world.step(dt/1000.0f, Constants.VELOCITY_ITERS, Constants.POSITION_ITERS);
        world.clearForces();
        player.update(dt);
        fireDelay -= dt;
        

        for(Enemy e : enemies)
        	e.update(dt);

        if(!player.active())
        	game.transition(-1);
	}
}
