/*
 * ActivityDiary
 *
 * Copyright (C) 2017 Raphael Mack http://www.raphael-mack.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.rampro.activitydiary.ui.main;



import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import de.rampro.activitydiary.R;
import de.rampro.activitydiary.helpers.GraphicsHelper;
import de.rampro.activitydiary.model.DiaryActivity;

public class SelectRecyclerViewAdapter extends RecyclerView.Adapter<SelectViewHolders>{
    //private List<DiaryActivity> mActivityList;
    private HashMap<Integer, List<DiaryActivity>> mActivityListGroup;
    private SelectListener mSelectListener;

    public SelectRecyclerViewAdapter(SelectListener selectListener, List<DiaryActivity> activityList){
         // mActivityList = activityList;
        mActivityListGroup = new HashMap<>();
//        for(List<DiaryActivity> i : mActivityListGroup){
//            i = new ArrayList<>(0);
//        }
//        for(DiaryActivity i : activityList){
//            mActivityListGroup.get(i.getMtype()).add(i);
//        }
        for(DiaryActivity i : activityList){
            if(mActivityListGroup.containsKey(i.getMtype())) mActivityListGroup.get(i.getMtype()).add(i);
            else {
                mActivityListGroup.put(i.getMtype(), new ArrayList<DiaryActivity>());
                mActivityListGroup.get(i.getMtype()).add(i);
            }
        }
        mSelectListener = selectListener;
        setHasStableIds(true);
    }

    @Override
    public SelectViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_part ,parent, false);
        SelectViewHolders rcv = new SelectViewHolders(mSelectListener, layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(SelectViewHolders holder, int position) {
//        DiaryActivity act = mActivityList.get(position);
//        NumberFormat formatter = new DecimalFormat("#0.00");
//        holder.mName.setText(act.getName());
//// show likelyhood in activity name
////     holder.mName.setText(act.getName() + " (" + formatter.format(ActivityHelper.helper.likelihoodFor(act)) + ")");
//// TODO #33:        holder.mSymbol.setImageResource(act.getPhoto());
//        holder.mBackground.setBackgroundColor(act.getColor());
//        holder.mName.setTextColor(GraphicsHelper.textColorOnBackground(act.getColor()));

        List<DiaryActivity> actGroup = mActivityListGroup.get(mActivityListGroup.keySet().toArray()[position]);
//        holder.mActivity = actGroup;
        RecyclerView interRecycleView = holder.itemView.findViewById(R.id.select_card_view_2);
        interRecycleView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false){


            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                int screenHeight = getHeight();
                int itemHeight = screenHeight / 3;

                return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, itemHeight);
            }
        });
        internViewAdapter innerAdapter = new internViewAdapter(mSelectListener, actGroup);
        interRecycleView.setAdapter(innerAdapter);

        TextView typeText = (TextView) holder.itemView.findViewById(R.id.typeTextView);
        typeText.setText(defineTypeText(position + 1));


        // TODO #31: set the width based on the likelyhood
    }
    public static String defineTypeText(int type){
        switch (type){
            case internViewAdapter.LIFE:
                return "生活";
            case internViewAdapter.WORK:
                return "工作";
            case internViewAdapter.HEALTH:
                return "健康";
            case internViewAdapter.RELAX:
                return "娱乐";
            default:
                return "其他";
        }
    }

    @Override
    public int getItemCount() {
        return mActivityListGroup.size();
    }

    public interface SelectListener{
        void onItemClick(int adapterPosition);
        boolean onItemLongClick(int adapterPosition);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public int positionOf(DiaryActivity activity){
        // return mActivityList.indexOf(activity);

        for(List<DiaryActivity> list : mActivityListGroup.values()){
            int tmp = list.indexOf(activity);
            if(tmp != -1) return tmp;
        }
        return -1;
    }

    public DiaryActivity item(int id){
        //return mActivityList.get(id);
        for(List<DiaryActivity> list : mActivityListGroup.values()){
            for(DiaryActivity i  : list){
                if(i.getId() == id) return i;
            }
        }
        return null;
    }
}
