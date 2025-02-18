package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.BodyData;
import com.mygdx.game.entities.Objeto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public interface Item extends Serializable {

    void render(SpriteBatch s);

    void updateItem();

    void updateItem(World world);

    void update();

    void setUserData(Body body);

    void setVisible(boolean b);
}
