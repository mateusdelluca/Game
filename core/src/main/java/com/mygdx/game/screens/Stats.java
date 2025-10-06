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

import java.util.TreeMap;

import static com.mygdx.game.images.Images.*;

public class Stats extends State {

    private BitmapFont font;

    private final float WIDTH_RECT = 20f, HEIGHT_RECT = 20f;

    public static float mouseX, mouseY;

    private final SpriteBatch spriteBatch = new SpriteBatch();

    private final TreeMap<String, Rectangle> statsRectangles = new TreeMap<>();

    public static int[] stats_values = new int[6];

    public static int points = 0, exp_Points = 0, base_level = 1;

    public static final String[] VALUES = new String[8];

    public Stats() {
        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.5f);

        VALUES[0] = "DEX";
        VALUES[1] = "VIT";
        VALUES[2] = "CRIT";

        VALUES[3] = "STR";
        VALUES[4] = "AGI";
        VALUES[5] = "WSD";

        VALUES[6] = "CLOSE_BUTTON";

        statsRectangles.put("DEX", new Rectangle(1010, 572, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put("VIT", new Rectangle(1010, 600, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put("CRIT", new Rectangle(1010, 627, WIDTH_RECT, HEIGHT_RECT));

        statsRectangles.put("STR", new Rectangle(1155, 572, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put("AGI", new Rectangle(1155, 600, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put("WSD", new Rectangle(1155, 627, WIDTH_RECT, HEIGHT_RECT));

        statsRectangles.put("CLOSE_BUTTON", new Rectangle(1170, 410, 30,30));
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
        stats.setPosition(800f, 280f);
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
            if (statsRectangles.get("CLOSE_BUTTON").contains(mouseX, mouseY)) {
                StateManager.setStates(StateManager.States.LEVEL);
            }
        }

        for (int index = 0; index < stats_values.length; index++){
            if (statsRectangles.get(VALUES[index]).contains(mouseX, mouseY)){
                stats_values[index]++;
                System.out.println(VALUES[index] + " " + stats_values[index]);
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
        mouseY = Gdx.input.getY();
        return false;
    }

    @Override
    public boolean scrolled ( float amountX, float amountY){
        return false;
    }
}
