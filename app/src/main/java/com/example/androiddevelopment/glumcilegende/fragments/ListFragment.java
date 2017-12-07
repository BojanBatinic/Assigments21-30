package com.example.androiddevelopment.glumcilegende.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androiddevelopment.glumcilegende.R;


/**
 * Created by BBLOJB on 21.11.2017..
 */
// Each Fragment extends Fragment class
public class ListFragment extends Fragment {

    // Container Activity must implement this interface
    public interface OnProductSelectedListener {
        void onProductSelected(int id);
    }
    OnProductSelectedListener listener;

    // onCreate method is a life-cycle method that is called when creating the fragment.
   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onActivityCreated method is a life-cycle method that is called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

     /*   //Loads glumce from array resource
        String[] glumci = getResources().getStringArray(R.array.glumci);

        //creates an ArrayAdapter from the array of String
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, glumci);
        ListView listView = (ListView) getView().findViewById(R.id.glumci);

        //assigns ArrayAdapter to ListView
        listView.setAdapter(dataAdapter);

        //updates ListFragment
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Send the URL to the host activity
                listener.onProductSelected((int)id);
            }
        }); */

    }

    // onCreateView method is a life-cycle method that is called to have the fragment instantiate its user interface view.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //shows a toast message ( a pop-up message)
       // Toast.makeText(getActivity(), "ListFragment.onCreateView()", Toast.LENGTH_SHORT).show();
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    // onDestroyView method is a life-cycle method that is called when the view previously created by onCreateView(LayoutInflater, ViewGroup, Bundle) has been detached from the fragment.

  /*  @Override
    public void onDestroyView() {
        super.onDestroyView();

        //shows a toast message (a pop-up messsage)
        Toast.makeText(getActivity(), "ListFragment.onDestroyView()", Toast.LENGTH_SHORT).show();
    } */

    // onAttach method is a life-cycle method that is called when a fragment is first attached to its context.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

     /*   //shows a toast messsage (a pop-up message)
        try {
            listener = (OnProductSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnItemSelectedListener");
        }*/
    }

  }
