package com.example.andorid.ersnexus.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.interfaces.Interface_StatusChangeListener;
import com.example.andorid.ersnexus.models.AchievementData;
import com.example.andorid.ersnexus.models.AchievementDataGSON;
import com.example.andorid.ersnexus.util.Const;
import com.example.andorid.ersnexus.util.HashMapGenerator;

import java.util.ArrayList;

public class Adapter_AchievementsList extends RecyclerView.Adapter<Adapter_AchievementsList.Viewholder_Achievements>{

    private Context mContext;
    private ArrayList<AchievementDataGSON.List> mAchievementDatas;
    private Interface_StatusChangeListener mInterfaceStatusChangeListener;


    public Adapter_AchievementsList(Context context,
                                    ArrayList<AchievementDataGSON.List> achievementDatas,
                                    Interface_StatusChangeListener interfaceStatusChangeListener){
        mContext = context;
        mAchievementDatas = achievementDatas;
        mInterfaceStatusChangeListener = interfaceStatusChangeListener;
    }

    @Override
    public Viewholder_Achievements onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new Viewholder_Achievements(inflater,parent);
    }

    @Override
    public void onBindViewHolder(final Viewholder_Achievements holder, final int position) {

        appointmentStatusDisplay(holder,position,mContext);
        holder.bindData(mAchievementDatas.get(position));

        holder.mStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterfaceStatusChangeListener.updateStatus(mAchievementDatas.get(position).getId(),
                        Const.Approved);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterfaceStatusChangeListener.onClick(mAchievementDatas.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAchievementDatas.size();
    }


    private void appointmentStatusDisplay(Viewholder_Achievements holder, int position, Context context) {

        Drawable icon = ContextCompat.getDrawable(context, R.drawable.token_textview_background).mutate();

        int statusId = Integer.valueOf(mAchievementDatas.get(position).getStatus());

        switch (statusId) {
            case Const.Pending:
                holder.mStatusButton.setVisibility(View.GONE);
                setData(holder,context.getResources().getColor(R.color.pending),
                        context.getResources().getColor(R.color.pending),
                        HashMapGenerator.getStatusFromHashMap(statusId),icon);
                break;
            case Const.Rejected:
                holder.mStatusButton.setVisibility(View.GONE);
                setData(holder,context.getResources().getColor(R.color.rejected),
                        context.getResources().getColor(R.color.rejected),
                        HashMapGenerator.getStatusFromHashMap(statusId),icon);
                break;
            case Const.Approved:
                holder.mStatusButton.setVisibility(View.GONE);
                setData(holder,context.getResources().getColor(R.color.approved),
                        context.getResources().getColor(R.color.approved),
                        HashMapGenerator.getStatusFromHashMap(statusId),icon);
                break;
        }


    }

    private void setData(Viewholder_Achievements holder, int colorBg, int colorText, String stringId, Drawable icon) {
        icon.setColorFilter(new PorterDuffColorFilter(colorBg, PorterDuff.Mode.SRC_IN));
        holder.mStatusBackground.setBackground(icon);
        holder.mStatusBackground.setVisibility(View.VISIBLE);
        holder.mStatusText.setText(stringId);
        holder.mStatusText.setTextColor(colorText);
    }

    class Viewholder_Achievements extends RecyclerView.ViewHolder {

        private TextView mStudentErnoTv;
        private TextView mActivityNameTv;
        private TextView mSubActivityNameTv;
        private TextView mActivityLevelTv;
        private TextView mActivityDateETv;
        private TextView mActivityDescriptionTv;
        private Button mStatusButton;
        private LinearLayout mStatusBackground;
        private TextView mStatusText;


        Viewholder_Achievements(LayoutInflater inflater, ViewGroup viewGroup){
            super(inflater.inflate(R.layout.list_item_achievements,viewGroup,false));

            mStudentErnoTv = (TextView) itemView.findViewById(R.id.list_item_achievements_user_erno);
            mActivityNameTv = (TextView) itemView.findViewById(R.id.list_item_achievements_activity_name);
            mSubActivityNameTv = (TextView) itemView.findViewById(R.id.list_item_achievements_sub_activity_name);
            mActivityLevelTv = (TextView) itemView.findViewById(R.id.list_item_achievements_activity_level);
            mActivityDateETv = (TextView) itemView.findViewById(R.id.list_item_achievements_activity_date);
            mActivityDescriptionTv = (TextView) itemView.findViewById(R.id.list_item_achievements_description);
            mStatusBackground = (LinearLayout) itemView.findViewById(R.id.list_item_achievements_status_background);
            mStatusText = (TextView) itemView.findViewById(R.id.list_item_achievements_status_text);
            mStatusButton = (Button) itemView.findViewById(R.id.list_item_achievements_status_button);


        }

        private void bindData(AchievementDataGSON.List achievementData){
            mStudentErnoTv.setText(achievementData.getEnrollmentnumber());
            mActivityNameTv.setText(achievementData.getActivityName());
            mSubActivityNameTv.setText(achievementData.getSubActivity());
            mActivityLevelTv.setText(achievementData.getActivityLevel());
            mActivityDateETv.setText(achievementData.getActivityDate());
            mActivityDescriptionTv.setText(achievementData.getActivityDescription());
        }



    }
}
