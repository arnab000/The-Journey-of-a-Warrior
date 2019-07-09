package com.demo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.demo.game.Demo;
import com.demo.game.Scenes.Hud;
import com.demo.game.Screens.PlayScreen;

public class Door extends InteractiveTileObject {
    public Door(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);// for accessing this object
        setCategoryFilter(Demo.DOOR_BIT);

    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Front body","collision");
        Hud.addScore(100);
        setCategoryFilter(Demo.DEFAULT_BIT); // Destroy_bit destroys the object

    }

}
