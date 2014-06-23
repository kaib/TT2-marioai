package de.haw.tt2.marioai;

import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.tasks.BasicTask;
import ch.idsia.benchmark.tasks.LearningTask;
import ch.idsia.tools.EvaluationInfo;
import ch.idsia.tools.MarioAIOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dude on 23.06.2014.
 */
public class Evaluation {

    public static class EvaluationData {
        float averageScore = 0;
        float wins = 0;
        float averageKills = 0;
        float averageDistance = 0;
        float averageTimeSpent = 0;



        public String toString() {
            return String.format("%f %f %f %f %f",
                    averageScore, wins, averageKills, averageDistance, averageTimeSpent);
        }

        public void computeFinalEvalInfo() {
            averageScore /= Params.EVAL_ITERATIONS;
            wins /= Params.EVAL_ITERATIONS;
            averageKills /= Params.EVAL_ITERATIONS;
            averageDistance /= Params.EVAL_ITERATIONS;
            averageTimeSpent /= Params.EVAL_ITERATIONS;
        }

        public void accumulateEvalInfo(EvaluationInfo evaluationInfo) {
            averageScore += evaluationInfo.computeWeightedFitness();
            wins += evaluationInfo.marioStatus == Mario.STATUS_WIN ? 1 : 0;
            averageKills += 1.0 *
                    evaluationInfo.killsTotal / evaluationInfo.totalNumberOfCreatures;
            averageDistance += 1.0 *
                    evaluationInfo.distancePassedCells / evaluationInfo.levelLength;
            averageTimeSpent += evaluationInfo.timeSpent;
        }
    }
    Logger logger = Logger.getLogger("MarioEval");
    public static enum Mode{DEBUG,DEMO,NORMAL};
    private Mode mode = Mode.NORMAL;
    private MarioAIOptions marioAIOptions;
    private QBot qbot;
    private List<EvaluationData> evaluationDataList = new ArrayList<EvaluationData>();

    public Evaluation(Mode mode){
        this.mode = mode;
        qbot = new QBot("Mario");
        marioAIOptions = new MarioAIOptions();
        marioAIOptions.setAgent(qbot);
        marioAIOptions.setVisualization(false);
        marioAIOptions.setFPS(200);

        marioAIOptions.setLevelDifficulty(0);

       // marioAIOptions.setDeadEndsCount(true);
       // marioAIOptions.setTubesCount(true);
       // marioAIOptions.setBlocksCount(true);
        //marioAIOptions.setGapsCount(false);
       // marioAIOptions.setCannonsCount(false);
        //marioAIOptions.setGreenMushroomMode(0);

        qbot.setMarioAIOptions(marioAIOptions);
        qbot.setLearningTask(new LearningTask(marioAIOptions));
    }

    public float evaluate() {
        if (mode == Mode.DEBUG) {
            marioAIOptions.setVisualization(true);
        }
        logger.info("StartLearningTask");
        qbot.learn();



        if (mode == Mode.DEMO) {
            marioAIOptions.setVisualization(true);
        }
        BasicTask basicTask = new BasicTask(marioAIOptions);

        logger.info("Begin Eval");
        logger.info("Begin Eval");
        logger.info("Begin Eval");
        logger.info("Begin Eval");
        logger.info("Begin Eval");

        logger.info("Task: " + basicTask);
        logger.info("Agent: " + qbot);

        EvaluationData results = new EvaluationData();
        evaluationDataList.add(results);

        for(int i = 0; i < Params.EVAL_ITERATIONS; i++){
            marioAIOptions.setLevelRandSeed(i);

            int failedCount = 0;
            while(!basicTask.runSingleEpisode(1)){
                System.err.println("OUT OF COMPUTATIONAL TIME!!!!");
                failedCount++;

            }

        }
        EvaluationInfo evaluationInfo = basicTask.getEvaluationInfo();
        results.computeFinalEvalInfo();
        return results.averageScore;



    }

    public static void main(String[] args) {
        Evaluation eval = new Evaluation(Mode.DEMO);
        eval.evaluate();
    }


}
