package com.cigam.sigil.AI;

public class SetMoveTargetToPlayerAction extends LeafTask {

    public SetMoveTargetToPlayerAction(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }

    @Override
    public boolean CheckConditions() {
        return blackboard.actor.health >= AIConstants.HEALTH_NEEDED_TO_ATTACK;
    }

    @Override
    public void Start() {
        blackboard.movingToTarget = true;
        blackboard.moveTarget = blackboard.player;
    }

    @Override
    public void End() {
        blackboard.movingToTarget = false;
        blackboard.moveTarget = null;
    }

    @Override
    public void DoTask() {
        if (blackboard.distanceToPlayer <= AIConstants.ATTACK_RANGE){
            taskController.finishWithSuccess();
        }
        
        if (blackboard.actor.health < AIConstants.HEALTH_NEEDED_TO_ATTACK){
            taskController.finishWithFailure();
        }
    }

}
