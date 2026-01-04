package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.images.PowerBar;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

import static com.mygdx.game.entities.Player.lvlUP;
import static com.mygdx.game.images.Player_Animations.*;
import static com.mygdx.game.screens.Stats.*;
import static com.mygdx.game.screens.levels.Level.player;

public class Character_Features {

    @Getter @Setter
    private float hp = 250, sp = 100, power = 120; //TODO: arrumar o hp da classe PowerBar colocando nesta classe e usar para outros personagens
    @Getter @Setter
    private float attack = 12f, def = 1, jumpingStrength = 12_000f, maxVelocityWalking = 15f;
    @Getter @Setter
    private int[] stats_values = new int[STATS_SIZE];
    public static float velocityX = 1500f, laserDistance = 1000f;

    //TODO: por enquanto o enemy_atack fica nesta classe, mas implementar para cada personagem
    @Getter @Setter
    private int enemy_attack = 12;
    @Getter
    private int laserDamage = 1, damage = -10, damageDraw = -10;
    @Getter
    private float powerSpent = PowerBar.power;
    public static float recoveryPowerGreenPotion = 30f, recoveryPowerBluePotion = 10f, recoveryPowerRedPotion = 10f;
    private float timer;
    private int agility = 1;


    public Character_Features(){
        Arrays.fill(stats_values, 1);
        init();
    }
    public void init() {
        PowerBar.maxSP = 100f + (4 * stats_values[WSD]);
        PowerBar.maxHP = 300f + (6f * stats_values[VIT]);
        PowerBar.maxPower = 50f + (5f * stats_values[WSD]);
        attack = 12f + (0.5f * stats_values[STR]);
        velocityX = 1500f + (150 * stats_values[AGI]);
        def = 1f + (0.5f * stats_values[VIT]);
        jumpingStrength = 12_000f + (1_000f * stats_values[STR]);
        laserDistance = 8_000f + (3000f * stats_values[DEX]);
        laserDamage = (int) (1 + (0.5f * stats_values[DEX]));
        recoveryPowerGreenPotion = 10f + stats_values[WSD] / 2f;
        powerSpent = 2f / (stats_values[WSD] / 2f);
        recoveryPowerBluePotion = 10f + stats_values[WSD] / 4f;
        recoveryPowerRedPotion = 10f + stats_values[VIT] / 4f;
        agility();
        if (!lvlUP && exp_Points >= 25) {
            points += 10;
            base_level += 1;
            exp_Points = 1;
//            EAGLE.play();
            lvlUP = true;
        }
    }

    public void drawDamage(SpriteBatch spriteBatch, BitmapFont font, Body body){
        font.draw(spriteBatch, "" + damageDraw, body.getPosition().x, body.getPosition().y);
    }

    public void update(Objeto obj){
        if (!obj.beenHit) {
            PowerBar.maxSP = 100f + (4 * stats_values[WSD]);
            PowerBar.maxHP = 300f + (6f * stats_values[VIT]);
            PowerBar.maxPower = 50f + (5f * stats_values[WSD]);
        }
        attack = 12f + (0.5f * stats_values[STR]);
        velocityX = 1500f + (150 * stats_values[AGI]);
        agility();
        def = 1f + (0.5f * stats_values[VIT]);
        jumpingStrength = 12_000f + (1_000f * stats_values[STR]);
        laserDistance = 8_000f + (3000f * stats_values[DEX]);
        laserDamage = (int) (1 + (0.5f * stats_values[DEX]));
        recoveryPowerGreenPotion = 10f + stats_values[WSD]/2f;
        powerSpent = 2f/(stats_values[WSD]/2f);
        recoveryPowerBluePotion = 10f + stats_values[WSD] / 4f;
        recoveryPowerRedPotion = 10f+ stats_values[VIT] / 4f;
        damage(obj);
        if (!lvlUP && exp_Points >= 25) {
            points += 10;
            base_level += 1;
            exp_Points = 1;
//            EAGLE.play();
            lvlUP = true;
        }
    }


    public void damage(Objeto obj){
        if (obj.beenHit) {
            if (obj.getBody().getUserData().toString().contains("Enemy"))
                damage = (int) (attack >= def ? -Math.abs(attack - (def / 2)) : -Math.abs(attack - (def / 4)));
            if (obj.getBody().getUserData().toString().equals("Player"))
                damage = (int) (enemy_attack >= def ? -Math.abs(enemy_attack - (def / 2)) : -Math.abs(enemy_attack - (def / 4)));
            damageDraw = damage;
            timer += Gdx.graphics.getDeltaTime();
            if (timer >= 0.2f) {
                obj.beenHit = false;
                timer = 0f;
                hp += damage;
                System.out.println(hp);
            }
        }
    }

    public void agility(){
        if (changed[AGI]) {
            agility = 5 + stats_values[AGI] / 2;
            ATTACKING_SWORD_FIRE_2.animator.createAnimation(agility);
            WALKING.animator.createAnimation(agility);
            WALKING_SWORD.animator.createAnimation(agility);
            PUNCHING_FIRE.animator.createAnimation(agility);
            changed[AGI] = false;
        }
    }
}
