package com.sampledemo.favorite;

public class FavoriteRecyclerAdapterPresenter {
    View view;
    FavoriteRecyclerAdapterPresenter(View view) {
        this.view = view;
    }
    void refreshFragment() {
        view.refreshFragment();
    }

    public interface View {
        void refreshFragment();
    }
}
