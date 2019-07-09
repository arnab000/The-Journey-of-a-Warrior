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
	public static  final float PPM=100;


	public static final short DEFAULT_BIT=1;
	public static final short WARRIOR_BIT=2;
	public static final short DOOR_BIT=4;//door bit / enemy bit
	public static final short GROUND_BIT=8;
	public static final short DESTROYED_BIT=16;


	
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
