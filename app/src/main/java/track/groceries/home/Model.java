package track.groceries.home;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {

    private int number,red,yellow,green;
    private String fruit;;
    private int optionlist;

    public Model( String fruit,int red,int yellow,int green)
    {

        this.fruit = fruit;
        this.red=red;
        this.yellow=yellow;
        this.green=green;
    }

    public String getFruit() {
        return fruit;
    }

    public int getRed()
    {
        return red;
    }
    public int getYellow()
    {
        return yellow;
    }
    public int getGreen()
    {
        return green;
    }
    public void setRed(int red){this.red = red; }
    public void setYellow(int yellow){this.yellow = yellow;}
    public void setGreen(int green){this.green = green;}

    private Model(Parcel in) {

        red = in.readInt();
        yellow = in.readInt();
        green = in.readInt();
        fruit = in.readString();
    }
    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(red);
        parcel.writeInt(yellow);
        parcel.writeInt(green);
        parcel.writeString(fruit);
    }
}