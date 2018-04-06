package twitter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import twitter.SocialNetwork;
import twitter.Tweet;

public class MySocialNetworkTest {
    
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
    
    private static final Tweet tweet1 = new Tweet(1, "a", "@b @c #mit", d1);
    private static final Tweet tweet2 = new Tweet(2, "b", "@b #hit", d1);
    private static final Tweet tweet3 = new Tweet(3, "c", "@c @a @b do you love me", d1);
    private static final Tweet tweet4 = new Tweet(4, "d", "rivest talk in 30 minutes #mit", d1);
    private static final Tweet tweet5 = new Tweet(5, "e", "#hit @b", d1);

    @Test
    public void testGuessFollowsGraphOnlyOne() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("a", new HashSet<String>(Arrays.asList("b", "c")));
        assertEquals(followpeople, followsGraph);
    }
    
    @Test
    public void testGuessFollowsGraphEmptyFollowGraph() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("b", new HashSet<String>(Arrays.asList()));
        assertEquals(followpeople, followsGraph);
    }
    
    @Test
    public void testGuessFollowsGraphRemoveOne() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("c", new HashSet<String>(Arrays.asList("a","b")));
        assertEquals(followpeople, followsGraph);
    }
     
//    @Test
//    public void testGuessFollowsGraphTheSameTag() {
//    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3,tweet4));
//        Map<String, Set<String>> followpeople = new HashMap<>();
//        followpeople.put("a", new HashSet<String>(Arrays.asList("b","c","d")));
//        followpeople.put("b", new HashSet<String>(Arrays.asList()));
//        followpeople.put("c", new HashSet<String>(Arrays.asList("a","b")));
//        followpeople.put("d", new HashSet<String>(Arrays.asList("a")));
//        
//        assertEquals(followpeople, followsGraph);
//    }
    
    @Test
    public void testGuessFollowsGraphTheSameTagInComplex() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3,tweet4,tweet5));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("a", new HashSet<String>(Arrays.asList("b","c","d")));
        followpeople.put("b", new HashSet<String>(Arrays.asList("e")));
        followpeople.put("c", new HashSet<String>(Arrays.asList("a","b")));
        followpeople.put("d", new HashSet<String>(Arrays.asList("a")));
        followpeople.put("e", new HashSet<String>(Arrays.asList("b")));
        assertEquals(followpeople, followsGraph);
    }
 
}

