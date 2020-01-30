package com.reynagagroup.ryelloreport;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.reynagagroup.ryelloreport.adapter.CartAdapter;
import com.reynagagroup.ryelloreport.adapter.PaymentAdapter;
import com.reynagagroup.ryelloreport.model.CartItemModel;
import com.reynagagroup.ryelloreport.model.UploadBuktiModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.hoang8f.widget.FButton;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.reynagagroup.ryelloreport.DetailActivity.ket_et;
import static com.reynagagroup.ryelloreport.DetailActivity.kirim_et;
import static com.reynagagroup.ryelloreport.DetailActivity.status_now;

public class DBqueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static List<UploadBuktiModel> uploadBuktiModelList = new ArrayList<>();

    public static List<CartItemModel> cartItemModelList = new ArrayList<>();
    private static StorageReference storageReference;

    private static int i,n;
    private static String imageUrl;
    public static int iterasi_order_1,n_order_1;

    public static void loadPayment(final Context context, final Dialog dialog, final String tgl){


        i=0;
        uploadBuktiModelList.clear();
       dialog.show();
        storageReference = FirebaseStorage.getInstance().getReference("bukti");


        firebaseFirestore.collection("USERS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document : task.getResult()) {

                        n=task.getResult().size();
                        final String id_user = document.getId();

                        firebaseFirestore.collection("USERS").document(document.getId()).collection("USER_NOTA")
                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (!task.getResult().isEmpty()){
                                    if (task.isSuccessful()) {
                                        String id = document.getId();

                                        for (final QueryDocumentSnapshot document : task.getResult()) {
                                            firebaseFirestore.collection("USERS").document(id).collection("USER_NOTA")
                                                    .document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {

                                                            i++;

                                                            if (!tgl.equals("")) {
                                                                if (tgl.equals(changeDate(task.getResult().getDate("tgl_pesan")))) {


                                                                    uploadBuktiModelList.add(new UploadBuktiModel(
                                                                            document.getId()
                                                                            , task.getResult().get("atasNama").toString()
                                                                            , id_user
                                                                            , task.getResult().get("username").toString()
                                                                            , task.getResult().get("imageUrl").toString()
                                                                            , task.getResult().get("bank").toString()
                                                                            , task.getResult().get("tgl_transfer").toString()
                                                                            , task.getResult().get("fullnameAddress").toString()
                                                                            , task.getResult().get("fullAddress").toString()
                                                                            , task.getResult().get("phone").toString()
                                                                            , task.getResult().get("email").toString()
                                                                            , task.getResult().get("pincodeAddress").toString()
                                                                            , task.getResult().getDate("tgl_pesan")
                                                                            , Integer.parseInt(Long.toString((Long) task.getResult().get("totalAmount")))
                                                                            , Integer.parseInt(Long.toString((Long) task.getResult().get("totalItems")))
                                                                            , Integer.parseInt(Long.toString((Long) task.getResult().get("totalItemsPrice")))
                                                                            , Integer.parseInt(Long.toString((Long) task.getResult().get("savedAmount")))
                                                                            , task.getResult().get("deliveryPrice").toString()
                                                                            , (boolean) task.getResult().get("ordered")
                                                                            , (boolean) task.getResult().get("packed")
                                                                            , (boolean) task.getResult().get("shipped")
                                                                            , (boolean) task.getResult().get("delivered")
                                                                            , (boolean) task.getResult().get("canceled")
                                                                            , task.getResult().getDate("ordered_date")
                                                                            , task.getResult().getDate("packed_date")
                                                                            , task.getResult().getDate("shipped_date")
                                                                            , task.getResult().getDate("delivered_date")
                                                                            , task.getResult().getDate("canceled_date")
                                                                            , task.getResult().get("ket_kirim").toString()
                                                                            , task.getResult().get("metode_kirim").toString()
                                                                            , (boolean) task.getResult().get("isfree")
                                                                    ));

                                                                }
                                                            } else {

                                                                    Date date = new Date();
                                                                    String dateString = changeDate(date);
                                                                    MainActivity.tgl.setText(dateString);
                                                                    Log.i("ddds",changeDate(task.getResult().getDate("tgl_pesan")));
                                                                Log.i("DATE",dateString);


                                                                if (dateString.equals(changeDate(task.getResult().getDate("tgl_pesan")))) {


                                                                        uploadBuktiModelList.add(new UploadBuktiModel(
                                                                                document.getId()
                                                                                , task.getResult().get("atasNama").toString()
                                                                                , id_user
                                                                                , task.getResult().get("username").toString()
                                                                                , task.getResult().get("imageUrl").toString()
                                                                                , task.getResult().get("bank").toString()
                                                                                , task.getResult().get("tgl_transfer").toString()
                                                                                , task.getResult().get("fullnameAddress").toString()
                                                                                , task.getResult().get("fullAddress").toString()
                                                                                , task.getResult().get("phone").toString()
                                                                                , task.getResult().get("email").toString()
                                                                                , task.getResult().get("pincodeAddress").toString()
                                                                                , task.getResult().getDate("tgl_pesan")
                                                                                , Integer.parseInt(Long.toString((Long) task.getResult().get("totalAmount")))
                                                                                , Integer.parseInt(Long.toString((Long) task.getResult().get("totalItems")))
                                                                                , Integer.parseInt(Long.toString((Long) task.getResult().get("totalItemsPrice")))
                                                                                , Integer.parseInt(Long.toString((Long) task.getResult().get("savedAmount")))
                                                                                , task.getResult().get("deliveryPrice").toString()
                                                                                , (boolean) task.getResult().get("ordered")
                                                                                , (boolean) task.getResult().get("packed")
                                                                                , (boolean) task.getResult().get("shipped")
                                                                                , (boolean) task.getResult().get("delivered")
                                                                                , (boolean) task.getResult().get("canceled")
                                                                                , task.getResult().getDate("ordered_date")
                                                                                , task.getResult().getDate("packed_date")
                                                                                , task.getResult().getDate("shipped_date")
                                                                                , task.getResult().getDate("delivered_date")
                                                                                , task.getResult().getDate("canceled_date")
                                                                                , task.getResult().get("ket_kirim").toString()
                                                                                , task.getResult().get("metode_kirim").toString()
                                                                                , (boolean) task.getResult().get("isfree")
                                                                        ));

                                                                    }



                                                            }


                                                            if (i > n) {

                                                                if(uploadBuktiModelList.size()>1) {
                                                                    Collections.sort(uploadBuktiModelList, new Comparator<UploadBuktiModel>() {
                                                                        @Override
                                                                        public int compare(UploadBuktiModel o1, UploadBuktiModel o2) {
                                                                            if (o1.getTgl_pesan() == null || o2.getTgl_pesan() == null)
                                                                                return 0;
                                                                            return o1.getTgl_pesan().compareTo(o2.getTgl_pesan());
                                                                        }

                                                                    });


                                                                    Collections.sort(uploadBuktiModelList, Collections.reverseOrder());
                                                               }


                                                                MainActivity.paymentAdapter = new PaymentAdapter(DBqueries.uploadBuktiModelList, context);
                                                                MainActivity.paymentRecyclerView.setAdapter(MainActivity.paymentAdapter);

                                                                MainActivity.linearLayoutManager = new LinearLayoutManager(context);
                                                                MainActivity.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                                                MainActivity.paymentRecyclerView.setLayoutManager(MainActivity.linearLayoutManager);


                                                                MainActivity.paymentAdapter.notifyDataSetChanged();
                                                                dialog.dismiss();
                                                                if (uploadBuktiModelList.isEmpty()){
                                                                    MainActivity.kosong.setVisibility(View.VISIBLE);
                                                                }else {
                                                                    MainActivity.kosong.setVisibility(View.INVISIBLE);
                                                                }
                                                                MainActivity.swipeRefreshLayout.setRefreshing(false);

                                                            }

                                                        }

                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });




                                        }
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }else {

                                    i++;

                                }

                            }
                        });
                    }

                    if (i == n) {
                        if (uploadBuktiModelList.isEmpty()){
                            MainActivity.kosong.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.kosong.setVisibility(View.INVISIBLE);
                        }
                        MainActivity.paymentAdapter = new PaymentAdapter(DBqueries.uploadBuktiModelList, context);
                        MainActivity.paymentRecyclerView.setAdapter(MainActivity.paymentAdapter);

                        MainActivity.linearLayoutManager = new LinearLayoutManager(context);
                        MainActivity.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        MainActivity.paymentRecyclerView.setLayoutManager(MainActivity.linearLayoutManager);

                        MainActivity.paymentAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        MainActivity.swipeRefreshLayout.setRefreshing(false);

                    }

                    }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static void loadItemPayment(final Context context, final Dialog dialog, final String iduser,final String idpayment){


        i=0;
        cartItemModelList.clear();

             firebaseFirestore.collection("USERS").document(iduser).collection("USER_NOTA")
                .document(idpayment).collection("ITEM").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        n=task.getResult().size();
                        Log.i("total",Integer.toString(n));
                        for (final QueryDocumentSnapshot document : task.getResult()) {
                            final String id_item = document.getId();

                            firebaseFirestore.collection("USERS").document(iduser).collection("USER_NOTA")
                                    .document(idpayment).collection("ITEM").document(id_item).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    i++;

                                        CartItemModel cartItemModel =new CartItemModel(CartItemModel.CART_ITEM, id_item,
                                            task.getResult().get("productImage").toString()
                                            , task.getResult().get("productTitle").toString()
                                            , (long) task.getResult().get("freeCoupons")
                                            , task.getResult().get("productPrice").toString()
                                            , task.getResult().get("oriPrice").toString()
                                            , (long) task.getResult().get("productQuantity")
                                            , (long) task.getResult().get("offersApplied")
                                            , (long) task.getResult().get("couponsApplied")
                                            , (Boolean) task.getResult().get("inStock")
                                            , Integer.parseInt(task.getResult().get("ratting").toString())
                                                , task.getResult().get("satuan").toString()
                                        );
                                    if ((long) task.getResult().get("couponsApplied")>0) {
                                        cartItemModel.setDiscountedPrice(task.getResult().get("discountedPrice").toString());
                                        cartItemModel.setSelectedCouponId(task.getResult().get("selectedCouponId").toString());
                                    }
                                    cartItemModelList.add(cartItemModel);





                                    if (i == n) {
                                        cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                        //DetailActivity.cartAdapter.notifyDataSetChanged();

                                        DetailActivity.cartAdapter = new CartAdapter(cartItemModelList);
                                        DetailActivity.item_recyclerview.setAdapter(DetailActivity.cartAdapter);

                                        DetailActivity.linearLayoutManager = new LinearLayoutManager(context);
                                        DetailActivity.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                        DetailActivity.item_recyclerview.setLayoutManager(DetailActivity.linearLayoutManager);

                                        DetailActivity.cartAdapter.notifyDataSetChanged();
                                        dialog.dismiss();

                                    }


                                }
                            });
                        }
                    }
                 }
             });



    }

    public static void updateDatePayment(final Context context, final Dialog dialog, final String iduser, final String idpayment, final Date tgl, final TextView status, final FButton konfirm) {

        final Map<String, Object> updatePayment = new HashMap<>();


        if(status_now!=0){
            if(status_now!=1){
                if(status_now!=2){
                    if(status_now==3){
                        updatePayment.put("delivered",(Boolean)true);
                        updatePayment.put("delivered_date", tgl);
                    }
                }else {
                    updatePayment.put("shipped",(Boolean)true);
                    updatePayment.put("shipped_date", tgl);
                    updatePayment.put("ket_kirim", ket_et.getText().toString());
                    updatePayment.put("metode_kirim", kirim_et.getText().toString());
                }
            }else {
                updatePayment.put("packed",(Boolean)true);
                updatePayment.put("packed_date", tgl);
            }
        }else {
            updatePayment.put("ordered",(Boolean)true);
            updatePayment.put("ordered_date", tgl);

        }

        firebaseFirestore.collection("USERS").document(iduser).collection("USER_NOTA")
                .document(idpayment).update(updatePayment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if (status_now!=0){
                        if (status_now!=1){
                            if (status_now!=2){
                                if (status_now==3) {
                                    status_now++;
                                    konfirm.setVisibility(View.GONE);
                                    status.setText("Telah Terkirim");
                                    Toast.makeText(context, "Pesanan Telah Terkirim..", Toast.LENGTH_SHORT).show();
                                    if (status_now==4) {

                                        DBqueries.addReward(context, idpayment, iduser, dialog);
                                    }
                                }
                            }else {
                                status_now++;
                                konfirm.setText("Terkirim");
                                konfirm.setButtonColor(context.getResources().getColor(R.color.delivered));
                                Toast.makeText(context, "Pesanan Sedang Dikirim..", Toast.LENGTH_SHORT).show();
                                status.setText("Sedang Dikirim");
                            }
                        }else {
                            status_now++;
                            konfirm.setText("Dikirim");
                            konfirm.setButtonColor(context.getResources().getColor(R.color.shipping));
                            Toast.makeText(context, "Pesanan Telah Dikemas..", Toast.LENGTH_SHORT).show();
                            status.setText("Telah Dikemas");
                        }
                    }else {
                        status_now++;
                        konfirm.setText("Dikemas");
                        konfirm.setButtonColor(context.getResources().getColor(R.color.packed));
                        Toast.makeText(context, "Pesanan Telah Dikonfirmasi..", Toast.LENGTH_SHORT).show();
                        status.setText("Telah Dikonfirmasi");
                    }

                    if (status_now>2){
                        //sedang-sudah dikirim
                        DetailActivity.ket.setVisibility(View.VISIBLE);
                        DetailActivity.ket_tv.setVisibility(View.VISIBLE);
                        DetailActivity.kirim.setVisibility(View.VISIBLE);
                        DetailActivity.kirim_tv.setVisibility(View.VISIBLE);
                        DetailActivity.ket.setText(ket_et.getText().toString());
                        DetailActivity.kirim.setText(kirim_et.getText().toString());
                    }else {
                        DetailActivity.ket.setVisibility(View.GONE);
                        DetailActivity.ket_tv.setVisibility(View.GONE);
                        DetailActivity.kirim.setVisibility(View.GONE);
                        DetailActivity.kirim_tv.setVisibility(View.GONE);
                    }

                    if (status_now ==2){
                        //akan-dikirim
                        ket_et.setVisibility(View.VISIBLE);
                        kirim_et.setVisibility(View.VISIBLE);
                    }else {
                        ket_et.setVisibility(View.GONE);
                        kirim_et.setVisibility(View.GONE);
                    }


                    if (status_now!=3) {
                        dialog.dismiss();
                    }

                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public  static void addReward (final Context context, final String id_nota, final String id_user, final Dialog loadingDialog){


        firebaseFirestore.collection("USERS").document(id_user).collection("USER_NOTA").document(id_nota)
                .collection("ITEM").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    iterasi_order_1 = 0;
                    n_order_1=task.getResult().size();
                    for (final DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        firebaseFirestore.collection("USERS").document(id_user).collection("USER_NOTA").document(id_nota)
                                .collection("ITEM").document(documentSnapshot.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if ((long) task.getResult().get("freeCoupons") > 0) {

                                    String product_ID = task.getResult().getString("productID");

                                    firebaseFirestore.collection("PRODUCTS").document(product_ID)
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                final String reward = task.getResult().get("free_coupon_type").toString();


                                                Map<String, Object> rewarddata = new HashMap<>();
                                                long DAY_IN_MS = 1000 * 60 * 60 * 24;
                                                Date date = new Date();
                                                date = new Date(date.getTime() + (2 * DAY_IN_MS));
                                                if (task.getResult().get("free_coupon_type").toString().toUpperCase().equals("DISCOUNT")) {
                                                    rewarddata.put("type", task.getResult().get("free_coupon_type").toString());
                                                    rewarddata.put("body", task.getResult().get("free_coupon_body").toString());
                                                    rewarddata.put("precentage", task.getResult().get("free_coupon_precentage").toString());
                                                    rewarddata.put("lower_limit", task.getResult().get("free_coupon_lower_limit").toString());
                                                    rewarddata.put("upper_limit", task.getResult().get("free_coupon_upper_limit").toString());
                                                    rewarddata.put("already_used", false);
                                                    rewarddata.put("validity", date);


                                                } else {
                                                    rewarddata.put("type", task.getResult().get("free_coupon_type").toString());
                                                    rewarddata.put("body", task.getResult().get("free_coupon_body").toString());
                                                    rewarddata.put("amount", task.getResult().get("free_coupon_amount").toString());
                                                    rewarddata.put("lower_limit", task.getResult().get("free_coupon_lower_limit").toString());
                                                    rewarddata.put("upper_limit", task.getResult().get("free_coupon_upper_limit").toString());
                                                    rewarddata.put("already_used", false);
                                                    rewarddata.put("validity", date);
                                                }


                                                firebaseFirestore.collection("USERS").document(id_user).collection("USER_REWARDS").add(rewarddata)
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                if (task.isSuccessful()) {






                                                                    firebaseFirestore.collection("USERS").document(id_user).collection("USER_DATA")
                                                                            .document("MY_NOTIFICATIONS").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {

                                                                                if (task.getResult()!=null) {
                                                                                    Map<String, Object> notifdata = new HashMap<>();
                                                                                    notifdata.put("list_size", (long)task.getResult().getLong("list_size") + 1);
                                                                                    notifdata.put("Body_" + (long) task.getResult().getLong("list_size"), "Selamat Anda Mendapatkan Reward " + reward);
                                                                                    notifdata.put("Image_" + (long) task.getResult().getLong("list_size"), "");
                                                                                    notifdata.put("Readed_" + (long) task.getResult().getLong("list_size"), false);

                                                                                    firebaseFirestore.collection("USERS").document(id_user).collection("USER_DATA")
                                                                                            .document("MY_NOTIFICATIONS").update(notifdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            iterasi_order_1++;


                                                                                            if (iterasi_order_1 == n_order_1) {


                                                                                                loadingDialog.dismiss();

                                                                                            }
                                                                                        }
                                                                                    });
                                                                                }
                                                                            } else {
                                                                                String error = task.getException().getMessage();
                                                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                                                loadingDialog.dismiss();
                                                                            }
                                                                        }
                                                                    });



                                                                } else {
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                                    loadingDialog.dismiss();
                                                                }
                                                            }
                                                        });


                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                loadingDialog.dismiss();
                                            }
                                        }
                                    });

                                }else {
                                    iterasi_order_1++;
                                    if (iterasi_order_1 == n_order_1) {
                                        loadingDialog.dismiss();
                                    }

                                }
                            }
                        });

                    }
                }else {

                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();

                }

            }});



    }


    public static String changeDate(Date date){

        final String NEW_FORMAT = "d/M/yyyy";
        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
        newDateString = sdf.format(date);
        return newDateString;
    }

}
