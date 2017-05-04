package ultramirinc.champs_mood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ultramirinc.champs_mood.fragments.FriendsFragment;
import ultramirinc.champs_mood.fragments.HomeFragment;
import ultramirinc.champs_mood.fragments.NotificationFragment;
import ultramirinc.champs_mood.fragments.ProfilFragment;
import ultramirinc.champs_mood.fragments.SearchFragment;
import ultramirinc.champs_mood.fragments.MyViewPager;
import ultramirinc.champs_mood.managers.UserManager;
import ultramirinc.champs_mood.models.User;

public class TabActivity extends AppCompatActivity {

    /*
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /*
     * The {@link ViewPager} that will host the section contents.
     */

    private MyViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Moods");
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        progressDialog = new ProgressDialog(this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (MyViewPager) findViewById(R.id.container);
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        loadUserInformationsSetupViewPager(mViewPager);


    }

    private void loadUserInformationsSetupViewPager(MyViewPager viewPager) {

        //Verify if a user is connected
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //NOT CONNECTED redirect to login.
            UserManager.getInstance().ClearCurrentUser();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else {

            progressDialog.setMessage("Loading user data...");
            progressDialog.show();
            DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
            Query userQuery = databaseUsers.orderByChild("id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = null;
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        removeKeyboard();
                        user = singleSnapshot.getValue(User.class);
                        UserManager.getInstance().setCurrentUser(user);
                        setupViewPager(viewPager);

                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.addTab(tabLayout.newTab().setText("Home"));
                        tabLayout.addTab(tabLayout.newTab().setText("Friends"));
                        tabLayout.addTab(tabLayout.newTab().setText("Search"));
                        tabLayout.addTab(tabLayout.newTab().setText("Notif"));
                        tabLayout.addTab(tabLayout.newTab().setText("Profil"));

                        tabLayout.setupWithViewPager(mViewPager);

                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_home);
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_firends);
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_search);
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_action_notif);
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_action_profil);
                        progressDialog.hide();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            userQuery.addListenerForSingleValueEvent(postListener);
        }
    }

    private void setupViewPager(MyViewPager viewPager){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new HomeFragment());
        mSectionsPagerAdapter.addFragment(new FriendsFragment());
        mSectionsPagerAdapter.addFragment(new SearchFragment());
        mSectionsPagerAdapter.addFragment(new NotificationFragment());
        mSectionsPagerAdapter.addFragment(new ProfilFragment());
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void removeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }





    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /*
        @Override
        public CharSequence getPageTitle(int position) {

            return mFragmentTitleList.get(position);
        }
        */

    }

    @Override
    public void onBackPressed(){
        finish();
    }


}
