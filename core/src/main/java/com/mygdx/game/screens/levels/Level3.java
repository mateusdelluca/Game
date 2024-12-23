package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Bullet;
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
        fans.add(new Fan(world, new Vector2(1200, 6000 - 1640)));
        fans.add(new Fan(world, new Vector2(1400, 6000 - 1120)));
        fans.add(new Fan(world, new Vector2(1280, 6000 - 640)));
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        spriteBatch.setProjectionMatrix(camera.combined);
        update(delta);
        camera.position.set(boy.getBody().getPosition().x, boy.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        camera.update();
        spriteBatch.begin();
        background.render(getClass().getSimpleName());
        powerBar.render();
        tile.render(camera);
        boy.render(spriteBatch);
        for (Bullet bullet : bullets)
            bullet.render(spriteBatch);
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

    @Override
    public void beginContact(Contact contact){
        super.beginContact(contact);
        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
            return;
//        if (fixtureA.getBody() == null || fixtureB.getBody() == null)
//            return;
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
            return;

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        System.out.println(tile.bodies_of_thorns);

        if (body1.getUserData().equals("Thorns_Colliders") && body2.getUserData().toString().equals("Boy")) {
            boyBeenHit();
        } else {
            if (body2.getUserData().equals("Thorns_Colliders") && body1.getUserData().toString().equals("Boy")) {
                boyBeenHit();
            }
        }
    }

}
