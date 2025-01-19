package com.mygdx.game.items.fans;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import java.io.Serializable;

public interface Fans extends Serializable {

    void render(SpriteBatch spriteBatch);

    void update2(Body b, float width);

    void update();

}
