package com.cigam.sigil.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.cigam.sigil.Constants;
import com.cigam.sigil.CrystalShard;
import com.cigam.sigil.Enemy;
import com.cigam.sigil.Entity;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.Player;
import com.cigam.sigil.SigilContactFilter;
import com.cigam.sigil.SigilContactListener;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.SolidProjectile;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Parser;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.magic.StringLexer;
import com.cigam.sigil.materials.Backgroundium;
import com.cigam.sigil.materials.Fire;
import com.cigam.sigil.materials.SelfMat;

public class AdventureScreen implements Screen {
	public World world;
	public SigilGame game;
	public Player player;
	Stage stage;
	Skin skin;

	ShapeRenderer sr;

	OrthographicCamera camera;
	OrthographicCamera hudCamera;
	Box2DDebugRenderer debugRenderer;
	OrthogonalTiledMapRenderer mapRenderer;

	public ArrayList<Entity> entities;
	public Parser parser;
    public ArrayList<Enemy> enemies;
    public ArrayList<SpellEffect> spells;
	public int fireDelay = 0;
	public int startDelay = 1000;
	public static int INIT_ENEMIES = 1;
	public Spell[] SpellsArray;
	private int dt;
	private TiledMap map;
	public boolean paused = false;
	public Array<PhysicalEntity> toDestroy;
	private int selectedSpell;
	
	public AdventureScreen(SigilGame g)
	{
		game = g;
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spells = new ArrayList<SpellEffect>();
		toDestroy = new Array<PhysicalEntity>();
		debugRenderer = new Box2DDebugRenderer();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		camera.zoom = 1.0f;
		hudCamera = new OrthographicCamera(w, h);

		world = new World(new Vector2(), true);
		world.setContactListener(new SigilContactListener());
		world.setContactFilter(new SigilContactFilter());
		Texture playerTexture = new Texture(Gdx.files.internal("art/player.png"));
		playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		player = new Player(game, new Sprite(playerTexture), this);
		
		SpellsArray = new Spell[10];
		
		parser = new Parser(new StringLexer());
		
		SpellsArray[0] = parser.parse(player, this, "Create(fire)");
		SpellsArray[1] = parser.parse(player, this, "Create(Summon(fire - - - self))");
		SpellsArray[2] = parser.parse(player, this, "Bind(fire - - - self))");
		//SpellsArray[3] = parser.parse(player, this, "Summon(fire expand slow slow self)");
		//for(String s: spellsToTest){
			//testSpells.add(parser.parse(player, this, s));
		//}
		//testSpell = new Create(player, game, new Create(player, game, new Create(player, game, new MaterialRune(new Fire()), null), null), null);
		//testSpell = new Summon()

		//for(Spell s: SpellsArray){
			//s.evalEffect();
		//}
		
		Utils.createBounds(world, 4500, 900);
		
		map = new TmxMapLoader().load("maps/MAP.tmx");
		float tileScale = 2;
		mapRenderer = new OrthogonalTiledMapRenderer(map, tileScale, game.batch);
		
        player.setPosition(new Vector2(128, 128));
        entities.add(player);
        
        int num_enemies = INIT_ENEMIES;

        for(int i = 0; i < num_enemies; i++)
        {
    		Texture enemyTexture = new Texture(Gdx.files.internal("art/enemy.png"));
    		enemyTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	Enemy enemy = new Enemy(game, new Sprite(enemyTexture), this, new SelfMat(), player);
            entities.add(enemy);
            enemies.add(enemy);
        }

        for(MapObject wall : map.getLayers().get("WallObjects").getObjects()) {
        	if(wall instanceof PolygonMapObject) {
        		PolygonMapObject pwall = (PolygonMapObject)wall;
        		Polygon p = pwall.getPolygon();
        		Utils.createWall(world, new Vector2(p.getX() * tileScale, p.getY() * tileScale), p, tileScale);
        	}
        }

        for(MapObject goal : map.getLayers().get("Goal").getObjects()) {
        	if(goal instanceof PolygonMapObject) {
        		PolygonMapObject pgoal = (PolygonMapObject)goal;
        		Vector2 center = new Vector2();
        		center = pgoal.getPolygon().getBoundingRectangle().getCenter(center);
        		center.scl(tileScale);
        		CrystalShard c = new CrystalShard(game, new Sprite(new Texture(Gdx.files.internal("art/fireball.png"))), this, new Backgroundium(), center);
        		c.destination = c.getPosition().cpy().add(new Vector2(100, 0));
        		entities.add(c);
        	}
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
	
    public void createFireball(Entity e, float angle, boolean player)
    {
    	Texture fTexture = new Texture(Gdx.files.internal("art/fireball.png"));
    	fTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	SolidProjectile f = new SolidProjectile(game, new Sprite(fTexture), this, new Fire(), angle, e, 1, Utils.angleToVector(angle).scl(Constants.PLAYER_PROJECTILE_SPEED));

    	//this.player.setRotation(angle);

        entities.add(f);
    }
    
    public SpellEffect createSpellEffect(SpellDescriptor s) {
    	Texture texture = new Texture(Gdx.files.internal("art/fireball.png"));
    	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    	SpellEffect e = new SpellEffect(game, this, new Sprite(texture), s);
		entities.add(e);
    	spells.add(e);
    	world.step(dt/1000.0f, Constants.VELOCITY_ITERS, Constants.POSITION_ITERS);
    	s.mat.OnCreate(e, this);
		System.out.println("spell with effect value " + e.effectValue);
    	return e;
	}
    
    public void destroySpellEffect(SpellEffect s){
    	entities.remove(s);
    	spells.remove(s);
    	s.kill();
    }

	@Override
	public void render(float delta) {
		Input in = Gdx.input;

		if(!paused) {
			
		if(in.isKeyPressed(Input.Keys.P)) {
			game.pauseScreen.createdSpell = null;
			game.pauseScreen.index = selectedSpell;
			game.setScreen(game.pauseScreen);
			return;
        }
		if(in.isKeyPressed(Input.Keys.O)) {
			game.pauseScreen.createdSpell = SpellsArray[selectedSpell];
			game.pauseScreen.index = selectedSpell;
			game.setScreen(game.pauseScreen);
			return;
		}

        //Moving the player
        Vector2 playerMoveVec = new Vector2();
        if(in.isKeyPressed(Input.Keys.A))
            playerMoveVec.x--;
        if(in.isKeyPressed(Input.Keys.D))
            playerMoveVec.x++;
        if(in.isKeyPressed(Input.Keys.S))
            playerMoveVec.y--;
        if(in.isKeyPressed(Input.Keys.W))
            playerMoveVec.y++;
        if(in.isKeyPressed(Input.Keys.Q))
            player.plane = Constants.MATERIAL_PLANE;
        if(in.isKeyPressed(Input.Keys.Z))
            player.plane = Constants.ETHEREAL_PLANE;
        
        
        playerMoveVec.nor();

        player.body.applyForceToCenter(playerMoveVec.scl((int)(player.body.getMass()*Constants.PLAYER_ACCELERATION_FACTOR)), true);
        
        for(int i = 0; i < spells.size(); i++){
        	if(spells.get(i).duration > 0){
        		spells.get(i).mat.Update();
        		spells.get(i).timeStep(delta);
        	} else {
        		destroySpellEffect(spells.get(i));
        	}
        }
        
        //System.out.println(player.body.m_mass + "is player mass");
        //System.out.println(player.body.getLinearVelocity() + "is player velocity");
        
        if(fireDelay <= 0)
        {
        	fireDelay = Constants.FIRE_DELAY;
        	Vector2 moff = new Vector2(in.getX(), Gdx.graphics.getHeight()-in.getY());
        	Vector2 center = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
        	float mFire = moff.sub(center).angle();
        	float baseAngle = 45;
        	if(in.isButtonPressed(Input.Buttons.LEFT) && in.isKeyPressed(Input.Keys.Z))
        		createFireball(player, mFire, true);
        	else if(in.isKeyPressed(Input.Keys.LEFT) && in.isKeyPressed(Input.Keys.UP))
	            createFireball(player, 3*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.UP) && in.isKeyPressed(Input.Keys.RIGHT))
	            createFireball(player, 1*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.RIGHT) && in.isKeyPressed(Input.Keys.DOWN))
	            createFireball(player, 7*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.DOWN) && in.isKeyPressed(Input.Keys.LEFT))
	            createFireball(player, 5*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.LEFT))
	            createFireball(player, 4*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.RIGHT))
	            createFireball(player, 0*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.DOWN))
	            createFireball(player, 6*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.UP))
	            createFireball(player, 2*baseAngle, true);
	        else if(in.isKeyPressed(Input.Keys.SPACE) && SpellsArray[selectedSpell] != null)
	        	SpellsArray[selectedSpell].cast();
	        else if(in.isKeyPressed(Input.Keys.I) && SpellsArray[selectedSpell] != null)
	        	SpellsArray[selectedSpell] = null;
	        else {
	        	boolean cast = false;
	        	for(int i = 0; i < 10; i++) {
	        		int j = i != 9 ? i : -1;
	        		if(SpellsArray[i] != null && in.isKeyPressed(Input.Keys.NUM_1 + j)) {
	        			if(selectedSpell == i)
	        				SpellsArray[selectedSpell].cast();
	        			selectedSpell = i;
	    	        	cast = true;
	        		}
	        	}
	        	if(!cast)
	        		fireDelay = 0;
	        }
        }

        fireDelay -= delta;
		}        

        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(player.getPosition(), 0);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		
		mapRenderer.setView(camera);
		mapRenderer.render();
		game.batch.begin();
		for(Entity r : entities)
			r.render(delta);
		game.batch.end();
		
		hudCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		hudCamera.update(true);
		sr.setProjectionMatrix(hudCamera.combined);

		int spellSlotWidth = (Gdx.graphics.getWidth() - 2 * 60 - 10) / 10 - 10;
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GRAY);
		sr.rect(0, 0, Gdx.graphics.getWidth(), 128);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		sr.rect(10, 10, 50, 100);
		for(int i = 0; i < 10; i++) {
			sr.rect(70 + i * (spellSlotWidth + 10), 10, spellSlotWidth, 100);
		}
		sr.rect(Gdx.graphics.getWidth() - 60, 10, 50, 100);
		sr.end();

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		float percentHealth = 1.0f * player.health / Constants.DEFAULT_HEALTH;
		if(percentHealth < 0) {
			percentHealth = 0;
		}
		sr.rect(10, 11, 49, 1 + (int)(98.0f * percentHealth));
		sr.setColor(Color.BLUE);
		float percentMana = 1.0f * player.totalManaBound / player.totalManaCapacity;
		if(percentMana < 0) {
			percentMana = 0;
		}
		sr.rect(Gdx.graphics.getWidth() - 60, 11, 49, 1 + (int)(98.0f * percentMana));
		sr.end();
		
		for(int i = 0; i < 10; i++) {
			if(SpellsArray[i] != null) {
				sr.begin(ShapeType.Filled);
				if(i == selectedSpell)
					sr.setColor(Color.GREEN);
				else
					sr.setColor(Color.BLUE);
				sr.rect(70 + i * (spellSlotWidth + 10), 11, spellSlotWidth - 1, 99);
				sr.end();
			}
		}
		
		runPhysics(delta);
	}

	public void runPhysics(float delta) {
		debugRenderer.render(world, camera.combined);
		if(!paused) {
			world.step(1/30f, 6, 2);
			world.clearForces();
		}
		
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		
		for(Contact c : world.getContactList()) {
			if(c.getFixtureA() != null && c.getFixtureB() != null){
				if(c.getFixtureA().getBody().getUserData() instanceof PhysicalEntity && c.getFixtureB().getBody().getUserData() instanceof PhysicalEntity) {
					PhysicalEntity a = ((PhysicalEntity) c.getFixtureA().getBody().getUserData());
					PhysicalEntity b = ((PhysicalEntity) c.getFixtureB().getBody().getUserData());
					if(toDestroy.contains(a, true) || toDestroy.contains(b, true)) {
						if(a.mat != null && b.mat != null) {
							//System.out.println("collision ended");
							a.mat.NoCollide(b);
							b.mat.NoCollide(a);
						}
						if(a instanceof SpellEffect) {
							SpellEffect e = (SpellEffect)a;
							if(e.sd != null && e.sd.mat instanceof Fire)
								b.setAutoDamage(0);
						}
						if(b instanceof SpellEffect) {
							SpellEffect e = (SpellEffect)b;
							if(e.sd != null && e.sd.mat instanceof Fire)
								a.setAutoDamage(0);
						}
					}
				}
			}
		}

		for(PhysicalEntity p : toDestroy) {
			if(bodies.contains(p.body, true)) {
				world.destroyBody(p.body);
				p.body.setActive(false);
				entities.remove(p);
			}
		}
		toDestroy.clear();
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		sr = new ShapeRenderer();
		fireDelay = Constants.FIRE_DELAY;
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub

		
	}
}
