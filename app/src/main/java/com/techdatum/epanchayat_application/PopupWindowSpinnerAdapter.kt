package com.techdatum.epanchayat_application

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.techdatum.epanchayat_application.databinding.Popupcommonspinner
import com.techdatum.epanchayat_application.datamodelclasses.ListOfRoleMasterDataModelClass

class PopupWindowSpinnerAdapter(
    val mContext: Context,
    val list: List<ListOfRoleMasterDataModelClass>
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val spinnerModel = Popupcommonspinner.inflate(layoutInflater, parent, false)
        spinnerModel.databinding = list.get(position)
        return spinnerModel.root
    }

    override fun getItem(position: Int): Any {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}