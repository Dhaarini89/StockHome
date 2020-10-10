package track.groceries.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.graphics.Paint;

import track.groceries.home.R;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private Context mContext;
    private List<Show> mShowList;
    private static int[] selectedIds;
    private int counter;
    private FruitDbAdapter mFruitDatabase,Fruithelper;
    private ToiletDbAdapter Toilethelper;
    private DiaryDbAdapter Diaryhelper;
    private PulseDbAdapter Pulsehelper;
    private SpiceDbAdapter Spicehelper;
    private ExtraDb ExtraDb;
      private OilsDbAdapter Oilshelper;
    private StationDbAdapter Stationhelper;
    private CerealsDbAdapter Cerealshelper;


    public ShowAdapter(Context context, List<Show> showList) {
        this.mContext = context;
        this.mShowList = showList;
        selectedIds=new int[500];
    }

    class ShowViewHolder extends RecyclerView.ViewHolder  {
        private TextView mNavigatorNameTextView,TextNumber;
        private FrameLayout rootView;
        private CheckBox checkbox;
        private View myview;
        public ShowViewHolder(final View itemView) {
            super(itemView);
            myview=itemView;

            mNavigatorNameTextView = itemView.findViewById(R.id.txtTitle);
            rootView = itemView.findViewById(R.id.constraint);
        }
    }
    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_row, parent, false);
        counter=0;
        for (int i=0;i<=10;i++) {
            selectedIds[i] = 50;
        }
        return new ShowViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final ShowViewHolder holder, final int position) {


        if (position == mShowList.size())
        {

        }
        else
            {

            holder.mNavigatorNameTextView.setText(mShowList.get(position).getdataList());

            int id = mShowList.get(position).getId();
            holder.myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.mNavigatorNameTextView.getPaint().isStrikeThruText()) {
                        holder.mNavigatorNameTextView.setPaintFlags(holder.mNavigatorNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        selectedIds[counter] = position;
                        counter = counter + 1;
                    } else {
                        holder.mNavigatorNameTextView.setPaintFlags(holder.mNavigatorNameTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        counter = counter - 1;
                        selectedIds[counter] = 50;
                    }
                }
            });
            holder.mNavigatorNameTextView.setPaintFlags(holder.mNavigatorNameTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);

            }
    }

    public void display()
    {
          int change;
        change=0;
        for (int j=0;j<counter;j++) {
            for (int i = j + 1; i < counter; i++) {
                if (selectedIds[i] > selectedIds[j]) {
                    change = selectedIds[i];
                    selectedIds[i] = selectedIds[j];
                    selectedIds[j] = change;


                }

            }
        }
        for (int k=0;k<counter;k++) {
            String product =mShowList.get(selectedIds[k]).getdataList();
                removeItem(selectedIds[k]);

                ExtraDb = new ExtraDb(mContext);
                int eid = ExtraDb.checkproduct(product);
                if (eid != 0 )
                {
                    long eeid = ExtraDb.deleteProduct(product);

                }
                Fruithelper = new FruitDbAdapter(mContext);
                int fid = Fruithelper.checkproduct(product);
                if (fid != 0) {
                    long ffid = Fruithelper.updatedata(product, 100, 0, 0);
                }
                Pulsehelper = new PulseDbAdapter(mContext);
                int ppid = Pulsehelper.checkproduct(product);
                if (ppid != 0) {
                    long pid = Pulsehelper.updatedata(product, 100, 0, 1);
                }
                Toilethelper = new ToiletDbAdapter(mContext);
                int ttid = Toilethelper.checkproduct(product);
                if (ttid != 0) {
                    long tid = Toilethelper.updatedata(product, 100, 0, 1);
                }
                Diaryhelper = new DiaryDbAdapter(mContext);
                int dcheck = Diaryhelper.checkproduct(product);
                if (dcheck != 0) {

                    long did = Diaryhelper.updatedata(product, 100, 0, 0);
                }
                Spicehelper = new SpiceDbAdapter(mContext);
                int scheck = Spicehelper.checkproduct(product);
                if (scheck != 0) {
                    long sid = Spicehelper.updatedata(product, 100, 0, 1);
                }

            Cerealshelper = new CerealsDbAdapter(mContext);
            int cecheck = Cerealshelper.checkproduct(product);
            if (cecheck != 0) {
                long ceid = Cerealshelper.updatedata(product, 100, 0, 1);
            }

            Stationhelper = new StationDbAdapter(mContext);
            int stcheck = Stationhelper.checkproduct(product);
            if (stcheck != 0) {
                long stid = Stationhelper.updatedata(product, 100, 0, 1);
            }

            Oilshelper = new OilsDbAdapter(mContext);
            int oicheck = Oilshelper.checkproduct(product);
            if (oicheck != 0) {
                long oiid = Oilshelper.updatedata(product, 100, 0, 1);
            }
        }
        counter = 0;
    }
    @Override
    public int getItemCount() {
        return mShowList.size();
    }
    public Show getItem(int position){
        return mShowList.get(position);
    }

    public void removeItem(int position) {
        mShowList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(0,mShowList.size());
    }

    public List<Show> getData()
   {
       return mShowList;
   }
}

