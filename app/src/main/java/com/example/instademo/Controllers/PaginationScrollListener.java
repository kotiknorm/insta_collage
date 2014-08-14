package com.example.instademo.Controllers;

import android.widget.AbsListView;

import com.example.instademo.Objects.Constants;

/**
 * Created by alexey on 11.08.14.
 */

public abstract class PaginationScrollListener implements AbsListView.OnScrollListener {

    private Pagination pagination = new Pagination();

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount > 0) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if ((lastItem == totalItemCount) && (pagination.isPagination()) && (firstVisibleItem != 0)) {
                pagination.stop();
                startPagination();
                up();
            }
        }
    }

    public abstract void startPagination();

    public void up() {
        pagination.up();
    }

    public int getOffset() {
        return pagination.offsetPagination;
    }

    public void refresh() {
        pagination.offsetPagination = 0;
        pagination.isPagination = true;
    }

    private final static class Pagination {

        private boolean isPagination = true;

        private int offsetPagination = 0;

        private void up() {
            isPagination = true;
            offsetPagination = offsetPagination + Constants.COUNT_PAGINATION;
        }

        private void stop() {
            isPagination = false;
        }

        private boolean isPagination() {
            return isPagination;
        }


    }
}
