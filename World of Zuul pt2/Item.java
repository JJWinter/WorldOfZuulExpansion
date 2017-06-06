
/**
 * Class Item - An Item in an adventure game.
 * 
 * This class is an extension on the "World of Zuul" application.
 * 
 * An "Item" can be possesed by the player, and will allow the user
 * to perform certain actions across the world that may be nesseccary 
 * to progress. An item will be picked up from a room.
 * 
 * @author 162205
 * @version 26/10/2016
 */
public class Item
{
    private String itemName;
    
    /**
     * Create an item with a given name
     * @param itemName The name of the item
     */
    public Item(String itemName)
    {
        this.itemName = itemName;
    }
    
    /**
     * Accsessor method
     * @return The name of the Item
     */
    public String getItemName(){
        return itemName;
    }
    
}
