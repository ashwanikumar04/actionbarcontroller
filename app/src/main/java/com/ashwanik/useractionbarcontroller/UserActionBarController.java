/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ashwanik.useractionbarcontroller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.TextView;

public class UserActionBarController {
    private View mBarView;
    private TextView mMessageView;
    private ViewPropertyAnimator mBarAnimator;
    private Handler mHideHandler = new Handler();
    private Button actionButton;
    private ActionListener mActionListener;

    public int getHideTime() {
        return hideTime;
    }

    public void setHideTime(int hideTime) {
        this.hideTime = hideTime;
    }

    private int hideTime;
    // State objects
    private Parcelable mActionToken;
    private CharSequence mActionMessage;
    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hideMessageBar(false);
        }
    };

    public UserActionBarController(View ActionBarView, ActionListener ActionListener, int actionBarMessage, int actionBarButton, int actionIconId) {
        mBarView = ActionBarView;
        mBarAnimator = mBarView.animate();
        mActionListener = ActionListener;

        mMessageView = (TextView) mBarView.findViewById(actionBarMessage);
        actionButton = (Button) mBarView.findViewById(actionBarButton);
        actionButton.setCompoundDrawablesWithIntrinsicBounds(actionIconId, 0, 0, 0);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideMessageBar(false);
                mActionListener.onAction(mActionToken);
            }
        });

        hideMessageBar(true);
    }

    public void showMessageBar(boolean immediate, CharSequence message, Parcelable ActionToken, CharSequence actionText) {
        mActionToken = ActionToken;
        mActionMessage = message;
        mMessageView.setText(mActionMessage);

        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, getHideTime() == 0 ? 5000 : getHideTime());
        if (actionText != "") {
            actionButton.setText(actionText);
        }
        mBarView.setVisibility(View.VISIBLE);
        if (immediate) {
            mBarView.setAlpha(1);
        } else {
            mBarAnimator.cancel();
            mBarAnimator
                    .alpha(1)
                    .setDuration(
                            mBarView.getResources()
                                    .getInteger(android.R.integer.config_shortAnimTime))
                    .setListener(null);
        }
    }

    public void hideMessageBar(boolean immediate) {
        mHideHandler.removeCallbacks(mHideRunnable);
        if (immediate) {
            mBarView.setVisibility(View.GONE);
            mBarView.setAlpha(0);
            mActionMessage = null;
            mActionToken = null;

        } else {
            mBarAnimator.cancel();
            mBarAnimator
                    .alpha(0)
                    .setDuration(mBarView.getResources()
                            .getInteger(android.R.integer.config_shortAnimTime))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mBarView.setVisibility(View.GONE);
                            mActionMessage = null;
                            mActionToken = null;
                        }
                    });
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("Action_message", mActionMessage);
        outState.putParcelable("Action_token", mActionToken);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mActionMessage = savedInstanceState.getCharSequence("Action_message");
            mActionToken = savedInstanceState.getParcelable("Action_token");

            if (mActionToken != null || !TextUtils.isEmpty(mActionMessage)) {
                showMessageBar(true, mActionMessage, mActionToken, "");
            }
        }
    }

    public interface ActionListener {
        void onAction(Parcelable token);
    }
}
