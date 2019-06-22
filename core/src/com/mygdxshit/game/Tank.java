package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Tank {

    private Polygon Tank;
    public Bullets bullets;
    private int lives;
    private int score;
    private int speed;
    private int startPosY;
    private int startPosX;
    private int direction;
    private int correction;
    private int startDirection;
    private boolean shooted;
    private float stateTime;
    private Sprite tankSprite;
    private Sprite liveSprite;
    private Sprite CraterSprite;
    private Array<Vector2> craters;
    private Animation<TextureRegion> animTank;
    private Animation<TextureRegion> explode;
    private TextureRegion[] tankFrames;
    private TextureRegion[] explodeFrames;
    private BitmapFont font;

    Tank(int startPosX, int startPosY, int startDirection) {
        Tank = new Polygon(new float[]{0, 0, 60, 0, 60, 100, 0, 100});
        Tank.setOrigin(30, 50);
        this.startPosY = startPosY;
        this.startPosX = startPosX;
        this.startDirection = startDirection;
        respawn(startPosX, startPosY, startDirection);

        stateTime = 0f;
        score = 0;
        bullets = new Bullets();
        Texture LivesImage = new Texture(Gdx.files.internal("lives.png"));
        craters = new Array<Vector2>();
        Texture CraterImage = new Texture(Gdx.files.internal("ExplosionCrater.png"));
        CraterSprite = new Sprite(CraterImage);
        CraterSprite.setSize(70, 70);
        liveSprite = new Sprite(LivesImage);
        font = new BitmapFont(Gdx.files.internal("test.fnt"));
        correction = (Gdx.graphics.getWidth() - 96 * 20) / 2;
        loadAnimations();
    }

    private void respawn(int posX, int posY, int direction) {
        Tank.setPosition(posX + 20, posY - 20);
        Tank.setRotation(direction * 90);
        speed = 0;
        lives = 3;
        this.direction = direction;
        shooted = false;
    }

    public void decLives() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public void setShooted(boolean shooted) {
        this.shooted = shooted;
    }

    public boolean isShooted() {
        return shooted;
    }

    public Polygon getTank() {
        return Tank;
    }

    public void checkMap(int[][] map) {
        if (Tank.getX() < 0) Tank.setPosition(0, Tank.getY());
        if (Tank.getX() > Gdx.graphics.getWidth() - 101)
            Tank.setPosition(Gdx.graphics.getWidth() - 94, Tank.getY());
        if (Tank.getY() < 0) Tank.setPosition(Tank.getX(), 0);
        if (Tank.getY() > Gdx.graphics.getHeight() - 120)
            Tank.setPosition(Tank.getX(), Gdx.graphics.getHeight() - 80);
        switch (direction) {
            case 0:
                if (map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() + 60 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() + 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() + 40 - correction) / 20)] >= 1)
                    if (checkBack(map))
                        movingBack();
                    else direction++;
                break;
            case 1:
                if (map[(int) ((Tank.getY() + 20) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 40) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 60) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 80) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1)
                    if (checkBack(map))
                        movingBack();
                    else direction--;
                break;
            case 2:
                if (map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() + 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() + 40 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() + 60 - correction) / 20)] >= 1) {
                    if (checkBack(map))
                        movingBack();
                    else direction++;
                }
                break;
            case 3:
                if (map[(int) (Tank.getY() + 20) / 20][(int) (Tank.getX() - 20 - correction) / 20] >= 1 ||
                        map[(int) ((Tank.getY() + 40) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 60) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 80) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1)
                    if (checkBack(map))
                        movingBack();
                    else direction++;
                break;
        }
    }

    public boolean checkBack(int[][] map) {
        switch (direction) {
            case 0:
                if (map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() + 60 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() + 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY()) / 20)][(int) ((Tank.getX() + 40 - correction) / 20)] >= 1)
                    return false;
                break;
            case 1:
                if (map[(int) ((Tank.getY() + 20) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 40) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 60) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 80) / 20)][(int) ((Tank.getX() - 20 - correction) / 20)] >= 1)
                    return false;
                break;
            case 2:
                if (map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() + 20 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() + 40 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 100) / 20)][(int) ((Tank.getX() + 60 - correction) / 20)] >= 1) {
                    return false;
                }
                break;
            case 3:
                if (map[(int) (Tank.getY() + 20) / 20][(int) (Tank.getX() + 80 - correction) / 20] >= 1 ||
                        map[(int) ((Tank.getY() + 40) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 60) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1 ||
                        map[(int) ((Tank.getY() + 80) / 20)][(int) ((Tank.getX() + 80 - correction) / 20)] >= 1)
                    return false;
                break;
        }
        return true;
    }

    public void movingBack() {
        switch (direction) {
            case 0:
                Tank.setPosition(Tank.getX(), (Tank.getY() - speed * Gdx.graphics.getDeltaTime()));
                break;
            case 1:
                Tank.setPosition((Tank.getX() - speed * Gdx.graphics.getDeltaTime()), Tank.getY());
                break;
            case 2:
                Tank.setPosition(Tank.getX(), (Tank.getY() + speed * Gdx.graphics.getDeltaTime()));
                break;
            case 3:
                Tank.setPosition((Tank.getX() + speed * Gdx.graphics.getDeltaTime()), Tank.getY());
                break;
        }
    }

    public void showLives(SpriteBatch batch, boolean side, int score) {
        if (side) {
            font.draw(batch, "Your lives ", correction, Gdx.graphics.getHeight());
            for (int i = 0; i < lives; i++) {
                liveSprite.setPosition(correction + 200 + i * 50, 52 * 20);
                liveSprite.draw(batch);
            }
            font.draw(batch, "Score:  " + score + " : ", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight());
        } else {
            font.draw(batch, "Enemy lives", Gdx.graphics.getWidth() - correction - 220, Gdx.graphics.getHeight());
            for (int i = 0; i < lives; i++) {
                liveSprite.setPosition(Gdx.graphics.getWidth() - correction - 400 + i * 50, 52 * 20);
                liveSprite.draw(batch);
            }
            font.draw(batch, String.valueOf(score), Gdx.graphics.getWidth() / 2 + 150, Gdx.graphics.getHeight());
        }
    }

    public void SetSpeed(int speed) {
        this.speed = speed;
    }

    public void SetDirection(int direction) {
        this.direction = direction;
        Tank.setRotation(direction * 90);
    }

    public int getDirection() {
        return direction;
    }

    public void addScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void ride() {
        switch (direction) {
            case 0:
                Tank.setPosition(Tank.getX(), (Tank.getY() + speed * Gdx.graphics.getDeltaTime()));
                break;
            case 1:
                Tank.setPosition((Tank.getX() + speed * Gdx.graphics.getDeltaTime()), Tank.getY());
                break;
            case 2:
                Tank.setPosition(Tank.getX(), (Tank.getY() - speed * Gdx.graphics.getDeltaTime()));
                break;
            case 3:
                Tank.setPosition((Tank.getX() - speed * Gdx.graphics.getDeltaTime()), Tank.getY());
                break;
        }
    }

    public void showCraters(SpriteBatch batch) {
        if (!craters.isEmpty()) {
            for (Vector2 vector2 : craters) {
                CraterSprite.setPosition(vector2.x, vector2.y);
                CraterSprite.draw(batch);
            }
        }
    }

    public void show(SpriteBatch batch) {
        TextureRegion currentFrame = animTank.getKeyFrame(stateTime, true);
        tankSprite = new Sprite(currentFrame);
        tankSprite.setRotation((3 - direction) * 90);
        tankSprite.setPosition(Tank.getX() - 20, Tank.getY() + 20);
        if (speed == 0) {
            tankSprite.draw(batch);
        } else {
            stateTime += Gdx.graphics.getDeltaTime();
            tankSprite.draw(batch);
        }
    }

    public void addCrator() {
        Vector2 v2 = new Vector2(Tank.getX() - 3, Tank.getY() + 35);
        craters.add(v2);
    }

    public void explode(SpriteBatch batch) {
        speed = 0;
        showCraters(batch);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = explode.getKeyFrame(stateTime, false);
        tankSprite = new Sprite(currentFrame);
        tankSprite.setRotation((3 - direction) * 90);
        tankSprite.setPosition(Tank.getX() - 20, Tank.getY() + 20);
        tankSprite.draw(batch);
        if (explode.isAnimationFinished(stateTime)) {
            restartAnimation(explode, explodeFrames);
            respawn(startPosX, startPosY, startDirection);
            tankSprite.setRotation(startDirection * 90);
            tankSprite.draw(batch);
        }
    }

    public void restartAnimation(Animation<TextureRegion> animation, TextureRegion[] Frames) {
        resetTime();
        animation = new Animation(0.025f, Frames);
    }

    public void resetTime() {
        stateTime = 0f;
    }

    private void loadAnimations() {
        Texture forAnim = new Texture(Gdx.files.internal("Explode.png"));
        TextureRegion[][] tmp = TextureRegion.split(forAnim, 100, 100);
        explodeFrames = new TextureRegion[81];
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                explodeFrames[index++] = tmp[i][j];
            }
        }
        explode = new Animation<TextureRegion>(0.025f, explodeFrames);
        explode.setPlayMode(Animation.PlayMode.NORMAL);

        forAnim = new Texture(Gdx.files.internal("Tank1.png"));
        TextureRegion[][] temp = TextureRegion.split(forAnim, 100, 60);
        tankFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            tankFrames[i] = temp[0][i];
        }
        animTank = new Animation<TextureRegion>(0.025f, tankFrames);
        animTank.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void readMessage(String message) {
        if (isNumeric(message)) {
            int temp = Integer.parseInt(message);
            switch (temp) {
                case 0: {
                    SetSpeed(200);
                    SetDirection(2);
                    break;
                }
                case 1: {
                    SetSpeed(200);
                    SetDirection(3);
                    break;
                }
                case 2: {
                    SetSpeed(200);
                    SetDirection(0);
                    break;
                }
                case 3: {
                    SetSpeed(200);
                    SetDirection(1);
                    break;
                }
                case 4: {
                    SetSpeed(0);
                    break;
                }
            }
        } else {
            String[] str = message.split(",");
            int a = Integer.parseInt(str[4]) + 2;
            if (a > 3)
                a -= 4;
            SetDirection(a);
            if (getDirection() % 2 == 1)
                Tank.setPosition((1920 - Integer.parseInt(str[0]) + correction - 60), 1040 - Integer.parseInt(str[1]) - 100);
            else
                Tank.setPosition((1920 - Integer.parseInt(str[0]) + correction - 60), 1040 - Integer.parseInt(str[1]) - 100);
            setLives(Integer.parseInt(str[2]));
            setScore(Integer.parseInt(str[3]));
        }
    }

    public boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}