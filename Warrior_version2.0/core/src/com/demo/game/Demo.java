package com.demo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.demo.game.Screens.PlayScreen;

public class Demo extends Game {
	public SpriteBatch batch;
	public static final int V_Height=300  ;
	public static final int V_Width=600;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
	    setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {

		super.render();

	}
	

}
