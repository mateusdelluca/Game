package com.mygdx.game.entities.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import static com.mygdx.game.sfx.Sounds.GUNSHOT;
import static com.mygdx.game.sfx.Sounds.TRIGGER;

public class Backup implements Item{

    private final int numberBulletsPerMagazine;
    @Getter @Setter
    private int numberBulletsInMagazine;
    @Getter
    @Setter
    private ArrayList<Bullet> bullets = new ArrayList<>();
    //    public static String stringNumbBullets = "";
    public static boolean showingNumbBullets = false;
    @Getter @Setter
    private int numberMagazines, restOfBullets;
    @Getter @Setter
    private boolean reloading;
    private int total;
    @Getter @Setter
    private boolean buttonReloadingPressed;

    public Backup(final int numberBulletsPerMagazine){
        this.numberBulletsPerMagazine = numberBulletsPerMagazine;
        numberBulletsInMagazine = numberBulletsPerMagazine;
        numberMagazines = 3;
    }

    public Backup(final int numberBulletsPerMagazine, int numberMagazines){
        this.numberBulletsPerMagazine = numberBulletsPerMagazine;
        numberBulletsInMagazine = numberBulletsPerMagazine;
        this.numberMagazines = numberMagazines;
    }

    @Override
    public void render(SpriteBatch s) {
        for (Bullet bullet : bullets)
            bullet.render(s);
//        if (showingNumbBullets) {
//            total = (numberBulletsPerMagazine * numberMagazines) + restOfBullets;
//            stringNumbBullets = numberBulletsInMagazine + "/" + total;
//        }
//       else
//            stringNumbBullets = "";
    }

    private void reloading(){
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (reloading) {
                    if (numberMagazines == 0 && restOfBullets > 0) {
                        if (restOfBullets <= numberBulletsPerMagazine) {
                            if (numberBulletsInMagazine == 0 || numberBulletsInMagazine < numberBulletsPerMagazine) {
                                numberBulletsInMagazine = restOfBullets;
                                restOfBullets = 0;
                            }
                            if (numberBulletsInMagazine > 0) {
                                if ((numberBulletsInMagazine + restOfBullets) <= numberBulletsPerMagazine)
                                    numberBulletsInMagazine += restOfBullets;
                                else{
                                    if ((numberBulletsInMagazine + restOfBullets) > numberBulletsPerMagazine){
                                        restOfBullets -= numberBulletsPerMagazine;
                                        numberBulletsInMagazine += Math.abs(restOfBullets);
                                        if (restOfBullets < 0)
                                            restOfBullets = 0;
                                    }
                                }
                            }
                        }
                        if (restOfBullets > numberBulletsPerMagazine){
                            restOfBullets -= numberBulletsPerMagazine;
                            numberBulletsInMagazine = numberBulletsPerMagazine;
                        }
                    } else {
                        if (numberMagazines > 0) {
                            if (numberBulletsInMagazine < numberBulletsPerMagazine) {
                                restOfBullets += numberBulletsInMagazine;
                                numberMagazines--;
                                numberBulletsInMagazine = numberBulletsPerMagazine;
                            }
                        }
                    }
                    TRIGGER.play();
                    reloading = false;
                    buttonReloadingPressed = false;
                }
            }
        }, 1);
    }

    @Override
    public void updateItem() {
        reloading();
    }

    public boolean isReloadingAllowed(){
        return (numberBulletsInMagazine < numberBulletsPerMagazine);
    }

    public void newBullet(Bullet bullet){
        if (numberBulletsInMagazine > 0 && !reloading) {
            numberBulletsInMagazine--;
            GUNSHOT.play();
            bullets.add(bullet);
        }
    }
}
