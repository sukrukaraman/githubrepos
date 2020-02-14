package com.ing.githubrepo.service.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ing.githubrepo.service.base.BaseResponseModel;

import java.util.List;

/**
 * Created by karamans on 13.02.2020.
 */
public class Repo extends BaseResponseModel implements Parcelable
{
    @SerializedName("full_name")
    private String fullName;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("node_id")
    private String nodeId;

    @SerializedName("owner")
    private Owner owner;

    @SerializedName("stargazers_count")
    private String starCount;

    @SerializedName("description")
    private String description;

    @SerializedName("open_issues_count")
    private String openIssuesCount;

    protected Repo(Parcel in) {
        fullName = in.readString();
        name = in.readString();
        id = in.readString();
        nodeId = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
        starCount = in.readString();
        description = in.readString();
        openIssuesCount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(nodeId);
        dest.writeParcelable(owner, flags);
        dest.writeString(starCount);
        dest.writeString(description);
        dest.writeString(openIssuesCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Repo> CREATOR = new Creator<Repo>() {
        @Override
        public Repo createFromParcel(Parcel in) {
            return new Repo(in);
        }

        @Override
        public Repo[] newArray(int size) {
            return new Repo[size];
        }
    };

    public String getFullName ()
    {
        return fullName;
    }

    public String getName ()
    {
        return name;
    }

    public String getId ()
    {
        return id;
    }


    public String getNodeId ()
    {
        return nodeId;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getStarCount() {
        return starCount;
    }

    public String getDescription() {
        return description;
    }

    public String getOpenIssuesCount() {
        return openIssuesCount;
    }

}
