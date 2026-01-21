package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.system.BodyData;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.mygdx.game.screens.Inventory.treeMap_Items;

public abstract class Item extends Objeto implements Serializable {


    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected int index;

    protected Sprite itemSprite;

    public Item(){
        try {
            name = toString();
            itemSprite = Images.getItemDraw(name);
        } catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public Item(float width, float height){
        super(width, height);
        this.itemSprite = Images.getItemDraw(this.toString());
        this.name = getClass().getSimpleName();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
