package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.images.Images;

public class Background {

    public static final int WIDTH = 1920, HEIGHT = 1080;
    public SpriteBatch spriteBatch = new SpriteBatch();
    public OrthographicCamera camera;

    public Background(){
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
    }

    public void render(){
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(Images.backGround,-WIDTH/2f,-HEIGHT/2f);
        spriteBatch.end();
    }


    public void dispose(){
        spriteBatch.dispose();
    }

}
