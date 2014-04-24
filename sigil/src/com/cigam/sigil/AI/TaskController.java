package com.cigam.sigil.AI;

public class TaskController {
    
    private boolean isStarted;
    private boolean isCompleted;
    private boolean isSuccessful;
    private Task task;
    
    public TaskController(Task task){
        this.task = task;
        isStarted = false;
        isCompleted = false;
        isSuccessful = true;
    }
    
    //starts the task through this task controller wrapper
    public void safeStart(){
        isStarted = true;
        task.Start();
    }
    
    //ends the task safely through this wrapper, and resets completed to false and started to false, since it isn't running anymore
    public void safeEnd(){
        isCompleted = false;
        isStarted = false;
        task.End();
    }
    
    //changes success to true and sets the completed flag for the linked task to see
    public void finishWithSuccess(){
        isSuccessful = true;
        isCompleted = true;     //this will cause ParentTask to call safeEnd later
    }
    
    //changes success to false and sets the completed flag for the linked task to see
    public void finishWithFailure(){
        isSuccessful = false;
        isCompleted = true;     //this will cause ParentTask to call safeEnd later
    }
    
    //NOTE: the reason we don't call safeEnd() directly from finishWithSuccess and finishWithFailure is that we still need the lined ParentTask to call
    //  ChildSucceeded() or ChildFailed(). We could call it from here using task.ChildSucceeded() or otherwise, but I like the task dealing with the tasks
    //  methods and being otherwise removed from the controller, which only updates flags on whether or not the task should be run.
    
    //resets the running state so isCompleted is false again. Note that this does not set the start state to true, which much be run through safeStart()
    //  to ensure that task.Start() is run correctly
    public void reset(){
        isCompleted = false;
        safeStart();
    }
    
    //===== ACCESSOR METHODS =====
    public boolean getIsStarted(){ return isStarted;}
    public boolean getIsCompleted(){ return isCompleted;}
    public boolean getIsSuccessful(){ return isSuccessful;}
    public boolean getIsFailed(){ return !isSuccessful;}
}
