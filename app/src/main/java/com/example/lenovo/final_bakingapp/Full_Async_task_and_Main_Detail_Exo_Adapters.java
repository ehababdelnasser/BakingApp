package com.example.lenovo.final_bakingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 05/11/2017.
 */

class Full_Async_task_and_Main_Detail_Exo_Adapters extends AsyncTask<String, Void, List<Bak_Attr>> {
    interface Exo_Interface {
        void onTaskComplete(List<?> result);
    }

    private Exo_Interface B_Call_back;

    Full_Async_task_and_Main_Detail_Exo_Adapters(Exo_Interface mCallBack)

    {
        this.B_Call_back = mCallBack;
    }

    private final String LOG_TAG = Full_Async_task_and_Main_Detail_Exo_Adapters.class.getSimpleName();

    private List<Bak_Attr> getProductDataFromJson(String productsJSONString) throws JSONException {

        Bak_Attr bakAttr;


        final String NAME = "name";
        final String ID = "id";
        final String SERVINGS = "servings";
        final String IMAGE = "image";

        final String QUANTITY = "quantity";
        final String MEASURE = "measure";
        final String INGREDIENT = "ingredient";


        final String STEP_ID = "id";
        final String SHORT_DESCRIPTION = "shortDescription";
        final String DESCRIPTION = "description";
        final String VIDEO_URL = "videoURL";
        final String THUMBNAIL = "thumbnailURL";

        JSONObject product, ingredJsonObj, stepsJsonObj;
        JSONArray jsonArray1 = new JSONArray(productsJSONString);
        JSONArray jsonArray2, jsonArray3;


        List<Bak_Attr> bakAttrList = new ArrayList<>();

        for (int i = 0; i < jsonArray1.length(); i++)
        {

            product = jsonArray1.getJSONObject(i);
            jsonArray2 = product.getJSONArray("ingredients");
            jsonArray3 = product.getJSONArray("steps");


            String name = product.getString(NAME);
            String id = product.getString(ID);
            String servings = product.getString(SERVINGS);
            String image = product.getString(IMAGE);


            ArrayList<String> dataIng = new ArrayList<>();
            ArrayList<Bak_Attr> stepsAttrArrayList = new ArrayList<>();

            for (int ii = 0; ii < jsonArray2.length(); ii++)
            {

                ingredJsonObj = jsonArray2.getJSONObject(ii);

                String quantity = ingredJsonObj.getString(QUANTITY);
                String measure = ingredJsonObj.getString(MEASURE);
                String ingredient = ingredJsonObj.getString(INGREDIENT);

                dataIng.add(ingredient + '\t' + quantity + '\t' + measure);

            }

            for (int iii = 0; iii < jsonArray3.length(); iii++)
            {
                Bak_Attr stepsAttr = new Bak_Attr();
                stepsJsonObj = jsonArray3.getJSONObject(iii);

                String step_id = stepsJsonObj.getString(STEP_ID);
                String short_description = stepsJsonObj.getString(SHORT_DESCRIPTION);
                String description = stepsJsonObj.getString(DESCRIPTION);
                String video_url = stepsJsonObj.getString(VIDEO_URL);
                String thumbnailURL = stepsJsonObj.getString(THUMBNAIL);

                    stepsAttr.setStepId(step_id);
                    stepsAttr.setShortDescription(short_description);
                    stepsAttr.setDescription(description);
                    stepsAttr.setVideoURL(video_url);
                    stepsAttr.setThumbnailURL(thumbnailURL);

                 stepsAttrArrayList.add(stepsAttr);


            }


            bakAttr = new Bak_Attr(id, name, servings,image);

            bakAttr.setIngrediantList(dataIng);
            bakAttr.setStepsAttrList(stepsAttrArrayList);

            bakAttrList.add(bakAttr);



        }
        return bakAttrList;


    }


    @Override
    protected List<Bak_Attr> doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String productsJSONStrin = null;

        try {

            URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuffer = new StringBuilder();
            if (inputStream == null) {
                return null;
            }

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                stringBuffer.append(line).append("\n");
            }

            if (stringBuffer.length() == 0) {
                return null;
            }
            productsJSONStrin = stringBuffer.toString();
        } catch (Exception e) {

            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final Exception e) {

                }
            }
        }

        try {
            return getProductDataFromJson(productsJSONStrin);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Bak_Attr> strings) {
        B_Call_back.onTaskComplete(strings);
    }


    public static class Rec_Adapt extends RecyclerView.Adapter<Rec_Adapt.ViewHolder> {

        public interface OnItemClickListener {
            void onItemClick(Bak_Attr item);

        }

        private List<Bak_Attr> baking_List;
        private final OnItemClickListener Interface;

        public Rec_Adapt(List<Bak_Attr> bakAttrList, OnItemClickListener listner) {
            this.baking_List = bakAttrList;
            this.Interface = listner;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.img_txt_view_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Bak_Attr bakAttr = baking_List.get(position);
            holder.bind(bakAttr, Interface);
        }

        @Override
        public int getItemCount() {
            if (baking_List == null) {
                return 0;
            }
            return baking_List.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_v);
                textView = (TextView) itemView.findViewById(R.id.txt_v);
            }

            void bind(final Bak_Attr item, final OnItemClickListener listener) {

                final Context context = itemView.getContext();

                textView.setText(item.getName());

                    if(item.getImage().length()>0)
                    {
                        Picasso.with(context).load(item.getImage()).into(imageView);

                    }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(item);
                    }
                });

            }

        }
    }



    public static class Detail_Adapt extends RecyclerView.Adapter<Detail_Adapt.ViewHolder> {

        public interface OnItemClickListener {
            void onItemClick(int item);

        }

        private List<Bak_Attr> bak_List;
        private final OnItemClickListener Interface;


        public Detail_Adapt(List<Bak_Attr> bakingObjsList, OnItemClickListener listner) {
            this.bak_List = bakingObjsList;
            this.Interface = listner;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Bak_Attr bakingObjs = bak_List.get(position);
            holder.bind(bakingObjs, Interface);
        }

        @Override
        public int getItemCount() {
            if (bak_List == null) {
                return 0;
            }
            return bak_List.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        class ViewHolder extends RecyclerView.ViewHolder {


            TextView textView;

            ViewHolder(View itemView) {
                super(itemView);

                textView = (TextView) itemView.findViewById(android.R.id.text1);
            }

            void bind(final Bak_Attr item, final OnItemClickListener listener) {



                textView.setText(item.getShortDescription());

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(getAdapterPosition());
                    }
                });

            }

        }
    }



    public static class Exo_Adapt extends RecyclerView.Adapter<Exo_Adapt.Exo_ViewHolder> {

        private Bak_Attr stepsAttr;
        private Context con;
        private onClickListener B_Call_back;

        public Exo_Adapt(Bak_Attr stepsAttr, Context context, onClickListener onClickListener) {
            this.stepsAttr = stepsAttr;
            this.con = context;
            this.B_Call_back = onClickListener;
        }

        public void changeDataSet(Bak_Attr data2) {
            this.stepsAttr = data2;
            this.notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public Exo_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_steps_details_layout, parent, false);

            return new Viewholder2(view);
        }

        @Override
        public void onBindViewHolder(Exo_ViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return (1);
        }

        private class Viewholder2 extends Exo_ViewHolder implements View.OnClickListener {
            TextView textView;
            Button nxt, prev;
            ImageView thup;

            public Viewholder2(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.step_txt3);
                nxt = (Button) itemView.findViewById(R.id.but2);
                prev = (Button) itemView.findViewById(R.id.but);
                thup = (ImageView) itemView.findViewById(R.id.img_videos);
                nxt.setOnClickListener(this);
                prev.setOnClickListener(this);
            }

            @Override
            public void bind(int position) {
                if (!stepsAttr.getThumbnailURL().equals("")) {
                    thup.setVisibility(View.VISIBLE);
                    Picasso.with(con).load(stepsAttr.getThumbnailURL()).fit().into(thup);
                } else {
                    thup.setVisibility(View.INVISIBLE);
                }
                textView.setText(stepsAttr.getDescription());
            }

            @Override
            public void onClick(View view) {
                if (view.getId() == nxt.getId())
                    B_Call_back.OnClick(0);
                else
                    B_Call_back.OnClick(1);
            }
        }

        public interface onClickListener {
            void OnClick(int c);

            void change(int idx);
        }


        public abstract static class Exo_ViewHolder extends RecyclerView.ViewHolder
        {
            public Exo_ViewHolder(View itemView) {
                super(itemView);
            }

            public abstract  void bind(int position);
        }
    }
}