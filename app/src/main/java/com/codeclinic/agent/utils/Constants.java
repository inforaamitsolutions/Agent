package com.codeclinic.agent.utils;

public class Constants {

    /*media section*/
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_FROM_GALLERY = 2;
    public static final int ACCESS_CAMERA_GALLERY = 3;
    public static final int ACCESS_SIGNATURE = 4;
    public static final String PICTURE_PATH = "picturePath";
    public static final String SIGNATURE_PATH = "signaturePath";
    public static int DEVICE_WIDTH = 0;

    public static int HOME_FRAGMENT = 1;
    public static int LOAN_FRAGMENT = 2;
    public static int LEAD_FRAGMENT = 3;
    public static int CUSTOMER_FRAGMENT = 4;

    /*Intent Claus*/
    public static String CustomerID = "customerId";
    public static String LoanNumber = "loanNumber";
    public static String AllInteraction = "allInteraction";
    public static String DueFollowUp = "dueFollowUp";
    public static String PromiseToPays = "promiseToPays";

    //Survey actions
    public static String CreateCustomer = "createCustomer";
    public static String CreateLead = "createLead";
    public static String BusinessUpdate = "businessUpdate";
    public static String SupplierUpdate = "supplierUpdate";
}
