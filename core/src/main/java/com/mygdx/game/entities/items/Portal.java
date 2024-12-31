package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;

public class Portal extends Objeto implements Item {

    public static final float WIDTH = 857/3f, HEIGHT = 873/3f;
    public static float X = 5650f, Y = 80f;

    public static boolean open_portal;

    public void render(SpriteBatch s){
        s.setColor(1f,1f,1f,1f);
        s.draw(Images.portal.currentSpriteFrame(!open_portal, true, false), X, Y, WIDTH, HEIGHT);
    }

    @Override
    public void renderShape(ShapeRenderer s) {
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
