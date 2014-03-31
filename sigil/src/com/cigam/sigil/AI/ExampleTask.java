package com.cigam.sigil.AI;

//an example leaf task
public class ExampleTask extends LeafTask {
    
    public ExampleTask(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }
    
    
    //check conditions for the task, usually based on blackboard information
    @Override
    public boolean CheckConditions() {
        return blackboard.exampleCondition;
    }
    
    //begin the task, depends heavily on the nature of the task itself
    @Override
    public void Start() {
        AIDebug.log(this.toString()+": task started...");
        blackboard.exampleParameter += 2;
        //do things...
    }
    
    //complete the task, depends heavily on the nature of the task itself
    @Override
    public void End() {
        AIDebug.log(this.toString()+": task ended...");
        blackboard.exampleParameter -= 2;
        //do things...
    }
    
    //logic to be performed on an update schedule, depends heavily on the nature of the task itself. This should contain logic conditions to finish the
    //  task, either in success or failure
    @Override
    public void DoTask() {
        AIDebug.log(this.toString()+": doing task...");
        blackboard.examplePointer.setVisible(blackboard.exampleCondition);
        //do things...
        
        if (blackboard.exampleCondition){
            taskController.finishWithSuccess();
        } else {
            taskController.finishWithFailure();
        }
    }
    
    

}
