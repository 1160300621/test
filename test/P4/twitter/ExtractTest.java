package twitter;

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import twitter.Extract;
import twitter.Timespan;
import twitter.Tweet;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T10:01:01Z");
    private static final Instant d4 = Instant.parse("2016-02-18T10:01:01Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "cdsadsfd", "miao miao miao", d3);
    private static final Tweet tweet4 = new Tweet(4, "eeedsfd", "pppppp", d4);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanOneTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanMoreThanTwoTweets1() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet3, tweet4));
        
        assertEquals("expected start", d3, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanMoreThanTwoTweets2() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d4, timespan.getEnd());
    }
    
    @Test
    public void testGetTimespanNoTweets() {
    	List<Tweet> ts=new ArrayList<Tweet>();
        Timespan timespan = Extract.getTimespan(ts);
        
        assertEquals(timespan,null);
    }
    
    private static final Tweet tweet5 = new Tweet(5, "dog", "hello@aab", d2);
    private static final Tweet tweet6 = new Tweet(6, "Dog", "nixibuxizhu@AAb", d2);
    private static final Tweet tweet7 = new Tweet(7, "bbitdiddle", "tes @cat# are you ok?", d2);
    private static final Tweet tweet8 = new Tweet(8, "bbitdiddle", "tes @CAT do you know?", d2);
    private static final Tweet tweet9 = new Tweet(9, "Pig", "bitdiddl@mit.edu", d2);
    private static final Tweet tweet10 = new Tweet(10, "donkey", "@zhu @gou @donkey", d2);
    private static final Tweet tweet11 = new Tweet(10, "donkey", "@zhu_233 @gou", d2);
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        Set<String> users=new HashSet<String>();
        users.add("aab");
        
        assertEquals(mentionedUsers, users);
    }

    @Test
    public void testGetMentionedUsersTheSameMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5,tweet6));
        Set<String> users=new HashSet<String>();
        users.add("aab");
        
        assertEquals(mentionedUsers, users);
    }
    
    @Test
    public void testGetMentionedUsersInvalidMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        Set<String> users=new HashSet<String>();
       
        assertEquals(mentionedUsers, users);
    }
    
    @Test
    public void testGetMentionedUsersTwoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7,tweet8));
        Set<String> users=new HashSet<String>();
        users.add("cat");
        
        assertEquals(mentionedUsers, users);
    }
    
    @Test
    public void testGetMentionedUsersInvalid2Mention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9));
        
        assertTrue(mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersOneTextTwoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet11));
        Set<String> users=new HashSet<String>();
        users.add("gou");
        users.add("zhu_233");

        assertEquals(mentionedUsers, users);
    }
    
    @Test
    public void testGetMentionedUsersComplexMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9,tweet10,tweet11));
        Set<String> users=new HashSet<String>();
        users.add("zhu");
        users.add("gou");
        users.add("donkey");
        users.add("zhu_233");

        assertEquals(mentionedUsers, users);
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
