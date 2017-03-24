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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import move4mobile.coders.AppController;
import move4mobile.coders.R;
import move4mobile.coders.models.Repo;
import move4mobile.coders.network.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentRepos extends Fragment {

    @BindView(R.id.list_repos)
    RecyclerView mRecyclerRepos;

    private List<Repo> mListRepos = new ArrayList<>();
    private RepoAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = "https://api.github.com/users/" + AppController.id + "/subscriptions";
        Log.d("FragmentRepos",url);

        NetworkUtil.getRetroController().getRepos(url).enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                mListRepos.addAll(response.body());
                Log.d("FragmentRepos",mListRepos.size() + "");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

        mAdapter = new RepoAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_repos,container,false);
        ButterKnife.bind(this,v);

        setUpRecycler();

        return v;
    }


    private void setUpRecycler(){
        mRecyclerRepos.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerRepos.setAdapter(mAdapter);
    }

    class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

        RepoAdapter(){
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repo, parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Repo repo = mListRepos.get(position);

            holder.mTextName.setText(repo.getName());
            holder.mTextLocation.setText(repo.getFull_name());
        }

        @Override
        public int getItemCount() {
            return mListRepos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            View mLayout;
            TextView mTextName, mTextLocation;
            ViewHolder(View v){
                super(v);
                mLayout = v;
                mTextName = (TextView) mLayout.findViewById(R.id.text_title);
                mTextLocation = (TextView) mLayout.findViewById(R.id.text_location);
            }
        }


    }


}
