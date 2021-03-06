package twitter;

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import twitter.Filter;
import twitter.Timespan;
import twitter.Tweet;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    private static final Instant d3 = Instant.parse("2016-02-17T12:10:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T15:00:00Z");
    private static final Instant d5 = Instant.parse("2016-02-17T11:30:00Z");
    
    private static final Tweet tweet3 = new Tweet(3, "oliver", "are you ok are you ok?", d3);
    private static final Tweet tweet4 = new Tweet(4, "oliver", "do you love me oh i love you", d4);
    private static final Tweet tweet5 = new Tweet(5, "nancy", "do you love me oh i love you", d5);

    @Test
    public void testWrittenByMultipleTweetsNoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "wangwangwang");
        
        assertEquals("expected singleton list", 0, writtenBy.size());
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    @Test
    public void testWrittenByMultipleTweetsDoubleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet3, tweet4, tweet5), "oliver");
        
        assertEquals("expected doubleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.containsAll(Arrays.asList(tweet3, tweet4)));
        assertEquals("expected same order", 1, writtenBy.indexOf(tweet4));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults2() {
        Instant testStart = Instant.parse("2016-02-17T11:20:00Z");
        Instant testEnd = Instant.parse("2016-02-17T13:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2 ,tweet3 ,tweet4 ,tweet5), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet3, tweet5)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet3));
        assertEquals("expected same order", 1, inTimespan.indexOf(tweet5));
    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults3() {
        Instant testStart = Instant.parse("2016-02-18T11:20:00Z");
        Instant testEnd = Instant.parse("2016-02-18T13:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2 ,tweet3 ,tweet4 ,tweet5), new Timespan(testStart, testEnd));
        
        assertTrue("expected non-empty list", inTimespan.isEmpty());
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    @Test
    public void testContaining2() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("you"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet3, tweet4, tweet5)));
        assertEquals("expected same order", 0, containing.indexOf(tweet3));
        assertEquals("expected same order", 1, containing.indexOf(tweet4));
        assertEquals("expected same order", 2, containing.indexOf(tweet5));
    }
    
    @Test
    public void testContaining3() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("abcdefg"));
        
        assertTrue("expected non-empty list", containing.isEmpty());
    }
    
    @Test
    public void testContaining4() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("is","ok"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet3)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
        assertEquals("expected same order", 1, containing.indexOf(tweet3));
    }
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
