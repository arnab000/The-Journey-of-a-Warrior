package com.demo.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TideMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demo.game.Demo;
import com.demo.game.Scenes.Hud;
import com.demo.game.Sprites.Warrior;
import com.demo.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {


    private Demo game ;
    public Warrior player;



    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
     //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;





    public PlayScreen(Demo game)
    {
        this.game=game;

        gamecam= new OrthographicCamera();
        gamePort= new FitViewport(Demo.V_Width,Demo.V_Height,gamecam);
        hud=new Hud(game.batch);
        mapLoader =new TmxMapLoader();
        map=mapLoader.load("map_l1.tmx");
        renderer= new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

       world =new World (new Vector2(0,-150),true);
       b2dr= new Box2DDebugRenderer();
       new B2WorldCreator(world,map);
        player= new Warrior(world,this);


    }

    @java.lang.Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 150f), player.b2body.getWorldCenter(), true);

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=40)
                player.b2body.applyLinearImpulse(new Vector2(17f,0),player.b2body.getWorldCenter(),true);

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-40 )
                player.b2body.applyLinearImpulse(new Vector2(-17f,0),player.b2body.getWorldCenter(),true);




    }
     public void update(float dt){
        handleInput(dt);
        world.step(1/60f,9,2);
        player.update(dt);
       gamecam.position.x= player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
     }
    @java.lang.Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);







        renderer.render();
        //render our box debug line
        b2dr.render(world,gamecam.combined);
         game.batch.setProjectionMatrix(gamecam.combined);
         game.batch.begin();
         player.draw(game.batch);
         game.batch.end();


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();



    }

    @java.lang.Override
    public void resize(int width, int height) {
      gamePort.update(width,height);
    }

    @java.lang.Override
    public void pause() {

    }

    @java.lang.Override
    public void resume() {

    }

    @java.lang.Override
    public void hide() {

    }

    @java.lang.Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
