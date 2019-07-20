package com.demo.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demo.game.Demo;
import com.demo.game.Sprites.Warrior;

public class WinScene implements Screen {

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Warrior warrior;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Demo game ;

    public WinScene(Demo game) {
        this.game=game;
        gamecam= new OrthographicCamera();
        world =new World (new Vector2(0,0),true);
        warrior= new Warrior(world,this,1570);
        gamePort= new FitViewport(Demo.V_Width,Demo.V_Height,gamecam);
        mapLoader =new TmxMapLoader();
        map=mapLoader.load("map_l2.tmx");
        renderer= new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

    }
    public void update(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
            game.setScreen(new Menu(this.game));
        warrior.update(dt,false);
        gamecam.position.x= warrior.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        warrior.draw(game.batch);
        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}