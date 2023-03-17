package track.groceries.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.graphics.Paint;

import track.groceries.home.R;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    public String[] ArraySeconds = new String[6];
    private Context mContext;
    private List<Show> mShowList;
    public static List<CheckedItems> mCheckeditems;
    private static int[] selectedIds;
    private int counter;
    private CheckedAdapter CA;
    private   RecyclerView recyclerView;
     private ExtraDb ExtraDb;


    public ShowAdapter(Context context, List<Show> showList) {
        this.mContext = context;
        this.mShowList = showList;
        selectedIds=new int[500];

    }

   static class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView TextNumber;
        private CheckBox mNavigatorNameTextView;
        private EditText mEditText;
        private FrameLayout rootView;
        private CheckBox checkbox;
        private View myview;

        ItemClickListener itemClickListener;
        public ShowViewHolder(final View itemView) {
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
         mCheckeditems = new ArrayList<>();
        final int pos=holder.getAdapterPosition();
        if (pos == mShowList.size())
        {

        }
        else {

            holder.mNavigatorNameTextView.setText(mShowList.get(pos).getdataList());
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
           // String QuantityValue = prefs.getString("QuantityValue", "1kg");
         holder.TextNumber.setText(mShowList.get(pos).getcount());
          // holder.TextNumber.setText(QuantityValue);
            int id = mShowList.get(pos).getId();
            holder.mNavigatorNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mNavigatorNameTextView.isChecked()) {
                        selectedIds[counter]=pos;
                        counter = counter + 1;
                    } else {
                        selectedIds[counter] = 50;
                        counter = counter - 1;


                    }
                }
            });

            }

         /*   holder.setItemClickListener(new ShowViewHolder.ItemClickListener() {
                                @Override
                                public void onItemClick(View v, int pos) {
                                    if (holder.mNavigatorNameTextView.isChecked()) {
                                        selectedIds[counter]=pos;
                                        counter = counter + 1;
                                    } else {
                                        selectedIds[counter] = 50;
                                        counter = counter - 1;


                                    }
                                }
                            }); */

       holder.TextNumber.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View view) {


                                               Intent I =new Intent(view.getContext(),QuantityActivity.class);
                                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                                                   String Prodcut =holder.mNavigatorNameTextView.getText().toString();
                                                    prefs.edit().putString("Product",Prodcut).commit();
                                                    I.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    I.putExtra(holder.mNavigatorNameTextView.getText().toString(),"Title");
                                                    view.getContext().startActivity(I);
                                                    
                                                    holder.TextNumber.setText(mShowList.get(position).getcount());

                                                   // AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.myDialog);
                                                 // View mView = LayoutInflater.from(view.getContext()).inflate(R.layout.showdialog,null);
                                                   // builder.setTitle("Choose the Quantity");
                                                 /*   Spinner spinner = (Spinner) mView.findViewById(R.id.spinner);
                                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, ArraySeconds );
                                                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                    spinner.setAdapter(arrayAdapter);
                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            // if (spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                                                            // Toast.makeText(getApplicationContext(),spinner.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                                                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                                                            prefs.edit().putString("Seconds", spinner.getSelectedItem().toString()).commit();
                                                            dialogInterface.dismiss();
                                                        }
                                                    });
                                                    builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            // if (spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                                                            dialogInterface.dismiss();
                                                        }
                                                    });
                                                    builder.setView(mView);
                                                    builder.create(); */
                                                  //  builder.show();

                                                }

                                            }
       );
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
                    long eeid = ExtraDb.updatedata(product, 100, 0, 0);

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
        notifyDataSetChanged();
        notifyItemRangeRemoved(0,mShowList.size());
    }

    public List<Show> getData()
   {
       return mShowList;
   }
}

