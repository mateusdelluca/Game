package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.inventory;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.*;

public class Inventory extends State {

    public static ArrayList<ItemToBeDrawn> itemsToBeDrawn = new ArrayList<>();
    public static float mouseX, mouseY;
    private Rectangle close_button = new Rectangle(1435f,720f,50,50);

    public static ArrayList<Vector2> positionsToFill = new ArrayList<>();

    public Inventory(){
//        for (int i = 0; i < 20; i++) {
//            addItemToInventory(new ItemToBeDrawn());
//        }
        addItemToInventory(new ItemToBeDrawn("NinjaStar"));
        fillRects();
    }

    private SpriteBatch spriteBatch = new SpriteBatch();

    @Override
    public void update() {
        for (int i = 0; i < itemsToBeDrawn.size(); i++){
            for (int j = itemsToBeDrawn.size() - 1; j >= 0; j--){
                if (i == j)
                    continue;
                if (itemsToBeDrawn.get(i).getName().equals(itemsToBeDrawn.get(j).getName())){
                    itemsToBeDrawn.set(index_x, itemsToBeDrawn.get(j));
                }
            }
        }
    }

    public static void addItemToInventory(ItemToBeDrawn itemToBeDrawn){
       boolean add[] = new boolean[20];
        if (itemsToBeDrawn.isEmpty())
            itemsToBeDrawn.add(itemToBeDrawn);
        if (itemsToBeDrawn.size() < ITEMS_LIMIT) {
            for (ItemToBeDrawn itemToBeDrawn2 : itemsToBeDrawn) {
                if (!itemToBeDrawn2.getName().equals(itemToBeDrawn.getName())) {
                   add[itemToBeDrawn.getIndex()] = true;
                }
            }
        }
        for (int i = 0; i < itemsToBeDrawn.size(); i++)
            if (add[i])
                itemsToBeDrawn.add(itemToBeDrawn);
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
        for (ItemToBeDrawn itemToBeDrawn : itemsToBeDrawn) {
            itemToBeDrawn.render(spriteBatch);
        }
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

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
//            for (ItemToBeDrawn itemToBeDrawn : itemsToBeDrawn.values()){
//                for (Vector2 pos : positionsToFill)
//                    itemToBeDrawn.setEquipped(itemToBeDrawn.getPosition().equals(pos) || rectangles2.contains(new Rectangle(mouseX, mouseY, 3, 9)));
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
        int index_x = 0;
        int index_y = 0;

        for (int i = 0; i < ITEMS_LIMIT; i++) {
            index_x = i;
            if (index_x % 4 == 0 && index_x != 0) {
                index_x = 0;
                index_y++;
            }
            Vector2 position = new Vector2(INITIAL_X + ((WIDTH + 6) * (index_x)), INITIAL_Y - ((HEIGHT + 9) * index_y));
            positionsToFill.add(position);

        }
    }
}
