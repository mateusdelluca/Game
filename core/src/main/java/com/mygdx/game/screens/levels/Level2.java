package com.mygdx.game.screens.levels;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.Portal;

import static com.mygdx.game.images.Images.tile;
import static com.mygdx.game.screens.levels.Level_Manager.*;
import static com.mygdx.game.screens.levels.Level_Manager.camera;

public class Level2 extends Level{
    public Level2() throws Exception {
        super();
        monsters1.clear();
        objetos.clear();
        world.destroyBody(boy.getBody());
        boy = new Boy(new Vector2(100, 500), viewport);
        objetos.add(new Portal(new Vector2(6000 - 300, 400)));
        objetos.add(boy);
    }


    @Override
    public void render(){
//        super.render();


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
        background.render(getClass().getSimpleName());
        tile.render(camera);
//        jack.render(spriteBatch);
//        boy.render(spriteBatch);
        for (Item item : items.values())
            item.render(spriteBatch);
        for (Objeto objeto : objetos)
            objeto.render(spriteBatch);
        powerBar.render(spriteBatch, camera);
        spriteBatch.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void update(){
        super.update();
        for (Objeto o : objetos)
            o.update();
    }

}
