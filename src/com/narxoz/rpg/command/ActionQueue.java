package com.narxoz.rpg.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionQueue {
    private final List<ActionCommand> queue = new ArrayList<>();

    public void enqueue(ActionCommand cmd) {
        queue.add(cmd);
        System.out.println("  📝 Enqueued: " + cmd.getDescription());
    }

    public void undoLast() {
        if (!queue.isEmpty()) {
            ActionCommand removed = queue.remove(queue.size() - 1);
            System.out.println("  ↩️ Undo last: " + removed.getDescription());
        } else {
            System.out.println("  ⚠️ Queue is empty, nothing to undo!");
        }
    }

    public void executeAll() {
        if (queue.isEmpty()) {
            System.out.println("  ⚠️ Queue is empty, nothing to execute!");
            return;
        }
        
        System.out.println("  ▶️ Executing " + queue.size() + " commands:");
        for (ActionCommand cmd : queue) {
            cmd.execute();
        }
        queue.clear();
        System.out.println("  ✅ Queue cleared after execution");
    }

    public List<String> getCommandDescriptions() {
        return queue.stream()
                   .map(ActionCommand::getDescription)
                   .collect(Collectors.toList());
    }
    
    public int size() {
        return queue.size();
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}