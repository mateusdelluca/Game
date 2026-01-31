package com.mygdx.game.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.screens.Inventory.*;
import static com.mygdx.game.screens.Inventory.mouseX;
import static com.mygdx.game.screens.Inventory.mouseY;
import static com.mygdx.game.screens.Inventory.rectangles;
import static com.mygdx.game.screens.Stats.level_font;

public class Item extends Objeto {
    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected int index;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    protected boolean beenTaken;

    private int click;

    public static final Integer INITIAL_X = 420, INITIAL_Y = 740;

    public static final int ITEMS_LIMIT = 20;

    public static boolean[] containsMouseInsideSlot = new boolean[ITEMS_LIMIT];

    public static boolean[] equipped = new boolean[ITEMS_LIMIT];

    public static Integer index_x = 0, index_y = 0;


    public static final int WIDTH = 75, HEIGHT = 85;

    public static final String[] NAME_ITEMS = {"Sword", "Laser_Headset", "Star", "NinjaRope", "Crystal", "CrystalRed", "Rifle", "JetPack", "Saber"};

    protected Sprite itemSprite;

    @Getter @Setter
    private Vector2 position = new Vector2();

    @Getter
    public static ArrayList<Vector2> positions = new ArrayList<>();


    public Item(){

    }

    public Item(float width, float height, String name){
        super(width, height);
        this.name = name;
        this.itemSprite = Images.getItemDraw(name);
        init();
    }

    public Item(float width, float height, Vector2 transformPosition){
        super(width, height);
    }

    public void init(){
        quantity++;

//        if (index_x % 4 == 0 && index_x != 0) {
//            index_x = 0;
//            index_y++;
//        }
//        position = new Vector2(INITIAL_X + ((WIDTH) * (index_x)), INITIAL_Y - ((HEIGHT) * index_y));
//        positions.add(position);
//        index_x++;
    }

    public void addItemToInventory(){
        if (beenTaken){
            if (((treeMap_Items.size() + 1) < ITEMS_LIMIT) && ((itemsToBeDrawn.size() + 1) < ITEMS_LIMIT) && !treeMap_Items.isEmpty()
                && !itemsToBeDrawn.isEmpty()) {
                itemsToBeDrawn.add(this);
                treeMap_Items.put(name, this);
                index = itemsToBeDrawn.size() - 1;
            }
            if (treeMap_Items.isEmpty()){
                treeMap_Items.put(name, this);
            }
            if (itemsToBeDrawn.isEmpty()) {
                itemsToBeDrawn.add(this);
            }
        }
        System.out.println(treeMap_Items.get(name));
    }



    public boolean contains() {
        if (!rectangles.isEmpty() && rectangles.size() > index)
            return (rectangles.get(index).contains(mouseX, mouseY));
        return false;
    }

//    public void drawItemInSlot(SpriteBatch spriteBatch) {
//        if (this.beenTaken) {
//            itemSprite.setPosition(positionsToFill.get(index).x, positionsToFill.get(index).y);
////            itemSprite.setOriginCenter();
//            itemSprite.draw(spriteBatch);
//            drawQuantity(spriteBatch);
//        }
//    }

    public void drawItemInSlot(SpriteBatch spriteBatch, float x, float y) {
        itemSprite.setOrigin(0,0);
        itemSprite.setPosition(x, y);
        itemSprite.draw(spriteBatch);
        drawQuantity(spriteBatch);
    }
    public void drawItemInSlot(SpriteBatch spriteBatch, float x, float y, String name) {
        itemSprite = Images.getItemDraw(name);
        itemSprite.setOrigin(0,0);
        itemSprite.setPosition(x, y);
        itemSprite.draw(spriteBatch);
        drawQuantity(spriteBatch);
    }

    public void equip() {
        if (containsMouseInsideSlot[index] && click >= 2) {
            for (Item item1 : itemsToBeDrawn) {
                if (item1.equals(this)) {
                    equipped[index] = !equipped[index];
                }
//                else {
//                    if (itemsToBeDrawn.contains(item1))
//                        itemsToBeDrawn.add(item1);
//                }
            }
            if (equipped[index]) {
                for (int i = 0; i < itemsToBeDrawn.size(); i++) {
                    if (i != index)
                        equipped[i] = false;
                }
            }
            click = 0;
        }
    }

    protected void drawQuantity(SpriteBatch spriteBatch){
        level_font.draw(spriteBatch, "" + quantity, positionsToFill.get(index).x + WIDTH, positionsToFill.get(index).y);
    }

    @Override
    public void update() {
       super.update();
    }

    public void update2(){
        containsMouseInsideSlot[index] = contains();
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            click++;
            equip();
//            System.out.println(click);
        }
    }

    @Override
    public String toString(){
        return name;
    }
}
