package ua.dashan.caloriescounter.Database;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DatabaseModel {
    private String foodName;
    private int calories;
  // private int foodPhoto;

  private byte[] image;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

   public byte[] get_image() {return image;}

    public Bitmap getImage() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void set_image(byte[] _image) {
        this.image = _image;
    }

  //  public int getFoodPhoto() {
    //    return foodPhoto;
   // }

  //  public void setFoodPhoto(int foodPhoto) {
   //     this.foodPhoto = foodPhoto;
    //}
}
