package com.mygdx.game.fans;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public interface Fans {

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void bodyCloseToFan2(Body b, float width);

    public abstract void update();

}
