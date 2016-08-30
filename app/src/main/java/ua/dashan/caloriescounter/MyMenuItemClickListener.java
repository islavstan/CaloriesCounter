package ua.dashan.caloriescounter;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
                return false;
        }
}
/*
private List<DatabaseModel>list=new ArrayList<DatabaseModel>();
        private DatabaseHelpher helpher=new DatabaseHelpher();
private int position;
public MyMenuItemClickListener(int positon) {
        this.position=positon;
        }

@Override
public boolean onMenuItemClick(MenuItem menuItem) {
       switch (menuItem.getItemId()) {

      case R.id.delete_menu:

       // String RemoveCategory=mDataSet.get(position).getCategory();
//                    mDataSet.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position,mDataSet.size());

        //mySharedPreferences.saveStringPrefs(SyncStateContract.Constants.REMOVE_CTAGURY,RemoveCategory,MainActivity.context);
       // Toast.makeText(getActivity(), "Add to favourite", Toast.LENGTH_SHORT).show();
        return true;
       */
/* case R.id.No_interasted:
        mDataSet.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mDataSet.size());
        Toast.makeText(MainActivity.context, "Done for now", Toast.LENGTH_SHORT).show();
        return true;
        case R.id.delete:
        mySharedPreferences.deletePrefs(Constants.REMOVE_CTAGURY,MainActivity.context);*//*

default:
        }
        return false;
        }
        }

*/
