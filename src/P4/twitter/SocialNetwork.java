/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {
	public static Set<String> getHashtags(Tweet tweet) {
		Set<String> set = new HashSet<>();
		String s = "";
		boolean flag = false;
		String text = tweet.getText();
		for (int j = 0; j < text.length(); j++) {
			if (flag) {
				if (text.charAt(j) == '_' || text.charAt(j) <= '9' && text.charAt(j) >= '0'
						|| text.charAt(j) <= 'z' && text.charAt(j) >= 'a'
						|| text.charAt(j) <= 'Z' && text.charAt(j) >= 'A') {
					s += text.charAt(j);
					if (j == text.length() - 1) {
						s = s.toLowerCase();
						set.add(s);
						s = "";
						flag = false;
						continue;
					}
					continue;
				} else if (text.charAt(j) == ' ' || text.charAt(j) == '\t' || j == text.length() - 1) {
					s = s.toLowerCase();
					set.add(s);
					s = "";
					flag = false;
					continue;
				} else
					flag = false;
				break;
			}

			if (text.charAt(j) == '#') {
				flag = true;
				continue;
			}

		}
		return set;
	}

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets
	 *            a list of tweets providing the evidence, not modified by this
	 *            method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
    	Map<String, Set<String>> FollowGraph=new HashMap<String,Set<String>>();
    	for (int i=0;i<tweets.size();i++) {
        	Set<String> set=new HashSet<String>();
    		List<Tweet> tweet=new ArrayList<Tweet>();
    		tweet.add(tweets.get(i));
    		set=Extract.getMentionedUsers(tweet);
    		if (set.contains(tweets.get(i).getAuthor())) {
    			set.remove(tweets.get(i).getAuthor());
    		}
    		FollowGraph.put(tweets.get(i).getAuthor(),set);
    	}
    	
//    	for (int i=0;i<tweets.size();i++) {
//    		Set<String> a=getHashtags(tweets.get(i));
//    		for (Tweet tw:tweets) {
//    			Set<String> b=getHashtags(tw);
//    			if (a.equals(b)&&tw!=tweets.get(i)&&!a.isEmpty()) {
//    				Set<String> c=FollowGraph.get(tweets.get(i).getAuthor());
//    				c.add(tw.getAuthor());
//    				FollowGraph.put(tweets.get(i).getAuthor(), c);
//    			}
//    		}
//    	}
    	
    	
  
    	return FollowGraph;
    }
    
    public static void main(String[] args) {
    	Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
        
        Tweet tweet1 = new Tweet(1, "a", "@b @c #mit", d1);
        Tweet tweet2 = new Tweet(2, "b", "@b", d1);
        Tweet tweet3 = new Tweet(3, "c", "@c @a @b do you love me", d1);
        Tweet tweet4 = new Tweet(4, "d", "rivest talk in 30 minutes #mit", d1);
        
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1,tweet2,tweet3,tweet4));
        Map<String, Set<String>> followpeople = new HashMap<>();
        followpeople.put("a", new HashSet<String>(Arrays.asList("b","c","d")));
        followpeople.put("b", new HashSet<String>(Arrays.asList()));
        followpeople.put("c", new HashSet<String>(Arrays.asList("a","b")));
        followpeople.put("d", new HashSet<String>(Arrays.asList("a")));
	}

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
    	List<String> influencers=new ArrayList<String>();
    	Map<String, Integer> NameCount=new HashMap<String,Integer>();
    	for (Set<String> set:followsGraph.values()) {
    		for (String s:set) {
    			if (!NameCount.containsKey(s)) {
    				NameCount.put(s, 0);
    			}
    			else{
    				int newvalue=NameCount.get(s)+1;
    				NameCount.put(s, newvalue);
    			}
    		}
    	}
    	
    	Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue()-o1.getValue();
			}	
    	};
    	List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(NameCount.entrySet());
    	Collections.sort(list,valueComparator);
    	
    	for (Map.Entry<String, Integer> map:list) {
    		influencers.add(map.getKey());
    	}
    	
    	for (String a:influencers) {
    		System.out.println(a+" "+NameCount.get(a));
    	}
    	return influencers; 
    }

}
