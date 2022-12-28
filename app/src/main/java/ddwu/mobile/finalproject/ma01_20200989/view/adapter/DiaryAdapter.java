package ddwu.mobile.finalproject.ma01_20200989.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import ddwu.mobile.finalproject.ma01_20200989.R;
import ddwu.mobile.finalproject.ma01_20200989.model.domain.dto.DiaryDto;

public class DiaryAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DiaryDto> diaries;
    private LayoutInflater layoutInflater;
    ViewHolder holder;

    public DiaryAdapter(Context context, int layout, List<DiaryDto> diaries) {
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
        return diaries.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(layout, viewGroup, false);
            holder = new ViewHolder();
            holder.diaryTitle = (TextView) view.findViewById(R.id.titleView);
            holder.diaryDate = (TextView) view.findViewById(R.id.todayView);
            holder.petStatus = (TextView) view.findViewById(R.id.statusView);
            holder.diaryContent = (TextView) view.findViewById(R.id.contentView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.diaryTitle.setText(diaries.get(i).getTitle());
        holder.diaryDate.setText(diaries.get(i).getDate());
        holder.petStatus.setText(diaries.get(i).getStatus());
        holder.diaryContent.setText(diaries.get(i).getContent());

        return view;
    }

    static class ViewHolder {
        TextView diaryTitle;
        TextView diaryDate;
        TextView petStatus;
        TextView diaryContent;
    }
}
