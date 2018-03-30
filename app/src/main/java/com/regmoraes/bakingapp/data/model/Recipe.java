package com.regmoraes.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

	@Expose
	@SerializedName("image")
	private String image;

	@Expose
	@SerializedName("servings")
	private int servings;

	@Expose
	@SerializedName("name")
	private String name;

	@Expose
	@SerializedName("ingredients")
	private ArrayList<Ingredient> ingredients;

	@Expose
	@SerializedName("id")
	private int id;

	@Expose
	@SerializedName("steps")
	private ArrayList<Step> steps;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public String getName(){
		return name;
	}

	public List<Ingredient> getIngredients(){
		return ingredients;
	}

	public int getId(){
		return id;
	}

	public ArrayList<Step> getSteps(){
		return steps;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.image);
		dest.writeInt(this.servings);
		dest.writeString(this.name);
		dest.writeTypedList(this.ingredients);
		dest.writeInt(this.id);
		dest.writeTypedList(this.steps);
	}

	public Recipe() {
	}

	protected Recipe(Parcel in) {
		this.image = in.readString();
		this.servings = in.readInt();
		this.name = in.readString();
		this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
		this.id = in.readInt();
		this.steps = in.createTypedArrayList(Step.CREATOR);
	}

	public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
		@Override
		public Recipe createFromParcel(Parcel source) {
			return new Recipe(source);
		}

		@Override
		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};
}