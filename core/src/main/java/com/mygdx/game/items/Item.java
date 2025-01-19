package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public interface Item extends Serializable {

    void render(SpriteBatch s);

    void updateItem();

    void update();
}
