package track.groceries.home;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import track.groceries.home.R;

import java.util.List;

public class FruitModelAdapter extends RecyclerView.Adapter<FruitModelAdapter.FruitModelViewHolder>  {

    private Context mContext;
    private List<Model> mModelList;
    private CustomAdapterListener mListener;
    private ExtraDb mFruitDatabase;
    private ExtraDb Fruithelper;

    public FruitModelAdapter(Context context, List<Model> modelList, CustomAdapterListener listener) {
        this.mContext = context;
        this.mModelList = modelList;
        this.mListener = listener;
        mFruitDatabase = new ExtraDb(context);
    }

    public interface CustomAdapterListener {
        void onModelSelected(Model model, View view);
    }

    public class FruitModelViewHolder extends RecyclerView.ViewHolder   {
        private TextView fruit;
        private SeekBar seekbar;
        FruitModelViewHolder(@NonNull final View itemView) {
            super(itemView);
            fruit = itemView.findViewById(R.id.text_fruit);
          //  itemView.setOnLongClickListener(this);
            seekbar = itemView.findViewById(R.id.seekBar);
            seekbar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        int progress = 0;
                        @Override
                        public void onProgressChanged(SeekBar seekBar,
                                                      int progresValue, boolean fromUser) {

                            progress = progresValue;
                            Integer T=mFruitDatabase.updatedata(fruit.getText().toString(),progress,0,0);
                            LayerDrawable progressdraw = (LayerDrawable)seekbar.getProgressDrawable();
                            Drawable backgrounddrawable = progressdraw.getDrawable(0);
                            Drawable progressdrawable = progressdraw.getDrawable(1);
                            if(progress <= 75 && progress >=25){

                               // seekbar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.yellow));
                                //seekbar.setProgressBackgroundTintList(ContextCompat.getColorStateList(mContext,R.color.colorIconText));
                                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.yellow),PorterDuff.Mode.SRC_IN);
                            }else
                            {
                                if(progress >75) {

                                 //   seekbar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.green));
                                   // seekbar.setProgressBackgroundTintList(ContextCompat.getColorStateList(mContext,R.color.colorIconText));
                                    progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.green),PorterDuff.Mode.SRC_IN);

                                }
                                else
                                 //   seekbar.setProgressTintList(ContextCompat.getColorStateList(mContext,R.color.red));
                               // seekbar.setProgressBackgroundTintList(ContextCompat.getColorStateList(mContext,R.color.colorIconText));
                                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.red),PorterDuff.Mode.SRC_IN);

                            }
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
        }


}



    @NonNull
    @Override
    public FruitModelAdapter.FruitModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_list, parent, false);
        return new FruitModelAdapter.FruitModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitModelAdapter.FruitModelViewHolder holder, final int position) {
        Integer RedFlg, YellowFlg, GreenFlg;
        final Model model = mModelList.get(position);
        LayerDrawable progressdraw = (LayerDrawable)holder.seekbar.getProgressDrawable();
        Drawable backgrounddrawable = progressdraw.getDrawable(0);
        Drawable progressdrawable = progressdraw.getDrawable(1);
        holder.fruit.setText(model.getFruit());
        holder.fruit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // removeItem(position);
                new AlertDialog.Builder(mContext,R.style.sample)
                        .setMessage("Are you sure you want to delete "+model.getFruit()+"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(position);
                                Fruithelper = new ExtraDb(mContext);
                                Integer D= Fruithelper.deleteProduct(model.getFruit());
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notifyItemChanged(position);
                                dialog.cancel();
                            }
                        })
                        .create()
                        .show();




                return true;
            }
        });
        RedFlg = mFruitDatabase.getRedFlag(model.getFruit());
        if (RedFlg <= 75 && RedFlg >= 25) {
            holder.seekbar.setMax(0);
            holder.seekbar.setMax(100);
            holder.seekbar.setProgress(RedFlg);
            holder.seekbar.refreshDrawableState();
            progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.yellow),PorterDuff.Mode.SRC_IN);
        } else {
            if (RedFlg > 75) {
                holder.seekbar.setMax(0);
                holder.seekbar.setMax(100);
                holder.seekbar.setProgress(RedFlg);
                holder.seekbar.refreshDrawableState();
                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.green),PorterDuff.Mode.SRC_IN);
            } else {
                holder.seekbar.setMax(100);
                holder.seekbar.setProgress(RedFlg);
                holder.seekbar.refreshDrawableState();
                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.red),PorterDuff.Mode.SRC_IN);
            }
        }

    }






    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public void removeItem(int position) {
        mModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(0,mModelList.size());
    }

    public List<Model> getData()
    {
        return mModelList;
    }
}


