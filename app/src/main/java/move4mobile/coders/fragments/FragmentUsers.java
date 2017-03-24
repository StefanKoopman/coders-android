package move4mobile.coders.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import move4mobile.coders.R;

/**
 * Fragment to show user leaderbord
 */
public class FragmentUsers extends Fragment {

    private DatabaseReference mRef;             // Users data ref

    @BindView(R.id.tablayout)                   // Tabbar
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    public enum PERIOD{
        DAY,WEEK,MONTH
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference("users");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users,container,false);
        ButterKnife.bind(this,v);

        setUpTabLayout();
        setUpFbListener();
        setUpViewPager();

        return v;
    }

    private void setUpViewPager(){
        // dus dit
        mViewPager.setAdapter(new UserPagerAdapter(getChildFragmentManager()));
    }

    /**
     * Listen for realtime data changes
     */
    private void setUpFbListener(){
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Do something hot
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Functionality of switching tabs
     */
    private void setUpTabLayout(){
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int pos = tab.getPosition();
//                mViewPager.setCurrentItem(pos,true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    class UserPagerAdapter extends FragmentPagerAdapter{

        public UserPagerAdapter(FragmentManager supportFragmentManager){
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                case 1:
                case 2:
                    Fragment f = new FragmentUserPage();
                    Bundle args = new Bundle();
                    args.putInt("period",position);
                    f.setArguments(args);
                    return f;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "DAY";
                case 1:
                    return "WEEK";
                case 2:
                    return "MONTH";
                default:
                    return null;

            }
        }
    }


}
