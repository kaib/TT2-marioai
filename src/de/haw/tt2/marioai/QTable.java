package de.haw.tt2.marioai;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by dude on 02.06.2014.
 * Auf Grundlage von http://www.itu.dk/courses/MAIG/E2011/Exercises/QLearning.java erstellt
 */
public class QTable {
    Random random;
    //TODO STATE CODING
    private HashMap<State, float[]> qTable;

    int actionCount;

    float explorationRate= Params.EXPLORATION_RATE;
    float gamma = Params.GAMMA;
    float learningRate = Params.LEARNING_RATE;

    //QLearning updates QValue one step back, we need the prevState and prevAction
    State prevState;
    //Number of enum Action
    int prevAction;

    public QTable(int actionCount) {
        this.actionCount = actionCount;
        this.random = new Random();
        qTable = new HashMap<State, float[]>();
    }
}
