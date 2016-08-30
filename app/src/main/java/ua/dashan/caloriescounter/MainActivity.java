package ua.dashan.caloriescounter;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ua.dashan.caloriescounter.Adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
          //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
           // setSupportActionBar(toolbar);
               TabLayout tab_layout = (TabLayout) findViewById(R.id.tab_layout);
                tab_layout.addTab(tab_layout.newTab().setText("Рацион"));
                tab_layout.addTab(tab_layout.newTab().setText("Счётчик"));
                tab_layout.addTab(tab_layout.newTab().setText("Календарь"));
             final ViewPager view_pager = (ViewPager) findViewById(R.id.pager);
              final ViewPagerAdapter adapter = new ViewPagerAdapter
        (getSupportFragmentManager(), tab_layout.getTabCount());
               view_pager.setAdapter(adapter);
               view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
               tab_layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                     @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    view_pager.setCurrentItem(tab.getPosition());
            }
                     @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                         }
                      @Override
            public void onTabReselected(TabLayout.Tab tab) {
                          }
        });
    }
   /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.<br />
            getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
      @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will<br />
        // automatically handle clicks on the Home/Up button, so long<br />
        // as you specify a parent activity in AndroidManifest.xml.<br />
        int id = item.getItemId();
            //noinspection SimplifiableIfStatement<br />
        if (id == R.id.action_settings) {
            return true;
        }
          return super.onOptionsItemSelected(item);
    }*/
}
