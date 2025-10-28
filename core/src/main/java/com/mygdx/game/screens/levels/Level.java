package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.entities.*;
import com.mygdx.game.images.Background;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.items.*;
import com.mygdx.game.items.fans.Fan;
import com.mygdx.game.items.fans.Fan2;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.Tile;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.screens.levels.Level_Manager.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;

public abstract class Level extends State implements ContactListener, Serializable {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    protected transient Background background;
    protected transient Box2DDebugRenderer box2DDebugRenderer;

    public static HashMap<String, Objeto> items2 = new HashMap<>();

    public static HashMap<String, Item> items = new HashMap<>();
    public static Player player;
    @Getter @Setter
    protected Jack jack;
    @Setter @Getter
    protected Girl girl;

    protected Mouse mouse;
    protected PowerBar powerBar;

    protected ArrayList<Objeto> fans = new ArrayList<>();

    public HashMap<String, Monster1> monsters1 = new HashMap<>();

    protected ArrayList<Block> blocks = new ArrayList<>();

    protected NinjaRope ninjaRope;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    protected ArrayList<Stand> stands = new ArrayList<>();
    protected NinjaStar ninjaStar;

    protected Array<Rope> ropes = new Array();
    public Rope rope;
    protected boolean beenHit;

    public ArrayList<Objeto> objetos = new ArrayList<>();

    public static Rifle rifle;

//    public String polygons_String = "";

    public Level() throws Exception {
        // Constructs a new OrthographicCamera, using the given viewport width and height
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        viewport = new ScreenViewport(camera);
        viewport.update(Level.WIDTH, Level.HEIGHT);
        rifle = new Rifle(new Vector2(500, 400));
        init();

        powerBar = new PowerBar();




//      items2.put(NinjaRope.class.getSimpleName(),new NinjaRope(new Vector2(450, 6000 - 450)));

    }

    public void init() {
        Box2D.init();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        background = new Background();

        tile = new Tile(getClass().getSimpleName() + "/" + getClass().getSimpleName() + ".tmx");
//        if (currentLevelName.equals(oldLevel))

        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false, "Rects");

        thorns = tile.loadMapObjects("Thorns_Rects");
        tile.createBodies(thorns, world, false, "Thorns_Rects");

        MapObjects thorns_colliders = tile.loadMapObjects("Thorns_Colliders");
        tile.createBodies(thorns_colliders, world, false, "Thorns_Colliders");

//        polygons_bodies = tile.loadMapObjects("Polygons");
//        tile.createBodies(polygons_bodies, world, false, "Polygons");

        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        BitmapFont font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.2f);

        fans = new ArrayList<>();

        fans.add(new Fan(new Vector2(1200 + 76, 6000 - 2400)));
        fans.add(new Fan(new Vector2(1200 + 2*76, 6000 - 2400)));
        fans.add(new Fan(new Vector2(1200, 6000 - 1640)));
        fans.add(new Fan(new Vector2(1400, 6000 - 1120)));
        fans.add(new Fan(new Vector2(1280, 6000 - 640)));

        fans.add(new Fan2(new Vector2(350, 6000 - 2100)));

        box2DDebugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);
        player = new Player(new Vector2(50, 1700), viewport);

    }

    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);




        spriteBatch.setProjectionMatrix(camera.combined);
//        if (!StateManager.oldState.equals(StateManager.States.PAUSE.name()))
//        update();
        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        camera.update();
        viewport.update(Level.WIDTH, Level.HEIGHT);
//        try {
//            renderObjects();
//        } catch (NullPointerException e) {
//            System.out.println(e.getMessage());
//        }
        shapeRenderer.setProjectionMatrix(camera.combined);
//        camera.update();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
//        boy.renderShape(shapeRenderer);
        ninjaRope.render(shapeRenderer, player.getBodyBounds());
        shapeRenderer.end();

        box2DDebugRenderer.render(world, camera.combined);

    }

    @Override
    public void pause() {
        renderObjects();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
    }


    public void renderObjects(){
        spriteBatch.begin();
        background.render();
        tile.render(camera);

//        for (Bullet bullet : bullets)
//            bullet.render(spriteBatch);
//        for (Objeto objeto : objetos) {
//            if (!(objeto instanceof Boy) && !(objeto instanceof Monster1))
//                objeto.render(spriteBatch);
//        }
//        for (Monster1 monster1 : monsters1.values()){
//            monster1.render(spriteBatch);
//        }
//        jack.render(spriteBatch);
//        girl.render(spriteBatch);
////        Rifle rifle = (Rifle) items.get("Rifle");
////        rifle.getLeftSideBullets().render(spriteBatch);
//        for (Item item : items.values())
//            item.render(spriteBatch);
//        for (Objeto fan : fans)
//            fan.render(spriteBatch);
//        boy.render(spriteBatch);
//        for (Block block : blocks)
//            block.render(spriteBatch);
//        powerBar.render(spriteBatch, camera,boy);
//        ninjaRope.render(spriteBatch);
//        for (Stand stand : stands)
//            stand.render(spriteBatch);
//        ninjaStar.render(spriteBatch);
//        rope.render(spriteBatch);
//        for (Rope rope : ropes)
//            rope.render(spriteBatch);
//TODO: corrigir o problema de vários objetos sererm desenhados separadamente de objetos o array
        spriteBatch.end();
    }

    public void update(){
        for (int i = 0; i < 10; i++) {
            world.step(1/60f, 7, 7);
            camera.update();
        }
        for (Objeto objeto : objetos){
            if (objeto != null)
                objeto.update();
        }
//        collisions();
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

        if (body1 == null || body2 == null)
            return;

//        for (Rope rope : ropes){
        if (rope != null)
            rope.beginContact(body1, body2);
//        }
        for (Objeto objeto : objetos) {
            if (objeto != null) {
                objeto.beenHit(body1, body2);
                objeto.beginContact(body1, body2);
            }
        }


        for (Item item : items.values()) {
//            boolean notCrystalOrPortal = !item.toString().contains("Crystal") && !item.toString().contains("Portal");
            if ((body1.getUserData().toString().contains(item.toString()) && body2.getUserData().toString().equals("Boy"))
            || body2.getUserData().toString().contains(item.toString()) && body1.getUserData().toString().equals("Boy")
            ){
                player.takeItem(item);
//                item.setVisible(false);
//                if (!item.toString().equals("Portal") && body1.getUserData().equals(item.toString())) {
//                    body1.setUserData("null");
//                    item.setUserData(body1);
//                }
//                else if (!item.toString().equals("Portal") && body2.getUserData().equals(item.toString())) {
//                    body2.setUserData("null");
//                    item.setUserData(body2);
//                }
            }
        }

//        for (Objeto o : objetos) {
//            if (body1.getUserData().toString().equals("Boy") || body2.getUserData().toString().equals("Boy"))
//                if (!body1.getUserData().toString().equals("Rifle") && !body2.getUserData().toString().equals("Rifle") &&
//                    !body1.getUserData().toString().contains("Crystal") && !body2.getUserData().toString().contains("Crystal") &&
//                    !body1.getUserData().toString().contains("BulletBoy") && !body2.getUserData().toString().contains("BulletBoy") &&
//                    !body1.getUserData().toString().contains("NinjaStar") && !body2.getUserData().toString().contains("NinjaStar") &&
//                    !body1.getUserData().toString().contains("NinjaRope") && !body2.getUserData().toString().contains("NinjaRope") &&
//                    !body1.getUserData().toString().contains("Rope") && !body2.getUserData().toString().contains("Rope") &&
//                    !body1.getUserData().toString().equals("Rects") && !body2.getUserData().toString().equals("Rects") &&
//                    !body1.getUserData().toString().contains("joint") && !body2.getUserData().toString().contains("joint") &&
//                    !body1.getUserData().toString().equals("Thorns_Rects") && !body2.getUserData().toString().equals("Thorns_Rects") &&
//                    !body1.getUserData().toString().equals("JetPack") && !body2.getUserData().toString().equals("JetPack") &&
//                    !body1.getUserData().toString().equals("Fan") && !body2.getUserData().toString().equals("Fan") &&
//                    !body1.getUserData().toString().equals("Fan2") && !body2.getUserData().toString().equals("Fan2") &&
//                    !body1.getUserData().toString().equals("Saber") && !body2.getUserData().toString().equals("Saber") &&
//                    !body1.getUserData().toString().contains("Block") && !body2.getUserData().toString().contains("Block") &&
//                    !body1.getUserData().toString().equals("null") && !body2.getUserData().toString().equals("null"))
//                    boy.beenHit();


//                if ((((
//
//                    body1.getUserData().equals("Thorns_Colliders") ||
//                        body1.getUserData().toString().equals("Boy"))
//
//                    &&
//
//                    body2.getUserData().toString().equals(o.getBodyData().userData))
//
//                    ||
//
//                    (((
//
//                        body2.getUserData().equals("Thorns_Colliders")) ||
//                        body2.getUserData().toString().equals("Boy"))
//
//                        &&
//
//                        body1.getUserData().toString().equals(o.getBodyData().userData)))) {
//                if (!(o instanceof Boy) && !(o instanceof Jack) && !(o instanceof Girl))

//        }
//        for (Objeto o : objetos) {
//            if (o instanceof NinjaRope)
//                continue;
//            if (((body1.getUserData().toString().equals("Bullet") || body1.getUserData().equals("Thorns_Colliders")) &&
//                body2.getUserData().toString().equals(o.getBodyData().userData))
//                || ((body2.getUserData().toString().equals("Bullet") || body2.getUserData().equals("Thorns_Colliders")) &&
//                body1.getUserData().toString().equals(o.getBodyData().userData))
//            || (body1.getUserData().toString().contains("NinjaStar") && body2.getUserData().toString().equals(o.getBodyData().userData))
//                || (body2.getUserData().toString().contains("NinjaStar") &&
//                body1.getUserData().toString().equals(o.getBodyData().userData))) {
//                if (!body1.getFixtureList().get(0).isSensor() &&
//                    !body2.getFixtureList().get(0).isSensor())
//                    if (!(o.getBody().getUserData().equals("BulletBoy")))
//                        o.beenHit(body1, body2);
//                if (body1.getUserData().toString().equals("Bullet")) {
//                    body1.setUserData("null");
//                } else {
//                    if (body2.getUserData().toString().equals("Bullet")) {
//                        body2.setUserData("null");
//                    }
//                }
//                if ((o instanceof Boy) || (o instanceof Jack) || (o instanceof Girl) )
//                    o.beenHit();

//            }

/*        }   TODO: blocos para todos os levels
       for (int i = 0; i < blocks.size(); i++) {
            if (((body1.getUserData().toString().equals("Bullet") &&
                body2.getUserData().toString().equals("Block" + i))
                || (body2.getUserData().toString().equals("Bullet") &&
                body1.getUserData().toString().equals("Block" + i)))
                || ((body1.getUserData().toString().equals("Block" + i)) && body2.getUserData().equals("Boy"))
            || (body2.getUserData().toString().equals("Block" + i)) && body1.getUserData().equals("Boy")){
                if (!body1.getFixtureList().get(0).isSensor() &&
                    !body2.getFixtureList().get(0).isSensor()) {
                    blocks.get(i).beenHit();
                    if (body1.getUserData().equals("Bullet")) {
                        body1.setUserData("null");
                    }
                    else {
                        if (body2.getUserData().equals("Bullet")) {
                            body2.setUserData("null");
                        }
                    }
                }
            }
        }
*/



//        Body bullet1 = contact.getFixtureA().getBody().getUserData().toString().equals("Bullet") ? contact.getFixtureA().getBody() : null;
//        Body bullet2 = contact.getFixtureB().getBody().getUserData().toString().equals("Bullet") ? contact.getFixtureA().getBody() : null;
//
//
//        for (Objeto objeto : objetos) {
//            if (bullet1 != null) {
//                if (objeto.getBodyData().equals(body2.getUserData())) {
//                    objeto.beenHit();
//                }
//            }
//            if (bullet2 != null) {
//                if (objeto.getBodyData().equals(body1.getUserData())) {
//                    objeto.beenHit();
//                }
//            }
//        }

//        if (body1.getUserData().toString().equals("null")) {
//            bodiesToDestroy.add(body1);
//        }
//        if (body2.getUserData().toString().equals("null")) {
//            bodiesToDestroy.add(body2);
//        }
    }

    @Override
    public void endContact(Contact contact) {
        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
            return;
        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
            return;
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
            return;

        Body body1 = contact.getFixtureA().getBody();
        Body body2 = contact.getFixtureB().getBody();

        ninjaRope.beginContact(contact);



//        for (Objeto o : objetos) {
//            if (o instanceof NinjaRope)
//                continue;
//            if (((body1.getUserData().toString().equals("Bullet") || body1.getUserData().equals("Thorns_Colliders")) &&
//                body2.getUserData().toString().equals(o.getBodyData().userData))
//                || ((body2.getUserData().toString().equals("Bullet") || body2.getUserData().equals("Thorns_Colliders")) &&
//                body1.getUserData().toString().equals(o.getBodyData().userData))) {
//                if (!body1.getFixtureList().get(0).isSensor() &&
//                    !body2.getFixtureList().get(0).isSensor())
//                    if (!(o.getBody().getUserData().equals("BulletBoy")))
//                        o.beenHit();
//            }
//        }

        for (Stand stand : stands) {
            if (body1.getUserData().toString().equals(stand.getBody().getUserData()) && body2.getUserData().toString().equals("Boy")
                || (body2.getUserData().toString().equals(stand.getBody().getUserData()) && body1.getUserData().toString().equals("Boy"))) {
                stand.beenHit();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
//        if (contact.getFixtureA() == null || contact.getFixtureB() == null)
//            return;
//        if (contact.getFixtureA().getBody() == null || contact.getFixtureB().getBody() == null)
//            return;
//        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null)
//            return;
//
//        Body body1 = contact.getFixtureA().getBody();
//        Body body2 = contact.getFixtureB().getBody();
//
//        if (body1 == null || body2 == null)
//            return;
//
//    }

/*    protected void collisions() {
//        for (Monster1 monster1 : monsters1.values()) {
//            if (player.attackUsingBodies().overlaps(monster1.getBodyBounds())) {
//                monster1.getBody().setLinearVelocity(0, 0);
//                if (player.animation().name().equals("SABER") && player.frameCounter() > 0f) {
//                    for (Fixture f : monster1.getBody().getFixtureList()) {
//                        f.setSensor(true);
//                    }
//                    monster1.animations.changeAnimation("MONSTER1_SPLIT");
//                    monster1.setSplit(true);
//                    monster1.getBody().setGravityScale(0f);
//                    monster1.getBody().setLinearVelocity(0f, 0f);
//                }
    TODO: VERIFICAR O RECTANGLE DE ACTION RECT DE BOY QUE FOI MUDADO PARA BODIES EM PLAYER
    */
//                else {
//                    if (!monster1.isSplit())
//                        monster1.animations.changeAnimation("MONSTER1_FLICKERING");
//                }
//                monster1.getBody().setFixedRotation(true);
//            }
//            else{
//                 if (monster1.getBodyBounds().overlaps(boy.getBodyBounds()) &&
//                     !boy.animations.name().equals("BOY_PUNCHING") &&
//                     !boy.actionRect().overlaps(monster1.getBodyBounds()) &&
//                     !boy.animations.name().equals("BOY_SABER")) {
//                    boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x +
//                        (monster1.getBody().getPosition().x > boy.getBody().getPosition().x ? -15 : 15),
//                        boy.getBody().getLinearVelocity().y + 20f);
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                beenHit = false;
//                            }
//                        }, 5000);
//            }
//                if (boy.actionRect().overlaps(monster1.getBodyBounds()) && boy.animations.name().equals("BOY_PUNCHING")) {
////                    monster1.getBody().setLinearVelocity(monster1.getBody().getLinearVelocity().x + monster1.getBody().getPosition().x > boy.getBody().getPosition().x ? 15 : -15, monster1.getBody().getLinearVelocity().y + 20f);
////                    monster1.animations = Animations.MONSTER1_FLICKERING;
//                    monster1.beenHit();
//                }

//        }
//        for (Block block : blocks)
//            if (boy.getBodyBounds().overlaps(block.getRectangle()))
//                block.beenHit();
//        if (boy.actionRect().overlaps(jack.getBodyBounds()) && boy.animations.name().equals("BOY_PUNCHING")) {
//            jack.beenHit();
//        }
//        for (Objeto o : objetos) {
//            for (Body body : bodiesToDestroy) {
//                body.setTransform(-10_500, -15_320, 0);
//            }
//            bodiesToDestroy.clear();
//        }
    }

    public static boolean contains(MapObjects mapObjects, Rectangle rectangle){
        for (MapObject mapObject : mapObjects){
            if ((((RectangleMapObject) mapObject).getRectangle().contains(rectangle))){
               return true;
            }
        } return false;
    }

    public static boolean contains(MapObjects mapObjects, Vector2 point){
        for (MapObject mapObject : mapObjects){
            if ((((RectangleMapObject) mapObject).getRectangle().contains(point))){
                return true;
            }
        } return false;
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        player.resize(spriteBatch, width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        player.keyDown(keycode);
        player.keyDown(keycode);
        if (keycode == Input.Keys.ESCAPE){
            takeScreenshot();
            StateManager.setStates(StateManager.States.PAUSE);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.touchDown(screenX, screenY, pointer, button);
        ninjaRope.justTouched(button);
        mouse.touchDown(screenX, screenY, button);
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

        player.mouseMoved(screenX, screenY);
        ninjaRope.mouseMoved(screenX, screenY);
//        mouse.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
