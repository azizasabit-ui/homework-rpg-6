package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class BlockHandler extends DefenseHandler {
    private final double blockPercent;

    public BlockHandler(double blockPercent) {
        this.blockPercent = blockPercent;
    }

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        int blockedAmount = (int)(incomingDamage * blockPercent);
        int remainingDamage = incomingDamage - blockedAmount;
        
        System.out.println("  🛡️ [BLOCK] " + target.getName() + " blocks " + blockedAmount + 
                          " damage (" + (int)(blockPercent * 100) + "% reduction)");
        
        if (remainingDamage > 0) {
            System.out.println("     Remaining damage: " + remainingDamage);
            passToNext(remainingDamage, target);
        } else {
            System.out.println("     All damage blocked!");
        }
    }
}