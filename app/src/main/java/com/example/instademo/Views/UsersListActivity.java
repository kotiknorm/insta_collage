package com.example.instademo.Views;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.instademo.Controllers.PaginationScrollListener;
import com.example.instademo.Controllers.UsersAdapter;
import com.example.instademo.Objects.Models.UsersList;
import com.example.instademo.R;
import com.example.instademo.Tasks.DownloadUrlsPhotos;
import com.example.instademo.Tasks.SearchUsers;

public class UsersListActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {

    private ListView peopleList;
    private String oldSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.insta_press)));
        getSupportActionBar().setTitle(R.string.titleMain);

        setContentView(R.layout.activity_main);
        peopleList = (ListView) findViewById(R.id.list_people);

        startNewSearch(getString(R.string.redMadRobot));
    }

    private void startNewSearch(String userSearch) {

        if (checkNewSearch(userSearch)) {
            oldSearch = userSearch;
            SearchUsers searchUser = new SearchUsers(this, userSearch);
            searchUser.execute();
        }
    }

    private boolean checkNewSearch(String newWord) {
        return (!oldSearch.equals(newWord));
    }

    public void startDownloadPhoto(final UsersList resultUsers) {

        final UsersAdapter usersAdapter = new UsersAdapter(resultUsers, this);
        peopleList.setAdapter(usersAdapter);
        final PaginationScrollListener pag = new PaginationScrollListener() {
            @Override
            public void startPagination() {
                usersAdapter.downList();
            }
        };

        peopleList.setOnScrollListener(pag);
        peopleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                DownloadUrlsPhotos downloadUrlsPhotos = new DownloadUrlsPhotos(UsersListActivity.this, resultUsers.getUserList().get(position));
                downloadUrlsPhotos.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.searchHint));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        startNewSearch(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() < 1) {
            startNewSearch(getString(R.string.redMadRobot));
        }
        return false;
    }


}
