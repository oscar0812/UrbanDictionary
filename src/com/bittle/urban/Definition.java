package com.bittle.urban;

import com.google.gson.JsonObject;

public class Definition {
    private String def = "";
    private String permaLink = "";
    private long thumbsUp = 0;
    private String author = "";
    private String word = "";
    private long defid = 0;
    private String currentVote = "";
    private String example = "";
    private long thumbsDown = 0;

    Definition(JsonObject obj){
        def = obj.get("definition").getAsString();
        permaLink = obj.get("permalink").getAsString();
        thumbsUp =  strToL(obj.get("thumbs_up").getAsString());
        author = obj.get("author").getAsString();
        word = obj.get("word").getAsString();
        defid = strToL(obj.get("defid").getAsString());
        currentVote = obj.get("current_vote").getAsString();
        example = obj.get("example").getAsString();
        thumbsDown = strToL(obj.get("thumbs_down").getAsString());
    }

    Definition(){
        // empty for when there's no definitions
    }

    private long strToL(String l){
        try{
            return Long.parseLong(l);
        }catch (Exception e){
            return 0;
        }
    }

    public String getDefinition(){
        return def;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public long getThumbsUp() {
        return thumbsUp;
    }

    public String getAuthor() {
        return author;
    }

    public String getWord() {
        return word;
    }

    public long getDefid() {
        return defid;
    }

    public String getCurrentVote() {
        return currentVote;
    }

    public String getExample() {
        return example;
    }

    public long getThumbsDown() {
        return thumbsDown;
    }

    @Override
    public String toString() {
        return word+"\n\n"+def+"\n\n"+example;
    }
}
