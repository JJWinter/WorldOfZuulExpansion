import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class GUI - A Visual Representation of Game
 * 
 * This class is an extension on the "World of Zuul" application.
 * 
 * A GUI will visually represent the events of the Game class, and will allow the user to
 * interact with buttons to control the Game's events.
 * 
 * @author 162205
 * @version 23/12/2016
 */
public class GUI extends JFrame{
    Game game;

    // ArrayList of Strings for the post-game Log file
    ArrayList<String> logFile = new ArrayList<String>();

    // Navigation Button Group
    JPanel buttonHolder = new JPanel(new GridLayout(3,3,4,4));
    ImageIcon upArrow = new ImageIcon(getClass().getResource("resources//uparrow.png"));
    JButton northButton = new JButton (upArrow); 
    ImageIcon rightArrow = new ImageIcon(getClass().getResource("resources//rightarrow.png"));
    JButton eastButton = new JButton (rightArrow);
    ImageIcon downArrow = new ImageIcon(getClass().getResource("resources//downarrow.png"));
    JButton southButton = new JButton (downArrow);
    ImageIcon leftArrow = new ImageIcon(getClass().getResource("resources//leftarrow.png"));
    JButton westButton = new JButton (leftArrow);
    JPanel middleButtons = new JPanel(new GridLayout(2,1));
    ImageIcon searchIcon = new ImageIcon(getClass().getResource("resources//search.png"));
    JButton searchButton = new JButton(searchIcon);
    ImageIcon takeIcon = new ImageIcon(getClass().getResource("resources//take.png"));
    JButton takeButton = new JButton(takeIcon);

    // Item List
    JPanel itemList = new JPanel(new GridLayout(20,1));

    // Map
    JPanel map = new JPanel(new GridLayout(8,3,5,5));

    // Image Holder
    JLabel image = new JLabel();

    // Middle Panel
    JPanel middle = new JPanel(new GridLayout(2,1));

    // Console
    JTextArea log = new JTextArea("", 10,2);

    // Menu Bar
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem helpMenuItem = new JMenuItem("Help");
    JMenuItem quitMenuItem = new JMenuItem("Quit");

    // Take Item Dialog Popup
    JDialog popup = new JDialog(this,"Take Item");
    JPanel popupPanel = new JPanel(new FlowLayout());

    /*
     * Set up GUI that uses a Game as the basis.
     * @param game The game to display
     */
    public GUI(Game game){
        this.game = game;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Custom window close function, creating a log file on exit
        addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent evt) {
                    generateLogFile();
                    System.exit(0);
                }
            });
        setTitle("World of Zuul");
        setSize(1000,1000);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Set Up Menu Bar
        menuBar.add(menu);
        menu.add(helpMenuItem);
        helpMenuItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    helpButton();
                }
            });
        menu.add(quitMenuItem);
        quitMenuItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    quitButton();
                    System.exit(0);
                }
            });
        setJMenuBar(menuBar);

        // Navigation Button Group
        buttonHolder.add(new JLabel());
        buttonHolder.add(northButton);
        northButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    goDirection("north");
                }
            });
        northButton.setBackground(new Color(144, 141,  109));
        buttonHolder.add(new JLabel());
        buttonHolder.add(westButton);
        westButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    goDirection("west");
                }
            });
        westButton.setBackground(new Color(144, 141,  109));
        middleButtons.add(searchButton);
        searchButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    searchArea();
                }
            });
        searchButton.setBackground(new Color(144, 141,  109));
        middleButtons.add(takeButton);
        takeButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    takeItemOptions();
                }
            });
        takeButton.setBackground(new Color(144, 141,  109));
        buttonHolder.add(middleButtons);
        buttonHolder.add(eastButton);
        eastButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    goDirection("east");
                }
            });
        eastButton.setBackground(new Color(144, 141,  109));
        buttonHolder.add(new JLabel());
        buttonHolder.add(southButton);
        southButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    goDirection("south");
                }
            });
        southButton.setBackground(new Color(144, 141,  109));
        buttonHolder.add(new JLabel());
        northButton.setToolTipText("Go North");
        eastButton.setToolTipText("Go East");
        southButton.setToolTipText("Go South");
        westButton.setToolTipText("Go West");
        searchButton.setToolTipText("Search Area");
        takeButton.setToolTipText("Take Item");

        // Item List
        itemList.setBorder(BorderFactory.createTitledBorder("Items"));
        itemList.setPreferredSize(new Dimension(200, 0));

        // Map
        map.setBorder(BorderFactory.createTitledBorder("Map"));
        map.setPreferredSize(new Dimension(400, 0));

        // Console
        log.setPreferredSize(new Dimension(0, 200));

        // Set Panel Colours
        itemList.setBackground(new Color(154, 151,  119));
        middle.setBackground(new Color(154, 151,  119));
        buttonHolder.setBackground(new Color(154, 151,  119));
        map.setBackground(new Color(154, 151,  119));
        log.setBackground(new Color(154, 151,  119));
        
        // Place panels in respective areas of screen
        middle.add(image);
        middle.add(buttonHolder);
        add(middle,BorderLayout.CENTER);
        add(itemList, BorderLayout.LINE_START);
        add(log, BorderLayout.PAGE_END);
        add(map, BorderLayout.LINE_END);

        // Take Item Popup Dialog Window
        popup.add(popupPanel);
        popup.setSize(100, 200);
        popup.setLocationRelativeTo(null);

        setVisible(true);
    }

    /*
     * Updates the colours and names of the GUI Map.
     * @param m List of rooms in Game.
     * @param currentRoom the room the user is currently in.
     */
    public void updateMap(ArrayList<Room> m, Room currentRoom){
        map.removeAll();
        for(int i = 0; i<24;i++){
            JLabel entry = new JLabel("", SwingConstants.CENTER);
            entry.setOpaque(true);
            // If the room has a name (blank rooms are used to fill empty spaces)
            if(m.get(i).getName() != "blank"){
                // If the room has been visited
                if(m.get(i).hasVisited()){
                    // If the room is the current room user is in.
                    if(m.get(i) == currentRoom){
                        entry.setBackground(Color.green);
                    } else {
                        entry.setBackground(Color.lightGray);
                    }
                    // If the room is locked or not.
                    if(m.get(i).isLocked()){
                        entry.setText("<html>" + m.get(i).getName() + " <br/> (Locked) </html>");
                        entry.setBackground(Color.gray);
                    } else {
                        entry.setText(m.get(i).getName());
                    }
                }
                // Fill red if user has not been to room.
                else{
                    entry.setBackground(Color.red);
                }
            } else {
                entry.setBackground(Color.darkGray);
            }
            map.add(entry);
        }
        // Switch GUI Image
        switchImage(currentRoom);

        revalidate();
    }

    /*
     * Switches the GUI's displayed image, depending on what room player is in.
     * @param currentRoom The room the user has entered.
     */
    public void switchImage(Room currentRoom){
        if(currentRoom.getName() == "Stables" || currentRoom.getName() == "Courtyard"){
            ImageIcon img = new ImageIcon("resources/castle.jpg");
            image.setIcon(img);
        } else if(currentRoom.getName() == "Castle Outskirts" ){
            ImageIcon img = new ImageIcon("resources/outskirts.jpg");
            image.setIcon(img);
        } else if(currentRoom.getName() == "Village"){
            ImageIcon img = new ImageIcon("resources/village.jpg");
            image.setIcon(img);
        } else if(currentRoom.getName() == "Inner Palace"){
            ImageIcon img = new ImageIcon("resources/palace.jpg");
            image.setIcon(img);
        } else if(currentRoom.getName() == "Cells"){
            ImageIcon img = new ImageIcon("resources/dungeon.jpg");
            image.setIcon(img);
        } else if(currentRoom.getName() == "Barracks" ||currentRoom.getName() == "Servants Quarters"){
            ImageIcon img = new ImageIcon("resources/barracks.jpg");
            image.setIcon(img);
        }
    }

    /*
     * Function for directional buttons.
     * Invokes the Go function in game.
     * @param direciton The direciton to move in.
     */
    private void goDirection(String direction){
        resetItemDialog();
        CommandWords commands = new CommandWords();
        Command c = new Command(commands.getCommandWord("go"), direction);
        game.processCommand(c);
        logFile.add("---Press: Go " + direction);
    }

    /*
     * Function for search button.
     * Invokes the Search function in game.
     */
    private void searchArea(){
        resetItemDialog();
        CommandWords commands = new CommandWords();
        Command c = new Command(commands.getCommandWord("search"), "");
        game.processCommand(c);
        logFile.add("---Press: Search Area ");
    }

    /*
     * Function for take item button.
     * Produces a Dialog screen listing the items in the room,
     * allowing user to pick between.
     */
    private void takeItemOptions(){
        Item[] itemList = game.getItemList();
        for(int i =0; i < itemList.length; i++){
            String name = itemList[i].getItemName();
            JButton b = new JButton(name);
            b.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    {
                        takeItem(name);
                    }
                });
            popupPanel.add(b);
        }
        popup.setVisible(true);
    }

    /*
     * Invokes the Take function in game.
     * @param item Name of Item to take.
     */
    private void takeItem(String item){
        resetItemDialog();
        CommandWords commands = new CommandWords();
        Command c = new Command(commands.getCommandWord("take"), item);
        game.processCommand(c);
        logFile.add("---Press: Take Item: "+ item);
    }

    /*
     * Adds the taken item to the GUI list of posessed items.
     * @param t Item to add to list.
     */
    public void addItemToList(Item t){
        JLabel newItem = new JLabel(t.getItemName());
        itemList.add(newItem);
    }

    /*
     * Reset's and hides 'Take Item' dialog.
     * Invoked when performing any other function.
     */
    private void resetItemDialog(){
        popupPanel.removeAll();
        popup.setVisible(false);
    }

    /*
     * Adds text to the GUI text box.
     * Adds new line (\n) after each line.
     * @param text Text to add to window.
     */
    public void addText(String text){
        log.append(text + "\n");
    }

    /*
     * Adds current contents of GUI text box to the Log file.
     * Clears the GUI text box.
     */
    public void clearText(){
        addToLogFile();
        log.setText("");
    }   

    /*
     * Function for Help button in menu
     * Prints game help to GUI text box.
     */
    private void helpButton(){
        clearText();
        game.printHelp();
    }

    /*
     * Function for escape button.
     * Invokes the QUIT function in game.
     */
    private void quitButton(){
        CommandWords commands = new CommandWords();
        Command c = new Command(commands.getCommandWord("quit"), null);
        game.processCommand(c);
        super.dispose();
    }

    /*
     * Adds contents of GUI text box to the Log String ArrayList.
     * Replaces "\n" with "\r\n".
     */
    private void addToLogFile(){
        String s = log.getText();
        // Replace 'New Line' (\n) with \r\n, which can be recognised by notepad.
        s = s.replaceAll("(?!\\r)\\n", "\r\n");
        logFile.add(s);
    }

    /*
     * Create a new log file, with timestamp, in /Logs sub folder.
     * Invoked on close.
     */
    private void generateLogFile(){ 
        addToLogFile();
        try {
            File file = new File("Logs/logFile"+ new Date().getTime() + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            for(String s: logFile){
                fileWriter.write(s);
                fileWriter.write(System.getProperty( "line.separator" ));
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}