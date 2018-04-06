/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
    	if (tweets.isEmpty()) {
    		return null;
    	}
        Instant start=tweets.get(0).getTimestamp(),end=tweets.get(0).getTimestamp();
        for (int i=1;i<tweets.size();i++) {
        	if (start.isAfter(tweets.get(i).getTimestamp())){
        		start=tweets.get(i).getTimestamp();
        	}
        	if (end.isBefore(tweets.get(i).getTimestamp())) {
        		end=tweets.get(i).getTimestamp();
        	}
        }
        Timespan t=new Timespan(start, end);
        return t;
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> set=new HashSet<>();
        for (int i=0;i<tweets.size();i++) {
        	String s="";
        	boolean flag=false;
        	String text=tweets.get(i).getText();
        	for (int j=0;j<text.length();j++) {
        		if (flag) {
        			if (text.charAt(j)=='_'||
        				text.charAt(j)=='-'||
        				text.charAt(j)<='9'&&text.charAt(j)>='0'||
        				text.charAt(j)<='z'&&text.charAt(j)>='a'||
        				text.charAt(j)<='Z'&&text.charAt(j)>='A') {
        				s+=text.charAt(j);
        				if (j==text.length()-1) {
        					s=s.toLowerCase();
            				set.add(s);
            				s="";
            				flag=false;
            				continue;
        				}
        				continue;
        			}
        			else if (text.charAt(j)==' '||text.charAt(j)=='\t'||j==text.length()-1) {
        				s=s.toLowerCase();
        				set.add(s);
        				s="";
        				flag=false;
        				continue;
        			}
        			else
    					s="";
    					flag=false;
    					continue;
        		}
        		
        		if (text.charAt(j)=='@') {
        			flag=true;
        			continue;
        		}
        		
        	}
        }
        return set;
    }
    
    public static void main(String[] args) {
        final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
        final Tweet tweet5 = new Tweet(5, "dog", "hello@aab", d2);
    	Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        Set<String> users=new HashSet<String>();
        users.add("aab");
        
        assertEquals(mentionedUsers, users);
    }
}
