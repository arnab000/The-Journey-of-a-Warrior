package com.demo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.demo.game.Scenes.Hud;
import com.demo.game.Screens.PlayScreen;
import java.util.*;

public class FireBomb extends Sprite {
    public enum State { STANDING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public Hud hud;
    public boolean destroy=false;
    private float x;
    private float y;
    private Texture enemystanding;





    private Animation<TextureRegion> enemyStand;


    private float stateTimer;

    private boolean runningRight;

    public FireBomb(World world, PlayScreen screen, float x,float y ) {

        enemystanding= new Texture("fire-skull.png");




        this.world = world;
        this.y=y;
        this.x=x;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;




        Array<TextureRegion> frames = new Array<TextureRegion>();


        for (int i = 0; i < 3; i++)
            frames.add(new TextureRegion(enemystanding, i*768/8, 0, 768/8, 112));
        enemyStand = new Animation(0.1f, frames);

        frames.clear();


        defineEnemy();

        setBounds(0, 0, 57/3,16);
        setRegion(new TextureRegion(enemystanding, 0, 0, 57/3, 16));


    }





    public void update(float dt) {
        float haha;
        float baba;


        b2body.applyLinearImpulse(new Vector2(-100,0),b2body.getWorldCenter(),true);
        float pos= b2body.getPosition().x;



        if (pos < 0 || destroy)
        {





            //world.step(0,0,0);
            // b2body.setActive(false);
            b2body.applyLinearImpulse(new Vector2(0,270),b2body.getWorldCenter(),true);


            setPosition(10000,200000);
        }
        else {
            setBounds(0, 0, 768/32, 112/4);
            setPosition(b2body.getPosition().x, b2body.getPosition().y-18);
            setRegion(getFrame(dt));
        }


    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case STANDING:

            default:
                region = enemyStand.getKeyFrame(stateTimer, true);
                break;




        }


        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;




        return region;


    }

    public State getState() {


        return State.STANDING;


    }
    public void draw(Batch batch){
        super.draw(batch);
    }

    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0,30);
        fdef.shape = shape;
        b2body.createFixture(fdef);


    }
}
