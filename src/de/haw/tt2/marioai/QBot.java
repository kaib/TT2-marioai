package de.haw.tt2.marioai;

import ch.idsia.agents.Agent;
import ch.idsia.agents.LearningAgent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.tasks.LearningTask;

/**
 * Created by dude on 02.06.2014.
 */
public class QBot extends BasicMarioAIAgent implements LearningAgent {
    public QBot(String s) {
        super(s);
    }

    @Override
    public void learn() {

    }

    @Override
    public void giveReward(float reward) {

    }

    @Override
    public void newEpisode() {

    }

    @Override
    public void setLearningTask(LearningTask learningTask) {

    }

    @Override
    public void setEvaluationQuota(long num) {

    }

    @Override
    public Agent getBestAgent() {
        return null;
    }

    @Override
    public void init() {

    }
}
