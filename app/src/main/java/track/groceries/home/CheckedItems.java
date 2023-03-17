package track.groceries.home;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckedItems implements Parcelable{

    private String datalist;
    private int count;
    private int id;

    public CheckedItems( String list,Integer id,Integer count)
    {

        this.datalist = list;
        this.id = id;
        this.count = count;

    }
    public int getId() {
        return id;
    }
    public String getcount() {
        String test =String.valueOf(count);
        return test;}

    public String getdataList() {
        return datalist;
    }
    private CheckedItems(Parcel in) {
        datalist = in.readString();
    }
    public static final Creator<CheckedItems> CREATOR = new Creator<CheckedItems>() {
        @Override
        public CheckedItems createFromParcel(Parcel in) {
            return new CheckedItems(in);
        }

        @Override
        public CheckedItems[] newArray(int size) {
            return new CheckedItems[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(datalist);
    }
}