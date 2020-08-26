package com.product.as.merchants.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meikoz.core.adapter.RecycleAdapter;
import com.meikoz.core.base.BaseActivity;
import com.product.as.merchants.R;
import com.product.as.merchants.adapter.AdministratorsAdapter;
import com.product.as.merchants.adapter.MemberAdapter;
import com.product.as.merchants.adapter.OnShelvesAdapter;
import com.product.as.merchants.api.ApiInterface;
import com.product.as.merchants.entity.UserLinkEntity;
import com.product.as.merchants.entity.UserListEntity;
import com.product.as.merchants.entity.WholesalerInfoEntity;
import com.product.as.merchants.model.Constants;
import com.product.as.merchants.popuwindow.MemberManagementPopuWindow;
import com.product.as.merchants.popuwindow.SelectMemberPopuWindow;
import com.product.as.merchants.util.NoticeObserver;
import com.product.as.merchants.util.SharedPreferencesUtils;
import com.product.as.merchants.util.ToastUtil;
import com.product.as.merchants.util.UiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//成员管理
public class MemberManagementActivity extends BaseActivity implements NoticeObserver.Observer {

    @Bind(R.id.rcyAdministrators)
    RecyclerView rcyAdministrators;
    @Bind(R.id.rcymember)
    RecyclerView rcymember;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_titles)
    TextView tvTitles;
    List<UserListEntity.DataBean> listEntities;
    List<UserListEntity.DataBean> listEntities1 = new ArrayList<>();
    List<UserListEntity.DataBean> listEntities2 = new ArrayList<>();
    @Bind(R.id.iv_right)
    ImageView ivRight;
    private AdministratorsAdapter administratorsAdapter;
    private MemberAdapter memberAdapter;
    private MemberManagementPopuWindow memberManagementPopuWindow;
    View rootView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_member_management;
    }

    @Override
    protected void onInitialization(Bundle bundle) throws IOException {
        NoticeObserver.getInstance().addObserver(this);
        String roleid = String.valueOf(SharedPreferencesUtils.getSp(Constants.ROLEID, ""));
        if (roleid.equals("1")) {
            ivRight.setImageResource(R.mipmap.add);
            ivRight.setOnClickListener(v -> {
                startActivity(AddMemberActivity.class);
            });
        }
        ivBack.setOnClickListener(v -> {
            finish();
        });
        tvTitles.setText("成员管理");
        rcyAdministrators.setLayoutManager(new LinearLayoutManager(this));
        rcymember.setLayoutManager(new LinearLayoutManager(this));
        userlist(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
    }

    public void userlist(String sid) {
        ApiInterface.ApiFactory.createApi().userlist(sid).enqueue(new Callback<UserListEntity>() {
            @Override
            public void onResponse(Call<UserListEntity> call, Response<UserListEntity> response) {
                if (response.body().getFlag() == 1) {
                    listEntities = response.body().getData();
                    if (listEntities1.size() > 0) {
                        listEntities1.clear();
                    }
                    if (listEntities2.size() > 0) {
                        listEntities2.clear();
                    }
                    for (int i = 0; i < listEntities.size(); i++) {
                        if (listEntities.get(i).getRole_id() == 1) {
                            listEntities1.add(listEntities.get(i));
                        } else {
                            listEntities2.add(listEntities.get(i));
                        }
                    }
                    if (administratorsAdapter == null) {
                        administratorsAdapter = new AdministratorsAdapter(MemberManagementActivity.this, R.layout.item_membermanagement, listEntities1);
                        rcyAdministrators.setAdapter(administratorsAdapter);
                    } else {
                        administratorsAdapter.notifyDataSetChanged();
                    }
                    if (memberAdapter == null) {
                        memberAdapter = new MemberAdapter(MemberManagementActivity.this, R.layout.item_membermanagement, listEntities2);
                        rcymember.setAdapter(memberAdapter);
                    } else {
                        memberAdapter.notifyDataSetChanged();
                    }

                    administratorsAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            String roleid = String.valueOf(SharedPreferencesUtils.getSp(Constants.ROLEID, ""));
                            if (roleid.equals("1")) {
                                memberManagementPopuWindow = new MemberManagementPopuWindow(MemberManagementActivity.this, v2 -> {
                                    UiUtils.backgroundAlpha(MemberManagementActivity.this, 1.0f);
                                    if (String.valueOf(SharedPreferencesUtils.getSp(Constants.UID, "")).equals(listEntities1.get(position).getUid() + "")) {
                                        ToastUtil.show("禁止删除自己!",200);
                                    }else {
                                        userunlink(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), listEntities1.get(position).getUid() + "");
                                    }

                                    memberManagementPopuWindow.dismiss();
                                }, v2 -> {
                                    UiUtils.backgroundAlpha(MemberManagementActivity.this, 1.0f);
                                    memberManagementPopuWindow.dismiss();
                                });
                                rootView = LayoutInflater.from(MemberManagementActivity.this)
                                        .inflate(R.layout.activity_main, null);
                                memberManagementPopuWindow.showAtLocation(rootView,
                                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            }

                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });
                    memberAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                            String roleid = String.valueOf(SharedPreferencesUtils.getSp(Constants.ROLEID, ""));
                            if (roleid.equals("1")) {
                                memberManagementPopuWindow = new MemberManagementPopuWindow(MemberManagementActivity.this, v2 -> {
                                    UiUtils.backgroundAlpha(MemberManagementActivity.this, 1.0f);
                                        userunlink(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")), listEntities2.get(position).getUid() + "");
                                    memberManagementPopuWindow.dismiss();
                                }, v2 -> {
                                    UiUtils.backgroundAlpha(MemberManagementActivity.this, 1.0f);
                                    memberManagementPopuWindow.dismiss();
                                });
                                rootView = LayoutInflater.from(MemberManagementActivity.this)
                                        .inflate(R.layout.activity_main, null);
                                memberManagementPopuWindow.showAtLocation(rootView,
                                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                            }
                        }

                        @Override
                        public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                            return false;
                        }
                    });

                } else {
                    rcyAdministrators.setVisibility(View.GONE);
                    rcymember.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserListEntity> call, Throwable t) {

            }
        });
    }


    public void userunlink(String sid, String uid) {
        ApiInterface.ApiFactory.createApi().userunlink(sid, uid).enqueue(new Callback<UserLinkEntity>() {
            @Override
            public void onResponse(Call<UserLinkEntity> call, Response<UserLinkEntity> response) {

                if (response.body().getFlag() == 1) {
                    ToastUtil.show(response.body().getMsg(), 200);
                    userlist(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
                } else {
                    ToastUtil.show(response.body().getMsg(), 200);
                }

            }

            @Override
            public void onFailure(Call<UserLinkEntity> call, Throwable t) {

            }
        });
    }

    @Override
    public <T> void update(int what, T t) {
        if (what == Constants.AddMemNextber) {
            userlist(String.valueOf(SharedPreferencesUtils.getSp(Constants.SID, "")));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NoticeObserver.getInstance().removeObserver(this);
    }

}
