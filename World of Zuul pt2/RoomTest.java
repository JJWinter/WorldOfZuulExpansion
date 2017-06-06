

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class Room.
 *
 * @author 162205
 * @version 09/11/2016
 */
public class RoomTest
{
    /**
     * Default constructor for test class RoomTest
     */
    public RoomTest()
    {
    }
    
    /**
     * Tests a new room has no items.
     */
    @Test
    public void testRoomItemsNumberNone(){
        Room r = new Room("in a test room", "Test Room");
        assertEquals(0, r.numberOfItems());
    }
    
    /**
     * Tests a room with an item correctly contains the item.
     */
    @Test
    public void testRoomItemsNumberOne(){
        Room r = new Room("in a test room", "Test Room");
        Item t = new Item("Key");
        r.addItem(t);
        assertEquals(1, r.numberOfItems());
    }
    
    /**
     * Tests a new room has not been visited.
     */
    @Test
    public void testRoomVisitedFalse(){
        Room r = new Room("in a test room", "Test Room");
        assertEquals(false, r.hasVisited());
    }
    
    /**
     * Tests a room is set to visited after being visited.
     */
    @Test
    public void testRoomVisitedTrue(){
        Room r = new Room("in a test room", "Test Room");
        r.setVisited();
        assertEquals(true, r.hasVisited());
    }
    
    /**
     * Tests a new room returns the correct room name.
     */
    @Test
    public void testRoomGetName(){
        Room r = new Room("in a test room", "Test Room");
        assertEquals("Test Room", r.getName());
    }
    
    /**
     * Tests the locking and unlocking of a room.
     */
    @Test
    public void testRoomLockedUnlocked(){
        Room r = new Room("in a test room", "Test Room");
        assertEquals(false, r.isLocked());
        r.setLocked();
        assertEquals(true,r.isLocked());
        r.unlock();
        assertEquals(false, r.isLocked());
    }
}
