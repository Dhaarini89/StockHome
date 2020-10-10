package track.groceries.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import track.groceries.home.R;

public class ListLoadActivity extends AppCompatActivity {
    FruitDbAdapter Fruithelper;
    PulseDbAdapter Pulsehelper;
    ToiletDbAdapter Toilethelper;
    DiaryDbAdapter Diaryhelper;
    SpiceDbAdapter Spicehelper;
    OilsDbAdapter Oilshelper;
    CerealsDbAdapter Cerealshelper;
    StationDbAdapter Stationhelper;

    private long idd;
    private String[] diarylist = new String[]{"Cheese","Cottage cheese","Butter","Milk","Ghee","Curd","Egg"};
    private String[] fruitlist = new String[]{"Carrot","Spinach","Apples", "Oranges", "Potatoes", "Tomatoes","Grapes","Onions","Garlic","Banana","Sweet Potato","Beans","Ginger"};
    private String[] pulselist = new String[]{"Cornflour","Cow peas","Gram flour","Walnuts","Bengal gram","Moong dhal","Cashews","Drygrapes","Almond","Mung bean","Toor dhal","Urid dhal","White channa","Black channa"};
    private String[] toiletries = new String[]{"Dishwasher","Soap", "Handwash", "Sanitizer", "Diaper", "Sanitary napkin", "Shampoo", "Tissue paper", "Toilet paper", "Hair oil", "Washing powder","Shaving cream"};
    private String[] spicelist = new String[]{"Baking powder","Baking Soda","Chilli powder", "Salt", "Sugar", "Mustard seeds","Turmeric powder", "Garam masala", "Cumin powder", "Cumin seeds", "Coriander powder", "Biriyani masala", "Chat masala","Channamasala powder"};
    private String[] oillist = new String[]{"Olive Oil","Cocunut Oil"};
    private String[] cereals = new String[]{"Bread","Muesli","Idly rice","Rice(பொண்ணி)","Rice(Basmati)"};
    private String[] station = new String[]{"Pencil","Pen","Paper","File","Eraser"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_load);
        Fruithelper = new FruitDbAdapter(this);
        Pulsehelper = new PulseDbAdapter(this);
        Toilethelper = new ToiletDbAdapter(this);
        Diaryhelper = new DiaryDbAdapter(this);
        Spicehelper = new SpiceDbAdapter(this);
        Oilshelper = new OilsDbAdapter(this);
        Cerealshelper= new CerealsDbAdapter(this);
        Stationhelper = new StationDbAdapter(this);

        idd = Oilshelper.trunc();
        for (int i = 0; i < oillist.length; i++) {

            long id = Oilshelper.insertData(oillist[i], 100, 0, 1);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");

            }
        }

        idd = Cerealshelper.trunc();
        for (int i = 0; i < cereals.length; i++) {

            long id = Cerealshelper.insertData(cereals[i], 100, 0, 1);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");

            }
        }

        idd = Stationhelper.trunc();
        for (int i = 0; i < station.length; i++) {

            long id = Stationhelper.insertData(station[i], 100, 0, 1);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");

            }
        }

         idd = Spicehelper.trunc();
        for (int i = 0; i < spicelist.length; i++) {

            long id = Spicehelper.insertData(spicelist[i], 100, 0, 1);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");

            }
        }
         idd = Fruithelper.trunc();
        for (int i = 0; i < fruitlist.length; i++) {

            long id = Fruithelper.insertData(fruitlist[i], 100, 0, 1);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");

            }
        }
         idd = Pulsehelper.trunc();
        for (int i = 0; i < pulselist.length; i++) {

            long id = Pulsehelper.insertData(pulselist[i], 100, 0, 100);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");

            }
        }
         idd = Toilethelper.trunc();
        for (int i = 0; i < toiletries.length; i++) {

            long id = Toilethelper.insertData(toiletries[i], 100, 0, 1);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");
            }
        }
         idd = Diaryhelper.trunc();
        for (int i = 0; i < diarylist.length; i++) {

            long id = Diaryhelper.insertData(diarylist[i], 100, 0, 100);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Unsuccessful");
            }
        }

        startActivity(new Intent(ListLoadActivity.this, MainActivity.class));

    }
}
