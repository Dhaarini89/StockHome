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


public class ToiletModelAdapter extends RecyclerView.Adapter<ToiletModelAdapter.ToiletModelViewHolder> {

    private Context mContext;
    private List<Model> mModelList;
    private CustomAdapterListener mListener;
    private ExtraDb mToiletDatabase;

    public ToiletModelAdapter(Context context, List<Model> modelList, CustomAdapterListener listener) {
        this.mContext = context;
        this.mModelList = modelList;
        this.mListener = listener;
        mToiletDatabase = new ExtraDb(context);
    }

    public interface CustomAdapterListener {
        void onModelSelected(Model model, View view);
    }
    class ToiletModelViewHolder extends RecyclerView.ViewHolder {

        private TextView Toilet;
        private SeekBar seekbar;
        ToiletModelViewHolder(@NonNull final View itemView) {
            super(itemView);
            Toilet = itemView.findViewById(R.id.text_fruit);
            seekbar = itemView.findViewById(R.id.seekBar);
            seekbar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        int progress = 0;

                        @Override
                        public void onProgressChanged(SeekBar seekBar,
                                                      int progresValue, boolean fromUser) {
                            progress = progresValue;
                            Integer T = mToiletDatabase.updatedata(Toilet.getText().toString(), progress, 0, 0);
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
        public ToiletModelAdapter.ToiletModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_list, parent, false);
            return new ToiletModelAdapter.ToiletModelViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull ToiletModelAdapter.ToiletModelViewHolder holder,final int position) {
            Integer RedFlg, YellowFlg, GreenFlg;
            final Model model = mModelList.get(position);
            holder.Toilet.setText(model.getFruit());
            LayerDrawable progressdraw = (LayerDrawable)holder.seekbar.getProgressDrawable();
            Drawable backgrounddrawable = progressdraw.getDrawable(0);
            Drawable progressdrawable = progressdraw.getDrawable(1);

            holder.Toilet.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // removeItem(position);
                    new AlertDialog.Builder(mContext,R.style.sample)
                            .setMessage("Are you sure you want to delete "+model.getFruit()+"?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    removeItem(position);
                                    mToiletDatabase = new ExtraDb(mContext);
                                    Integer D= mToiletDatabase.deleteProduct(model.getFruit());
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

            RedFlg = mToiletDatabase.getRedFlag(model.getFruit());
            if(RedFlg <= 75 && RedFlg >=25){
                holder.seekbar.setMax(0);
                holder.seekbar.setMax(100);
                holder.seekbar.setProgress(RedFlg);
                holder.seekbar.refreshDrawableState();
                progressdrawable.setColorFilter(ContextCompat.getColor(mContext,R.color.yellow),PorterDuff.Mode.SRC_IN);
            }
            else {
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


