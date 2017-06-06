import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes - Modified by 162205
 * @version 2016.11.07
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private GUI gui;

    private ArrayList<Item> playerItems = new ArrayList<Item>();

    ArrayList<Room> roomMap = new ArrayList<Room>();

    private boolean gameFinished = false;  // Used to dictate if the game has finished

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        gui = new GUI(this);
        createRooms();
        parser = new Parser();
        play();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room throneRoom, innerPalace, storage, secretRoom, servantQuarters, courtyard, barracks, cells, dungeons, gate,
        stables, gardens, swamp, castleOutskirts, villageCenter, blacksmith, tailorShop, potionShop;

        // create the rooms
        throneRoom = new Room("in the Throne Room", "Throne Room");
        innerPalace = new Room("inside the Inner Palace", "Inner Palace");
        storage = new Room("in a storage area", "Storage");
        secretRoom = new Room("in a hidden room", "Secret Room");
        servantQuarters = new Room("in the servants quarters", "Servants Quarters");
        courtyard = new Room("in the castle courtyard", "Courtyard");
        barracks = new Room("in the castle barracks", "Barracks");
        cells = new Room("outside the castle prison cells", "Cells");
        dungeons = new Room("in the deeper depths of the castle dungeons", "Dungeons");
        gate = new Room("afront the castle gate", "Gate");
        stables = new Room("at the stables outside the castle", "Stables");
        gardens = new Room("in the castle gardens", "Gardens");
        castleOutskirts = new Room("in the castle outskirts", "Castle Outskirts");
        swamp = new Room("in the swamps", "Swamp");
        villageCenter = new Room("In the village center. It seems deserted","Village");
        blacksmith = new Room("In the village forge", "Village Forge");
        tailorShop = new Room("In the village tailors","Village Tailor");
        potionShop = new Room("In the village potion store", "Village Potion Store");

        // Create Room Item Hint Messages
        tailorShop.setItemHint("Maybe there will be something useful I can wear...");
        blacksmith.setItemHint("Maybe I can grab a weapon from here...");
        potionShop.setItemHint("There has to be something useful here...");
        swamp.setItemHint("You see a twinkling amongst some rags in the swamp...");
        dungeons.setItemHint("You see a corpse chained up...");
        barracks.setItemHint("There must be something useful here...");
        secretRoom.setItemHint("You see a chest of drawers...");

        // initialise room exits
        throneRoom.setExit("south", innerPalace);
        innerPalace.setExit("north",throneRoom);
        innerPalace.setExit("west", storage);
        innerPalace.setExit("east", servantQuarters);
        innerPalace.setExit("south", courtyard);
        storage.setExit("east",innerPalace);
        storage.setExit("north", secretRoom);
        secretRoom.setExit("south", storage);
        servantQuarters.setExit("south", barracks);
        servantQuarters.setExit("west", innerPalace);
        barracks.setExit("west", courtyard);
        barracks.setExit("north",servantQuarters);        
        courtyard.setExit("north", innerPalace);
        courtyard.setExit("east", barracks);
        courtyard.setExit("west", cells);
        courtyard.setExit("south", gate);
        cells.setExit("south", dungeons);
        cells.setExit("east",courtyard);
        dungeons.setExit("north",cells);        
        gate.setExit("north",courtyard);
        gate.setExit("south",stables);
        stables.setExit("north",gate);
        stables.setExit("east",gardens);
        stables.setExit("south",castleOutskirts);
        gardens.setExit("south",swamp);
        gardens.setExit("west",stables);
        swamp.setExit("west",castleOutskirts);
        swamp.setExit("north", gardens);
        castleOutskirts.setExit("north",stables);
        castleOutskirts.setExit("east",swamp);
        castleOutskirts.setExit("south",villageCenter);
        villageCenter.setExit("north",castleOutskirts);
        villageCenter.setExit("west",blacksmith);
        villageCenter.setExit("east",tailorShop);
        villageCenter.setExit("south",potionShop);
        blacksmith.setExit("east",villageCenter);
        tailorShop.setExit("west",villageCenter);
        potionShop.setExit("north",villageCenter);

        // Add items to rooms
        Item dungeonKey = new Item("Dungeon-Key");
        swamp.addItem(dungeonKey);
        Item potionStoreKey = new Item("Store-Key");
        dungeons.addItem(potionStoreKey);
        Item boots = new Item("Heavy-Boots");
        tailorShop.addItem(boots);
        Item dagger = new Item("Dagger");
        blacksmith.addItem(dagger);
        Item potion = new Item("Potion");
        potionShop.addItem(potion);
        Item sword = new Item("Sword");
        barracks.addItem(sword);
        Item grandKey = new Item("Grand-Key");
        secretRoom.addItem(grandKey);

        // Lock rooms
        dungeons.setLocked();
        potionShop.setLocked();
        innerPalace.setLocked();
        throneRoom.setLocked();

        // Create Enemies
        Enemy troll = new Enemy("Troll", dagger);
        barracks.addEnemy(troll);
        Enemy spider = new Enemy("Giant Spider", sword);
        storage.addEnemy(spider);
        Enemy giant = new Enemy("Giant", potion);
        throneRoom.addEnemy(giant);

        // Cheat for testing
        //playerItems.add(potion);
        //playerItems.add(grandKey);
        //playerItems.add(dagger);

        // Set GUI Map Colours
        roomMap.add(secretRoom);
        roomMap.add(throneRoom);
        roomMap.add(new Room("","blank"));
        roomMap.add(storage);
        roomMap.add(innerPalace);
        roomMap.add(servantQuarters);
        roomMap.add(cells);
        roomMap.add(courtyard);
        roomMap.add(barracks);
        roomMap.add(dungeons);
        roomMap.add(gate);
        roomMap.add(new Room("","blank"));
        roomMap.add(new Room("","blank"));
        roomMap.add(stables);
        roomMap.add(gardens);
        roomMap.add(new Room("","blank"));
        roomMap.add(castleOutskirts);
        roomMap.add(swamp);
        roomMap.add(blacksmith);
        roomMap.add(villageCenter);
        roomMap.add(tailorShop);
        roomMap.add(new Room("","blank"));
        roomMap.add(potionShop);
        roomMap.add(new Room("","blank"));

        currentRoom = castleOutskirts;  // start game outside
        castleOutskirts.setVisited();
        gui.updateMap(roomMap, currentRoom);
   }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            //Command command = parser.getCommand();
           // finished = processCommand(command);
        }
        gui.addText("Thank you for playing. Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gui.addText("Welcome to the World of Zuul!");
        gui.addText("In the distance, you see a menacing castle");
        gui.addText("Go to Menu->Help if you need help.");
        gui.addText(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        // Empty the GUI's text area
        gui.clearText();

        if(commandWord == CommandWord.UNKNOWN) {
            gui.addText("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if(commandWord == CommandWord.TAKE) {
            takeItem(command);
        }
        else if(commandWord == CommandWord.SEARCH) {
            searchRoom();
        }

        // Refresh UI Map
        gui.updateMap(roomMap, currentRoom);

        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    public void printHelp() 
    {
        gui.addText("You are lost. You are alone. You wander");
        gui.addText("Your command words are:");
        gui.addText(parser.showCommands());
        gui.addText("To navigate through the world, use the arrow button of the direction you want to go.");
        gui.addText("To look for any items in an area, use the 'search' button, and you shall be notified of any items.");
        gui.addText("To pick up an item, press the 'take button' and select the item you want.");
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message. If the room is locked, print an error message.
     */
    private void goRoom(Command command) 
    {
        boolean progress = false;
        // If there is no second word, we don't know where to go...
        if(!command.hasSecondWord()) {
            gui.addText("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        // If there is no path.
        if (nextRoom == null) {
            gui.addText("There is no door!");
            return;
        }

        // For traversing the Swamp
        else if(nextRoom.getName() == "Swamp"){
            for(Item t : playerItems){
                // If the player has the boots.
                if(t.getItemName() == "Heavy-Boots"){
                    gui.addText("You slog through the swamp in your Heavy Boots");
                    progress = true;
                    break;
                }
            }
            // If the player does not have the boots.
            if(progress == false){
                gui.addText("You can't navigate the swamp in your current getup.");
                gui.addText("Maybe something could help you walk through the swamp.");
                nextRoom.setVisited();
            }
        } 

        // If the next room is locked, the player cannot progress unless they have the correct item. 
        else if (nextRoom.isLocked()){
            // Special case for the Inner Palace only unlocking when approaching from Servants Quarters
            if(nextRoom.getName() == "Inner Palace" && currentRoom.getName() == "Servants Quarters"){
                gui.addText("This route into the Inner Palace is unlocked");
                gui.addText("You open the doorway to the courtyard");
                nextRoom.unlock();
                progress = true;
            }
            else {
                boolean correctKey = false;
                Item key = null;

                // If the player has no items, the door will not unlock.
                if(playerItems.size() == 0){
                    nextRoom.setVisited();
                }

                // To enter dungeons, they must have the "Dungeon-Key".
                else if(nextRoom.getName() == "Dungeons"){
                    for(Item t : playerItems){
                        if(t.getItemName() == "Dungeon-Key"){
                            correctKey = true;
                            key = t;
                            break;
                        }
                    } 
                } 
                // To enter Village Potion Store, they must have the "Store-Key".
                else if(nextRoom.getName() == "Village Potion Store"){
                    for(Item t : playerItems){
                        if(t.getItemName() == "Store-Key"){
                            correctKey = true;
                            key = t;
                            break;
                        }
                    } 
                }
                // To enter Throne Room, they must have the "Grand-Key".
                else if(nextRoom.getName() == "Throne Room"){
                    for(Item t : playerItems){
                        if(t.getItemName() == "Grand-Key"){
                            correctKey = true;
                            key = t;
                            break;
                        }
                    } 
                }

                // If the user successfully unlocked the door or not.
                if (correctKey){
                    gui.addText("You unlocked the path with the " + key.getItemName());
                    nextRoom.unlock();
                    progress = true;         
                } else {
                    gui.addText("The door appears to be locked...");
                    nextRoom.setVisited();
                    gui.addText("There should be a key around somewhere.");
                }
            }
        }

        // If none of the above conditions, progress.
        else {
            progress = true;
        }

        // If an enemy blocks the path.
        if(nextRoom.getEnemy() != null && !nextRoom.isLocked()){
            Enemy enemy = nextRoom.getEnemy();
            gui.addText("A hostile " + enemy.getName() + " stands before you!");
            if(playerItems.contains(enemy.getWeakness())){
                
                gui.addText("You slay the " + enemy.getName() + " with your " + enemy.getWeakness().getItemName());
                nextRoom.removeEnemy();
                progress = true;
            } else {
                gui.addText("The " +enemy.getName()+ " proves too tough for you, so you retreat.");
                gui.addText("Maybe you need a certain kind of weapon...");
                progress = false;
            }
        }

        // Progress to the next room
        if(progress){
            currentRoom = nextRoom;
            // If the player cleared the final room, the game is over.
            if(currentRoom.getName() == "Throne Room"){
                gui.addText("");
                gui.addText("--------------");
                gui.addText("Congratulations!");
                gui.addText("You have completed the game");
                gui.addText("--------------");
                gameFinished = true;
            }
        }

        // If the game has not finished, continue to print descriptions of possible movement.
        if(!gameFinished){
            gui.addText(currentRoom.getLongDescription());
            if(currentRoom.hasVisited()){
                if(currentRoom.printItemHint() == null){
                } else {
                    gui.addText(currentRoom.printItemHint());
                }
            } else {
                currentRoom.setVisited();
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            gui.addText("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Pick up the requested item in the room. If incorrect, print error.
     * Add this item to the player's list of items, and remove item from room.
     */
    private void takeItem(Command command){
        if(!command.hasSecondWord()){
            gui.addText("Take which item?");
        } else {
            String itemName = command.getSecondWord();
            if(currentRoom.getItem(itemName) == null){
                gui.addText("Not a valid item to take");
            } else {
                Item t = currentRoom.getItem(itemName);
                playerItems.add(t); 
                currentRoom.removeItem(t);
                gui.addText("You picked up the " + t.getItemName());
                gui.addText(currentRoom.getLongDescription());
                addItemToGui(t);
            }
        }
    }
    
    /**
     * Returns the list of items in currentRoom as an Array.
     * @return Array of items.
     */
    public Item[] getItemList(){
        return currentRoom.getItemList();
    }

    /**
     * Adds a picked up item to the list of items in the GUI.
     * @param t Item to add to GUI.
     */
    private void addItemToGui(Item t){
        gui.addItemToList(t);
    }
    
    /**
     * Print line for searching the current room for items.
     */
    private void searchRoom(){
        gui.addText(currentRoom.getItems());
    }

}
