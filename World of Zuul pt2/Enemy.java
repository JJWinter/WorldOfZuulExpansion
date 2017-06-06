
/**
 * Class Enemy - An Enemy in an adventure game.
 * 
 * This class is an extension on the "World of Zuul" application.
 * 
 * An "Enemy" will block a path to a certain area, but can be killed
 * if the user posesses the correct item, allowing the user to progress.

 * @author 162205
 * @version 28/10/2016
 */ class Enemy
{
    private String name;
    private Item weakness;
    
    /**
     * Create an Enemy with a name, along with
     * the item required to kill it.
     * @param name The name of the enemy
     * @param weakness The item required to kill the enemy
     */
    public Enemy(String name, Item weakness)
    {
        this.name = name;
        this.weakness = weakness;
    }
    
    /**
     * Accsessor method
     * @return The item the enemy is weak to.
     */
    public Item getWeakness(){
        return weakness;
    }
    
    /**
     * Accsessor method
     * @return The name of the enemy.
     */
    public String getName(){
        return name;
    }
}
