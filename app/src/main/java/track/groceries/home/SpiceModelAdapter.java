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


public class SpiceModelAdapter extends RecyclerView.Adapter<SpiceModelAdapter.SpiceModelViewHolder> {

    private Context mContext;
    private List<Model> mModelList;
    private CustomAdapterListener mListener;
    private SpiceDbAdapter mSpiceDatabase;
    public  SpiceModelAdapter(Context context, List<Model> modelList, CustomAdapterListener listener) {
        this.mContext = context;
        this.mModelList = modelList;
        this.mListener = listener;
        mSpiceDatabase = new SpiceDbAdapter(context);
    }

    public interface CustomAdapterListener {
        void onModelSelected(Model model, View view);
    }
    class SpiceModelViewHolder extends RecyclerView.ViewHolder  {
        private TextView Spice;
        private SeekBar seekbar;
        SpiceModelViewHolder(@NonNull final View itemView) {
            super(itemView);
            Spice = itemView.findViewById(R.id.text_fruit);
            seekbar = itemView.findViewById(R.id.seekBar);
            seekbar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        int progress = 0;
                        @Override
                        public void onProgressChanged(SeekBar seekBar,
                                                      int progresValue, boolean fromUser) {
                            progress = progresValue;
                            Integer T=mSpiceDatabase.updatedata(Spice.getText().toString(),progress,0,0);
                            LayerDrawable progressdraw = (LayerDrawable)seekbar.getProgressDrawable();
                            Drawable backgrounddrawable = progressdraw.getDrawable(0);
                            Drawable progressdrawable = progressdraw.getDrawable(1);

                            if(progress <= 75 && progress >=25){
                                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.yellow), PorterDuff.Mode.SRC_IN);

                            }else
                            {
                                if(progress >75) {
                                    progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.green),PorterDuff.Mode.SRC_IN);

                                }
                                else
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
    public SpiceModelAdapter.SpiceModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_list, parent, false);
        return new SpiceModelAdapter.SpiceModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpiceModelAdapter.SpiceModelViewHolder holder, final int position) {
        Integer RedFlg;
        final Model model = mModelList.get(position);
        holder.Spice.setText(model.getFruit());
        LayerDrawable progressdraw = (LayerDrawable)holder.seekbar.getProgressDrawable();
        Drawable backgrounddrawable = progressdraw.getDrawable(0);
        Drawable progressdrawable = progressdraw.getDrawable(1);

        holder.Spice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // removeItem(position);
                new AlertDialog.Builder(mContext,R.style.sample)
                        .setMessage("Are you sure you want to delete "+model.getFruit()+"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeItem(position);
                                mSpiceDatabase = new SpiceDbAdapter(mContext);
                                Integer D= mSpiceDatabase.deleteProduct(model.getFruit());
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
        RedFlg = mSpiceDatabase.getRedFlag(model.getFruit());
        if(RedFlg <= 75 && RedFlg >=25){
            holder.seekbar.setMax(0);
            holder.seekbar.setMax(100);
            holder.seekbar.setProgress(RedFlg);
            holder.seekbar.refreshDrawableState();
            progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.yellow),PorterDuff.Mode.SRC_IN);
        }
        else {
            if (RedFlg > 75)
            { holder.seekbar.setMax(0);
                holder.seekbar.setMax(100);
                holder.seekbar.setProgress(RedFlg);
                holder.seekbar.refreshDrawableState();
                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.green),PorterDuff.Mode.SRC_IN);
            }
            else
            {
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


