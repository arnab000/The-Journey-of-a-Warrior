package com.demo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.demo.game.Demo;
import com.demo.game.Screens.PlayScreen;

public class Warrior extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, JATTACK, GLIDDING,HURT}

    ;
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private Texture mariostanding;
    private Texture marioruning;
    private Texture marioJumpingUp;
    private Texture marioFalling;
    private Texture marioattacking;
    private Texture mariocomboattacking;
    private Texture mariosliding;
    private Texture mariohurting;


    private Animation<TextureRegion> marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJumpUp;
    private Animation<TextureRegion> marioFall;
    private Animation<TextureRegion> marioAttack;
    private Animation<TextureRegion> marioComboAttack;
    private Animation<TextureRegion> marioSLIDE;
    private Animation<TextureRegion>marioHurt;


    private float stateTimer;
    public boolean jump_attack;
    public boolean attack;
    private  boolean hurt;
    private boolean combo;
    public boolean runningRight;


    public Warrior(World world, PlayScreen screen) {
        mariostanding = new Texture("gothic-hero-idle.png");
        marioruning = new Texture("gothic-hero-run.png");
        marioJumpingUp = new Texture("gothic-hero-jump.png");
        marioFalling = new Texture("gothic-hero-jump.png");
        marioattacking = new Texture("gothic-hero-attack.png");
        mariocomboattacking = new Texture("gothic-hero-jump-attack.png");
        mariosliding = new Texture("gothic-hero-crouch.png");
        mariohurting= new Texture("gothic-hero-hurt.png");



        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        attack = false;
        combo = false;
        hurt=false;
        jump_attack=false;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++)
            frames.add(new TextureRegion(mariostanding, i * 152/4, 0, 152/4, 48));
        marioStand = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 12; i++)
            frames.add(new TextureRegion(marioruning, i * 792/12, 0, 792/12, 48));
        marioRun = new Animation(0.1f, frames);

        frames.clear();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(marioJumpingUp, i * 305 / 5, 0, 305/5, 77));
        marioJumpUp = new Animation(0.2f, frames);
        frames.clear();
        for (int i = 3; i < 5; i++)
            frames.add(new TextureRegion(marioFalling, i * 305/5, 0, 305 / 5, 77));
        marioFall = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(marioattacking, i * 576/6, 0, 576/6, 48));
        marioAttack = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(mariocomboattacking, i * 504/6, 0, 504/6, 80));
        marioComboAttack = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(mariosliding, i * 144 / 3, 0, 144 / 3, 48));
        marioSLIDE = new Animation(0.05f, frames);
        frames.clear();

        for (int i = 0; i <3; i++)
            frames.add(new TextureRegion(mariohurting, i * 144/3, 0, 144/3, 48));
        marioHurt = new Animation(0.25f, frames);
        frames.clear();

        defineWarrior();

        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setRegion(new TextureRegion(mariostanding, 0, 0, 232, 439));


    }

    public float getbounds(char p) {
        float x;
        float y;
        currentState = getState();
        switch (currentState) {
            case STANDING:
            default:
                x = 152/4;
                y = 48;

                break;
            case RUNNING:
                x = 792/12 ;
                y = 48;

                break;
            case JUMPING:
                x = 305/5;
                y = 77;
                break;

            case FALLING:
                x = 305/5;
                y = 77;
                break;
            case ATTACKING:
                x = 576/6;
                y = 48;
                break;
            case JATTACK:
                x = 504/6;
                y = 80;
                break;

            case HURT:
            case GLIDDING:
                x=144/3;
                y=48;
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
                x = b2body.getPosition().x - 20;
                y = b2body.getPosition().y - 7;

                break;
            case ATTACKING:
                x = b2body.getPosition().x - 40;
                y = b2body.getPosition().y - 12;
                break;
        }
        if (p == 'x')
            return x;
        else
            return y;

    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            attack = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            combo = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.W))
            jump_attack= true;


    }

    public void update(float dt,boolean bool) {
        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setPosition(getPos('x'), getPos('y'));

        setRegion(getFrame(dt));
        //System.out.println(bool);
        hurt=bool;
        handleInput();
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case STANDING:
            default:
                region = marioStand.getKeyFrame(stateTimer, true);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = marioJumpUp.getKeyFrame(stateTimer);
                break;
            case FALLING:
                region = marioFall.getKeyFrame(stateTimer);
                break;
            case ATTACKING:
                region = marioAttack.getKeyFrame(stateTimer, true);

                break;
            case JATTACK:
                region = marioComboAttack.getKeyFrame(stateTimer, true);
                break;
            case GLIDDING:
                region = marioSLIDE.getKeyFrame(stateTimer);
                break;
            case HURT:
                region = marioHurt.getKeyFrame(stateTimer);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX() && hurt==false ) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX() && hurt==false) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        if (attack == true && stateTimer > 0.6)
            attack = false;
        if (combo == true && stateTimer > 1.0)
            combo = false;
        if (jump_attack == true && stateTimer > 0.6)
            jump_attack = false;

        return region;


    }

    public State getState() {

        if(hurt==true)
            return State.HURT;
        else if (jump_attack==true){
            return State.JATTACK;
        }
        else if (combo == true && b2body.getLinearVelocity().y==0)
            return State.GLIDDING;
        else if (attack == true) {
            return State.ATTACKING;

        } else if (b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0) {

            return State.RUNNING;

        } else {

            return State.STANDING;
        }

    }

    public void defineWarrior() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(60, 60);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0,7);
        fdef.filter.categoryBits= Demo.WARRIOR_BIT;
        fdef.filter.maskBits=Demo.DEFAULT_BIT | Demo.GROUND_BIT | Demo.DOOR_BIT; // ground doesnt work
        //fdef.filter.maskBits=Demo.DEFAULT_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-6,6),new Vector2(6,6));
        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("head");



    }
}
