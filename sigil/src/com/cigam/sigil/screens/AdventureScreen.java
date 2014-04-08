package com.cigam.sigil.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cigam.sigil.SigilContactListener;
import com.cigam.sigil.Constants;
import com.cigam.sigil.Enemy;
import com.cigam.sigil.Entity;
import com.cigam.sigil.PhysicalEntity;
import com.cigam.sigil.SigilGame;
import com.cigam.sigil.SolidProjectile;
import com.cigam.sigil.Player;
import com.cigam.sigil.Utils;
import com.cigam.sigil.magic.Spell;
import com.cigam.sigil.magic.SpellDescriptor;
import com.cigam.sigil.magic.SpellEffect;
import com.cigam.sigil.magic.Verb;
import com.cigam.sigil.magic.targets.MaterialRune;
import com.cigam.sigil.magic.targets.Self;
import com.cigam.sigil.magic.verbs.Bind;
import com.cigam.sigil.magic.verbs.Create;
import com.cigam.sigil.magic.verbs.Summon;
import com.cigam.sigil.materials.Fire;
import com.cigam.sigil.materials.SelfMat;

public class AdventureScreen implements Screen {
	public World world;
	SigilGame game;
	Player player;
	Stage stage;
	Skin skin;
	
	OrthographicCamera camera;
	Box2DDebugRenderer debugRenderer;
	OrthogonalTiledMapRenderer mapRenderer;

	public ArrayList<Entity> entities;
    public ArrayList<Enemy> enemies;
    public ArrayList<SpellEffect> spells;
	public int fireDelay = 0;
	public int startDelay = 1000;
	public static int INIT_ENEMIES = 5;
	public ArrayList<Verb> testSpells;
	private int dt;
	private TiledMap map;
	private Sprite background;
	public boolean paused = false;
	
	public AdventureScreen(SigilGame g)
	{
		game = g;
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		spells = new ArrayList<SpellEffect>();
		debugRenderer = new Box2DDebugRenderer();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(w, h);
		camera.zoom = 1.0f;

		world = new World(new Vector2(), true);
		world.setContactListener(new SigilContactListener());
		
		Texture playerTexture = new Texture(Gdx.files.internal("art/player.png"));
		playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		player = new Player(game, new Sprite(playerTexture), this);

		testSpells = new ArrayList<Verb>();
		CircleShape c = new CircleShape();
		c.setRadius(10);
		testSpells.add(new Create(player, this, new MaterialRune(new SpellDescriptor(new Fire(), 8, 1, null, null, 0, c, Vector2.Zero)), null));
		ArrayList<Spell> args = new ArrayList<Spell>();
		args.add(new MaterialRune(new SpellDescriptor(new Fire(), 8, 1, null, null, 0, c, Vector2.Zero)));
		testSpells.add(new Create(new Summon(player, this, new Self(), (ArrayList<Spell>) args.clone()), null));
		testSpells.add(new Bind(player, this, new Self(), (ArrayList<Spell>) args.clone()));

		//testSpell = new Create(player, game, new Create(player, game, new Create(player, game, new MaterialRune(new Fire()), null), null), null);
		//testSpell = new Summon()

		for(Verb s: testSpells){
			s.topEvalEffect();
		}
		
		Utils.createBounds(world, 500, 500);
		
		map = new TmxMapLoader().load("maps/testmap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1.0f/16, game.batch);
		
		Texture bTexture = new Texture(Gdx.files.internal("art/background.png"));
		bTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background = new Sprite(bTexture);
		
        player.setPosition(new Vector2(128, 128));
        entities.add(player);
        
        int num_enemies = INIT_ENEMIES;

        for(int i = 0; i < num_enemies; i++)
        {
    		Texture enemyTexture = new Texture(Gdx.files.internal("art/enemy.png"));
    		enemyTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        	Enemy enemy = new Enemy(game, new Sprite(enemyTexture), this, new SelfMat());
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
        world.clearForces();
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
	        else if(in.isKeyPressed(Input.Keys.NUM_0))
	        	testSpells.get(0).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_1))
	        	testSpells.get(1).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_2))
	        	testSpells.get(2).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_3))
	        	testSpells.get(3).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_4))
	        	testSpells.get(4).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_5))
	        	testSpells.get(5).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_6))
	        	testSpells.get(6).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_7))
	        	testSpells.get(7).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_8))
	        	testSpells.get(8).cast();
	        else if(in.isKeyPressed(Input.Keys.NUM_9))
	        	testSpells.get(9).cast();
	        else
	        	fireDelay = 0;
        }
        
        fireDelay -= delta;
		}        
        runPhysics(delta);
	
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.position.set(player.getPosition(), 0);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		mapRenderer.setView(camera);
		mapRenderer.render();

		game.batch.begin();
		background.draw(game.batch);
		for(Entity r : entities)
			r.render();
		game.batch.end();
		
		
		runPhysics(delta);
	}

	public void runPhysics(float delta) {
		debugRenderer.render(world, camera.combined);
		if(!paused) {
		world.step(1/60f, 6, 2);
		world.clearForces();
		}
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
