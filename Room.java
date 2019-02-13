import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Room {
    private boolean visited = false;

    private String mapIcon;


    public void enter(Player player) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int encounter = random.nextInt(2)+1;

        if (!visited) {
            // if encounter is monster
            if (encounter == 1) {

                Monster monster = new Monster();
                monster.initializeMonster();

                System.out.println("A " + monster.getMonsterType() + " appears");
                boolean monsterAlive = true;
                while (monsterAlive) {

                    System.out.println("Select an action: [1] Attack, [2] Run ");
                    String combatAction = scanner.nextLine();


                    //attack
                    if (combatAction.equals("1")) {

                        // set random damage
                        randMonsterDam(monster);
                        // attack player
                        monster.attack(player);
                        // set player random damage
                        randPlayerDam(player);
                        System.out.println("The " + monster.getMonsterType() + " hits you for " + monster.getDamage() + " damage!");
                        System.out.println("HP: " + player.getHealth());
                        //player attack
                        player.attack(monster);
                        // reset player damage
                        resetPlayerDam(player);
                        // reset monster damage
                        resetMonsterDam(monster);

                        if (monster.getHealth() <= 0) {
                            System.out.println("The " + monster.getMonsterType() + " dies!");
                            int randGold = ThreadLocalRandom.current().nextInt(10, 30) + 1;
                            player.onLoot(randGold);
                            monsterAlive = false;
                        }
                    }


                    //run
                    else if (combatAction.equals("2")) {
                        monster.attack(player);
                        System.out.println("You try to run... ");
                        System.out.println("The " + monster.getMonsterType() + " attacks and hits you for " + monster.getDamage() + " damage as you try to escape");
                        break;

                    }
                    else {
                        System.out.println("Invalid input");

                    }
                    visited = true;

                }
            }

            // if encounter is loot
            else if (encounter == 2) {
                int goldOrHeal = random.nextInt(2) + 1;

                // health encounter
                if (goldOrHeal == 1) {
                    int healthPotion = ThreadLocalRandom.current().nextInt(5, 30) + 1;
                    player.onHeal(healthPotion);
                    System.out.println("You found a health potion and gained " + healthPotion + " health");
                }

                // gold encounter
                if (goldOrHeal == 2) {
                    int goldLoot = ThreadLocalRandom.current().nextInt(5, 35) + 1;
                    player.onLoot(goldLoot);


                }
                visited = true;
            }

        }

    }



    public String getMapIcon() {
        return mapIcon;
    }

    public void randPlayerDam(Player player){
        Random random = new Random();
        player.setDamage(random.nextInt(player.getDamage()+1));
        if(player.getDamage() == 0 || player.getDamage() == 1)
        {
            player.setDamage(random.nextInt(player.getDamage()+1));
        }
    }
    public void randMonsterDam(Monster monster){
        Random random = new Random();
        monster.setDamage(random.nextInt(monster.getDamage())+1);
    }

    public void resetMonsterDam(Monster monster){
        // reset monster damage for proper random
        if(monster.getMonsterType().equals("Goblin")){
            monster.setDamage(10);
        }
        else if(monster.getMonsterType().equals("Zombie")){
            monster.setDamage(15);
        }
        else if(monster.getMonsterType().equals("Orc")){
            monster.setDamage(20);
        }
        else if(monster.getMonsterType().equals("Deneke")){
            monster.setDamage(5);
        }
    }
    public void resetPlayerDam(Player player){
        // reset player damage for proper random effect
        if(player.getPlayerClass().equals("1")){
            player.setDamage(15);
        }
        else if(player.getPlayerClass().equals("2")){
            player.setDamage(10);
        }

    }

    public void setMapIcon(String mapIcon) {
        this.mapIcon = mapIcon;
    }
}