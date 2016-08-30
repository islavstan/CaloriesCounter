package ua.dashan.caloriescounter.Adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ua.dashan.caloriescounter.CalendarFragment;
import ua.dashan.caloriescounter.CaloriesCounterFragment;
import ua.dashan.caloriescounter.UserFoodInformationFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public ViewPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UserFoodInformationFragment tab1 = new UserFoodInformationFragment();
                return tab1;
            case 1:
                CaloriesCounterFragment tab2 = new CaloriesCounterFragment();
                return tab2;
            case 2:
                CalendarFragment tab3 = new CalendarFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
    return mNumOfTabs;
    }
}
