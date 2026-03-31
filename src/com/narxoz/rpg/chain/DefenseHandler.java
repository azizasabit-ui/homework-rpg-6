package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public abstract class DefenseHandler {
    private DefenseHandler next;

    public DefenseHandler setNext(DefenseHandler next) {
        this.next = next;
        return next;
    }

    protected DefenseHandler getNext() {
        return next;
    }

    protected void passToNext(int damage, ArenaFighter target) {
        if (damage > 0 && next != null) {
            next.handle(damage, target);
        } else if (damage > 0 && next == null) {
            System.out.println("  ⚠️ No handler for remaining " + damage + " damage!");
        }
    }

    public abstract void handle(int incomingDamage, ArenaFighter target);
}