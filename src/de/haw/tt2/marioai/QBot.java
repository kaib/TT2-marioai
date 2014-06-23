package de.haw.tt2.marioai;

import ch.idsia.agents.Agent;
import ch.idsia.agents.LearningAgent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.mario.environments.Environment;
import ch.idsia.benchmark.tasks.LearningTask;
import ch.idsia.tools.EvaluationInfo;
import ch.idsia.tools.MarioAIOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dude on 02.06.2014.
 */
public class QBot implements LearningAgent {
   private String name;
    Logger logger = Logger.getLogger("MarioBot");;
    //Options,Task
    private MarioAIOptions marioAIOptions;
    private LearningTask learningTask;
    //State
    private State currentState;

    //QTable
    private QTable qTable;

    //Agent is a State maschine, so we need to keep track of the States INIT LEARN EVAL
    private enum Phase {
        INIT, LEARN, EVAL
    };

    private Phase currentPhase = Phase.INIT;

    private int learningTrial = 0;

    private List<Integer> scores = new ArrayList<Integer>(Params.TRAINING_ITERATIONS);

    public QBot(String s) {

        this.name = s;
        currentState = new State();
        qTable = new QTable(Params.NUMBER_OF_ACTIONS);

        logger.info("MARIO BOT CREATED");
    }
    @Override
    public boolean[] getAction(){
        return Action.getAction(qTable.getNextAction(currentState));
    }

    @Override
    public void integrateObservation(Environment environment) {
        //Observagtion after the Action => Info on prevState x prevAction -> currentAction, reward for prevAction
        currentState.update(environment);
        if(currentPhase == Phase.INIT && environment.isMarioOnGround()) {
           // logger.info("Marion on ground, start learning now");
            currentPhase = Phase.LEARN;
        } else if(currentPhase == Phase.LEARN) {

            //Update QValues
            qTable.updateQTable(currentState.getReward(),currentState);
        }
    }

    @Override
    public void learn() {
        marioAIOptions.setLevelRandSeed(Params.SEED);
        marioAIOptions.setMarioMode(Params.MARIO_MODE);
        for(int i = 0; i < Params.TRAINING_ITERATIONS; i++) {
            learnOnce();
        }
        eval();
    }

    private void learnOnce(){

        init();
        learningTask.runSingleEpisode(1);

        EvaluationInfo evaluationInfo = learningTask.getEvaluationInfo();

        int score = evaluationInfo.computeWeightedFitness();

       // logger.info("SCORE: " + score);
        logger.info("EndLearningTrial: " + learningTrial + " Score: " + score);
        learningTrial++;
    }

    private void eval(){
     //   logger.info("ENTER EVAL PHASE");
        currentPhase = Phase.EVAL;

        //EVAL Exploration Chance
        qTable.setExplorationRate(Params.EVAL_EXPLORATION_RATE);

    }


    @Override
    public void reset() {
      //  logger.info("RESET");
        currentState = new State();
    }
    @Override
    public void init() {
      //  logger.info("INIT");
        currentPhase = Phase.INIT;
    }

    @Override
    public void setObservationDetails(int rfWidth, int rfHeight, int egoRow, int egoCol) {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
           this.name = name;
    }

    @Override
    public void newEpisode() {
    }

    @Override
    public void setLearningTask(LearningTask learningTask) {
        this.learningTask = learningTask;
    }

    @Override
    public void setEvaluationQuota(long num) {

    }

    @Override
    public Agent getBestAgent() {
        return this;
    }


    @Override
    public void giveReward(float reward) {
    }
    @Override
    public void giveIntermediateReward(float intermediateReward) {
    }

    public void setMarioAIOptions(MarioAIOptions marioAIOptions) {
        this.marioAIOptions = marioAIOptions;
    }

    @Override
    public String toString() {
        return "QBot{" +
                "name='" + name + '\'' +
                ", marioAIOptions=" + marioAIOptions +
                ", learningTask=" + learningTask +
                '}';
    }
}
