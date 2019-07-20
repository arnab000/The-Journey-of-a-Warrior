package com.demo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.demo.game.Screens.PlayScreen;


public class Enemy_2 extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, JATTACK, GLIDDING,WALKING,HURTING,DYING}

    ;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;


    private Texture enemystanding;
    private Texture enemyattacking;
    private Texture enemydying;
    private Texture enemyhurting;
    private Texture enemywalking;



    private Animation<TextureRegion> enemyStand;
    private Animation<TextureRegion> enemyAttack;
    private Animation<TextureRegion> enemyWalk;
    private Animation<TextureRegion>enemyDie;
    private Animation<TextureRegion> enemyHurt;


    private float stateTimer;
    private  int x;
    public boolean attack;
    public boolean hurt;
    public boolean dead;
    private boolean runningRight;

    public Enemy_2(World world, PlayScreen screen,int x) {
        enemyattacking= new Texture("enemy2_attack.png");
        enemystanding= new Texture("enemy2_idle.png");
        enemydying=new Texture("enemy2_dead.png");
        enemywalking= new Texture("enemy2_walk.png");
        enemyhurting=new Texture("enemy2_hurt.png");



        this.x=x;
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        attack = false;
        hurt=false;
        dead=false;




        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0;i<10;i++){
            frames.add(new TextureRegion(enemyattacking,i*1613,0,1613,1401));
        }
        enemyAttack = new Animation(0.05f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++)
            frames.add(new TextureRegion(enemystanding, 1046*i, 0, 1046, 722));

        enemyStand = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++)
            frames.add(new TextureRegion(enemywalking, 11800/10*i, 0, 11800/10, 777));

        enemyWalk = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++)
            frames.add(new TextureRegion(enemydying, 11830/10*i, 0, 11830/10, 964));

        enemyDie = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 10; i++)
            frames.add(new TextureRegion(enemyhurting, 11920/10*i, 0, 11920/10, 929));

        enemyHurt = new Animation(0.1f, frames);
        frames.clear();


        defineEnemy();



        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setRegion(new TextureRegion(enemystanding, 0, 0, 1046, 722));

    }



    public float getbounds(char p) {
        float x;
        float y;
        currentState = getState();
        switch (currentState) {
            case STANDING:
            default:
                x = 1046/10;
                y = 722/10;

                break;

            case ATTACKING:
                x = 1613/10;
                y = 1401/10;
                break;

            case DYING:
                x=1183/10;
                y=964/10;
                break;

            case HURTING:
                x=1192/10;
                y=929/10;
                break;

            case WALKING:
                x=1180/10;
                y=777/10;
                break;


        }
        if (p == 'x')
            return x;
        else
            return y;

    }

    public float getPos(char p) {
        float x;
        float y;
        currentState = getState();
        switch (currentState) {
            case STANDING:
            default:
                x = b2body.getPosition().x - 65;
                y = b2body.getPosition().y - 22;

                break;
            case ATTACKING:
                x = b2body.getPosition().x-75;
                y = b2body.getPosition().y-32;
                break;
            case WALKING:
                x = b2body.getPosition().x-75;
                y = b2body.getPosition().y-32;
                break;
            case DYING:
                x=b2body.getPosition().x;
                y=b2body.getPosition().y-60;
        }
        if (p == 'x')
            return x;
        else
            return y;

    }

    public void handleInput() {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
//            attack = true;




    }

    public void update(float dt,int pos) {
        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setPosition(getPos('x'), getPos('y'));
        setRegion(getFrame(dt));
        if(pos<=b2body.getPosition().x)
            runningRight=false;
        else
            runningRight=true;

        handleInput();
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case STANDING:
            default:
                region = enemyStand.getKeyFrame(stateTimer, true);
                break;

            case ATTACKING:
                region = enemyAttack.getKeyFrame(stateTimer, true);

                break;

            case HURTING:
                region=enemyHurt.getKeyFrame(stateTimer,true);
                break;

            case DYING:
                region=enemyDie.getKeyFrame(stateTimer);
                break;

            case WALKING:
                region= enemyWalk.getKeyFrame(stateTimer,true);
                break;

        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        if (attack == true && stateTimer > 5)
            attack = false;



        return region;


    }

    public State getState() {
        if(dead==true)
            return State.DYING;
        else if(hurt==true)
            return State.HURTING;
        else if (attack == true )
            return State.ATTACKING;
        else if(b2body.getLinearVelocity().x!=0)
            return State.WALKING;
        else

            return State.STANDING;


    }

    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,50);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0,20);
        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}