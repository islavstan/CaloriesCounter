package ua.dashan.caloriescounter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelpher extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="calories";
    private static final int DATABASE_VERSION =7;
    private static final String CALORIES_COUNT_TABLE = "CALCOUNT";
    private static final String FOOD_TABLE = "food";
    private static final String CREATE_FOOD_TABLE = "create table "+FOOD_TABLE +"(name TEXT primary key , calories INTEGER , image BLOB)";
    private static final String CREATE_CALORIES_COUNT_TABLE= "create table "+CALORIES_COUNT_TABLE +"(date TEXT primary key , calcount INTEGER )";
    Context context;
    int calories;
    int calCount;

    public DatabaseHelpher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_CALORIES_COUNT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);

        // Create tables again
        onCreate(db);
    }


    public void insertCaloriesCountIntoDB(String date,int caloriesCount) {
        SQLiteDatabase db = this.getWritableDatabase();
       String query ="SELECT * FROM CALCOUNT WHERE date = ?";
        Cursor cursor =db.rawQuery(query,new String[] { date });
        if(cursor.getCount()==0){
          //  String insert ="INSERT INTO CALCOUNT (date,calcount) VALUES ( " + date + " , "+ caloriesCount + " )";
           // db.execSQL(insert);
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("calcount", caloriesCount);
        db.insert(CALORIES_COUNT_TABLE, null, values);
            cursor.close();
            db.close();
        }else{
            String update ="UPDATE CALCOUNT SET calcount = calcount + "+caloriesCount+" WHERE date = '"+date+"'";
            db.execSQL(update);
            cursor.close();
            db.close();
        }


       /* String query = "INSERT INTO CALCOUNT (date,calcount) VALUES ( " + date + " , "
                + caloriesCount + " ) ON DUPLICATE KEY UPDATE calcount=calcount + " + caloriesCount + " ;";*/
      //  String query = "INSERT OR REPLACE INTO CALCOUNT (date,calcount) VALUES ( "+date+ " ,(SELECT CASE WHEN exists" +
            //    "(SELECT calcount FROM CALCOUNT WHERE date = " +date+" ) THEN 10 ELSE "+caloriesCount+" END ) )";
       /* String query = "REPLACE INTO CALCOUNT (date,calcount) VALUES ( " + date + " , "
                + caloriesCount + " )";*/
       /* db.execSQL(query);
        db.close();
*/

      // Cursor cursor=db.rawQuery(query,null);
       // UPDATE table SET c=c+1 WHERE a=1;
      /*  Log.d("stas",Integer.toString(cursor.getCount()));
         if(cursor.moveToFirst()) {
             String strSQL = "UPDATE CALCOUNT SET calcount = calcount + " + caloriesCount +" WHERE date = "+ date;
             db.execSQL(strSQL);
             cursor.close();
             db.close();

         }
        else  {
             ContentValues values = new ContentValues();
             values.put("date", date);
             values.put("calcount", caloriesCount);
             db.insert(CALORIES_COUNT_TABLE, null, values);
             cursor.close();
             db.close();
        }*/}




    /* Insert into database*/
    public void insertIntoDB(String name,int calories,byte[] image /*int image*/ ){


        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("calories", calories);
        values.put("image", image);

        // 3. insert
        db.insert(FOOD_TABLE, null, values);
        // 4. close
        db.close();
        Toast.makeText(context, "insert value", Toast.LENGTH_LONG);

    }
    /* Retrive  data from database */
    public List<DatabaseModel> getDataFromDB(){
        List<DatabaseModel> modelList = new ArrayList<DatabaseModel>();
        String query = "select * from "+FOOD_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                DatabaseModel model = new DatabaseModel();
                model.setFoodName(cursor.getString(0));
                model.setCalories(cursor.getInt(1));
                model.set_image(cursor.getBlob(2));
              //  model.setFoodPhoto(cursor.getInt(2));

                modelList.add(model);
            }while (cursor.moveToNext());
        }

        return modelList;
    }


    //delete a row from database

   public void deleteARow(String name){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(FOOD_TABLE, "name" + " = ?", new String[] { name });
        db.close();
    }

    //getting food calories
    public int getCaloriesFromDB(String name){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT calories FROM food where name = ?", new String[]{name});
        while (cursor.moveToNext()) {
             calories = cursor.getInt(0);
        }
        return calories;
    }
    public int getCaloriesCount(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT calcount FROM CALCOUNT where date = ?", new String[]{date});
        while (cursor.moveToNext()) {
            calCount = cursor.getInt(0);
        }
        return calCount;
    }
    public int getCaloriesCountForCalendar(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT calcount FROM CALCOUNT where date = ?", new String[]{date});
        if (cursor.moveToNext()) {
            calCount = cursor.getInt(0);
        }
        else {calCount=0;}
        return calCount;
    }
    }

