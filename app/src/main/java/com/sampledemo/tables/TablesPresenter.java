package com.sampledemo.tables;

public class TablesPresenter {
    public interface View {
        void onClick(boolean value);
        void gotoTableDetails(int position);
    }
}
