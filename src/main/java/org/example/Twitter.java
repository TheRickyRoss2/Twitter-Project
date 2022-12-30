package org.example;

import java.io.*;
import java.util.*;

import java.util.Scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.*;

class Tweet {
    String content;
    int userId;
    int tweetId;

    ArrayList<String> hashtags;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

}

class User {
    int userId;
    String name = "";
    String handle;
    String username;
    String password;
    ArrayList<Integer> tweets = new ArrayList<Integer>();
    ArrayList<Integer> followers = new ArrayList<Integer>();
    ArrayList<Integer> following = new ArrayList<Integer>();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Integer> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Integer> tweets) {
        this.tweets = tweets;
    }

    public ArrayList<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<Integer> followers) {
        this.followers = followers;
    }

    public ArrayList<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<Integer> following) {
        this.following = following;
    }

} 


public class Twitter
{
    public static int currentUserId = -1;
    public static int tweetCounter = 0;
    public static LinkedHashMap<String, Tweet> tweetDict = new LinkedHashMap<String, Tweet>();
    public static LinkedHashMap<String, User> userDict = new LinkedHashMap<String, User>();


    public static void main(String args[])
    {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Hello, Twitter");
        try {
            printMenu();
            parseJsonInfo("database.json");

            // If the login is correct, continue with Twitter
            String userName = myObj.nextLine();
            String passWord = myObj.nextLine();

            if(login(userName, passWord) != -1){
                start();
            }
        } catch (IOException | ParseException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public static void printMenu() throws java.io.FileNotFoundException {
        /*
        Function prints out the commands for the user's convenience
         */
        Scanner input = new Scanner(new File("Instructions.txt"));
        while (input.hasNextLine())
        {
           System.out.println(input.nextLine());
        }
    }

    public static int login(String username, String password){
        /*
        Input: The username && password
        Output: The username's userId or -1 if null and void
        */

        for(int i = 1; i < userDict.size()+1; i++) {
            String userId = Integer.toString(i);

            String tempUser = userDict.get(userId).username;
            String tempPass = userDict.get(userId).password;
            if( (username.equals(tempUser)) && (password.equals(tempPass))){
                currentUserId = i;

            }
        }
        if(userDict.get(Integer.toString(currentUserId)) != null) {
            System.out.println("Current user's ign: " + userDict.get(Integer.toString(currentUserId)).name);
        } else {
            System.out.println("Incorrect login credentials.");
        }

        return currentUserId;
    }

    public static void parseJsonInfo(String filename) throws IOException, ParseException {
        /*
        Function takes in a json file and fills out the local dictionaries inside Twitter
        */
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(filename));

        // Fill in the tweetDict
        tweetDict = objectMapper.readValue(jsonNode.get("tweets").traverse(), new TypeReference<LinkedHashMap<String, Tweet>>(){});
        // Make sure to update the tweet counter to the amount we have in the script
        tweetCounter = tweetDict.size();

        // Fill in the userDict
        userDict = objectMapper.readValue(jsonNode.get("users").traverse(), new TypeReference<LinkedHashMap<String, User>>(){});

    }
    public static void start() throws IOException, ParseException {
        /*
        Function starts Twitter's command line.
         */

        Scanner in = new Scanner(System.in);
        String s = " ";

        String searchVal = "automated text";

//        showAllTweets();
//        showAllUsers();
//        showAllFollowers();
//        showUserTimeline();
//        makeTweet("automated text", new ArrayList<String>(Arrays.asList("#test", "help")));
//        searchTweet(searchVal);

        while(s != "quit"){
            s = in.nextLine();
            if(s.toLowerCase().contains("entire timeline")){
                showAllTweets();

             } else if((s.toLowerCase().contains("show all users"))){
                 showAllUsers();

             } else if((s.toLowerCase().contains("show all my followers"))){
                 showAllFollowers();

             } else if(s.toLowerCase().contains("home timeline")) {
                 showUserTimeline();

             } else if(s.toLowerCase().contains("searching")) {
                 System.out.print("Search Input: ");
                 String t = in.nextLine();
                 searchTweet(t);

             } else if(s.toLowerCase().contains("tweeting")) {
                System.out.print("Text Body: ");
                String textBody = in.nextLine();

                ArrayList<String> hashtags = new ArrayList<String>();
                while(true){
                    System.out.println("Add hashtag: ");
                    String answer = in.nextLine();
                    hashtags.add(in.nextLine());

                    if (!answer.contains("#")){
                        System.out.println("End of Hash adding");
                        break;
                    }
                }
                makeTweet(textBody, hashtags);

            } else {
                s = "quit";
            }
        }
        in.close();
    }

    public static ArrayList<ArrayList<String>> showAllTweets(){
        /*
        Function prints all tweets located inside the json file.
        */

        ArrayList<ArrayList<String>> results = new ArrayList<>();

        System.out.println("// All Tweets: //");

        for(int i = 1; i < tweetDict.size()+1; i++) {
            ArrayList<String> temp = new ArrayList<>();
            String tweetId = Integer.toString(i);
            String userId = Integer.toString(tweetDict.get(tweetId).userId);

            System.out.print("Tweet Id: " + tweetId);
            System.out.print(" / User: " + userDict.get(userId).name);
            System.out.println(" / Content: " + tweetDict.get(tweetId).content);

            Collections.addAll(temp, tweetId, userDict.get(userId).name, tweetDict.get(tweetId).content);
            results.add(temp);
        }
        return results;
    }

     public static ArrayList<ArrayList<String>> showAllUsers(){
        /*
        Function prints the info of all users located inside the json file.
        */
         ArrayList<ArrayList<String>> results = new ArrayList<>();

         System.out.println("// All Users: //");

         for(int i = 1; i < userDict.size()+1; i++) {
             ArrayList<String> temp = new ArrayList<>();
             String userId = Integer.toString(i);

             System.out.print("User Id: " + userId);
             System.out.println(" / User: " + userDict.get(userId).name);

             Collections.addAll(temp, userId, userDict.get(userId).name);
             results.add(temp);
         }
         return results;
     }

     public static ArrayList<ArrayList<String>> showAllFollowers() throws IOException, ParseException {
        /*
        Function prints the information of all followers of the current user.
        */
         ArrayList<ArrayList<String>> results = new ArrayList<>();

         if(currentUserId ==-1) {
             System.out.println("Current user is null");
         } else{
             ArrayList<Integer> followers = userDict.get(Integer.toString(currentUserId)).followers;
             System.out.println("// Your Followers: // ");
             for(int i = 0; i < followers.size(); i++){
                 ArrayList<String> temp = new ArrayList<>();

                 String followerId = String.valueOf(followers.get(i));
                 System.out.println(userDict.get(followerId).name);

                 Collections.addAll(temp, followerId, userDict.get(followerId).name);
                 results.add(temp);
             }
         }
         return results;

     }

    // // actual functions
     public static ArrayList<ArrayList<String>> showUserTimeline(){
         /*
          * 1. Fetch all the tweets from Global Tweet Table/Redis for a particular user
          *     Which also includes retweets, save retweets as tweets with original tweet reference
          * 2. Display it on user timeline, order by date time
          *
          */
         ArrayList<ArrayList<String>> results = new ArrayList<>();

         int maxTweets = 10;

         System.out.println("// User's Timeline up to: " + maxTweets + " //");

         // Obtain the ids of the followers
         HashSet<String> relevantIds = new HashSet<String>();
         relevantIds.add(String.valueOf(currentUserId));
         ArrayList<Integer> following= userDict.get(String.valueOf(currentUserId)).following;
         for(int i = 0; i < following.size(); i++) {
             String followingId = String.valueOf(following.get(i));
             relevantIds.add(followingId);
         }

         // Obtain all posts from the current user and the followers that they are interested in
         int counter = 0;
         for(int j = 1; j < tweetDict.size()+1; j++) {
             if (counter == maxTweets){
                 break;
             }
             String tweetId = Integer.toString(j);
             String userId = Integer.toString(tweetDict.get(tweetId).userId);

             if (relevantIds.contains(userId)) {
                 ArrayList<String> temp = new ArrayList<>();

                 System.out.print("Tweet Id: " + tweetId);
                 System.out.print(" / User: " + userDict.get(userId).name);
                 System.out.println(" / Content: " + tweetDict.get(tweetId).content);
                 counter++;

                 Collections.addAll(temp, tweetId, userDict.get(tweetId).name, tweetDict.get(tweetId).content);
                 results.add(temp);
             }
         }

         return results;
     }

     public static ArrayList<Object> makeTweet(String userText, ArrayList<String> userHashtags){
        /*
        Input: User's text && user's hashtags
        Output: An array containing both the user's text and user's hashtags

        Function adds in the new tweet into the current json file.
        */

         ArrayList<Object> results = new ArrayList<>();
         Collections.addAll(results, userText, userHashtags);

         //Write to tweetDict
         System.out.println("// Write New Tweet //");

         tweetCounter++;
         ArrayList<String> newHashtags = userHashtags;

         Tweet newTweet = new Tweet();
         newTweet.setTweetId(tweetCounter);
         newTweet.setUserId(currentUserId);
         newTweet.setContent(userText);
         newTweet.setHashtags(newHashtags);


         tweetDict.put(String.valueOf(tweetCounter), newTweet);

         //Merge all the data back into the json file
         ObjectMapper mapper = new ObjectMapper();

         HashMap<String, Object> comboJson = new HashMap<String, Object>();
         comboJson.put("tweets", tweetDict);
         comboJson.put("users", userDict);

         // System.out.println(comboJson);
         try (FileWriter file = new FileWriter("database.json")) {
             //We can write any JSONArray or JSONObject instance to the file
             mapper.writeValue(new File("database.json"), comboJson);

         } catch (IOException e) {
             e.printStackTrace();
         }


         return results;
     }

     public static ArrayList<ArrayList<String>> searchTweet(String substring){
        /*
        Input: Search string
        Output: An array containing all tweets with this search string
        */

         ArrayList<ArrayList<String>> results = new ArrayList<>();

        System.out.println("// Tweets containing " + substring + " //");
        for(Map.Entry<String,Tweet> tweetEntry : tweetDict.entrySet()) {
            String tweetId = tweetEntry.getKey();
            Tweet tweetValue = tweetEntry.getValue();
            if (tweetValue.getContent().contains(substring)) {
                ArrayList<String> temp = new ArrayList<>();

                System.out.print("Tweet Id: " + tweetId);
                System.out.print(" / User: " + tweetValue.getUserId());
                System.out.println(" / Content: " + tweetValue.getContent());

                Collections.addAll(temp, tweetId, String.valueOf(tweetValue.getUserId()), tweetValue.getContent());
                results.add(temp);
            }
        }
        return results;
     }

}
