package com.cigam.sigil.AI;

public class Sequencer extends ParentTask{
    
    public Sequencer(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }
    
    
    //===== OVERRIDES =====
    
    //On a sequencer child success, we move to the next child. If there are no children left, the task returns with success
    @Override
    protected void ChildSucceeded() {
        int currentTaskIndex = taskController.subtaskList.indexOf(taskController.currentTask);
        //if we reach the end of the subtask list, we have succeeded with the sequencer, so the task returns with success
        if (currentTaskIndex >= taskController.subtaskList.size() - 1){
            taskController.finishWithSuccess();
        }
        
        //otherwise we go to the next task
        currentTaskIndex++;
        //if there is an out of bounds error, it could be due to this area. Somehow the subtask list is getting cleared at some point?
        Task newTask = taskController.subtaskList.get(currentTaskIndex);
        
        //fail early if the new task would fail on conditions
        if (!newTask.CheckConditions()){
            taskController.finishWithFailure();
        }
        //otherwise we are good to go
    }
    
    //On a sequencer child failure, the task returns with failure
    @Override
    protected void ChildFailed(){
        taskController.finishWithFailure();
    }
    
}
