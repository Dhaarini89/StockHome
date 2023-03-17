package track.groceries.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import android.graphics.Paint;

import track.groceries.home.R;

public class CheckedAdapter extends RecyclerView.Adapter<CheckedAdapter.CheckedViewHolder> {

    private Context mContext;
    private List<CheckedItems> mCheckedItems;
    private static int[] selectedIds;
    private int counter;
    private ExtraDb ExtraDb;


    public CheckedAdapter(Context context, List<CheckedItems> checkeditems) {
        this.mContext = context;
        this.mCheckedItems = checkeditems;
        selectedIds=new int[500];
    }

    static class CheckedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView TextNumber;
        private CheckBox mNavigatorNameTextView;
        private EditText mEditText;
        private FrameLayout rootView;
        private CheckBox checkbox;
        private View myview;
        ItemClickListener itemClickListener;
        public CheckedViewHolder(final View itemView) {
            super(itemView);
            myview=itemView;
            TextNumber = itemView.findViewById(R.id.TextQuantity);
            mNavigatorNameTextView = itemView.findViewById(R.id.txtTitle);
            rootView = itemView.findViewById(R.id.constraint);
            mNavigatorNameTextView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        interface ItemClickListener {

            void onItemClick(View v,int pos);
        }
    }
    @NonNull
    @Override
    public CheckedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_row, parent, false);
        counter=0;
        for (int i=0;i<=10;i++) {
            selectedIds[i] = 50;
        }
        return new CheckedViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final CheckedViewHolder holder, final int position) {

        if (! mCheckedItems.get(position).getdataList().equalsIgnoreCase("Added to Cart")) {
            if (position == mCheckedItems.size()) {

            } else {

                holder.mNavigatorNameTextView.setText(mCheckedItems.get(position).getdataList());
                holder.TextNumber.setText(mCheckedItems.get(position).getcount());
                int id = mCheckedItems.get(position).getId();
        /*    holder.mNavigatorNameTextView.setOnClickListener(new View.OnClickListener() {
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

            } */
                holder.setItemClickListener(new CheckedViewHolder.ItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {


                        if (holder.mNavigatorNameTextView.isChecked()) {
                            Message.message(mContext, holder.mNavigatorNameTextView.getText().toString() + String.valueOf(pos));
                            swapItem(pos, 0);
                            counter = counter + 1;
                        } else {
                            counter = counter - 1;
                            Message.message(mContext, "I am not connected");

                        }
                    }
                });
            }
            holder.TextNumber.setOnClickListener(new View.OnClickListener() {

                                                     @Override
                                                     public void onClick(View view) {
                                                         Intent I = new Intent(view.getContext(), QuantityActivity.class);
                                                         I.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                         I.putExtra(holder.mNavigatorNameTextView.getText().toString(), "Title");
                                                         view.getContext().startActivity(I);
                                                     }

                                                 }
            );
        }

    }

    public void swapItem(int fromposition,int toposition)
    {
        Collections.swap(mCheckedItems,fromposition,toposition);
        notifyItemMoved(fromposition,toposition);
    }
    public void display()
    {
    /*      int change;
        change=0;
        for (int j=0;j<counter;j++) {
            for (int i = j + 1; i < counter; i++) {
                if (selectedIds[i] > selectedIds[j]) {
                    change = selectedIds[i];
                    selectedIds[i] = selectedIds[j];
                    selectedIds[j] = change;


                }

            }
        } */
        for (int k=0;k<counter;k++) {
            String product =mCheckedItems.get(k).getdataList();
            removeItem(k);
            ExtraDb = new ExtraDb(mContext);
            int eid = ExtraDb.checkproduct(product);
            if (eid != 0 )
            {
                long eeid = ExtraDb.updatedata(product, 100, 0, 0);

            }

        }
        counter = 0;
    }
    @Override
    public int getItemCount() {
        return mCheckedItems.size();
    }
    public CheckedItems getItem(int position){
        return mCheckedItems.get(position);
    }

    public void removeItem(int position) {
        mCheckedItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(0,mCheckedItems.size());
    }

    public List<CheckedItems> getData()
    {
        return mCheckedItems;
    }
}

