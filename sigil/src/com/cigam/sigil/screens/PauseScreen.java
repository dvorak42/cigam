package com.cigam.sigil.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.cigam.sigil.SigilGame;

public class PauseScreen implements Screen {
	SigilGame game;
	Screen parent;
	Sprite pauseImage;
	Stage stage;
	Skin skin;
	String spellString;
	TextField spellInput;

	public PauseScreen(final SigilGame game, final Screen parent) {
		this.game = game;
		this.parent = parent;
		Texture texture = new Texture(Gdx.files.internal("art/screens/pause.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin();
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		// Trying to make a textfield
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.fontColor = Color.BLACK;
		skin.add("default", textFieldStyle);
		spellInput = new TextField("Enter Spell", skin);

		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		//table.setSize(260, 195);
		table.setPosition(0, 0);
		//table.align(Align.right | Align.bottom);

		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton resume = new TextButton("Resume Game", skin);
		table.add(resume);
		table.row();
		table.add(spellInput);
		
		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		resume.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
					//System.out.println("Clicked! Is checked: " + resume.isChecked());
					game.setScreen(parent);
			}
		});
		
		spellInput.setTextFieldListener(new TextFieldListener() {
			public void keyTyped (TextField textField, char key) {
				if (key == '\n') {
					spellString = spellInput.getMessageText();
					System.out.println(spellString);
				}
			}
		});
		
		pauseImage = new Sprite(texture);
	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.gameScreen.paused = true;
		game.gameScreen.render(delta);
		game.gameScreen.paused = false;

		game.hudBatch.begin();
		ShapeRenderer sr = new ShapeRenderer();
		sr.begin(ShapeType.Filled);
		sr.setColor(new Color(0, 0, 0, 0.4f));
		//sr.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sr.end();
		pauseImage.draw(game.hudBatch);
		game.hudBatch.end();
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);	
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			game.setScreen(parent);
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
		stage.dispose();
		skin.dispose();
	}
}
