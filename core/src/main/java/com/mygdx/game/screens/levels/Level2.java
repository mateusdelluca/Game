package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.entities.*;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.NinjaRope;
import com.mygdx.game.items.Portal;
import com.mygdx.game.items.minis.Minis;


import static com.mygdx.game.entities.Player.minis;
import static com.mygdx.game.images.Images.tile;
import static com.mygdx.game.screens.levels.Level_Manager.*;
import static com.mygdx.game.screens.levels.Level_Manager.camera;

public class Level2 extends Level{
//    public ArrayList<Objeto> objetos = new ArrayList<>();

    public Level2() {
        super();
        monsters1.clear();
        objetos.clear();
        player.setViewport(viewport);
        player.getBody().setTransform(new Vector2(100, 200), 0);
        camera.update();
        ninjaRope = new NinjaRope(player.getBody());
        mouse = new Mouse(player.getBody().getPosition());
        jack = new Jack(new Vector2(2300f, 300f));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(500, 40),     Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(800, 40),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1300, 40),    Monster1.class.getSimpleName() + monsters1.size()));
        objetos.add(new Portal(new Vector2(6000 - 300, 400)));
        objetos.add(player);
        objetos.add(jack);
        objetos.addAll(monsters1.values());
        items.put("Rifle", rifle);
        items.get("Rifle").updateItem();
    }


    @Override
    public void render(){
//        super.render();


        spriteBatch.setProjectionMatrix(camera.combined);
//        if (!StateManager.oldState.equals("PAUSE"))
        update();


        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y + 200f, 0);
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
//        boy.render(spriteBatch);
        powerBar.render(spriteBatch, camera);
        spriteBatch.end();
        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void update(){
        super.update();
        for (Objeto o : objetos)
            o.update();
        for (Item item : items.values())
            item.update();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.touchDown(screenX, screenY, pointer, button);
        ninjaRope.justTouched(button);
        mouse.touchDown(screenX, screenY, button);
        return false;
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

        for (Objeto o : objetos) {
            if (o != null && !(o instanceof Portal))
                o.beenHit(body1, body2);
            if (o instanceof Portal)
                o.beginContact(contact);
            if (o instanceof Player)
                o.beginContact(contact);
        }

        for (Minis m : minis){
            m.beginContact(body1,body2);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
