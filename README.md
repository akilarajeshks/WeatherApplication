NOTES:
    After working through this entire exercise, the following areas can be made better:

    1. In Navigation Library, each fragment is considered as a destination which is added to the backstack. In this application, each fragment need not be a separate destination because we don't want each fragment to be added in the backstack(i.e when a fragment_loader navigates to fragment_error we don't want fragment_loader on backpress).
     Hence, instead of keeping different fragments , it can be rendered as different layouts for different states/responses in a single fragment (single destination).

    2. OkHttpClient and retrofit which are being used as a singleton, can be injected as we may get requirements for their re-usability.