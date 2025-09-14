package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.items.Rifle;

import java.io.Serializable;

public class PowerBar implements Serializable{

    public static final float WIDTH = Images.hp.getWidth(), HEIGHT = Images.hp.getHeight();
    public static final float WIDTH2 = Images.sp.getWidth(), HEIGHT2 = Images.sp.getHeight();

    public static float hp = WIDTH, sp = WIDTH2;

    private Vector3 position = new Vector3();

    public PowerBar(){
        Texture t = new Texture(Gdx.files.internal("Font.png"));
        Images.font = new BitmapFont(Gdx.files.internal("Font.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        Images.font.getData().scale(0.5f);
    }

    public void render(SpriteBatch s, OrthographicCamera playerBody, Boy boy){
        position = playerBody.position;
        if (boy != null && boy.getRifle() != null)
            Images.font.draw(s, boy.getRifle().stringNumbBullets, -500 + position.x, position.y - 450);
        s.draw(Images.bar, -900 + position.x, position.y - 450);
        hp = Math.min(Math.max(Math.max(0, hp), hp), WIDTH);
        if (hp > WIDTH)
            hp = WIDTH;
        s.draw(Images.hp, -900 + position.x + 110, position.y - 450, hp, 124);
        s.draw(Images.sp, -900 + position.x + 110, position.y - 450, Math.min(sp, WIDTH2), 124);
        if (position.y > 5400f)
            position.y = 5400f;
        if (position.x < 970f)
            position.x = 970f;

    }

}
