package com.techdatum.epanchayat_application.webservices

import android.content.Context
import com.google.gson.Gson
import com.techdatum.epanchayat_application.EPanchayatApplication


class SaveSharedPreference {
    private val ePanchayatSP = "EpanchayatSP"
    private val ePanchayatClientName = "EpanchayatClientName"
    private val ePanchayatClientOfficeId = "EPanchayatClientOfficeId"
    private val EpanchayatClientPhonenumber = "EpanchayatClientInfo"

    private val AppUserId = "AppUserId"
    private val TitleName = "TitleName"
    private val Name = "Name"
    private val SurName = "SurName"
    private val UserName = "UserName"
    private val Password = "Password"
    private val Gender = "Gender"
    private val DateofBirth = "DateofBirth"
    private val MobileNumber = "MobileNumber"
    private val IMEINumber = "IMEINumber"
    private val EmailId = "EmailId"
    private val RoleId = "RoleId"
    private val Role = "Role"
    private val DistrictId = "DistrictId"
    private val District = "District"
    private val DivisionId = "DivisionId"
    private val Division = "Division"
    private val MandalId = "MandalId"
    private val Mandal = "Mandal"
    private val PanchayatId = "PanchayatId"
    private val Panchayat = "Panchayat"
    private val NicOfficeId = "NicOfficeId"
    private val IsApproved = "IsApproved"
    private val ProfilePicName = "ProfilePicName"


    companion object {
        var sessionData: SaveSharedPreference? = SaveSharedPreference()
        fun getSessionDataInstance(): SaveSharedPreference {
            if (sessionData == null) {
                sessionData = SaveSharedPreference()
            }
            return sessionData!!
        }
    }

    fun saveRole(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Role, name)
        } else {
            e.putString(Role, null)
        }
        e.commit()
    }

    fun getRole(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Role,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Role, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }


    fun saveDistrict(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(District, name)
        } else {
            e.putString(District, null)
        }
        e.commit()
    }

    fun getDistrict(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                District,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(District, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }













    fun saveAppUserId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(AppUserId, name)
        } else {
            e.putString(AppUserId, null)
        }
        e.commit()
    }

    fun getAppUserIde(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                AppUserId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(AppUserId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }


    fun saveTitleName(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(TitleName, name)
        } else {
            e.putString(TitleName, null)
        }
        e.commit()
    }

    fun getTitleName(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                TitleName,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(TitleName, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveName(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Name, name)
        } else {
            e.putString(Name, null)
        }
        e.commit()
    }

    fun getName(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Name,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Name, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveClientSurName(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(SurName, name)
        } else {
            e.putString(SurName, null)
        }
        e.commit()
    }

    fun getSurName(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                SurName,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(SurName, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveUserName(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(UserName, name)
        } else {
            e.putString(UserName, null)
        }
        e.commit()
    }

    fun getUserName(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                UserName,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(UserName, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun savePassword(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Password, name)
        } else {
            e.putString(Password, null)
        }
        e.commit()
    }

    fun getPassword(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Password,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Password, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveGender(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Gender, name)
        } else {
            e.putString(Gender, null)
        }
        e.commit()
    }

    fun getGender(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Gender,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Gender, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveDateofBirth(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(DateofBirth, name)
        } else {
            e.putString(DateofBirth, null)
        }
        e.commit()
    }

    fun getDateofBirth(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                DateofBirth,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(DateofBirth, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveMobileNumber(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(MobileNumber, name)
        } else {
            e.putString(MobileNumber, null)
        }
        e.commit()
    }

    fun getMobileNumber(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                MobileNumber,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(MobileNumber, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveIMEINumber(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(IMEINumber, name)
        } else {
            e.putString(IMEINumber, null)
        }
        e.commit()
    }

    fun getIMEINumber(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                IMEINumber,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(IMEINumber, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveEmailId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(EmailId, name)
        } else {
            e.putString(EmailId, null)
        }
        e.commit()
    }

    fun getEmailId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                EmailId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(EmailId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveRoleId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(RoleId, name)
        } else {
            e.putString(RoleId, null)
        }
        e.commit()
    }

    fun getRoleId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                RoleId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(RoleId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveDistrictId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(DistrictId, name)
        } else {
            e.putString(DistrictId, null)
        }
        e.commit()
    }

    fun getDistrictId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                DistrictId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(DistrictId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveDivisionId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(DivisionId, name)
        } else {
            e.putString(DivisionId, null)
        }
        e.commit()
    }

    fun getDivisionId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                DivisionId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(DivisionId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveDivision(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Division, name)
        } else {
            e.putString(Division, null)
        }
        e.commit()
    }

    fun getDivision(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Division,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Division, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveMandalId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(MandalId, name)
        } else {
            e.putString(MandalId, null)
        }
        e.commit()
    }

    fun getMandalId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                MandalId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(MandalId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveMandal(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Mandal, name)
        } else {
            e.putString(Mandal, null)
        }
        e.commit()
    }

    fun getMandal(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Mandal,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Mandal, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun savePanchayatId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(PanchayatId, name)
        } else {
            e.putString(PanchayatId, null)
        }
        e.commit()
    }

    fun getPanchayatId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                PanchayatId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(PanchayatId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun savePanchayat(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(Panchayat, name)
        } else {
            e.putString(Panchayat, null)
        }
        e.commit()
    }

    fun getPanchayat(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                Panchayat,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(Panchayat, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveNicOfficeId(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(NicOfficeId, name)
        } else {
            e.putString(NicOfficeId, null)
        }
        e.commit()
    }

    fun getNicOfficeId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                NicOfficeId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(NicOfficeId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveIsApproved(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(IsApproved, name)
        } else {
            e.putString(IsApproved, null)
        }
        e.commit()
    }

    fun getIsApproved(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                IsApproved,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(IsApproved, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveProfilePicName(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(ProfilePicName, name)
        } else {
            e.putString(ProfilePicName, null)
        }
        e.commit()
    }

    fun getProfilePicName(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                ProfilePicName,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(ProfilePicName, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun saveClientName(name: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (name != null) {
            e.putString(EpanchayatClientPhonenumber, name)
        } else {
            e.putString(EpanchayatClientPhonenumber, null)
        }
        e.commit()
    }

    fun getClientClientname(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                EpanchayatClientPhonenumber,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(EpanchayatClientPhonenumber, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }


    fun saveClientmobilenumber(mobilenumber: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (mobilenumber != null) {
            e.putString(ePanchayatClientName, mobilenumber)
        } else {
            e.putString(ePanchayatClientName, null)
        }
        e.commit()
    }

    fun saveClientOfficeId(officeid: String?) {
        val e = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        if (officeid != null) {
            e.putString(ePanchayatClientOfficeId, officeid)
        } else {
            e.putString(ePanchayatClientOfficeId, null)
        }
        e.commit()
    }


    fun getClientmobilenumber(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                ePanchayatClientName,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(ePanchayatClientName, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun getClientofficeId(): String? {
        if (EPanchayatApplication.getInstance().getSharedPreferences(
                ePanchayatSP,
                Context.MODE_PRIVATE
            ).getString(
                ePanchayatClientOfficeId,
                null
            ) == null
        ) {
            return null
        } else {
            val data = EPanchayatApplication.getInstance()
                .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE)
                .getString(ePanchayatClientOfficeId, null)
            val gson = Gson()
            val obj = gson.toJson(data)
            return obj
        }
    }

    fun clearAll() {
        val editor = EPanchayatApplication.getInstance()
            .getSharedPreferences(ePanchayatSP, Context.MODE_PRIVATE).edit()
        editor.clear().apply()
        editor.apply()
    }
}