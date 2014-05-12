package com.cigam.sigil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.cigam.sigil.SigilGame;


public class HelpScreen implements Screen {
	SigilGame game;
	Sprite bgImage;
	Screen child;
	boolean spaceUnpressed;
	AssetManager assetManager;
	
	OrthographicCamera camera;
		
	public HelpScreen(final SigilGame game, final String bg, final Screen child) {
		this.game = game;
		this.child = child;
		spaceUnpressed = false;
		Texture texture = new Texture(Gdx.files.internal(bg));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bgImage = new Sprite(texture);
		bgImage.flip(false, true);
		
		assetManager = new AssetManager();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			spaceUnpressed = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&&spaceUnpressed){
			spaceUnpressed = false;
			game.setScreen(child);
		}
		/*
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
			wasDown = true;
		if(wasDown && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			wasDown = false;
			//System.out.println(rMenu.getValue());
		}*/
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.setProjectionMatrix(camera.combined);
		camera.update(true);
		game.batch.begin();
		bgImage.setColor(game.batch.getColor());
		bgImage.draw(game.batch);
		bgImage.setColor(Color.WHITE);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
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


