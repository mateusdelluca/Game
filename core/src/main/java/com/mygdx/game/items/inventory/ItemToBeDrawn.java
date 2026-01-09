package com.mygdx.game.items.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.items.NinjaRope;
import com.mygdx.game.system.BodyData;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.Rifle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

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

    public static boolean[] contains = new boolean[ITEMS_LIMIT];

    @Getter @Setter
    private String name;

    public static boolean[] equipped = new boolean[ITEMS_LIMIT];
    @Getter
    private int index;
    private Item item;
    
    public static HashMap<Item, Boolean> items = new HashMap<>();
    public ItemToBeDrawn(Item item){
//        name = getClass().getSimpleName();
        this.item = item;
        this.name = item.toString();
        addItemToInventory(this);
        System.out.println(name);
        treeMap_Items.put(name, this);
//        if (!treeMap_Items.isEmpty())
        this.index = treeMap_Items.size() - 1;
        item.setIndex(index);
        if (!items.containsKey(item))
           items.put(item, equipped[index]);
//        if (name.equals(equip))
//            return;
//        if (Inventory.itemsToBeDrawn.size() < ITEMS_LIMIT) {
//            if (index_x % 4 == 0 && index_x != 0){
//                index_x = 0;
//                index_y++;
//            }
//
//            position = new Vector2(INITIAL_X + ((WIDTH + 6) * (index_x)), INITIAL_Y - ((HEIGHT + 4) * index_y));
//            positions.add(position);
//            index_x++;
//            index2 = itemsToBeDrawn.isEmpty() ? 0 : itemsToBeDrawn.size() - 1;
//            index = index2;
//            Vector3 positionRect = new Vector3(position.x, position.y, 0f);
//            rectangles.add(new Rectangle(positionRect.x, positionRect.y, WIDTH, HEIGHT));
//        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Sprite item = Images.getItemDraw(name);
//        if (equipped[index]) {
//            Sprite equip = new Sprite(Images.getItemDraw("Equipped"));
//            equip.setSize(67, 74);
//            equip.setPosition(positionsToFill.get(index).x + 2f, positionsToFill.get(index).y + 12f);
//            equip.draw(spriteBatch);
//        }
        for (int i = 0; i < treeMap_Items.sequencedValues().size(); i++) {
            if (item != null) {
                item.setPosition(positionsToFill.get(index).x, positionsToFill.get(index).y);
                item.draw(spriteBatch);
            }
        }
//        if ((Images.getItemDraw(name).getBoundingRectangle().contains(positionsToFill.get(index))
//            || rectangles2.isEmpty()) && index < 20){
//            Rectangle rectangle = Images.getItemDraw(name).getBoundingRectangle();
//        if (!rectangles2.contains(rectangle))
//            rectangles2.add(rectangle);
//    }
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
        contains[index] = contains();
//        System.out.println(contains[index]);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            click++;
            equip();
            System.out.println(click);
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
    public boolean isVisible() {
        return false;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public void setIndex(int index) {

    }

    public void equip(){
        if (contains[index] && click >= 2) {

            for (Item item1 : items.keySet()) {
                if (item1.equals(item)){
                    equipped[index] = !equipped[index];
                } else {
                    if (items.containsKey(item1))
                        items.replace(item1, false);
                }
            }
            if (equipped[index]) {
                for (int i = 0; i < treeMap_Items.sequencedValues().size(); i++) {
                    if (i != index)
                        equipped[i] = false;
                }
            }
            click = 0;
        }
//        if (contains[index] && click >= 2) {
//            equipped[index] = !equipped[index];
//            System.out.println(equipped[index]);
//            if (name.equals("Sword")){
//                Boy.usingSword = equipped[index];
//                Boy.shooting = false;
//                Boy.saber_taken = false;
//                NinjaRope.isActive2 = false;
//                Boy.throwing = false;
//                Boy.ropeShoot = false;
//            }
//            else{
//            if (name.equals("Rifle")) {
//                Boy.throwing = false;
//                Rifle.showingNumbBullets = equipped[index];
//                Boy.shooting = equipped[index];
//                Boy.degrees = 0f;
//                Boy.saber_taken = false;
//                NinjaRope.isActive2 = false;
//                Boy.ropeShoot = false;
//            } else {
//                if (name.contains("NinjaStar")) {
//                    Boy.degrees = 0f;
//                    Boy.throwing = equipped[index];
//                    Rifle.showingNumbBullets = false;
//                    Boy.saber_taken = false;
//                    Boy.shooting = false;
//                    NinjaRope.isActive2 = false;
//                    Boy.ropeShoot = false;
//                } else {
//                    if (name.equals("JetPack"))
//                        Boy.use_jetPack = equipped[index];
//                    else {
//                        if (name.equals("Saber")) {
//                            Boy.saber_taken = equipped[index];
//                            Boy.throwing = false;
//                            Rifle.showingNumbBullets = false;
//                            NinjaRope.isActive2 = false;
//                            Boy.ropeShoot = false;
//                        } else {
//                            if (name.equals("NinjaRope")) {
//                                NinjaRope.isActive2 = equipped[index];
//                                Boy.ropeShoot = equipped[index];
//                            } else {
//                                if (name.equals("Laser_Headset")) {
//                                    Boy.laser = equipped[index];
//                                } else {
//                                    NinjaRope.isActive2 = false;
//                                    Boy.ropeShoot = false;
////                              Boy.throwing = false;
////                              Rifle.showingNumbBullets = false;
//                                    //                            Boy.shooting = false;
////                              Boy.saber_taken = false;
////                              Boy.nameOfAnimation = "BOY_IDLE";
//                                }
//                            }
//                        }
//                     }
//                    }
//                }
//            }

//        }
//        unequipped();
    }


//    public void render(ShapeRenderer sr){
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        sr.begin(ShapeRenderer.ShapeType.Filled);
//        sr.setColor(new Color(1,0,0,105/255f));
//        for (Rectangle r : rectangles)
//            sr.rect(r.x, r.y, WIDTH2, HEIGHT2);
//        sr.end();
//        Gdx.gl.glDisable(GL20.GL_BLEND);
//
//    }

    public boolean contains() {
    if (!rectangles.isEmpty() && rectangles.size() > index)
        if (rectangles.get(index).contains(mouseX, mouseY)) {
            return true;
        } else {
            return false;
        }
    return false;
    }

}
