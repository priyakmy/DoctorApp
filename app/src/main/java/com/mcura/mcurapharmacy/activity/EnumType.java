package com.mcura.mcurapharmacy.activity;

public class EnumType {
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
    public static enum AppointmentOrNot {
        mAppointmentAlreadyFixed(1), mNewAppointment(2);
        private int id;

        AppointmentOrNot(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    public enum ScheduleStatus {
        kScheduleStatusChartNotGenerated(1), kScheduleStatusChartGenerated(2);
        private int id;

        ScheduleStatus(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
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
        Next_Follow_Up("Next Follow Up", 19),
        Diagnosis("Diagnosis",20),
        Remarks("Remarks",21),
        Free_Text("Free Text",101),
        Provisional_Diagnosis("Provisional Diagnosis",22),
        Investigation("Investigation",23),
        Lab_Order_Later("Lab Order Later",25),
        Admission("Admission",26);

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
    public static enum SummaryDataTypeEnum {
        Docs("Docs",0),
        Clips("Clips",1),
        Instructions("Instructions",2);

        String summaryDataTypeName;
        int summaryDataTypeId;

        SummaryDataTypeEnum(String toString, int value) {
            summaryDataTypeName = toString;
            summaryDataTypeId = value;
        }

        public String getSummaryDataTypeName() {
            return summaryDataTypeName;
        }

        public int getSummaryDataTypeId() {
            return summaryDataTypeId;
        }

    }

    public static enum SummaryClassTypeEnum {
        Free_Text("Free Text",0),
        Video("Video",1),
        Stylus("Stylus",2),
        Pdf("Pdf",3),
        Template("Template",4),
        AVH("AVH",5);

        String summaryClassTypeName;
        int summaryClassTypeId;

        SummaryClassTypeEnum(String toString, int value) {
            summaryClassTypeName = toString;
            summaryClassTypeId = value;
        }

        public String getSummaryClassTypeName() {
            return summaryClassTypeName;
        }

        public int getSummaryClassTypeId() {
            return summaryClassTypeId;
        }
    }
    public static enum ActTransactMasterEnum{
        Booking_APT("Booking APT", 1),
        Block_APT("Block APT", 2),
        Unblock_APT("Unblock APT", 3),
        CANCEL_APT("CANCEL APT", 4),
        ReSchedule_APT("ReSchedule APT", 5),
        Schedule_Creation("Schedule Creation", 6),
        Schedule_ReScheduling("Schedule ReScheduling", 7),
        Start_OPD("Start OPD", 8),
        End_OPD("End OPD", 9),
        Check_In("Check In", 10),
        Visiting("Visiting", 11),
        Checkout("Checkout", 12),
        Msg_Broadcast("Msg Broadcast", 13),
        Patient_Record_Upload("Patient Record Upload", 14),
        Attempt_To_Download("Attempt to download", 15),
        User_Login("User Login", 16),
        Register_Patient_From_Queue("Register Patient From Queue", 17),
        Edit_Patient_From_Queue("Edit Patient From Queue", 18),
        No_Show("No Show", 19),
        Register_Patient_From_Top_Panel("Register Patient From Top Panel", 22),
        Register_patient_From_Appointment("Register Patient From Appointment", 23),
        Edit_Patient_From_Top_Panel("Edit Patient From Top Panel", 24),
        video_subscribe("Video Subscribe", 27),
        video_unsubscribe("Video Unsubscribe", 28);


        String ActTransactMasterTypeName;
        int ActTransactMasterTypeId;

        ActTransactMasterEnum(String toString, int value) {
            ActTransactMasterTypeName = toString;
            ActTransactMasterTypeId = value;
        }

        public String getActTransactMasterTypeName() {
            return ActTransactMasterTypeName;
        }

        public int getActTransactMasterTypeId() {
            return ActTransactMasterTypeId;
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
