package com.mygdxshit.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

    private final TankBattle game;
    private TextButton Create;
    private Skin skin;
    private TextureAtlas atlas;
    private Stage stage;

    public MainMenuScreen(final TankBattle game) {
        this.game = game;

        atlas = new TextureAtlas("button.pack");
        skin = new Skin(atlas);

        BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonOn");
        textButtonStyle.over = skin.getDrawable("buttonOff");
        textButtonStyle.down = skin.getDrawable("buttonOff");
        textButtonStyle.font = font;

        stage = new Stage(new ScreenViewport());
        Create = new TextButton("Create", textButtonStyle);
        Create.getLabel().setFontScale(0.8f, 0.6f);
        Create.setWidth(500);
        Create.setHeight(250);
        Create.setPosition(Gdx.graphics.getWidth() / 2 - 250, Gdx.graphics.getHeight() / 2 - 150);
        Create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        stage.addActor(Create);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int x, int y) {
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
    }
}
