package com.sampledemo.profile;

class ProfilePresenter {
    private View view;
    ProfilePresenter(View view) {
        this.view = view;
    }
    void updateKeyValueForEmail(String value) {
        view.updateValueInSharedPreferenceForEmail(value);
    }
    void updateKeyValueForHobby(String value) {
        view.updateValueInSharedPreferenceForHobby(value);
    }
    void updateKeyValueForJoin(String value) {
        view.updateValueInSharedPreferenceForJoin(value);
    }
    void setTextForEmail() {
        view.setTextForEmail();
    }
    void setTextForHobby() {
        view.setTextForHobby();
    }
    void setTextForJoin() {
        view.setTextForJoin();
    }
    void showChoiceDialog() {
        view.showChoiceDialog();
    }
    void cameraPermissionAsk() {
        view.cameraPermissionAsk();

    }
    void setTextForUsername() {
        view.setTextForUsername();
    }
    void galleryPermissionAsk() {
        view.galleryPermissionAsk();
    }
    void showRationale(String msg) {
        view.showRationale(msg);
    }
    void captureFromCamera() {
        view.captureFromCamera();
    }
    void getFromGallery() {
        view.getFromGallery();
    }
    void setAvatarImage() {
        view.setAvatarImage();
    }
    public interface View {
        void updateValueInSharedPreferenceForEmail(String value);
        void updateValueInSharedPreferenceForHobby(String value);
        void updateValueInSharedPreferenceForJoin(String value);
        void setTextForUsername();
        void setTextForEmail();
        void setTextForHobby();
        void setTextForJoin();
        void showChoiceDialog();
        void cameraPermissionAsk();
        void galleryPermissionAsk();
        void showRationale(String msg);
        void captureFromCamera();
        void getFromGallery();
        void setAvatarImage();
    }
}
