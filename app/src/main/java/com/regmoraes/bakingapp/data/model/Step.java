package com.regmoraes.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {

	@Expose
	@SerializedName("videoURL")
	private String videoURL;

	@Expose
	@SerializedName("description")
	private String description;

	@Expose
	@SerializedName("id")
	private int id;

	@Expose
	@SerializedName("shortDescription")
	private String shortDescription;

	@Expose
	@SerializedName("thumbnailURL")
	private String thumbnailURL;

	public String getVideoURL(){
		return videoURL;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getShortDescription(){
		return shortDescription;
	}

	public String getThumbnailURL(){
		return thumbnailURL;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.videoURL);
		dest.writeString(this.description);
		dest.writeInt(this.id);
		dest.writeString(this.shortDescription);
		dest.writeString(this.thumbnailURL);
	}

	public Step() {
	}

	protected Step(Parcel in) {
		this.videoURL = in.readString();
		this.description = in.readString();
		this.id = in.readInt();
		this.shortDescription = in.readString();
		this.thumbnailURL = in.readString();
	}

	public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
		@Override
		public Step createFromParcel(Parcel source) {
			return new Step(source);
		}

		@Override
		public Step[] newArray(int size) {
			return new Step[size];
		}
	};
}