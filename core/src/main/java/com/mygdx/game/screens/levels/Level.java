package com.mygdx.game.screens.levels;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.tommyettinger.textra.Font;
import com.mygdx.game.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.*;
import com.mygdx.game.handler.MyContactListener;
import com.mygdx.game.images.Animations;
import com.mygdx.game.images.PowerBar;
import com.mygdx.game.screens.PauseScreen;
import com.mygdx.game.screens.Tile;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.entities.Crystal.numCrystalsCollected;

public abstract class Level implements Screen, InputProcessor {

    public static final int WIDTH = 1920, HEIGHT = 1080;
    protected Application app;

    public SpriteBatch spriteBatch;
    public Viewport viewport;
    public OrthographicCamera camera;
    public World world;
    public Background background;
    public ArrayList<Crystal> crystals;
    Box2DDebugRenderer box2DDebugRenderer;
    ShapeRenderer shapeRenderer;
    @Getter @Setter
    private Tile tile;
    private Portal portal;
    private ArrayList<Rectangle> thorns_rects;
    protected MapObjects thorns, staticObjects;
    public ArrayList<RectangleMapObject> thornsRectangleMapObjects;
    public ArrayList<Rectangle> horizontalRectsThorns;
    public ArrayList<Rectangle> verticalRectsThorns;
    private PowerBar powerBar;
    protected Boy boy;
    protected Monster1[] monsters1 = new Monster1[2];
    protected Jack jack;
    String tilePath;
    private Leaf[] leafs = new Leaf[50];

    private BitmapFont font;
    private String mensage = "Collect all blue crystals!";
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    private MyContactListener myContactListener;
    public Level(String tilePath, Application app){
        this.app = app;
        this.tilePath = tilePath;
        world = new World(new Vector2(0,-10f), false);
        spriteBatch = new SpriteBatch();
//        player = new Player(world, this);
//        enemies = new ArrayList<Enemy>();
//        for (int i = 1; i < 5; i++){
//            enemies.add(new Enemy(world, new Vector2(1000 * i, 350)));
//        }
//        camera.setToOrtho(false);
//        camera.viewportHeight = Gdx.graphics.getHeight() * (float) 1/32;
//        camera.viewportWidth = Gdx.graphics.getWidth() * (float) 1/32;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        viewport = new ScreenViewport(camera);
        camera.update();
        thorns_rects = new ArrayList<>();

        Texture t = new Texture(Gdx.files.internal("Font2.png"));
        font = new BitmapFont(Gdx.files.internal("Font2.fnt"), new TextureRegion(t));
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        font.getData().scale(0.2f);

        tile = new Tile(tilePath);
        staticObjects = tile.loadMapObjects("Rects");
        tile.createBodies(staticObjects, world, false);

        thorns = tile.loadMapObjects("Thorns");
        tile.createBodies(thorns, world, false);

        thornsRectangleMapObjects = new ArrayList<>();
//        horizontalRectsThorns = new ArrayList<>();
        verticalRectsThorns = new ArrayList<>();

        for (MapObject m : thorns){
            RectangleMapObject t1 = (RectangleMapObject) m;
            thornsRectangleMapObjects.add(t1);
//            if (t1.getName().equals("Thorns1")){
//                t1.getRectangle().width += 7f;
//                t1.getRectangle().height -= 10f;
//                horizontalRectsThorns.add(t1.getRectangle());
//
//            } else {
//                if (t1.getName().equals("Thorns5")) {
//                    t1.getRectangle().x -= 7f;
//                    t1.getRectangle().width += 7f;
//                    t1.getRectangle().height -= 10f;
//                    horizontalRectsThorns.add(t1.getRectangle());
//                } else {
//                    if (t1.getName().equals("Thorns6")) {
//                        t1.getRectangle().x -= 7f;
//                        t1.getRectangle().width += 7f;
//                        t1.getRectangle().height -= 10f;
//                        horizontalRectsThorns.add(t1.getRectangle());
//                    } else {
            t1.getRectangle().height += 7f;
            t1.getRectangle().y -= 7f;
            verticalRectsThorns.add(t1.getRectangle());
            myContactListener = new MyContactListener(world);
        }
//                }
//            }
//        }

        crystals = new ArrayList<>();
        portal = new Portal();
        for (int i = 0; i < Crystal.X_POSITIONS.length; i++) {
            crystals.add(new Crystal(world));
        }
        background = new Background();
        box2DDebugRenderer = new Box2DDebugRenderer(true, false, false, false, false, true);


        shapeRenderer = new ShapeRenderer();
        powerBar = new PowerBar();


        boy = new Boy(world, new Vector2(100, 800));
        monsters1[0] = new Monster1(world, new Vector2(300, 450));
        monsters1[1] = new Monster1(world, new Vector2(1600, 650));

        for (int i = 0; i < leafs.length; i++)
            leafs[i] = new Leaf(world, new Vector2(new Random().nextFloat(10_000), new Random().nextFloat(10_000)));
         jack = new Jack(world, new Vector2(2150, 650));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);// Clear screen
//        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        for (int i = 0; i < 5; i++)
            update(delta);
//        player.update(delta);
//        for (Enemy enemy : enemies)
//            enemy.update(delta);
        update(delta);

        background.render();
        powerBar.render();
        box2DDebugRenderer.render(world, camera.combined);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setAutoShapeType(true);



//        System.out.println(boy.getBody().getPosition().toString());

        spriteBatch.setProjectionMatrix(camera.combined);
        if (boy.getBody().getPosition().x > WIDTH/2f && boy.getBody().getPosition().x < (6000 - WIDTH))
            camera.position.set((boy.getBody().getPosition().x) + WIDTH/4f, 400 + HEIGHT/4f, 0);
        if (boy.getBody().getPosition().x >= (6000 - WIDTH))
            camera.position.set(6000 - WIDTH/2f, HEIGHT / 2f, 0);
        if (boy.getBody().getPosition().x < WIDTH/2f)
            camera.position.set(0f + WIDTH / 2f, 400 + HEIGHT / 4f, 0);
        renderObjects();

        shapeRenderer.begin();
//        for (Enemy enemy : enemies)
//            enemy.render(shapeRenderer);
//        player.render(shapeRenderer);
        for (Crystal c : crystals)
            c.render(shapeRenderer);
        portal.render(shapeRenderer);
        boy.render(shapeRenderer);
        for (Monster1 m : monsters1)
            m.render(shapeRenderer);

        shapeRenderer.end();
    }

    public void renderObjects(){
        spriteBatch.begin();
//        level1.render(camera);
        tile.render(camera);
        portal.render(spriteBatch);
        for (Crystal crystal : crystals)
            crystal.render(spriteBatch);
//        for (Enemy enemy : enemies)
//            enemy.render(spriteBatch);
//        player.render(spriteBatch);
        boy.render(spriteBatch);
        jack.render(spriteBatch);
        for (Monster1 m : monsters1)
            m.render(spriteBatch);
        for (Leaf l : leafs)
            l.render(spriteBatch);
        jack.render(spriteBatch);
        for (Bullet bullet : bullets)
            bullet.render(spriteBatch);
        font.draw(spriteBatch,mensage, 850,400);
        spriteBatch.end();
    }

    public void update(float delta){
        world.step(delta, 7,7);
        camera.update();
        world.step(delta, 7,7);
        camera.update();

//        for (Rectangle rect : horizontalRectsThorns) {
//            if (rect.overlaps(player.getBodyBounds())){
//                if (rect.equals(horizontalRectsThorns.get(1))){
//                    player.getBody().setTransform(player.getBody().getPosition().x - 7f, player.getBody().getPosition().y, 0);
//                } if(rect.equals(horizontalRectsThorns.get(0))){
//                    player.getBody().setTransform(player.getBody().getPosition().x + 7f, player.getBody().getPosition().y, 0);
//                }
//                System.out.println("horizontalRectsThorns");
//                if (!player.animations.name().equals("IDLE_FLASH")) {
//                    PowerBar.hp -= 20;
//                    player.animations = Animations.IDLE_FLASH;
//                }
//            }
//        }
//        for (Rectangle rect : verticalRectsThorns) {
//            if (rect.overlaps(player.getBodyBounds())){
//                System.out.println("verticalRectsThorns");
//                player.getBody().setTransform(player.getBody().getPosition().x, player.getBodyBounds().y - 90f, 0);
//                if (!player.animations.name().equals("IDLE_FLASH") && !player.animations.name().equals("JUMPING") && !player.isHited()) {
//                    player.setHited(true);
//                    Timer timer = new Timer();
//                    timer.scheduleTask(new Timer.Task() {
//                        @Override
//                        public void run() {
//                            PowerBar.hp -= 20; player.setHited(false);
//                        }
//                    }, 0f, 1000);
//                    player.animations = Animations.IDLE_FLASH;
//                    player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y + 10f);
//                }
//            }
//        }

//        for (Enemy enemy : enemies) {
//            if (!enemy.isSplit()){
//                if (Math.abs(enemy.getBodyBounds().x - player.getBodyBounds().x) < 300 && enemy.getBody().getAngle() < Math.toRadians(30f)) {
//                   if (!enemy.isHited()) {
//                       enemy.setAnimation("E_WALKING");
//                       enemy.getBody().setLinearVelocity(player.getBodyBounds().x < enemy.getBodyBounds().x ? -5 : 5,
//                               enemy.getBody().getLinearVelocity().y);
//                       enemy.setFlip(player.getBodyBounds().x < enemy.getBodyBounds().x);
//                   } else {
//                       if (enemy.getBody().getLinearVelocity().x > 0)
//                           enemy.setFlip(false);
//                       else
//                           enemy.setFlip(true);
//                       if (Math.abs(enemy.getBodyBounds().x - player.getBodyBounds().x) < 100) {
//                           if (!enemy.animations.name().equals("E_PUNCHED") &&
//                                   !Intersector.overlaps(player.getAction(), enemy.getBodyBounds())) {
//                               enemy.setAnimation("E_PUNCH");
//                           }
//                       }
//                   }
//                   if (player.getAction().overlaps(enemy.getBodyBounds())) {
//                        enemy.setAnimation("E_PUNCHED");
//                        if (!enemy.isHited()) {
//                            enemy.setHited(true);
//                            PUNCHED.play();
//                        }
//                        enemy.setFrameCounter(0);
//                   }
//                } else {
//                    enemy.getBody().setLinearVelocity(0, enemy.getBody().getLinearVelocity().y);
//                    enemy.setAnimation(("E_IDLE"));
//                }
//
//                if (Intersector.overlaps(enemy.getAction(), player.getBodyBounds())) {
//                    if (!player.isHited()) {
//                        player.animations = Animations.PUNCHED;
//                        player.setHited(true);
//                        player.setFrameCounter(0);
//                    }
//                }
//            }
//            if (Intersector.overlaps(player.getAction(), enemy.getBodyBounds()) && player.animations.name().equals("SABER")) {
//                enemy.setAnimation("E_SPLIT");
//                enemy.setSplit(true);
//                enemy.getBody().setLinearVelocity(0,0);
//                enemy.getBody().setFixedRotation(true);
//            }
//        }
        for (int index = 0; index < Crystal.X_POSITIONS.length; index++){
            crystals.get(index).taked(boy.getBodyBounds());
        }
//        if (portal.getRectangle().contains(player.getBodyBounds())){
//            teletransport.play();
//            player.getBody().setTransform(350, 400, 0);
//        }
        for (int index = 0; index < bullets.size(); index++) {
            for (Monster1 monster1 : monsters1){
                if (bullets.get(index).intersectsRectangle(monster1.getRect())) {
                    monster1.setHP(monster1.getHP() - 1);
                }
            }
            if (bullets.get(index).intersectsRectangle(boy.getBodyBounds())
                || boy.getBodyBounds().overlaps(bullets.get(index).getRect())) {
//                if (!boy.isStricken()) {
                    hited(bullets.get(index));
//                }
            }
        }

        if (numCrystalsCollected >= 16) {
            Portal.open_portal = true;
            mensage = "The portal is opened!";
        }

        if (portal.getRectangle().contains(boy.getBodyBounds())){
            Sounds.TELETRANSPORT.play();
            Levels.changeLevel("Level2", app);
            boy.getBody().setTransform(100, 800, 0);
        }
        for (Monster1 monster1 : monsters1) {
            if (boy.actionRect().overlaps(monster1.getBodyBounds())) {
                monster1.getBody().setLinearVelocity(0,0);
                if (boy.animations.name().equals("BOY_SABER")) {
                    monster1.animations = Animations.MONSTER1_SPLIT;
                    monster1.setSplit(true);
                    for (Fixture f : monster1.getBody().getFixtureList()){
                        f.setSensor(true);
                    }
                    monster1.getBody().setGravityScale(0f);
                    monster1.getBody().setLinearVelocity(0f,0f);
                }
                else {
                    if (!monster1.isSplit())
                        monster1.animations = Animations.MONSTER1_FLICKERING;
                }
                monster1.getBody().setFixedRotation(true);
            } else
            if (monster1.getBodyBounds().overlaps(boy.getBodyBounds()) && !boy.actionRect().overlaps(monster1.getBodyBounds()) && !boy.animations.name().equals("BOY_SABER")) {
                hited(monster1);
            }
            if (boy.actionRect().overlaps(monster1.getBodyBounds())) {
                monster1.getBody().setLinearVelocity(monster1.getBody().getLinearVelocity().x + monster1.getBody().getPosition().x > boy.getBody().getPosition().x ? 15 : -15, monster1.getBody().getLinearVelocity().y + 2f);
                monster1.animations = Animations.MONSTER1_FLICKERING;
            }
            if (boy.getBodyBounds().overlaps(monster1.getBodyBounds()) && !monster1.isSplit()) {
                boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x, boy.getBody().getLinearVelocity().y + 20f);
                monster1.animations = Animations.MONSTER1_FLICKERING;
                hited();
            }
            for (Rectangle rect : verticalRectsThorns) {
                if (boy.getBodyBounds().overlaps(rect)) {
                    hited();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//        player.resize(spriteBatch, width, height);
//        for (Enemy enemy : enemies)
//            enemy.resize(spriteBatch, width, height);
        boy.resize(spriteBatch, width, height);
//        for (Monster1 monster1 : monsters1)
//            monster1.resize(spriteBatch. width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        for (Crystal c : crystals)
            c.dispose();
        spriteBatch.dispose();
        world.dispose();
        background.dispose();
        powerBar.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        boy.keyDown(keycode);
//        if (player.isStand()) {
//            player.keyDown(keycode);
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.input.setInputProcessor(app.jogo.pauseScreen);
            app.setScreen(app.jogo.pauseScreen);
//          level_musicPosition = songLevel1.getPosition();
            Sounds.LEVEL1.stop();
            if (!Sounds.PAUSE_SCREEN.isPlaying()) {
                Sounds.PAUSE_SCREEN.play();
//                Sounds.PAUSE_SCREEN.setPosition(PauseScreen.pause_musicPosition);
            }
        }



//        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        boy.keyUp(keycode);
//        player.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        player.touchDown(screenX, screenY,pointer,button);
        boy.touchDown(screenX, screenY,pointer, button);
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
//        player.mouseMoved(screenX, screenY);
        boy.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

//    public static TimerTask timerTask(Runnable... r){
//        return new TimerTask() {
//            @Override
//            public void run() {
//                for (Runnable rr : r)
//                    rr.run();
//            }
//        };
//    }
//
//    private void timer(long timer1, Runnable... r){
//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(timerTask(r), timer1);
//    }


    private void hited(Objeto object){
        boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x + (object.getBody().getPosition().x > boy.getBody().getPosition().x ? -15 : 15), boy.getBody().getLinearVelocity().y + 15f);
        boy.animations = Animations.BOY_STRICKEN;
        boy.setStricken(true);
        PowerBar.hp -= 20;
        Sounds.HURT.play();
    }

    private void hited(){
        boy.getBody().setLinearVelocity(boy.getBody().getLinearVelocity().x, 100f);
        boy.animations = Animations.BOY_STRICKEN;
        PowerBar.hp -= 20;
        boy.setStricken(true);
        Sounds.HURT.play();
    }
}
