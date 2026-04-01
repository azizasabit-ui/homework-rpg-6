package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;
import java.util.Random;

public class DodgeHandler extends DefenseHandler {
    private final double dodgeChance;
    private final Random random;

    public DodgeHandler(double dodgeChance, long seed) {
        this.dodgeChance = dodgeChance;
        this.random = new Random(seed);
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        double roll = random.nextDouble();
        
        if (roll < dodgeChance) {
            System.out.println("  ✨ [DODGE] " + target.getName() + " evades " + incomingDamage + 
                              " damage! (Roll: " + String.format("%.3f", roll) + 
                              " < " + String.format("%.3f", dodgeChance) + ")");
            
        } else {
            System.out.println("  🎯 [DODGE FAIL] " + target.getName() + " fails to dodge! (Roll: " + 
                              String.format("%.3f", roll) + " >= " + String.format("%.3f", dodgeChance) + ")");
            passToNext(incomingDamage, target);
        }
    }
}