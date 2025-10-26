package com.mygdx.game.system;

public class Load{

    public Load(int index) {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/Save" + index + ".dat"));
//            if (!loaded)
//                for (int i = 0; i < objetos.size(); i++) {
//                    world.destroyBody(objetos.get(i).getBody());
//                }
//            if (!newGame && !loaded) {
////                objetos.clear();
//                loaded = true;
//            }
//            currentLevel = (Level) ois.readObject();
//            currentLevel.boy.setViewport(viewport);
//            objetos.add(currentLevel.boy);
//            world = new World(new Vector2(0,-10), true);
//
////            currentLevel.setJack(new Jack(new Vector2(currentLevel.getJack().getBodyData().position)));
////            objetos.add(currentLevel.getJack());
//            objetos.addAll(currentLevel.monsters1.values());
//            objetos.add(currentLevel.getGirl());
//            objetos.add(currentLevel.getJack());
//            currentLevel.init();
//             for (Monster1 m : currentLevel.monsters1.values()) {
//                 m.loadBody(BodyDef.BodyType.DynamicBody, false);
//             }
//            currentLevel.boy.loadBody(BodyDef.BodyType.DynamicBody, false);
//            currentLevel.boy.setViewport(viewport);
//            currentLevel.boy.getViewport().update(Level.WIDTH, Level.HEIGHT);
//            if (currentLevel.boy.getRifle() != null)
//                currentLevel.boy.animations = Animations.valueOf(Boy.nameOfAnimation);
//            else
//            if (currentLevel.boy.animations.name() != null)
//                currentLevel.boy.animations = Animations.valueOf("BOY_IDLE");
//            world.setContactListener(currentLevel);
//            ois.close();
//            StateManager.setStates(StateManager.States.PAUSE);
//            if (Sounds.PAUSE_SONG.isPlaying())
//                Sounds.PAUSE_SONG.stop();
//            if (!Sounds.LEVEL1.isPlaying())
//                Sounds.LEVEL1.play();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch(NullPointerException e){
//            e.printStackTrace();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
