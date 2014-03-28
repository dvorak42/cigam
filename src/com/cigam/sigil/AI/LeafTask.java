package com.cigam.sigil.AI;

public abstract class LeafTask extends Task {
    
    protected TaskController taskController;
    
    public LeafTask(AIBlackboard attachedBlackboard) {
        super(attachedBlackboard);
        CreateAndAttachController();
    }
    
    //creates a new task controller and links it to this task instance
    private void CreateAndAttachController(){
        taskController = new TaskController(this);
    }
    
    //===== OVERRIDES =====
    //===== ACCESSOR METHODS =====
    
    //returns the task controller linked to this task instance
    @Override
    public TaskController GetTaskController(){
        return taskController;
    }
    
}
