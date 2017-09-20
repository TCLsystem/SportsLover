package com.example.user.sportslover.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.user.sportslover.dto.User;
import com.example.user.sportslover.model.UserModel;
import com.example.user.sportslover.user.LoginActivity;

/**
 * Created by user on 17-9-16.
 */

    public class UserFragmentPresenter {
        private UserModel mUserModel = new UserModel();

        public UserFragmentPresenter() {
        }

        /**
         * 登录
         *
         * @param context
         */
        public void onLogin(Context context) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }

        /**
         * 发说说
         *
         * @param context
         */
        public void onSendDynamic(Context context, User user) {
//            if (mUserModel.isLogin()) {
//                Intent intent = new Intent(context, SendDynamicActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("User", user);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            } else {
//                onLogin(context);
//            }
        }

    }
