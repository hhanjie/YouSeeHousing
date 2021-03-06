package com.example.youseehousing.lib.listing;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.youseehousing.lib.fragment.ActivityFragmentOrigin;
import com.example.youseehousing.lib.fragment.ListPageFragment;
import com.example.youseehousing.lib.fragment.RefreshableListFragmentPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 *
 *  This class handles querying the database and populating the list of items
 *
 */
public class MainHousingListing_PopulateList extends ListPage {
    private ActivityFragmentOrigin afoActivity;
    private String TAG = "MainHousingListing";

//    public static List<ListingDetails> ITEMS = new ArrayList<ListingDetails>();

    private static final int COUNT = 75; // Max number of listings to query at once from database.
    private final ListPageFragment.ListType TYPE = ListPageFragment.ListType.MAIN_LISTING_PAGE;

    /**
     * Constructor
     * @param activityFragmentOrigin : the activity to which this object belongs
     */
    public MainHousingListing_PopulateList(Activity activityFragmentOrigin, RefreshableListFragmentPage fragment) {
        super(activityFragmentOrigin, fragment);
        afoActivity = super.getActivityFragmentOrigin();
//        super.clearList();
        queryDatabase();
    }

    /**
     * This method queries the Cloud Firestore database for COUNT listings.
     * And calls addListingToPage for each retrieved
     * Returns true if query was successful
     * Returns false otherwise
     * TODO: Paginate data https://firebase.google.com/docs/firestore/query-data/query-cursors
     * TODO: Querying database probably deserves its own class
     **/
    private boolean queryDatabase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (db != null) {
            db.collection("listing")
                    .limit(COUNT)
                    .orderBy("price")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // call function to get listing details
                                    ListingDetails item =
                                            ListingDetails.makeListingDetailsFromDocumentSnapshot(document);
                                    // Run item through filter and if it passes, add it to page
                                    if (item != null) {
                                        if (checkListingPassesFilter(item)) {
                                            Log.i(TAG, "Listing " + item.getAddress() + " passes filter!");
                                            addListingToTemporaryBuffer(item);
                                        } else {
                                            Log.i(TAG, "Listing " + item.getAddress() + " does not pass filter!");
                                        }
                                    }
                                }
                                // When done adding items, call assign new list
                                // This is an attempted fix for the index out of bounds exception
                                assignNewItemsList();

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

            return true;
        }
        else {
            Log.e(TAG, "db reference is null!");
            return false;
        }
    }

    /**
     * If listing passes filters, return true.
     * @param item
     */
    private boolean checkListingPassesFilter(ListingDetails item) {
        return DaFilter.getInstance().passes(item);
    }
}

