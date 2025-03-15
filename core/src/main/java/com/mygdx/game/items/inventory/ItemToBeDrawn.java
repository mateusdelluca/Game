package com.mygdx.game.items.inventory;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.Inventory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ItemToBeDrawn {

    public static Integer size = 0;

    public static Integer index_x = 0, index_y = 0;
    public static final int ITEMS_LIMIT = 20;
    public static final Integer INITIAL_X = 420, INITIAL_Y = 740;

    private Vector2 position;

    public static final int WIDTH = 75, HEIGHT = 80;
    public static ArrayList<Rectangle> rectangles = new ArrayList<>();

    @Getter @Setter
    private String name;
    @Getter @Setter
    private boolean equipped;

    public ItemToBeDrawn(){
//        name = getClass().getSimpleName();
        name = "Saber";
        if (Inventory.itemsToBeDrawn.size() < ITEMS_LIMIT) {
            if (index_x % 4 == 0 && index_x != 0){
                index_x = 0;
                index_y++;
            }
            position = new Vector2(INITIAL_X + ((WIDTH + 6) * index_x++), INITIAL_Y - ((HEIGHT + 9) * index_y));
            Vector3 positionRect = new Vector3(position.x, position.y, 0f);
            rectangles.add(new Rectangle(positionRect.x, positionRect.y, WIDTH, HEIGHT + 5f));
        } else{
            size = 0;
        }
    }

    public void render(SpriteBatch spriteBatch, String name){
        if (equipped) {
            Sprite equip = new Sprite(Images.getItemDraw("Equipped"));
            equip.setSize(67,74);
            equip.setPosition(position.x + 2f, position.y + 12f);
            equip.draw(spriteBatch);
        }
        Images.getItemDraw(name).setPosition(position.x,position.y);
        Images.getItemDraw(name).draw(spriteBatch);
    }

    @Override
    public String toString(){
        return name;
    }

}
