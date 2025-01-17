package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.*;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.fans.Fan;
import com.mygdx.game.fans.Fan2;
import com.mygdx.game.fans.Fans;
import com.mygdx.game.items.Crystal;
import com.mygdx.game.items.Item;
import com.mygdx.game.items.JetPack;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.Tile;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Level extends State implements ContactListener{

    public static final int WIDTH = 1920, HEIGHT = 1080;

    protected SpriteBatch spriteBatch = new SpriteBatch();
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected World world;
    protected Background background;
    protected Box2DDebugRenderer box2DDebugRenderer;

    private HashMap<String, Item> items = new HashMap<>();
    private BitmapFont font;
    protected Boy boy;

    protected MapObjects thorns, staticObjects;

    @Getter
    @Setter
    protected Tile tile;

    protected PowerBar powerBar;

    protected ArrayList<Fans> fans = new ArrayList<>();

    protected HashMap<String, Monster1> monsters1 = new HashMap<>();
    protected Jack jack;
    protected Girl girl;

    protected ArrayList<Body> bodiesToDestroy = new ArrayList<>();

    public Level() {
        world = new World(new Vector2(0,-10f), true);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new ScreenViewport(camera);
        camera.update();

        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.2f);

        tile = new Tile("Level3/Level3.tmx");
        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false, "Rects");

        thorns = tile.loadMapObjects("Thorns_Rects");
        tile.createBodies(thorns, world, false, "Thorns_Rects");

        MapObjects thorns_colliders = tile.loadMapObjects("Thorns_Colliders");
        tile.createBodies(thorns_colliders, world, false, "Thorns_Colliders");

        background = new Background();

        powerBar = new PowerBar();

        boy = new Boy(world, new Vector2(10, 5700), viewport);

        fans.clear();

        fans.add(new Fan(world, new Vector2(1200 + 76, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200 + 2*76, 6000 - 2400)));
        fans.add(new Fan(world, new Vector2(1200, 6000 - 1640)));
        fans.add(new Fan(world, new Vector2(1400, 6000 - 1120)));
        fans.add(new Fan(world, new Vector2(1280, 6000 - 640)));

        fans.add(new Fan2(world, new Vector2(350, 6000 - 2100)));

        monsters1.clear();

        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(640, 6000 - 320), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(480, 6000 - 2000), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(2080, 6000 - 360), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(3200, 6000 - 1080), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(4800, 6000 - 2720), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(2680, 6000 - 2720), Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(world, new Vector2(240, 6000 - 5880), Monster1.class.getSimpleName() + monsters1.size()));



        items.put(Rifle.class.getSimpleName(), new Rifle(world, new Vector2(440, 6000 - 350)));

        for (int index = 1, posX = 320, posY = (6000 - 240); index < 16; index++) {
            if (index < 5) {
                posX = 320 + (100 * index);
            }
            if (index >= 5) {
                posY = 6000 - 240 - (150 * index);
                posX = 320 + 3 * 200;
            }
            if ((index >= 8 && index <= 11)) {
                posX = 320 + (100 * (index - 5));
                posY = 6000 - 2000;
            }
            if ((index >= 12 && index <= 15)) {
                posX = 520 + (100 * (index - 10));
                posY = 6000 - 2300;
            }
            items.put(Crystal.class.getSimpleName() + items.size(), new Crystal(world, new Vector2(posX, posY)));
        }

        items.put(JetPack.class.getSimpleName(), new JetPack(world, new Vector2(400, 6000 - 2400)));
        items.put(Portal.class.getSimpleName(), new Portal(world, new Vector2(2450,6000 - 5600)));
        items.get("Portal").updateItem();

        world.setContactListener(this);

        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, false, false, true);
    }

    @Override
    public void render(){
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
//        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);




        spriteBatch.setProjectionMatrix(camera.combined);

//        if (StateManager.oldState != StateManager.States.LEVEL)
            update();


        camera.position.set(boy.getBody().getPosition().x, boy.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        camera.update();
        renderObjects();
        box2DDebugRenderer.render(world, camera.combined);
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
        world.dispose();
//        background.dispose();
        powerBar.dispose();
    }


    public void renderObjects(){
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.setAutoShapeType(true);
//        shapeRenderer.begin();
//        boy.renderShape(shapeRenderer);
//        shapeRenderer.end();

        spriteBatch.begin();
        background.render(getClass().getSimpleName());
        tile.render(camera);
//        for (Bullet bullet : bullets)
//            bullet.render(spriteBatch);
        for (Item item : items.values())
            item.render(spriteBatch);
        for (Fans fan : fans) {
            fan.render(spriteBatch);
        }
        for (Monster1 monster1 : monsters1.values()){
            monster1.render(spriteBatch);
        }
        boy.render(spriteBatch);
        powerBar.render(spriteBatch, camera);
        spriteBatch.end();
    }

    public void update(){
        for (int i = 0; i < 5; i++) {
            world.step(6, 7, 7);
            camera.update();
//            world.step(60, 7, 7);
//            camera.update();
        }

        collisions();

        for (Fans fan : fans)
            fan.bodyCloseToFan2(boy.getBody(), Boy.BOX_WIDTH);
        updateObjects();

    }

    public void updateObjects(){
//        for (Bullet bullet : bullets)
//            bullet.update();
        for (Item item : items.values())
            item.update();
        boy.update();
        for (Fans fan : fans) {
            fan.update();
        }
        for (Monster1 monster1 : monsters1.values()){
            monster1.update(boy);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
            return;
        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
            return;
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
            return;

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        for (Item item : items.values()) {
            if ((body1.getUserData().toString().equals(item.toString()) && body2.getUserData().toString().equals("Boy")) ||
                (body2.getUserData().toString().equals(item.toString()) && body1.getUserData().toString().equals("Boy"))) {
                boy.equip_Item(item);

                if (!item.toString().equals("Portal") && body1.getUserData().equals(item.toString()))
                    body1.setUserData("null");
                else if (!item.toString().equals("Portal") && body2.getUserData().equals(item.toString()))
                    body2.setUserData("null");
            }
        }

        if ((body1.getUserData().toString().equals("Bullet") &&
            body2.getUserData().toString().equals("Boy"))
            || (body2.getUserData().toString().equals("Bullet") &&
            body1.getUserData().toString().equals("Boy"))) {
            if (body1.getFixtureList().get(0).isSensor() ||
                body2.getFixtureList().get(0).isSensor())
                return;
            if (body1.getUserData().toString().equals("Bullet")) {
                body1.setUserData("null");
            } else {
                if (body2.getUserData().toString().equals("Bullet")) {
                    body2.setUserData("null");
                }
            }
//            boyBeenHit();
        }

        if (body1.getUserData().equals("Thorns_Colliders") && body2.getUserData().toString().equals("Boy")) {
//            boyBeenHit();
        } else {
            if (body2.getUserData().equals("Thorns_Colliders") && body1.getUserData().toString().equals("Boy")) {
//                boyBeenHit();
            }
        }

        for (Monster1 m1 : monsters1.values()) {
            if (body1.getUserData().toString().equals(m1.toString()) && body2.getUserData().toString().equals("Boy")
                || body2.getUserData().toString().equals(m1.toString()) && body1.getUserData().toString().equals("Boy")) {
//                boyBeenHit();
            }
            if (body1.getUserData().equals("Thorns_Colliders") && body2.getUserData().toString().equals(m1.getBody().getUserData())) {
//                monster1BeenHit(m1, body1);
            } else {
                if (body2.getUserData().equals("Thorns_Colliders") && body1.getUserData().toString().equals(m1.getBody().getUserData())) {
//                    monster1BeenHit(m1, body2);
                }
            }
            if ((body1.getUserData().toString().equals("Bullet") &&
                body2.getUserData().toString().equals(m1.toString())
            ) || body2.getUserData().toString().equals("Bullet") &&
                body1.getUserData().toString().equals(m1.toString())) {
                if (body1.getUserData().toString().equals("Bullet")) {
//                    monster1BeenHit(m1, body1);
                    body1.setUserData("null");
                    body1.setGravityScale(0.1f);
                } else {
                    if (body2.getUserData().toString().equals("Bullet")) {
//                        monster1BeenHit(m1, body2);
                        body2.setUserData("null");
                        body2.setGravityScale(0.1f);
                    }
                }
            }
        }
        if (body1.getUserData().toString().equals("null")) {
            bodiesToDestroy.add(body1);
        }
        if (body2.getUserData().toString().equals("null")) {
            bodiesToDestroy.add(body2);
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    protected void collisions() {
        for (Monster1 monster1 : monsters1.values()) {
            if (boy.actionRect().overlaps(monster1.getBodyBounds())) {
                monster1.getBody().setLinearVelocity(0, 0);
                if (boy.animations.name().equals("BOY_SABER")) {
                    monster1.animations = Animations.MONSTER1_SPLIT;
                    monster1.setSplit(true);
                    for (Fixture f : monster1.getBody().getFixtureList()) {
                        f.setSensor(true);
                    }
                    monster1.getBody().setGravityScale(0f);
                    monster1.getBody().setLinearVelocity(0f, 0f);
                } else {
                    if (!monster1.isSplit())
                        monster1.animations = Animations.MONSTER1_FLICKERING;
                }
                monster1.getBody().setFixedRotation(true);
            } else
//            if (monster1.getBodyBounds().overlaps(boy.getBodyBounds()) && !boy.actionRect().overlaps(monster1.getBodyBounds()) && !boy.animations.name().equals("BOY_SABER")) {
////                boyBeenHit(monster1);
//            }
                if (boy.actionRect().overlaps(monster1.getBodyBounds()) && boy.animations.name().equals("BOY_PUNCHING")) {
                    monster1.getBody().setLinearVelocity(monster1.getBody().getLinearVelocity().x + monster1.getBody().getPosition().x > boy.getBody().getPosition().x ? 15 : -15, monster1.getBody().getLinearVelocity().y + 20f);
                    monster1.animations = Animations.MONSTER1_FLICKERING;
                }

//            if (boy.getBodyBounds().overlaps(monster1.getBodyBounds()) && !monster1.isSplit()) {
//                boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x, boy.getBody().getLinearVelocity().y + 20f);
//                monster1.animations = Animations.MONSTER1_FLICKERING;
//                boyBeenHit();
//            }
//            for (Rectangle rect : verticalRectsThorns) {
//                if (boy.getBodyBounds().overlaps(rect)) {
//                    boyBeenHit();
//                }
//            }
        }
//        if (boy.actionRect().overlaps(jack.getBodyBounds()) && boy.animations.name().equals("BOY_PUNCHING")) {
//            jack.setBeenHit(true);
//        }
            for (Body body : bodiesToDestroy) {
                if (body.getTransform().getPosition().x != 0) {
                    body.setTransform(new Random().nextFloat(1000), new Random().nextFloat(3000), 0);
                }
         }
            bodiesToDestroy.clear();
        }

    protected void boyBeenHit(){
        if (!boy.isBeenHit()) {
            boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x, boy.getBody().getLinearVelocity().y + 40f);
            boy.animations = Animations.BOY_STRICKEN;
            PowerBar.hp -= 10;
            boy.setBeenHit(true);
            Sounds.HURT.play();
        }
    }

    protected void monster1BeenHit(Monster1 m1, Body body) {
        String name = "MONSTER1_FLICKERING";
        m1.getBody().setLinearVelocity(m1.getBody().getWorldCenter().x > body.getWorldCenter().x ? m1.getBody().getLinearVelocity().x + 10f : m1.getBody().getLinearVelocity().x - 10f, m1.getBody().getLinearVelocity().y + 20);
        m1.animations = Animations.valueOf(name);
        Sounds.MONSTER_HURT.play();
        m1.setHP(m1.getHP() - 1);
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        boy.resize(spriteBatch, width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        boy.keyDown(keycode);
        if (keycode == Input.Keys.ESCAPE){
            StateManager.setState(StateManager.States.LOAD);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        boy.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boy.touchDown(screenX, screenY, pointer, button);
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
        boy.mouseMoved(screenX, screenY);
        System.out.println(screenX);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
