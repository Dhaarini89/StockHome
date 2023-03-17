package track.groceries.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    private ArrayList<NotifyOption> NotifyOptionSet;
    Context mContext;
    ArrayList<String> selectedStrings = new ArrayList<String>();

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        CheckBox checkBox;
        TextView txtCount;
    }

    public CustomAdapter(ArrayList NotifyOption, Context context) {
        super(context, R.layout.layout_notifycheck, NotifyOption);
        this.NotifyOptionSet = NotifyOption;
        this.mContext = context;

    }
    @Override
    public int getCount() {
        return NotifyOptionSet.size();
    }

    @Override
    public NotifyOption getItem(int position) {
        return NotifyOptionSet.get(position);
    }


    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notifycheck, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            viewHolder.txtCount = (TextView) convertView.findViewById(R.id.txtCount);
            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        NotifyOption item = getItem(position);


        viewHolder.txtName.setText(item.name);
     //   viewHolder.txtCount.setText(item.checknotify);
        viewHolder.checkBox.setChecked(item.checked);


        return result;
    }



}
