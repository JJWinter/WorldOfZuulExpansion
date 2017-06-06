

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class EnemyTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class EnemyTest
{
    /**
     * Default constructor for test class EnemyTest
     */
    public EnemyTest()
    {
    }

    /**
     * Tests an Enemy's weakness.
     */
    @Test
    public void testEnemyWeaknessItem(){
        Item t = new Item("WeaknessItem");
        Enemy e = new Enemy("TestEnemy",t); 
        assertEquals(t, e.getWeakness());
    }
    
     /**
     * Tests an Enemy's name.
     */
    @Test
    public void testEnemyGetName(){
        Item t = new Item("WeaknessItem");
        Enemy e = new Enemy("TestEnemy",t); 
        assertEquals("TestEnemy", e.getName());
    }
}
