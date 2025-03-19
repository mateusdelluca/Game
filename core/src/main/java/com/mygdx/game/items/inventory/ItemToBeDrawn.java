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
import com.mygdx.game.entities.Boy;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.screens.Inventory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.screens.Inventory.*;

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
    @Getter
    private int index;

    public ItemToBeDrawn(String name){
//        name = getClass().getSimpleName();
        this.name = name;
        if (Inventory.itemsToBeDrawn.size() < ITEMS_LIMIT) {
            if (index_x % 4 == 0 && index_x != 0){
                index_x = 0;
                index_y++;
            }
            index = index_x;
            position = new Vector2(INITIAL_X + ((WIDTH + 6) * (index_x)), INITIAL_Y - ((HEIGHT + 4) * index_y));
            positions.add(position);
            index_x++;
//            Vector3 positionRect = new Vector3(position.x, position.y, 0f);
//            rectangles.add(new Rectangle(positionRect.x, positionRect.y, WIDTH, HEIGHT));
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Sprite item = Images.getItemDraw(name);
        if (equipped) {
            Sprite equip = new Sprite(Images.getItemDraw("Equipped"));
            equip.setSize(67, 74);
            equip.setPosition(position.x + 2f, position.y + 12f);
            equip.draw(spriteBatch);
        }
        if (position != null) {
            item.setPosition(position.x, position.y);
            item.draw(spriteBatch);
        }
        if ((Images.getItemDraw(name).getBoundingRectangle().contains(positionsToFill.get(index))
            || rectangles2.isEmpty()) && index < 20){
            Rectangle rectangle = Images.getItemDraw(name).getBoundingRectangle();
        if (!rectangles2.contains(rectangle))
            rectangles2.add(rectangle);
    }
        update();
    }

    @Override
    public void updateItem() {

    }

    @Override
    public void updateItem(World world) {

    }

    @Override
    public void update() {
//        for (int index = 0; index < Math.min(rectangles2.size(), ITEMS_LIMIT); index++) {
        if (!rectangles2.isEmpty() && rectangles2.size() > index)
            if (rectangles2.get(index).contains(mouseX, mouseY)) {
                    contains[index] = true;
                } else {
                    contains[index] = false;
                }
    //        }
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
        if (contains[index] && click >= 2) {
            equipped = !equipped;
            if (name.equals("Rifle")) {
                Boy.throwing = false;
                Rifle.showingNumbBullets = equipped;
                Boy.degrees = 0f;
            } else {
                if (name.equals("NinjaStar")) {
                    Boy.degrees = 0f;
                    Boy.throwing = equipped;
                    Rifle.showingNumbBullets = false;
                } else{
                    Boy.nameOfAnimation = "BOY_IDLE";
                }
            }
            click = 0;
        }
    }

}
