package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.images.Images;

public class Portal extends Objeto{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;
    public static final float X = 5650f, Y = 80f;

    public void render(SpriteBatch s){
        s.draw(Images.portal.currentSpriteFrame(false, true, false), X, Y, WIDTH, HEIGHT);
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(X, Y, WIDTH, HEIGHT);
    }

    public Rectangle getRectangle(){
        return new Rectangle(X, Y, WIDTH, HEIGHT);
    }
}
