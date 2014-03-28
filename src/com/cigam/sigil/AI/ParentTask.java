package com.cigam.sigil.AI;

public abstract class ParentTask extends Task {
    
    protected ParentTaskController taskController;
    
    public ParentTask(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
        CreateAndAttachController();
    }

    //creates a new parent task controller and links it to this task instance
    private void CreateAndAttachController(){
        taskController = new ParentTaskController(this);
    }
    
    //methods called when a child task fails or succeeds
    protected abstract void ChildSucceeded();
    protected abstract void ChildFailed();
    
    
    //===== OVERRIDES =====
    
    //returns false if this task has no child tasks, otherwise true
    @Override
    public boolean CheckConditions(){
        return taskController.subtaskList.size() > 0;
    }
    
    //runs the current subtask to be run. Starts, finishes, or updates the current subtask as necessary
    @Override
    public void DoTask(){
        //sanity checks
        if (taskController.getIsCompleted()){
            return;
        }
        if (taskController.currentTask == null){
            return;
        }
        
        //otherwise we have a current task
        
        //start it if it isn't started yet
        if (!taskController.currentTask.GetTaskController().getIsStarted()){
            taskController.currentTask.GetTaskController().safeStart();
        }
        //finish it if it has ended
        else if (taskController.currentTask.GetTaskController().getIsCompleted()){
            taskController.currentTask.GetTaskController().safeEnd();
            if (taskController.currentTask.GetTaskController().getIsSuccessful()){
                ChildSucceeded();
            } else if (taskController.currentTask.GetTaskController().getIsFailed()){
                ChildFailed();
            }
        }
        //otherwise we are ready to update
        else {
            taskController.currentTask.DoTask();
        }
    }
    
    //selects the first subtask in the subtask list, if available. Throws an exception if the subtask list is empty
    @Override
    public void Start(){
        if (taskController.subtaskList.size() <= 0){
            //throw new Exception("attempted to start a task when none were available");
            System.out.println("attempted to start a task when none were available");
        }
        taskController.currentTask = taskController.subtaskList.get(0);
    }
    
    //ends this task branch
    @Override
    public void End(){
        //nothing for now
    }
    
    //===== ACCESSOR METHODS =====
    
    //returns the task controller linked to this task instance
    @Override
    public TaskController GetTaskController(){
        return taskController;
    }
}
