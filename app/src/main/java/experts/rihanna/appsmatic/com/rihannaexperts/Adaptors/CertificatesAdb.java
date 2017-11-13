package experts.rihanna.appsmatic.com.rihannaexperts.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Get.CertificatesList;
import experts.rihanna.appsmatic.com.rihannaexperts.API.ModelsPOJO.Certificates.Update.Certificate;
import experts.rihanna.appsmatic.com.rihannaexperts.API.WebServiceTools.Generator;
import experts.rihanna.appsmatic.com.rihannaexperts.Helpers.Dialogs;
import experts.rihanna.appsmatic.com.rihannaexperts.R;

/**
 * Created by Eng Ali on 11/12/2017.
 */
public class CertificatesAdb extends RecyclerView.Adapter<CertificatesAdb.vh0> {


    private CertificatesList certificatesList;
    private Context context;

    //This var is a flag to determine the operation update or register to control fragments refresh
    private int operationMode;

    public CertificatesAdb(CertificatesList certificatesList, Context context, int operationMode) {
        this.certificatesList = certificatesList;
        this.context = context;
        this.operationMode = operationMode;
    }

    @Override
    public vh0 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new vh0(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_certificate_info,parent,false));
    }

    @Override
    public void onBindViewHolder(final vh0 holder, final int position) {

        holder.certName.setText(certificatesList.getCertificates().get(position).getName());
        holder.certYear.setText(certificatesList.getCertificates().get(position).getYearAcquired()+"");
      //  holder.certSpicialty.setText(certificatesList.getCertificates().get(position).);
        holder.certGranter.setText(certificatesList.getCertificates().get(position).getAuthorizedBy());



        //Updates button action
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                holder.editBtn.clearAnimation();
                holder.editBtn.setAnimation(anim);
                Certificate editecert=new Certificate();
                editecert.setId(certificatesList.getCertificates().get(position).getId());
                editecert.setName(certificatesList.getCertificates().get(position).getName());
                editecert.setServiceCategoryId(certificatesList.getCertificates().get(position).getServiceCategoryId());
                editecert.setAuthorizedBy(certificatesList.getCertificates().get(position).getAuthorizedBy());
                editecert.setYearAcquired(certificatesList.getCertificates().get(position).getYearAcquired() + "");
                editecert.setExpertId(certificatesList.getCertificates().get(position).getExpertId());
                Dialogs.fireUpdateCertDialog(context,holder.editBtn,editecert,operationMode);

            }
        });



    }

    @Override
    public int getItemCount() {
        return certificatesList.getCertificates().size();
    }

    public static class vh0 extends RecyclerView.ViewHolder{
        private TextView certName,certGranter,certSpicialty,certYear;
        private ImageView editBtn;
        public vh0(View itemView) {
            super(itemView);
            certName=(TextView)itemView.findViewById(R.id.cert_name_tv);
            certGranter=(TextView)itemView.findViewById(R.id.cert_granter_tv);
            certSpicialty=(TextView)itemView.findViewById(R.id.cert_sicilaty_tv);
            certYear=(TextView)itemView.findViewById(R.id.cert_year_tv);
            editBtn=(ImageView)itemView.findViewById(R.id.edit_cert_btn);
        }
    }
}
