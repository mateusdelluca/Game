package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.TreeMap;

import static com.mygdx.game.images.Images.*;

public class Stats extends State {

    private BitmapFont stats_font, level_font, points_font;

    private final float WIDTH_RECT = 20f, HEIGHT_RECT = 20f;

    public static float mouseX, mouseY;

    private final SpriteBatch spriteBatch = new SpriteBatch();

    private final TreeMap<String, Rectangle> statsRectangles = new TreeMap<>();
    private final TreeMap<String, Rectangle> statsCoordinates = new TreeMap<>();

    public static int[] stats_values = new int[6];

    public static int points = 10, exp_Points = 0, base_level = 1;

    public static final String[] VALUES = new String[7];

    public Stats() {
        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        stats_font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        stats_font.getData().setScale(0.35f,0.35f);

        level_font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        level_font.getData().setScale(0.4f,0.4f);

        points_font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        points_font.getData().setScale(0.9f,0.9f);

        VALUES[0] = "DEX";
        VALUES[1] = "VIT";
        VALUES[2] = "CRIT";

        VALUES[3] = "STR";
        VALUES[4] = "AGI";
        VALUES[5] = "WSD";

        VALUES[6] = "CLOSE_BUTTON";

        statsRectangles.put(VALUES[0], new Rectangle(1010, 572, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(VALUES[1], new Rectangle(1010, 600, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(VALUES[2], new Rectangle(1010, 627, WIDTH_RECT, HEIGHT_RECT));

        statsRectangles.put(VALUES[3], new Rectangle(1155, 572, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(VALUES[4], new Rectangle(1155, 600, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(VALUES[5], new Rectangle(1155, 627, WIDTH_RECT, HEIGHT_RECT));

        statsRectangles.put(VALUES[6], new Rectangle(1170, 410, 30,30));






        statsCoordinates.put(VALUES[0], new Rectangle(1010, 627, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(VALUES[1], new Rectangle(1010, 600, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(VALUES[2], new Rectangle(1010, 572, WIDTH_RECT, HEIGHT_RECT));

        statsCoordinates.put(VALUES[3], new Rectangle(1155, 627, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(VALUES[4], new Rectangle(1155, 600, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(VALUES[5], new Rectangle(1155, 572, WIDTH_RECT, HEIGHT_RECT));
    }

    @Override
    public void update() {
        //TODO: analizar qual melhor maneira para atualizar valor de hp devido o aumento em vitalidade
        // e como desenhar na tela em tempo que abre o stage Stats
        PowerBar.hp_0 = 150f + (5f * stats_values[1]);
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

        for (int index = 0; index < stats_values.length; index++) {
            stats_font.setColor(Color.BLACK);
            stats_font.draw(spriteBatch, "" + stats_values[index], (statsCoordinates.get(VALUES[index]).x - 20), (statsCoordinates.get(VALUES[index]).y - 120));
            stats_font.setColor(Color.WHITE);
            stats_font.draw(spriteBatch, "" + stats_values[index], (statsCoordinates.get(VALUES[index]).x - 19), (statsCoordinates.get(VALUES[index]).y + 1 - 120));
        }

        level_font.setColor(Color.BLACK);
        level_font.draw(spriteBatch, "" + base_level, 935, 550);
        level_font.setColor(Color.WHITE);
        level_font.draw(spriteBatch, "" + base_level, 935 - 1, 550 + 1);


        points_font.setColor(Color.BLACK);
        points_font.draw(spriteBatch, "" + points, 1140, 555);
        points_font.setColor(Color.WHITE);
        points_font.draw(spriteBatch, "" + points, 1140 - 1, 555 + 1);

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
                if (points > 0) {
                    stats_values[index]++;
                    points--;
                    System.out.println(VALUES[index] + " " + stats_values[index]);
                    System.out.println(statsRectangles.get(VALUES[index]));
                }
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
