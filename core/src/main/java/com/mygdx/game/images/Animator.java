package com.mygdx.game.images;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.screens.PausePage;
import lombok.Getter;
import lombok.Setter;

public class Animator{
    @Getter
    private int numColumns, numRows, numFrames, width, height;  //rows and columns of the sprite sheet
    private float fps;
    private String path;
    @Getter
    private Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    public Texture spriteSheet;
    public Sprite sprite[];
    public Sprite spriteInverse[];
    @Setter
    @Getter
    public float stateTime; // A variable for tracking elapsed time for the animation
    private TextureRegion[] frames;
    public int totalFrames;
    public int framePosition;
    // Place the regions into a 1D array in the correct order, starting from the top
    // left, going across first. The Animation constructor requires a 1D array.
    private boolean runningFirstTime = true;
    public float frameDuration;
    private float alphaComponent = 0f;
    private Color color = Color.RED;
    public float totalTime;
    public boolean finishedAnimation;

    public static boolean changedAnimation;
    private boolean looping;
    public Animator(int numFrames, int numColumns, float fps, int width, int height, String path) {
        this.numFrames = numFrames;
        this.numColumns = numColumns;
        this.fps = fps;
        this.path = path;
        this.width = width;
        this.height = height;
        //example path = "spriteAnimation.png";
        //int numColumns = 6, numRows = 5;
        int numRows = numFrames / numColumns;
        if (numFrames % numColumns != 0)
            numRows++;
        this.numRows = numRows;
        spriteSheet = new Texture(Gdx.files.internal("" + this.path));   // Load the sprite sheet as a Texture
        sprite = new Sprite[numFrames];
        spriteInverse = new Sprite[numFrames];
        createAnimation();
    }

    public Animator(Color color, int numFrames, int numColumns, float fps, int width, int height, String path) {
        this.numFrames = numFrames;
        this.numColumns = numColumns;
        this.fps = fps;
        this.path = path;
        this.width = width;
        this.height = height;
        this.color = color;
        //example path = "spriteAnimation.png";
        //int numColumns = 6, numRows = 5;
        int numRows = numFrames / numColumns;
        if (numFrames % numColumns != 0)
            numRows++;
        this.numRows = numRows;
        Pixmap pixmap = new Pixmap(Gdx.files.internal("" + this.path));
          // Load the sprite sheet as a Texture
        spriteSheet = new Texture(pixmap);
        spriteSheet = paint(pixmap, color);
        sprite = new Sprite[numFrames];
        spriteInverse = new Sprite[numFrames];
        createAnimation();
    }


    public Animator(float alphaComponent, int numFrames, int numColumns, float fps, int width, int height, String path) {
        this.numFrames = numFrames;
        this.numColumns = numColumns;
        this.fps = fps;
        this.path = path;
        this.width = width;
        this.height = height;
        this.alphaComponent = alphaComponent;
        //example path = "spriteAnimation.png";
        //int numColumns = 6, numRows = 5;
        int numRows = numFrames / numColumns;
        if (numFrames % numColumns != 0)
            numRows++;
        this.numRows = numRows;
        Pixmap pixmap = new Pixmap(Gdx.files.internal("" + this.path));
        // Load the sprite sheet as a Texture
        spriteSheet = new Texture(pixmap);
        spriteSheet = paint(pixmap);
        sprite = new Sprite[numFrames];
        spriteInverse = new Sprite[numFrames];
        createAnimation();
    }


    private Texture paint(Pixmap pixmap, Color color){
        try {
            for (int i = 0; i < pixmap.getWidth(); i++) {
                for (int j = 0; j < pixmap.getHeight(); j++) {
                    int pixel = pixmap.getPixel(i, j);
                    if ((pixel & 0x000000FF) != 0) {
                        pixmap.setColor(color);
                    pixmap.drawPixel(i,j, Color.rgba8888(color.r, color.g, color.b, color.a));
                    }
                }
            }
            return new Texture(pixmap);
        } catch(GdxRuntimeException e){

        }
        return null;
    }

    private Texture paint(Pixmap pixmap){
        try {
            for (int i = 0; i < pixmap.getWidth(); i++) {
                for (int j = 0; j < pixmap.getHeight(); j++) {
                    int pixel = pixmap.getPixel(i, j);
                    if ((pixel & 0x000000FF) != 0.00f) {
                        pixmap.setColor(new Color(1f,1f,1f,0.1f));
                        pixmap.drawPixel(i,j, Color.rgba8888(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.5f));
                    }
                }
            }
            return new Texture(pixmap);
        } catch(GdxRuntimeException e){

        }
        return null;
    }

    private void createAnimation(){
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        // Vinculando a textura ao shader
//        ShaderProgram shader = new ShaderProgram(Gdx.files.internal("vertex_shader.glsl"), Gdx.files.internal("fragment_shader.glsl"));
//        shader.bind();
//
//        // Renderize seus objetos aqui
//        SpriteBatch sp =new SpriteBatch();
//        sp.setShader(shader);
//        spriteSheet.bind(0); // Vincule a textura à unidade 0
//        sp.setShader(null);

        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                width, height);
        frames = new TextureRegion[numFrames];


        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (totalFrames >= numFrames)
                    break;
                sprite[totalFrames] = new Sprite(tmp[i][j]);
                spriteInverse[totalFrames] = new Sprite(tmp[i][j]);
                spriteInverse[totalFrames].flip(true, false);
                frames[totalFrames++] = tmp[i][j];
            }
        }

        frameDuration = 1f/fps;
        totalTime = frameDuration * numFrames;
        // Initialize the Animation with the frame interval and array of frames
        animation = new Animation<>(frameDuration, sprite);
//        for (int index = 0; index < numFrames; index++) {
//            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//            sprite[index] = new Sprite(animation.getKeyFrame(stateTime * 1.01F));
//        }

    }

    public void setFrameCounter(int frame){
        setStateTime(timeToFramePosition(frame));
    }

    public float timeToFramePosition(int frame){
        if (frame >= numFrames)
            frame = numFrames;
        return (1f/fps) * frame;
    }

//    public float totalTime(){
//        return totalTime;
//    }

//    public Sprite sprite(boolean looping){
//        updateFramePosition();
//        return sprite[framePosition];
//    }

    public TextureRegion getFrame(int index){
        if (index < numFrames) {
            return frames[index];
        } else{
            return frames[0];
        }
    }

//    public TextureRegion getFramePosition(){
//        updateFramePosition();
//        return frames[framePosition];
//    }

//    public void updateFramePosition(){
//        if (framePosition >= (numFrames - 1))
//            framePosition = 0;
//        else
//            framePosition++;
//    }

    public void update(){
        if (stateTime < totalTime) {
            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
            finishedAnimation = false;
        }
        else {
            stateTime = totalTime;
            finishedAnimation = true;
        }
        if (changedAnimation) {
            resetAnimation();
            changedAnimation = false;
        }
        if (looping && finishedAnimation){
            resetAnimation();
        }
    }

    public void resetAnimation(){
        stateTime = 0;
    }

     public TextureRegion currentFrame(boolean looping){

         // Get current frame of animation for the current stateTime
         return animation.getKeyFrame(stateTime, looping);
     }

    public int framePosition(){
        return animation.getKeyFrameIndex(stateTime);
    }


    public boolean isAnimFinished(){
        return finishedAnimation;
    }

//    public boolean ani_finished2(){
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//        float targetTime = numFrames/fps;
//        return stateTime >= targetTime;
////        return animation.isAnimationFinished(stateTime);
//    }

//    public void resetStateTime(){
//       if (ani_finished())
//           stateTime = 0f;
//    }

    public TextureRegion currentSpriteFrame0(boolean useOnlyLastFrame, boolean looping, boolean flip){
        // Get current frame of animation for the current stateTime
        Sprite s = null;
        if (!useOnlyLastFrame && !PausePage.pause) {
            s = new Sprite(animation.getKeyFrame(stateTime, looping));
        } else{
            if (useOnlyLastFrame || PausePage.pause){
                s = new Sprite(lastFrame());
            }
        }
//        if (looping && isAnimFinished() && !PausePage.pause) TODO: verificar se é necessário o uso de !PausePage.pause para parar animações em método render
        if (looping && isAnimFinished())
            resetAnimation();
//        s.setColor(color);
//        SpriteBatch s1 = new SpriteBatch();
//        s1.begin();
//        s.draw(s1);
//        s1.end();
//        s1.dispose();
//        s.setRotation((float) Math.toDegrees(rotation));
//        System.out.println(s.getRotation());
        if (flip) {
            s.flip(true, false);
            return s;
        }
        return s;
    }

    public TextureRegion currentSpriteFrame(boolean useOnlyLastFrame, boolean looping, boolean flip){
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        this.looping = looping;
        Sprite s = null;
        if (!useOnlyLastFrame) {
            s = new Sprite(animation.getKeyFrame(stateTime, looping));
        } else{
//            if (useOnlyLastFrame){
                s = new Sprite(lastFrame());
//            }
        }
        //        if (looping && isAnimFinished() && !PausePage.pause) TODO: verificar se é necessário o uso de !PausePage.pause para parar animações em método render
//        if (looping && isAnimFinished())
//            stateTime = 0f;
//        s.setColor(color);
//        SpriteBatch s1 = new SpriteBatch();
//        s1.begin();
//        s.draw(s1);
//        s1.end();
//        s1.dispose();
//        s.setRotation((float) Math.toDegrees(rotation));
//        System.out.println(s.getRotation());
        if (flip) {
            s.flip(true, false);
            return s;
        }
        return s;
    }

    public TextureRegion currentSpriteFrameUpdateStateTime(boolean useOnlyLastFrame, boolean looping, boolean flip){
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        Sprite s = null;
        if (!useOnlyLastFrame) {
            s = new Sprite(animation.getKeyFrame(stateTime, looping));
        } else{
//            if (useOnlyLastFrame){
            s = new Sprite(lastFrame());
//            }
        }
        //        if (looping && isAnimFinished() && !PausePage.pause) TODO: verificar se é necessário o uso de !PausePage.pause para parar animações em método render
//        if (looping && isAnimFinished())
//            stateTime = 0f;
//        s.setColor(color);
//        SpriteBatch s1 = new SpriteBatch();
//        s1.begin();
//        s.draw(s1);
//        s1.end();
//        s1.dispose();
//        s.setRotation((float) Math.toDegrees(rotation));
//        System.out.println(s.getRotation());
        if (flip) {
            s.flip(true, false);
            return s;
        }
        return s;
    }


//    public TextureRegion currentSpriteFrame(boolean useOnlyLastFrame, boolean looping, boolean flip, float w, float h){
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//        // Get current frame of animation for the current stateTime
//        Sprite s = null;
//        if (!useOnlyLastFrame && !PausePage.pause) {
//            s = new Sprite(animation.getKeyFrame(stateTime, looping));
//        } else{
//            if (useOnlyLastFrame || PausePage.pause){
//                s = new Sprite(lastFrame());
//            }
//        }
//        if (looping && ani_finished() && !PausePage.pause)
//            stateTime = 0f;
////        s.setColor(color);
////        SpriteBatch s1 = new SpriteBatch();
////        s1.begin();
////        s.draw(s1);
////        s1.end();
////        s1.dispose();
////        s.setRotation((float) Math.toDegrees(rotation));
////        System.out.println(s.getRotation());
//
//        if (flip) {
//            s.flip(true, false);
//            return s;
//        }
//        return s;
//    }


    public TextureRegion currentSpriteFrame(boolean onlyFirstFrame, float stateTime){
        Sprite s = null;
        if (!PausePage.pause) {
            s = new Sprite(animation.getKeyFrame(stateTime, false));
        }
        return s;
    }

    public TextureRegion currentSpriteFrame(boolean useOnlyLastFrame, boolean looping){
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        Sprite s = null;
        if (!useOnlyLastFrame) {
            s = new Sprite(animation.getKeyFrame(stateTime, looping));
        } if (useOnlyLastFrame){
            s = new Sprite(lastFrame());
        }
        if (looping && isAnimFinished())
            stateTime = 0f;
//        s.setColor(color);
//        SpriteBatch s1 = new SpriteBatch();
//        s1.begin();
//        s.draw(s1);
//        s1.end();
//        s1.dispose();
//        s.setRotation((float) Math.toDegrees(rotation));
//        System.out.println(s.getRotation());
        return s;
    }

    public TextureRegion currentSpriteFrame(int frameUnique, boolean looping, boolean flip){
//        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
        // Get current frame of animation for the current stateTime
        Sprite s = null;
        s = new Sprite(frames[frameUnique]);

        if (looping && isAnimFinished())
            stateTime = 0f;
//        s.setColor(color);
//        SpriteBatch s1 = new SpriteBatch();
//        s1.begin();
//        s.draw(s1);
//        s1.end();
//        s1.dispose();
//        s.setRotation((float) Math.toDegrees(rotation));
//        System.out.println(s.getRotation());
        if (flip) {
            s.flip(true, false);
            return s;
        }
        return s;
    }

//    public float frameCounter(float stateTime){
//        return animation.getKeyFrame(stateTime).getU2() * getNumFrames();
//    }

    public TextureRegion lastFrame(){
        return frames[frames.length - 1];
    }

    public void dispose(){
        spriteSheet.dispose();
    }

//    public void setFrameDuration(float targetTime){
//        frameDuration = targetTime;
//        // Initialize the Animation with the frame interval and array of frames
//        animation = new Animation<>(frameDuration, frames);
//        for (int index = 0; index < numFrames; index++) {
//            stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
//            sprite[index] = new Sprite(animation.getKeyFrame(stateTime * 1.01F));
//       }
//

    public float frameCounter(){
        return animation.getKeyFrameIndex(stateTime);
    }

    public boolean lastFrame0(){
        return stateTime >= (frameDuration * numFrames);
    }

    public float stateTimePosition(int framePosition){
        return frameDuration * framePosition;
    }

    public boolean isOnFrame(float frame){
       return stateTime == (int) (frameDuration * frame);
    }

}
