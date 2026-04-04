package com.narxoz.rpg.tournament;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import java.util.Random;

public class TournamentEngine {
    private final ArenaFighter hero;
    private final ArenaOpponent opponent;
    private Random random = new Random(1L);
    private DefenseHandler defenseChain;

    public TournamentEngine(ArenaFighter hero, ArenaOpponent opponent) {
        this.hero = hero;
        this.opponent = opponent;
    }

    public TournamentEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public TournamentResult runTournament() {
        TournamentResult result = new TournamentResult();
        int round = 0;
        final int maxRounds = 20;
        
        result.addLine("🏆 TOURNAMENT START: " + hero.getName() + " vs " + opponent.getName());
        result.addLine("Initial Stats:");
        result.addLine("  Hero: " + hero.getStatus());
        result.addLine("  Opponent: " + opponent.getStatus());
        
        DefenseHandler dodge = new DodgeHandler(hero.getDodgeChance(), random.nextLong());
        DefenseHandler block = new BlockHandler(hero.getBlockRating() / 100.0);
        DefenseHandler armor = new ArmorHandler(hero.getArmorValue());
        DefenseHandler hp = new HpHandler();
        
        dodge.setNext(block).setNext(armor).setNext(hp);
        defenseChain = dodge;
        result.addLine("\n✅ Defense chain built: Dodge → Block → Armor → HP");
        
        ActionQueue actionQueue = new ActionQueue();
        
        while (hero.isAlive() && opponent.isAlive() && round < maxRounds) {
            round++;
            result.addLine("\n═══════════════════════════════════════");
            result.addLine("ROUND " + round);
            result.addLine("═══════════════════════════════════════");
            
            result.addLine("\n📋 Planning phase:");
            actionQueue.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
            
            if (hero.getHealPotions() > 0 && hero.getHealth() < hero.getMaxHealth() * 0.5) {
                actionQueue.enqueue(new HealCommand(hero, 30));
            }
            
            actionQueue.enqueue(new DefendCommand(hero, 0.1));
            
            result.addLine("\n📋 Queued actions:");
            for (String desc : actionQueue.getCommandDescriptions()) {
                result.addLine("  • " + desc);
            }
            
            result.addLine("\n⚡ Execution phase:");
            actionQueue.executeAll();
            
            if (!opponent.isAlive()) {
                result.addLine("\n💀 " + opponent.getName() + " has been defeated!");
                break;
            }
            
            result.addLine("\n🔴 Opponent's turn:");
            int opponentDamage = opponent.getAttackPower();
            result.addLine("  " + opponent.getName() + " attacks for " + opponentDamage + " damage!");
            result.addLine("\n🛡️ Defense chain processing:");
            defenseChain.handle(opponentDamage, hero);
            
            result.addLine("\n📊 Round " + round + " summary:");
            result.addLine("  Hero HP: " + hero.getHealth() + "/" + hero.getMaxHealth());
            result.addLine("  Opponent HP: " + opponent.getHealth() + "/" + opponent.getHealth());
        }
        
        String winner;
        if (!hero.isAlive()) {
            winner = opponent.getName();
            result.addLine("\n💀 " + hero.getName() + " has been defeated!");
        } else if (!opponent.isAlive()) {
            winner = hero.getName();
            result.addLine("\n🏆 " + hero.getName() + " emerges victorious!");
        } else {
            winner = "Draw (max rounds reached)";
            result.addLine("\n⏰ Tournament ended due to max rounds limit!");
        }
        
        result.setWinner(winner);
        result.setRounds(round);
        
        result.addLine("\n═══════════════════════════════════════");
        result.addLine("🏆 TOURNAMENT END");
        result.addLine("Winner: " + winner);
        result.addLine("Total rounds: " + round);
        
        return result;
    }
}