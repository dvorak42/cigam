package com.cigam.sigil.AI;

public class AttackPlayerAction extends LeafTask {

    public AttackPlayerAction(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }

    @Override
    public boolean CheckConditions() {
        return blackboard.distanceToPlayer <= AIConstants.ATTACK_RANGE + .25f;
    }

    @Override
    public void Start() {
        blackboard.attacking = true;
        blackboard.attackTarget = blackboard.player;
    }

    @Override
    public void End() {
        blackboard.attacking = false;
        blackboard.attackTarget = null;
    }

    @Override
    public void DoTask() {
        if (blackboard.distanceToPlayer > AIConstants.ATTACK_RANGE + .5f){
            taskController.finishWithFailure();
        }
        
        if (blackboard.player.health <= 0){
            taskController.finishWithSuccess();
        }
    }

}
