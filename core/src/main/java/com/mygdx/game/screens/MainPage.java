//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.images.Images;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level_Manager;
import com.mygdx.game.sfx.Sounds;

import static com.mygdx.game.Application.level_manager;
import static com.mygdx.game.sfx.Sounds.LASER_HEADSET;

public class MainPage extends State {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    public static final int NEWGAME = 2;
    public static final int LOADGAME = 1;
    public static final int EXIT = 0;

    public static final int NUM_OPTIONS = 3;

    private boolean[] isTouched = new boolean[NUM_OPTIONS];

    private int[] x = new int[NUM_OPTIONS];
    private int[] y = new int[NUM_OPTIONS];

    private String[] options = new String[NUM_OPTIONS];

    private Rectangle[] options_rects = new Rectangle[NUM_OPTIONS];
    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);
    private int optionChoosed = -1;

    private BitmapFont font;
    private SpriteBatch spriteBatch;

    private Camera camera;
    private Viewport viewport;
    private Sound shot;

    public static boolean newGame;

    public MainPage(){
        for(int index = EXIT; index <= NEWGAME; ++index) {
            this.x[index] = (1920/2) - 200;
            this.y[index] = 120 + 55 * index;
            this.options_rects[index] = new Rectangle(this.x[index], this.y[index] - 20, 360, 30);
        }
        shot = Gdx.audio.newSound(Gdx.files.internal("sounds/gun shot.wav"));
        this.options[NEWGAME] = "     NEW  GAME";
        this.options[LOADGAME] = "     LOAD GAME";
        this.options[EXIT] = "          EXIT";

        Texture t = new Texture(Gdx.files.internal("Font.png"));
        font = new BitmapFont(Gdx.files.internal("Font.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.5f);

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        if (!Sounds.PAUSE_SONG.isPlaying()) {
            Sounds.PAUSE_SONG.play();
            Sounds.PAUSE_SONG.setLooping(true);
        }
        spriteBatch = new SpriteBatch();
        viewport = new ScreenViewport(camera);
    }

    @Override
    public void create() {

    }

    public void update() {
//        Jogo.currentScreen = getClass().toString();

        camera.update();
        if (!Sounds.PAUSE_SONG.isPlaying())
            Sounds.PAUSE_SONG.play();
        int counter = 0;
        for(int index = EXIT; index <= NEWGAME; ++index) {
            if (this.mouseRectangle.overlaps(this.options_rects[index])) {
                this.isTouched[index] = true;
                this.optionChoosed = index;
            } else {
                this.isTouched[index] = false;
               ++counter;
            }
        }
        if (counter == 3) {
            counter = 0;
            this.optionChoosed = -1;
        }
    }



    @Override
    public void render() {
        update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        Sprite sprite = new Sprite(Images.splashScreen);
        sprite.setSize(WIDTH, HEIGHT);
        sprite.draw(spriteBatch);

//        font.setColor(Color.BLACK);
//        font.draw(spriteBatch, "BLUE DOME", (1920/2f - 200) + 2, (1080/2f) + 2);
//        font.setColor(Color.WHITE);
//        font.draw(spriteBatch, "blue dome", 1920/2f - 200, 1080/2f);
        for(int index = EXIT; index <= NEWGAME; ++index) {
            font.setColor(Color.BLACK);
            font.draw(spriteBatch, this.options[index], this.x[index] + 2, this.y[index] + 2);
            font.setColor(Color.WHITE);
            font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            if (this.isTouched[index]) {
                font.setColor(Color.RED);
                font.draw(spriteBatch, this.options[index], this.x[index], this.y[index]);
            }
        }
        spriteBatch.end();
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
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
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
            case NEWGAME: {
                Sounds.PAUSE_SONG.stop();
                Sounds.LEVEL1.stop();
                Sounds.LEVEL1.play();
                Sounds.LEVEL1.setLooping(true);

                StateManager.oldState = StateManager.States.MAIN_MENU.name();

                level_manager = new Level_Manager();

                StateManager.setStates(StateManager.States.LEVEL);

                dispose();
                break;
            }
            case LOADGAME:{
                StateManager.setStates(StateManager.States.LOAD);
                break;
            }
            case EXIT: {
                System.exit(0);
                break;
            }
        }
//        shot.play();
        LASER_HEADSET.play();
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
