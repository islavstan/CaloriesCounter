package ua.dashan.caloriescounter;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;
import ua.dashan.caloriescounter.Database.DatabaseModel;

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.HorizontalViewHolder> {

    static List<DatabaseModel> dbList;
    static Context context;
   static DatabaseHelpher helpher;

    private static final java.text.DateFormat FORMATTER = SimpleDateFormat.getDateInstance();


    HorizontalRecyclerAdapter(Context context, List<DatabaseModel> dbList ){
      this.dbList = new ArrayList<DatabaseModel>();
        this.context = context;
        this.dbList = dbList;
        helpher=new DatabaseHelpher(context);

    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row_horizontal, null);

        // create ViewHolder

        HorizontalViewHolder viewHolder = new HorizontalViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        holder.name.setText(dbList.get(position).getFoodName());
        holder.calories.setText(dbList.get(position).getCalories()+" каллорий");
        holder.food_image.setImageBitmap(dbList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }


    public static class HorizontalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        DatabaseHelpher helpher;
       static int cal;


        public TextView name,calories;

        public CircleImageView food_image;

        public HorizontalViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            helpher=new DatabaseHelpher(context);
            name = (TextView) itemLayoutView
                    .findViewById(R.id.food_name);
            calories = (TextView)itemLayoutView.findViewById(R.id.food_calories);
            food_image=(CircleImageView)itemLayoutView.findViewById(R.id.food_image);
            itemLayoutView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
       cal=helpher.getCaloriesFromDB(dbList.get(getAdapterPosition()).getFoodName());
              final int one = 0X0001;
             CaloriesCounterFragment.handler.sendEmptyMessageDelayed(one,1000);
            CalendarDay day = new CalendarDay();
           String date =FORMATTER.format(day.getDate());
            helpher.insertCaloriesCountIntoDB(date,cal);
            int progressFromDb=helpher.getCaloriesCount(date);
            CaloriesCounterFragment.progress=helpher.getCaloriesCountForCalendar(date);
            CaloriesCounterFragment.progressText.setText( progressFromDb+" / "+"2000");








            /*Intent intent = new Intent(context,DetailsActivity.class);

            Bundle extras = new Bundle();
            extras.putInt("position",getAdapterPosition());
            intent.putExtras(extras);

            *//*
            int i=getAdapterPosition();
            intent.putExtra("position", getAdapterPosition());*//*
            context.startActivity(intent);
            Toast.makeText(RecyclerAdapter.context, "you have clicked Row " + getAdapterPosition(), Toast.LENGTH_LONG).show();*/
        }
    }

}

