package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.entities.*;
import com.mygdx.game.items.*;
import com.mygdx.game.items.minis.Minis;

import static com.mygdx.game.entities.Player.minis;
import static com.mygdx.game.images.Images.tile;
import static com.mygdx.game.items.Rope.NUM_ROPES;
import static com.mygdx.game.screens.levels.Level_Manager.*;

public class Level4 extends Level{
    public Level4(){  //TODO: fix render method and update
        super();
//        mouse = new Mouse(player.getBody().getPosition());

        //TODO: fix positions and number of 'objetos'
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(640, 6000 - 2000),     Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(480, 6000 - 2000),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2080, 6000 - 360),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(3200, 6000 - 1080),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(4800, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2680, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(240, 6000 - 5880),    Monster1.class.getSimpleName() + monsters1.size()));
        for (int index = 1, posX = 320, posY = (6000 - 240); index < 16; index++) {
            if (index < 5) {
                posX = 320 + (100 * index);
            }
            if (index >= 5) {
                posY = 6000 - 240 - (150 * index);
                posX = 320 + 3 * 200;
            }
            if ((index >= 8 && index <= 11)) {
                posX = 320 + (100 * (index - 5));
                posY = 6000 - 2000;
            }
            if ((index >= 12 && index <= 15)) {
                posX = 520 + (100 * (index - 10));
                posY = 6000 - 2300;
            }
            items.put(Crystal.class.getSimpleName() + items.size(), new Crystal(new Vector2(posX, posY)));
        }
        items.put(Rifle.class.getSimpleName(), new Rifle(new Vector2(850, 6000 - 450)));
        items.put(JetPack.class.getSimpleName(), new JetPack(new Vector2(400, 6000 - 2400)));
        items.put(Saber.class.getSimpleName(), new Saber(new Vector2(500, 6000 - 2400)));
        items.put(Portal.class.getSimpleName(), new Portal(new Vector2(2450,6000 - 5600)));
//        items.put(NinjaRope.class.getSimpleName(), new NinjaRope(new Vector2(450, 6000 - 400)));
        items.put(Star.class.getSimpleName(), new Star(new Vector2(200, 300)));
////            items.get("Portal").updateItem();
//
//            items2.put(JetPack.class.getSimpleName(), new JetPack(new Vector2(400, 6000 - 2400)));
//            items2.put(Saber.class.getSimpleName(), new Saber(new Vector2(500, 6000 - 2400)));
//            items2.put(Portal.class.getSimpleName(), new Portal(new Vector2(2450,6000 - 5600)));
//

//            for (int i = 0; i < 5; i++)
//                blocks.add(new Block(new Vector2(850 + i * 50, 6000 - 530)));
//
//            for (int i = 0; i < 5; i++)
//                blocks.add(new Block(new Vector2(850 + i * 50, 4050)));
//
//            for (int i = 0; i < 28; i++)
//                blocks.add(new Block(new Vector2(3330 + (i * 50), 4810)));
//
//            for (int i = 0; i < 27; i++)
//                blocks.add(new Block(new Vector2(4200 + (i * 50), 4050)));
//
//
//            for (int i = 1; i < 7; i++) {
//                stands.add(new Stand(3000 + (200 * i), 6000 - 720));
//            }
//
////        objetos.addAll(blocks);
//
        rope = new Rope(new Vector2(300, 26000 - 400 + (NUM_ROPES * Rope.HEIGHT)), true);
//            for (int i = 0; i < NUM_ROPES; i++){
//                ropes.add(new Rope(new Vector2(300f, 6000 - 400 + (i * Rope.HEIGHT)), false));
//                if (i == 0) {
//                    rope.joint(ropes.get(i).getBodyA());
//                    continue;
//                }
//                ropes.get(i).joint(ropes.get(i-1).getBodyA());
//            }
//            objetos.addAll(items2.values());
//        ninjaRope = new NinjaRope(player.getBody());
        objetos.add(player);
        objetos.add(jack);
//            objetos.add(girl);
//            objetos.addAll(stands);
//        objetos.add(ninjaRope);
        objetos.add(ninjaStar);
        objetos.addAll(monsters1.values());
//        }


        for (Item item : items.values()) {
            if (item != null) {
                //item.updateItem();//TODO: verificar o uso de update item em classes filhas de Item
            }
        }
    }

    @Override
    public void render(){
        spriteBatch.setProjectionMatrix(camera.combined);
//        if (!StateManager.oldState.equals("PAUSE"))
        update();


        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 800f)
            camera.position.x = 800f;
        if (camera.position.y < 1080f/2f)
            camera.position.y = 1080f/2f;
        camera.update();
        viewport.update(Level.WIDTH, Level.HEIGHT);

        spriteBatch.begin();
        background.render();
        tile.render(camera);
        for (Item item : items.values()) {
            if (item != null) {
                item.render(spriteBatch);
            }
        }
        for (Objeto objeto : objetos) {
            if (objeto != null)
                objeto.render(spriteBatch);
        }
        powerBar.render(spriteBatch, camera);
        spriteBatch.end();
        box2DDebugRenderer.render(world, camera.combined);
    }

    public void update(){
        super.update();
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


        for (Minis m : minis){
            m.beginContact(body1,body2);
        }
    }



    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
