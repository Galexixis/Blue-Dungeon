package blueDungeon.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import blueDungeon.engine.timer.PhysiqueTimer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    //Timer réel de l'engine
    private PhysiqueTimer physiqueTimer;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        physiqueTimer = new PhysiqueTimer();
    }

    @Override
    public void render() {

        //Récupère le dt de la denière frame
        float deltaTime = com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        //ENvoit une update à engine contenant le dt
        physiqueTimer.newFrameUpdate(deltaTime);

        //Rendu (généré automatique lors de la création du projet)
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
