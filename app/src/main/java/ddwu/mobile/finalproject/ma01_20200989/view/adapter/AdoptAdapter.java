package ddwu.mobile.finalproject.ma01_20200989.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

            holder.petPicture = (ImageView) view.findViewById(R.id.petPictureView);
            holder.kind = (TextView) view.findViewById(R.id.kindView);
            holder.protection = (TextView) view.findViewById(R.id.protectionView);
            holder.city = (TextView) view.findViewById(R.id.cityView);
            holder.disease = (TextView) view.findViewById(R.id.diseaseView);
            holder.startDate = (TextView) view.findViewById(R.id.startDateView);
            holder.endDate = (TextView) view.findViewById(R.id.endDateView);

            view.setTag(holder);
        } else {
           holder = (ViewHolder) view.getTag();
        }

        new DownloadFilesTask().execute(adopts.get(i).getUrl());
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

    private class DownloadFilesTask extends AsyncTask<String,Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bmp = null;
            try {
                String img_url = strings[0]; //url of the image
                URL url = new URL(img_url);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            holder.petPicture.setImageBitmap(result);
        }
    }
}
