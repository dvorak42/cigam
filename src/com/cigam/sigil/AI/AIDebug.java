package com.cigam.sigil.AI;

//static class
public class AIDebug {
    
    static String dumpFilepath;
    static String logDump;
    
    static void log(String entry){
        logDump += "\n"+entry;
    }
    
    static void outputDump(){
        System.out.print(logDump);
    }

}
