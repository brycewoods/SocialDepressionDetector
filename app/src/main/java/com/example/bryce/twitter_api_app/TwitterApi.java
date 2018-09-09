package com.example.bryce.twitter_api_app;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
*   Description: Api that accesses twitter feeds.
* */
public class TwitterApi implements DataGatherer {

    // Fields
    private HashMap<String,String> m_accounts;
    private ArrayList<String> m_ScannedID;

    public TwitterApi()
    {
        m_accounts = new HashMap<String,String>();
        m_ScannedID = new ArrayList<String>();
    }

    public TwitterApi(HashMap<String,String> accountHandles)
    {
        m_accounts = accountHandles;
        m_ScannedID = new ArrayList<String>();


    }

    public TwitterApi(HashMap<String,String> accountHandles , ArrayList<String> scanned)
    {
        m_accounts = accountHandles;
        m_ScannedID = scanned;
    }

    public ArrayList<String> RetrieveData()
    {

        ArrayList<String> foundTweets = new ArrayList<String>();

        for(String userHandler : m_accounts.keySet())
        {
            foundTweets.addAll(RetrieveTweets(userHandler));
        }

        return foundTweets;
    }

    public void AddAccount(String userHandle)
    {
        m_accounts.put(userHandle , "");
    }

    /*
    *
    * Description: Retrieves Tweets from a designated userHandle. Returns the time stamp e.g. 4h or 23Aug
    * if > then a day. Also returns the actual tweet.
    * Input: Target handle to retrieve data from.
    * Return: HashMap<String,String>.
    * */
    private ArrayList<String> RetrieveTweets(String userHandle)
    {
        ArrayList<String> foundTweets = new ArrayList<String>();

        try
        {
            URL selectedUrl = new URL("https://mobile.twitter.com/" + userHandle);
            HttpURLConnection connection = (HttpURLConnection) selectedUrl.openConnection();
            connection.setRequestMethod("GET");

            InputStream input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            StringBuffer response = new StringBuffer();
            String line;

            while((line = reader.readLine()) != null)
            {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            Document parsedDoc = Jsoup.parse(response.toString());

            Elements tweets = parsedDoc.getElementsByClass("tweet-text");
            Elements timeStamps = parsedDoc.getElementsByClass("timestamp");
            Elements idElements = parsedDoc.getElementsByAttributeValueContaining("name" , "tweet_");

            for(int i = 0; i< idElements.size() ; i++)
            {
                if(!SearchForID(ParseID(idElements.get(i).attr("name"))))
                {
                    Log.d("SEARCH TEST" , "NOT FOUND: SCANNING");
                    String cleanedTweet = CleanTweet(tweets.get(i).text());
                    if(!cleanedTweet.equals(""))
                    {
                        foundTweets.add(cleanedTweet);
                    }

                    m_ScannedID.add(ParseID(idElements.get(i).attr("name")));

                }
                else
                {
                    Log.d("SEARCH TEST" , "FOUND : IGNORING");
                }

            }
        }
        catch(IOException e)
        {
            Log.e("RETRIEVE TWEET", "Error: cannot get url");
        }

        return foundTweets;

    }

    /*
    *
    * Description: Cleans tweet of all non-alphanumeric characters.
    * Input: Tweet to be cleaned.
    * Return: Cleaned Tweet.
    *
    * */
    private String CleanTweet(String input)
    {
        String cleaned = "";

        cleaned = input.replaceAll("[^A-Za-z0-9 ]" ,"");

        return cleaned;
    }

    /*
    *
    * Description: Cleans the id tags and ruturns clean ids.
    * Return: String representing id.
    *
    * */
    private String ParseID(String inputId)
    {
        String id = "";

        int idStart = inputId.indexOf('_');

        //Log.d("Start index" , Integer.toString(idStart));

        id = inputId.substring(idStart+1 , inputId.length());

        return id;

    }

    /*
    *
    * Description: Searches list for id.
    * Return: Boolean representing if id is found.
    *
    * */
    private boolean SearchForID(String inputID)
    {
        for(String id: m_ScannedID)
        {
            if(inputID.equals(id))
            {
                return true;
            }
        }

        return false;
    }

}
