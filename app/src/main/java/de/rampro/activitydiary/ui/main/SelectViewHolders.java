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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.rampro.activitydiary.R;
import de.rampro.activitydiary.helpers.GraphicsHelper;
import de.rampro.activitydiary.model.DiaryActivity;

class SelectViewHolders extends RecyclerView.ViewHolder  {

  /*  public TextView mName;
    public ImageView mSymbol;
    public View mBackground;*/
    private SelectRecyclerViewAdapter.SelectListener mListener;
    public  List<DiaryActivity> mActivity;

    public RecyclerView mViewRecyclerView;







    public SelectViewHolders(SelectRecyclerViewAdapter.SelectListener listener, View itemView) {
        super(itemView);
      /*  itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mName = (TextView) itemView.findViewById(R.id.activity_name);
        mSymbol = (ImageView) itemView.findViewById(R.id.activity_image);
        mBackground = (View) itemView.findViewById(R.id.activity_background); */
        mListener = listener;
        //mViewRecyclerView = (RecyclerView) itemView.findViewById(R.id.select_card_view_2);
        //mViewRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        //mViewRecyclerView.setAdapter(new internViewAdapter(listener, mActivity));
    }

  /*  @Override
    public void onClick(View view) {
        final int position = getAdapterPosition();
        if(position != RecyclerView.NO_POSITION) {
            mListener.onItemClick(position);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        final int position = getAdapterPosition();
        if(position != RecyclerView.NO_POSITION) {
            return mListener.onItemLongClick(position);
        }
        return false;
    }          */



}
