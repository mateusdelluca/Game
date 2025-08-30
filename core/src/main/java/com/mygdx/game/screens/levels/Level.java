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
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.Background;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.items.*;
import com.mygdx.game.items.fans.Fan;
import com.mygdx.game.items.fans.Fan2;
import com.mygdx.game.items.fans.Fans;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.Tile;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.items.Rope.NUM_ROPES;
import static com.mygdx.game.screens.levels.Level_Manager.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import static com.mygdx.game.images.Images.*;
import static com.mygdx.game.screens.levels.Level_Manager.bodiesToDestroy;
import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;

public class Level extends State implements ContactListener, Serializable {

    public static final int WIDTH = 1920, HEIGHT = 1080;

    protected transient Background background;
    protected transient Box2DDebugRenderer box2DDebugRenderer;

    public static HashMap<String, Objeto> items2 = new HashMap<>();

    public static HashMap<String, Item> items = new HashMap<>();
    public Boy boy;
    @Getter @Setter
    private Jack jack;
    @Setter @Getter
    private Girl girl;

    private Mouse mouse;
    protected PowerBar powerBar;

    protected ArrayList<Fans> fans = new ArrayList<>();

    public HashMap<String, Monster1> monsters1 = new HashMap<>();

    private final ArrayList<Block> blocks = new ArrayList<>();

    private NinjaRope ninjaRope;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private ArrayList<Stand> stands = new ArrayList<>();
    private NinjaStar ninjaStar;

    private Array<Rope> ropes = new Array();
    Rope rope;
    private boolean beenHit;


    public void init(){
        Box2D.init();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        background = new Background();

        tile = new Tile("Level3/Level3.tmx");
        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false, "Rects");

        thorns = tile.loadMapObjects("Thorns_Rects");
        tile.createBodies(thorns, world, false, "Thorns_Rects");

        MapObjects thorns_colliders = tile.loadMapObjects("Thorns_Colliders");
        tile.createBodies(thorns_colliders, world, false, "Thorns_Colliders");

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

    }

    public Level() {
        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        viewport = new ScreenViewport(camera);
        viewport.update(Level.WIDTH, Level.HEIGHT);

        init();

        powerBar = new PowerBar();

        boy = new Boy(new Vector2(10, 5700), viewport);

        ninjaRope = new NinjaRope(boy.getBody());

        jack = new Jack(new Vector2(300, 6000 - 380f));

        girl = new Girl(new Vector2(4000, 6000 - 700f));

        monsters1.clear();

        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(640, 6000 - 2000),     Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(480, 6000 - 2000),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2080, 6000 - 360),    Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(3200, 6000 - 1080),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(4800, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(2680, 6000 - 2720),   Monster1.class.getSimpleName() + monsters1.size()));
        monsters1.put(Monster1.class.getSimpleName() + monsters1.size(), new Monster1(new Vector2(240, 6000 - 5880),    Monster1.class.getSimpleName() + monsters1.size()));

        items.put(Rifle.class.getSimpleName(), new Rifle(new Vector2(850, 6000 - 450)));


        items2 = new HashMap<>();
        items2.put(Rifle.class.getSimpleName(),(Objeto) items.get(Rifle.class.getSimpleName()));
        items2.put(Rifle.class.getSimpleName(), jack.getRifle());
        items2.put(Rifle.class.getSimpleName(), girl.getRifle());

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
            items.put(Crystal.class.getSimpleName() + items.size(), new Crystal(new Vector2(posX, posY), items.size()));
            items2.put(Crystal.class.getSimpleName() + items.size(), new Crystal(new Vector2(posX, posY)));
        }

        items.put(JetPack.class.getSimpleName(), new JetPack(new Vector2(400, 6000 - 2400)));
        items.put(Saber.class.getSimpleName(), new Saber(new Vector2(500, 6000 - 2400)));
        items.put(Portal.class.getSimpleName(), new Portal(new Vector2(2450,6000 - 5600)));
        items.put(NinjaRope.class.getSimpleName(), new NinjaRope(new Vector2(450, 6000 - 400)));
        items.get("Portal").updateItem();

        items2.put(JetPack.class.getSimpleName(), new JetPack(new Vector2(400, 6000 - 2400)));
        items2.put(Saber.class.getSimpleName(), new Saber(new Vector2(500, 6000 - 2400)));
        items2.put(Portal.class.getSimpleName(), new Portal(new Vector2(2450,6000 - 5600)));
//        items2.put(NinjaRope.class.getSimpleName(),new NinjaRope(new Vector2(450, 6000 - 450)));
        ninjaStar = new NinjaStar(new Vector2(200, 6000 - 400));
        for (int i = 0; i < 5; i++)
            blocks.add(new Block(new Vector2(850 + i * 50, 6000 - 530)));

        for (int i = 0; i < 5; i++)
            blocks.add(new Block(new Vector2(850 + i * 50, 4050)));

        for (int i = 0; i < 28; i++)
            blocks.add(new Block(new Vector2(3330 + (i * 50), 4810)));

        for (int i = 0; i < 27; i++)
            blocks.add(new Block(new Vector2(4200 + (i * 50), 4050)));


        for (int i = 1; i < 7; i++) {
            stands.add(new Stand(3000 + (200 * i), 6000 - 720));
        }

//        objetos.addAll(blocks);

        rope = new Rope(new Vector2(300, 26000 - 400 + (NUM_ROPES * Rope.HEIGHT)), true);
        for (int i = 0; i < NUM_ROPES; i++){
            ropes.add(new Rope(new Vector2(300f, 6000 - 400 + (i * Rope.HEIGHT)), false));
            if (i == 0) {
                rope.joint(ropes.get(i).getBodyA());
                continue;
            }
            ropes.get(i).joint(ropes.get(i-1).getBodyA());
        }

        mouse = new Mouse(boy.getBody().getPosition());

        objetos.addAll(items2.values());
        objetos.add(boy);
        objetos.add(jack);
        objetos.add(girl);
        objetos.addAll(stands);
        objetos.addAll(monsters1.values());

    }

    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);




        spriteBatch.setProjectionMatrix(camera.combined);
//        if (!StateManager.oldState.equals("PAUSE"))
            update();


        camera.position.set(boy.getBody().getPosition().x, boy.getBody().getPosition().y, 0);
        if (camera.position.y > 5400f)
            camera.position.y = 5400f;
        if (camera.position.x < 970f)
            camera.position.x = 970f;
        camera.update();
        viewport.update(Level.WIDTH, Level.HEIGHT);
        renderObjects();
        shapeRenderer.setProjectionMatrix(camera.combined);
//        camera.update();

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
//        boy.renderShape(shapeRenderer);
        ninjaRope.render(shapeRenderer, boy.getBodyBounds());
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
        background.render(getClass().getSimpleName());
        tile.render(camera);

//        for (Bullet bullet : bullets)
//            bullet.render(spriteBatch);
//        for (Objeto objeto : objetos) {
//            if (!(objeto instanceof Boy) && !(objeto instanceof Monster1))
//                objeto.render(spriteBatch);
//        }
        for (Monster1 monster1 : monsters1.values()){
            monster1.render(spriteBatch);
        }
        jack.render(spriteBatch);
        girl.render(spriteBatch);
//        Rifle rifle = (Rifle) items.get("Rifle");
//        rifle.getLeftSideBullets().render(spriteBatch);
        for (Item item : items.values())
            item.render(spriteBatch);
        for (Fans fan : fans)
            fan.render(spriteBatch);
        boy.render(spriteBatch);
        for (Block block : blocks)
            block.render(spriteBatch);
        powerBar.render(spriteBatch, camera);
        ninjaRope.render(spriteBatch);
        for (Stand stand : stands)
            stand.render(spriteBatch);
        ninjaStar.render(spriteBatch);
        rope.render(spriteBatch);
        for (Rope rope : ropes)
            rope.render(spriteBatch);
        spriteBatch.end();

    }

    public void update(){
        for (int i = 0; i < 5; i++) {
            world.step(1/60f, 7, 7);
            camera.update();
        }
        collisions();
        updateObjects();
    }

    public void updateObjects(){
//        for (Bullet bullet : bullets)
//            bullet.update();
        for (Objeto objeto : objetos) {
            if (objeto instanceof Monster1 || objeto instanceof Boy || objeto instanceof Bullet)
                continue;
            objeto.update();
        }
        ninjaRope.update();
//        for (Rope rope : ropes)
//            rope.update();
//        rope.update();
        items.get("Rifle").update();
//        ninjaRope.update(0f);
        for (Monster1 monster1 : monsters1.values()){
            monster1.update(boy);
        }
        for (Block block : blocks)
            block.update();
        boy.update();
        for (Fans fan : fans)
            fan.update2(boy.getBody(), Boy.BOX_WIDTH);
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


        boy.beginContact(contact);

//        for (Rope rope : ropes){
            rope.beginContact(body1, body2);
//        }

        for (Item item : items.values()) {
//            boolean notCrystalOrPortal = !item.toString().contains("Crystal") && !item.toString().contains("Portal");
            if ((body1.getUserData().toString().contains(item.toString()) && body2.getUserData().toString().equals("Boy"))
            || body2.getUserData().toString().contains(item.toString()) && body1.getUserData().toString().equals("Boy")){
                boy.takeItem(item);

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
        for (Objeto o : objetos) {
            if (o instanceof NinjaRope)
                continue;
            if (((body1.getUserData().toString().equals("Bullet") || body1.getUserData().equals("Thorns_Colliders")) &&
                body2.getUserData().toString().equals(o.getBodyData().userData))
                || ((body2.getUserData().toString().equals("Bullet") || body2.getUserData().equals("Thorns_Colliders")) &&
                body1.getUserData().toString().equals(o.getBodyData().userData))
            || (body1.getUserData().toString().contains("NinjaStar") && body2.getUserData().toString().equals(o.getBodyData().userData))
                || (body2.getUserData().toString().contains("NinjaStar") &&
                body1.getUserData().toString().equals(o.getBodyData().userData))) {
//                if (!body1.getFixtureList().get(0).isSensor() &&
//                    !body2.getFixtureList().get(0).isSensor())
//                    if (!(o.getBody().getUserData().equals("BulletBoy")))
                        o.beenHit(body1, body2);
//                if (body1.getUserData().toString().equals("Bullet")) {
//                    body1.setUserData("null");
//                } else {
//                    if (body2.getUserData().toString().equals("Bullet")) {
//                        body2.setUserData("null");
//                    }
//                }
//                if ((o instanceof Boy) || (o instanceof Jack) || (o instanceof Girl) )
//                    o.beenHit();

            }

        }
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



        for (Objeto o : objetos) {
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
        }

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


        for (Objeto o : objetos) {
//            if (body1.getUserData().toString().equals("Boy") || body2.getUserData().toString().equals("Boy"))
//                if (!body1.getUserData().toString().equals("Rifle") && !body2.getUserData().toString().equals("Rifle") &&
//                    !body1.getUserData().toString().contains("Crystal") && !body2.getUserData().toString().contains("Crystal") &&
//                    !body1.getUserData().toString().equals("Bullet") && !body2.getUserData().toString().equals("Bullet") &&
//                    !body1.getUserData().toString().equals("Rects") && !body2.getUserData().toString().equals("Rects") &&
//                    !body1.getUserData().toString().equals("Thorns_Rects") && !body2.getUserData().toString().equals("Thorns_Rects") &&
//                    !body1.getUserData().toString().equals("JetPack") && !body2.getUserData().toString().equals("JetPack"))
//                    boy.beenHit();
            if (((body1.getUserData().toString().contains("Bullet") || body1.getUserData().equals("Thorns_Colliders")) &&
                body2.getUserData().toString().equals(o.getBody().getUserData()))
                || ((body2.getUserData().toString().contains("Bullet") || body2.getUserData().equals("Thorns_Colliders")) &&
                body1.getUserData().toString().equals(o.getBody().getUserData()))
                || (body2.getUserData().toString().contains("NinjaStar") &&
                body1.getUserData().toString().equals(o.getBody().getUserData()))) {
                if (!body1.getFixtureList().get(0).isSensor() &&
                    !body2.getFixtureList().get(0).isSensor()) {

//                if (body1.getUserData().toString().equals("Bullet")) {
//                    body1.setUserData("null");
//                } else {
//                    if (body2.getUserData().toString().equals("Bullet")) {
//                        body2.setUserData("null");
//                    }
//                }
//                 if (!(o instanceof Boy))
//                    o.beenHit();
                }
            }
        }
    }

    protected void collisions() {
        for (Monster1 monster1 : monsters1.values()) {
            if (boy.actionRect().overlaps(monster1.getBodyBounds())) {
                monster1.getBody().setLinearVelocity(0, 0);
                if (boy.animations.name().equals("BOY_SABER") && boy.frameCounter() > 0f) {
                    for (Fixture f : monster1.getBody().getFixtureList()) {
                        f.setSensor(true);
                    }
                    monster1.animations = Animations.MONSTER1_SPLIT;
                    monster1.setSplit(true);
                    monster1.getBody().setGravityScale(0f);
                    monster1.getBody().setLinearVelocity(0f, 0f);
                } else {
                    if (!monster1.isSplit())
                        monster1.animations = Animations.MONSTER1_FLICKERING;
                }
                monster1.getBody().setFixedRotation(true);
            } else
                 if (monster1.getBodyBounds().overlaps(boy.getBodyBounds()) && !boy.animations.name().equals("BOY_PUNCHING") && !boy.actionRect().overlaps(monster1.getBodyBounds()) && !boy.animations.name().equals("BOY_SABER")) {
                boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x + (monster1.getBody().getPosition().x > boy.getBody().getPosition().x ? -15 : 15), boy.getBody().getLinearVelocity().y + 20f);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        beenHit = false;
                    }
                }, 5000);
                if (!beenHit) {
                    boy.beenHit();
                    beenHit = true;
                }
            }
                if (boy.actionRect().overlaps(monster1.getBodyBounds()) && boy.animations.name().equals("BOY_PUNCHING")) {
//                    monster1.getBody().setLinearVelocity(monster1.getBody().getLinearVelocity().x + monster1.getBody().getPosition().x > boy.getBody().getPosition().x ? 15 : -15, monster1.getBody().getLinearVelocity().y + 20f);
//                    monster1.animations = Animations.MONSTER1_FLICKERING;
                    monster1.beenHit();
                }

        }
        for (Block block : blocks)
            if (boy.getBodyBounds().overlaps(block.getRectangle()))
                block.beenHit();
        if (boy.actionRect().overlaps(jack.getBodyBounds()) && boy.animations.name().equals("BOY_PUNCHING")) {
            jack.beenHit();
        }
        for (Objeto o : objetos) {
            for (Body body : bodiesToDestroy) {
                body.setTransform(-10_500, -15_320, 0);
            }
            bodiesToDestroy.clear();
        }
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
        boy.resize(spriteBatch, width, height);
    }

    @Override
    public boolean keyDown(int keycode) {
        boy.keyDown(keycode);
        if (keycode == Input.Keys.ESCAPE){
            takeScreenshot();
            StateManager.setState(StateManager.States.PAUSE);
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
        boy.mouseMoved(screenX, screenY);
        ninjaRope.mouseMoved(screenX, screenY);
//        mouse.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
