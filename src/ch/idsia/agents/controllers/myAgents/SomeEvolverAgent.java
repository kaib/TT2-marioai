package ch.idsia.agents.controllers.myAgents;

import ch.idsia.agents.Agent;
import ch.idsia.agents.LearningAgent;
import ch.idsia.agents.controllers.BasicMarioAIAgent;
import ch.idsia.benchmark.tasks.LearningTask;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dude on 31.05.2014.
 */
public class SomeEvolverAgent extends BasicMarioAIAgent implements LearningAgent {
    private ArrayList<SarsaAgent> population = new ArrayList<SarsaAgent>();
    private SarsaAgent currentAgent;
    private int popSize = 100;
    enum State{INIT,EXPLORE,SELECT}
    private State state = State.INIT;
    private int popIndex = 0;
    private float fitness = 0;


    public SomeEvolverAgent(String s) {
        super(s);
    }

    @Override
    public void learn() {
        currentAgent = Collections.max(population);
    }

    @Override
    public void giveReward(float reward) {
        currentAgent.setFitness(reward);
        switch(state) {
            case INIT:
               if(popIndex == popSize){
                   state = state.EXPLORE;
               }
               break;
            case EXPLORE:
                //STUFF
                state = state.SELECT;
                break;
            case SELECT:
                population.add(currentAgent);
                state = state.EXPLORE;
        }

    }

    @Override
    public void newEpisode() {
        switch(state) {
            case INIT:
                currentAgent = population.get(popIndex);
                break;
            case EXPLORE:
                SarsaAgent best = Collections.max(population);
                SarsaAgent newAgent = new SarsaAgent("explored");
                currentAgent = newAgent;
                break;
            case SELECT:
                SarsaAgent worst = Collections.min(population);
                population.remove(worst);
                SarsaAgent newAgent1 = new SarsaAgent("selected");
                currentAgent = newAgent1;
                break;
        }

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
        for(int i = 0; i< popSize; i++)  {
            SarsaAgent agent = new SarsaAgent("Agent: " + i) ;
            agent.setFitness(0);
            population.add(agent);
        }

    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }


}
