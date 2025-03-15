package com.mygdx.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.Item;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.inventory;
import static com.mygdx.game.inventory.Item.ITEMS_LIMIT;
import static com.mygdx.game.manager.StateManager.oldState;

public class Inventory extends State {


    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    public static ArrayList<com.mygdx.game.inventory.Item> items = new ArrayList<>();

    public Inventory(){
//        items.add(new com.mygdx.game.inventory.Item());
        for (int i = 0; i < 20; i++)
            new com.mygdx.game.inventory.Item();
    }

    private SpriteBatch spriteBatch = new SpriteBatch();

    @Override
    public void update() {

    }

    public static void addItemToInventory(com.mygdx.game.inventory.Item item){
        if (items.size() < ITEMS_LIMIT)
            items.add(item);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        spriteBatch.begin();
        inventory.setPosition(350, 200);
        inventory.draw(spriteBatch);
        for (com.mygdx.game.inventory.Item item : items)
            item.render(spriteBatch, Images.saber_inventory);
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
        if (keycode == Input.Keys.ESCAPE){
            StateManager.setState(StateManager.States.PAUSE);
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
    public boolean touchDown(int i, int i1, int i2, int i3) {
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
        System.out.println("x:" + i + " y:" + i1);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
