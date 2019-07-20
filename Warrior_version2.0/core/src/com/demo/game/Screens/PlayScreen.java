package com.demo.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.demo.game.Demo;
import com.demo.game.Scenes.Hud;
import com.demo.game.Sprites.*;
import com.demo.game.Tools.B2WorldCreator;
import com.demo.game.Tools.Collision;
import com.demo.game.Tools.WorldContactListener;
import java.util.*;


public class PlayScreen implements Screen {

    private long countDt=0;
    private float timeCount=0;
    private float tCount=0;
    private float sweetiming=0;
    private float tCount1=0;
    private float sweetiming1=0;
    private float tCount2=0;
    private float sweetiming2=0;
   public float yy=1;
    public float xx=1;
    public float zz=1;
    public float rr=1;
    private long Timer=1;
    private Demo game ;
    public Warrior player;
    public Warrior warrior1;
    public Music music;
    public Enemy enemy;
    public Demon demon;
    public Ghost ghost;
    public Ghost ghost1;

    public ArrayList<Fireball>fireball;
    public ArrayList<FireBomb>firebomb;
    public ArrayList<FireBomb>firebomb1;

    public Collision collision;
    public boolean bool;
    public boolean isHurt=false;
    public boolean enemyisHurt=false;
    public Texture mariohurting;

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
        music=Demo.assetManager.get("soundTrack.mp3",Music.class);
        music.setLooping(true);
        music.play();
        gamecam= new OrthographicCamera();
        gamePort= new FitViewport(Demo.V_Width,Demo.V_Height,gamecam);
        hud=new Hud(game.batch);
        mapLoader =new TmxMapLoader();
        map=mapLoader.load("map_l1.tmx");
        renderer= new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        bool=false;
       collision= new Collision();
       world =new World (new Vector2(0,-150),true);
       b2dr= new Box2DDebugRenderer();
       new B2WorldCreator(world,map);
       fireball=new ArrayList<Fireball>();
       firebomb=new  ArrayList<FireBomb>();
       firebomb1=new ArrayList<FireBomb>();
       enemy= new Enemy(world,this,1500);
       demon=new Demon(world,this,200);
        player= new Warrior(world,this,60);
        //warrior1= new Warrior(world,this);
        ghost=new Ghost(world,this,500);
        ghost1=new Ghost(world,this,1000);

       // world.setContactListener(new WorldContactListener());

    }


    @java.lang.Override
    public void show() {

    }
    public void handleInput(float dt){
        timeCount+=dt;
         tCount+=dt;
         sweetiming+=dt;
        tCount1+=dt;
        sweetiming1+=dt;
        tCount2+=dt;
        sweetiming2+=dt;


        if(timeCount>=1){
            Timer++;

            timeCount=0;
        }
        countDt++;

        countDt=countDt%500;
        for (Fireball fire : fireball) {
            //System.out.println(player.b2body.getPosition().x+" "+player.b2body.getPosition().y+ " "+fire.b2body.getPosition().x+ " "+fire.b2body.getPosition().y);
            if (collision.getDif(player.b2body.getPosition().x, fire.b2body.getPosition().x, player.b2body.getPosition().y, fire.b2body.getPosition().y, Collision.String.fireball, demon.attack) == 20) {

                fire.destroy = true;
                player.hurt = true;
                hud.addScore(-10);


            }


        }
        for (FireBomb fire1 : firebomb) {
            //System.out.println(player.b2body.getPosition().x+" "+player.b2body.getPosition().y+ " "+fire.b2body.getPosition().x+ " "+fire.b2body.getPosition().y);
            if (collision.getDif(player.b2body.getPosition().x, fire1.b2body.getPosition().x, player.b2body.getPosition().y, fire1.b2body.getPosition().y, Collision.String.firebomb, ghost.attack & player.combo) == 20) {

                fire1.destroy = true;
                player.hurt = true;
                hud.addScore(-10);


            }


        }
        for (FireBomb fire2: firebomb1) {
            //System.out.println(player.b2body.getPosition().x+" "+player.b2body.getPosition().y+ " "+fire.b2body.getPosition().x+ " "+fire.b2body.getPosition().y);
            if (collision.getDif(player.b2body.getPosition().x, fire2.b2body.getPosition().x, player.b2body.getPosition().y, fire2.b2body.getPosition().y, Collision.String.firebomb, ghost.attack & player.combo) == 20) {

                fire2.destroy = true;
                player.hurt = true;
                hud.addScore(-10);


            }


        }


        //System.out.println(player.hurt);
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)  && player.b2body.getLinearVelocity().y==0)
            player.b2body.applyLinearImpulse(new Vector2(0, 153f), player.b2body.getWorldCenter(), true);

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x<=36 ) {

                if(demon.b2body.getPosition().x-player.b2body.getPosition().x<=40 && !demon.Hurt)
                    System.out.println("new");

                else if(enemy.b2body.getPosition().x-player.b2body.getPosition().x<=50 && !enemy.dead )
                    System.out.println("new");
                else if(ghost .b2body.getPosition().x-player.b2body.getPosition().x<=40 && !ghost.Hurt){
                    System.out.println("new");

                }
                else if(ghost1 .b2body.getPosition().x-player.b2body.getPosition().x<=40 && !ghost1.Hurt){
                    System.out.println("new");

                }
                else
                player.b2body.applyLinearImpulse(new Vector2(21f, 0), player.b2body.getWorldCenter(), true);
            }

            if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-36 && (enemy.Hurt==true || enemy.Hurt==false) )
                player.b2body.applyLinearImpulse(new Vector2(-21f,0),player.b2body.getWorldCenter(),true);


        if(Timer%1==0 && sweetiming1>=1 && ghost.b2body.getPosition().x-player.b2body.getPosition().x>=45){
            ghost.attack=true;
            ghost.stateTimer=0;
            sweetiming1=0;
        }
        //if(demon.attack)
        // System.out.println(demon.stateTimer);

        if(tCount1>=1 &&  ghost.stateTimer>0.7&& ghost.attack==true && !ghost.Hurt && ghost.b2body.getPosition().x-player.b2body.getPosition().x<200){

            //System.out.println(demon.stateTimer);
            firebomb.add(new FireBomb(world,this,ghost.b2body.getPosition().x-10,ghost.b2body.getPosition().y-10) );
            tCount1=0;
        }

        if(Timer%1==0 && sweetiming2>=1 && ghost1.b2body.getPosition().x-player.b2body.getPosition().x>=45){
            ghost1.attack=true;
            ghost1.stateTimer=0;
            sweetiming2=0;
        }
        //if(demon.attack)
        // System.out.println(demon.stateTimer);

        if(tCount2>=1 &&  ghost1.stateTimer>0.7&& ghost1.attack==true && !ghost1.Hurt && ghost1.b2body.getPosition().x-player.b2body.getPosition().x<200){

            //System.out.println(demon.stateTimer);
            firebomb1.add(new FireBomb(world,this,ghost1.b2body.getPosition().x-10,ghost1.b2body.getPosition().y-10) );
            tCount2=0;
        }













        if(Timer%1==0 && sweetiming>=1 && demon.b2body.getPosition().x-player.b2body.getPosition().x>=45){
                demon.attack=true;
                demon.stateTimer=0;
                sweetiming=0;
            }
            //if(demon.attack)
       // System.out.println(demon.stateTimer);

        if(tCount>=1 &&  demon.stateTimer>0.7&& demon.attack==true && !demon.Hurt){

            //System.out.println(demon.stateTimer);
            fireball.add(new Fireball(world,this,demon.b2body.getPosition().x-10,demon.b2body.getPosition().y-10) );
            tCount=0;
        }


        if(Timer%15==0 && (enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=200 && !enemy.dead)
            enemy.b2body.applyLinearImpulse(new Vector2(20f,20),enemy.b2body.getWorldCenter(),true);
        if(Timer%37==0 && (enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=200 && !enemy.dead)
            enemy.b2body.applyLinearImpulse(new Vector2(9f,1),enemy.b2body.getWorldCenter(),true);
            if((enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=70 && Timer%5==0  )
                enemy.attack = true;


            else
                enemy.attack=false;
            if((enemy.b2body.getPosition().x-player.b2body.getPosition().x)<=200 && (enemy.b2body.getPosition().x-player.b2body.getPosition().x)>=70 && enemy.b2body.getLinearVelocity().x>=-25 && enemy.attack==false)
                enemy.b2body.applyLinearImpulse(new Vector2(-5f,0),enemy.b2body.getWorldCenter(),true);
       // System.out.println(player.b2body.getPosition().x+ "player");
        if(collision.getDif(player.b2body.getPosition().x,demon.b2body.getPosition().x,player.b2body.getPosition().y,demon.b2body.getPosition().y, Collision.String.Barbarian,player.attack|player.jump_attack)==40){
            System.out.println("shoot");
           yy++;

        }
        System.out.println(yy);
        if(yy>100)
        {
            demon.Hurt=true;
        }
        if(collision.getDif(player.b2body.getPosition().x,ghost.b2body.getPosition().x,player.b2body.getPosition().y,ghost.b2body.getPosition().y, Collision.String.Barbarian,player.attack|player.jump_attack)==40){
            System.out.println("shoot");
            xx++;

        }
        System.out.println(yy);
        if(xx>100)
        {
            ghost.Hurt=true;
        }
        if(collision.getDif(player.b2body.getPosition().x,ghost1.b2body.getPosition().x,player.b2body.getPosition().y,ghost1.b2body.getPosition().y, Collision.String.Barbarian,player.attack|player.jump_attack)==40){
            System.out.println("shoot");
            zz++;

        }
        System.out.println(yy);
        if(zz>100)
        {
            ghost1.Hurt=true;
        }

        if(collision.getDif(player.b2body.getPosition().x,enemy.b2body.getPosition().x,player.b2body.getLinearVelocity().y,enemy.b2body.getPosition().y,Collision.String.Goblin,player.attack)==30 && !enemy.attack)
        {

            System.out.println("baal");
            enemy.Hurt=true;
            rr++;
            //System.out.println(k);
        }
        if(rr>200)
            enemy.dead=true;



        else if(collision.getDif(player.b2body.getPosition().x,enemy.b2body.getPosition().x,player.b2body.getPosition().y,enemy.b2body.getPosition().y, Collision.String.Wizard,enemy.attack)==50) {
            {
                player.hurt = true;
                hud.addScore(-20);
            }


               // player.update(dt,isHurt);





        }









    }
     public void update(float dt){
        handleInput(dt);





if(player.b2body.getPosition().x>2000 && enemy.dead)
    game.setScreen(new WinScene(this.game));
if(hud.getScore()<=0 || hud.getTime()<=0)
    game.setScreen(new GameOverSceen(this.game));












        world.step(1/60f,9,2);
        ghost.update(dt);
        ghost1.update(dt);
        enemy.update(dt,bool,player.b2body.getPosition().x,k,enemyisHurt);
        player.update(dt,isHurt);
        demon.update(dt);

         for (Fireball fire : fireball){
             fire.update(dt);
         }
         for (FireBomb fire1 : firebomb){
             fire1.update(dt);
         }
         for (FireBomb fire2 : firebomb1){
             fire2.update(dt);
         }
        //fireball.update(dt);

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
        b2dr.render(world,gamecam.combined);
         game.batch.setProjectionMatrix(gamecam.combined);
         game.batch.begin();
         ghost.draw(game.batch);
         ghost1.draw(game.batch);
         demon.draw(game.batch);
         enemy.draw(game.batch);
        player.draw(game.batch);
        for (Fireball fire : fireball){
            fire.draw(game.batch);
        }
        for (FireBomb fire1 : firebomb){
            fire1.draw(game.batch);
        }
        for (FireBomb fire2 : firebomb1){
            fire2.draw(game.batch);
        }
       // fireball.draw(game.batch);

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
