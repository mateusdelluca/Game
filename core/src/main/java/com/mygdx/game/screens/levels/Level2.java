package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.entities.*;
import com.mygdx.game.items.Portal;
import com.mygdx.game.sfx.Sounds;


import static com.mygdx.game.screens.levels.Level_Manager.camera;

public class Level2 extends Level{
//    public ArrayList<Objeto> objetos = new ArrayList<>();

    public Level2() {
        super();
        camera.update();
//        ninjaRope = new NinjaRope(player.getBody());
        jack = new Jack(new Vector2(2300f, 300f));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(500, 40),     Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(800, 40),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(1300, 40),    Monster1.class.getSimpleName() + monsters1.size()));
        objetos.add(new Portal(new Vector2(6000 - 300, 400)));

        objetos.add(jack);
        objetos.addAll(monsters1.values());
//        items.put("Rifle", rifle); //TODO: RESOLVER PROBLEMA DE RIFLE QUE ATUALIZA COM ESTE MÉTODO UPDATEITEM QUE NÃO HÁ MAIS EM ITEM NEM ABSTRATO NEM EXISTENTE
//        items.get("Rifle").updateItem();
    }


    @Override
    public void render(){
        super.render();
        if (Sounds.LEVEL1.isPlaying()) {
            Sounds.LEVEL1.stop();
            Sounds.LEVEL2.play();
            Sounds.LEVEL2.setLooping(true);
        }

//        spriteBatch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void update(){
        super.update();
//        for (Item item : items.values())
//            item.update();
//        for (Objeto o : objetos)
//            o.update();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.touchDown(screenX, screenY, pointer, button);
//        ninjaRope.justTouched(button);

        return false;
    }

    @Override
    public void beginContact(Contact contact) {
        super.beginContact(contact);

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
//        for (Objeto o : objetos) {
//            if (o != null && !(o instanceof Portal))
//                o.beenHit(body1, body2);
//            if (o instanceof Portal)
//                o.beginContact(contact);
//            if (o instanceof Player)
//                o.beginContact(contact);
//        }
//

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
