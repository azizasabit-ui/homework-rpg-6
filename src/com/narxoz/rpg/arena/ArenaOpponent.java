package com.narxoz.rpg.arena;

public class ArenaOpponent {
    private final String name;
    private int health;
    private final int maxHealth;
    private final int attackPower;

    public ArenaOpponent(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }

    public void takeDamage(int amount) {
        int actualDamage = Math.min(amount, health);
        health = Math.max(0, health - amount);
        System.out.println("  ⚔️ " + name + " takes " + actualDamage + " damage! HP: " + health + "/" + maxHealth);
    }

    public void restoreHealth(int amount) {
        health = Math.min(maxHealth, health + amount);
        System.out.println("  ↩️ " + name + " restores " + amount + " HP! HP: " + health + "/" + maxHealth);
    }

    public boolean isAlive() {
        return health > 0;
    }
    
    public String getStatus() {
        return name + " [HP: " + health + "/" + maxHealth + ", ATK: " + attackPower + "]";
    }
}