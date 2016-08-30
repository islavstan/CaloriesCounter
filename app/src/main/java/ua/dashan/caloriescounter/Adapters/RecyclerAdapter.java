package ua.dashan.caloriescounter.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ua.dashan.caloriescounter.Database.DatabaseHelpher;
import ua.dashan.caloriescounter.Database.DatabaseModel;
import ua.dashan.caloriescounter.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    static List<DatabaseModel> dbList;
    static Context context;
    DatabaseHelpher helpher;


    public RecyclerAdapter(Context context, List<DatabaseModel> dbList ){
        this.dbList = new ArrayList<DatabaseModel>();
        this.context = context;
        this.dbList = dbList;
        helpher=new DatabaseHelpher(context);

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(R.layout.item_row, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {

        holder.name.setText(dbList.get(position).getFoodName());
        holder.calories.setText(dbList.get(position).getCalories()+" каллорий");
        holder.food_image.setImageBitmap(dbList.get(position).getImage());
        holder.img_row_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.img_row_delete,position);
            }
        });

    }
    private void deleteItem(int position){

        notifyItemRemoved(position);
        notifyItemRangeChanged(position,dbList.size());
        dbList.remove(position);
    }
    public void addItem(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name,calories;
        public CircleImageView food_image;
        public ImageView img_row_delete;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView
                    .findViewById(R.id.food_name);
            calories = (TextView)itemLayoutView.findViewById(R.id.food_calories);
            food_image=(CircleImageView)itemLayoutView.findViewById(R.id.food_image);
            img_row_delete=(ImageView)itemLayoutView.findViewById(R.id.img_row_delete);
            itemLayoutView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
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
    private  void showPopupMenu(View view,final int  position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(),view );
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                             @Override
                                             public boolean onMenuItemClick(MenuItem item) {
                                                 switch (item.getItemId()) {
                                                     case R.id.delete_menu:
                                                         //dbList.remove(position);
                                                       helpher.deleteARow(dbList.get(position).getFoodName());
                                                         deleteItem(position);
                                                        // notifyDataSetChanged();

                                                        // notifyItemRemoved(position);
                                                         //notifyItemRangeChanged(position,dbList.size());
                                                         return true;
                                                     default:}

                                                 return false;
                                             }});


                popup.show();
    }
}