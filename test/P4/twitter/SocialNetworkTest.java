package twitter;

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import twitter.SocialNetwork;
import twitter.Tweet;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
 private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "a", "@B @c", d1);
    private static final Tweet tweet2 = new Tweet(2, "b", "@b", d1);
    private static final Tweet tweet3 = new Tweet(3, "c", "@c @a @b do you love me", d1);
    private static final Tweet tweet4 = new Tweet(4, "d", "rivest talk in 30 minutes", d1);

    @Test
    public void testGuessFollowsGraph1() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("a", new HashSet<String>(Arrays.asList("b", "c")));
        assertEquals(followpeople, followsGraph);
    }
    
    @Test
    public void testGuessFollowsGraph2() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("b", new HashSet<String>(Arrays.asList()));
        assertEquals(followpeople, followsGraph);
    }
    
    @Test
    public void testGuessFollowsGraph3() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("c", new HashSet<String>(Arrays.asList("a","b")));
        assertEquals(followpeople, followsGraph);
    }
    
    @Test
    public void testGuessFollowsGraph4() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("d", new HashSet<String>(Arrays.asList()));
        assertEquals(followpeople, followsGraph);
    }
     
    @Test
    public void testGuessFollowsGraphComplex() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3,tweet4));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("a", new HashSet<String>(Arrays.asList("b","c")));
        followpeople.put("b", new HashSet<String>(Arrays.asList()));
        followpeople.put("c", new HashSet<String>(Arrays.asList("a","b")));
        followpeople.put("d", new HashSet<String>(Arrays.asList()));
        
        assertEquals(followpeople, followsGraph);
    }
    
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersOnlyOne() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("a", new HashSet<String>(Arrays.asList("b")));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals(Arrays.asList("b"), influencers);
    }
    
    @Test
    public void testInfluencersTheSameInfluence() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("a", new HashSet<String>(Arrays.asList("b")));
        followsGraph.put("b", new HashSet<String>(Arrays.asList("c","d")));        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals(Arrays.asList("b","c","d"), influencers);
    }

    @Test
    public void testInfluencersSortComplex() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("a", new HashSet<String>(Arrays.asList("b")));
        followsGraph.put("b", new HashSet<String>(Arrays.asList("c","d")));
        followsGraph.put("c", new HashSet<String>(Arrays.asList("a","b")));
        followsGraph.put("d", new HashSet<String>(Arrays.asList("a","b")));        
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals(Arrays.asList("b","a","c","d"), influencers);
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
