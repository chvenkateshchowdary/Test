package com.example.cyclone.cyclone;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import com.example.cyclone.cyclone.data.LogReader;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import layout.Two;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    LogReader LogReader1 = new LogReader(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);

        /* Code by us */
        /* ********************************************************************************** */

        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);

        if (isFirstRun) {
            try {
                get();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        } else {
            try {
                get1();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }


        /* ********************************************************************************** */
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

           if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                View rootView = inflater.inflate(R.layout.fragment_one, container, false);

                return rootView;
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                   View rootView = inflater.inflate(R.layout.fragment_two, container, false);
                   return rootView;
               } else {
                   View rootView = inflater.inflate(R.layout.fragment_three, container, false);
                   return rootView;
               }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        PlaceholderFragment palce = new PlaceholderFragment();
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return palce.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return " TOTAL USAGE ";
                case 1:
                    return " TODAY ";
                case 2:
                    return " GRAPHS ";
            }
            return null;
        }
    }

    /* Code by us */
    /* ********************************************************************************** */

   /*public View displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor logCursor = db.rawQuery("SELECT rowid _id,name,Time_Used,app_Image FROM logs ORDER BY Time_Used DESC", null);
        LogCursorAdapter logAdapter = new LogCursorAdapter(this, logCursor);
        ListView lvlogItems = (ListView)  findViewById(R.id.list_item);
        lvlogItems.setAdapter((logAdapter));
        return lvlogItems;
    }*/

    /* ********************************************************************************** */


    /* Code by us */
    /* ********************************************************************************** */

    public void get() throws PackageManager.NameNotFoundException {
        //  Creating an object for UsageStates class(Contains usage statistics for an app package for specific time range)
        Context context = this;

        // variable for Package Name
        String PackageName = "";

        // variable for Application Name
        String AppName = "";

        // variables for calculating time
        long TimeInForGround = 0;

        int AppUsageTime = 0, hours = 0, seconds = 0;

        // Creating an Object for UsageStatesManager class( accesses for system time logs)
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        long time = System.currentTimeMillis();


        List<UsageStats> stats = null;

        stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 2000 * 60, time);


        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();

            for (UsageStats usageStats : stats) {

                TimeInForGround = usageStats.getTotalTimeInForeground();

                PackageName = usageStats.getPackageName();

                AppUsageTime = (int) ((TimeInForGround / (1000 * 60)) % 60);

                PackageManager packageManager = context.getPackageManager();

                ApplicationInfo applicationInfo = null;

                try {

                    applicationInfo = packageManager.getApplicationInfo(PackageName, 0);

                } catch (final PackageManager.NameNotFoundException e) {
                }


                AppName = (String) ((applicationInfo != null) ? packageManager.getApplicationLabel(applicationInfo) : "???");

                Log.i("BAC", "PackageName :   " + AppName + " Time : " + AppUsageTime);

                Drawable icon = packageManager.getApplicationIcon(PackageName);
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();

                LogReader1.insertData(AppName, AppUsageTime, image);

// d3 js and pi chart
            }

        }
    }

    /* ********************************************************************************** */


    /* Code by us */
    /* ********************************************************************************** */

    public void get1() throws PackageManager.NameNotFoundException {
        //  Creating an object for UsageStates class(Contains usage statistics for an app package for specific time range)
        Context context = this;

        // variable for Package Name
        String PackageName = "";

        // variable for Application Name
        String AppName = "";

        // variables for calculating time
        long TimeInForGround = 0;

        int AppUsageTime = 0, hours = 0, seconds = 0;

        // Creating an Object for UsageStatesManager class( accesses for system time logs)
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        long time = System.currentTimeMillis();


        List<UsageStats> stats = null;

        stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 2000 * 60, time);


        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();

            for (UsageStats usageStats : stats) {

                TimeInForGround = usageStats.getTotalTimeInForeground();

                PackageName = usageStats.getPackageName();

                AppUsageTime = (int) ((TimeInForGround / (1000 * 60)) % 60);

                PackageManager packageManager = context.getPackageManager();

                ApplicationInfo applicationInfo = null;

                try {

                    applicationInfo = packageManager.getApplicationInfo(PackageName, 0);

                } catch (final PackageManager.NameNotFoundException e) {
                }
                Drawable icon = packageManager.getApplicationIcon(PackageName);
                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();

                AppName = (String) ((applicationInfo != null) ? packageManager.getApplicationLabel(applicationInfo) : "???");

                Log.i("BAC", "PackageName :   " + AppName + " Time : " + AppUsageTime);


                LogReader1.updateData(AppName, AppUsageTime, image);

// d3 js and pi chart
            }
        }
    }

    /* ********************************************************************************** */
}
