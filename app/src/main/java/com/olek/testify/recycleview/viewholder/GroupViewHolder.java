package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olek.testify.R;

import org.w3c.dom.Text;


/*


 */

public class GroupViewHolder extends RecyclerView.ViewHolder {


    public TextView codeNameView;
    public TextView descriptionView;
    public TextView language;


    public GroupViewHolder(View v) {
        super(v);


        codeNameView = (TextView) v.findViewById(R.id.card_group_code_name);
        descriptionView = (TextView) v.findViewById(R.id.card_group_description);
        language = (TextView) v.findViewById(R.id.card_group_language);
    }
}
