import java.util.Set;
import java.util.Map.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * A Room may also store a list of Items and may also include an Enemy.
 * 
 * @author  Michael Kolling and David J. Barnes - Modified by 162205
 * @version 2016.11.07
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private boolean visited;
    private String roomName;

    private boolean locked = false;

    private ArrayList<Item> roomItems = new ArrayList<Item>();  // Stores items in the room.
    private Enemy roomEnemy;
    
    private String itemHint;

    /**
     * Create a room described "description" with name "roomName". Initially, it has
     * no exits. "description" is something like "a kitchen" or "an open court yard".
     * @param description The room's description.
     * @param roomName The name of the room
     */
    public Room(String description, String roomName) 
    {
        this.description = description;
        this.roomName = roomName;
        exits = new HashMap<String, Room>();
        visited = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, and the rooms name, if it has been visited before, for example
     * "Exits: north (unknown) west (Cells)".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        // For each direction a room has a room attached, append the name of the adjacent room.
        for(Entry<String, Room> e : exits.entrySet()) {
            String direction = e.getKey();
            Room room = e.getValue();
            // If room has been visited, append the name of the room. If not, append (unknown).
            if(room.hasVisited()){
                returnString += " " + direction + " (" + room.getName() + ")"; 
            } else {
                returnString += " " + direction + " (Unknown)";
            }
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Set a boolean dictating if a room has been visited at least once
     */
    public void setVisited(){
        visited = true;
    }

    /**
     * Accsessor method
     * @return A boolean value for visited.
     */
    public boolean hasVisited(){
        return visited;
    }

    /**
     * Accsessor method
     * @return The name of the room
     */
    public String getName(){
        return roomName;
    }

    /**
     * Add an Item object to the ArrayList of items in the room
     * @param item The Item to add
     */
    public void addItem(Item item){
        roomItems.add(item);
    }

    /**
     * Remove an item from the ArrayList of items in the room.
     * @param itemName The name of the item to take.
     * @return The item that has been taken from the room.
     */
    public Item getItem(String itemName){
        Item item = null;
        for(Item t:roomItems){
            if (itemName.equals(t.getItemName())){
                item = t;
                return item;
            }
        }
        return item;
    }
    
    /**
     * Removes an item from the current room.
     * @param t Item to remove from room.
     */
    public void removeItem(Item t){
        roomItems.remove(t);
    }

    /**
     * Accsessor method
     * @return Number of items in room.
     */
    public int numberOfItems(){
        return roomItems.size();
    }

    /**
     * Displays the list of items in the room, if any.
     * @return A string containing the list of the items.
     */
    public String getItems(){
        if(roomItems.size() > 0) {
            String itemList = "You find these items: ";
            for(Item i : roomItems){
                itemList += i.getItemName() + " ";
            }
            return itemList;
        } else {
            return "You could not find any items";
        }
    }
    
    /**
     * Returns the list of items in the room as an array.
     * @return Array of Items.
     */
    public Item[] getItemList(){
        Item[] array = roomItems.toArray(new Item[roomItems.size()]);
        return array;
    }

    /**
     * Sets the room's boolean locked to true, locking the room.
     */
    public void setLocked(){
        locked = true;
    }

    /**
     * Sets the boolean locked to false, unlocking the room.
     */
    public void unlock(){
        locked = false;
    }

    /**
     * Accsessor method
     * @return Boolean for if room is locked or not.
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Adds an Enemy object to reside in this room.
     * @param e The Enemy to add to the room.
     */
    public void addEnemy(Enemy e){
        roomEnemy = e;
    }

    /**
     * Removes the Enemy from the room by setting to null.
     */
    public void removeEnemy(){
        roomEnemy = null;
    }

    /**
     * Returns a boolean to see if the room contains an enemy or not.
     * @return Boolean true if there is an enemy, elsewise false.
     */
    public boolean hasEnemy(){
        if(roomEnemy == null){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Accsessor method
     * @return The Enemy in the room.
     */
    public Enemy getEnemy(){
        return roomEnemy;
    }
    
    /**
     * Sets a line that hints the user that an item is in the room.
     * @param hint The line hinting the user.
     */
    public void setItemHint(String hint){
        itemHint = hint;
    }
    
    /**
     * Acsessor method
     * @return The item hint message.
     */
    public String printItemHint(){
        return itemHint;
    }

}
