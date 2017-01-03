package com.example.audi_abir.triumphsecurity.fragment;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.animation.ValueAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.audi_abir.triumphsecurity.R;
import com.example.audi_abir.triumphsecurity.activity.MainActivity;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.R.attr.fragment;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private EditText a,b;
    private MaterialTextField inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //login
        auth = FirebaseAuth.getInstance();

        //btnSignIn = (Button) findViewById(R.id.circularButton1);
        //btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (MaterialTextField) view.findViewById(R.id.username);
        inputPassword = (MaterialTextField) view.findViewById(R.id.userpass);
        a=(EditText) view.findViewById(R.id.useremail);
        b=(EditText) view.findViewById(R.id.userpasse);
        //btnResetPassword = (Button) view.findViewById(R.id.btn_reset_password);
        //
        // Inflate the layout for this fragment

        final CircularProgressButton circularButton1 = (CircularProgressButton) view.findViewById(R.id.circularButton1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (circularButton1.getProgress() == 0) {

                    //
                    simulateSuccessProgress(circularButton1);


                }else {
                    circularButton1.setProgress(0);


                    /**/

                }
                //login
                String email = a.getText().toString();
                final String password = b.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        a.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag("fragment_tag_String");
                                    if(fragment != null)
                                        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction ft = fm.beginTransaction();
                                    ft.replace(R.id.home_layout, new LockFragment(), "fragment_tag_String");
                                    ft.addToBackStack("fragment_tag_String");
                                    ft.commit();
                                    fm.executePendingTransactions();
                                }
                            }
                        });
                //logend


                /*Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.home_layout).getClass().getName();
                if(fragment != null)
                    getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_layout, new LockFragment())
                        .commit().executePendingTransactions();
                RelativeLayout layout = (RelativeLayout) a.findViewById(R.id.homerelay);
                layout.removeAllViews();

        FragmentManager fmg = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fmg.getBackStackEntryCount(); ++i) {
            fmg.popBackStack();
        }*/


                //





            }
        });

        /*final CircularProgressButton circularButton2 = (CircularProgressButton) view.findViewById(R.id.circularButton2);
        circularButton2.setIndeterminateProgressMode(true);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton2.getProgress() == 0) {
                    circularButton2.setProgress(50);
                } else if (circularButton2.getProgress() == -1) {
                    circularButton2.setProgress(0);
                } else {
                    circularButton2.setProgress(-1);
                }
            }
        });*/
        //return inflater.inflate(R.layout.fragment_home, container, false);
        final CircularProgressButton circularButton2 = (CircularProgressButton) view.findViewById(R.id.circularButton2);
        circularButton2.setIndeterminateProgressMode(true);
        circularButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (circularButton1.getProgress() == 0) {
                    final String email =a.getText().toString();
                    final String password =b.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getActivity().getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    System.out.println("abi======="+a.getText().toString());
                                    Toast.makeText(getActivity(),"Success", Toast.LENGTH_SHORT).show();

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Fragment fragment = new PhotosFragment();

                                        //FragmentManager fragmentManager = getFragmentManager();

                                        //fragmentManager.beginTransaction().replace(R.id.home_layout, fragment).commit();
                                        //getActivity().finish();
                                    }
                                }
                            });

                    //
                    simulateSuccessProgress(circularButton2);
                } else {
                    circularButton2.setProgress(0);
                }
                //



            }
        });

        return view;
    }
    private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(2000);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }
    private void simulateErrorProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 99);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
                if (value == 99) {
                    button.setProgress(-1);
                }
            }
        });
        widthAnimation.start();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.home_layout);
        if(fragment != null)
            getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
