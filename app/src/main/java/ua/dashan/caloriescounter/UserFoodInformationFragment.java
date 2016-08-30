package ua.dashan.caloriescounter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import ua.dashan.caloriescounter.Adapters.RecyclerAdapter;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;
import ua.dashan.caloriescounter.Database.DatabaseModel;


public class UserFoodInformationFragment extends Fragment {
   private RecyclerView recyclerView;
    private DatabaseHelpher helpher;
    private ActionButton fab;
    private List<DatabaseModel> dbList;
    public   static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_user_information, container, false);



        dbList= new ArrayList<DatabaseModel>();
        recyclerView=(RecyclerView)v.findViewById(R.id.recycleview);
        fab=(ActionButton)v.findViewById(R.id.action_button);
        fab.playShowAnimation();
       /* fab.setButtonColor(getActivity().getResources().getColor(R.color.colorFab));
        fab.setButtonColorPressed(getActivity().getResources().getColor(R.color.colorFabPressed));*/
         int buttonColor = Color.parseColor("#e6ee9c");
         int buttonColorPressed = Color.parseColor("#cddc39");
         fab.setButtonColor(buttonColor);
         fab.setButtonColorPressed(buttonColorPressed);
        fab.setImageResource(R.drawable.plus);
        float scale = getActivity().getResources().getDisplayMetrics().density;
        int shadow = (int )(3*scale+0.5);
        fab.setShadowRadius(shadow);
        fab.setShowAnimation(ActionButton.Animations.ROLL_FROM_DOWN);
        fab.setHideAnimation(ActionButton.Animations.JUMP_TO_RIGHT);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(),FoodAddActivity.class);
               // getActivity().startActivity(intent);
                startActivityForResult(intent,800);
            }
        });



        recyclerView.setHasFixedSize(true);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    fab.hide();
                }
                else{
                    fab.show();
                }
            }
        });
        helpher=new DatabaseHelpher(getActivity());
        dbList=new ArrayList<DatabaseModel>();
        dbList=helpher.getDataFromDB();
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerAdapter(getActivity(),dbList);
        recyclerView.setAdapter(adapter);




       /* // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(this,dbList);
        mRecyclerView.setAdapter(mAdapter);
*/
        return v;
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        //Log.d("log","result="+resultCode);
        if (resultCode==800)
            Log.d("logii","result="+resultCode);
        adapter.notifyDataSetChanged();
        dbList=helpher.getDataFromDB();
        adapter=new RecyclerAdapter(getActivity(),dbList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }
    /*public  static void addFood(){
       adapter.notifyDataSetChanged();
    }*/



}
