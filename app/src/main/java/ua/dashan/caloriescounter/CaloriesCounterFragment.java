package ua.dashan.caloriescounter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.fanrunqi.waveprogress.WaveProgressView;
import ua.dashan.caloriescounter.Adapters.RecyclerAdapter;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;
import ua.dashan.caloriescounter.Database.DatabaseModel;

public class CaloriesCounterFragment extends Fragment {
    public static RecyclerView recyclerView;
    private DatabaseHelpher helpher;
    public static List<DatabaseModel> dbList;
    public static EditText targetET;





    public static   RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static WaveProgressView waveProgressbar;
    public static TextView progressText;








    private static final int one = 0X0001;


    static int progress;
    static int progressForStart;
    private int oldDataLehgth;
    public static Handler handler;
    int targetIsNull;

    private static final java.text.DateFormat FORMATTER = SimpleDateFormat.getDateInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_calories_counter, container, false);

        waveProgressbar =(WaveProgressView)v.findViewById(R.id.waveProgressbar3);


      //  waveProgressbar.setMaxProgress(maxProgress);
        waveProgressbar.setWaveColor("#f0b55e");



        //Init();




        targetET =(EditText)v.findViewById(R.id.userTarget);

        targetET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                TargetDialogFragment dialogFragment = new TargetDialogFragment ();
                dialogFragment.show(fm, "Sample Fragment");
            }
        });
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


        targetIsNull =helpher.targetIsNull();
        if(targetIsNull>0){
            targetET.setText(helpher.getTarget()+" каллорий");
        }else{
            targetET.setText("...");
        }

        CalendarDay day = new CalendarDay();
        String date =FORMATTER.format(day.getDate());
        if(helpher.getCaloriesCountForCalendar(date) !=0){
        progressForStart=helpher.getCaloriesCountForCalendar(date);}


        if(helpher.getTarget()!=0){
waveProgressbar.setMaxProgress(helpher.getTarget());}
        waveProgressbar.setCurrent(progressForStart,progressForStart+"");

        handler  = new Handler() {
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
return v;
   }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
/*
   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==800){
            Log.d("loga","result="+resultCode);
            dbList=helpher.getDataFromDB();
            Log.d("st",Integer.toString(resultCode));
            adapter.notifyDataSetChanged();
            adapter=new HorizontalRecyclerAdapter(getActivity(),dbList);
            recyclerView.setAdapter(adapter);
        }
    }*/





    //нужно решить эту проблему
  /* @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {

           if (isVisibleToUser) {
          dbList=helpher.getDataFromDB();
       if(oldDataLehgth<dbList.size())
            adapter.notifyDataSetChanged();
           adapter=new HorizontalRecyclerAdapter(getActivity(),dbList);
           recyclerView.setAdapter(adapter);
        }else{


        }}*/

  /*  private void Init() {

        waveProgressbar.setMaxProgress(maxProgress);
        waveProgressbar.setWaveColor("#f0b55e");

    }*/


}
