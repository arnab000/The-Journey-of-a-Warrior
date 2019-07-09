package com.demo.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected  Fixture fixture;

    public  InteractiveTileObject(World world,TiledMap map,Rectangle bounds){
        this.world=world;
        this.map=map;
        this.bounds=bounds;
        BodyDef bdef= new BodyDef();
        FixtureDef fdef=new FixtureDef();
        PolygonShape shape = new PolygonShape();

       /* bdef.type=BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth())/2,bounds.getY()+bounds.getHeight()/2);
        body =world.createBody(bdef);

        shape.setAsBox(bounds.getWidth()/2,bounds.getHeight()/2);
        fdef.shape=shape;
        body.createFixture(fdef);*/
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(bounds.getX()+bounds.getWidth()/2,bounds.getY()+bounds.getHeight()/2);
        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2,bounds.getHeight()/2);
        fdef.shape=shape;
        fixture=body.createFixture(fdef);
    }

    public abstract  void onHeadHit();

    public void setCategoryFilter(short filterbit){
             Filter filter=new Filter();
             filter.categoryBits=filterbit;
             fixture.setFilterData(filter);
    }

}
