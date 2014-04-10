package com.cigam.sigil.AI;

public class IdleAboutAction extends LeafTask {

    public IdleAboutAction(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }

    @Override
    public boolean CheckConditions() {
        return blackboard.distanceToPlayer > AIConstants.ENGAGE_RANGE;
    }

    @Override
    public void Start() {
        blackboard.movingToPoint = false;
        blackboard.movingToTarget = false;
    }

    @Override
    public void End() {
        
    }

    @Override
    public void DoTask() {
        blackboard.agentHealth += AIConstants.HEALTH_REGEN_PER_FRAME;
        blackboard.agentHealth = Math.min(blackboard.agentHealth, AIConstants.MAX_HEALTH);
        if (blackboard.distanceToPlayer <= AIConstants.ENGAGE_RANGE){
            taskController.finishWithFailure();
        }
    }

}
