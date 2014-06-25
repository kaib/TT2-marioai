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

    private int actionCount;

    private float explorationRate= Params.EXPLORATION_RATE;
    private float gamma = Params.GAMMA;
    private float learningRate = Params.LEARNING_RATE;

    //QLearning updates QValue one step back, we need the prevState and prevAction
    State prevState;
    //Number of enum Action
    int prevAction;

    public QTable(int actionCount) {
        this.actionCount = actionCount;
        this.random = new Random();
        qTable = new HashMap<State, float[]>();
    }

    public int getNextAction(State state){
        prevState = state;
        if(random.nextFloat() < explorationRate) {
            prevAction = explore();
        } else {
            prevAction = getBestAction(state);
        }
        return prevAction;
    }

    public int getBestAction(State state){
        float[] qValues = getQValues(state);
        if(qValues == null) {
            System.err.println("No Q Values Defined for this state");
        }

        float maxQ = Float.NEGATIVE_INFINITY;
        int qIndex = 0;
        for(int i = 0; i < qValues.length; i++) {
            if (qValues[i] > maxQ) {
                maxQ = qValues[i];
                qIndex = i;
            }
        }
        return qIndex;
    }

    public int explore(){
        return random.nextInt(actionCount);
    }

    public void updateQTable(float reward, State currentState){
        //QUpdate Rule:
        //Q(prevState,prevAction) = (1 - alpha) * QPrev + (alpha) * (reward + gamma * maxQ)

        //alpha = decreasing: alpha = learningRate / count(prevState,prevAction)
        float[] prevQValues = getQValues(prevState);
        float prevQValue = prevQValues[prevAction];

        int currentAction = getBestAction(currentState);
        float maxQValue = getQValues(currentState)[currentAction];

        //TODO IMPLEMENT DECREASING ALPHA
        float alpha = learningRate;

        float newQValue = (1 - alpha) * prevQValue + (alpha) * (reward + gamma * maxQValue);

        prevQValues[prevAction] = newQValue;
    }

    //QValues for State
    //if State not existed bevore all QValues = 0, TODO TEST RANDOM Q VAlUE APPROACH
    public float[] getQValues(State state){
        if(!qTable.containsKey(state)) {
            float[] initQValues = getInitQValues();
            qTable.put(state,initQValues);
        }
        return qTable.get(state);
    }

    //Currently Default QValues are 0
    private float[] getInitQValues() {
        float maxF = 0.1f;
        float minF = -0.1f;

        float[] result = new float[actionCount];
        for(int i = 0; i < result.length; i++){
            result[i] = random.nextFloat() * (maxF-minF) +minF;
        }
        return result;
    }

    public HashMap<State, float[]> getqTable() {
        return qTable;
    }

    public void setExplorationRate(float explorationRate) {
        this.explorationRate = explorationRate;
    }

    @Override
    public String toString() {
        return "QTable{" +
                "qTable=" + qTable +
                ", actionCount=" + actionCount +
                ", explorationRate=" + explorationRate +
                ", gamma=" + gamma +
                ", learningRate=" + learningRate +
                '}';
    }
}
