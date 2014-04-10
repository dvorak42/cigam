package com.cigam.sigil.AI;

public class BasicEnemyBT {
    
    public Task rootNode;
    public AIBlackboard bb;
    
    public void createBehaviourTree(AIBlackboard blackboardToAttach){
        bb = blackboardToAttach;
        
        //root node
        this.rootNode = new Selector(bb);
        
        //idle behavior
        Task idleBehavior = new Sequencer(bb);
        ((ParentTaskController)idleBehavior.GetTaskController()).addTask(new IdleAboutAction(bb));
        
        //attack behavior
        Task attackBehavior = new Sequencer(bb);
        ((ParentTaskController)attackBehavior.GetTaskController()).addTask(new SetMoveTargetToPlayerAction(bb));
        ((ParentTaskController)attackBehavior.GetTaskController()).addTask(new AttackPlayerAction(bb));
        
        //flee behavior
        Task fleeBehavior = new Sequencer(bb);
        ((ParentTaskController)fleeBehavior.GetTaskController()).addTask(new FleePlayerAction(bb));
        
        //connections
        ((ParentTaskController)rootNode.GetTaskController()).addTask(idleBehavior);
        ((ParentTaskController)rootNode.GetTaskController()).addTask(attackBehavior);
        ((ParentTaskController)rootNode.GetTaskController()).addTask(fleeBehavior);
        rootNode.GetTaskController().reset();
    }
    
    public void updateBehaviorTree(){
        rootNode.DoTask();
    }
    
}
