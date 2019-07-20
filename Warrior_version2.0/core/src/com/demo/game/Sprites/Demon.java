package com.demo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.demo.game.Screens.PlayScreen;

public class Demon extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, ATTACKING, JATTACK, GLIDDING,HURT};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private int x;
    private Texture enemystanding;

    private Texture enemyattacking;

    private Texture enemydying;




    private Animation<TextureRegion> enemyStand;
    private Animation<TextureRegion> enemyAttack;

    private Animation<TextureRegion> enemyDeath;


    public float stateTimer;
    public boolean attack;
    public boolean Hurt;
    private boolean runningRight;

    public Demon(World world, PlayScreen screen,int x) {
        enemyattacking= new Texture("hell-beast-breath.png");
        enemystanding= new Texture("hell-beast-idle.png");

        enemydying =new Texture("hell-beast-burn.png");


        this.world = world;
        this.x=x;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = false;
        attack = false;



        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0;i<4;i++)

            frames.add(new TextureRegion(enemyattacking,i*256/4,0,256/4,64));
        enemyAttack = new Animation(0.2f, frames);
        frames.clear();



        for (int i = 0; i < 6; i++)
            frames.add(new TextureRegion(enemystanding, i*330/6, 0, 330/6, 67));
        enemyStand = new Animation(0.1f, frames);

        frames.clear();

        for (int i = 0; i < 7; i++)
            frames.add(new TextureRegion(enemydying, i*444/6, 0, 444/6,160));
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

            default:
                x = 330/6;
                y = 67;

                break;

            case ATTACKING:
                x = 256/4;
                y = 64;
                break;
            case HURT:
                x=444/6;
                y=160;
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
                x = b2body.getPosition().x -10;
                y = b2body.getPosition().y-40 ;

                break;
            case ATTACKING:

                x = b2body.getPosition().x -10;
                y = b2body.getPosition().y - 40;

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

    public void update(float dt) {
        if(Hurt==true)
            b2body.applyLinearImpulse(new Vector2(0,156),b2body.getWorldCenter(),true);
        setBounds(0, 0, getbounds('x'), getbounds('y'));
        setPosition(getPos('x'), getPos('y'));
        setRegion(getFrame(dt));


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

            case HURT:
                region=enemyDeath.getKeyFrame(stateTimer);
                break;


        }


        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;


      if(attack==true && stateTimer>0.8){
          attack=false;
      }

        return region;


    }


    public State getState() {
        if(Hurt==true)
            return State.HURT;
        else if (attack == true )
            return State.ATTACKING;


        else

            return State.STANDING;


    }

    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0,40);
        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}
