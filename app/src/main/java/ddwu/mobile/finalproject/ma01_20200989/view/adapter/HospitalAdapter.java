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
    private List<HospitalDto> hospitals;
    private LayoutInflater layoutInflater;
    ViewHolder holder;

    public HospitalAdapter(Context context, int layout, List<HospitalDto> diaries) {
        this.context = context;
        this.layout = layout;
        this.hospitals = diaries;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hospitals.size();
    }

    @Override
    public Object getItem(int i) {
        return hospitals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
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

        holder.hospitalTitleView.setText(hospitals.get(i).getTitle());
        holder.hospitalAddressView.setText(hospitals.get(i).getAddress());
        holder.hospitalStatusView.setText(hospitals.get(i).getStatus());
        holder.hospitalTelView.setText(hospitals.get(i).getTel());

        return view;
    }

    static class ViewHolder {
        TextView hospitalTitleView;
        TextView hospitalStatusView;
        TextView hospitalAddressView;
        TextView hospitalTelView;
    }
}
