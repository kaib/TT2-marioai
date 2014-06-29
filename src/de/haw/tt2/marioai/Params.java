package de.haw.tt2.marioai;

/**
 * Created by dude on 02.06.2014.
 */
public class Params {

    //MARIO AI Specific Variables

    public static int TRAINING_ITERATIONS = 1000;
    public static int MARIO_MODE = 2;

    public static int SEED = 0;
    public static int EVAL_SEED = 0;

    public static int EVAL_ITERATIONS = 10;

    public static final float EVAL_EXPLORATION_RATE = 0.0001f;

    //Q-Learning Parameters
    //Presents the Number of possible Actions per QState -> Number of Colls in QTable
    public static final int NUMBER_OF_ACTIONS = 12;
    public static final float EXPLORATION_RATE = 0.3f;

    // Gamma: [0,1] Je näher an der 1, desto eher wird die Langzeitbelohnung optimiert
    //              Gamma = 0 => Optimierung der sofortigen Belohnung
    public static final float GAMMA = 0.1f;

    //Gibt an inwieweit zukünftige Belohnungen das bisher gelernte beeinflussen
    //LEARNING_RATE = 1 => Alles bisher gelernte wird komplett überschrieben
    //Laut Literatur sinnvoll dies über die Zeit hinweg zu verringern.
    public static float LEARNING_RATE = 0.1f;

    public static final int MIN_STUCK_FRAMES = 25;
    public static final float DIRECTION_THRESHOLD = 0.8f;
    public static final float DISTANCE_THRESHOLD = 2f;





    public static final class REWARDS {
        public static final int DISTANCE = 5;
        public static final int HEIGHT = 2;
        public static final int COLLISION = -10;
        public static final int KILLED_BY_FIRE = 10;
        public static final int KILLED_BY_STOMP = 10;
        public static final int TIME= -1;
        public static final int STUCK = -10;
        public static final int WIN = 1000;

        public static final int MODE = 0;
        public static final int COINS = 0;
        public static final int FLOWER_FIRE = 0;
        public static final int KILLS = 0;
        public static final int KILLED_BY_SHELL = 0;
        public static final int MUSHROOM = 0;
        public static final int HIDDEN_BLOCK = 0;
        public static final int GREEN_MUSHROOM = 0;
        public static final int STOMP = 0;

    }


    //Logging Params
        public static boolean LOAD_Q_TABLE_AT_START = false;
        public static String Q_TABLE_FILE_NAME = "qTable.xml";
        public static String SCORES_FILE_NAME = "scores.xml";
}
