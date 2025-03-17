package com.mygdx.game.items.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.BodyData;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.screens.Inventory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.screens.Inventory.mouseX;
import static com.mygdx.game.screens.Inventory.mouseY;

public class ItemToBeDrawn implements Item {

    public static Integer index_x = 0, index_y = 0;
    public static final int ITEMS_LIMIT = 20;
    public static final Integer INITIAL_X = 420, INITIAL_Y = 740;
    @Getter
    private Vector2 position = new Vector2();

    @Getter
    public static ArrayList<Vector2> positions = new ArrayList<>();
    public static final int WIDTH = 75, HEIGHT = 85;
    public static ArrayList<Rectangle> rectangles2 = new ArrayList<>();
    private int click;

    private boolean contains[] = new boolean[ITEMS_LIMIT];

    @Getter @Setter
    private String name;
    @Getter @Setter
    private boolean equipped;

    public ItemToBeDrawn(String name){
//        name = getClass().getSimpleName();
        this.name = name;
        if (Inventory.itemsToBeDrawn.size() < ITEMS_LIMIT) {
            if (index_x % 4 == 0 && index_x != 0){
                index_x = 0;
                index_y++;
            }
            position = new Vector2(INITIAL_X + ((WIDTH + 6) * (index_x++)), INITIAL_Y - ((HEIGHT + 4) * index_y));
            positions.add(position);
//            Vector3 positionRect = new Vector3(position.x, position.y, 0f);
//            rectangles.add(new Rectangle(positionRect.x, positionRect.y, WIDTH, HEIGHT));
        }
    }

    public void render(SpriteBatch spriteBatch, String name){
        update();
        if (equipped) {
            Sprite equip = new Sprite(Images.getItemDraw("Equipped"));
            equip.setSize(67,74);
            equip.setPosition(position.x + 2f, position.y + 12f);
            equip.draw(spriteBatch);
        }
        if (position != null) {
            Images.getItemDraw(name).setPosition(position.x, position.y);
            Images.getItemDraw(name).draw(spriteBatch);
        }
        if (!rectangles2.contains(Images.getItemDraw(name).getBoundingRectangle()))
            rectangles2.add(Images.getItemDraw(name).getBoundingRectangle());
    }


    @Override
    public void render(SpriteBatch s) {

    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {
        for (int index = 0; index < rectangles2.size(); index++) {
            if (rectangles2.get(index).contains(mouseX, mouseY)) {
                contains[index] = true;
            } else {
                contains[index] = false;
            }
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            equip();
        }
    }

    @Override
    public void setUserData(Body body) {

    }

    @Override
    public void setUserData(String name) {

    }

    @Override
    public BodyData getBodyData() {
        return null;
    }

    @Override
    public void setVisible(boolean b) {

    }

    @Override
    public String toString(){
        return name;
    }

    public void equip(){
        if (click < 2)
            click++;
        else
            click = 0;
        for (int index = 0; index < rectangles2.size(); index++) {
            if (contains[index] && click >= 2) {
                equipped = !equipped;
                if (name.equals("Rifle"))
                    Rifle.showingNumbBullets = equipped;
                click = 0;
            }
        }

    }

}
