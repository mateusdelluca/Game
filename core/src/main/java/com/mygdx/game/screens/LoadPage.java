package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.images.Images;
import com.mygdx.game.Application;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.system.Load;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.manager.StateManager.currentState;
import static com.mygdx.game.manager.StateManager.oldState;
//import static com.mygdx.game.manager.StateManager.oldState;

public class LoadPage extends State {

    public static final float WIDTH = 1920, HEIGHT = 1080;
    public static final float WIDTH2 = 1920, HEIGHT2 = 5690;

    SpriteBatch spriteBatch = new SpriteBatch(), spriteBatch2 = new SpriteBatch();
    private OrthographicCamera camera = new OrthographicCamera(WIDTH, HEIGHT);
    private OrthographicCamera camera2 = new OrthographicCamera(WIDTH, HEIGHT);
    private ScreenViewport viewport = new ScreenViewport(camera);

    private Rectangle mouseRectangle = new Rectangle(0, 0, 3, 9);

    private Rectangle[] rects = new Rectangle[6];

    private Sprite[] sprites = new Sprite[7];

    public LoadPage(){
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        for (int k = 0; k < 6; k++) {
            sprites[k] = new Sprite(Images.saves[k]);
        }
        sprites[6] = new Sprite(Images.box);
        boxes();

//        rects[0].x = -615;
//        y[0] = ;


    }

    public void update(){
        camera.update();
        if (camera.position.y < (5690 - 1080))
            camera.translate(0,0.2f);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

        update();

        spriteBatch.setProjectionMatrix(camera.combined);

        Sprite s = new Sprite(Images.load_page);
        spriteBatch.begin();
        s.draw(spriteBatch);
        s.setSize(WIDTH2, HEIGHT2);
        spriteBatch.end();


        spriteBatch2.setProjectionMatrix(camera2.combined);
        spriteBatch2.begin();


        for (int k = 0; k < rects.length; k++) {
            sprites[k].draw(spriteBatch2);
            sprites[k].setPosition(rects[k].x, rects[k].y);
        }

        for (Rectangle rectangle : rects) {
            if (rectangle.contains(mouseRectangle)) {
                sprites[6].setPosition(rectangle.x, rectangle.y);
                sprites[6].draw(spriteBatch2);
                break;
            }
        }

        spriteBatch2.end();
    }

    public void boxes() {
        float width = Images.saves[0].getWidth();
        float height = Images.saves[0].getHeight();

        for (int i = 0, j = 0, k = 0; k < 6; i++, k++) {

            if (i > 2) {
                i = 0;
                j++;
            }

            Rectangle r = new Rectangle((400 * i) - WIDTH/4f, (j * 300) - HEIGHT/4f, width, height);
            rects[k] = r;
        }
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

    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE){
            if (oldState.equals("MAIN_MENU")) {
                StateManager.setState(StateManager.States.MAIN_MENU);
            } else{
                if (oldState.equals("PAUSE")) {
                    StateManager.setState(StateManager.States.PAUSE);
                }
            }
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
        for (int i = 0; i < rects.length; i++) {
            if (rects[i].contains(mouseRectangle)) {
                new Load(i);
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

        Vector3 worldVect3 = new Vector3(screenX, screenY, 0f);
        camera2.unproject(worldVect3);
//        System.out.println(worldVect3.x + " " + worldVect3.y);
        this.mouseRectangle.setPosition(worldVect3.x, worldVect3.y);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
