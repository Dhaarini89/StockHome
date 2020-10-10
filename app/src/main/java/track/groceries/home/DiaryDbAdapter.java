package track.groceries.home;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDbAdapter {
    myDiaryDbHelper myhelper;
    private int flag;
    private Integer redflag;
    public DiaryDbAdapter(Context context)
    {
        myhelper = new myDiaryDbHelper(context);
    }

    public long insertData(String name, Integer red,Integer yellow,Integer green)
    {
        /* Insert a new Product into the Diary Table */

        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDiaryDbHelper.Product_NAME, name);
        contentValues.put(myDiaryDbHelper.Red_Signal, red);
        contentValues.put(myDiaryDbHelper.Yellow_Signal, yellow);
        contentValues.put(myDiaryDbHelper.Green_Signal, green);
        long id = dbb.insert(myDiaryDbHelper.TABLE_NAME, null , contentValues);
        dbb.close();
        return id;
    }

    public  int deleteProduct(String value) {

        /* Delete a Product from Table */

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String deleteproduct = "Product_NAME=?";
        String[] args = {value.trim()};
        int abc = db.delete(myDiaryDbHelper.TABLE_NAME, deleteproduct, args);
        db.close();
        return abc;
    }

    public String getData()
    {
        /* Retrieve List of Products */

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDiaryDbHelper.UID, myDiaryDbHelper.Product_NAME, myDiaryDbHelper.Red_Signal, myDiaryDbHelper.Yellow_Signal, myDiaryDbHelper.Green_Signal};
        Cursor cursor =db.query(myDiaryDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDiaryDbHelper.UID));
            String Product_Name =cursor.getString(cursor.getColumnIndex(myDiaryDbHelper.Product_NAME));
            Integer  Red_Signal =cursor.getInt(cursor.getColumnIndex(myDiaryDbHelper.Red_Signal));
            Integer  Yellow_Signal =cursor.getInt(cursor.getColumnIndex(myDiaryDbHelper.Yellow_Signal));
            Integer  Green_Signal =cursor.getInt(cursor.getColumnIndex(myDiaryDbHelper.Green_Signal));
            buffer.append(cid+ "   " + Product_Name + "   " + Red_Signal +"" + Yellow_Signal+ "" +Green_Signal+" \n");
        }
        db.close();
        return buffer.toString();
    }

    public String getRedData() {

        /* Retrieve out of stock products from the table */

        String sql = "select "+myDiaryDbHelper.Product_NAME+" from " + myDiaryDbHelper.TABLE_NAME +" where "+ myDiaryDbHelper.Red_Signal + "<25";
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<Model> storedata = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToFirst()){
            do{
                String Product_Name = cursor.getString(0);
                buffer.append( Product_Name + " \n");
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return buffer.toString();
    }

    public String getYellowData() {

        /* Retrieve the Items that are in Use */

        String sql = "select "+myDiaryDbHelper.Product_NAME+" from " + myDiaryDbHelper.TABLE_NAME +" where "+ myDiaryDbHelper.Red_Signal + ">=25 and " + myDiaryDbHelper.Red_Signal + "<=75";
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<Model> storedata = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToFirst()){
            do{
                String Product_Name = cursor.getString(0);
                buffer.append( Product_Name + " \n");
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return buffer.toString();
    }

    public String getGreenData() {

        /* Retrieve the Items that are in Use */

        String sql = "select "+myDiaryDbHelper.Product_NAME+" from " + myDiaryDbHelper.TABLE_NAME +" where "+ myDiaryDbHelper.Red_Signal + ">=75";
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<Model> storedata = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.moveToFirst()){
            do{
                String Product_Name = cursor.getString(0);
                buffer.append( Product_Name + " \n");
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return buffer.toString();
    }

    public Integer getRedFlag(String pdct) {

        /* Retrieve the threshold value of the Product */

        String[] column = { myDiaryDbHelper.Red_Signal};
        String selection = myDiaryDbHelper.Product_NAME + " = ?";
        String[] selectionArgs = {pdct.trim()};
        SQLiteDatabase db = myhelper.getReadableDatabase();
        Cursor cursor = db.query(myDiaryDbHelper.TABLE_NAME, column, selection, selectionArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
             redflag = Integer.parseInt(cursor.getString(0));
        }
        db.close();
        return redflag;
    }

    public  int checkproduct(String uname)
    {

        /* Check whether a Product is available in the Table */

        String[] column = { myDiaryDbHelper.Product_NAME};
        String selection = myDiaryDbHelper.Product_NAME + " = ?";
        String[] selectionArgs = {uname.trim()};
        SQLiteDatabase db = myhelper.getReadableDatabase();
        Cursor cursor = db.query(myDiaryDbHelper.TABLE_NAME, column, selection, selectionArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String Product_Name = cursor.getString(0);
            if (Product_Name.isEmpty()) {
                flag = 0;
            } else {
                flag = 1;
            }
        }
        db.close();
        return flag;
    }

    public ArrayList<Model> getdata(){

        /* Obtain all records from the table */

        String sql = "select * from " + myDiaryDbHelper.TABLE_NAME;
        SQLiteDatabase db = myhelper.getReadableDatabase();
        ArrayList<Model> storedata = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String Product_Name = cursor.getString(1);
                int Red_Signal = Integer.parseInt(cursor.getString(2));
                int Yellow_Signal = Integer.parseInt(cursor.getString(3));
                int Green_Signal = Integer.parseInt(cursor.getString(4));
                storedata.add(new Model(Product_Name,Red_Signal,Yellow_Signal,Green_Signal));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return storedata;
    }

    public  int trunc()
    {
        /* Delete all entries from the table */

        SQLiteDatabase db = myhelper.getWritableDatabase();
        int count =db.delete(myDiaryDbHelper.TABLE_NAME , null,null);
        db.close();
        return  count;
    }

    public int updatedata(String Product_Name ,Integer Red_Signal,Integer Yellow_Signal,Integer Green_Signal)
    {
        /* Update the threshold limit to 100 for a product */

        String data=Product_Name.trim();
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDiaryDbHelper.Red_Signal,Red_Signal);
        contentValues.put(myDiaryDbHelper.Yellow_Signal,Yellow_Signal);
        contentValues.put(myDiaryDbHelper.Green_Signal,Green_Signal);
        String[] whereArgs= {data};
        int count =db.update(myDiaryDbHelper.TABLE_NAME,contentValues, myDiaryDbHelper.Product_NAME+" = ?",whereArgs );
        db.close();
        return count;
    }

    static class myDiaryDbHelper extends SQLiteOpenHelper
    {
        /* Create and Delete table */

        private static final String DATABASE_NAME = "myDiary";    // Database Name
        private static final String TABLE_NAME = "Diary_List";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String Product_NAME = "Product_Name";    //Column II
        private static final String Red_Signal= "Red_Signal";    // Column III
        private static final String Yellow_Signal= "Yellow_Signal";    // Column III
        private static final String Green_Signal= "Green_Signal";    // Column IV
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Product_NAME+" VARCHAR(255) ,"+ Red_Signal+" INTEGER,"+Yellow_Signal+" INTEGER,"+Green_Signal+" INTEGER);";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDiaryDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }

    }
}