package de.haw.tt2.marioai;

/**
 * Created by dude on 02.06.2014.
 */
public class Params {

    //MARIO AI Specific Variables

    public static int ITERATIONS = 1000;

    public static int SEED = 0;
    public static int EVAL_SEED = 0;

    public static int EVAL_ITERATIONS = 10;

    public static final float EVAL_EXPLORATION_RATE = 0.01f;

    //Q-Learning Parameters
    //Presents the Number of possible Actions per QState -> Number of Colls in QTable
    public static final int NUMBER_OF_ACTIONS = 12;
    public static final float EXPLORATION_RATE = 0.2f;

    // Gamma: [0,1] Je näher an der 1, desto eher wird die Langzeitbelohnung optimiert
    //              Gamma = 0 => Optimierung der sofortigen Belohnung
    public static final float GAMMA = 0.8f;

    //Gibt an inwieweit zukünftige Belohnungen das bisher gelernte beeinflussen
    //LEARNING_RATE = 1 => Alles bisher gelernte wird komplett überschrieben
    //Laut Literatur sinnvoll dies über die Zeit hinweg zu verringern.
    public static float LEARNING_RATE = 0.8f;

    //TODO Stuck state?


    public static final int OBSERVATION_LEVELS = 2;
    public static final int[] OBSERVATION_SIZES = {1,3};

    public static final float[] ENEMIES_DISTANCE_REWARD_SCALER = {0f,0f,0.15f};
    //TODO Rewards anpassen

    //Struct like Class for Reward Params:

    public static final class REWARDS {
        public static final int distance = 50;
        public static final int height = 50;
        public static final int collision = -800;
        public static final int killedByFire = 10;
        public static final int killedByStomp = 10;

        public static final int stuck = 0;
        public static final int win = 0;
        public static final int mode = 0;
        public static final int coins = 0;
        public static final int flowerFire = 0;
        public static final int kills = 0;
        public static final int killedByShell = 0;
        public static final int mushroom = 0;
        public static final int timeLeft = 0;
        public static final int hiddenBlock = 0;
        public static final int greenMushroom = 0;
        public static final int stomp = 0;

    }


    //Logging Params
        public static boolean LOAD_Q_TABLE_AT_START = false;
        public static String Q_TABLE_FILE_NAME = "qTable.xml";
        public static String SCORES_FILE_NAME = "scores.xml";
}
