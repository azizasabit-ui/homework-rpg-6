package com.narxoz.rpg.command;

import com.narxoz.rpg.arena.ArenaOpponent;

public class AttackCommand implements ActionCommand {
    private final ArenaOpponent target;
    private final int attackPower;
    private int damageDealt;

    public AttackCommand(ArenaOpponent target, int attackPower) {
        this.target = target;
        this.attackPower = attackPower;
    }

    @Override
    public void execute() {
        int oldHealth = target.getHealth();
        target.takeDamage(attackPower);
        damageDealt = oldHealth - target.getHealth();
        System.out.println("  🗡️ Attack deals " + damageDealt + " damage!");
    }

    @Override
    public void undo() {
        if (damageDealt > 0) {
            target.restoreHealth(damageDealt);
        }
    }

    @Override
    public String getDescription() {
        return "Attack for " + attackPower + " damage";
    }
}