package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.screens.SavePage.PRINTSCREEN;

public class Stats extends State {

    private BitmapFont font;

    public static float mouseX, mouseY;

    private Rectangle close_button = new Rectangle(1435f,720f,50,50);

    private final SpriteBatch spriteBatch = new SpriteBatch();

    public Stats() {
        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.5f);
    }

    @Override
    public void update() {

    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        update();
        spriteBatch.begin();
        printScreen.draw(spriteBatch);
        stats.setPosition(450, 300);
        stats.draw(spriteBatch);
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
        spriteBatch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.Q){
            StateManager.setStates(StateManager.States.LEVEL);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (close_button.contains(mouseX, mouseY)) {
                StateManager.setStates(StateManager.States.LEVEL);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp ( int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchCancelled ( int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchDragged ( int screenX, int screenY, int pointer){
        return false;
    }

    @Override
    public boolean mouseMoved ( int screenX, int screenY){
        mouseX = Gdx.input.getX();
        mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        System.out.println(mouseX + " " + mouseY);
        return false;
    }

    @Override
    public boolean scrolled ( float amountX, float amountY){
        return false;
    }
}
