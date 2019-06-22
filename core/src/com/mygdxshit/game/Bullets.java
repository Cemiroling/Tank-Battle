package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Bullets extends Bullet {

    private Array<Bullet> bullets;
    private Sprite bulletSprite;
    private Sprite flashSprite;
    private boolean shootedState;
    private int correction;
    private float stateTime;
    private TextureRegion[] collisionFrames;
    private Animation<TextureRegion> animCollision;
    private Sprite collisionSprite;
    private Vector2 collisionPlace;
    private int collisionDirection;
    private boolean collision;

    Bullets() {
        bullets = new Array<Bullet>();
        Texture BulletImage = new Texture(Gdx.files.internal("bullet.png"));
        bulletSprite = new Sprite(BulletImage);
        shootedState = false;
        Texture FlashImage = new Texture(Gdx.files.internal("Flash.png"));
        flashSprite = new Sprite(FlashImage);
        flashSprite.setSize(35, 26);
        correction = (Gdx.graphics.getWidth() - 96 * 20) / 2;
        collision = false;
        Texture forAnim = new Texture(Gdx.files.internal("collision.png"));
        TextureRegion[][] temp = TextureRegion.split(forAnim, 32, 39);
        collisionFrames = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            collisionFrames[i] = temp[0][i];
        }
        animCollision = new Animation<TextureRegion>(0.025f, collisionFrames);
    }

    public void setShootedState(boolean state) {
        shootedState = state;
    }

    public void show(SpriteBatch batch) {
        if (!bullets.isEmpty()) {
            for (Bullet bullet : bullets) {
                bulletSprite.setRotation(bullet.getDirection() * 90);
                bulletSprite.setPosition(bullet.getX() - 7, (bullet.getY() - 12));
                bulletSprite.draw(batch);
            }
        }
    }

    public void showFlash(int x, int y, int direction, SpriteBatch batch) {
        if (shootedState) {
            flashSprite.setRotation((3 - direction) * 90);
            switch (direction) {
                case 0:
                    flashSprite.setPosition((x + 29), (y + 73));
                    break;
                case 1:
                    flashSprite.setPosition((x + 80), (y + 20));
                    break;
                case 2:
                    flashSprite.setPosition((x + 29), (y - 32));
                    break;
                case 3:
                    flashSprite.setPosition((x - 24), (y + 20));
                    break;

            }
            flashSprite.draw(batch);
        }
    }

    public boolean isCollision() {
        return collision;
    }

    public void startCollision(Bullet bullet) {
        switch (bullet.getDirection()) {
            case 0:
                collisionPlace = new Vector2(bullet.getX() - 20, bullet.getY() - 30);
                break;
            case 1:
                collisionPlace = new Vector2(bullet.getX() - 30, bullet.getY() - 10);
                break;
            case 2:
                collisionPlace = new Vector2(bullet.getX() - 10, bullet.getY());
                break;
            case 3:
                collisionPlace = new Vector2(bullet.getX(), bullet.getY() - 20);
        }
        collisionDirection = bullet.getDirection();
        collision = true;
    }

    public void check(int[][] map, Tank tank) {
        if (!(bullets.isEmpty()))
            for (Iterator<Bullet> iter = bullets.iterator(); iter.hasNext(); ) {
                Bullet bullet = iter.next();
                bullet.show();
                if (bullet.getY() > Gdx.graphics.getHeight() - 45 || bullet.getY() < 0 ||
                        bullet.getX() >= Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() - 20 * 96) + correction + 10 || bullet.getX() <= correction)
                    iter.remove();
                else {
                    if (map[(bullet.getY() / 20)][((bullet.getX() - correction) / 20)] >= 1) {
                        if (map[(bullet.getY() / 20)][((bullet.getX() - correction) / 20)] == 1)
                            map[(bullet.getY() / 20)][((bullet.getX() - correction) / 20)] = 0;
                        else
                            startCollision(bullet);
                        iter.remove();
                    }
                    if (map[((bullet.getY()) / 20)][((bullet.getX() - correction) / 20)] == -1) {
                        if (Intersector.overlapConvexPolygons(bullet.getBullet(), tank.getTank())) {
                            tank.decLives();
                            if (tank.getLives() == 0) {
                                tank.setShooted(true);
                                tank.addScore();
                                tank.addCrator();
                                tank.resetTime();
                            }
                            startCollision(bullet);
                            iter.remove();
                        }
                    }
                }
            }
    }

    public void collision(SpriteBatch batch) {
        animCollision.setPlayMode(Animation.PlayMode.LOOP);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animCollision.getKeyFrame(stateTime, false);
        collisionSprite = new Sprite(currentFrame);
        collisionSprite.setRotation((5 - collisionDirection) * 90);
        collisionSprite.setPosition(collisionPlace.x, collisionPlace.y);
        collisionSprite.draw(batch);
        if (animCollision.isAnimationFinished(stateTime)) {
            restartAnimation(animCollision, collisionFrames);
            collision = false;
        }
    }

    public void restartAnimation(Animation<TextureRegion> animation, TextureRegion[] Frames) {
        resetTime();
        animation = new Animation(0.025f, Frames);
    }

    public void resetTime() {
        stateTime = 0f;
    }

    public void Shoot(Tank tank) {
        shootedState = true;
        Bullet bullet = new Bullet(tank.getTank().getX() - 20, tank.getTank().getY() + 20, tank.getDirection());
        bullets.add(bullet);
    }
}