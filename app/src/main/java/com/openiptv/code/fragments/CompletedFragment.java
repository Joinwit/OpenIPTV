package com.openiptv.code.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.leanback.app.GuidedStepSupportFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;

import com.openiptv.code.PreferenceUtils;
import com.openiptv.code.R;
import com.openiptv.code.epg.EPGService;

import java.util.List;

import static com.openiptv.code.Constants.PREFERENCE_SETUP_COMPLETE;

public class CompletedFragment extends GuidedStepSupportFragment {
    private static final int ACTION_ID_SETTINGS = 1;
    private static final int ACTION_ID_COMPLETE = 2;
    private static final String TAG = SetupActivity.class.getName();

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {

        return new GuidanceStylist.Guidance(
                "Completed",
                "All content for your account has been synced. Channels, Programs and Recordings are all linked to your TVHeadend Server",
                getString(R.string.account_label),
                null);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        GuidedAction action = new GuidedAction.Builder(getActivity())
                .id(ACTION_ID_COMPLETE)
                .title("Finish")
                .description("Exit Setup Wizard")
                .editable(false)
                .build();

        actions.add(action);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        if (action.getId() == ACTION_ID_COMPLETE) {

            PreferenceUtils preferenceUtils = new PreferenceUtils(getActivity());
            preferenceUtils.setBoolean(PREFERENCE_SETUP_COMPLETE, true);

            Log.d(TAG, "Exiting Setup!");
            Intent intent = new Intent(getActivity(), EPGService.class);
            getActivity().startService(intent);

            // Wrap up setup
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();

        }
    }
}
