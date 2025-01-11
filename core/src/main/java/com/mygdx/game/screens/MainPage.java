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
import com.mygdx.game.sfx.Sounds;

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

        if (!Sounds.PAUSE_SCREEN.isPlaying()) {
            Sounds.PAUSE_SCREEN.play();
            Sounds.PAUSE_SCREEN.setLooping(true);
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
        if (!Sounds.PAUSE_SCREEN.isPlaying())
            Sounds.PAUSE_SCREEN.play();
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
                Sounds.PAUSE_SCREEN.stop();
                if (!Sounds.LEVEL1.isPlaying())
                    Sounds.LEVEL1.play();
                Sounds.LEVEL1.setLooping(true);
                StateManager.currentState = States.
                Gdx.input.setInputProcessor(app.jogo.levelManager);
                app.jogo.levelManager.changeLevel("Level3", app);
                break;
            }
            case LOADGAME:{
//                app.setScreen(app.jogo.loadScreen);
//                Gdx.input.setInputProcessor(app.jogo.loadScreen);
                break;
            }
            case EXIT: {
                System.exit(0);
                break;
            }
        }
        shot.play();
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
