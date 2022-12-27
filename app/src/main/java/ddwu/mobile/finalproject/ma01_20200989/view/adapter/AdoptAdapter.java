package ddwu.mobile.finalproject.ma01_20200989.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.AdoptDto;

public class AdoptAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<AdoptDto> adopts;
    private LayoutInflater layoutInflater;
    ViewHolder holder;

    public AdoptAdapter(Context context, int layout, List<AdoptDto> adopts) {
        this.context = context;
        this.layout = layout;
        this.adopts = adopts;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return adopts.size();
    }

    @Override
    public Object getItem(int i) {
        return adopts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return adopts.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position = i;

        if (view == null) {
            view = layoutInflater.inflate(layout, viewGroup, false);

            holder = new AdoptAdapter.ViewHolder();

            holder.petPicture = (ImageView) view.findViewById(R.id.petPicture);
            holder.kind = (TextView) view.findViewById(R.id.kind);
            holder.protection = (TextView) view.findViewById(R.id.protection);
            holder.city = (TextView) view.findViewById(R.id.city);
            holder.disease = (TextView) view.findViewById(R.id.disease);
            holder.startDate = (TextView) view.findViewById(R.id.startDate);
            holder.endDate = (TextView) view.findViewById(R.id.endDate);

            view.setTag(holder);
        } else {
           holder = (ViewHolder) view.getTag();
        }

        holder.petPicture.setImageResource(R.drawable.dog);
        holder.kind.setText(adopts.get(i).getKind());
        holder.protection.setText(adopts.get(i).getProtection());
        holder.city.setText(adopts.get(i).getCity());
        holder.disease.setText(adopts.get(i).getDisease());
        holder.startDate.setText(adopts.get(i).getStartDate().toString());
        holder.endDate.setText(adopts.get(i).getEndDate().toString());

        return view;
    }

    static class ViewHolder {
        ImageView petPicture;
        TextView kind;
        TextView protection;
        TextView city;
        TextView disease;
        TextView startDate;
        TextView endDate;
    }
}
