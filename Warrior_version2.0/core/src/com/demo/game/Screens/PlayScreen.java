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
import com.demo.game.Sprites.Enemy;
import com.demo.game.Sprites.Warrior;
import com.demo.game.Tools.B2WorldCreator;
import com.demo.game.Tools.Collision;
import com.demo.game.Tools.WorldContactListener;


public class PlayScreen implements Screen {

    private long countDt=0;
    private float timeCount=0;
    private long Timer=1;
    private Demo game ;
    public Warrior player;
    public Warrior warrior1;
    public Enemy enemy;
    public Collision collision;
    public boolean bool;
    public boolean isHurt=false;
    public boolean enemyisHurt=false;

    public int k=1;




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
        map=mapLoader.load("Gothic_map.tmx");
        renderer= new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        bool=false;
       collision= new Collision();
       world =new World (new Vector2(0,-150),true);
       b2dr= new Box2DDebugRenderer();
       new B2WorldCreator(world,map);
       enemy= new Enemy(world,this,200);
        player= new Warrior(world,this);
        warrior1= new Warrior(world,this);
       // world.setContactListener(new WorldContactListener());

    }


    @java.lang.Override
    public void show() {

    }
    public void handleInput(float dt){
        timeCount+=dt;
        if(timeCount>=1){
            Timer++;
            timeCount=0;
        }
        countDt++;
        countDt=countDt%500;
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 153f), player.b2body.getWorldCenter(), true);

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=36 &&( ((enemy.b2body.getPosition().x-player.b2body.getPosition().x)>=35 ||enemy.Hurt==true)||((enemy.b2body.getPosition().x-player.b2body.getPosition().x)>=35 ||enemy.Hurt==true)))
                player.b2body.applyLinearImpulse(new Vector2(21f,0),player.b2body.getWorldCenter(),true);

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-36 )
                player.b2body.applyLinearImpulse(new Vector2(-21f,0),player.b2body.getWorldCenter(),true);
            if((enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=70 && Timer%5==0 && enemy.Hurt==false  )
                bool = true;


            else
                bool=false;
            if((enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=200 && (enemy.b2body.getPosition().x-player.b2body.getPosition().x)>=70 && enemy.b2body.getLinearVelocity().x>=-25 && enemy.attack==false)
                enemy.b2body.applyLinearImpulse(new Vector2(-5f,0),player.b2body.getWorldCenter(),true);
            if(Timer%15==0 && (enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=200)
                enemy.b2body.applyLinearImpulse(new Vector2(20f,20),player.b2body.getWorldCenter(),true);

        if (collision.getDif(player.b2body.getPosition().x,enemy.b2body.getPosition().x, Collision.String.Barbarian,enemy.attack)==0)
            isHurt=false;
        else if(collision.getDif(player.b2body.getPosition().x,enemy.b2body.getPosition().x, Collision.String.Barbarian,enemy.attack)==20) {

                isHurt = true;

               // player.update(dt,isHurt);





        }


        if(collision.getDif(player.b2body.getPosition().x,enemy.b2body.getPosition().x,Collision.String.Barbarian,player.attack)==20)
        {

            enemyisHurt=true;
            System.out.println(k);
        }
        else
            enemyisHurt=false;







    }
     public void update(float dt){
        handleInput(dt);
        world.step(1/60f,9,2);
        enemy.update(dt,bool,player.b2body.getPosition().x,k,enemyisHurt);
        player.update(dt,isHurt);

         //System.out.println(enemy.b2body.getPosition().x);
        // System.out.println(player.b2body.getPosition().x);

        hud.update(dt);
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
       // b2dr.render(world,gamecam.combined);
         game.batch.setProjectionMatrix(gamecam.combined);
         game.batch.begin();

         enemy.draw(game.batch);
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
