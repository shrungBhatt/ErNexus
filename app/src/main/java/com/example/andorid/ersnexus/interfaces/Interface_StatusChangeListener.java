package com.example.andorid.ersnexus.interfaces;

import com.example.andorid.ersnexus.models.AchievementDataGSON;

public interface Interface_StatusChangeListener {

    void updateStatus(String activityId, int statusId);

    void onClick(AchievementDataGSON.List achievementDataGSON,int position);
}
