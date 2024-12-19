package com.mygdx.game.screens.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.mygdx.game.Application;
import com.mygdx.game.screens.Tile;

public class Level3 extends Level{

    private MapObjects thorns_colliders;

    public Level3(Application app) {
        super("Level3/Level3.tmx", app);

        spriteBatch = new SpriteBatch();

        tile = new Tile(tilePath);
        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false, "Rects");

        thorns = tile.loadMapObjects("Thorns_Rects");
        tile.createBodies(thorns, world, false, "Thorns_Rects");

        thorns_colliders = tile.loadMapObjects("Thorns_Colliders");
        tile.createBodies(thorns_colliders, world, false, "Thorns_Colliders");
    }

    @Override
    public void render(float delta){
        update(delta);
        spriteBatch.begin();
        spriteBatch.end();
    }

    @Override
    public void update(float delta){

    }
}
