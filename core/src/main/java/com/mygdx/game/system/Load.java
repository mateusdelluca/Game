package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Jack;
import com.mygdx.game.entities.Monster1;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Animations;
import com.mygdx.game.items.Crystal;
import com.mygdx.game.items.JetPack;
import com.mygdx.game.items.Portal;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level;
import com.mygdx.game.sfx.Sounds;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import static com.mygdx.game.screens.MainPage.newGame;
import static com.mygdx.game.screens.levels.Level_Manager.*;

public class Load{

    public Load(int index){
        try {
            loaded = true;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/Save" + index + ".dat"));
            for (int i = 0; i < objetos.size(); i++) {
                world.destroyBody(objetos.get(i).getBody());
                if (!newGame) {
                    objetos.remove(i);
                    newGame = true;
                }
            }
            currentLevel.boy.setViewport(viewport);
            currentLevel = (Level) ois.readObject();
            ois.close();
            world = new World(new Vector2(0,-10), true);
//            currentLevel.setJack(new Jack(new Vector2(currentLevel.getJack().getBodyData().position)));
//            objetos.add(currentLevel.getJack());
            objetos.addAll(currentLevel.monsters1.values());
            currentLevel.init();
             for (Monster1 m : currentLevel.monsters1.values()) {
                 m.loadBody(BodyDef.BodyType.DynamicBody, false);
             }
            currentLevel.boy.loadBody(BodyDef.BodyType.DynamicBody, false);
            currentLevel.boy.setViewport(viewport);
            currentLevel.boy.getViewport().update(Level.WIDTH, Level.HEIGHT);
            if (currentLevel.boy.getRifle() != null)
                currentLevel.boy.animations = Animations.valueOf(Boy.nameOfAnimation);
            else
                currentLevel.boy.animations = Animations.valueOf("BOY_IDLE");
            StateManager.setState(StateManager.States.PAUSE);
            if (Sounds.PAUSE_SONG.isPlaying())
                Sounds.PAUSE_SONG.stop();
            if (!Sounds.LEVEL1.isPlaying())
                Sounds.LEVEL1.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

}
