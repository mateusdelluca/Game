package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.images.Images;

public class Portal extends Objeto{

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;
    public static final float X = 5650f, Y = 80f;

    public static boolean open_portal;

    public void render(SpriteBatch s){
        s.draw(Images.portal.currentSpriteFrame(!open_portal, true, false), X, Y, WIDTH, HEIGHT);
    }

    @Override
    public void render(ShapeRenderer s) {
        s.rect(X, Y, WIDTH, HEIGHT);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Rectangle getRectangle(){
        return new Rectangle(X, Y, WIDTH, HEIGHT);
    }

}
