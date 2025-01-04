package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Background;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.items.Bullet;
import com.mygdx.game.entities.Monster1;
import com.mygdx.game.entities.items.Rifle;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.fans.Fan;
import com.mygdx.game.fans.Fan2;
import com.mygdx.game.fans.Fans;
import com.mygdx.game.screens.Tile;
import com.mygdx.game.entities.items.Item;
import java.util.HashMap;

public class Level3 extends Level implements ContactListener {

    private HashMap<String, Item> items = new HashMap<>();
    ShapeRenderer shapeRenderer = new ShapeRenderer();;
    public Level3(Application app) {
        super();
        super.app = app;

        tile = new Tile("Level3/Level3.tmx");
        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false, "Rects");

        thorns = tile.loadMapObjects("Thorns_Rects");
        tile.createBodies(thorns, world, false, "Thorns_Rects");

        MapObjects thorns_colliders = tile.loadMapObjects("Thorns_Colliders");
        tile.createBodies(thorns_colliders, world, false, "Thorns_Colliders");

        background = new Background();

        powerBar = new PowerBar();

        boy = new Boy(world, new Vector2(10, 5700), viewport);

        fans.clear();

//        fans.add(new Fan(world, new Vector2(1200, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200 + 76, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200 + 2*76, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200, 6000 - 1640)));
        fans.add(new Fan(world, new Vector2(1400, 6000 - 1120)));
        fans.add(new Fan(world, new Vector2(1280, 6000 - 640)));

        fans.add(new Fan2(world, new Vector2(350, 6000 - 2100)));

        monsters1.clear();

        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(640, 6000 - 320), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(480, 6000 - 2000), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(2080, 6000 - 360), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(3200, 6000 - 1080), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(4800, 6000 - 2720), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(2680, 6000 - 2720), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(240, 6000 - 5880), Monster1.class.getSimpleName() + monsters1.size()));

        world.setContactListener(this);

        items.put(Rifle.class.getSimpleName() + items.size(), new Rifle(world,new Vector2(440, 6000 - 350)));

        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, false, false, true);
    }

    @Override
    public void render(float delta){
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
//        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        box2DDebugRenderer.render(world, camera.combined);


        spriteBatch.setProjectionMatrix(camera.combined);
        update(delta);
        camera.position.set(boy.getBody().getPosition().x, boy.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        camera.update();
        renderObjects();
//        System.out.println(boy.getBody().getPosition().x);
    }

    @Override
    public void renderObjects(){
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.setAutoShapeType(true);
//        shapeRenderer.begin();
//        boy.renderShape(shapeRenderer);
//        shapeRenderer.end();



        spriteBatch.begin();
        background.render(getClass().getSimpleName());
        tile.render(camera);
        powerBar.render(spriteBatch, camera);
        for (Bullet bullet : bullets)
            bullet.render(spriteBatch);
        for (Item item : items.values())
            item.render(spriteBatch);
        boy.render(spriteBatch);
        for (Fans fan : fans) {
            fan.render(spriteBatch);
        }
        for (Monster1 monster1 : monsters1.values()){
            monster1.render(spriteBatch);
            monster1.update(boy);
        }
        spriteBatch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void update(float delta){
        for (int i = 0; i < 5; i++) {
            world.step(delta, 7, 7);
            camera.update();
            world.step(delta, 7, 7);
            camera.update();
        }

        collisions();

        for (Fans fan : fans)
            fan.bodyCloseToFan2(boy.getBody(), Boy.BOX_WIDTH);


    }

    @Override
    public void beginContact(Contact contact){
        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
            return;
        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
            return;
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
            return;

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        System.out.println(tile.bodies_of_thorns);

        for (Item item : items.values()) {
            if ((body1.getUserData().toString().equals(item.toString()) && body2.getUserData().toString().equals("Boy")) ||
                (body2.getUserData().toString().equals(item.toString()) && body1.getUserData().toString().equals("Boy"))) {
                boy.equip_Item(item);

                if (body1.getUserData().equals(item.toString()))
                    body1.setUserData("null");
                else
                    if (body2.getUserData().equals(item.toString()))
                        body2.setUserData("null");
            }
        }

        if ((body1.getUserData().toString().equals("Bullet") &&
           body2.getUserData().toString().equals("Boy"))
            || (body2.getUserData().toString().equals("Bullet") &&
            body1.getUserData().toString().equals("Boy"))) {
            if (body1.getFixtureList().get(0).isSensor() ||
                body2.getFixtureList().get(0).isSensor())
                return;
            if (body1.getUserData().toString().equals("Bullet")){
                body1.setUserData("null");
            } else{
                if (body2.getUserData().toString().equals("Bullet")){
                    body2.setUserData("null");
                }
            }
            boyBeenHit();
        }

        if (body1.getUserData().equals("Thorns_Colliders") && body2.getUserData().toString().equals("Boy")) {
            boyBeenHit();
        } else {
            if (body2.getUserData().equals("Thorns_Colliders") && body1.getUserData().toString().equals("Boy")) {
                boyBeenHit();
            }
        }

        for (Monster1 m1 : monsters1.values()){
            if (body1.getUserData().toString().equals(m1.toString()) && body2.getUserData().toString().equals("Boy")
                || body2.getUserData().toString().equals(m1.toString()) && body1.getUserData().toString().equals("Boy")){
                boyBeenHit();
            }
            if (body1.getUserData().equals("Thorns_Colliders") && body2.getUserData().toString().equals(m1.getBody().getUserData())) {
                monster1BeenHit(m1, body1);
            } else {
                if (body2.getUserData().equals("Thorns_Colliders") && body1.getUserData().toString().equals(m1.getBody().getUserData())) {
                    monster1BeenHit(m1, body2);
                }
            }
            if ((body1.getUserData().toString().equals("Bullet") &&
                body2.getUserData().toString().equals(m1.toString())
            ) || body2.getUserData().toString().equals("Bullet") &&
                body1.getUserData().toString().equals(m1.toString())){
                if (body1.getUserData().toString().equals("Bullet")){
                    monster1BeenHit(m1, body1);
                    body1.setUserData("null");
                    body1.setGravityScale(0.1f);
                } else{
                    if (body2.getUserData().toString().equals("Bullet")){
                        monster1BeenHit(m1, body2);
                        body2.setUserData("null");
                        body2.setGravityScale(0.1f);
                    }
                }
            }
        }
        if (body1.getUserData().toString().equals("null")){
            bodiesToDestroy.add(body1);
        }
        if (body2.getUserData().toString().equals("null")){
            bodiesToDestroy.add(body2);
        }
    }

}
