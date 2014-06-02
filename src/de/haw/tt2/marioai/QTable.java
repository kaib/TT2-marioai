package de.haw.tt2.marioai;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by dude on 02.06.2014.
 * Auf Grundlage von http://www.itu.dk/courses/MAIG/E2011/Exercises/QLearning.java erstellt
 */
public class QTable {
    Random rGenerator;
    //TODO STATE CODING
    private HashMap<char[], float[]> qTable;

    int actionCount = Params.NUMBER_OF_ACTIONS;

    float explorationRate= Params.EXPLORATION_RATE;
    float gamma = Params.GAMMA;
    float learningRate = Params.LEARNING_RATE;

    //QLearning updates QValue one step back, we need the prevState and prevAction
    State prevState;

}
