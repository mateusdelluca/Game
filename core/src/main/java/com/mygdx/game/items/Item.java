package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.system.BodyData;

import java.io.Serializable;

import static com.mygdx.game.screens.Inventory.treeMap_Items;

public abstract class Item extends Objeto implements Serializable {

    public Item(){
    }

    public Item(float width, float height){
        super(width, height);
    }

    public abstract void updateItem();

    public abstract void updateItem(World world);

    @Override
    public void update() {
        super.update();
    }

    public abstract void setUserData(Body body);

    public abstract void setUserData(String name);

    public abstract BodyData getBodyData();

    public int getIndex(){
        if (!treeMap_Items.isEmpty())
            return treeMap_Items.get(this.toString()).getIndex();
        return 0;
    }

    public void setIndex(int index){
        treeMap_Items.get(this.toString()).setIndex(index);
    }

    @Override
    public String toString(){
        return getClass().getSimpleName();
    }
}
