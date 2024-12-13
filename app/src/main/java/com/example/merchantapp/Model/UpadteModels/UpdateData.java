package com.example.merchantapp.Model.UpadteModels;

public class UpdateData
{
    private String upsertedId;

    private String upsertedCount;

    private String acknowledged;

    private String modifiedCount;

    private String matchedCount;

    public String getUpsertedId ()
    {
        return upsertedId;
    }

    public void setUpsertedId (String upsertedId)
    {
        this.upsertedId = upsertedId;
    }

    public String getUpsertedCount ()
    {
        return upsertedCount;
    }

    public void setUpsertedCount (String upsertedCount)
    {
        this.upsertedCount = upsertedCount;
    }

    public String getAcknowledged ()
    {
        return acknowledged;
    }

    public void setAcknowledged (String acknowledged)
    {
        this.acknowledged = acknowledged;
    }

    public String getModifiedCount ()
    {
        return modifiedCount;
    }

    public void setModifiedCount (String modifiedCount)
    {
        this.modifiedCount = modifiedCount;
    }

    public String getMatchedCount ()
    {
        return matchedCount;
    }

    public void setMatchedCount (String matchedCount)
    {
        this.matchedCount = matchedCount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [upsertedId = "+upsertedId+", upsertedCount = "+upsertedCount+", acknowledged = "+acknowledged+", modifiedCount = "+modifiedCount+", matchedCount = "+matchedCount+"]";
    }
}