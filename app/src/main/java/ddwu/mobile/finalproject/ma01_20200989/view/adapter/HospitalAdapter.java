package ddwu.mobile.finalproject.ma01_20200989.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.HospitalDto;

public class HospitalAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HospitalDto> diaries;
    private LayoutInflater layoutInflater;
    ViewHolder holder;

    public HospitalAdapter(Context context, int layout, List<HospitalDto> diaries) {
        this.context = context;
        this.layout = layout;
        this.diaries = diaries;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return diaries.size();
    }

    @Override
    public Object getItem(int i) {
        return diaries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();

            holder.hospitalTitleView = (TextView) view.findViewById(R.id.hospitalTitleView);
            holder.hospitalAddressView = (TextView) view.findViewById(R.id.hospitalAddressView);
            holder.hospitalStatusView = (TextView) view.findViewById(R.id.hospitalStatusView);
            holder.hospitalTelView = (TextView) view.findViewById(R.id.hospitalTelView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    static class ViewHolder {
        TextView hospitalTitleView;
        TextView hospitalStatusView;
        TextView hospitalAddressView;
        TextView hospitalTelView;
    }
}
