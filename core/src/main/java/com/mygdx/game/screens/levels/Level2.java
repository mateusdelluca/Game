package com.mygdx.game.screens.levels;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.entities.Jack;
import com.mygdx.game.entities.Monster1;
import com.mygdx.game.screens.Jogo;
import com.mygdx.game.screens.Tile;

public class Level2 extends Level{



    public Level2(){
        super("Level2/Level2.tmx", Jogo.app);
        spriteBatch = new SpriteBatch();
//        monsters1.get("Monster10").getBody().setTransform(1800, 200, 0);
        Monster1.id = 0;
    }

    @Override
    public void render(float deltaTime){
        super.render(deltaTime);
        if (boy.getBody().getPosition().x > WIDTH/2f && boy.getBody().getPosition().x < (6000 - WIDTH))
            camera.position.set((boy.getBody().getPosition().x) + WIDTH/4f, HEIGHT/2f, 0);
        if (boy.getBody().getPosition().x >= (6000 - WIDTH))
            camera.position.set(6000 - WIDTH/2f, HEIGHT / 2f, 0);
        if (boy.getBody().getPosition().x < WIDTH/2f)
            camera.position.set(0f + WIDTH / 2f, HEIGHT / 2f, 0);
        getTile().render(camera);
        spriteBatch.begin();
        boy.render(spriteBatch);
        for (Monster1 monster1 : monsters1.values())
            monster1.render(spriteBatch);
        jack.render(spriteBatch);
        spriteBatch.end();
    }

//    @Override
//    public void resize(int width, int height) {
//        super.resize(width, height);
//    }
//
//    @Override
//    public void pause() {
//        super.pause();
//    }
//
//    @Override
//    public void resume() {
//        super.resume();
//    }
//
//    @Override
//    public void hide() {
//        super.hide();
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//    }
//
//    @Override
//    public boolean keyDown(int keycode) {
//        super.keyDown(keycode);
//        return false;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        super.keyUp(keycode);
//        return false;
//    }

}
