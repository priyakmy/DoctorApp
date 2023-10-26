package com.mcura.mcurapharmacy.Utils;

/**
 * Created by mCURA1 on 11/24/2016.
 */
public class EnumType {
    public static enum LabPharmacyType {
        mPharmacy(1), mlab(2);
        private int id;

        LabPharmacyType(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum StatusType {
        mPending(1), mComplete(2), mRequested(3), mBillingDone(4), mSampleCollection(5), mReportCollection(6);
        private int id;

        StatusType(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum EntryType {
        mData(1), mHandwriting(2), mVideo(6), mImage(7), mTextFile(8);
        private int id;

        EntryType(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public static enum loginRoleType {

        mLabOrderUser(5), mPharmacyUser(4);
        private int roleId;

        loginRoleType(int roleId) {
            this.roleId = roleId;
        }

        public int getID() {
            return roleId;
        }
    }

    public static enum facilityTypeId {

        mLabOrderType(3), mPharmacyType(4);
        private int facilityId;

        facilityTypeId(int roleId) {
            this.facilityId = roleId;
        }

        public int getID() {
            return facilityId;
        }
    }

    public static enum RecordNatureEnum {
        Chief_Complaints("Chief Complaints", 1),
        Lab_Report("Lab Report", 2),
        Advice("Advice", 3),
        Lab_Order("Lab Order", 4),
        Doctor_Note("Doctor Note", 5),
        Current_Visit_Image("Current Visit Image", 6),
        Doctor_Comments("Doctor Comments", 7),
        Referrals("Referrals", 8),
        Template("Template", 9),
        Plan(" Plan", 11),
        Medical_History("Medical History", 12),
        Visit_Summary("Visit Summary", 13),
        Clinical_Summary(" Clinical Summary", 14),
        Physical_Examination("Physical Examination", 15),
        Lab_Report_Finding("Lab Report Finding", 16),
        Instructions("Instructions", 18),
        Next_Follow_Up(" Next Follow Up", 19);

        String natureName;
        int natureId;

        RecordNatureEnum(String toString, int value) {
            natureName = toString;
            natureId = value;
        }

        public String getNatureName() {
            return natureName;
        }

        public int getNatureId() {
            return natureId;
        }
    }
    public static enum LabObjNature{
        kLabObjNaturePackage(0),kLabObjNatureGroup(1),kLabObjNatureTest(2);
        private int labObjNatureId;

        LabObjNature(int roleId) {
            this.labObjNatureId = roleId;
        }

        public int getID() {
            return labObjNatureId;
        }
    }

    public static enum PaymentStatusId {
        mPaymentSuccessfull(1), mFillCashCard(2), mBlankHospitalNo(3), mPaymentNotDone(4), mPaymentALreadyDone(5), mPaymentDone(6), mErrorInPayment(7), mPaymentModeNotCorrect(8), mOrderTransactionIdNull(9), mPaymentBlankScheduleId(10);
        private int statusId;

        PaymentStatusId(int statusId) {
            this.statusId = statusId;
        }

        public int getStatusId() {
            return statusId;
        }
    }
}
