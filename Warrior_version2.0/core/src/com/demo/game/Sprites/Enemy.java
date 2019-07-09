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

public class Enemy extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, JATTACK, GLIDDING,HURT};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private int x;
    private Texture enemystanding;

    private Texture enemyattacking;
    private Texture enemyrunning;
    private Texture enemydying;




    private Animation<TextureRegion> enemyStand;
    private Animation<TextureRegion> enemyAttack;
    private Animation<TextureRegion> enemyRun;
    private Animation<TextureRegion> enemyDeath;


    private float stateTimer;
    public boolean attack;
    public boolean Hurt;
    private boolean runningRight;

    public Enemy(World world, PlayScreen screen,int x) {
        enemyattacking= new Texture("demon-attack.png");
        enemystanding= new Texture("demon-idle.png");
        enemyrunning=new Texture("demon-idle.png");
        enemydying =new Texture("demon-attack-no-breath.png");


        this.world = world;
        this.x=x;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = false;
        attack = false;



        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0;i<11;i++)

                frames.add(new TextureRegion(enemyattacking,i*2640/11,0,2640/11,192));
        enemyAttack = new Animation(0.08f, frames);
        frames.clear();

        for (int i=0;i<6;i++)
            frames.add(new TextureRegion(enemystanding, i*960/6, 0, 960/6, 144));
        enemyRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(enemystanding, i*960/6, 0, 960/6, 144));
        enemyStand = new Animation(0.1f, frames);

        frames.clear();

        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(enemydying, i*1536/8, 0, 1536/8,176));
        enemyDeath = new Animation(0.1f, frames);

        frames.clear();

        defineEnemy();

        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setRegion(new TextureRegion(enemystanding, 0, 0, 408, 1728/6));


    }


    public float getbounds(char p) {
        float x;
        float y;
        currentState = getState();
        switch (currentState) {
            case STANDING:
            case HURT:
            default:
                x = 960/6;
                y = 144;

                break;

            case ATTACKING:
                x = 2640/11;
                y = 192;
                break;
            case RUNNING:
                x=960/6;
                y=144;
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
                x = b2body.getPosition().x - 55;
                y = b2body.getPosition().y - 20;

                break;
            case ATTACKING:

                x = b2body.getPosition().x -110;
                y = b2body.getPosition().y - 35;

                break;


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

    public void update(float dt,boolean bool,float pos,int dada,boolean enemyhurt) {
        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setPosition(getPos('x'), getPos('y'));
        setRegion(getFrame(dt));
        if(pos<=b2body.getPosition().x)
            runningRight=true;
        else
            runningRight=false;
        attack=bool;

        Hurt=enemyhurt;

        handleInput();
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case STANDING:
            case RUNNING:
            default:
                region = enemyStand.getKeyFrame(stateTimer, true);
                break;

            case ATTACKING:
                region = enemyAttack.getKeyFrame(stateTimer, true);

                break;

            case HURT:
                region=enemyDeath.getKeyFrame(stateTimer);
                break;


        }


        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;




        return region;


    }

    public State getState() {
        if(Hurt==true)
            return State.HURT;
        else if (attack == true )
            return State.ATTACKING;
        else if(b2body.getLinearVelocity().x!=0)
            return State.RUNNING;

        else

            return State.STANDING;


    }

    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,1000);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0,40);
        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}
