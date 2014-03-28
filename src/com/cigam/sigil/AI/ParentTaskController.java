package com.cigam.sigil.AI;

import java.util.ArrayList;

public class ParentTaskController extends TaskController {
    
    public ArrayList<Task> subtaskList;
    public Task currentTask;
    
    public ParentTaskController(Task task) {
        super(task);
        subtaskList = new ArrayList<Task>();
        currentTask = null;
    }
    
    
    public void addTask(Task taskToAdd){
        subtaskList.add(taskToAdd);
    }
    
    //===== OVERRIDES =====
    
    //resets the task controller, and moves the current task pointer back to the front of the subtask list
    @Override
    public void reset(){
        super.reset();
        currentTask = subtaskList.get(0);
    }

}
