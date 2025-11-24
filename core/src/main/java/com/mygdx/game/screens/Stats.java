package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Character_Features;
import com.mygdx.game.images.Images;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;

import java.util.TreeMap;

import static com.mygdx.game.entities.Player.character_features;
import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.screens.levels.Level_Manager.camera;
import static com.mygdx.game.screens.levels.Level_Manager.spriteBatch;

public class Stats extends State {

    private BitmapFont stats_font, level_font, points_font, points2_font;

    private final float WIDTH_RECT = 20f, HEIGHT_RECT = 20f;

    private final float WIDTH_CLOSE_BUTTON = 30f, HEIGHT_CLOSE_BUTTON = 30f;

    public static float mouseX, mouseY;
    public static final int CLOSE_BUTTON = 6;

    private final SpriteBatch spriteBatch = new SpriteBatch();

    private final TreeMap<String, Rectangle> statsRectangles = new TreeMap<>();
    private final TreeMap<String, Rectangle> statsCoordinates = new TreeMap<>();

    public static int points = 10, exp_Points = 1, base_level = 1;

    public static final int STATS_SIZE = 6;
    public static final int DEX = 0, VIT = 1, CRIT = 2, STR = 3, AGI = 4, WSD = 5;

    public static final String[] KEYS = new String[7];

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

        points2_font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        points2_font.getData().setScale(0.5f,0.5f);

        KEYS[DEX] = "DEX";
        KEYS[VIT] = "VIT";
        KEYS[CRIT] = "CRIT";

        KEYS[STR] = "STR";
        KEYS[AGI] = "AGI";
        KEYS[WSD] = "WSD";

        KEYS[CLOSE_BUTTON] = "CLOSE_BUTTON";

        statsRectangles.put(KEYS[DEX], new Rectangle(1010, 420, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(KEYS[VIT], new Rectangle(1010, 450, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(KEYS[CRIT], new Rectangle(1010, 470, WIDTH_RECT, HEIGHT_RECT));

        statsRectangles.put(KEYS[STR], new Rectangle(1155, 420, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(KEYS[AGI], new Rectangle(1155, 450, WIDTH_RECT, HEIGHT_RECT));
        statsRectangles.put(KEYS[WSD], new Rectangle(1155, 470, WIDTH_RECT, HEIGHT_RECT));

        statsRectangles.put(KEYS[CLOSE_BUTTON], new Rectangle(1175, 270, WIDTH_CLOSE_BUTTON, HEIGHT_CLOSE_BUTTON));

        statsCoordinates.put(KEYS[DEX], new Rectangle(1010, 727, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(KEYS[VIT], new Rectangle(1010, 700, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(KEYS[CRIT], new Rectangle(1010, 672, WIDTH_RECT, HEIGHT_RECT));

        statsCoordinates.put(KEYS[STR], new Rectangle(1155, 727, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(KEYS[AGI], new Rectangle(1155, 700, WIDTH_RECT, HEIGHT_RECT));
        statsCoordinates.put(KEYS[WSD], new Rectangle(1155, 672, WIDTH_RECT, HEIGHT_RECT));
    }

    @Override
    public void update() {
        //TODO: analizar qual melhor maneira para atualizar valor de hp devido o aumento em vitalidade
        // e como desenhar na tela em tempo que abre o stage Stats

//        char_features.update();
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
        character_features.update();

        spriteBatch.begin();

        printScreen.draw(spriteBatch);

        stats.setPosition(800f, 380f);
        atributes.setPosition(800f, 200);
        stats.draw(spriteBatch);
        atributes.draw(spriteBatch);

        for (int index = 0; index < character_features.getStats_values().length; index++) {
            stats_font.setColor(Color.BLACK);
            stats_font.draw(spriteBatch, "" + character_features.getStats_values()[index], (statsCoordinates.get(KEYS[index]).x - 20), (statsCoordinates.get(KEYS[index]).y - 120));
            stats_font.setColor(Color.WHITE);
            stats_font.draw(spriteBatch, "" + character_features.getStats_values()[index], (statsCoordinates.get(KEYS[index]).x - 19), (statsCoordinates.get(KEYS[index]).y + 1 - 120));
        }

        level_font.setColor(Color.BLACK);
        level_font.draw(spriteBatch, "" + base_level, 935, 650);
        level_font.setColor(Color.WHITE);
        level_font.draw(spriteBatch, "" + base_level, 935 - 1, 650 + 1);


        points2_font.setColor(Color.BLACK);
        points2_font.draw(spriteBatch, "   Defense: " + character_features.getDef(), 830, 350);
        points2_font.draw(spriteBatch, "   Attack: " + character_features.getAttack(), 830, 315);
        points2_font.draw(spriteBatch, "   Max HP: " + PowerBar.maxHP, 830, 275);
        points2_font.draw(spriteBatch, "   Max SP: " + PowerBar.maxSP, 830, 235);

        points2_font.draw(spriteBatch, "Max Power: " + PowerBar.maxPower, 1000, 235);

        points2_font.setColor(Color.WHITE);
        points2_font.draw(spriteBatch, "   Defense: " + character_features.getDef(), 830 - 2, 350 + 2);
        points2_font.draw(spriteBatch, "   Attack: " + Boy.attack, 830 - 2, 315 + 2);
        points2_font.draw(spriteBatch, "   Max HP: " + PowerBar.maxHP, 830 - 2, 275 + 2);
        points2_font.draw(spriteBatch, "   Max SP: " + PowerBar.maxSP, 830 - 2, 235 + 2);

        points2_font.draw(spriteBatch, "Max Power: " + PowerBar.maxPower, 1000 - 2, 235 + 2);

        points_font.setColor(Color.BLACK);
        points_font.draw(spriteBatch, "" + points, 1140, 655);
        points_font.setColor(Color.WHITE);
        points_font.draw(spriteBatch, "" + points, 1140 - 1, 655 + 1);


        spriteBatch.draw(Images.power, 885, 488, Math.max(25f, exp_Points * 5f), Images.power.getHeight());

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
            StateManager.setStates(StateManager.States.LEVEL_MANAGER);
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
                StateManager.setStates(StateManager.States.LEVEL_MANAGER);
//                System.out.println(mouseX + " " + mouseY + " " + statsRectangles.get("CLOSE_BUTTON").getX() + " " + statsRectangles.get("CLOSE_BUTTON").getY());
            }
            System.out.println(mouseX + " " + mouseY);
        }

        for (int index = 0; index < character_features.getStats_values().length; index++){
            if (statsRectangles.get(KEYS[index]).contains(mouseX, mouseY)){
                if (points > 0) {
                    character_features.getStats_values()[index]++;
                    points--;
                    System.out.println(KEYS[index] + " " + character_features.getStats_values()[index]);
                    System.out.println(statsCoordinates.get(KEYS[index]));
                }
            }
        }
        System.out.println(mouseX + " " + mouseY);
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
