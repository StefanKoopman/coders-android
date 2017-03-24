package move4mobile.coders.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import move4mobile.coders.R;
import move4mobile.coders.models.GithubUsert;


public class FragmentUserPage  extends Fragment{

    @BindView(R.id.recycler_stats)
    RecyclerView mRecyclerStats;


    private static final String ARG_PERIOD = "period";
    private int periodValue = 0;
    private List<GithubUsert> mListUsers = new ArrayList<>();
    private UserAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        periodValue = args.getInt(ARG_PERIOD);
        mAdapter = new UserAdapter();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference();

        DatabaseReference commits = ref.child("metrics").child("user");

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);

        String monthStr = "";
        month += 1;
        if(month < 10){
            monthStr = "0" + String.valueOf(month);
        }else{
            monthStr = String.valueOf(month);
        }

        String dayStr = "";
        if(day < 10){
            dayStr = "0" + String.valueOf(day);
        }else{
            dayStr = String.valueOf(day);
        }

        String weekStr = "";
        if(weekOfYear < 10){
            weekStr = "0" + String.valueOf(weekOfYear);
        }else{
            weekStr = String.valueOf(weekOfYear);
        }



        DatabaseReference period = null;
        if(periodValue == 0){
            String child = String.valueOf(year)+monthStr+dayStr;
            Log.d("FragmentUser",child);

            period = commits.child("commits_per_day").child(child);
        }else if(periodValue == 1){
            period = commits.child("commits_per_week").child(String.valueOf(year)+weekStr);
        }else{
            String child = String.valueOf(year) + monthStr;
            Log.d("FragmentUser",child);
            period = commits.child("commits_per_month").child(child);
        }


        period.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GithubUsert usert = dataSnapshot.getValue(GithubUsert.class);
                if(!mListUsers.contains(usert)){
                    mListUsers.add(usert);
                    if(mRecyclerStats != null){
                        mAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_page,container,false);
        ButterKnife.bind(this,v);

        mRecyclerStats.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerStats.setAdapter(mAdapter);






        return v;
    }

    class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

        UserAdapter(){
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user, parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            GithubUsert user = mListUsers.get(position);

            holder.mTextName.setText(user.getName());
            holder.mTextScore.setText(user.getScore()+"");
        }

        @Override
        public int getItemCount() {
            return mListUsers.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            View mLayout;
            TextView mTextName, mTextScore;
            ViewHolder(View v){
                super(v);
                mLayout = v;
                mTextName = (TextView) mLayout.findViewById(R.id.text_name);
                mTextScore = (TextView) mLayout.findViewById(R.id.text_score);
            }
        }


    }
}
