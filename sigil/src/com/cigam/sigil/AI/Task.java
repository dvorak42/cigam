package com.cigam.sigil.AI;

public abstract class Task {
    
    protected AIBlackboard blackboard;
    
    /*
     * Creates a new task instance
     * params ==
     * attachedBlackboard: AI blackboard this task instance should interact with
     */
    public Task (AIBlackboard attachedBlackboard){
        blackboard = attachedBlackboard;
    }
    
    /*
     * Returns whether the task can be updated or not 
     */
    public abstract boolean CheckConditions();
    
    /*
     * Runs startup logic
     */
    public abstract void Start();
    
    /*
     * Runs ending logic
     */
    public abstract void End();
    
    /*
     * Task logic run each cycle
     */
    public abstract void DoTask();
    
    
    //===== ACCESSOR METHODS =====
    /*
     * Returns the task controller overseeing this task instance
     */
    public abstract TaskController GetTaskController();
}
