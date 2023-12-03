import java.util.Random;
import java.util.Scanner;

public class SoldierMission {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("What's your name, soldier? ");
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        System.out.println("Welcome to Soldier's Mission!");
        System.out.println("Let's get started, Private " + playerName + ".");
        System.out.println("Defeat 5 enemies to complete the mission.");
       
        String[] weaponsArray = {"AK 47", "Benelli M4 Shotgun", "SERRATED COMBAT KNIFE"};
        int[] weaponDamageArray = {100, 150, 80}; 
        
        String[] mobsArray = {"Normal Mob", "Plated Mob", "High HP Mob"};
        int[] mobHealthArray = {100, 80, 150}; 

        int playerHealth = 100; 
        int defeatedEnemies = 0; 
        boolean continueGame = true;

        String currentEnemy = mobsArray[random.nextInt(mobsArray.length)];

        String lastUsedWeapon = "";

        while (continueGame && defeatedEnemies < 5 && playerHealth > 0) {
            // Generating Scenario and random foes with unique traits
            int enemyIndex = random.nextInt(mobsArray.length);
            String enemy = mobsArray[enemyIndex]; // Randomly select an enemy from mobsArray
            int enemyHealth = getFixedMobHealth(enemy, mobHealthArray); // Get fixed health for the enemy

            // Set the current enemy type
            currentEnemy = enemy;

            System.out.println("Soldier has encountered a " + currentEnemy + " with " + enemyHealth + " health");

            while (enemyHealth > 0) {
                // Input: Soldier is now fighting the enemy
                String weaponUsed;
                do {
                    System.out.print("Select a weapon (");
                    for (String weapon : weaponsArray) {
                        System.out.print(weapon + ", ");
                    }
                    System.out.println("): ");
                    weaponUsed = scanner.nextLine();

                    // Check if the selected weapon is the same as the last used weapon
                    if (weaponUsed.equalsIgnoreCase(lastUsedWeapon)) {
                        System.out.println("You cannot use the same weapon two times in a row. Choose a different weapon.");
                    }
                } while (weaponUsed.equalsIgnoreCase(lastUsedWeapon));

                // Update the last used weapon
                lastUsedWeapon = weaponUsed;

                // Process: Applying damage based on weapon
                int weaponIndex = findWeaponIndex(weaponsArray, weaponUsed);
                int damage = (weaponIndex != -1) ? weaponDamageArray[weaponIndex] : 0;

                // Check if the enemy has armor
                boolean enemyHasArmor = currentEnemy.equals("Plated Mob");

                // Adjust damage based on weapon and enemy armor
                if (weaponUsed.equals("Benelli M4 Shotgun")) {
                    if (enemyHasArmor) {
                        damage = 25; // Deal 25 damage against plated armor
                    } else {
                        damage = 150; // Deal 130 damage against no armor
                    }
                } else if (weaponUsed.equals("SERRATED COMBAT KNIFE")) {
                    if (enemyHasArmor) {
                        damage = 80; // Serrated Combat Knife does no damage to plated enemies
                    } else {
                        damage = 80; // Deal 75 damage against enemies without armor
                    }
                }else if (weaponUsed.equals("AK 47")) {
                    if (enemyHasArmor) {
                        damage = 35; // Serrated Combat Knife does no damage to plated enemies
                    } else {
                        damage = 100; // Deal 75 damage against enemies without armor
                    }
                }

                // Process: Applies damage to enemy
                enemyHealth -= damage;
                System.out.println("Deals " + damage + " damage to the " + currentEnemy);

                // Check if the enemy is defeated
                if (enemyHealth <= 0) {
                    System.out.println("You have defeated the " + currentEnemy + "! Well done, soldier");
                    defeatedEnemies++;

                    // Check if the mission is accomplished (5 defeated enemies)
                    if (defeatedEnemies == 5) {
                        System.out.println("Mission Accomplished! You have defeated 5 enemies.");
                        continueGame = false; // Stop the game
                        break; // Exit the loop
                    }

                    break; // Exit the loop if the enemy is defeated
                }

                // Enemy attacks the player for 35 damage
                int enemyAttackDamage = 35;
                playerHealth -= enemyAttackDamage;
                System.out.println("The " + currentEnemy + " hits you for " + enemyAttackDamage + " damage.");

                // Display player's health
                System.out.println("Player's health: " + playerHealth);

                // Check if the player's health is zero or less
                if (playerHealth <= 0) {
                    System.out.println("Your health is zero or less. Game Over!");
                    continueGame = false; // Stop the game
                    break; // Exit the loop
                }
            }

            if (continueGame && defeatedEnemies < 5) {
                // Repeat the process for the next encounter with the same enemy type
                System.out.println("Continue the game? (yes/no): ");
                String userInput = scanner.nextLine().toLowerCase();
                continueGame = userInput.equals("yes");
            }
        }
    }

    private static int findWeaponIndex(String[] weaponsArray, String weaponUsed) {
        for (int i = 0; i < weaponsArray.length; i++) {
            if (weaponsArray[i].equalsIgnoreCase(weaponUsed)) {
                return i;
            }
        }
        return -1; // to disregard other words that are not the given weapons
    }

    private static int getFixedMobHealth(String mobType, int[] mobHealthArray) {
        switch (mobType) {
            case "Normal Mob":
                return mobHealthArray[0];
            case "Plated Mob":
                return mobHealthArray[1];
            case "High HP Mob":
                return mobHealthArray[2];
            default:
                return 0; 
        }
    }
}

