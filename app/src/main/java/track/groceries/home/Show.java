package track.groceries.home;

import android.os.Parcel;
import android.os.Parcelable;

public class Show implements Parcelable {


    private String datalist;
    private String count;
    private int id;

    public Show( String list,Integer id,String count)
    {

        this.datalist = list;
        this.id = id;
        this.count = count;

    }
    public int getId() {
        return id;
    }
    public String getcount() {
        
        return count;}

    public String getdataList() {
        return datalist;
    }
    private Show(Parcel in) {
        datalist = in.readString();
    }
    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
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