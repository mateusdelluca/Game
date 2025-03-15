package com.mygdx.game.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.Inventory;

public class Item{


    private SpriteBatch spriteBatch = new SpriteBatch();

    public static Integer size = 0;

    public static Integer index_x = 0, index_y = 0;
    public static final int ITEMS_LIMIT = 20;
    public static final Integer INITIAL_X = 420, INITIAL_Y = 740;

    private Vector2 position;

    public static final int WIDTH = 75, HEIGHT = 80;

    private String name;

    public Item(){
        name = getClass().getSimpleName();
        if (Inventory.items.size() < ITEMS_LIMIT) {
            if (index_x % 4 == 0 && index_x != 0){
                index_x = 0;
                index_y++;
            }
            position = new Vector2(INITIAL_X + ((WIDTH + 6) * index_x++), INITIAL_Y - ((HEIGHT + 9) * index_y));
            Inventory.addItemToInventory(this);
        } else{
            size = 0;
        }
    }

    public void render(SpriteBatch spriteBatch){
        Images.saber_inventory.setPosition(INITIAL_X, INITIAL_Y);
        Images.saber_inventory.draw(spriteBatch);
    }

    public void render(SpriteBatch spriteBatch, Sprite image){
        image.setPosition(position.x,position.y);
        image.draw(spriteBatch);
    }

    public Item getItemByName(String name){
          if (this.name.equals(name))
            return this;
        return null;
    }

    @Override
    public String toString(){
        return name;
    }

}
