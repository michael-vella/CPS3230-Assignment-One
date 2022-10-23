package org.example;

public class Alert {
    // This will be used for the POST Json Object
    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;
    protected String postedBy = "c55bc56a-232c-46a4-9778-7f0d41690aa2";
    protected int priceInCents;

    public Alert() {}

    public Alert(int alertType, String heading, String description, String url, String imageUrl, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.priceInCents = priceInCents;
    }

    public void setAlertType(int alertType){
        this.alertType = alertType;
    }

    public void setHeading(String heading){
        this.heading = heading;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public void setPriceInCents(int priceInCents){
        this.priceInCents = priceInCents;
    }

    public int getAlertType(){
        return this.alertType;
    }

    public String getHeading() {
        return this.heading;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.url;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getPostedBy() {
        return this.postedBy;
    }

    public long getPriceInCents() {
        return this.priceInCents;
    }
}
