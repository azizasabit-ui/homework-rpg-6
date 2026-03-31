package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class ArmorHandler extends DefenseHandler {
    private final int armorValue;

    public ArmorHandler(int armorValue) {
        this.armorValue = armorValue;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int reducedDamage = Math.max(0, incomingDamage - armorValue);
        int absorbedAmount = incomingDamage - reducedDamage;
        
        System.out.println("  🪖 [ARMOR] " + target.getName() + " armor absorbs " + absorbedAmount + 
                          " damage (Armor: " + armorValue + ")");
        
        if (reducedDamage > 0) {
            System.out.println("     Remaining damage: " + reducedDamage);
            passToNext(reducedDamage, target);
        } else {
            System.out.println("     All damage absorbed by armor!");
        }
    }
}