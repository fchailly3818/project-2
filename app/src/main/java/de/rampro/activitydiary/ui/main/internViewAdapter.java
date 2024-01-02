/*
 * ActivityDiary
 *
 * Copyright (C) 2023 Raphael Mack http://www.raphael-mack.de
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.rampro.activitydiary.R;
import de.rampro.activitydiary.helpers.GraphicsHelper;
import de.rampro.activitydiary.model.DiaryActivity;

public class internViewAdapter extends RecyclerView.Adapter<internViewAdapter.internViewHolder> {

    private List<DiaryActivity> mActivityList;
    private SelectRecyclerViewAdapter.SelectListener mSelectListener;
    public static final int LIFE = 1;
    public static final int WORK = 2;
    public static final int HEALTH = 3;
    public static final int RELAX = 4;

    public internViewAdapter(SelectRecyclerViewAdapter.SelectListener selectListener, List<DiaryActivity> activityList) {
        mActivityList = activityList;
        mSelectListener = selectListener;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public internViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_recycler_item, parent, false);
        internViewHolder rcv = new internViewHolder(mSelectListener, layoutView);

        return rcv;
        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull internViewHolder holder, int position) {
        DiaryActivity act = mActivityList.get(position);
        NumberFormat formatter = new DecimalFormat("#0.00");
        holder.mName.setText(act.getName());
// show likelyhood in activity name
//     holder.mName.setText(act.getName() + " (" + formatter.format(ActivityHelper.helper.likelihoodFor(act)) + ")");
// TODO #33:        holder.mSymbol.setImageResource(act.getPhoto());
        holder.mBackground.setBackgroundColor(act.getColor());
        holder.mName.setTextColor(GraphicsHelper.textColorOnBackground(act.getColor()));
    }

    @Override
    public int getItemCount() {
        return mActivityList.size();
    }

    @Override
    public long getItemId(int position) {
        return mActivityList.get(position).getId();
    }

    public int positionOf(DiaryActivity activity) {
        return mActivityList.indexOf(activity);
    }

    public DiaryActivity item(int id) {
        return mActivityList.get(id);
    }

    public class internViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public ImageView mSymbol;
        public View mBackground;
        private SelectRecyclerViewAdapter.SelectListener mListener;

        internViewHolder(SelectRecyclerViewAdapter.SelectListener listener, View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(mActivityList.get(position).getId());
                }
            });
            itemView.setOnLongClickListener(v -> {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    return mListener.onItemLongClick(mActivityList.get(position).getId());
                }
                return false;
            });
            mName = (TextView) itemView.findViewById(R.id.activity_name);
            mSymbol = (ImageView) itemView.findViewById(R.id.activity_image);
            mBackground = (View) itemView.findViewById(R.id.activity_background);
            mListener = listener;
        }
    }


}
