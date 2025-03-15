package com.mygdx.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.images.Images;
import com.mygdx.game.inventory.ItemToBeDrawn;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.inventory;
import static com.mygdx.game.inventory.ItemToBeDrawn.ITEMS_LIMIT;

public class Inventory extends State {


    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    public static ArrayList<ItemToBeDrawn> itemsToBeDrawn = new ArrayList<>();

    private OrthographicCamera camera = new OrthographicCamera(1920,1080);

    public Inventory(){
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        for (int i = 0; i < 20; i++)
            new ItemToBeDrawn();
    }

    private SpriteBatch spriteBatch = new SpriteBatch();

    @Override
    public void update() {
        for (int i = 0; i < ItemToBeDrawn.rectangles.size(); i++) {
            if (ItemToBeDrawn.rectangles.get(i).overlaps(mouseRectangle)) {
                itemsToBeDrawn.get(i).setEquipped(true);
            } else{
                itemsToBeDrawn.get(i).setEquipped(false);
            }
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
            itemToBeDrawn.render(spriteBatch, Images.getItemDraw("Rifle"));
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
        Vector3 worldVect3 = new Vector3(i,i1, 0f);
        camera.unproject(worldVect3);
        mouseRectangle.setPosition(worldVect3.x, worldVect3.y);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
