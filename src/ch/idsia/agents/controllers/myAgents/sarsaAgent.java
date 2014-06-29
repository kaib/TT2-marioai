package ch.idsia.agents.controllers.myAgents;

/**
 * Created by dude on 31.05.2014.
 */

import ch.idsia.agents.Agent;
import ch.idsia.agents.LearningAgent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.tasks.LearningTask;

public class SarsaAgent extends BasicMarioAIAgent implements LearningAgent,Comparable {
    private float fitness = 0;


    public SarsaAgent(String s) {
        super(s);
    }

    @Override
    public void learn() {

    }

    @Override
    public void giveReward(float reward) {
      this.fitness = reward;
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
        this.setFitness(0);
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof SarsaAgent) {
            return Float.compare(this.getFitness(),((SarsaAgent) o).getFitness());
        }   else{
            return 0;
        }
    }
}
