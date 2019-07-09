package com.demo.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.demo.game.Demo;

public class Ground extends InteractiveTileObject {

    public Ground(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        //fixture.setUserData(this);
        setCategoryFilter(Demo.GROUND_BIT);
    }

    @Override
    public void onHeadHit() {

    }
}
