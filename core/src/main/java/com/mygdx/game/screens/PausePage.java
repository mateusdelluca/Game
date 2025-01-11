package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.images.Images;
import com.mygdx.game.Application;
import com.mygdx.game.manager.State;
import com.mygdx.game.screens.levels.Level_Manager;
import com.mygdx.game.sfx.Sounds;

public class PausePage extends State {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    public static final int BOX_WIDTH = 400, BOX_HEIGHT = 500;

    public static final int RETURN = 3;
    public static final int SAVEGAME = 2;
    public static final int LOADGAME = 1;
    public static final int EXIT = 0;

    public static final int NUM_OPTIONS = 4;

    private boolean[] isTouched = new boolean[NUM_OPTIONS];

    private int[] x = new int[NUM_OPTIONS];
    private int[] y = new int[NUM_OPTIONS];

    private String[] options = new String[NUM_OPTIONS];

    private Rectangle[] options_rects = new Rectangle[NUM_OPTIONS];
    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    private int optionChoosed = -1;

    private BitmapFont font;
    private SpriteBatch spriteBatch = new SpriteBatch();

    private Camera camera = new OrthographicCamera(WIDTH, HEIGHT);
    private Viewport viewport;

    public PausePage(){
        for(int index = EXIT; index <= RETURN; ++index) {
            this.x[index] = 850;
            this.y[index] = 370 + 100 * index;
            this.options_rects[index] = new Rectangle(this.x[index], this.y[index] - 20, 360, 30);
        }

        options[RETURN] = "  RETURN";
        options[SAVEGAME] = "SAVE GAME";
        options[LOADGAME] = "LOAD GAME";
        options[EXIT] = "     EXIT";

        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.2f);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        viewport = new ScreenViewport(camera);
    }

    public void update(){
//        Jogo.currentScreen = getClass().toString();

        camera.update();

        int counter = 0;
        for(int index = EXIT; index <= RETURN; ++index) {
            if (this.mouseRectangle.overlaps(this.options_rects[index])) {
                this.isTouched[index] = true;
                this.optionChoosed = index;
            } else {
                this.isTouched[index] = false;
                ++counter;
            }
        }
        if (counter == NUM_OPTIONS) {
            counter = 0;
            this.optionChoosed = -1;
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        update();

        spriteBatch.setProjectionMatrix(camera.combined);
//        app.jogo.levelManager.currentLevel.background.render(Level_Manager.currentLevelName);
//        app.jogo.levelManager.currentLevel.renderObjects();
        spriteBatch.begin();
        spriteBatch.setColor(1f, 1f, 1f, 0.5f);
        spriteBatch.draw(Images.pauseBox, 765, 250, BOX_WIDTH, BOX_HEIGHT);
        spriteBatch.setColor(1f, 1f, 1f, 1f);
        for(int index = EXIT; index <= RETURN; ++index) {
            font.setColor(Color.BLACK);
            font.draw(spriteBatch, this.options[index], this.x[index] + 2, this.y[index] + 2);
            font.setColor(Color.WHITE);
            font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            if (this.isTouched[index]) {
                font.setColor(Color.BLUE);
                font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            }
        }
        spriteBatch.end();
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        Images.pauseBox.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
//        if (keycode == Input.Keys.ESCAPE){
//            app.setScreen(app.jogo.levelManager.currentLevel);
//            Sounds.PAUSE_SCREEN.pause();
//            if (!Sounds.LEVEL1.isPlaying()) {
////                Sounds.LEVEL1.setPosition(Sounds.pause_musicPosition);
//                Sounds.LEVEL1.play();
//            }
//            Gdx.input.setInputProcessor(app.jogo.levelManager.currentLevel);
//        }
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
        switch (this.optionChoosed) {
            case RETURN: {
//                app.setScreen(app.jogo.levelManager.currentLevel);
////                Sounds.PAUSE_SCREEN.pause();
//                if (!Sounds.LEVEL1.isPlaying()) {
////                    Sounds.LEVEL1.setPosition(Sounds.pause_musicPosition);
//                    Sounds.LEVEL1.play();
//                }
//                Gdx.input.setInputProcessor(app.jogo.levelManager.currentLevel);
                break;
            }
            case SAVEGAME:{
//                app.setScreen(app.jogo.saveMenu);
//                Gdx.input.setInputProcessor(app.jogo.saveMenu);
                break;
            }
            case LOADGAME:{
//                app.setScreen(app.jogo.loadScreen);
//                Gdx.input.setInputProcessor(app.jogo.loadScreen);
                break;
            }
            case EXIT: {
//                app.setScreen(app.jogo.mainMenu);
//                Gdx.input.setInputProcessor(app.jogo.mainMenu);
//                Sounds.PAUSE_SCREEN.stop();
                break;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Suponha que você tenha uma câmera (por exemplo, OrthographicCamera) configurada
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        viewport.unproject(worldCoordinates);

        // Agora 'worldCoordinates' contém as coordenadas do mundo
        float worldX = worldCoordinates.x;
        float worldY = worldCoordinates.y;

        this.mouseRectangle.setPosition(worldX, worldY);
//        System.out.println(worldX + " " + worldY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
