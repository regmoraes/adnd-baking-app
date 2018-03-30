package com.regmoraes.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

	@Expose
	@SerializedName("quantity")
	private float quantity;

	@Expose
	@SerializedName("measure")
	private String measure;

	@Expose
	@SerializedName("ingredient")
	private String name;

	public void setQuantity(float quantity){
		this.quantity = quantity;
	}

	public float getQuantity(){
		return quantity;
	}

	public void setMeasure(String measure){
		this.measure = measure;
	}

	public String getMeasure(){
		return measure;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(this.quantity);
		dest.writeString(this.measure);
		dest.writeString(this.name);
	}

	public Ingredient() {
	}

	protected Ingredient(Parcel in) {
		this.quantity = in.readFloat();
		this.measure = in.readString();
		this.name = in.readString();
	}

	public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
		@Override
		public Ingredient createFromParcel(Parcel source) {
			return new Ingredient(source);
		}

		@Override
		public Ingredient[] newArray(int size) {
			return new Ingredient[size];
		}
	};
}