package com.junyor.taskmanager.exception;

public class SubtaskNotFoundException extends RuntimeException {

    public SubtaskNotFoundException() {super("Subtask not found. "); }
    public SubtaskNotFoundException(String message) {
        super(message);
    }

}
