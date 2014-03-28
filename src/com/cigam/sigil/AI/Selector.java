package com.cigam.sigil.AI;

public class Selector extends ParentTask {

    public Selector(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
    }
    
    //Helper method which selects the next viable task in the subtaskList, if there is any. Otherwise returns null
    public Task ChooseNewTask(){
        Task newTask = null;
        int currentTaskIndex = taskController.subtaskList.indexOf(taskController.currentTask);
        while (newTask == null){
            //break out if we have reached the end of the list with no valid tasks
            if (currentTaskIndex == taskController.subtaskList.size() - 1){
                break;
            }
            //otherwise continue iteration
            
            currentTaskIndex ++;
            //if the task being looked at is usable, it is assigned as the next task
            if (taskController.subtaskList.get(currentTaskIndex).CheckConditions()){
                newTask = taskController.subtaskList.get(currentTaskIndex);
            }
        }
        return newTask;
    }
    
    
    //====== OVERRIDES =====
    
    //On a selector child success, the task returns with success
    @Override
    protected void ChildSucceeded(){
        taskController.finishWithSuccess();
    }
    
    //On a selector child failure, the next subtask is tried. If no subtasks remain, the task returns with failure
    @Override
    protected void ChildFailed(){
        taskController.currentTask = ChooseNewTask();
        
        if (taskController.currentTask == null){
            taskController.finishWithFailure();
        }
    }
    
}
