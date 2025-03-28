package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.mygdx.game.images.Images.inventory;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.*;


public class Inventory extends State {

    public static CopyOnWriteArrayList<ItemToBeDrawn> itemsToBeDrawn = new CopyOnWriteArrayList<>();

    public static TreeMap<String, ItemToBeDrawn> treeMap_Items = new TreeMap<>();


    public static float mouseX, mouseY;
    private Rectangle close_button = new Rectangle(1435f,720f,50,50);

    public static final int WIDTH = 85, HEIGHT = 97;

    public static final int WIDTH2 = 80, HEIGHT2 = 90;
    public static final Integer INITIAL_X = 415, INITIAL_Y = 740;

    public static ArrayList<Vector2> positionsToFill = new ArrayList<>();

    public static ArrayList<Rectangle> rectangles = new ArrayList<>();

    private ShapeRenderer shapeRenderer;
    public Inventory(){
//        for (int i = 0; i < 20; i++) {
//            addItemToInventory(new ItemToBeDrawn());
//        }
//        addItemToInventory(new ItemToBeDrawn("NinjaStar"));
        fillRects();
        shapeRenderer = new ShapeRenderer();
    }

    private SpriteBatch spriteBatch = new SpriteBatch();

    @Override
    public void update() {
//        for (int i = 0; i < itemsToBeDrawn.size(); i++){
//            for (int j = itemsToBeDrawn.size() - 1; j >= 0; j--){
//                if (i == j)
//                    continue;
//                if (itemsToBeDrawn.get(i).getName().equals(itemsToBeDrawn.get(j).getName())){
//                    itemsToBeDrawn.set(j, itemsToBeDrawn.get(j));
//                }
//            }
//        }
    }

    public static void addItemToInventory(ItemToBeDrawn i1){
       ItemToBeDrawn itemFirst = null;
       if (itemsToBeDrawn.isEmpty()) {
           itemFirst = i1;
            itemsToBeDrawn.add(itemFirst);
            return;
        }
        if (itemsToBeDrawn.size() < ITEMS_LIMIT) {
            for (ItemToBeDrawn i2 : itemsToBeDrawn) {
                if(i2 == i1) {
                    continue;
                }
                itemsToBeDrawn.add(i1);
                treeMap_Items.put(i1.getName(), i1);
                System.out.println(treeMap_Items.get(i1.getName()));
            }
        }
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        update();
        spriteBatch.begin();
        inventory.setPosition(350, 200);
        inventory.draw(spriteBatch);
        for (Vector2 vec2 : positionsToFill)
            spriteBatch.draw(Images.spaceItem, vec2.x, vec2.y);
        for (ItemToBeDrawn itemToBeDrawn : treeMap_Items.sequencedValues()) {
            itemToBeDrawn.render(spriteBatch);
        }
        spriteBatch.end();

        render(shapeRenderer);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.I){
            StateManager.setState(StateManager.States.LEVEL);
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (close_button.contains(mouseX, mouseY)) {
                StateManager.setState(StateManager.States.LEVEL);
            }
//            for (ItemToBeDrawn itemToBeDrawn : itemsToBeDrawn.values()){
//                itemToBeDrawn.touchDown(screenX, screenY, pointer, button);
//            }
//            for (ItemToBeDrawn itemToBeDrawn : itemsToBeDrawn){
//                for (Vector2 pos : positionsToFill)
//                    equipped[itemToBeDrawn.getIndex()] = (itemToBeDrawn.getPosition().equals(pos) || rectangles2.contains(new Rectangle(mouseX, mouseY, 3, 9)));
//            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    private void fillRects() {
        int index_X = 0;
        int index_Y = 0;

        for (int i = 0; i < ITEMS_LIMIT; i++) {
            if (index_X % 4 == 0 && index_X != 0) {
                index_X = 0;
                index_Y++;
            }
            Vector2 position = new Vector2(INITIAL_X + ((WIDTH) * (index_X++)), INITIAL_Y - ((HEIGHT) * index_Y));
            positionsToFill.add(i, position);
            rectangles.add(i, new Rectangle(positionsToFill.get(i).x, positionsToFill.get(i).y, WIDTH, HEIGHT));
        }
    }

    public void render(ShapeRenderer sr){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(0,1,0,105/255f));
        for (ItemToBeDrawn itemToBeDrawn : treeMap_Items.sequencedValues()) {
            int i = itemToBeDrawn.getIndex();
            if (ItemToBeDrawn.equipped[i])
               sr.rect(rectangles.get(i).x + 5f, rectangles.get(i).y + 5f, WIDTH2 - 5f, HEIGHT2);
        }
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
