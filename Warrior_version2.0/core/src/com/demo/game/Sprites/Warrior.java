package com.demo.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.demo.game.Screens.PlayScreen;

public class Warrior extends Sprite {
    public enum State{FALLING,JUMPING,STANDING,RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private Texture mariostanding;
    private Texture marioruning;
    private Animation<TextureRegion>marioStand;
    private Animation<TextureRegion> marioRun;
    private float stateTimer;
    private boolean runningRight;

    public Warrior(World world, PlayScreen screen){
        mariostanding=new Texture("hero_idle.png");
        marioruning=new Texture("hero_run.png");

        this.world=world;
        currentState=State.STANDING;
        previousState=State.STANDING;
        stateTimer=0;
        runningRight=true;
        Array<TextureRegion> frames=new Array<TextureRegion>();
        for(int i=0;i<10;i++)
            frames.add(new TextureRegion(mariostanding,i*232,0,232,439));
        marioStand=new Animation(0.1f,frames);
        frames.clear();

        for(int i=0;i<10;i++)
            frames.add(new TextureRegion(marioruning,i*363,0,363,458));
        marioRun=new Animation(0.1f,frames);

        frames.clear();

        defineWarrior();

       setBounds(0,0,getbounds('x'),getbounds('y'));
       setRegion(new TextureRegion(mariostanding,0,0,232,439));


    }
    public float getbounds(char p){
        float x;
        float y;
        currentState=getState();
        switch (currentState){
            case STANDING:
            default:
                x=232/8;
                y=439/8;

                break;
            case RUNNING:
                 x=363/8;
                 y=458/8;

                break;


        }
        if(p=='x')
            return x;
        else
            return y;

    }

    public void update(float dt){
        setBounds(0,0,getbounds('x'),getbounds('y'));
        setPosition(b2body.getPosition().x-10,b2body.getPosition().y-5);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        currentState =getState();
        TextureRegion region;
        switch (currentState){
            case STANDING:
                default:
                region= marioStand.getKeyFrame(stateTimer,true);

                break;
            case RUNNING:
                region=marioRun.getKeyFrame(stateTimer,true);

                break;


        }
        if((b2body.getLinearVelocity().x<0 ||!runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight=false;
        }
        else if((b2body.getLinearVelocity().x>0|| runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight=true;
        }
        stateTimer= currentState == previousState ? stateTimer + dt : 0;
        previousState=currentState;
        return region;


    }
    public State getState(){
        if(b2body.getLinearVelocity().x!=0) {
            //setBounds(0,0,363/8,458/8);
            return State.RUNNING;

        }
        else {
            //setBounds(0,0,232/8,439/8);
            return State.STANDING;
        }

    }
    public void defineWarrior(){
        BodyDef bdef =new BodyDef();
        bdef.position.set(60,60);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape=new CircleShape();
        shape.setRadius(5);
        fdef.shape=shape;
        b2body.createFixture(fdef);




    }
}
