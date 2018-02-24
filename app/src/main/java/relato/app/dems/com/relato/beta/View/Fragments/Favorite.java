package relato.app.dems.com.relato.beta.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import relato.app.dems.com.relato.beta.Model.ItemFeed;
import relato.app.dems.com.relato.beta.R;
import relato.app.dems.com.relato.beta.View.Details.DetailsRelato;
import relato.app.dems.com.relato.beta.View.Util.ViewHolder.RelatoViewHolderStructure;

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorite extends Fragment {

    private RecyclerView mRecyclerMisLecturas;
    private DatabaseReference mDatabaseMisLecturas;
    private FirebaseAuth mAuth;

    public Favorite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        mDatabaseMisLecturas = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabaseMisLecturas.keepSynced(true);

        LinearLayoutManager layoutManagerMisLecturas
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        layoutManagerMisLecturas.setReverseLayout(true);
        layoutManagerMisLecturas.setStackFromEnd(true);

        mRecyclerMisLecturas = (RecyclerView) view.findViewById(R.id.fragmento_mis_favoritos);
        mRecyclerMisLecturas.setHasFixedSize(true);

        mRecyclerMisLecturas.setLayoutManager(layoutManagerMisLecturas);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        mAuth = FirebaseAuth.getInstance();

        //Query queryRef = mDatabaseMisLecturas.orderByChild("IdMiLectura").equalTo(userId);
        Query queryRef = mDatabaseMisLecturas.child(userId);


        FirebaseRecyclerAdapter<ItemFeed, RelatoViewHolderStructure>
                firebaseRecyclerAdapterMyLecturas = new FirebaseRecyclerAdapter<ItemFeed, RelatoViewHolderStructure>(
                ItemFeed.class,
                R.layout.design_structure_relato_menu,
                RelatoViewHolderStructure.class,queryRef) {
            @Override
            protected void populateViewHolder(RelatoViewHolderStructure viewHolder, ItemFeed model, final int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setCatergory(model.getCategory());
                viewHolder.setAuthor(model.getAuthor());

                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());



                viewHolder.mViewStructure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mProgress.setMessage("Accediendo...");
                        // mProgress.show();
                        Toast.makeText(getContext(),"Identificador "+post_key,Toast.LENGTH_SHORT).show();
                        Intent singleBlogIntent = new Intent(getActivity().getApplicationContext(), DetailsRelato.class);
                        singleBlogIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        singleBlogIntent.putExtra("blog_id", post_key);
                        startActivity(singleBlogIntent);




                        Log.v("id","id"+post_key);

                      /*  new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
     mDatabaseMisLecturas.child(post_key).removeValue();
                            }id-L51uKEvbrgMuX8p42No
                        }, 2000);*/
                    }
                });
            }
        };

        mRecyclerMisLecturas.setAdapter(firebaseRecyclerAdapterMyLecturas);




        return view;
    }

}









