package com.example.danielmaina.instanews;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by danielmaina on 10/6/16.
 */
public class ReadRss extends AsyncTask<Void, Void, Void> {

    private ArrayList<FeedItem>feedItems=new ArrayList<>();


    //getter class for the feedItems ArrayList to enable it to be accessed by the ArrayAdapter in the mainActivity
    public ArrayList<FeedItem> getFeedItems() {
        return feedItems;
    }


    Context context;
    //String address = "http://ntv.nation.co.ke/2720202-2720202-view-asFeed-a4x2dfz/index.xml";
    String address = "http://www.sciencemag.org/rss/news_current.xml";
    ProgressDialog progressDialog;
    URL url;

    //default constructor
    public ReadRss(){

    }


    //constructor for the class
    public ReadRss(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }



    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Getdata());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item=new FeedItem();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        if (cureent.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(cureent.getTextContent());
                            item.getTitle();
                        }else if (cureent.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(cureent.getTextContent());
                            item.getDescription();
                        }else if (cureent.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(cureent.getTextContent());
                            item.getPubDate();
                        }else if (cureent.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(cureent.getTextContent());
                            item.getLink();
                        }
                    }
                    feedItems.add(item);
                    Log.d("theItem", String.valueOf(item));
                    Log.d("itemTitle", item.getTitle());
                    Log.d("itemDescription",item.getDescription());
                    Log.d("itemPubDate",item.getPubDate());
                    Log.d("itemLink",item.getLink());


                }
            }
        }
    }




    public Document Getdata() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
