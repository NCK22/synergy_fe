package com.nyasa.synergyfieldengineer.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.nyasa.synergyfieldengineer.Activity.TabParentCaseDetailActivity;
import com.nyasa.synergyfieldengineer.R;
import com.nyasa.synergyfieldengineer.Pojo.ChildPojoCase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitListAdapter extends RecyclerView.Adapter<VisitListAdapter.ItemRowHolder> implements Serializable {

    private ArrayList<ChildPojoCase> dataList;
    private Context mContext;
    ProgressDialog progressDialog;
    ArrayList<String> list_profile=new ArrayList<String>();


    public VisitListAdapter(Context context, ArrayList<ChildPojoCase> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        progressDialog=new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait adapter");
/*        mListItem = new ArrayList<ChildPojoProfile>();
        spCustProfile=new SPCustProfile(mContext);*/
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_list_row, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemRowHolder holder, final int position) {

        Log.e("BindviewHolder","called");
    //    spCustProfile=new SPCustProfile(mContext);


        final ChildPojoCase singleItem = dataList.get(position);
        holder.case_id.setText(singleItem.getCaseNo());
        holder.applicant_name.setText(singleItem.getClientName());
        holder.project_name.setText(singleItem.getProjectName());
       // if(singleItem.getVillageCity()!=null)
        //holder.address1.setText(singleItem.getVillageCity()+" ,"+singleItem.getPincode());
        holder.address1.setText("City : N/A"+" ,"+singleItem.getPincode());
        holder.address2.setText(singleItem.getState());
        holder.contact.append(singleItem.getClientContactNo());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext.startActivity(new Intent(mContext, TabParentCaseDetailActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView case_id,applicant_name,project_name,address1,address2,contact;
        public LinearLayout lyt_parent;
        public Button btnEdit;
        ImageButton ibtnInterest;

        public ItemRowHolder(View itemView) {
            super(itemView);

            case_id = (TextView) itemView.findViewById(R.id.tv_caseId);
            applicant_name = (TextView) itemView.findViewById(R.id.tv_applicantName);
            project_name = (TextView) itemView.findViewById(R.id.tv_projectName);
            address1 = (TextView) itemView.findViewById(R.id.tv_address1);
            address2 = (TextView) itemView.findViewById(R.id.tv_address2);
            contact=(TextView)itemView.findViewById(R.id.tv_contact);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit);

        }
    }
        public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    }

