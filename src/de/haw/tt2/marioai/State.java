package de.haw.tt2.marioai;
import ch.idsia.benchmark.mario.engine.GeneralizerLevelScene;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.tools.ReplayerOptions;

/**
 * Created by dude on 02.06.2014.
 */
public class State {

    private static final int MARIO_X = 9;
    private static final int MARIO_Y = 9;

    private int marioMode = 2;
    private int direction = 8;
    private float marioX = 0f;
    private float marioY = 0f;

    private int onGround = 1;
    private int canJump = 1;

    private int hitByEnemy = 0;


    private boolean[] directEnemies = new boolean[12];
    private int[] enemysPerObservationLevel = new int[Params.OBSERVATION_LEVELS];

    private int totalEnemyCount = 0;
    private int lastTotalEnemyCount = 0;

    //Killed Enemies in this Frame
    private int enemiesKilledByStomp = 0;
    private int enemiesKilledByFire = 0;
    private int laseEnemiesKilledByFire = 0;
    private int lastEnemiesKilledByStomp = 0;

    private boolean[] obstacles = new boolean[4];

    //TODO CHECK GAPS

    private long computedState;
    private Environment environment;
    private byte[][] scene;

    private int distance = 0;
    private int height = 0;
    private int lastDistance = 0;
    private int lastHeight = 0;

    public State(){ }

    public void update(Environment environment) {
        this.environment = environment;
        this.scene = environment.getMergedObservationZZ(1,1);

        //Update Distance Delta
        distance = environment.getEvaluationInfo().distancePassedPhys - distance;

        //todo Height


    }


    //DONT FORGET hasHCODE UND EQuals
}
