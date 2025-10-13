package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.mygdx.game.entities.*;
import com.mygdx.game.items.*;
import com.mygdx.game.items.minis.Minis;

import static com.mygdx.game.entities.Boy.minis;
import static com.mygdx.game.images.Images.tile;
import static com.mygdx.game.screens.levels.Level_Manager.*;

public class Level1 extends Level{

    public Level1() throws Exception {
        super();
        monsters1.clear();
        boy.setViewport(viewport);
        ninjaRope = new NinjaRope(boy.getBody());
        mouse = new Mouse(boy.getBody().getPosition());
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(500, 400),     Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(800, 400),    Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1300, 400),    Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1600, 400),   Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1850, 500),   Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2650, 600),   Monster1.class.getSimpleName() + monsters1.size(), boy));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(3300, 600),    Monster1.class.getSimpleName() + monsters1.size(), boy));

        items.clear();
        for (int index = 1, posX = 320, posY = (540); index <= 7; index++) {
            posX = 320 + (100 * index);
            items.put(Crystal.class.getSimpleName() + items.size(), new Crystal(new Vector2(posX, posY), items.size()));
        }
        items.put(Rifle.class.getSimpleName(), new Rifle(new Vector2(500, 400)));
        items.put(Laser_Headset.class.getSimpleName(), new Laser_Headset(new Vector2(300, 400)));
        items.get("Rifle").updateItem();
        items.get("Laser_Headset").updateItem();
        objetos.clear();
        objetos.add(boy);
        objetos.add(new Jack(new Vector2(3000, 400)));
        objetos.add(new Portal(new Vector2(6000 - 300, 20)));
        objetos.addAll(monsters1.values());
        objetos.add(new Robot(new Vector2(700, 300)));
    }

    @Override
    public void render(){

//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
//        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);




        spriteBatch.setProjectionMatrix(camera.combined);
//        if (!StateManager.oldState.equals("PAUSE"))
        update();


        camera.position.set(boy.getBody().getPosition().x, boy.getBody().getPosition().y + 200f, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        if (camera.position.y < 1080f/2f)
            camera.position.y = 1080f/2f;
        camera.update();
        viewport.update(Level.WIDTH, Level.HEIGHT);


        spriteBatch.begin();
        background.render();
        tile.render(camera);
//        jack.render(spriteBatch);
//        boy.render(spriteBatch);
        for (Item item : items.values())
            item.render(spriteBatch);
        for (Objeto objeto : objetos)
            objeto.render(spriteBatch);
        powerBar.render(spriteBatch, camera, boy);
        spriteBatch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void update(){
        for (int i = 0; i < 5; i++) {
            world.step(1/60f, 10, 7);
            camera.update();
        }



        for (Objeto objeto : objetos) {
            objeto.update();
        }
        for (Item item : items.values())
            item.update();
    }


    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);

        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
            return;
        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
            return;
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
            return;

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        if (body1 == null || body2 == null)
            return;
//
//        boy.beginContact(contact);
//
//        for (Monster1 m : monsters1.values()){
//            m.beginContact(body1, body2);
//        }


        for (Minis m : minis){
            m.beginContact(body1,body2);
        }

        for (Objeto o : objetos)
            if (o instanceof Portal)
                ((Portal) o).beginContact(contact);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
//        super.beginContact(contact);
//
//        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
//            return;
//        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
//            return;
//        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
//            return;
//
//        Body body1 = contact.getFixtureA().getBody();
//        Body body2 = contact.getFixtureB().getBody();
//
//        if (body1 == null || body2 == null)
//            return;
//
//        boy.beginContact(contact);
//
//        for (Monster1 m : monsters1.values()){
//            m.beginContact(body1, body2);
//        }
    }
}
