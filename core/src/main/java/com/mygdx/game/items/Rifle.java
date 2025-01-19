package com.mygdx.game.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.items.Cartridge.MAX_ROUNDS;

public class Rifle extends Objeto implements Item{

    public static final float WIDTH = Images.rifle.getWidth();
    public static final float HEIGHT = Images.rifle.getHeight();
//    public static final int MAX_ROUNDS = 30;
    public final float MULTIPLY = 1/6f;
    @Getter
    private ArrayList<Cartridge> numCartridges = new ArrayList<>();
    @Getter
    private Cartridge leftSideBullets;
    private Vector2 position;
    private float angle = 0f;

    @Getter @Setter
    private boolean buttonReloadingPressed;
    public static boolean showingNumbBullets = false;
    private int rightSide;
    public static String stringNumbBullets = "";
    private int total;

    public Rifle(World world, Vector2 position){

        super(world, WIDTH, HEIGHT);
        super.width = WIDTH * MULTIPLY;
        super.height = HEIGHT * MULTIPLY;
        this.position = position;

        body = createBoxBody(new Vector2(width, height), BodyDef.BodyType.StaticBody, true);
        body.setTransform(position, 0);
        body.setUserData(getClass().getSimpleName());

        for (int index = 0; index < 1; index++){
            numCartridges.add(new Cartridge());
        }

        leftSideBullets = new Cartridge();
        numCartridges.add(leftSideBullets);
    }

    @Override
    public void render(SpriteBatch s) {
        if (!body.getUserData().toString().equals("null")) {
            position = body.getPosition();
            Sprite rifle = new Sprite(Images.rifle);
            rifle.setSize(width, height);
            rifle.setOriginCenter();
            rifle.setRotation(angle);
            rifle.setPosition(position.x, position.y);
            rifle.draw(s);
        }
        if (showingNumbBullets) {
            rightSide = 0;
            for (Cartridge c : numCartridges) {
                if (c.equals(leftSideBullets))
                    break;
                rightSide += c.getBulletsLeft().size();
            }
            total = rightSide;
            stringNumbBullets = leftSideBullets + "/" + rightSide;
        } else{
            stringNumbBullets = "";
        }
        leftSideBullets.render(s);
    }

    public void reloading() {
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (Cartridge.reloading) {
                    if (!numCartridges.isEmpty()) {
                        if (!leftSideBullets.getBulletsLeft().isEmpty() &&  //se não estiver vazio e não estiver cheio o cartucho que aparece na esquerda
                            leftSideBullets.getBulletsLeft().size() < MAX_ROUNDS) {
                            removeBulletsFromFirstCartridge(leftSideBullets.getAccumulated());
                            if (!leftSideBullets.equals(numCartridges.getFirst())) {
                                leftSideBullets.getBulletsLeft().clear();
                                leftSideBullets.setBulletsLeft(init(max_bullets()));
                            }
                        }
                        if (numCartridges.getFirst().getBulletsLeft().isEmpty())
                            numCartridges.removeFirst();
                        else {

                        }
                        if (leftSideBullets.getBulletsLeft().isEmpty()) {
                            if (numCartridges.size() > 1 && total >= MAX_ROUNDS)
                                leftSideBullets = new Cartridge();
                        }
                    }
                    buttonReloadingPressed = false;
                    Cartridge.reloading = false;
                }
            }
        }, 1f);
    }

    @Override
    public void updateItem() {
       reloading();
    }

    @Override
    public void update() {
        angle++;
    }

    @Override
    public void renderShape(ShapeRenderer s) {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    private ArrayList<Bullet> init(int max_rounds){
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (int index = 1; index <= max_rounds; index++){
            bullets.add(new Bullet());
        }
        return bullets;
    }

    private void removeBulletsFromFirstCartridge(int accumulated) {
        if (numCartridges.getFirst().equals(leftSideBullets))
            return;
        for (int i = accumulated; i > 0; i--)
            numCartridges.getFirst().getBulletsLeft().removeLast();
        leftSideBullets.setAccumulated(0);
    }

    private int max_bullets(){
        total = leftSideBullets.getBulletsLeft().size()
            + total - (total >= MAX_ROUNDS ? MAX_ROUNDS : leftSideBullets.getBulletsLeft().size());
        return Math.max(total, MAX_ROUNDS);
    }
}
