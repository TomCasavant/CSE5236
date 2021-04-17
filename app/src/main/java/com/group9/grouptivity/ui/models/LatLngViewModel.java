package com.group9.grouptivity.ui.models;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.group9.grouptivity.firebase.models.GroupMessage;

public class LatLngViewModel extends ViewModel {
    private LatLng mLatLng;

    /*** Gets the LatLng stored in this ViewModel. Requires that there exists a LatLng stored here (check via a call to hasLatLng). */
    public LatLng getLatLng() {
        return this.mLatLng;
    }

    /** Sets the LatLng to store in this ViewModel. */
    public void setLatLng(LatLng latLng) {
        this.mLatLng = latLng;
    }

    /** Returns true if there exists a LatLng stored in this ViewModel */
    public boolean hasLatLng() {
        return this.mLatLng != null;
    }
}
