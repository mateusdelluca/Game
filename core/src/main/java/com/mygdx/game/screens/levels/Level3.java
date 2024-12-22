package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.images.Images;
import com.mygdx.game.objetos.Fan;
import com.mygdx.game.screens.Tile;

public class Level3 extends Level{

    public Level3(Application app) {
        super("Level3/Level3.tmx", app);

        tile = new Tile("Level3/Level3.tmx");
        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false, "Rects");

        thorns = tile.loadMapObjects("Thorns_Rects");
        tile.createBodies(thorns, world, false, "Thorns_Rects");

        MapObjects thorns_colliders = tile.loadMapObjects("Thorns_Colliders");
        tile.createBodies(thorns_colliders, world, false, "Thorns_Colliders");

        boy = new Boy(world, new Vector2(10, 5700), viewport);

        fans.clear();

        fans.add(new Fan(world, new Vector2(1200, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200 + 76, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200 + 2*76, 6000 - 2400)));
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        spriteBatch.setProjectionMatrix(camera.combined);
        update(delta);
        camera.position.set((boy.getBody().getPosition().x) + WIDTH/4f, boy.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        camera.update();
        System.out.println(camera.position.x);
        spriteBatch.begin();
        background.render("Level3");
        powerBar.render();
        tile.render(camera);
        boy.render(spriteBatch);
        for (Fan fan : fans) {
            fan.render(spriteBatch);
        }

        spriteBatch.end();
    }

    @Override
    public void update(float delta){
        for (int i = 0; i < 5; i++) {
            world.step(delta, 7, 7);
            camera.update();
            world.step(delta, 7, 7);
            camera.update();
        }

        for (Fan fan : fans)
            fan.bodyCloseToFan2(boy.getBody(), Boy.BOX_WIDTH);
    }

}
