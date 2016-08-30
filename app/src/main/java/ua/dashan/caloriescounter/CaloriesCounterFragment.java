package ua.dashan.caloriescounter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.fanrunqi.waveprogress.WaveProgressView;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;
import ua.dashan.caloriescounter.Database.DatabaseModel;

public class CaloriesCounterFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseHelpher helpher;
    private List<DatabaseModel> dbList;
    private  RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
   public static WaveProgressView waveProgressbar;
   public static TextView progressText;
    private int maxProgress=2000;

    public static TextView getTextCal() {
        return textCal;
    }


    static TextView textCal;

    public static int getOne() {
        return one;
    }

    private static final int one = 0X0001;

    public static int getProgress() {
        return progress;
    }

    static int progress;
    private int oldDataLehgth;
    public  static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case one:
                    //   for(progress=0;progress<=100;progress++){
                    waveProgressbar.setCurrent(progress, progress+"");
                    sendEmptyMessageDelayed(one, 100);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_calories_counter, container, false);
        waveProgressbar =(WaveProgressView)v.findViewById(R.id.waveProgressbar3);
      /*  bt=(Button)v.findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessageDelayed(one, 1000);
                progress+=10;
            }
        });*/
        Init();





        progressText=(TextView)v.findViewById(R.id.progressText);

//        int cCount = helpher.getCaloriesCount(dateString);
        //если в базе данных нету записи вылетает с ошибкой, так как нечего показывать
   // progressText.setText(cCount+"");


        recyclerView=(RecyclerView)v.findViewById(R.id.horizontal_recycler_view);
        helpher=new DatabaseHelpher(getActivity());
        dbList=new ArrayList<DatabaseModel>();
        dbList=helpher.getDataFromDB();
        oldDataLehgth=dbList.size();
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new HorizontalRecyclerAdapter(getActivity(),dbList);
        recyclerView.setAdapter(adapter);
return v;
    }
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        //Log.d("log","result="+resultCode);
        if (resultCode==800)
            Log.d("hot","result="+resultCode);
        adapter.notifyDataSetChanged();
        dbList=helpher.getDataFromDB();
        adapter=new HorizontalRecyclerAdapter(getActivity(),dbList);
        recyclerView.setAdapter(adapter);
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            dbList=helpher.getDataFromDB();
         if(oldDataLehgth<dbList.size())
            adapter.notifyDataSetChanged();
           adapter=new HorizontalRecyclerAdapter(getActivity(),dbList);
           recyclerView.setAdapter(adapter);
        }else{}
        }
    private void Init() {

        waveProgressbar.setMaxProgress(maxProgress);
        waveProgressbar.setWaveColor("#f0b55e");

    }}