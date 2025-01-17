package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

public interface Item {

    void render(SpriteBatch s);

    void updateItem();

    void update();
}
