package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Boy;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Animations;
import com.mygdx.game.items.Crystal;
import com.mygdx.game.items.JetPack;
import com.mygdx.game.items.Portal;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import static com.mygdx.game.screens.levels.Level_Manager.*;

public class Load{

//    public Load(int numSave){
//        FileHandle fileHandle = Gdx.files.internal("saves/Save" + numSave + ".json");
//        State currentState;
//        try {
//            ObjectInputStream ois = new ObjectInputStream(fileHandle.read());
//            currentState = (State) ois.readObject();
//            StateManager.States.LEVEL.setState(currentState);
//            StateManager.setState(StateManager.States.LEVEL);
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public Load(int index){
        try {
            loaded = true;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Save" + index + ".dat"));
            for (Objeto objeto : objetos) {
                objeto.getBody().setUserData("null");
                bodiesToDestroy.add(objeto.getBody());
                world.destroyBody(objeto.getBody());
            }
            currentLevel.boy.setViewport(viewport);
            currentLevel = (Level) ois.readObject();
            ois.close();
            world = new World(new Vector2(0,-10), true);
            currentLevel.init();
            world.setContactListener(currentLevel);
            currentLevel.boy.loadBody(BodyDef.BodyType.DynamicBody, false);
            currentLevel.boy.setViewport(viewport);
            currentLevel.boy.getViewport().update(Level.WIDTH, Level.HEIGHT);
            if (currentLevel.boy.getRifle() != null)
                currentLevel.boy.animations = Animations.valueOf(Boy.nameOfAnimation);
            else
                currentLevel.boy.animations = Animations.valueOf("BOY_IDLE");
            for (Objeto objeto : objetos) {
                if (objeto instanceof Crystal || objeto instanceof Rifle || objeto instanceof JetPack
                    || objeto instanceof Portal)
                    objeto.loadBody(BodyDef.BodyType.StaticBody, true);
//                    if (objeto instanceof Monster1)
//                        objeto.loadBody(BodyDef.BodyType.DynamicBody, false);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
