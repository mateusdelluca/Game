package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.images.Images;



public class Background{

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public transient SpriteBatch spriteBatch = new SpriteBatch();
    public transient OrthographicCamera camera;

    public Background(){
        init();
    }

    private void init() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
    }

    public void render(String level){
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(getBackgroundLevel(level),-WIDTH/2f,-HEIGHT/2f);
        spriteBatch.end();
    }

    public Sprite getBackgroundLevel(String level){
        if (level.equals("Level3"))
            return Images.backGround2;
        return Images.mountains4;
    }

    public void render(){
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(Images.mountains4,-WIDTH/2f,-HEIGHT/2f);
        spriteBatch.end();
    }


    public void dispose(){
        spriteBatch.dispose();
    }
    
}
