package com.narxoz.rpg.arena;

public class ArenaFighter {
    private final String name;
    private int health;
    private final int maxHealth;
    private double dodgeChance;
    private final int blockRating;
    private final int armorValue;
    private final int attackPower;
    private int healPotions;

    public ArenaFighter(String name, int health, double dodgeChance,
                        int blockRating, int armorValue, int attackPower, int healPotions) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.dodgeChance = dodgeChance;
        this.blockRating = blockRating;
        this.armorValue = armorValue;
        this.attackPower = attackPower;
        this.healPotions = healPotions;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public double getDodgeChance() { return dodgeChance; }
    public int getBlockRating() { return blockRating; }
    public int getArmorValue() { return armorValue; }
    public int getAttackPower() { return attackPower; }
    public int getHealPotions() { return healPotions; }

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
        System.out.println("   " + name + " takes " + amount + " damage! HP: " + health + "/" + maxHealth);
    }

    public void heal(int amount) {
        if (healPotions <= 0) {
            System.out.println("   No heal potions left!");
            return;
        }
        int oldHealth = health;
        health = Math.min(maxHealth, health + amount);
        int actualHeal = health - oldHealth;
        healPotions--;
        System.out.println("   " + name + " heals for " + actualHeal + " HP! (Potions left: " + healPotions + ")");
    }

    public void modifyDodgeChance(double delta) {
        dodgeChance += delta;
        
        dodgeChance = Math.max(0.0, Math.min(0.75, dodgeChance));
        System.out.println("  🛡️ " + name + " dodge chance " + (delta > 0 ? "increased" : "decreased") + 
                          " by " + Math.abs(delta) + " to " + String.format("%.2f", dodgeChance));
    }

    public boolean isAlive() {
        return health > 0;
    }
    
    public String getStatus() {
        return name + " [HP: " + health + "/" + maxHealth + ", Dodge: " + String.format("%.2f", dodgeChance) + "]";
    }
}