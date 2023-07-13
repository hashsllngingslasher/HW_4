import java.util.Random;

public class Main {
    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {300, 270, 250, 200, 500, 250, 300, 240};
    public static int[] heroesDamage = {15, 10, 20, 10, 2, 15, 10, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Lucky", "Golem", "Berserk", "Thor", "Medical"};
    public static int roundNumber;
    public static int armor = bossDamage / 5;
    public static int previousValue = bossDamage;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        thorStunning();
        bossHits();
        berserkBlock();
        heroesHit();
        printStatistics();
        luckyDamageDodge();
        medicHealing();

    }

    public static void medicHealing() {
        int medicPower = 50;
        int min = heroesHealth[0];
        for (int i = 0; i < heroesHealth.length - 1; i++) {
            if (min > heroesHealth[i] && heroesHealth[i] != 0) {
                min = heroesHealth[i];
            }
        }
        if (min < 100 && min > 0 && heroesHealth[7] > 0) {
            int minIndex = 0;
            for (int i = 0; i < heroesHealth.length - 1; i++) {
                if (heroesHealth[i] == min) {
                    minIndex = i;
                    break;
                }
            }
            heroesHealth[minIndex] += medicPower;
            System.out.println("Medic will heal " + heroesAttackType[minIndex] + " by: " + medicPower + " HP");
        }
    }

    public static void luckyDamageDodge() {
        Random random = new Random();
        boolean chanceToMiss = random.nextBoolean();
        if (chanceToMiss && heroesHealth[3] > 0 && bossDamage > 0) {
            if (heroesHealth[4] > 0) {
                heroesHealth[3] += bossDamage - armor;
            } else {
                heroesHealth[3] += bossDamage;
            }
            System.out.println("Lucky will not get damage!");
        }
    }

    public static void berserkBlock() {
        if (bossDamage > 0) {
            if (heroesHealth[5] > 0) {
                if (heroesHealth[4] > 0) {
                    int block = (bossDamage - armor) / 10;
                    heroesHealth[5] += block;
                    bossHealth -= block;
                    System.out.println("Berserk will hit Boss by: " + (heroesDamage[5] + block) + " HP");
                } else {
                    int block = bossDamage / 10;
                    heroesHealth[5] += block;
                    bossHealth -= block;
                    System.out.println("Berserk will hit Boss by: " + (heroesDamage[5] + block) + " HP");
                }
            }
        }
    }

    public static void thorStunning() {
        Random random = new Random();
        boolean stunning = random.nextBoolean();
        if (stunning) {
            bossDamage = 0;
            System.out.println("Thor will stun Boss for one round!");
        } else {
            bossDamage = previousValue;
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length - 1); // 0,1,2,3,4,5,6,
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {// and Golem's Shield Ability
        if (bossDamage > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && heroesAttackType[i] != "Golem") {
                    if (heroesHealth[4] > 0) {
                        heroesHealth[i] -= bossDamage - armor;
                    } else {
                        heroesHealth[i] -= bossDamage;
                    }
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                } else if (heroesHealth[i] > 0) {
                    heroesHealth[i] -= bossDamage + armor * (heroesAttackType.length - 1);
                    if (heroesHealth[i] < 0) {
                        heroesHealth[i] = 0;
                    }
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println(heroesAttackType[i] + " will hit critical damage: " + damage + " HP");
                }
                bossHealth -= damage;
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("" + "\n" + "          HEROUS WON!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("" + "\n" + "          BOSS WON...");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("----------------- ROUND " + roundNumber + " -------------------");
        System.out.println("Boss - Health: " + bossHealth + " |  Damage: " + bossDamage + " |  Weakness: " + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " -  Health: " + heroesHealth[i] + " |  Damage: " + heroesDamage[i]);
        }
    }
}
