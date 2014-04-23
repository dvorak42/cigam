package com.cigam.sigil.AI;

public class FleePlayerAction extends LeafTask {

    public FleePlayerAction(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }

    @Override
    public boolean CheckConditions() {
        return blackboard.actor.health <= AIConstants.HEALTH_NEEDED_TO_ATTACK;
    }

    @Override
    public void Start() {
        
    }

    @Override
    public void End() {
        
    }

    @Override
    public void DoTask() {
        if (blackboard.player.getPosition().dst(blackboard.actor.getPosition()) > AIConstants.FLEE_TO_RANGE){
            taskController.finishWithSuccess();
        } else {
            blackboard.actor.body.applyForceToCenter(blackboard.actor.getPosition().cpy().sub(blackboard.player.getPosition()).nor().scl(blackboard.actorSpeed), false);
        }
    }

}
