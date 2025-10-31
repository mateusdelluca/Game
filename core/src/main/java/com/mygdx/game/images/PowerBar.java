package com.mygdx.game.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Player;

import java.io.Serializable;

import static com.mygdx.game.screens.Stats.char_features;
import static com.mygdx.game.screens.levels.Level_Manager.spriteBatch;

public class PowerBar implements Serializable{

    public static final float WIDTH = Images.hp.getWidth(), HEIGHT = Images.hp.getHeight();
    public static final float WIDTH2 = Images.sp.getWidth(), HEIGHT2 = Images.sp.getHeight();
    public static final float WIDTH3 = Images.power.getWidth(), HEIGHT3 = Images.power.getHeight();
    public static final Animator HIT = new Animator(4,4,16,128,128,"fire/Hit.png");
    public static float hp_0 = WIDTH/3f, sp_0 = WIDTH2/2f, maxSP = sp_0, maxHP = WIDTH, power = WIDTH3/6f, maxPower = power;
    public static boolean hit;
    private Vector3 position = new Vector3();

    private static float x, y;
    public PowerBar(){
        Texture t = new Texture(Gdx.files.internal("Font.png"));
        Images.font = new BitmapFont(Gdx.files.internal("Font.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        Images.font.getData().scale(0.5f);
    }

    public void render(SpriteBatch s, OrthographicCamera playerPos){
        position = playerPos.position;
        if (Player.rifle != null)
            Images.font.draw(s, Player.rifle.stringNumbBullets, -500 + position.x, position.y - 450);
        s.draw(Images.bar, -900 + position.x, position.y - 450);

        sp_0 = Math.min(Math.max(0, sp_0), maxSP);
        power = Math.min(Math.max(0, power), maxPower);
        if (maxPower > WIDTH3)
            maxPower = WIDTH3;
        if (maxHP > WIDTH)
            maxHP = WIDTH;
        if (maxSP > WIDTH2)
            maxSP = WIDTH2;
        if (hp_0 > WIDTH)
            hp_0 = WIDTH;
        if (power > maxPower)
            power = maxPower;
        if (hp_0 < 0){
            hp_0 = 0f;
        }
        hp_0 = Math.min(Math.max(0, hp_0), maxHP);
        s.draw(Images.hp, -900 + position.x + 110, position.y - 450, hp_0, 124);
        s.draw(Images.sp, -900 + position.x + 110, position.y - 450, Math.min(sp_0, maxSP), 124);
        s.draw(Images.power, -900 + position.x, position.y - 450 + 3f, power, Images.power.getHeight());
        if (position.y > 5400f)
            position.y = 5400f;
        if (position.x < 970f)
            position.x = 970f;

//        hit(boy.getBody(), s);
    }
//    public static void hit(Body body){
//        if (body != null && hit) {
//
//            Sprite sprite = new Sprite(HIT.currentSpriteFrame(false, false));
//            sprite.setPosition(body.getPosition().x + 32f / 2f, body.getPosition().y);
//            sprite.setSize(96, 128);
//            if (spriteBatch != null && !spriteBatch.isDrawing()) {
//                spriteBatch.begin();
//                sprite.draw(spriteBatch);
//                spriteBatch.end();
//            }
//            if (HIT.ani_finished()) {
//                HIT.resetStateTime();
//                hit = false;
//            }
//        }
//    }

//    public static void hit(Body body, SpriteBatch spriteBatch){
//        if (body != null && hit) {
//
//            Sprite sprite = new Sprite(HIT.currentSpriteFrame(false, false));
//            sprite.setPosition(body.getPosition().x + 32f / 2f, body.getPosition().y);
//            sprite.setSize(96, 128);
//            sprite.draw(spriteBatch);
//
//            if (HIT.ani_finished()) {
//                HIT.resetStateTime();
//                hit = false;
//            }
//        }
//    }
}
