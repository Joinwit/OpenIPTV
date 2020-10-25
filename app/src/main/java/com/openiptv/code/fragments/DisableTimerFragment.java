package com.openiptv.code.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.leanback.app.GuidedStepSupportFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;

import com.openiptv.code.DatabaseActions;
import com.openiptv.code.PreferenceUtils;
import com.openiptv.code.R;

import java.util.List;

public class DisableTimerFragment extends GuidedStepSupportFragment {
    final static long USERNAME = 1L;
    final static long PASSWORD = 2L;
    final static long SIGNIN = 3L;
    final static long CANCEL = 4L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {

        return new GuidanceStylist.Guidance(
                getString(R.string.disable),
                getString(R.string.disable_des),
                getString(R.string.account_label),
                ContextCompat.getDrawable(getActivity(), R.drawable.standard));
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        GuidedAction username = new GuidedAction.Builder(getActivity())
                .title("")
                .description(R.string.username)
                .editable(true)
                .id(USERNAME)
                .build();
        GuidedAction password = new GuidedAction.Builder(getActivity())
                .title("")
                .description(R.string.password)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .editInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .editable(true)
                .id(PASSWORD)
                .build();
        GuidedAction signIn = new GuidedAction.Builder(getActivity())
                .title(R.string.signin)
                .editable(false)
                .id(SIGNIN)
                .build();
        GuidedAction cancel = new GuidedAction.Builder(getActivity())
                .title(R.string.cancel)
                .editable(false)
                .id(CANCEL)
                .build();
        actions.add(username);
        actions.add(password);
        actions.add(signIn);
        actions.add(cancel);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        long id = action.getId();

        if (id == SIGNIN) {
            DatabaseActions dbAction = new DatabaseActions(getContext());
            String username = findActionById(USERNAME).getTitle().toString().trim();
            String password = findActionById(PASSWORD).getTitle().toString().trim();

            boolean result = dbAction.checkParentControlPassword(username, password);
            dbAction.close();


            if (result == false) {
                Toast.makeText(getContext(), "The account username or parent control password are not correct" , Toast.LENGTH_SHORT).show();
            } else if (result) {
                PreferenceUtils preferenceUtils = new PreferenceUtils(getContext());
                preferenceUtils.setBoolean("pcEnable", false);
                Toast.makeText(getContext(), "Timer is disabled!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        } else if (id == CANCEL) {
            getActivity().finish();
        }
    }
}
