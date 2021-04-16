package com.group9.grouptivity.ui.login;

import android.app.Activity;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group9.grouptivity.R;
import com.group9.grouptivity.data.LoginRepository;
import com.group9.grouptivity.firebase.FirebaseRTDBHelper;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, Activity activity) {
        // can be launched in a separate asynchronous job
        FirebaseRTDBHelper.getInstance().login(username, password, activity);
        if (FirebaseRTDBHelper.getInstance().isLoggedIn()) {
            loginResult.setValue(new LoginResult(new LoggedInUserView("test")));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }
    public void signUp(String username, String password, String nickname, Activity activity) {
        // can be launched in a separate asynchronous job
        FirebaseRTDBHelper.getInstance().createAccount(username, password, nickname, activity);
        if (FirebaseRTDBHelper.getInstance().isLoggedIn()) {
            loginResult.setValue(new LoginResult(new LoggedInUserView("test")));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}