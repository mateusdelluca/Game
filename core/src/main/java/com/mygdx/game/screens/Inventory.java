package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.items.inventory.ItemToBeDrawn;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.inventory;
import static com.mygdx.game.items.inventory.ItemToBeDrawn.ITEMS_LIMIT;

public class Inventory extends State {

    public static ArrayList<ItemToBeDrawn> itemsToBeDrawn = new ArrayList<>();

    private float mouseX, mouseY;

    private Rectangle close_button = new Rectangle(1435f,720f,50,50);
    private boolean click;
    private Integer index = -1;

    public Inventory(){
        for (int i = 0; i < 20; i++) {
            addItemToInventory(new ItemToBeDrawn());
        }
    }

    private SpriteBatch spriteBatch = new SpriteBatch();

    @Override
    public void update() {
        for (int i = 0; i < ItemToBeDrawn.rectangles.size(); i++) {
            if (ItemToBeDrawn.rectangles.get(i).contains(mouseX, mouseY))
                index = i;
        }
    }

    public static void addItemToInventory(ItemToBeDrawn itemToBeDrawn){
        if (itemsToBeDrawn.size() < ITEMS_LIMIT)
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
        for (ItemToBeDrawn itemToBeDrawn : itemsToBeDrawn)
            itemToBeDrawn.render(spriteBatch, itemToBeDrawn.getName());
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT){
            if (close_button.contains(mouseX, mouseY))
                StateManager.setState(StateManager.States.PAUSE);
        }
        if (pointer >= 0 && index >= 0 && button == Input.Buttons.LEFT) {
            click = !click;
            itemsToBeDrawn.get(index).setEquipped(click);
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
        System.out.println("x: " + mouseX + " y: " +  mouseY);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
