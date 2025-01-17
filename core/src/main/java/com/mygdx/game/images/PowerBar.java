package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.items.Rifle;

public class PowerBar {

    public static final float WIDTH = Images.hp.getWidth()/2f, HEIGHT = Images.hp.getHeight()/2f;
    public static final float WIDTH2 = Images.hp2.getWidth()/3f, HEIGHT2 = Images.hp2.getHeight()/2f;

    public static float hp = WIDTH2, sp = WIDTH2;

    private Vector3 position = new Vector3();
    private BitmapFont font;
    public PowerBar(){
        Texture t = new Texture(Gdx.files.internal("Font.png"));
        font = new BitmapFont(Gdx.files.internal("Font.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.5f);
    }

    public void render(SpriteBatch s, OrthographicCamera playerBody){
        position = playerBody.position;
        font.draw(s, Rifle.stringNumbBullets, -900 + position.x, position.y - 400);
        s.draw(Images.hp, -900 + position.x + 70, position.y + 470, WIDTH, HEIGHT);
        s.draw(Images.hp2,-900 + position.x +  70 + 40, position.y + 470, hp, HEIGHT);
        s.draw(Images.sp, -900 + position.x +  70, position.y + 420, WIDTH, HEIGHT);
        s.draw(Images.sp2, -900 + position.x + 70 + 40, position.y + 420, sp, HEIGHT);
        if (position.y > 5400f)
            position.y = 5400f;
        if (position.x < 970f)
            position.x = 970f;

    }


    public void dispose(){
        Images.hp.dispose();
        Images.hp2.dispose();
        Images.sp.dispose();
        Images.sp2.dispose();
    }

}
