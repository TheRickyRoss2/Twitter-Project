package org.example;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TwitterTest {
    Twitter testObject = new Twitter();

    @BeforeEach
    void setUp() throws IOException, ParseException {

        testObject.parseJsonInfo("testbase.json");
        testObject.login("user", "pass2");
    }

//    @Test
//    void main() {
//    }

//    @Test
//    void printMenu() {
//        assertEquals("Checking size of List", 3, obj.sizeOfStudent());
//    }

    @Test
    void login() {
        assertFalse(Twitter.login("user", "pass2") == -1, "Check that the Twitter login is false");
    }

//    @Test
//    void parseJsonInfo() {
//
//    }

//    @Test
//    void start() {
//
//    }

    @Test
    void showAllTweets() {
        assertTrue(Twitter.showAllTweets().size() > 0, "Number of tweets is empty");
    }

    @Test
    void showAllUsers() {
        assertTrue(Twitter.showAllUsers().size() > 0, "Number of users is empty");
    }

    @Test
    void showAllFollowers() throws IOException, ParseException {
        assertTrue(Twitter.showAllFollowers().size() > 0, "Number of followers is empty");
    }

    @Test
    void showUserTimeline() {
        assertTrue(Twitter.showUserTimeline().size() > 0, "Number of user is empty");
    }

    @Test
    void makeTweet() {
        ArrayList<Object> exampleTweet = Twitter.makeTweet("Hello", new ArrayList<String>(Arrays.asList("#Check1", "#Check2", "#Check3")));
        ArrayList<String> hashes = new ArrayList<String>(Arrays.asList("#Check1", "#Check2", "#Check3"));

        assertTrue(exampleTweet.get(0) == "Hello", exampleTweet.get(0).toString());
        assertTrue(exampleTweet.get(1).equals(hashes), exampleTweet.get(1).toString());

    }

    @Test
    void searchTweet() {
        assertTrue(Twitter.searchTweet("Hello World").size() >= 1, "Search function is broken/Database is missing");
    }
}